package com.jebeljing.video;

import org.openimaj.image.DisplayUtilities;
import org.openimaj.image.MBFImage;
import org.openimaj.image.processing.edges.CannyEdgeDetector;
import org.openimaj.video.Video;
import org.openimaj.video.VideoDisplay;
import org.openimaj.video.VideoDisplayListener;
import org.openimaj.video.capture.VideoCapture;
import org.openimaj.video.capture.VideoCaptureException;
import org.openimaj.video.xuggle.XuggleVideo;

import java.io.File;

/**
 * Created by jingshanyin on 4/15/18.
 */
public class VideoIntro {

    public static void main(String[] args) throws VideoCaptureException {
        Video<MBFImage> video = new XuggleVideo(new File("/Users/jingshanyin/Desktop/hellovideo/videos/keyboardcat.flv"));
        VideoDisplay<MBFImage> display = VideoDisplay.createVideoDisplay(video);

//        Video<MBFImage> video = new VideoCapture(320, 240);
//        VideoDisplay<MBFImage> display1 = VideoDisplay.createVideoDisplay(video);

//        for (MBFImage mbfImage: video) {
//            DisplayUtilities.displayName(mbfImage.process(new CannyEdgeDetector()), "videoFrames");
//        }

        display.addVideoListener(new VideoDisplayListener<MBFImage>() {
            @Override
            public void afterUpdate(VideoDisplay<MBFImage> videoDisplay) {

            }

            @Override
            public void beforeUpdate(MBFImage fImages) {
                fImages.processInplace(new CannyEdgeDetector());
            }
        });
    }
}
