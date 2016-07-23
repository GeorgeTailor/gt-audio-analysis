package com.gt.experimental;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;

import com.badlogic.audio.io.MP3Decoder;
import com.gt.utils.AudioFiles;

public class NNSamplesService {
	//TODO try downsampling
	public static void main(String[] args) throws FileNotFoundException, Exception{
		MP3Decoder decoder = new MP3Decoder( new FileInputStream(AudioFiles.hungarianRhapsody));
		//double[] samples = new double[525312];//for 512 inputs and 1 output
		double[] samples = new double[4097];
		Long counter = 0l;//24193
		
        File samplesFile = new File("hungarianSamplesLargeOutput.txt");
        BufferedWriter writer = new BufferedWriter(new FileWriter(samplesFile));
        System.out.println(samplesFile.getCanonicalPath());
		while (decoder.readSamples(samples) > 0) {
			counter++;
			/*if(counter > 100)
				break;*/
			writeToFile(samples, samplesFile, writer);
		}
		System.out.println("counter " + counter);
		Long numOfSamples = 0l;
		Long samplesArraySize = 500000l;
		numOfSamples = counter*samplesArraySize;
		System.out.println("total samples " + numOfSamples);//12096500000
	}
	
	private static void writeToFile(double[] samples, File samplesFile, BufferedWriter writer){
		//BufferedWriter writer = null;
		try{
			/*int breakCounter = 513;
			for(int i=0;i<samples.length;i++){
				if(i != 0 && i % breakCounter == 0){
					writer.write(String.valueOf(samples[i]));
					i = i-512;
					breakCounter *= 2;
					writer.write(System.getProperty("line.separator"));
				}
                writer.write(String.valueOf(samples[i]));
            }*/
			for(int i=1;i<samples.length;i++){
				double sample = round(samples[i], 3);
				//if(sample <= 0) continue;
				writer.write(String.valueOf(sample) + " ");
				if(i % 1024 == 0){
	                writer.write(String.valueOf(sample));
					writer.write(System.getProperty("line.separator"));
				}
            }
		}
		catch(IOException e){
			e.printStackTrace();
		}
        /*try {
            System.out.println(samplesFile.getCanonicalPath());

            writer = new BufferedWriter(new FileWriter(samplesFile));
            
            for(int i=0;i<samples.length;i++){
                writer.write(String.valueOf(samples[i]));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                writer.close();
            } catch (Exception e) {
            	e.printStackTrace();
            }
        }*/
	}
	public static double round(double value, int places) {
	    if (places < 0) throw new IllegalArgumentException();

	    BigDecimal bd = new BigDecimal(value);
	    bd = bd.setScale(places, RoundingMode.HALF_UP);
	    return bd.doubleValue();
	}
}
