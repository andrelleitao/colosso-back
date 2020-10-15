package com.ectoum.colosso.core.domain;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.jpa.domain.Specification;

import com.ectoum.colosso.core.domain.api.BaseEntity;
import com.ectoum.colosso.core.search.enums.SearchOperation;
import com.ectoum.colosso.core.search.model.SearchCriteria;

public class BaseSpecificationImpl<T extends BaseEntity<PK>, PK extends Serializable> implements Specification<T> {
	private static final long serialVersionUID = 1L;

	private List<SearchCriteria> list;

	public BaseSpecificationImpl() {
		this.list = new ArrayList<>();
	}

	public void add(SearchCriteria criteria) {
		list.add(criteria);
	}

	@SuppressWarnings("unchecked")
	@Override
	public Predicate toPredicate(Root<T> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
		// Cria uma nova lista de predicados do Criteria.
		List<Predicate> predicates = new ArrayList<>();

		// Adiciona os predicados baseando-se nos critérios fornecidos.
		String[] attributes = null;
		String entityJoin = null;
		String entityJoinAttribute = null;
		Path<?> path = null;
		
		for (SearchCriteria criteria : list) {
			// Caso seja informado na key o atributo de uma entidade de 
			// relacionamento, será separado o atributo da key e em seguida
			// realizado o join para levar em consideração o atributo da entidade
			// de relacionamento.
			if(criteria.getKey().indexOf(".") > 0) {
				attributes = criteria.getKey().split("\\.");
				entityJoinAttribute = attributes[1];
				entityJoin = attributes[0];
				
				// Acessa o atributo da entidade de relacionamento.
				path = root.get(entityJoin).get(entityJoinAttribute);
			} else {
				path = root.get(criteria.getKey());
			}
			
			if (criteria.getOperation().equals(SearchOperation.GREATER_THAN)) {
				if (path.getJavaType() == LocalDate.class) {
					predicates.add(builder.greaterThan((Path<LocalDate>) path,
							LocalDate.parse(criteria.getValue().toString())));
				} else if (path.getJavaType() == LocalDateTime.class) {
					predicates.add(builder.greaterThan((Path<LocalDateTime>) path,
							LocalDateTime.parse(criteria.getValue().toString())));
				} else {
					predicates.add(builder.greaterThan((Path<String>) path, criteria.getValue().toString()));
				}
			} else if (criteria.getOperation().equals(SearchOperation.LESS_THAN)) {
				if (path.getJavaType() == LocalDate.class) {
					predicates.add(builder.lessThan((Path<LocalDate>) path,
							LocalDate.parse(criteria.getValue().toString())));
				} else if (path.getJavaType() == LocalDateTime.class) {
					predicates.add(builder.lessThan((Path<LocalDateTime>) path,
							LocalDateTime.parse(criteria.getValue().toString())));
				} else {
					predicates.add(builder.lessThan((Path<String>) path, criteria.getValue().toString()));
				}
			} else if (criteria.getOperation().equals(SearchOperation.GREATER_THAN_EQUAL)) {
				if (path.getJavaType() == LocalDate.class) {
					predicates.add(builder.greaterThanOrEqualTo((Path<LocalDate>) path,
							LocalDate.parse(criteria.getValue().toString())));
				} else if (root.get(criteria.getKey()).getJavaType() == LocalDateTime.class) {
					predicates.add(builder.greaterThanOrEqualTo((Path<LocalDateTime>) path,
							LocalDateTime.parse(criteria.getValue().toString())));
				} else {
					predicates.add(
							builder.greaterThanOrEqualTo((Path<String>) path, criteria.getValue().toString()));
				}
			} else if (criteria.getOperation().equals(SearchOperation.LESS_THAN_EQUAL)) {
				if (path.getJavaType() == LocalDate.class) {
					predicates.add(builder.lessThanOrEqualTo((Path<LocalDate>) path,
							LocalDate.parse(criteria.getValue().toString())));
				} else if (path.getJavaType() == LocalDateTime.class) {
					predicates.add(builder.lessThanOrEqualTo((Path<LocalDateTime>) path,
							LocalDateTime.parse(criteria.getValue().toString())));
				} else {
					predicates.add(
							builder.lessThanOrEqualTo((Path<String>) path, criteria.getValue().toString()));
				}
			} else if (criteria.getOperation().equals(SearchOperation.NOT_EQUAL)) {
				predicates.add(builder.notEqual(path, criteria.getValue()));
			} else if (criteria.getOperation().equals(SearchOperation.EQUAL)) {
				if (path.getJavaType() == LocalDate.class) {
					predicates.add(builder.equal(path,
							LocalDate.parse(criteria.getValue().toString())));
				} else if (path.getJavaType() == LocalDateTime.class) {
					predicates.add(builder.equal(path,
							LocalDateTime.parse(criteria.getValue().toString())));
				} else {
					predicates.add(builder.equal(path, criteria.getValue()));
				}
			} else if (criteria.getOperation().equals(SearchOperation.MATCH)) {
				predicates.add(builder.like(builder.lower((Path<String>) path),
						"%" + criteria.getValue().toString().toLowerCase() + "%"));
			} else if (criteria.getOperation().equals(SearchOperation.MATCH_END)) {
				predicates.add(builder.like(builder.lower((Path<String>) path),
						criteria.getValue().toString().toLowerCase() + "%"));
			} else if (criteria.getOperation().equals(SearchOperation.MATCH_START)) {
				predicates.add(builder.like(builder.lower((Path<String>) path),
						"%" + criteria.getValue().toString().toLowerCase()));
			} else if (criteria.getOperation().equals(SearchOperation.IN)) {
				predicates.add(builder.in(root.get(criteria.getKey())).value(criteria.getValue()));
			} else if (criteria.getOperation().equals(SearchOperation.NOT_IN)) {
				predicates.add(builder.not(root.get(criteria.getKey())).in(criteria.getValue()));
			}
		}

		return builder.and(predicates.toArray(new Predicate[0]));
	}
}
