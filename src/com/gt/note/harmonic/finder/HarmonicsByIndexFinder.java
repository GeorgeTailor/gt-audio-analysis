package com.gt.note.harmonic.finder;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.gt.utils.LambdomaUtils;
import com.gt.utils.MathUtils;
import com.gt.utils.WindowUtils;

public class HarmonicsByIndexFinder {
	private static Map<Integer, String> foundNotes = new LinkedHashMap<Integer, String>();
	public static void clearMap(){
		foundNotes = new LinkedHashMap<Integer, String>();;
	}
	public static void findIntervalsByIndex(int indexOfMax, List<Double> spectrum, double eps){
		System.out.println("array length " + spectrum.size());
		//WindowUtils.blackmanHarrisNutall(spectrum); // quite good one
		WindowUtils.hamming(spectrum);
		//signal mean
		double mu = MathUtils.countMean(spectrum);
		//standard deviation
		double sigma = MathUtils.countStandardDeviation(mu, spectrum);
		
		List<Double> intervals = LambdomaUtils.getLambdomaIntervals();
		
		double threshold = sigma + mu;//spectrum.get(indexOfMax)/4;
		
		//subtracting mean values to zero out at least some noise
		/*for(int i=0;i<spectrum.size();i++){
			double substr = spectrum.get(i) - mu;
			if(substr < 0)
				spectrum.set(i, 0d);
			else
				spectrum.set(i, spectrum.get(i) - mu);
		}*/
		/*for(int i=HPS.findIndexOfMax(spectrum);i<spectrum.size();i++){
			if(i != 0){
				double nextNote = i*1.05946;
				while(i<nextNote && i < spectrum.size()-1){
					i++;
					spectrum.set(i, 0d);
				}
			}			
		}*/
		List<Double> localMaxima = MathUtils.countLocalMaxima(spectrum);
		double localMaximaThreshold = 0;
		for(int i=0;i<localMaxima.size();i++)
			localMaximaThreshold += localMaxima.get(i)/localMaxima.size();
		for(int i=0;i<spectrum.size();i++){
			//local mean and standard deviation for threshold
			if(i != 0 && i % 256 == 0){
				List<Double> localSpectrum = new ArrayList<>();
				double localMax = 0;
				for(int j=i;j<i+256;j++){
					if(spectrum.get(j) > localMax)
						localMax = spectrum.get(j);
					localSpectrum.add(spectrum.get(j));
				}
				localMaxima.add(localMax);
				double localMu = MathUtils.countMean(localSpectrum);
				double localSigma = MathUtils.countStandardDeviation(localMu, localSpectrum);
				threshold = localSigma;
			}
			//subtract local minima
			/*if(i != 0 && i % 128 == 0){
				List<Double> localSpectrum = new ArrayList<>();
				for(int j=i;j<i+128;j++){
					localSpectrum.add(spectrum.get(j));
					double localMu = countMean(localSpectrum);
					double substr = spectrum.get(j) - localMu;
					if(substr < 0 || spectrum.get(j) - mu < 0)
						spectrum.set(j, 0d);
					else
						spectrum.set(j, spectrum.get(j) - mu);
				}				
			}*/
			//subtract everything except for maximum
			/*if(i != 0 && i % 512 == 0){
				double localMax = 0;
				for(int j=i;j<i+512;j++){
					if(spectrum.get(j) > localMax)
						localMax = spectrum.get(j);
				}
				for(int j=i;j<i+512;j++){
					if(spectrum.get(j) != localMax)
						spectrum.set(j, 0d);
				}					
			}*/
			// needs more dynamic factor rather than simply 60
			if(spectrum.get(i) < threshold || spectrum.get(i) < localMaximaThreshold)// || spectrum.get(i) < 60)
				continue;	
			for(int j=0;j<intervals.size();j++){
				if(localHarmonicCheck(intervals.get(j)) || spectrum.get(i) == 0)
					continue;
				if(MathUtils.almostEqual(intervals.get(j)*indexOfMax, i, eps) || MathUtils.almostEqual((intervals.get(j)*indexOfMax)*(-1), (double)i, eps)){
					populateNoteName(i*44100/spectrum.size(), i, spectrum.get(i), intervals.get(j).toString() + " threshold = " + threshold);
				}
			}
		}
		printNotes();
	}
	public static void printNotes(){
		foundNotes.forEach((k,v) -> System.out.println("index: " + k + " " + v));
	}
	//searching only in range 1 to 2, because 
	//only there could be actual interval notes
	private static boolean localHarmonicCheck(double interval){
		if(interval >= 1)// && interval < 3)
			return false;
		else 
			return true;	 
	}
	public static void populateNoteName(double frequency, int index, double value, String intervalName){
		for(int i=1;i<=9;i++){
			if(MathUtils.almostEqual(frequency, countOctaveByNumber(16.35, i), 7))
				foundNotes.put(index,"C, value="+value+", frequency="+frequency+", interval="+intervalName);
			if(MathUtils.almostEqual(frequency, countOctaveByNumber(17.32,i), 7))
				foundNotes.put(index,"C#, value="+value+", frequency="+frequency+", interval="+intervalName);
			if(MathUtils.almostEqual(frequency, countOctaveByNumber(18.35,i), 7))	
				foundNotes.put(index,"D, value="+value+", frequency="+frequency+", interval="+intervalName);
			if(MathUtils.almostEqual(frequency, countOctaveByNumber(19.45,i), 7))
				foundNotes.put(index,"D#, value="+value+", frequency="+frequency+", interval="+intervalName);	
			if(MathUtils.almostEqual(frequency, countOctaveByNumber(20.6,i), 7))
				foundNotes.put(index,"E, value="+value+", frequency="+frequency+", interval="+intervalName);	
			if(MathUtils.almostEqual(frequency, countOctaveByNumber(21.83,i), 7))
				foundNotes.put(index,"F, value="+value+", frequency="+frequency+", interval="+intervalName);	
			if(MathUtils.almostEqual(frequency, countOctaveByNumber(23.12,i), 7))
				foundNotes.put(index,"F#, value="+value+", frequency="+frequency+", interval="+intervalName);		
			if(MathUtils.almostEqual(frequency, countOctaveByNumber(24.5,i), 7))
				foundNotes.put(index,"G, value="+value+", frequency="+frequency+", interval="+intervalName);	
			if(MathUtils.almostEqual(frequency, countOctaveByNumber(25.96,i), 7))
				foundNotes.put(index,"G#, value="+value+", frequency="+frequency+", interval="+intervalName);	
			if(MathUtils.almostEqual(frequency, countOctaveByNumber(27.5,i), 7))
				foundNotes.put(index,"A, value="+value+", frequency="+frequency+", interval="+intervalName);	
			if(MathUtils.almostEqual(frequency, countOctaveByNumber(29.14,i), 7))
				foundNotes.put(index,"A#, value="+value+", frequency="+frequency+", interval="+intervalName);	
			if(MathUtils.almostEqual(frequency, countOctaveByNumber(30.87,i), 7))
				foundNotes.put(index,"B, value="+value+", frequency="+frequency+", interval="+intervalName);	
		}
	}
	private static double countOctaveByNumber(double frequency, int number){
		for(int i=0;i<number;i++){
			frequency = frequency*2;
		}
		return frequency;
	}
}
