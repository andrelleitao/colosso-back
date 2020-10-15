package com.ectoum.colosso.core.enums;

public enum YesNo {
	YES("S"), NO("N");
	
	private String code;
	 
    private YesNo(String code) {
        this.code = code;
    }
 
    public String getCode() {
        return code;
    }
}