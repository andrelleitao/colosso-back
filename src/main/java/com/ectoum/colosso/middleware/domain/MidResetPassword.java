package com.ectoum.colosso.middleware.domain;

import java.io.Serializable;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter @Getter @NoArgsConstructor
public class MidResetPassword implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private String uuid;	
	private String password;
	private String confirmationPassword;
}
