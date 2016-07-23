package com.gt.user;

import java.util.List;
import java.util.Map;

import com.gt.utils.HPS;
import com.gt.utils.NoteUtils;

public class UserNotesService {
	public List<String> findNotes(List<Map<Integer, Map<Integer, Double>>> datasets, List<String> noteNames){
		for(Map<Integer, Map<Integer, Double>> dataset : datasets){
			for (Map.Entry<Integer, Map<Integer, Double>> entry : dataset.entrySet()) {
				handleEpisode(entry.getValue(), noteNames);
			}
		}
		return noteNames;
	}
	private List<String> handleEpisode(Map<Integer, Double> episode, List<String> noteNames){
		int maxIndex = HPS.findIndexOfMax(episode);
		double frequency = maxIndex*44100/65536;
		String noteName = NoteUtils.populateNoteName(frequency);
		if(!noteName.equals("undefined"))
			noteNames.add(noteName);
		return noteNames;
	}
}
