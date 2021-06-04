package com.google.lkr;

public class People {
	int idx;
	String id;
	String pwd;
	String name;
	String address;
	String birthday;
	String created;
	String updated;
	
	People(){
		
	}
	People(int idx, String id, String pwd, String name, String address, String birthday, String created, String updated){
		this.idx = idx;
		this.id = id;
		this.pwd = pwd;
		this.name = name;
		this.address = address;
		this.birthday = birthday;
		this.created = created;
		this.updated = updated;
	}
	People(String id, String pwd, String name, String address, String birthday, String created, String updated){
		this.id = id;
		this.pwd = pwd;
		this.name = name;
		this.address = address;
		this.birthday = birthday;
		this.created = created;
		this.updated = updated;
	}
}
