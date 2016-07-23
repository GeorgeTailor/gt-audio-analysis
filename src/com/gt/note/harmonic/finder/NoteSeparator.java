package com.gt.note.harmonic.finder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.gt.utils.HPS;
import com.gt.utils.LambdomaUtils;
import com.gt.utils.MathUtils;

public class NoteSeparator {
	static int counter = 0;
	public Map<Integer, Map<Integer, Double>> seriesMatrix = new HashMap<>();
	public Map<Integer, Map<Integer, Double>> separateNotes(int counter, int maxIndex, List<Double> spectrum){
		List<Double> intervals = LambdomaUtils.getLambdomaIntervals();		
		Map<Integer, Double> series = new HashMap<>();

		double mean = MathUtils.countMean(spectrum);
		double sigma = MathUtils.countStandardDeviation(mean, spectrum);
		for(int i=0;i<spectrum.size();i++){
			if(spectrum.get(i) == 0 || i > 7000)
				continue;
			if(i % 1024 == 0 && i != 0){
				List<Double> localSpectrum = new ArrayList<>();
				int localCounter = 0;
				for(int j=i;j<1024;j++){
					localSpectrum.set(localCounter, spectrum.get(j));
					localCounter++;
				}
				mean = MathUtils.countMean(localSpectrum);
				sigma = MathUtils.countStandardDeviation(mean, localSpectrum);
			}
			if(spectrum.get(i) < sigma || spectrum.get(i) < 1)
				continue;
			for(int j=0;j<intervals.size();j++){
				if(MathUtils.almostEqual(intervals.get(j)*maxIndex, i, 0.01)){
					series.put(i, spectrum.get(i));
//					if(i-10 > 0 && i+10 < spectrum.size()){
//						for(int v=0;v<10;v++){
//							if(spectrum.get(i) != 0){
//								spectrum.set(i-v, 0d);
//								spectrum.set(i+v, 0d);
//							}
//						}
//					}
					spectrum.set(i, 0d);
				}
			}
		}
		seriesMatrix.put(counter, series);
		NoteSeparator.counter++;
		spectrum.set(maxIndex, 0d);
		if(NoteSeparator.counter == 7)
			return seriesMatrix;
		separateNotes(NoteSeparator.counter, HPS.findIndexOfMax(spectrum), spectrum);
		NoteSeparator.counter = 0;
		return seriesMatrix;
	}
}
