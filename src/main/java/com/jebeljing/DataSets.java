package com.jebeljing;

import org.apache.commons.vfs2.FileSystemException;
import org.openimaj.data.dataset.*;
import org.openimaj.image.*;
import org.openimaj.image.dataset.BingImageDataset;
import org.openimaj.image.dataset.FlickrImageDataset;
import org.openimaj.util.api.auth.DefaultTokenFactory;
import org.openimaj.util.api.auth.common.BingAPIToken;
import org.openimaj.util.api.auth.common.FlickrAPIToken;

import java.util.Map;

import static org.openimaj.data.dataset.MapBackedDataset.of;

/**
 * Created by jingshanyin on 4/14/18.
 */
public class DataSets {

    public static void main(String[] args) throws Exception {
        VFSListDataset<FImage> images = new VFSListDataset<FImage>("/Users/jingshanyin/Desktop/hellovideo/images",
                ImageUtilities.FIMAGE_READER);
        System.out.println(images.size());
//        DisplayUtilities.display(images.getRandomInstance(), "A random image from the dataset");
//        DisplayUtilities.display("My images", images);

        //create datasets from other sources such as compressed archives containing data items and even from remote data
        // that is not stored on the local disk
        VFSListDataset<FImage> faces = new VFSListDataset<FImage>("zip:http://datasets.openimaj.org/att_faces.zip",
                ImageUtilities.FIMAGE_READER);
//        DisplayUtilities.display("ATT faces", faces);

//        VFSGroupDataset<FImage> groupedFaces = new VFSGroupDataset<FImage>("zip:http://datasets.openimaj.org/att_faces.zip",
//                ImageUtilities.FIMAGE_READER);
//        for (final Map.Entry<String, VFSListDataset<FImage>> entry: groupedFaces.entrySet()) {
//            DisplayUtilities.display(entry.getKey(), entry.getValue());
//            DisplayUtilities.display(entry.getValue().getRandomInstance(), "A random image per person");
//        }


        //Sometimes, it can be useful to be able to dynamically create a dataset of images from the web.
        // In the image analysis community, Flickr is often used as a source of tagged images for performing activities
        // such as training classifiers.
        /*
        Personal project for learning OpenIMAJ
        Key:
        3ed81d1f1520c8307935a1fefba87aa2

        Secret:
        dd19f9bdc61cbcc1
         */
        FlickrAPIToken flickrToken = DefaultTokenFactory.get(FlickrAPIToken.class);
//        FlickrImageDataset<FImage> cats = FlickrImageDataset.create(ImageUtilities.FIMAGE_READER, flickrToken, "cat", 10);
//        DisplayUtilities.display("Cats", cats);

        //BingImageDataset
//        BingAPIToken bingAPIToken = DefaultTokenFactory.get(BingAPIToken.class);
//        BingImageDataset<FImage> dogs = BingImageDataset.create(ImageUtilities.FIMAGE_READER, bingAPIToken, "dog", 10);
//        DisplayUtilities.display("Dogs", dogs);

    }
}
