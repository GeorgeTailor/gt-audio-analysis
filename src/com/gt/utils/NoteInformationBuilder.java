package com.gt.utils;

import com.gt.dto.NoteInformation;

public class NoteInformationBuilder {
	private NoteInformationBuilder(){}
	
	public static NoteInformation buildNoteInformationObject(Double frequency, int index, Double amplitude, String intervalName, String noteName, int seriesNumber){
		NoteInformation.Builder builder = new NoteInformation.Builder(frequency, index);
		builder.withNoteName(noteName);
		builder.withSeriesNumber(seriesNumber);
		builder.withAmplitude(amplitude);
		builder.withInterval(intervalName);
		NoteInformation noteInformation = builder.build();
		return noteInformation;
	}
}
