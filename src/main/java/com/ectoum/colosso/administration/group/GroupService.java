package com.ectoum.colosso.administration.group;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;

import com.ectoum.colosso.core.domain.BaseServiceImpl;
import com.ectoum.colosso.core.exception.model.CustomMessageException;

@Service
public class GroupService extends BaseServiceImpl<Long, Group, GroupRepository> {
	@Autowired
	private MessageSource messageSource;
	
	public Group findByName(String name) {
		return getRepository().findByName(name);
	}
	
	@Override
	public Group save(Group entity)  throws CustomMessageException {
		Group searched = findByName(entity.getName());
		
		if (searched != null && searched.getId() != entity.getId()) {
			throw new CustomMessageException(
					String.format(messageSource.getMessage("group.exists", null, LocaleContextHolder.getLocale()),
							entity.getName()));
		}
		
		return getRepository().save(entity);
	}
}
