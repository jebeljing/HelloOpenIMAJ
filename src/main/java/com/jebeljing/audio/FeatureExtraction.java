package com.jebeljing.audio;

import org.openimaj.audio.AudioFormat;
import org.openimaj.audio.JavaSoundAudioGrabber;
import org.openimaj.audio.SampleChunk;
import org.openimaj.audio.features.MFCC;
import org.openimaj.audio.processor.FixedSizeSampleAudioProcessor;
import org.openimaj.video.xuggle.XuggleAudio;
import org.openimaj.vis.general.BarVisualisation;

import java.io.File;

/**
 * Created by jingshanyin on 4/16/18.
 */
public class FeatureExtraction {

    public static void main(String[] args) {

//        final BarVisualisation vis = new BarVisualisation(400, 400);
//        vis.setAxisLocation( 100 );
//        vis.showWindow("MFCCs");
//        SampleChunk sc = null;
//        final XuggleAudio xa = new XuggleAudio(new File("/Users/jingshanyin/Desktop/hellovideo/audios/audiocheck.net_sweep20-20klin.wav"));
//        MFCC mfcc = new MFCC(xa);
//        while ((sc = mfcc.nextSampleChunk()) != null) {
//            double[][] mfccs = mfcc.getLastCalculatedFeatureWithoutFirst();
//            vis.setData(mfccs[0]);
//        }

        JavaSoundAudioGrabber jsag = new JavaSoundAudioGrabber(new AudioFormat(16, 44.1, 1));
        new Thread(jsag).start();
        FixedSizeSampleAudioProcessor fssap = new FixedSizeSampleAudioProcessor(jsag, 441*3, 441);
        final MFCC mfcc = new MFCC(fssap);
        final BarVisualisation vis = new BarVisualisation(400, 400);
        vis.setAxisLocation( 100 );
        vis.showWindow("MFCCs");
        while (mfcc.nextSampleChunk() != null) {
            final double[][] mfccs = mfcc.getLastCalculatedFeatureWithoutFirst();
            vis.setData(mfccs[0]);
        }
    }
}
