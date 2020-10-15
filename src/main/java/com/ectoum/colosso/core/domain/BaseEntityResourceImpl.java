package com.ectoum.colosso.core.domain;

import java.io.Serializable;

import com.ectoum.colosso.core.domain.api.BaseEntityResource;

public class BaseEntityResourceImpl<PK extends Serializable> implements BaseEntityResource<PK> {
	private PK id;
	
	@Override
	public PK getId() {
		return this.id;
	}

	@Override
	public void setId(PK id) {
		this.id = id;		
	}	
}
