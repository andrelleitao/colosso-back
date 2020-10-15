package com.ectoum.colosso.administration.role;

import com.ectoum.colosso.core.domain.BaseEntityAuditedResourceImpl;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter @Getter @NoArgsConstructor
public class RoleResource extends BaseEntityAuditedResourceImpl {
	private String name;
}
