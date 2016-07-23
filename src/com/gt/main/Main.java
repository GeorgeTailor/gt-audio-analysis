package com.gt.main;

import java.awt.Dimension;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.JScrollPane;

import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.xy.DefaultTableXYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.ui.ApplicationFrame;
import org.jfree.ui.RefineryUtilities;

import com.badlogic.audio.io.Decoder;
import com.badlogic.audio.io.MP3Decoder;
import com.badlogic.audio.io.WaveDecoder;
import com.gt.note.harmonic.finder.HarmonicsByIndexFinder;
import com.gt.user.UserInterface;
import com.gt.utils.AudioFiles;
import com.gt.utils.ChartUtils;
import com.gt.utils.FFTUtils;
import com.gt.utils.WindowUtils;

public class Main extends ApplicationFrame {

	private static final long serialVersionUID = 333695123646662221L;

	static JFreeChart chart;
	static JScrollPane sp;

	public Main(String title) throws Exception {
		super(title);
		DefaultTableXYDataset dataset = new DefaultTableXYDataset();// createDataset();//
		XYSeries xyseries = new XYSeries("Guitar D#m7", false, false);
		for (int j = 0; j < 1; j++) {
			xyseries.add(j, j);
		}
		dataset.addSeries(xyseries);
		/* JFreeChart */ chart = ChartUtils.createChart(dataset);
		ChartPanel chartPanel = new ChartPanel(chart);
		chartPanel.setPreferredSize(new Dimension(450, 450));
		JScrollPane sp = new JScrollPane(chartPanel);
		sp.setPreferredSize(new Dimension(500, 500));
		sp.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		setContentPane(sp);
	}

	public static DefaultTableXYDataset createDataset() throws Exception {

		DefaultTableXYDataset dataset = new DefaultTableXYDataset();

		//MP3Decoder decoder = new MP3Decoder( new FileInputStream(AudioFiles.tappingRun));
		//WaveDecoder decoder = new WaveDecoder(new FileInputStream(AudioFiles.voiceOfTheSoulPart));
		//WaveDecoder decoder = new WaveDecoder(new FileInputStream(AudioFiles.dMajorChordDistorted)); // D, A, F#
		//WaveDecoder decoder = new WaveDecoder(new FileInputStream(AudioFiles.gMajorClean)); //G, B, D
		WaveDecoder decoder = new WaveDecoder(new FileInputStream(AudioFiles.eMinorClean)); //E, G, B
		//WaveDecoder decoder = new WaveDecoder(new FileInputStream(AudioFiles.fmaddb6)); //F, D, C, C#

		// http://electronics.stackexchange.com/questions/12407/what-is-the-relation-between-fft-length-and-frequency-resolution
		// http://zone.ni.com/reference/en-XX/help/372416B-01/svtconcepts/fft_funda/
		// fftbin resolution should be Fs/N
		// we want fft bin resolution ~ 1
		// Fs - sampling rate
		// N - number of samples
		double[] samples = new double[65536];// 1024//65536//32768

		while (decoder.readSamples(samples) > 0) {
			// removeDcComponent(samples);
			// WindowUtils.blackman(samples);
			// WindowUtils.hamming(samples);
			WindowUtils.blackmanHarrisNutall(samples); // creates unreal peaks
			// WindowUtils.bartlettHann(samples);// creates unreal peaks
			// WindowUtils.bartlett(samples);// creates unreal peaks
			// WindowUtils.lanczos(samples);// creates unreal peaks
			// WindowUtils.hann(samples);// creates unreal peaks
			// WindowUtils.gauss(samples);// creates unreal peaks
			DefaultTableXYDataset dataset1 = FFTUtils.performFFTonSamples(samples, true);
			chart.getXYPlot().setDataset(dataset1);
		}
		HarmonicsByIndexFinder.printNotes();
		 /* 
		 * List<Double> spectrum = computePowerSpectrum(fft); 
		 * List<Double> phase = computePhaseSpectrum(fft); 
		 * List<Double> time = calculateTime(spectrum, phase);
		 */
		return dataset;
	}
	
	public static void createParametrizedDataset(String filePath) throws Exception {
		
		Decoder decoder = null;
		WaveDecoder waveDecoder = null;
		if(filePath.endsWith(".mp3")){
			decoder = new MP3Decoder( new FileInputStream(filePath));
		} else if (filePath.endsWith(".wav")){
			waveDecoder = new WaveDecoder( new FileInputStream(filePath));
		}

		double[] samples = new double[8096];
		
		List<Double> powerSpectrum = new ArrayList<>();
		
		Path audioFile = Paths.get(filePath);
		String fileName = audioFile.getFileName().toString();
		int index = fileName.indexOf(".wav");
		if(index > 0){
			fileName = fileName.replace(".wav", ".txt");
		}

		FileWriter writer = new FileWriter(fileName); 
		
		int counter = 0;
		if(filePath.endsWith(".mp3")){
			while (decoder.readSamples(samples) > 0) {
				counter++;
				WindowUtils.blackmanHarrisNutall(samples); // creates unreal peaks
				powerSpectrum = FFTUtils.performParametrizedFFTonSamples(samples, true);		
				writeDataToFile(powerSpectrum, filePath, counter, writer);
				if(counter == 2)
					counter = 0;
			}
		} else if (filePath.endsWith(".wav")){
			while (waveDecoder.readSamples(samples) > 0) {
				counter++;
				WindowUtils.blackmanHarrisNutall(samples); // creates unreal peaks
				powerSpectrum = FFTUtils.performParametrizedFFTonSamples(samples, true);
				writeDataToFile(powerSpectrum, filePath, counter, writer);
				if(counter == 2)
					counter = 0;
			}
		}		
		writer.close();
	}

	private static void writeDataToFile(List<Double> powerSpectrum, String filePath, int counter, FileWriter writer) throws IOException {		
		for (Double myDouble : powerSpectrum) { 
			writer.write(String.valueOf(myDouble) + " ");
		}				
		if(counter == 2)
			writer.write("\n");
	}

	public static void main(String[] args) throws Exception {
		//usersMode();
		developersMode();
	}
	public static void generetaFileWithFrequencies(String filePath) throws Exception{
		createParametrizedDataset(filePath);
	}
	private static void usersMode() throws Exception{
		UserInterface.main(null);
	}
	private static void developersMode() throws Exception{
		Main demo = new Main("Oscilloscope");
		demo.pack();
		RefineryUtilities.centerFrameOnScreen(demo);
		demo.setVisible(true);

		// playSound();
		new Thread(new Runnable() {
			public void run() {
				try {
					createDataset();
				} catch (Exception v) {
					v.printStackTrace();
				}
			}
		}).start();
	}

	public static synchronized void playSound() {
		new Thread(new Runnable() {
			// The wrapper thread is unnecessary, unless it blocks on the
			// Clip finishing;
			public void run() {
				try {
					Clip clip = AudioSystem.getClip();
					AudioInputStream inputStream = AudioSystem.getAudioInputStream(new File(AudioFiles.discoFever));
					clip.open(inputStream);
					clip.start();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}).start();
	}
}
