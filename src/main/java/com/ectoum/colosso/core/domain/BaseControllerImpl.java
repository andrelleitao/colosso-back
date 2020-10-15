package com.ectoum.colosso.core.domain;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.ectoum.colosso.core.domain.api.BaseController;
import com.ectoum.colosso.core.domain.api.BaseEntity;
import com.ectoum.colosso.core.domain.api.BaseEntityResource;
import com.ectoum.colosso.core.domain.api.BaseRepository;
import com.ectoum.colosso.core.domain.api.BaseService;
import com.ectoum.colosso.core.exception.model.CustomMessageException;
import com.ectoum.colosso.core.search.request.RequestFilter;

public abstract class BaseControllerImpl<PK extends Serializable, T extends BaseEntity<PK>, R extends BaseRepository<T, PK>, S extends BaseService<PK, T, R>, DTO extends BaseEntityResource<PK>>
		implements BaseController<PK, T, R, S, DTO> {
	@Autowired
	private S service;

	@PostMapping
	@Override
	public ResponseEntity<DTO> save(@RequestBody T entity) throws CustomMessageException {
		return ResponseEntity.status(HttpStatus.CREATED).body(toResource(service.save(entity)));
	}
	
	@PatchMapping("/{id}")
	@Override
	public ResponseEntity<Void> update(@RequestBody DTO resource, @PathVariable("id") PK id) throws CustomMessageException {
		Optional<T> entityOptional = service.findById(id);

		if (!entityOptional.isPresent()) {
			return ResponseEntity.notFound().build();
		}
		
		// Converte o DTO(Resource) para T
		T entity = fromResource(resource, entityOptional.get());		

		service.save(entity);

		return ResponseEntity.noContent().build();
	}

	@DeleteMapping("/{id}")
	@Override
	public ResponseEntity<DTO> delete(@PathVariable("id") PK id) throws CustomMessageException {
		Optional<T> entityOptional = service.findById(id);

		if (!entityOptional.isPresent()) {
			return ResponseEntity.notFound().build();
		}

		service.deleteById(id);

		return ResponseEntity.noContent().build();
	}

	@GetMapping("/{id}")
	@Override
	public ResponseEntity<DTO> findById(@PathVariable("id") PK id) throws CustomMessageException {
		Optional<T> entityOptional = service.findById(id);

		if (!entityOptional.isPresent()) {
			return ResponseEntity.notFound().build();
		}

		return ResponseEntity.status(HttpStatus.OK).body(toResource(entityOptional.get()));
	}

	@GetMapping
	@Override
	public List<DTO> findAll() throws CustomMessageException {
		List<T> entities = service.findAll();
		return entities.stream().map(this::toResource).collect(Collectors.toList());
	}

	@PostMapping("/search")
	@Override
	public Page<DTO> search(@RequestBody RequestFilter request) throws CustomMessageException {
		Page<DTO> entities = service.search(request.getFilters(), request.getPage(), request.getSize(),
				request.getOrders()).map(this::toResource);
		return entities;
	}
	
	@PostMapping("/findByFilters")
	@Override
	public List<DTO> findByFilters(@RequestBody RequestFilter request) throws CustomMessageException {
		List<T> entities = service.findByFilters(request.getFilters());
		return entities.stream().map(this::toResource).collect(Collectors.toList());
	}

	@Override
	public S getService() {
		return service;
	}

	public abstract T fromResource(DTO resource, T entity);

	public abstract DTO toResource(T entity);
}
