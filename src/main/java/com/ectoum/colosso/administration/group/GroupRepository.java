package com.ectoum.colosso.administration.group;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.ectoum.colosso.core.domain.api.BaseRepository;

@Repository
public interface GroupRepository extends BaseRepository<Group, Long> {
	@Query(value = "SELECT g FROM Group g WHERE g.name = ?1")
	Group findByName(String name);
}
