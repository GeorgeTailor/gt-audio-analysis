package com.gt.utils;

import java.util.ArrayList;
import java.util.List;

public class LambdomaUtils {
	private LambdomaUtils(){}
	
	static {
		lambdomaIntervals = countLambdomaIntervals();
		intervals = countIntervals();
	}
	
	public static List<Double> getLambdomaIntervals(){
		return lambdomaIntervals;
	}
	
	public static List<Double> getIntervals(){
		return intervals;
	}
	
	private static List<Double> lambdomaIntervals;
	private static List<Double> intervals;
	
	public static List<Double> countLambdomaIntervals(){
		List<Double> intervals = new ArrayList<>();
		Double numeral = 1d;
		Double denominator = 1d;
		for(int j=0;j<100;j++){
			for(int i=0;i<100;i++){
				intervals.add(numeral / denominator);
				denominator++;
			}
			denominator = 1d;
			numeral++;
		}		
		return intervals;
	}
	public static List<Double> countIntervals(){
		List<Double> intervals = new ArrayList<>();
		for(int j=0;j<20;j++){
			intervals.add((double) (j+1));
		}		
		return intervals;
	}
}
