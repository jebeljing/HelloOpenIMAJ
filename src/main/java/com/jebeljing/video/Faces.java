package com.jebeljing.video;

import org.openimaj.image.FImage;
import org.openimaj.image.MBFImage;
import org.openimaj.image.colour.RGBColour;
import org.openimaj.image.colour.Transforms;
import org.openimaj.image.processing.face.detection.DetectedFace;
import org.openimaj.image.processing.face.detection.FaceDetector;
import org.openimaj.image.processing.face.detection.HaarCascadeDetector;
import org.openimaj.image.processing.face.detection.keypoints.FKEFaceDetector;
import org.openimaj.image.processing.face.detection.keypoints.FacialKeypoint;
import org.openimaj.image.processing.face.detection.keypoints.KEDetectedFace;
import org.openimaj.image.typography.hershey.HersheyFont;
import org.openimaj.math.geometry.point.Point2d;
import org.openimaj.math.geometry.shape.Ellipse;
import org.openimaj.math.geometry.shape.Rectangle;
import org.openimaj.video.VideoDisplay;
import org.openimaj.video.VideoDisplayListener;
import org.openimaj.video.capture.VideoCapture;
import org.openimaj.video.capture.VideoCaptureException;

import java.util.List;

/**
 * Created by jingshanyin on 4/15/18.
 */
public class Faces {

    public static void main(String[] args) throws VideoCaptureException {
        VideoCapture vc = new VideoCapture(320, 240);
        VideoDisplay<MBFImage> vd = VideoDisplay.createVideoDisplay(vc);
        vd.addVideoListener(new VideoDisplayListener<MBFImage>() {
            @Override
            public void afterUpdate(VideoDisplay<MBFImage> videoDisplay) {

            }

            @Override
            public void beforeUpdate(MBFImage fImages) {
//                FaceDetector<DetectedFace, FImage> fd = new HaarCascadeDetector(40);
//                List<DetectedFace> faces = fd.detectFaces(Transforms.calculateIntensity(fImages));
                FaceDetector<KEDetectedFace, FImage> fd = new FKEFaceDetector();
                List<KEDetectedFace> faces = fd.detectFaces(Transforms.calculateIntensity(fImages));
                for (KEDetectedFace face: faces) {
                    fImages.drawShape(face.getBounds(), RGBColour.RED);
                    for(FacialKeypoint fk: face.getKeypoints()) {
                        Point2d pt = fk.position;
                        pt.translate((float)face.getBounds().minX(), (float)face.getBounds().minY());
//                        System.out.println("X=" + pt.getX() + ",Y=" + pt.getY());
                        fImages.drawPoint(pt, RGBColour.GREEN, 3);
                    }

//                    System.out.println(face.getKeypoints().length);
                    Point2d pt = face.getKeypoints()[7].position;

                    fImages.drawShapeFilled(new Ellipse(pt.getX(), pt.getY(), 5f, 2.5f, 0f), RGBColour.WHITE);
                    fImages.drawShapeFilled(new Ellipse(pt.getX() - 20f, pt.getY() - 10f, 8f, 4f, 0f), RGBColour.WHITE);
                    fImages.drawShapeFilled(new Ellipse(pt.getX() - 40f, pt.getY() - 30f, 12f, 8f, 0f), RGBColour.WHITE);
                    fImages.drawShapeFilled(new Ellipse(pt.getX() - 80f, pt.getY() - 60f, 20f, 15f, 0f), RGBColour.WHITE);
                    fImages.drawText("OpenIMAJ is", (int)pt.getX() - 80, (int)pt.getY() - 60, HersheyFont.ASTROLOGY, 10, RGBColour.BLACK);
                    fImages.drawText("Awesome", (int)pt.getX() - 70, (int)pt.getY() - 50, HersheyFont.ASTROLOGY, 10, RGBColour.BLACK);
                }
            }
        });
    }
}
