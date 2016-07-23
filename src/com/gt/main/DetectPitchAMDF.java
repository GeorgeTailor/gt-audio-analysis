package com.gt.main;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

import org.jtransforms.fft.DoubleFFT_3D;

import com.badlogic.audio.io.WaveDecoder;

import pl.edu.icm.jlargearrays.DoubleLargeArray;

public class DetectPitchAMDF {
	public static final String doubleBassC = "C:/Asseco/153894__carlos-vaquero__double-bass-c-2-tenuto-non-vibrato.wav";
	public static final String file = "C:/Asseco/68443__pinkyfinger__piano-e.wav";
	public static final String fBass = "C:/Asseco/133024__crescendo__f-bass-note-guitar-string.wav";
	public static final String eGuitar = "C:/Asseco/248177__johnnypanic__e-string-02.wav";
	public static final String ePiano = "C:/Asseco/95330__ramas26__e.wav";
	public static final String e6Guitar= "C:/Asseco/153960__carlos-vaquero__classical-guitar-e-6-plucked-non-vibrato.wav";	
	
	public static final String doubleBassE5= "C:/Asseco/153934__carlos-vaquero__double-bass-e-5-tenuto-non-vibrato.wav";
	//70
	public static final String guitarE5= "C:/Asseco/153981__carlos-vaquero__classical-guitar-e-5-plucked-non-vibrato.wav";
	
	public static final String fluteE5= "C:/Asseco/154254__carlos-vaquero__transverse-flute-e-5-tenuto-vibrato.wav";
	public static final String violinE5= "C:/Asseco/153601__carlos-vaquero__violin-e-5-tenuto-vibrato.wav";
	public static final String guitarFminor= "C:/Asseco/fminGuitar.wav"; // f d#
	
	@SuppressWarnings("unused")
	private static long[] sizes3D = new long[]{16, 32, 64, 128, 256, 512, 1024, 2048, 5, 17, 30, 95, 180, 270, 324, 420};
	
	@SuppressWarnings("unused")
	public static void main(String args[]) throws FileNotFoundException, Exception{
		
		//initializing decoder, reading samples and writing them
		WaveDecoder decoder = new WaveDecoder(new FileInputStream(guitarFminor));
		double[] samples = new double[1024];
		List<Double> samplesList = new ArrayList<>();
		while (decoder.readSamples(samples) > 0) {
			for (int i = 0; i < samples.length; i++) {
				samplesList.add(samples[i]);
			}
		}
		double[] samplesArray = new double[samplesList.size()];
		for(int i=0;i<samplesList.size();i++){
			samples[i] = samplesList.get(i);
		}
		DoubleLargeArray doubleLargeArray = new DoubleLargeArray(samples);
		
		//needs 3 arguments, 
		//x - colums (time/frequencies) 
		//y - rows   (amplitude)
		//z - length of a circle (?) depth of a sound
		//initializing the class
		long maxAmplitude = findMaxAmplitude(samples);
		DoubleFFT_3D doubleFFT3D = new DoubleFFT_3D(samples.length, maxAmplitude, maxAmplitude);
		
		//a - raw sound dataset from eg. WaveDecoder
		//do a Fourier Transform on the sound samples
		//and write them
		doubleFFT3D.complexForward(doubleLargeArray);
		double[] spectrumData = doubleLargeArray.getDoubleData();
		
		//visualize 3d data
		//framework not found 
	}
	
	private static long findMaxAmplitude(double[] samples){
		long max = 0;
		for(int i=0;i<samples.length;i++){
			if(max < samples[i])
				max = (long) samples[i];
		}
		return max;
	}
}
