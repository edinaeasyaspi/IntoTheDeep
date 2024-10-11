package edu.edina.opmodes.teleop.test;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import org.firstinspires.ftc.robotcore.internal.camera.calibration.CameraCalibration;
import org.opencv.core.Mat;
import org.opencv.imgproc.Imgproc;

import java.util.ArrayList;
import java.util.Collections;

import edu.edina.library.util.projection.Point;
import edu.edina.library.util.projection.Projector;
import edu.edina.library.util.projection.Vec;

public class BackProjectionVisionProcessor implements org.firstinspires.ftc.vision.VisionProcessor {
    private Mat hsvMat;
    private double fovDiag;
    private String diagString;
    private Point[] points;
    private ArrayList<Integer> xList, yList;
    private Projector proj;

    public BackProjectionVisionProcessor() {
        hsvMat = new Mat();
        points = new Point[4];
    }

    @Override
    public void init(int width, int height, CameraCalibration calibration) {
        if (width != 640)
            throw new RuntimeException("width != 640");
        if (height != 480)
            throw new RuntimeException("height != 480");
        xList = new ArrayList<>();
        yList = new ArrayList<>();
        proj = new Projector(0, 640, 480);
    }

    @Override
    public Object processFrame(Mat frame, long captureTimeNanos) {
        Imgproc.cvtColor(frame, hsvMat, Imgproc.COLOR_RGB2HSV);

        points[0] = hueMedian(frame, 60);
        points[1] = hueMedian(frame, 120);
        points[2] = hueMedian(frame, 180);
        points[3] = hueMedian(frame, 300);

        for (Point p : points)
            if (p == null)
                return null;

        Vec[] v = proj.backProjectSquare(points);


        return points;
    }

    @Override
    public void onDrawFrame(Canvas canvas, int onscreenWidth, int onscreenHeight, float scaleBmpPxToCanvasPx, float scaleCanvasDensity, Object userContext) {
        Paint paint = new Paint();
        paint.setColor(Color.YELLOW);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(scaleCanvasDensity * 4);

        for (Point p : points) {
            if (p == null)
                continue;

            float x = (float) (p.x * scaleBmpPxToCanvasPx);
            float y = (float) (p.y * scaleBmpPxToCanvasPx);
            canvas.drawLine(x, y - 3, x, y + 3, paint);
            canvas.drawLine(x - 3, y, x + 3, y, paint);
        }
    }

    private Point hueMedian(Mat frame, int hue360) {
        int hueMin = (hue360 - 30) / 2;
        int hueMax = (hue360 + 30) / 2;

        xList.clear();
        yList.clear();

        double[] pix = new double[3];
        for (int i = 0; i < frame.height(); i++) {
            for (int j = 0; j < frame.width(); j++) {
                frame.get(i, j, pix);
                if (hueMin <= pix[0] && pix[0] <= hueMax) {
                    xList.add(j);
                    yList.add(i);
                }
            }
        }

        if (xList.size() == 0)
            return null;

        return new Point(median(xList), median(yList));
    }

    private static int median(ArrayList<Integer> list) {
        Collections.sort(list);
        return list.get(list.size() / 2);
    }
}
