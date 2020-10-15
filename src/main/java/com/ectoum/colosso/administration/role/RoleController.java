package com.ectoum.colosso.administration.role;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ectoum.colosso.administration.constants.RESTService;
import com.ectoum.colosso.core.domain.BaseControllerImpl;

@RestController
@RequestMapping(RESTService.ROLE)
public class RoleController extends BaseControllerImpl<Long, Role, RoleRepository, RoleService, RoleResource> {
	@Autowired
	private ModelMapper modelMapper;
	
	@Override
	public Role fromResource(RoleResource resource, Role entity) {
		entity.setName(resource.getName());
		entity.setIsEnabled(resource.getIsEnabled());
		return entity;
	}

	@Override
	public RoleResource toResource(Role entity) {
		return modelMapper.map(entity, RoleResource.class);
	}
}
