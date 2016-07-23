package com.gt.visualization;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.math3.analysis.polynomials.PolynomialSplineFunction;

import com.badlogic.audio.io.WaveDecoder;
import com.gt.utils.AudioFiles;
import com.gt.utils.MathUtils;

public class DataForVisualizationCreator {
	public List<List<double[]>> createDataForVisualization() throws FileNotFoundException, Exception{
		WaveDecoder decoder = new WaveDecoder(new FileInputStream(AudioFiles.pureFsharp));
		int bitRate = decoder.getBitRate();
		System.out.println("bitRate " + bitRate);
		double[] samples = new double[1024];

		List<Double> list = new ArrayList<>();

		while (decoder.readSamples(samples) > 0) {
			for (int i = 0; i < samples.length; i++) {
				list.add((double) samples[i]);
			}
		}
		double[] input = new double[list.size()];
		for (int i = 0; i < input.length; i++)
			input[i] = list.get(i);
		
		List<PolynomialSplineFunction> polynomialSplineFunctions = MathUtils.getPolynomialSplineFunctions(input);
		List<double[]> listOfThirdDimensions = new ArrayList<double[]>();
		double[] thirdDimensionOfSound = new double[input.length];
		for(PolynomialSplineFunction polynomialSplineFunction : polynomialSplineFunctions){
			for(int v=0;v<input.length/8;v++){
				thirdDimensionOfSound[v] = polynomialSplineFunction.value(v);
			}
			listOfThirdDimensions.add(thirdDimensionOfSound);
		}
				
		double[] firstDimensionArray = new double[input.length];
		for(int i=0;i<input.length;i++)
			firstDimensionArray[i] = i;
		List<List<double[]>>threeDimensionsList = new ArrayList<List<double[]>>();
		List<double[]> firstDimensionList = new ArrayList<double[]>();
		firstDimensionList.add(firstDimensionArray);
		List<double[]> secondDimensionList = new ArrayList<double[]>();
		secondDimensionList.add(input);
		
		threeDimensionsList.add(firstDimensionList);
		threeDimensionsList.add(secondDimensionList);
		threeDimensionsList.add(listOfThirdDimensions);
		
		System.out.println(firstDimensionArray.length);
		System.out.println(input.length);
		System.out.println(thirdDimensionOfSound.length);
		return threeDimensionsList;
	}
}
