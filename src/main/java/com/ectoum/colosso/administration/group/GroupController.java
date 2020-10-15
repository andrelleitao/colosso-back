package com.ectoum.colosso.administration.group;

import java.util.HashSet;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ectoum.colosso.administration.constants.RESTService;
import com.ectoum.colosso.administration.role.Role;
import com.ectoum.colosso.core.domain.BaseControllerImpl;

@RestController
@RequestMapping(RESTService.GROUP)
public class GroupController extends BaseControllerImpl<Long, Group, GroupRepository, GroupService, GroupResource> {
	@Autowired
	private ModelMapper modelMapper;
		
	@Override
	public Group fromResource(GroupResource resource, Group entity) {
		entity.setName(resource.getName());
		entity.setIsEnabled(resource.getIsEnabled());
				
		// Preenche a lista de roles.
		entity.setRoles(new HashSet<Role>());
		resource.getRoles().forEach(res -> {
			entity.getRoles().add(modelMapper.map(res, Role.class));
		});
		
		return entity;
	}

	@Override
	public GroupResource toResource(Group entity) {
		return modelMapper.map(entity, GroupResource.class);
	}
}
