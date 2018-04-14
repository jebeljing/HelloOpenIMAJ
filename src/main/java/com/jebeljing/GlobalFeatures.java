package com.jebeljing;

import org.openimaj.feature.DoubleFVComparison;
import org.openimaj.image.DisplayUtilities;
import org.openimaj.image.ImageUtilities;
import org.openimaj.image.pixel.statistics.HistogramModel;
import org.openimaj.math.statistics.distribution.MultidimensionalHistogram;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by jingshanyin on 4/13/18.
 */
public class GlobalFeatures {

    public static void main(String[] args) throws IOException {
        URL[] imageURLs = new URL[] {
                new URL("http://users.ecs.soton.ac.uk/dpd/projects/openimaj/tutorial/hist1.jpg"),
                new URL("http://users.ecs.soton.ac.uk/dpd/projects/openimaj/tutorial/hist2.jpg"),
                new URL("http://users.ecs.soton.ac.uk/dpd/projects/openimaj/tutorial/hist3.jpg")
        };
        List<MultidimensionalHistogram> histograms = new ArrayList<>();
        HistogramModel model = new HistogramModel(4, 4, 4);
        for(URL u: imageURLs) {
            model.estimateModel(ImageUtilities.readMBF(u));
            histograms.add(model.histogram.clone());
        }

        double min = Double.MAX_VALUE;
        int minI = -1;
        int minJ = -1;
        for (int i = 0; i < histograms.size(); i++) {
            for (int j = i; j < histograms.size(); j++) {
//                double distance = histograms.get(i).compare(histograms.get(j), DoubleFVComparison.EUCLIDEAN);
                double distance = histograms.get(i).compare(histograms.get(j), DoubleFVComparison.INTERSECTION);
                System.out.println("i=" + i + ", j=" + j + ", distance=" + distance);
                if (i != j && distance < min) {
                    min = distance;
                    minI = i;
                    minJ = j;
                }
            }
        }
        DisplayUtilities.display(ImageUtilities.readMBF(imageURLs[minI]));
        DisplayUtilities.display(ImageUtilities.readMBF(imageURLs[minJ]));
    }
}
