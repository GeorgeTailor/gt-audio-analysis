package com.gt.note.interval.finder;

import java.util.List;

import com.gt.dto.NoteInformation;
import com.gt.utils.HPS;
import com.gt.utils.Intervals;
import com.gt.utils.MathUtils;
import com.gt.utils.NoteInformationBuilder;
import com.gt.utils.NoteUtils;

public class IntervalByIndexFinder implements IntervalFinder {
	
	@SuppressWarnings("unused")
	public List<NoteInformation> findIntervals(double rootIndex, List<Double> spectrum, double eps, List<NoteInformation> notesInfo){
		int indexOfMax = HPS.findIndexOfMax(spectrum);
		System.out.println("Searching for octave at index " + rootIndex);
		//signal mean
		double mu = MathUtils.countMean(spectrum);	
		//standard deviation
		double sigma = MathUtils.countStandardDeviation(mu, spectrum);
		int octave = 0;
		double threshold = spectrum.get(indexOfMax)/10;
		for(int i=(int)rootIndex;i<spectrum.size();i++){
			//double threshold = sigma + mu;
			if(spectrum.get(i) < threshold || spectrum.get(i) < 30)
				continue;
			
			if(MathUtils.almostEqual(Intervals.getInterval(Intervals.minorSecond)*rootIndex, i, eps)){
				notesInfo.add(NoteInformationBuilder.buildNoteInformationObject((double)i*44100/spectrum.size(), i, spectrum.get(i), Intervals.minorSecond.toString(), NoteUtils.populateNoteName((double)i*44100/spectrum.size()), 0));
			}		
			if(MathUtils.almostEqual(Intervals.getInterval(Intervals.majorSecond1)*rootIndex, i, eps)){
				notesInfo.add(NoteInformationBuilder.buildNoteInformationObject((double)i*44100/spectrum.size(), i, spectrum.get(i), Intervals.majorSecond1.toString(), NoteUtils.populateNoteName((double)i*44100/spectrum.size()), 0));
			}
			if(MathUtils.almostEqual(Intervals.getInterval(Intervals.minorThird)*rootIndex, i, eps)){
				notesInfo.add(NoteInformationBuilder.buildNoteInformationObject((double)i*44100/spectrum.size(), i, spectrum.get(i), Intervals.minorThird.toString(), NoteUtils.populateNoteName((double)i*44100/spectrum.size()), 0));
			}
			if(MathUtils.almostEqual(Intervals.getInterval(Intervals.majorThird)*rootIndex, i, eps)){
				notesInfo.add(NoteInformationBuilder.buildNoteInformationObject((double)i*44100/spectrum.size(), i, spectrum.get(i), Intervals.majorThird.toString(), NoteUtils.populateNoteName((double)i*44100/spectrum.size()), 0));
			}
			if(MathUtils.almostEqual(Intervals.getInterval(Intervals.perfectFourth)*rootIndex, i, eps)){
				notesInfo.add(NoteInformationBuilder.buildNoteInformationObject((double)i*44100/spectrum.size(), i, spectrum.get(i), Intervals.perfectFourth.toString(), NoteUtils.populateNoteName((double)i*44100/spectrum.size()), 0));
			}
			if(MathUtils.almostEqual(Intervals.getInterval(Intervals.augmentedFourth)*rootIndex, i, eps)){
				notesInfo.add(NoteInformationBuilder.buildNoteInformationObject((double)i*44100/spectrum.size(), i, spectrum.get(i), Intervals.augmentedFourth.toString(), NoteUtils.populateNoteName((double)i*44100/spectrum.size()), 0));
			}
			if(MathUtils.almostEqual(Intervals.getInterval(Intervals.diminishedFifth)*rootIndex, i, eps)){
				notesInfo.add(NoteInformationBuilder.buildNoteInformationObject((double)i*44100/spectrum.size(), i, spectrum.get(i), Intervals.diminishedFifth.toString(), NoteUtils.populateNoteName((double)i*44100/spectrum.size()), 0));
			}
			if(MathUtils.almostEqual(Intervals.getInterval(Intervals.perfectFifthInterval)*rootIndex, i, eps)){
				notesInfo.add(NoteInformationBuilder.buildNoteInformationObject((double)i*44100/spectrum.size(), i, spectrum.get(i), Intervals.perfectFifthInterval.toString(), NoteUtils.populateNoteName((double)i*44100/spectrum.size()), 0));
			}
			if(MathUtils.almostEqual(Intervals.getInterval(Intervals.minorSixth)*rootIndex, i, eps)){
				notesInfo.add(NoteInformationBuilder.buildNoteInformationObject((double)i*44100/spectrum.size(), i, spectrum.get(i), Intervals.minorSixth.toString(), NoteUtils.populateNoteName((double)i*44100/spectrum.size()), 0));
			}
			if(MathUtils.almostEqual(Intervals.getInterval(Intervals.majorSixth)*rootIndex, i, eps)){
				notesInfo.add(NoteInformationBuilder.buildNoteInformationObject((double)i*44100/spectrum.size(), i, spectrum.get(i), Intervals.majorSixth.toString(), NoteUtils.populateNoteName((double)i*44100/spectrum.size()), 0));
			}
			if(MathUtils.almostEqual(Intervals.getInterval(Intervals.minorSeventh1)*rootIndex, i, eps)
					|| MathUtils.almostEqual(Intervals.getInterval(Intervals.minorSeventh2)*rootIndex, i, eps)){
				notesInfo.add(NoteInformationBuilder.buildNoteInformationObject((double)i*44100/spectrum.size(), i, spectrum.get(i), Intervals.minorSeventh1.toString(), NoteUtils.populateNoteName((double)i*44100/spectrum.size()), 0));
			}
			if(MathUtils.almostEqual(Intervals.getInterval(Intervals.majorSeventh)*rootIndex, i, eps)){
				notesInfo.add(NoteInformationBuilder.buildNoteInformationObject((double)i*44100/spectrum.size(), i, spectrum.get(i), Intervals.majorSeventh.toString(), NoteUtils.populateNoteName((double)i*44100/spectrum.size()), 0));
			}
			if(MathUtils.almostEqual(Intervals.getInterval(Intervals.perfectOctave)*rootIndex, i, eps)){
				notesInfo.add(NoteInformationBuilder.buildNoteInformationObject((double)i*44100/spectrum.size(), i, spectrum.get(i), Intervals.perfectOctave.toString(), NoteUtils.populateNoteName((double)i*44100/spectrum.size()), 0));
				octave = i;
				break;
			}
		}
		if(octave > 0)
			findIntervals(octave, spectrum, eps, notesInfo);
		return notesInfo;
	}
}
