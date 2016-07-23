package com.gt.utils;

public enum Intervals {
	perfectFifthInterval(3, 2),
	minorSecond (16, 15),
	thirteenth (7,6),
	majorSecond1 (9,8),
	majorSecond2 (10,9),
	minorThird (6,5),
	majorThird (5,4),
	perfectFourth (4,3),
	augmentedFourth (45,32),
	diminishedFifth (64,45),
	minorSixth (8,5),
	majorSixth (5,3),
	minorSeventh1 (16,9),
	minorSeventh2 (9,5),
	majorSeventh (15,8),
	perfectOctave(1, 2);
	
	private String name;
	private final double numeral;
    private final double denominator;
    private Intervals(double numeral, double denominator) {
        this.numeral = numeral;
        this.denominator = denominator;
    }
    
    public static double getInterval(Intervals interval){
    	return interval.numeral/interval.denominator;
    }
    public String toString() {
        return this.name;
     }
}
