package com.gt.utils;

import java.util.List;

public class NoteUtils {
	private NoteUtils(){}
	public static double countOctaveByNumber(double frequency, int number){
		for(int i=0;i<number;i++){
			frequency = frequency*2;
		}
		return frequency;
	}
	public static double performFundamentalFreqCheck(List<Double> spectrum, double rootFrequency, double eps){
		for(int i=0;i<spectrum.size();i++){
			double frequency = i*44100/spectrum.size();
			if(spectrum.get(i)< 10)
				continue;
			if(MathUtils.almostEqual(Math.pow(Intervals.getInterval(Intervals.perfectOctave), -1)*rootFrequency, frequency, eps)){
				return performFundamentalFreqCheck(spectrum, frequency, eps);
			}
		}
		return rootFrequency;
	}
	public static String populateNoteName(double frequency){
		for(int i=1;i<=9;i++){
			if(MathUtils.almostEqual(frequency, NoteUtils.countOctaveByNumber(16.35, i), 5))
				return "C";
			if(MathUtils.almostEqual(frequency, NoteUtils.countOctaveByNumber(17.32,i), 5))
				return "C#";
			if(MathUtils.almostEqual(frequency, NoteUtils.countOctaveByNumber(18.35,i), 5))	
				return "D";
			if(MathUtils.almostEqual(frequency, NoteUtils.countOctaveByNumber(19.45,i), 5))
				return "D#";
			if(MathUtils.almostEqual(frequency, NoteUtils.countOctaveByNumber(20.6,i), 5))
				return "E";
			if(MathUtils.almostEqual(frequency, NoteUtils.countOctaveByNumber(21.83,i), 5))
				return "F";
			if(MathUtils.almostEqual(frequency, NoteUtils.countOctaveByNumber(23.12,i), 5))
				return "F#";		
			if(MathUtils.almostEqual(frequency, NoteUtils.countOctaveByNumber(24.5,i), 5))
				return "G";
			if(MathUtils.almostEqual(frequency, NoteUtils.countOctaveByNumber(25.96,i), 5))
				return "G#";
			if(MathUtils.almostEqual(frequency, NoteUtils.countOctaveByNumber(27.5,i), 5))
				return "A";
			if(MathUtils.almostEqual(frequency, NoteUtils.countOctaveByNumber(29.14,i), 5))
				return "A#";
			if(MathUtils.almostEqual(frequency, NoteUtils.countOctaveByNumber(30.87,i), 5))
				return "B";
		}
		return "undefined";
	}
}
