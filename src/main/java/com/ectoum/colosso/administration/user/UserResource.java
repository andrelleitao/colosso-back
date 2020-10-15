package com.ectoum.colosso.administration.user;

import java.time.LocalDate;
import java.util.Set;

import com.ectoum.colosso.administration.group.GroupResource;
import com.ectoum.colosso.administration.role.RoleResource;
import com.ectoum.colosso.core.domain.BaseEntityAuditedResourceImpl;
import com.ectoum.colosso.core.enums.TrueFalse;
import com.ectoum.colosso.enums.Gender;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter @Getter @NoArgsConstructor
public class UserResource extends BaseEntityAuditedResourceImpl {
	private String firstName;
	private String lastName;
	private String email;
	private LocalDate dateBirth;
	private Gender gender;
	private TrueFalse isActive;
	private Set<GroupResource> groups;
	private Set<RoleResource> roles;
}
