package com.ectoum.colosso.core.search.model;

import com.ectoum.colosso.core.search.enums.SearchOperation;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter @Getter @AllArgsConstructor @NoArgsConstructor
public class Filter {
	private String field;
	private Object value;
	private SearchOperation operation;
}