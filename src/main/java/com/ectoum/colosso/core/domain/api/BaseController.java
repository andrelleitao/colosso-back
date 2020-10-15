package com.ectoum.colosso.core.domain.api;

import java.io.Serializable;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import com.ectoum.colosso.core.exception.model.CustomMessageException;
import com.ectoum.colosso.core.search.request.RequestFilter;

public interface BaseController<PK 
	extends Serializable, T extends BaseEntity<PK>, R extends BaseRepository<T, PK>, S extends BaseService<PK, T, R>, DTO> {
	ResponseEntity<DTO> save(@RequestBody T entity) throws CustomMessageException;
		
	ResponseEntity<Void> update(@RequestBody DTO resource, @PathVariable("id") PK id) throws CustomMessageException;
	
	ResponseEntity<DTO> delete(@PathVariable PK id) throws CustomMessageException;
	
	ResponseEntity<DTO> findById(@PathVariable PK id) throws CustomMessageException;
	
	List<DTO> findAll() throws CustomMessageException;
	
	S getService();	
	
	T fromResource(DTO resource, T entity);
	
	DTO toResource(T entity);
	
	Page<DTO> search(RequestFilter request) throws CustomMessageException;
	
	List<DTO> findByFilters(RequestFilter request) throws CustomMessageException;
}
