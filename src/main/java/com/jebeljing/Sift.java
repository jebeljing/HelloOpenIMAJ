package com.jebeljing;

import org.openimaj.feature.local.list.LocalFeatureList;
import org.openimaj.feature.local.matcher.*;
import org.openimaj.feature.local.matcher.consistent.ConsistentLocalFeatureMatcher2d;
import org.openimaj.image.DisplayUtilities;
import org.openimaj.image.ImageUtilities;
import org.openimaj.image.MBFImage;
import org.openimaj.image.colour.RGBColour;
import org.openimaj.image.feature.local.engine.DoGSIFTEngine;
import org.openimaj.image.feature.local.keypoints.Keypoint;
import org.openimaj.math.geometry.transforms.HomographyModel;
import org.openimaj.math.geometry.transforms.HomographyRefinement;
import org.openimaj.math.geometry.transforms.estimation.RobustAffineTransformEstimator;
import org.openimaj.math.geometry.transforms.estimation.RobustHomographyEstimator;
import org.openimaj.math.model.fit.RANSAC;

import java.io.IOException;
import java.net.URL;

/**
 * Created by jingshanyin on 4/13/18.
 */
public class Sift {

    public static void main(String[] args) throws IOException {
        MBFImage query = ImageUtilities.readMBF(new URL("http://static.openimaj.org/media/tutorial/query.jpg"));
        MBFImage target = ImageUtilities.readMBF(new URL("http://static.openimaj.org/media/tutorial/target.jpg"));

        DoGSIFTEngine engine = new DoGSIFTEngine();
        LocalFeatureList<Keypoint> queryKeypoints = engine.findFeatures(query.flatten());
        LocalFeatureList<Keypoint> targetKeypoints = engine.findFeatures(target.flatten());

        LocalFeatureMatcher<Keypoint> matcher = new BasicMatcher<Keypoint>(80);
        matcher.setModelFeatures(queryKeypoints);
        matcher.findMatches(targetKeypoints);

        MBFImage basicMatches = MatchingUtilities.drawMatches(query, target, matcher.getMatches(), RGBColour.RED);
        DisplayUtilities.display(basicMatches);

//        matcher = new BasicTwoWayMatcher<>();
//        matcher.setModelFeatures(queryKeypoints);
//        matcher.findMatches(targetKeypoints);
//
//        MBFImage basicTwoWayMatches = MatchingUtilities.drawMatches(query, target, matcher.getMatches(), RGBColour.RED);
//        DisplayUtilities.display(basicTwoWayMatches);

//        RobustAffineTransformEstimator modelFitter = new RobustAffineTransformEstimator(5.0, 1500,
//                new RANSAC.PercentageInliersStoppingCondition(0.5));
//        matcher = new ConsistentLocalFeatureMatcher2d<Keypoint>(new FastBasicKeypointMatcher<>(8), modelFitter);
//        matcher.setModelFeatures(queryKeypoints);
//        matcher.findMatches(targetKeypoints);
//        MBFImage consistentMatches = MatchingUtilities.drawMatches(query, target, matcher.getMatches(), RGBColour.RED);
//        DisplayUtilities.display(consistentMatches);

//        target.drawShape(query.getBounds().transform(modelFitter.getModel().getTransform().inverse()), 3, RGBColour.BLUE);
//        DisplayUtilities.display(target);

        RobustHomographyEstimator modelFitter = new RobustHomographyEstimator(5.0, 1500,
                new RANSAC.PercentageInliersStoppingCondition(0.5), HomographyRefinement.NONE);
        matcher = new ConsistentLocalFeatureMatcher2d<Keypoint>(new FastBasicKeypointMatcher<>(8), modelFitter);
        matcher.setModelFeatures(queryKeypoints);
        matcher.findMatches(targetKeypoints);
        MBFImage consistentMatches = MatchingUtilities.drawMatches(query, target, matcher.getMatches(), RGBColour.RED);
        DisplayUtilities.display(consistentMatches);

    }
}
