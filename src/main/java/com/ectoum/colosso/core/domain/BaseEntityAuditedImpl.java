package com.ectoum.colosso.core.domain;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.MappedSuperclass;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.ectoum.colosso.core.enums.TrueFalse;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Getter;
import lombok.Setter;

/**
 * Superclasse {@link MappedSuperclass} que server de base para todas as
 * entidades que necessitam suportar exclusão lógica.
 * 
 * @author André Leitão <andrelleitao@gmail.com>
 * 
 */
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
@JsonIgnoreProperties(
        value = {"createdAt", "updatedAt"}
)
@Getter @Setter
public abstract class BaseEntityAuditedImpl<PK extends Serializable> extends BaseEntityImpl<PK> {
	private static final long serialVersionUID = 1L;
	
	@CreatedDate
	@Column(name = "CREATED_AT", nullable = false, updatable = false)
	private LocalDateTime createdAt;

	@LastModifiedDate
	@Column(name = "UPDATED_AT", nullable = false)	
	private LocalDateTime updatedAt;

	@Column(name = "IS_ENABLED", nullable = false)
	@Enumerated(EnumType.ORDINAL)
	private TrueFalse isEnabled = TrueFalse.TRUE;
}
