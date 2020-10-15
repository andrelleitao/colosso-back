package com.ectoum.colosso.administration.user;

import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;

import com.ectoum.colosso.administration.constants.Module;
import com.ectoum.colosso.core.domain.BaseServiceImpl;
import com.ectoum.colosso.core.enums.TrueFalse;
import com.ectoum.colosso.core.exception.model.CustomMessageException;
import com.ectoum.colosso.mail.emailLink.EmailLink;
import com.ectoum.colosso.mail.emailLink.EmailLinkService;
import com.google.common.hash.Hashing;

@Service
public class UserService  extends BaseServiceImpl<Long, User, UserRepository> {
	@Autowired
	private MessageSource messageSource;

	@Autowired
	private EmailLinkService emailLinkService;

	public User findByEmail(String email) {
		return getRepository().findByEmail(email);
	}

	@Override
	public User save(User entity) throws CustomMessageException {
		User searchedUser = findByEmail(entity.getEmail());

		if (searchedUser != null && searchedUser.getId() != entity.getId()) {
			throw new CustomMessageException(
					String.format(messageSource.getMessage("user.exists", null, LocaleContextHolder.getLocale()),
							searchedUser.getEmail()));
		}
		
		// Quando o id vem diferente de null implica 
		// que a operação é uma atualização.
		if(entity.getId() != null) {
			entity.setPassword(searchedUser.getPassword());
		} else {
			if (entity.getPassword().length() < Module.MINIMUM_PASSWORD_LENGTH) {
				throw new CustomMessageException(
						String.format(messageSource.getMessage("user.min.password", null, LocaleContextHolder.getLocale()),
								Module.MINIMUM_PASSWORD_LENGTH));
			}

			String sha256hex = Hashing.sha256().hashString(entity.getPassword(), StandardCharsets.UTF_8).toString();
			entity.setPassword(sha256hex);
		}		

		return getRepository().save(entity);
	}

	/**z
	 * Responsável por realizar a atualização de senha do usuário. Para cada solicitação de recuperação de senha é 
	 * gerado um UUID e através dele a aplicação analisa se o link enviado para o usuário está expirado ou não.
	 * @param uuid
	 * @param email
	 * @param password
	 * @throws CustomMessageException
	 */
	public void resetPassword(String uuid, String password) throws CustomMessageException {
		EmailLink emailLink = emailLinkService.findByUUID(uuid);

		if (emailLink == null) {
			throw new CustomMessageException(
					messageSource.getMessage("resetPassword.invalidLink", null, LocaleContextHolder.getLocale()));
		} else {
			// Checks timeout email link.
			LocalDateTime createdAt = emailLink.getCreatedAt();
			LocalDateTime deadline = createdAt.plusMinutes(Module.TIMEOUT_IN_MINUTES_TO_RESET_PASSWORD);
			LocalDateTime now = LocalDateTime.now();
			
			if (deadline.isBefore(now)) {
				throw new CustomMessageException(
						messageSource.getMessage("emailLink.expiredLink", null, LocaleContextHolder.getLocale()));
			} else {
				User user = getRepository().findByEmail(emailLink.getEmail());
				user.setPassword(password);
				getRepository().save(user);
			}
		}
	}
	
	/**
	 * Responsável por realizar a ativação de conta do usuário. Para a ativação é  
	 * gerado um UUID e através dele a aplicação analisa se o link enviado para o usuário está expirado ou não.
	 * @param uuid
	 * @throws CustomMessageException
	 */
	public void activeAccount(String uuid) throws CustomMessageException {
		EmailLink emailLink = emailLinkService.findByUUID(uuid);

		if (emailLink == null) {
			throw new CustomMessageException(
					messageSource.getMessage("emailLink.invalidLink", null, LocaleContextHolder.getLocale()));
		} else {
			// Checks timeout email link.
			LocalDateTime createdAt = emailLink.getCreatedAt();
			LocalDateTime deadline = createdAt.plusMinutes(Module.TIMEOUT_IN_MINUTES_TO_ACTIVE_ACCOUNT);
			LocalDateTime now = LocalDateTime.now();
			
			if (deadline.isBefore(now)) {
				throw new CustomMessageException(
						messageSource.getMessage("emailLink.expiredLink", null, LocaleContextHolder.getLocale()));
			} else {
				User user = getRepository().findByEmail(emailLink.getEmail());
				user.setIsActive(TrueFalse.TRUE);
				getRepository().save(user);
			}
		}
	}
}
