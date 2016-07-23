package com.gt.utils;

import java.util.List;
import java.util.Map;

public class HPS {
	/* Harmonic Product spectrum 
	 * @param fftData Discrete Fourier transform of data
	 * @param N Number of times we downsample the spectrum to get HPS
	 */
	private HPS(){}
	
	public static int hps( List<Double> fftSpectrum, int N, List<Double> hps)
	{
	    // Make a new array to store HPS
	    //double hps[] = new double[fftSpectrum.size()];
	    for(int i=0;i<fftSpectrum.size();i++){
	    		hps.add(fftSpectrum.get(i));
	    }

	    // Perform HPS:
	    // Go through each downsampling factor
	    for (int downsamplingFactor = 1; downsamplingFactor <= N; downsamplingFactor++)
	    {
	        // Go through samples of the downsampled signal and compute HPS at this iteration
	        for(int idx = 0; idx < fftSpectrum.size()/downsamplingFactor; idx++)
	        {
	        	double tempHps = hps.get(idx);
	        	tempHps *= fftSpectrum.get(idx * downsamplingFactor);
	            hps.set(idx, tempHps);
	        }
	    }

	    return findIndexOfMax(hps);
	}
	
	public static int findIndexOfMax(List<Double> array){
		int indexOfMax = 0;
		double max = Double.MIN_VALUE;
		for(int i=0;i<array.size();i++){
			if(i<10)
				continue;
			if(array.get(i) > max){
				max = array.get(i);
				indexOfMax = i;
			}
		}
		return indexOfMax;
	}
	public static int findIndexOfMax(Map<Integer, Double> map){
		int indexOfMax = 0;
		double max = Double.MIN_VALUE;
		for (Map.Entry<Integer, Double> entry : map.entrySet()) {
			if(entry.getValue() > max){
				max = entry.getValue();
				indexOfMax = entry.getKey();
			}
		}
		return indexOfMax;
	}
	public static double findValueOfMax(List<Double> array){
		double max = Double.MIN_VALUE;
		for(int i=1;i<array.size();i++){
			if(i<10)
				continue;
			if(array.get(i) > max){
				max = array.get(i);
				if(i==0)
					max = 1;
			}
		}
		return max;
	}
}
