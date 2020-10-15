package com.ectoum.colosso.core.domain.api;

import java.io.Serializable;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface BaseRepository<T extends BaseEntity<PK>, PK extends Serializable>
		extends JpaRepository<T, PK>, JpaSpecificationExecutor<T> {

}
