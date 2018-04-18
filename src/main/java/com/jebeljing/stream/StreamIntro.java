package com.jebeljing.stream;

import org.openimaj.image.DisplayUtilities;
import org.openimaj.image.MBFImage;
import org.openimaj.image.processing.face.detection.DetectedFace;
import org.openimaj.image.processing.face.detection.HaarCascadeDetector;
import org.openimaj.stream.functions.ImageFromURL;
import org.openimaj.stream.functions.ImageSiteURLExtractor;
import org.openimaj.stream.functions.twitter.TwitterURLExtractor;
import org.openimaj.stream.provider.twitter.TwitterStreamDataset;
import org.openimaj.util.api.auth.DefaultTokenFactory;
import org.openimaj.util.api.auth.common.TwitterAPIToken;
import org.openimaj.util.function.MultiFunction;
import org.openimaj.util.function.Operation;
import org.openimaj.util.stream.Stream;
import twitter4j.Status;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by jingshanyin on 4/17/18.
 */
public class StreamIntro {

    public static void main(String[] args) {
        TwitterAPIToken token = DefaultTokenFactory.get(TwitterAPIToken.class);
        TwitterStreamDataset stream = new TwitterStreamDataset(token);
//        stream.forEach(new Operation<Status>() {
//            public void perform(Status status) {
//                System.out.println(status.getText());
//            }
//        });
        Stream<URL> urlStream = stream.map(new TwitterURLExtractor());
        Stream<URL> imageUrlStream = urlStream.map(new ImageSiteURLExtractor(false));
        Stream<MBFImage> imageStream = imageUrlStream.map(ImageFromURL.MBFIMAGE_EXTRACTOR);
        HaarCascadeDetector detector = HaarCascadeDetector.BuiltInCascade.frontalface_default.load();
        imageStream.map(new MultiFunction<MBFImage, MBFImage>() {
            @Override
            public List<MBFImage> apply(MBFImage fImages) {
                List<DetectedFace> detected = detector.detectFaces(fImages.flatten());
                List<MBFImage> faces = new ArrayList<MBFImage>();
                for (DetectedFace face: detected) {
                    faces.add(fImages.extractROI(face.getBounds()));
                }
                return faces;
            }
        }).forEach(new Operation<MBFImage>(){
            @Override
            public void perform(MBFImage fImages) {
                DisplayUtilities.displayName(fImages, "image");
            }
        });
    }
}
