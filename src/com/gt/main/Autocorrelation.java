package com.gt.main;

import org.jtransforms.fft.DoubleFFT_1D;

public class Autocorrelation {
	public static void fftAutoCorrelation(double[] x, double[] ac) {
		int n = x.length;
		// Assumes n is even.
		DoubleFFT_1D fft = new DoubleFFT_1D(n);
		fft.realForward(x);
		ac[0] = sqr(x[0]);
		ac[0] = 0; // For statistical convention, zero out the mean
		ac[1] = sqr(x[1]);
		for (int i = 2; i < n; i += 2) {
			ac[i] = Math.sqrt(sqr(x[i]) + sqr(x[i + 1]));
			ac[i + 1] = 0;
		}
		DoubleFFT_1D ifft = new DoubleFFT_1D(n);
		ifft.realInverse(ac, true);
		// For statistical convention, normalize by dividing through with
		// variance
		for (int i = 1; i < n; i++)
			ac[i] /= ac[0];
		ac[0] = 1;
	}
	private static double sqr(double x) {
        return x * x;
    }
	
	public static double autoCorrelation(double[] buffer){
		double result = 0;
		for (int i=0; i<buffer.length; i++){
			double something = ((double)Math.abs(buffer[i]))/(double)buffer.length;
        	result = result + something;
        }
		return result;
	}
}
