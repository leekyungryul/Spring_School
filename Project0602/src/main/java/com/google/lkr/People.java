package com.google.lkr;

public class People {
	int idx;
	String id;
	String pwd;
	String name;
	String birthday;
	String address;
	String created;
	String updated;

	People() {

	}
	People(int idx, String id, String pwd, String name, String birthday, String address, String created, String updated) {
		this.idx = idx;
		this.id = id;
		this.pwd = pwd;
		this.name = name;
		this.birthday = birthday;
		this.address = address;
		this.created = created;
		this.updated = updated;
	}
	People(String id, String pwd, String name, String birthday, String address, String created, String updated) {
		this.id = id;
		this.pwd = pwd;
		this.name = name;
		this.birthday = birthday;
		this.address = address;
		this.created = created;
		this.updated = updated;
	}
	
}
