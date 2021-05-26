package com.goolge.lkr;

public class Student {
	int idx;
	String name;
	double middleScore;
	double finalScore;
	String date;
	Student(){
		
	}
	Student(String name, double middleScore, double finalScore, String date){
//		this.idx = idx;
		this.name = name;
		this.middleScore = middleScore;
		this.finalScore = finalScore;
		this.date = date;
	}
	Student(int idx, String name, double middleScore, double finalScore, String date){
		this.idx = idx;
		this.name = name;
		this.middleScore = middleScore;
		this.finalScore = finalScore;
		this.date = date;
	}
	Student(int idx, String name, double middleScore, double finalScore){
		this.idx = idx;
		this.name = name;
		this.middleScore = middleScore;
		this.finalScore = finalScore;
	}
}
