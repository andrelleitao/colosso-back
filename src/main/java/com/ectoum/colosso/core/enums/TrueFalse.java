package com.ectoum.colosso.core.enums;

public enum TrueFalse {
	TRUE(1), FALSE(0);
	
	private Integer code;
	
	private TrueFalse(Integer code) {
		this.code = code;
	}
	
	public Integer getCode() {
		return this.code;
	}
}
