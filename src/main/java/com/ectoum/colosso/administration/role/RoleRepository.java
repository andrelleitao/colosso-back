package com.ectoum.colosso.administration.role;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.ectoum.colosso.core.domain.api.BaseRepository;

@Repository
public interface RoleRepository extends BaseRepository<Role, Long>{
	@Query(value = "SELECT r FROM Role r WHERE r.name = ?1")
	Role findByName(String name);
}
