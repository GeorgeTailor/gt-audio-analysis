package com.gt.user;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.stream.Stream;

import com.badlogic.audio.io.MP3Decoder;
import com.badlogic.audio.io.WaveDecoder;
import com.gt.utils.FFTUtils;
import com.gt.utils.WindowUtils;

public class UserService {
	public Map<String, Double> findNotes(String filePath) throws FileNotFoundException, Exception{
		String extension = filePath.substring(filePath.length()-3, filePath.length());
		switch(extension){
			case "wav":
				return handleWavFile(filePath);
			case "mp3":
				return handleMp3File(filePath);
			default:
				throw new RuntimeException("file extension not supported");
		}
	}
	
	private Map<String, Double> handleWavFile(String filePath) throws FileNotFoundException, Exception{
		File file = new File(filePath);
		if(file.length() > 5000000)
			throw new RuntimeException("File is too large.");
		System.out.println(file.length());
		WaveDecoder decoder = new WaveDecoder(new FileInputStream(filePath));
		double[] samples = new double[65536];
		List<Map<Integer, Map<Integer, Double>>> datasets = new ArrayList<>();
		while (decoder.readSamples(samples) > 0) {
			WindowUtils.blackmanHarrisNutall(samples);
			datasets.add(FFTUtils.performFFTonSamplesForUser(samples));
		}
		UserNotesService userNotesService = new UserNotesService();
		List<String> noteNames = new ArrayList<>();
		userNotesService.findNotes(datasets, noteNames);
		return countNoteProbability(noteNames);
	}
	private Map<String, Double> handleMp3File(String filePath) throws FileNotFoundException, Exception{
		File file = new File(filePath);
		if(file.length() > 5000000)
			throw new RuntimeException("File is too large.");
		MP3Decoder decoder = new MP3Decoder(new FileInputStream(filePath));
		double[] samples = new double[65536];
		List<Map<Integer, Map<Integer, Double>>> datasets = new ArrayList<>();
		while (decoder.readSamples(samples) > 0) {
			WindowUtils.blackmanHarrisNutall(samples);
			datasets.add(FFTUtils.performFFTonSamplesForUser(samples));
		}
		UserNotesService userNotesService = new UserNotesService();
		List<String> noteNames = new ArrayList<>();
		userNotesService.findNotes(datasets, noteNames);
		return countNoteProbability(noteNames);
	}
	private Map<String, Double> countNoteProbability(List<String> noteNames){
		Map<String, Double> noteProbabilities = new HashMap<String, Double>();
		Set<String> set = new HashSet<String>(noteNames);
		int max = 0;
		for(String s: set){
			if(Collections.frequency(noteNames,s) > max)
				max = Collections.frequency(noteNames,s);
		}
		for(String s: set){
			double probability = (double)Collections.frequency(noteNames,s)/(double)max;
			noteProbabilities.put(s, probability);
		}
		return sortByValue(noteProbabilities);
	}
	public static <K, V extends Comparable<? super V>> Map<K, V> sortByValue( Map<K, V> map ){
	    Map<K, V> result = new LinkedHashMap<>();
	    Comparator<Entry<K, V>> byValue = (entry1, entry2) -> entry1.getValue().compareTo(entry2.getValue());
	    Stream<Map.Entry<K, V>> stream = map.entrySet().stream();
	    stream.sorted(byValue.reversed()).forEachOrdered(e -> result.put(e.getKey(), e.getValue()));
	    return result;
	}
}
