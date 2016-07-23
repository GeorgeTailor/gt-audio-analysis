package com.gt.note.interval.finder;

import java.util.List;

import com.gt.dto.NoteInformation;

public interface IntervalFinder {
	public List<NoteInformation> findIntervals(double rootValue, List<Double> spectrum, double eps, List<NoteInformation> notesInfo);
}
