package com.jebeljing.audio;

import org.openimaj.audio.AudioFormat;
import org.openimaj.audio.AudioPlayer;
import org.openimaj.audio.JavaSoundAudioGrabber;
import org.openimaj.audio.SampleChunk;
import org.openimaj.audio.analysis.FourierTransform;
import org.openimaj.audio.filters.EQFilter;
import org.openimaj.video.xuggle.XuggleAudio;
import org.openimaj.vis.audio.AudioSpectrogram;
import org.openimaj.vis.audio.AudioWaveform;
import org.openimaj.vis.general.BarVisualisation;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by jingshanyin on 4/16/18.
 */
public class AudioIntro {

    public static void main(String[] args) throws MalformedURLException, InterruptedException {

//        final AudioWaveform vis = new AudioWaveform(400, 400);
        final BarVisualisation vis = new BarVisualisation(400, 400);
        vis.setAutoScale(false);
        vis.getAxesRenderer().setDrawYTicks(false);
        vis.showWindow("Waveform");

//        final XuggleAudio xa = new XuggleAudio(
//                new URL("http://www.audiocheck.net/download.php?" +
//                "filename=Audio/audiocheck.net_sweep20-20klin.wav"));
        final XuggleAudio xa = new XuggleAudio(new File("/Users/jingshanyin/Desktop/hellovideo/audios/audiocheck.net_sweep20-20klin.wav"));
        SampleChunk sc = null;
//        while ((sc = xa.nextSampleChunk()) != null)
//            vis.setData(sc.getSampleBuffer());

        EQFilter eq = new EQFilter(xa, EQFilter.EQType.LPF, 5000);
        FourierTransform fft = new FourierTransform(eq);
        while ((sc = fft.nextSampleChunk()) != null) {
            float[][] fftData = fft.getMagnitudes();
            vis.setData(fftData[0]);
        }

/*
        final AudioSpectrogram aw = new AudioSpectrogram(400, 660);
        aw.showWindow("Spectrogram");
        final JavaSoundAudioGrabber audio = new JavaSoundAudioGrabber(new AudioFormat(16, 44.1, 1));
        new Thread(audio).start();
        while (audio.isStopped()) {
            Thread.sleep(50);
        }
        SampleChunk sc = null;
        while ((sc = audio.nextSampleChunk()) != null)
            aw.setData(sc);
            */
    }
}
