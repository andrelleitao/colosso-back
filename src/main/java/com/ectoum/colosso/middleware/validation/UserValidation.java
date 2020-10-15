package com.ectoum.colosso.middleware.validation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Component;

import com.ectoum.colosso.administration.constants.Module;
import com.ectoum.colosso.core.exception.model.CustomMessageException;
import com.ectoum.colosso.middleware.domain.MidActiveAccount;
import com.ectoum.colosso.middleware.domain.MidResetPassword;
import com.ectoum.colosso.middleware.domain.MidUser;
import com.ectoum.colosso.utils.ValidationUtil;


@Component
public class UserValidation {
	@Autowired
	private MessageSource messageSource;
	
	public void validates(MidUser midUser) throws CustomMessageException  {
		if(midUser.getFirstname() == null || (midUser.getFirstname() != null && midUser.getFirstname().trim().equalsIgnoreCase(""))) {
			throw new CustomMessageException(messageSource.getMessage("user.firstName.required", null,
					LocaleContextHolder.getLocale()));
		}
		
		if(midUser.getLastname() == null || (midUser.getLastname() != null && midUser.getLastname().trim().equalsIgnoreCase(""))) {
			throw new CustomMessageException(messageSource.getMessage("user.lastName.required", null,
					LocaleContextHolder.getLocale()));
		}
		
		if(midUser.getEmail() == null || (midUser.getEmail() != null && midUser.getEmail().trim().equalsIgnoreCase(""))) {
			throw new CustomMessageException(messageSource.getMessage("user.email.required", null,
					LocaleContextHolder.getLocale()));
		}
		
		if(!ValidationUtil.isValidEmail(midUser.getEmail())) {
			throw new CustomMessageException(messageSource.getMessage("user.email.invalid", null,
					LocaleContextHolder.getLocale()));
		}
		
		if(midUser.getGender() == null) {
			throw new CustomMessageException(messageSource.getMessage("user.gender.required", null,
					LocaleContextHolder.getLocale()));
		}
		
		if(midUser.getPassword() == null || (midUser.getPassword() != null && midUser.getPassword() .trim().equals(""))) {
			throw new CustomMessageException(messageSource.getMessage("user.password.required", null,
					LocaleContextHolder.getLocale()));
		}
	}
	
	public void forgotPassword(MidUser midUser) throws CustomMessageException  {	
		if(midUser.getEmail() == null || (midUser.getEmail() != null && midUser.getEmail().trim().equals(""))) {
			throw new CustomMessageException(String.format(messageSource.getMessage("mid.required.email", null,
					LocaleContextHolder.getLocale()), midUser.getEmail()));
		}
		
		if(!ValidationUtil.isValidEmail(midUser.getEmail())) {
			throw new CustomMessageException(String.format(messageSource.getMessage("mid.invalid.email", null,
					LocaleContextHolder.getLocale()), midUser.getEmail()));
		}
	}
	
	public void resetPassword(MidResetPassword midResetPassword) throws CustomMessageException  {	
		if(midResetPassword.getPassword() == null) {
			throw new CustomMessageException(messageSource.getMessage("user.password.required", null,
					LocaleContextHolder.getLocale()));
		} else if(midResetPassword.getUuid() == null) {
			throw new CustomMessageException(messageSource.getMessage("emailLink.uuid.not.found", null,
					LocaleContextHolder.getLocale()));
		} else if(midResetPassword.getConfirmationPassword() == null) {
			throw new CustomMessageException(messageSource.getMessage("user.passwordConfirmation.required", null,
					LocaleContextHolder.getLocale()));
		} else if (!midResetPassword.getPassword().equals(midResetPassword.getConfirmationPassword())) {
			throw new CustomMessageException(messageSource.getMessage("user.passwordConfirmation.invalid", null,
					LocaleContextHolder.getLocale()));
		} else if (midResetPassword.getPassword().length() < Module.MINIMUM_PASSWORD_LENGTH) {
			throw new CustomMessageException(String.format(messageSource.getMessage("user.password.minimum", null,
					LocaleContextHolder.getLocale()), Module.MINIMUM_PASSWORD_LENGTH));
		}
	}
	
	public void activeAccount(MidActiveAccount midActiveAccount) throws CustomMessageException  {	
		if(midActiveAccount.getUuid() == null) {
			throw new CustomMessageException(messageSource.getMessage("emailLink.uuid.not.found", null,
					LocaleContextHolder.getLocale()));
		}
	}
}
