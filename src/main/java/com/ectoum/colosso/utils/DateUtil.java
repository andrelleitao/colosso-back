package com.ectoum.colosso.utils;

import java.time.LocalDate;

public class DateUtil {
	public static LocalDate parseToLocalDate(String dateStr) {
		return LocalDate.parse(dateStr);
	}
}
