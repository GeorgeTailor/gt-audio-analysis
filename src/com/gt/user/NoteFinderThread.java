package com.gt.user;

import java.io.FileNotFoundException;
import java.util.Map;

public class NoteFinderThread implements Runnable {
	
	private volatile String value;

	public NoteFinderThread(String filePath) throws FileNotFoundException, Exception {
		UserService userService = new UserService();
		Map<String, Double> foundNotes = userService.findNotes(filePath);
		value = "";
		for (Map.Entry<String, Double> entry : foundNotes.entrySet()){
			value += "Note name: " + entry.getKey() + " note probability: " + entry.getValue() + System.lineSeparator();
		}
	}

	public synchronized void run() {
	}
	
	public String getValue() {
        return value;
    }
}