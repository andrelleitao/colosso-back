package com.ectoum.colosso.middleware.converters;

import com.ectoum.colosso.administration.user.User;
import com.ectoum.colosso.enums.Gender;
import com.ectoum.colosso.middleware.domain.MidUser;

public class UserConverter {
	public static User toIntegration(MidUser service) {
		User user = new User();
		
		user.setFirstName(service.getFirstname());
		user.setLastName(service.getLastname());
		user.setEmail(service.getEmail());
		user.setDateBirth(service.getDateOfBirth());
		user.setGender(Gender.valueOf(service.getGender().name()));
		user.setPassword(service.getPassword());
				
		return user;
	}
}
