package com.ectoum.colosso.middleware;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ectoum.colosso.administration.user.UserService;
import com.ectoum.colosso.core.exception.model.CustomMessageException;
import com.ectoum.colosso.mail.components.UserMailService;
import com.ectoum.colosso.middleware.constants.RESTService;
import com.ectoum.colosso.middleware.converters.UserConverter;
import com.ectoum.colosso.middleware.domain.MidActiveAccount;
import com.ectoum.colosso.middleware.domain.MidResetPassword;
import com.ectoum.colosso.middleware.domain.MidUser;
import com.ectoum.colosso.middleware.validation.UserValidation;

@RestController
@RequestMapping(RESTService.PATH_API)
public class MiddlewareEndpoint {
	@Autowired
	private UserValidation userValidation;	
	@Autowired
	private UserService userService;
	@Autowired
	private UserMailService emailService;
	
	@PostMapping(RESTService.SIGN_UP)
	public ResponseEntity<Object> signUp(@RequestBody MidUser midUser) throws CustomMessageException {			
		userValidation.validates(midUser); 
		userService.save(UserConverter.toIntegration(midUser));
		emailService.sendAccountActivationEmail(midUser.getEmail());
		return ResponseEntity.status(HttpStatus.CREATED).build();
	}
	
	@PostMapping(RESTService.FORGOT_PASSWORD)
	public ResponseEntity<Object> forgotPassword(@RequestBody MidUser midUser) throws CustomMessageException {
		userValidation.forgotPassword(midUser);
		emailService.sendEmailForgotPassword(midUser.getEmail());		
		return ResponseEntity.status(HttpStatus.OK).build();
	}
	
	@PostMapping(RESTService.RESET_PASSWORD)
	public ResponseEntity<Object> resetPassword(@RequestBody MidResetPassword midResetPassword) throws CustomMessageException {
		userValidation.resetPassword(midResetPassword);
		userService.resetPassword(midResetPassword.getUuid(), midResetPassword.getPassword());		
		return ResponseEntity.status(HttpStatus.OK).build();
	}
	
	@PostMapping(RESTService.ACTIVE_ACCOUNT)
	public ResponseEntity<Object> activeAccount(@RequestBody MidActiveAccount midActiveAccount) throws CustomMessageException {
		userValidation.activeAccount(midActiveAccount);
		userService.activeAccount(midActiveAccount.getUuid());		
		return ResponseEntity.status(HttpStatus.OK).build();
	}
}
