package com.google.lkr;

public class Student {
	int idx;
	String name;
	double middleScore;
	double finalScore;
	String created;
	
	Student(){
		
	}
	Student(String name, double midScore, double finScore, String date){
		this.name = name;
		this.middleScore = midScore;
		this.finalScore = finScore;
		this.created = date;
	}
	Student(int idx, String name, double midScore, double finScore, String date){
		this.idx = idx; 
		this.name = name;
		this.middleScore = midScore;
		this.finalScore = finScore;
		this.created = date;
	}
	Student(int idx, String name, double midScore, double finScore){
		this.idx = idx; 
		this.name = name;
		this.middleScore = midScore;
		this.finalScore = finScore;
	}
}
