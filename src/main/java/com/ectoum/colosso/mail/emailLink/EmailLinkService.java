package com.ectoum.colosso.mail.emailLink;

import org.springframework.stereotype.Service;

import com.ectoum.colosso.core.domain.BaseServiceImpl;

@Service
public class EmailLinkService extends BaseServiceImpl<Long, EmailLink, EmailLinkRepository> {
	public EmailLink findByUUID(String uuid) {
		return getRepository().findByUUID(uuid);
	}
}
