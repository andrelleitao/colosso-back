package com.ectoum.colosso.administration.user;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.ectoum.colosso.core.domain.api.BaseRepository;

@Repository
public interface UserRepository extends BaseRepository<User, Long> {	
	@Query(value = "SELECT u FROM User u WHERE u.email = ?1")
	User findByEmail(String email);
}
