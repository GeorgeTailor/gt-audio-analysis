package com.gt.dto;

public class NoteInformation {
	private int index;
	private String name;
	private Double amplitude;
	private Double frequency;
	private int seriesNumber;
	private String interval;

	public static class Builder {
		// obligatory parameters
		private final Double frequency;
		private final int index;
		// optional parameters - initialize with default values
		private Double amplitude = 0d;
		private String name = "";
		private int seriesNumber = 0;
		private String interval = "";

		public Builder(Double frequency, int index) {
			this.frequency = frequency;
			this.index = index;
		}

		public Builder withAmplitude(Double val) {
			amplitude = val;
			return this;
		}

		public Builder withNoteName(String noteName) {
			name = noteName;
			return this;
		}

		public Builder withSeriesNumber(int val) {
			seriesNumber = val;
			return this;
		}
		
		public Builder withInterval(String val) {
			interval = val;
			return this;
		}

		public NoteInformation build() {
			return new NoteInformation(this);
		}
	}

	private NoteInformation(Builder builder) {
		frequency = builder.frequency;
		index = builder.index;
		amplitude = builder.amplitude;
		name = builder.name;
		seriesNumber = builder.seriesNumber;
		interval = builder.interval;
	  }

	public int getIndex() {
		return index;
	}

	public String getName() {
		return name;
	}

	public Double getAmplitude() {
		return amplitude;
	}

	public Double getFrequency() {
		return frequency;
	}

	public int getSeriesNumber() {
		return seriesNumber;
	}
	
	public String getInterval() {
		return interval;
	}

	public void printNoteInformation(){
		System.out.println();
	}
}
