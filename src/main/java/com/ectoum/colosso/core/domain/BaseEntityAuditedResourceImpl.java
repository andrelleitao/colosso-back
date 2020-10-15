package com.ectoum.colosso.core.domain;

import java.time.LocalDateTime;

import com.ectoum.colosso.core.enums.TrueFalse;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter @Getter @NoArgsConstructor
public class BaseEntityAuditedResourceImpl extends BaseEntityResourceImpl<Long> {	
	private LocalDateTime createdAt;	
	private LocalDateTime updatedAt;
	private TrueFalse isEnabled;
}