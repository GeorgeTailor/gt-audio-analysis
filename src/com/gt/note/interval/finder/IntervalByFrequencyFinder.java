package com.gt.note.interval.finder;

import java.util.List;

import com.gt.dto.NoteInformation;
import com.gt.utils.Intervals;
import com.gt.utils.MathUtils;
import com.gt.utils.NoteInformationBuilder;
import com.gt.utils.NoteUtils;

public class IntervalByFrequencyFinder implements IntervalFinder {
	// finding intervals
	// @param spectrum containing all frequencies
	// @param maximum frequency from spectrum
	// @param epsilon, approximation rate
	@SuppressWarnings("unused")
	public List<NoteInformation> findIntervals(double rootFrequency, List<Double> spectrum, double eps,
			List<NoteInformation> notesInfo) {
		// rootFrequency = performFundamentalFreqCheck(spectrum, rootFrequency,
		// eps);
		/*
		 * if(!almostEqual(rootFrequency, hpsFrequncy, eps)) rootFrequency =
		 * hpsFrequncy;
		 */
		System.out.println("Searching for octave " + rootFrequency);
		double octave = 0;

		int firstPeakIndex = (int) rootFrequency * spectrum.size() / 44100;
		for (int i = firstPeakIndex; i < spectrum.size(); i++) {
			for (int j = i; j < i + 100; j++) {
				if (i + 100 < spectrum.size()) {
					if (spectrum.get(j) > spectrum.get(i)) {
						i = j;
					}
				}
			}
			double frequency = i * 44100 / spectrum.size();
			double previousFrequency = (i - 1) * 44100 / spectrum.size();
			if (i != 0 && MathUtils.almostEqual(frequency, previousFrequency, eps / 20))
				continue;
			// the larger the frequency in spectrum the larger should be
			// threshold
			double threshold = i > 2000 ? i / 50 : 150;
			if (spectrum.get(i) < 150)
				continue;
			if (MathUtils.almostEqual(Intervals.getInterval(Intervals.minorSecond) * rootFrequency, frequency, eps)) {
				notesInfo.add(NoteInformationBuilder.buildNoteInformationObject(frequency, i, spectrum.get(i), Intervals.minorSecond.toString(), NoteUtils.populateNoteName((double)i*44100/spectrum.size()), 0));
			}
			if (MathUtils.almostEqual(Intervals.getInterval(Intervals.thirteenth) * rootFrequency, frequency, eps)) {
				notesInfo.add(NoteInformationBuilder.buildNoteInformationObject(frequency, i, spectrum.get(i), Intervals.thirteenth.toString(), NoteUtils.populateNoteName((double)i*44100/spectrum.size()), 0));
			}
			if (MathUtils.almostEqual(Intervals.getInterval(Intervals.majorSecond1) * rootFrequency, frequency, eps)
					|| MathUtils.almostEqual(Intervals.getInterval(Intervals.majorSecond2) * rootFrequency, frequency,
							eps)) {
				notesInfo.add(NoteInformationBuilder.buildNoteInformationObject(frequency, i, spectrum.get(i), Intervals.majorSecond1.toString(), NoteUtils.populateNoteName((double)i*44100/spectrum.size()), 0));
			}
			if (MathUtils.almostEqual(Intervals.getInterval(Intervals.minorThird) * rootFrequency, frequency, eps)) {
				notesInfo.add(NoteInformationBuilder.buildNoteInformationObject(frequency, i, spectrum.get(i), Intervals.minorThird.toString(), NoteUtils.populateNoteName((double)i*44100/spectrum.size()), 0));
			}
			if (MathUtils.almostEqual(Intervals.getInterval(Intervals.majorThird) * rootFrequency, frequency, eps)) {
				notesInfo.add(NoteInformationBuilder.buildNoteInformationObject(frequency, i, spectrum.get(i), Intervals.majorThird.toString(), NoteUtils.populateNoteName((double)i*44100/spectrum.size()), 0));
			}
			if (MathUtils.almostEqual(Intervals.getInterval(Intervals.perfectFourth) * rootFrequency, frequency, eps)) {
				notesInfo.add(NoteInformationBuilder.buildNoteInformationObject(frequency, i, spectrum.get(i), Intervals.perfectFourth.toString(), NoteUtils.populateNoteName((double)i*44100/spectrum.size()), 0));
			}
			if (MathUtils.almostEqual(Intervals.getInterval(Intervals.augmentedFourth) * rootFrequency, frequency,
					eps)) {
				notesInfo.add(NoteInformationBuilder.buildNoteInformationObject(frequency, i, spectrum.get(i), Intervals.augmentedFourth.toString(), NoteUtils.populateNoteName((double)i*44100/spectrum.size()), 0));
			}
			if (MathUtils.almostEqual(Intervals.getInterval(Intervals.diminishedFifth) * rootFrequency, frequency,
					eps)) {
				notesInfo.add(NoteInformationBuilder.buildNoteInformationObject(frequency, i, spectrum.get(i), Intervals.diminishedFifth.toString(), NoteUtils.populateNoteName((double)i*44100/spectrum.size()), 0));
			}
			if (MathUtils.almostEqual(Intervals.getInterval(Intervals.perfectFifthInterval) * rootFrequency, frequency,
					eps)) {
				notesInfo.add(NoteInformationBuilder.buildNoteInformationObject(frequency, i, spectrum.get(i), Intervals.perfectFifthInterval.toString(), NoteUtils.populateNoteName((double)i*44100/spectrum.size()), 0));
			}
			if (MathUtils.almostEqual(Intervals.getInterval(Intervals.minorSixth) * rootFrequency, frequency, eps)) {
				notesInfo.add(NoteInformationBuilder.buildNoteInformationObject(frequency, i, spectrum.get(i), Intervals.minorSixth.toString(), NoteUtils.populateNoteName((double)i*44100/spectrum.size()), 0));
			}
			if (MathUtils.almostEqual(Intervals.getInterval(Intervals.majorSixth) * rootFrequency, frequency, eps)) {
				notesInfo.add(NoteInformationBuilder.buildNoteInformationObject(frequency, i, spectrum.get(i), Intervals.majorSixth.toString(), NoteUtils.populateNoteName((double)i*44100/spectrum.size()), 0));
			}
			if (MathUtils.almostEqual(Intervals.getInterval(Intervals.minorSeventh1) * rootFrequency, frequency, eps)
					|| MathUtils.almostEqual(Intervals.getInterval(Intervals.minorSeventh2) * rootFrequency, frequency,
							eps)) {
				notesInfo.add(NoteInformationBuilder.buildNoteInformationObject(frequency, i, spectrum.get(i), Intervals.minorSeventh1.toString(), NoteUtils.populateNoteName((double)i*44100/spectrum.size()), 0));
			}
			if (MathUtils.almostEqual(Intervals.getInterval(Intervals.majorSeventh) * rootFrequency, frequency, eps)) {
				notesInfo.add(NoteInformationBuilder.buildNoteInformationObject(frequency, i, spectrum.get(i), Intervals.majorSeventh.toString(), NoteUtils.populateNoteName((double)i*44100/spectrum.size()), 0));
			}
			if (MathUtils.almostEqual(Intervals.getInterval(Intervals.perfectOctave) * rootFrequency, frequency, eps)) {
				notesInfo.add(NoteInformationBuilder.buildNoteInformationObject(frequency, i, spectrum.get(i), Intervals.perfectOctave.toString(), NoteUtils.populateNoteName((double)i*44100/spectrum.size()), 0));
				octave = frequency;
			}
		}
		if (octave > 0)
			findIntervals(octave, spectrum, eps, notesInfo);
		// nameFoundNotes(foundFrequencies);
		return notesInfo;
	}
}
