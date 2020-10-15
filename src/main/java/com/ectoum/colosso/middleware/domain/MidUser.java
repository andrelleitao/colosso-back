package com.ectoum.colosso.middleware.domain;

import java.io.Serializable;
import java.time.LocalDate;

import com.ectoum.colosso.middleware.enums.MidGender;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter @Getter @NoArgsConstructor
public class MidUser implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private Long id;
	private String firstname;
	private String lastname;
	private String email;
	private LocalDate dateOfBirth;
	private MidGender gender;
	private String password;
}
