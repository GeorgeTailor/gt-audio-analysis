package com.gt.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.jfree.data.xy.DefaultTableXYDataset;
import org.jtransforms.fft.DoubleFFT_1D;

import com.gt.note.harmonic.finder.NoteSeparator;

public class FFTUtils {
	private FFTUtils(){}
	public static List<Double> computePhaseSpectrum(double[] fft){
		List<Double> phase = new ArrayList<>();
		for (int i = 0; i < fft.length/2; i++) {
			//double fftPhase = Math.atan2(fft[i + 1], fft[i]);
			double fftPhase = Math.atan(fft[i + 1] / fft[i]);
			phase.add(fftPhase);
		}
		return phase;
	}
	@SuppressWarnings("unused")
	private static List<Double> calculateTime(List<Double> spectrum, List<Double> phase){
		List<Double> time = new ArrayList<Double>();
		for(int i=0;i<spectrum.size();i++){
			// power spectrum part
			double omega = 2*Math.PI*i;
			//double omega = 360*i;
			//phase spectrum part
			double phi = phase.get(i);
			//time
			if(omega == 0){
				time.add(0d);
			} else {
				double timeInstance = phi / omega;
				time.add(timeInstance);
			}		
		}
		return time;
	}
	// getting power spectrum data
	public static List<Double> computePowerSpectrum(double[] fft){
		List<Double> spectrum = new ArrayList<>();
		for (int i = 0; i < fft.length /2; i++) {
			
			double fftFrequency = Math.sqrt(fft[i] * fft[i] + fft[i + 1] * fft[i + 1]);
			spectrum.add(fftFrequency);
		}
		
		return spectrum;
	}
	public static int calculateLengthOfFftArray(double[] inputSamples){
		int samplesLength = inputSamples.length;
		for(int i=0;i<inputSamples.length;i++){
			if((samplesLength > 0) && ((samplesLength & (samplesLength - 1)) != 0))
				samplesLength--;
		}
		return samplesLength;
	}
	@SuppressWarnings("unused")
	private double calculateOccurenceOfRootNote(List<Double> spectrum, int index){
		double time = 0;
		for(int i=0;i<index;i++){
			double frequency = spectrum.get(i)*44100/spectrum.size();
			time+=1/frequency;
		}
		return time;
	}
	@SuppressWarnings("unused")
	private double calculateLengthOfFile(List<Double> timeArray, int size){
		double time = 0;
		for(int i=0;i<size;i++){
			double timeInstance = timeArray.get(i);
			time+=Math.abs(timeInstance);
		}
		return time;
	}
	@SuppressWarnings("unused")
	private static void checkIntervals(List<Double> spectrum, List<Double> time){
		//hamming(spectrum); //quite good
		//hann(spectrum); // shifts spectrum to right side
		//gauss(spectrum); // lowers the amplitude like crazy, amplifies left side of densiest part
		//blackman(spectrum); // same as gauss, but amplifies middle part of densiest part
		//lanczos(spectrum); // similar to hamming
		//bartlett(spectrum); // same as lanczos
		//blackmanHarrisNutall(spectrum); // clears out harmonics
		//bartlettHann(spectrum); // similar blackman
		System.out.println("array length " + spectrum.size());
		List<Double> hps = new ArrayList<Double>();
		int hpsMaxIndex = HPS.hps(spectrum, 8, hps);//8
		double hpsFrequncy = hpsMaxIndex*44100/spectrum.size();
		System.out.println("hpsMaxIndex " + hpsMaxIndex);
		System.out.println("hpsFreq " + hpsFrequncy);
		
		/*double occurenceOfRootNote = calculateOccurenceOfRootNote(spectrum, hpsMaxIndex);
		System.out.println("root note occured at " + occurenceOfRootNote);
		double lengthOfFile = calculateLengthOfFile(time, spectrum.size());
		System.out.println("length of file " + lengthOfFile);*/
		double max = HPS.findValueOfMax(spectrum);
		int indexOfMax = HPS.findIndexOfMax(spectrum);
		double frequencyOfMax = indexOfMax*44100/spectrum.size();
		System.out.println("frequency of max " + frequencyOfMax);
		System.out.println("index of max " + indexOfMax);
		double eps = 30;
		
		//GuitarChordFinder.findIntervalsByValue(firstPeakValue, spectrum, eps);
		//GuitarChordFinder.findIntervalsByFrequencies(spectrum, hpsFrequncy, eps, hpsFrequncy);
		//GuitarChordFinder.findIntervalsByIndex(hpsMaxIndex, spectrum, eps);	
	}
	public static void deleteFrequenciesFromSpectrum(Integer index, Double amplitude, List<Double> spectrum){
		spectrum.set(index, 0d);
	}
	public static List<Double> performParametrizedFFTonSamples(double[] input, boolean fullScan) {

		DoubleFFT_1D fftDo = new DoubleFFT_1D(input.length);
		double[] fft = new double[input.length * 2];
		System.arraycopy(input, 0, fft, 0, input.length);
		fftDo.realForwardFull(fft);

		List<Double> spectrum = FFTUtils.computePowerSpectrum(fft);

		return spectrum;
	}
	public static DefaultTableXYDataset performFFTonSamples(double[] input, boolean fullScan) {
		DefaultTableXYDataset dataset = new DefaultTableXYDataset();

		DoubleFFT_1D fftDo = new DoubleFFT_1D(input.length);
		double[] fft = new double[input.length * 2];
		// int fftLength = calculateLengthOfFftArray(input);
		System.arraycopy(input, 0, fft, 0, input.length);
		// fftDo.realForward(fft);
		fftDo.realForwardFull(fft);

		List<Double> spectrum = FFTUtils.computePowerSpectrum(fft);
		// List<Double> phase = computePhaseSpectrum(fft);
		// List<Double> time = calculateTime(spectrum, phase);

		NoteSeparator noteSeparator = new NoteSeparator();
		Map<Integer, Map<Integer, Double>> separatedFFT = 
				noteSeparator.separateNotes(0, HPS.findIndexOfMax(spectrum), spectrum);

		if(!fullScan){
			for (Map.Entry<Integer, Map<Integer, Double>> entry : separatedFFT.entrySet()) {
				if (entry.getValue() == null) {
					System.out.println("Found null " + entry.getKey() + " with value " + entry.getValue());
				} else {
					dataset.addSeries(ChartUtils.prepareXYSeries(entry.getValue()));
				}
			}
		} else {
			dataset.addSeries(ChartUtils.prepareFullXYSeries(spectrum));
		}
		System.out.println();
		return dataset;
	}
	public static Map<Integer, Map<Integer, Double>> performFFTonSamplesForUser(double[] input) {

		DoubleFFT_1D fftDo = new DoubleFFT_1D(input.length);
		double[] fft = new double[input.length * 2];
		System.arraycopy(input, 0, fft, 0, input.length);
		fftDo.realForwardFull(fft);

		List<Double> spectrum = FFTUtils.computePowerSpectrum(fft);

		NoteSeparator noteSeparator = new NoteSeparator();
		Map<Integer, Map<Integer, Double>> separatedFFT = 
				noteSeparator.separateNotes(0, HPS.findIndexOfMax(spectrum), spectrum);

		return separatedFFT;
	}
}
