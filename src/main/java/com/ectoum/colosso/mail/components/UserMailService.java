package com.ectoum.colosso.mail.components;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.core.env.Environment;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring5.SpringTemplateEngine;

import com.ectoum.colosso.administration.user.User;
import com.ectoum.colosso.administration.user.UserService;
import com.ectoum.colosso.core.exception.model.CustomMessageException;
import com.ectoum.colosso.mail.emailLink.EmailLink;
import com.ectoum.colosso.mail.emailLink.EmailLinkService;
import com.ectoum.colosso.mail.enums.EmailLinkType;

@Component
public class UserMailService {
	@Autowired
	private JavaMailSender emailSender;
	@Autowired
	private SpringTemplateEngine thymeleafTemplateEngine;
	@Autowired
	private Environment env;
	@Autowired
	private EmailLinkService emailLinkService;
	@Autowired
	private MessageSource messageSource;
	@Autowired
	private UserService userService;
	@Autowired	
	private MessageSource mailMessageSource;

	public void sendEmailForgotPassword(String email) throws CustomMessageException {
		User user = userService.findByEmail(email);

		if (user == null) {
			throw new CustomMessageException(String
					.format(messageSource.getMessage("user.not.found", null, LocaleContextHolder.getLocale()), email));
		}

		// Generate unique code
		String uuid = UUID.randomUUID().toString();
		EmailLink emailLink = new EmailLink();
		emailLink.setEmail(user.getEmail());
		emailLink.setUuid(uuid);
		emailLink.setType(EmailLinkType.RESET_PASSWORD);

		try {
			emailLinkService.save(emailLink);

			Map<String, Object> templateModel = new HashMap<String, Object>();
			templateModel.put("recipientName", user.getFirstName());
			templateModel.put("companyName", env.getProperty("app.name"));
			templateModel.put("baseUrl", env.getProperty("app.frontendBaseURL"));
			templateModel.put("uuid", uuid);
			templateModel.put("footer", env.getProperty("mail.footer"));

			try {
				sendMessageUsingThymeleafTemplate(user.getEmail(),
						mailMessageSource.getMessage("forgotPassword.title", null, LocaleContextHolder.getLocale()),
						templateModel, "forgot-password.html");
			} catch (MessagingException e) {
				throw new CustomMessageException(e.getMessage());
			}
		} catch (Exception e) {
			throw new CustomMessageException(e.getMessage());
		}

	}

	private void sendMessageUsingThymeleafTemplate(String to, String subject, Map<String, Object> templateModel, String template)
			throws MessagingException {

		Context thymeleafContext = new Context();
		thymeleafContext.setVariables(templateModel);
		String htmlBody = thymeleafTemplateEngine.process(template, thymeleafContext);

		sendHtmlMessage(to, subject, htmlBody);
	}

	private void sendHtmlMessage(String to, String subject, String htmlBody) throws MessagingException {
		MimeMessage message = emailSender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
		helper.setTo(to);
		helper.setSubject(subject);
		helper.setText(htmlBody, true);
		emailSender.send(message);
	}

	public void sendAccountActivationEmail(String email) throws CustomMessageException {
		User user = userService.findByEmail(email);

		if (user == null) {
			throw new CustomMessageException(String
					.format(messageSource.getMessage("user.not.found", null, LocaleContextHolder.getLocale()), email));
		}

		// Generate unique code
		String uuid = UUID.randomUUID().toString();
		EmailLink emailLink = new EmailLink();
		emailLink.setEmail(user.getEmail());
		emailLink.setUuid(uuid);
		emailLink.setType(EmailLinkType.ACCOUNT_ACTIVATION);
		
		try {
			emailLinkService.save(emailLink);

			Map<String, Object> templateModel = new HashMap<String, Object>();
			templateModel.put("recipientName", user.getFirstName());			
			templateModel.put("baseUrl", env.getProperty("app.frontendBaseURL"));
			templateModel.put("uuid", uuid);
			templateModel.put("footer", env.getProperty("mail.footer"));

			try {
				sendMessageUsingThymeleafTemplate(user.getEmail(),
						mailMessageSource.getMessage("accountActivation.title", null, LocaleContextHolder.getLocale()),
						templateModel, "account-activation.html");
			} catch (MessagingException e) {
				throw new CustomMessageException(e.getMessage());
			}
		} catch (Exception e) {
			throw new CustomMessageException(e.getMessage());
		}
	}
}
