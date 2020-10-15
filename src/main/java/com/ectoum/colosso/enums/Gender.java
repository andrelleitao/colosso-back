package com.ectoum.colosso.enums;

public enum Gender {
	MALE("M"), FEMALE("F");
	
	private String value;
	
	private Gender(String value) {
		this.value = value;
	}
	
	public String getValue() {
		return this.value;
	}
}
