package com.ectoum.colosso.core.search.request;

import java.util.List;

import com.ectoum.colosso.core.search.model.Filter;
import com.ectoum.colosso.core.search.model.Order;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter @Getter @NoArgsConstructor
public class RequestFilter {
	private List<Filter> filters;	
	private List<Order> orders;
	private Integer page;
	private Integer size;
}
