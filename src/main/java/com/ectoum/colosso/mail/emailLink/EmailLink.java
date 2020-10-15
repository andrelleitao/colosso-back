package com.ectoum.colosso.mail.emailLink;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.ectoum.colosso.administration.constants.DBSequence;
import com.ectoum.colosso.constants.DBSchema;
import com.ectoum.colosso.core.domain.BaseEntityAuditedImpl;
import com.ectoum.colosso.mail.enums.EmailLinkType;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "emails_links", schema = DBSchema.SCHEMA_LOG)
@Getter @Setter @NoArgsConstructor
public class EmailLink extends BaseEntityAuditedImpl<Long> {
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = DBSequence.SEQ_EMAIL_LINK)
	@SequenceGenerator(name = DBSequence.SEQ_EMAIL_LINK,  schema=DBSchema.SCHEMA_LOG, sequenceName = DBSequence.SEQ_EMAIL_LINK, allocationSize = 1, initialValue = 1)
	@Column(name = "ID_EMAIL_LINK")
	@Override
	public Long getId() {		
		return super.getId();
	}
	
	@Column(name = "UUID", length = 255, nullable = false)
	private String uuid;	
	
	@Column(name = "EMAIL", length = 100, nullable = false)
	private String email;	
	
	@Column(name = "TYPE", length = 50, nullable = false)
	@Enumerated(EnumType.STRING)
	private EmailLinkType type;
}
