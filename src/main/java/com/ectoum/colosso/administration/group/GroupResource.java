package com.ectoum.colosso.administration.group;

import java.util.Set;

import com.ectoum.colosso.administration.role.RoleResource;
import com.ectoum.colosso.core.domain.BaseEntityAuditedResourceImpl;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter @Getter @NoArgsConstructor
public class GroupResource extends BaseEntityAuditedResourceImpl {	
	private String name;
	private Set<RoleResource> roles;
}