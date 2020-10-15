package com.ectoum.colosso.administration.role;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.ectoum.colosso.administration.constants.DBSequence;
import com.ectoum.colosso.administration.group.Group;
import com.ectoum.colosso.administration.user.User;
import com.ectoum.colosso.constants.DBSchema;
import com.ectoum.colosso.core.domain.BaseEntityAuditedImpl;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name="roles", schema = DBSchema.SCHEMA_ADMINISTRATION)
@Getter @Setter @NoArgsConstructor
public class Role extends BaseEntityAuditedImpl<Long> {
	private static final long serialVersionUID = 1L;
	
	public Role(String name) {
		this.name = name;
	}
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = DBSequence.SEQ_ROLE)
	@SequenceGenerator(name = DBSequence.SEQ_ROLE,  schema=DBSchema.SCHEMA_ADMINISTRATION, sequenceName = DBSequence.SEQ_ROLE, allocationSize = 1, initialValue = 1)
	@Column(name = "ID_ROLE")
	@Override
	public Long getId() {		
		return super.getId();
	}
		
	@Column(name = "NAME")
	private String name;	
	
	private Set<User> users;
	
	@ManyToMany(mappedBy = "roles")
	public Set<User> getUsers() {
		return users;
	}
	
	private Set<Group> groups;
	
	@ManyToMany(mappedBy = "roles")
	public Set<Group> getGroups() {
		return groups;
	}
}
