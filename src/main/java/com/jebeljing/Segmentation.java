package com.jebeljing;

import org.openimaj.image.DisplayUtilities;
import org.openimaj.image.ImageUtilities;
import org.openimaj.image.MBFImage;
import org.openimaj.image.colour.ColourSpace;
import org.openimaj.image.connectedcomponent.GreyscaleConnectedComponentLabeler;
import org.openimaj.image.pixel.ConnectedComponent;
import org.openimaj.image.processor.PixelProcessor;
import org.openimaj.image.typography.hershey.HersheyFont;
import org.openimaj.ml.clustering.FloatCentroidsResult;
import org.openimaj.ml.clustering.assignment.HardAssigner;
import org.openimaj.ml.clustering.kmeans.FloatKMeans;

import java.io.IOException;
import java.net.URL;
import java.util.Arrays;
import java.util.List;

/**
 * Created by jingshanyin on 4/12/18.
 */
public class Segmentation {
    public static void main(String[] args) throws IOException {
        MBFImage input = ImageUtilities.readMBF(new URL("http://static.openimaj.org/media/tutorial/sinaface.jpg"));
        input = ColourSpace.convert(input, ColourSpace.CIE_Lab);
        FloatKMeans cluster = FloatKMeans.createExact(2);
        float[][] imageData = input.getPixelVectorNative(new float[input.getWidth() * input.getHeight()][3]);
        FloatCentroidsResult result = cluster.cluster(imageData);
        final float[][] centroids = result.centroids;
        for(float[] fs: centroids) {
            System.out.println(Arrays.toString(fs));
        }
        final HardAssigner<float[], ?, ?> assigner = result.defaultHardAssigner();
//        for (int y = 0; y < input.getHeight(); y++) {
//            for (int x = 0; x < input.getWidth(); x++) {
//                float[] pixel = input.getPixelNative(x, y);
//                int centroid = assigner.assign(pixel);
//                input.setPixelNative(x, y, centroids[centroid]);
//            }
//        }
        input.processInplace(new PixelProcessor<Float[]>() {
            @Override
            public Float[] processPixel(Float[] floats) {

                int centroid = assigner.assign(new float[2]);
                return centroids[centroid];
            }
        });
        input = ColourSpace.convert(input, ColourSpace.RGB);
        DisplayUtilities.display(input);

        GreyscaleConnectedComponentLabeler labeler = new GreyscaleConnectedComponentLabeler();
        List<ConnectedComponent> components = labeler.findComponents(input.flatten());

        int i = 0;
        for (ConnectedComponent comp: components) {
            if (comp.calculateArea() < 300) {
                continue;
            }
            input.drawText(""+(i++), comp.calculateCentroidPixel(), HersheyFont.TIMES_MEDIUM, 40);
        }
        DisplayUtilities.display(input);
    }
}
