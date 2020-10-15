package com.ectoum.colosso.core.domain.api;

import java.io.Serializable;

public interface BaseEntity<PK> extends Serializable {
	PK getId();

	void setId(PK id);
}
