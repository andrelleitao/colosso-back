package com.ectoum.colosso.administration.user;

import java.time.LocalDate;
import java.time.Period;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.Email;

import com.ectoum.colosso.administration.constants.DBSequence;
import com.ectoum.colosso.administration.group.Group;
import com.ectoum.colosso.administration.role.Role;
import com.ectoum.colosso.constants.DBSchema;
import com.ectoum.colosso.core.domain.BaseEntityAuditedImpl;
import com.ectoum.colosso.core.enums.TrueFalse;
import com.ectoum.colosso.enums.Gender;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "users", schema = DBSchema.SCHEMA_ADMINISTRATION)
@Getter @Setter @NoArgsConstructor
public class User extends BaseEntityAuditedImpl<Long> {	
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = DBSequence.SEQ_USER)
	@SequenceGenerator(name = DBSequence.SEQ_USER,  schema=DBSchema.SCHEMA_ADMINISTRATION, sequenceName = DBSequence.SEQ_USER, allocationSize = 1, initialValue = 1)
	@Column(name = "ID_USER")
	@Override
	public Long getId() {		
		return super.getId();
	}
		
	@Email
	@Column(name = "EMAIL", nullable = false)
	private String email;
		
	@Column(name = "PASSWORD", nullable = false)	
	private String password;	
	
	@Column(name = "FIRST_NAME", nullable = false)
	private String firstName;
		
	@Column(name = "LAST_NAME", nullable = false)
	private String lastName;
	
	@Column(name = "DATE_BIRTH", nullable = false)
	private LocalDate dateBirth;
	
	@Column(name = "GENDER", nullable = false)	
	@Enumerated(EnumType.STRING)
	private Gender gender;
	
	@Transient
	public Integer getAge() {
		LocalDate now = LocalDate.now();
		Period diff = Period.between(this.dateBirth, now);
		return diff.getYears();
	}
		
	private Set<Role> roles;
	
	@ManyToMany
	@JoinTable(
			name = "users_roles",
			schema = DBSchema.SCHEMA_ADMINISTRATION,
			joinColumns = @JoinColumn(name = "id_user"),
			inverseJoinColumns = @JoinColumn(name = "id_role"))
	public Set<Role> getRoles() {
		return roles;
	}	
	
	private Set<Group> groups;
	
	@ManyToMany
	@JoinTable(
			name="users_groups",
			schema = DBSchema.SCHEMA_ADMINISTRATION,
			joinColumns = @JoinColumn(name = "id_user"),
			inverseJoinColumns = @JoinColumn(name = "id_group"))
	public Set<Group> getGroups() {
		return groups;
	}
		
	@Column(name = "IS_ACTIVE", nullable = false)
	private TrueFalse isActive = TrueFalse.FALSE;
}
