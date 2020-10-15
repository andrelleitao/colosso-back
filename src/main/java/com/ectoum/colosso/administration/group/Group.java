package com.ectoum.colosso.administration.group;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.ectoum.colosso.administration.constants.DBSequence;
import com.ectoum.colosso.administration.role.Role;
import com.ectoum.colosso.administration.user.User;
import com.ectoum.colosso.constants.DBSchema;
import com.ectoum.colosso.core.domain.BaseEntityAuditedImpl;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name="groups", schema = DBSchema.SCHEMA_ADMINISTRATION)
@Getter @Setter @NoArgsConstructor
public class Group extends BaseEntityAuditedImpl<Long> {
	private static final long serialVersionUID = 1L;

	public Group(String name) {
		this.name = name;
	}
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = DBSequence.SEQ_GROUP)
	@SequenceGenerator(name = DBSequence.SEQ_GROUP,  schema=DBSchema.SCHEMA_ADMINISTRATION, sequenceName = DBSequence.SEQ_GROUP, allocationSize = 1, initialValue = 1)
	@Column(name = "ID_GROUP")
	@Override
	public Long getId() {		
		return super.getId();
	}	
	
	@Column(name = "NAME")
	private String name;
		
	private Set<User> users;
	
	@ManyToMany(mappedBy = "groups")	
	public Set<User> getUsers() {
		return users;
	}
	
	private Set<Role> roles;
	
	@ManyToMany
	@JoinTable(
			name = "groups_roles",
			schema = DBSchema.SCHEMA_ADMINISTRATION,
			joinColumns = @JoinColumn(name = "id_group"),
			inverseJoinColumns = @JoinColumn(name = "id_role"))
	public Set<Role> getRoles() {
		return roles;
	}	
}
