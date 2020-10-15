package com.ectoum.colosso.administration.role;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;

import com.ectoum.colosso.core.domain.BaseServiceImpl;
import com.ectoum.colosso.core.exception.model.CustomMessageException;

@Service
public class RoleService extends BaseServiceImpl<Long, Role, RoleRepository> {
	@Autowired
	private MessageSource messageSource;
	
	private Role findByName(String name) {
		return getRepository().findByName(name);
	}
	
	@Override
	public Role save(Role entity)  throws CustomMessageException {
		Role searched = findByName(entity.getName());
		
		if (searched != null) {
			throw new CustomMessageException(
					String.format(messageSource.getMessage("role.exists", null, LocaleContextHolder.getLocale()),
							entity.getName()));
		}
		
		return getRepository().save(entity);
	}
}
