package com.gt.utils;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.math3.analysis.polynomials.PolynomialFunction;
import org.apache.commons.math3.analysis.polynomials.PolynomialSplineFunction;

public class MathUtils {
	private MathUtils(){}
	
	public static boolean almostEqual(double a, double b, double eps){
	    return Math.abs(a-b)<eps;
	}
	public static double sqr(double a){
		return a*a;
	}
	
	public static double countMean(List<Double> spectrum){
		double mu = 0;
		for(int i=0;i<spectrum.size();i++){
			mu += spectrum.get(i)/spectrum.size();
		}
		return mu;
	}
	public static double countStandardDeviation(double mu, List<Double> spectrum){
		double sum = 0;
		for(int i=0;i<spectrum.size()-1;i++){
			sum += MathUtils.sqr(spectrum.get(i)-mu);
		}
		double sigma = Math.sqrt(sum/(spectrum.size()-1));
		return sigma;
	}
	
	public static List<PolynomialSplineFunction> getPolynomialSplineFunctions(double[] input){
		List<PolynomialSplineFunction> splineFunctions = new ArrayList<PolynomialSplineFunction>();
		for(int k=0;k<8;k++){
			double[] firstArgumentForInterpolation = new double[input.length/8];
			double[] secondArgumentForInterpolation = new double[input.length/8];
			for(int i=0;i<input.length/8;i++){
				firstArgumentForInterpolation[i] = i;
				secondArgumentForInterpolation[i] = input[i+k];
			}
			// Number of intervals.  The number of data points is n + 1.
		    int n = input.length/8 - 1;
		    // Slope of the lines between the datapoints.
		    final double m[] = new double[n];
		    for (int i = 0; i < n; i++) {
		        m[i] = (secondArgumentForInterpolation[i + 1] - secondArgumentForInterpolation[i]) / (firstArgumentForInterpolation[i + 1] - firstArgumentForInterpolation[i]);
		    }
		    PolynomialFunction polynomials[] = new PolynomialFunction[n];
		    final double coefficients[] = new double[2];
		    for (int i = 0; i < n; i++) {
		        coefficients[0] = secondArgumentForInterpolation[i];
		        coefficients[1] = m[i];
		        polynomials[i] = new PolynomialFunction(coefficients);
		    }
		    splineFunctions.add(new PolynomialSplineFunction(firstArgumentForInterpolation, polynomials));
		}		
	    return splineFunctions;
	}
	public static List<Double> countLocalMaxima(List<Double> spectrum){
		List<Double> localMaxima = new ArrayList<>();
		for(int i=0;i<spectrum.size();i++){
			if(i != 0 && i % 1024 == 0){
				double localMax = 0;
				for(int j=i;j<i+1024;j++){
					if(spectrum.get(j) > localMax)
						localMax = spectrum.get(j);
				}
				localMaxima.add(localMax);
			}
		}
		return localMaxima;
	}
}
