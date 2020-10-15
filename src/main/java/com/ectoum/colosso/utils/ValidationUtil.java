package com.ectoum.colosso.utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.regex.Pattern;

public class ValidationUtil {
	public static boolean isValidEmail(String email) {
		String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\." + "[a-zA-Z0-9_+&*-]+)*@" + "(?:[a-zA-Z0-9-]+\\.)+[a-z"
				+ "A-Z]{2,7}$";

		Pattern pat = Pattern.compile(emailRegex);
		if (email == null)
			return false;
		return pat.matcher(email).matches();
	}
	
	public static boolean isValidDate(String dateStr, String dateFormat) {
		if(dateStr != null) {
			DateFormat sdf = new SimpleDateFormat(dateFormat);
	        //sdf.setLenient(false);
	        
	        try {
	            sdf.parse(dateStr);
	        } catch (ParseException e) {
	            return false;
	        }
	        
	        return true;
		}		
        
        return false;
	}
}
