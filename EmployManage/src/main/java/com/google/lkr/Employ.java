package com.google.lkr;

public class Employ {
	int idx;
	String name;
	String sex;
	String adress;
	String part;
	String date;
	Employ(){
		
	}
	Employ(String name, String sex, String adress, String part){
		this.name = name;
		this.sex = sex;
		this.adress = adress;
		this.part = part;
	}
	Employ(int idx, String name, String sex, String adress, String part){
		this.idx = idx;
		this.name = name;
		this.sex = sex;
		this.adress = adress;
		this.part = part;
	}
	Employ(int idx, String name, String sex, String adress, String part, String date){
		this.idx = idx;
		this.name = name;
		this.sex = sex;
		this.adress = adress;
		this.part = part;
		this.date = date;
	}
	
}
