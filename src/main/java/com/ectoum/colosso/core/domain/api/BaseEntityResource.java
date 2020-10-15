package com.ectoum.colosso.core.domain.api;

public interface BaseEntityResource<PK> {
	PK getId();

	void setId(PK id);
}
