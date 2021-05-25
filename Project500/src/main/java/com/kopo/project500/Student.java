package com.kopo.project500;

public class Student {
	int idx;
	String name;
	double middleScore;
	double finalScore;
	String created;
	
	Student() {
		
	}
	
	Student(String name, double middleScore, double finalScore, String created) {
		this.name = name;
		this.middleScore = middleScore;
		this.finalScore = finalScore;
		this.created = created;
	}
	
	Student(int idx, String name, double middleScore, double finalScore, String created) {
		this.idx = idx;
		this.name = name;
		this.middleScore = middleScore;
		this.finalScore = finalScore;
		this.created = created;
	}
}
