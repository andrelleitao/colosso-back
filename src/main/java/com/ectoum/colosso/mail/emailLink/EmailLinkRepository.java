package com.ectoum.colosso.mail.emailLink;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.ectoum.colosso.core.domain.api.BaseRepository;

@Repository
public interface EmailLinkRepository extends BaseRepository<EmailLink, Long> {	
	@Query(value = "SELECT el FROM EmailLink el WHERE el.uuid=?1")
	EmailLink findByUUID(String uuid);
}
