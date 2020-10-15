package com.ectoum.colosso.core.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import com.ectoum.colosso.constants.CFGCommon;
import com.ectoum.colosso.core.domain.api.BaseEntity;
import com.ectoum.colosso.core.domain.api.BaseRepository;
import com.ectoum.colosso.core.domain.api.BaseService;
import com.ectoum.colosso.core.exception.model.CustomMessageException;
import com.ectoum.colosso.core.search.model.Filter;
import com.ectoum.colosso.core.search.model.Order;
import com.ectoum.colosso.core.search.model.SearchCriteria;

/**
 * Implementação abstrata de {@link BaseEntityService}.
 * 
 * @author André Leitão <andrelleitao@gmail.com>
 *
 * @param <PK> Tipo da chave primária.
 * 
 */
public abstract class BaseServiceImpl<PK extends Serializable, T extends BaseEntity<PK>, R extends BaseRepository<T, PK>>
		implements BaseService<PK, T, R> {
	@Autowired
	private R repository;

	private BaseSpecificationImpl<T, PK> spec;

	@Override
	public T save(T entity) throws CustomMessageException {
		return repository.save(entity);
	}

	@Override
	public Page<T> search(List<Filter> filters, Integer page, Integer size, List<Order> orders) {
		page = (page == null) ? 0 : page;
		size = (size == null) ? CFGCommon.PAGE_SIZE : size;

		spec = new BaseSpecificationImpl<T, PK>();

		filters.stream().forEach(filter -> {
			spec.add(new SearchCriteria(filter.getField(), filter.getOperation(), filter.getValue()));
		});

		List<org.springframework.data.domain.Sort.Order> filterOrders = new ArrayList<org.springframework.data.domain.Sort.Order>();

		if (orders != null) {
			orders.stream().forEach(order -> {
				filterOrders
						.add(new org.springframework.data.domain.Sort.Order(order.getDirection(), order.getField()));
			});
		}

		Pageable pageable = PageRequest.of(page, size, Sort.by(filterOrders));

		return repository.findAll(spec, pageable);
	}

	@Override
	public List<T> findByFilters(List<Filter> filters) {
		spec = new BaseSpecificationImpl<T, PK>();

		filters.stream().forEach(filter -> {
			spec.add(new SearchCriteria(filter.getField(), filter.getOperation(), filter.getValue()));
		});

		return repository.findAll(spec);
	}

	@Override
	public List<T> findAll() {
		return repository.findAll();
	}
	
	@Override
	public Optional<T> findById(PK id) {
		return repository.findById(id);
	}
	
	@Override
	public void deleteById(PK id) {
		repository.deleteById(id);
	}

	@Override
	public R getRepository() {
		return repository;
	}
}
