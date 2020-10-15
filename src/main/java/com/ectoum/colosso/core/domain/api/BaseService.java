package com.ectoum.colosso.core.domain.api;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import com.ectoum.colosso.core.exception.model.CustomMessageException;
import com.ectoum.colosso.core.search.model.Filter;
import com.ectoum.colosso.core.search.model.Order;

@Service
public interface BaseService<PK extends Serializable, T extends BaseEntity<PK>, R extends BaseRepository<T, PK>> {
	T save(T entity) throws CustomMessageException;

	Page<T> search(List<Filter> filters, Integer page, Integer size, List<Order> orders);

	List<T> findByFilters(List<Filter> filters);

	R getRepository();

	List<T> findAll();

	Optional<T> findById(PK id);
	
	void deleteById(PK id);
}
