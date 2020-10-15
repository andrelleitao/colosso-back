package com.ectoum.colosso.middleware.enums;

public enum MidGender {
	MALE("M"), FEMALE("F");
	
	private final String value;
	
	MidGender(String value) {
		this.value = value;
	}
	
	public String getValue() {
		return this.value;
	}
}
