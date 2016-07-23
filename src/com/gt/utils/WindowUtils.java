package com.gt.utils;

import java.util.List;

public class WindowUtils {
	public static void hamming(double[] samples) {
		for (int i = 0; i < samples.length; i++) {
			samples[i] *= (0.54f - 0.46f * Math.cos(Math.PI*2 * i / (samples.length - 1)));
		}
	}
	public static void hamming(List<Double> spectrum) {
		double[] spectrumArray = new double[spectrum.size()];
		for(int i=0;i<spectrum.size();i++){
			spectrumArray[i] = spectrum.get(i);
		}
		for (int i = 0; i < spectrumArray.length; i++) {
			spectrumArray[i] *= (0.54f - 0.46f * Math.cos(Math.PI*2 * i / (spectrumArray.length - 1)));
		}
		for(int i=0;i<spectrum.size();i++){
			spectrum.set(i, spectrumArray[i]);
		}
	}
	public static void bartlett(List<Double> spectrum){
		double[] spectrumArray = new double[spectrum.size()];
		for(int i=0;i<spectrum.size();i++){
			spectrumArray[i] = spectrum.get(i);
		}
		for (int i = 0; i < spectrumArray.length; i++) {
			spectrumArray[i] *= 2f / (spectrum.size() - 1) * ((spectrum.size() - 1) / 2f - Math.abs(i - (spectrum.size() - 1) / 2f));
		}
		for(int i=0;i<spectrum.size();i++){
			spectrum.set(i, spectrumArray[i]);
		}		
	}
	public static void bartlett(double[] samples){
		for (int i = 0; i < samples.length; i++) {
			samples[i] *= 2f / (samples.length - 1) * ((samples.length - 1) / 2f - Math.abs(i - (samples.length - 1) / 2f));
		}	
	}
	public static void bartlettHann(List<Double> spectrum){
		double[] spectrumArray = new double[spectrum.size()];
		for(int i=0;i<spectrum.size();i++){
			spectrumArray[i] = spectrum.get(i);
		}
		for (int i = 0; i < spectrumArray.length; i++) {
			spectrumArray[i] *= 0.62 - 0.48 * Math.abs(i / (spectrum.size() - 1) - 0.5) - 0.38 * Math.cos(Math.PI *2 * i / (spectrum.size() - 1));
		}
		for(int i=0;i<spectrum.size();i++){
			spectrum.set(i, spectrumArray[i]);
		}
	}
	public static void bartlettHann(double[] samples){
		for (int i = 0; i < samples.length; i++) {
			samples[i] *= 0.62 - 0.48 * Math.abs(i / (samples.length - 1) - 0.5) - 0.38 * Math.cos(Math.PI *2 * i / (samples.length - 1));
		}
	}
	public static void blackmanHarrisNutall(double[] samples){
		float c0 = 0.355768f;
		float c1 = 0.487396f;
		float c2 = 0.144232f; 
		float c3 = 0.012604f;
		for (int i = 0; i < samples.length; i++) {
			float sum = 0;	
			sum += c0 * Math.cos((Math.PI*2 * 0 * i ) / (float) (samples.length)) ;
			sum += c1 * Math.cos((Math.PI*2 * 1 * i ) / (float) (samples.length));
			sum += c2 * Math.cos((Math.PI*2 * 2 * i ) / (float) (samples.length));
			sum += c3 * Math.cos((Math.PI*2 * 3 * i ) / (float) (samples.length));
			samples[i] *= sum;
		}
	}
	public static void blackmanHarrisNutall(List<Double> spectrum){
		float c0 = 0.355768f;
		float c1 = 0.487396f;
		float c2 = 0.144232f; 
		float c3 = 0.012604f;
		
		double[] spectrumArray = new double[spectrum.size()];
		for(int i=0;i<spectrum.size();i++){
			spectrumArray[i] = spectrum.get(i);
		}
		for (int i = 0; i < spectrumArray.length; i++) {
			float sum = 0;	
			sum += c0 * Math.cos((Math.PI*2 * 0 * i ) / (float) (spectrum.size())) ;
			sum += c1 * Math.cos((Math.PI*2 * 1 * i ) / (float) (spectrum.size()));
			sum += c2 * Math.cos((Math.PI*2 * 2 * i ) / (float) (spectrum.size()));
			sum += c3 * Math.cos((Math.PI*2 * 3 * i ) / (float) (spectrum.size()));
			spectrumArray[i] *= sum;
		}
		for(int i=0;i<spectrum.size();i++){
			spectrum.set(i, spectrumArray[i]);
		}
	}
	public static void lanczos(List<Double> spectrum){
		double[] spectrumArray = new double[spectrum.size()];
		for(int i=0;i<spectrum.size();i++){
			spectrumArray[i] = spectrum.get(i);
			float x = 2 * i / (float) (spectrum.size() - 1) - 1;
			spectrumArray[i] *= (float) (Math.sin(Math.PI * x) / (Math.PI * x));
		}
		for(int i=0;i<spectrum.size();i++){
			spectrum.set(i, spectrumArray[i]);
		}
	}
	public static void lanczos(double[] samples){
		for(int i=0;i<samples.length;i++){
			float x = 2 * i / (float) (samples.length - 1) - 1;
			samples[i] *= (float) (Math.sin(Math.PI * x) / (Math.PI * x));
		}
	}
	public static void hann(List<Double> spectrum) {
		double[] spectrumArray = new double[spectrum.size()];
		for(int i=0;i<spectrum.size();i++){
			spectrumArray[i] = spectrum.get(i);
			spectrumArray[i] *= 0.5 * (1 - Math.cos(2 * Math.PI * i / (spectrum.size() - 1)));
		}
		for(int i=0;i<spectrum.size();i++){
			spectrum.set(i, spectrumArray[i]);
		}
	}
	public static void hann(double[] samples) {
		for(int i=0;i<samples.length;i++){
			samples[i] *= 0.5 * (1 - Math.cos(2 * Math.PI * i / (samples.length - 1)));
		}
	}
	public static void gauss(List<Double> spectrum){
		double[] spectrumArray = new double[spectrum.size()];
		double alpha = 0.3; //0.1 <=> 0.5
		for(int i=0;i<spectrum.size();i++){
			spectrumArray[i] = spectrum.get(i);
			spectrumArray[i] *= Math.pow(Math.E,-0.5*Math.pow((i -(spectrum.size() - 1)/(double) 2)/(alpha*(spectrum.size() - 1)/(double)2),(double)2));
		}
		for(int i=0;i<spectrum.size();i++){
			spectrum.set(i, spectrumArray[i]);
		}		
	}
	public static void gauss(double[] samples){
		double alpha = 0.3; //0.1 <=> 0.5
		for(int i=0;i<samples.length;i++){
			samples[i] *= Math.pow(Math.E,-0.5*Math.pow((i -(samples.length - 1)/(double) 2)/(alpha*(samples.length - 1)/(double)2),(double)2));
		}	
	}
	public static void blackman(List<Double> spectrum){
		double[] spectrumArray = new double[spectrum.size()];
		double alpha = 0.16; // standard
		for(int i=0;i<spectrum.size();i++){
			spectrumArray[i] = spectrum.get(i);
			double a0 = (1 - alpha) / 2f;
			double a1 = 0.5f;
			double a2 = alpha / 2f;
			spectrumArray[i] *= a0 - a1 * (float) Math.cos(Math.PI*2 * i / (spectrum.size() - 1)) + a2 * (float) Math.cos(4 * Math.PI * i / (spectrum.size() - 1));
		}
		for(int i=0;i<spectrum.size();i++){
			spectrum.set(i, spectrumArray[i]);
		}
	}
	public static void blackman(double[] spectrum){
		double alpha = 0.16; // standard
		for(int i=0;i<spectrum.length;i++){
			double a0 = (1 - alpha) / 2f;
			double a1 = 0.5f;
			double a2 = alpha / 2f;
			spectrum[i] *= a0 - a1 * (float) Math.cos(Math.PI*2 * i / (spectrum.length - 1)) + a2 * (float) Math.cos(4 * Math.PI * i / (spectrum.length - 1));
		}
	}
	public static void removeDcComponent(double[] samples){
		double xm1 = 0;
		double ym1 = 0;	
		//pole
		int R = (samples.length-1) / samples.length;
		for(int i=0;i<samples.length;i++){
			double outputSample, inputSample = samples[i];
			outputSample = inputSample - xm1 + R * ym1;
			xm1 = inputSample;
			ym1 = outputSample;
			samples[i] = ym1;
		}
		
	}
}
