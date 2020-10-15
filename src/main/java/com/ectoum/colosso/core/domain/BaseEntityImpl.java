package com.ectoum.colosso.core.domain;

import java.io.Serializable;

import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;

import com.ectoum.colosso.core.domain.api.BaseEntity;

/**
 * Implementação abstrata da {@link BaseEntity}.
 * 
 * @author André Leitão <andrelleitao@gmail.com>
 *
 * @param <PK> Tipo da chave primária.
 * 
 */
@MappedSuperclass
public abstract class BaseEntityImpl<PK extends Serializable> implements BaseEntity<PK> {
	private static final long serialVersionUID = 1L;

	private PK id;
	
	@Override
	@Transient
	public PK getId() {
		return this.id;
	}

	@Override
	public void setId(PK id) {
		this.id = id;		
	}
}
