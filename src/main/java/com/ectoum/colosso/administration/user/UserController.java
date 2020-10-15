package com.ectoum.colosso.administration.user;

import java.util.HashSet;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ectoum.colosso.administration.constants.RESTService;
import com.ectoum.colosso.administration.group.Group;
import com.ectoum.colosso.administration.role.Role;
import com.ectoum.colosso.core.domain.BaseControllerImpl;

@RestController
@RequestMapping(RESTService.USER)
public class UserController extends BaseControllerImpl<Long, User, UserRepository, UserService, UserResource> {
	@Autowired
	private ModelMapper modelMapper;

	@Override
	public User fromResource(UserResource resource, User entity) {
		entity.setFirstName(resource.getFirstName());
		entity.setLastName(resource.getLastName());
		entity.setDateBirth(resource.getDateBirth());
		entity.setEmail(resource.getEmail());
		entity.setGender(resource.getGender());

		// Preenche a lista de roles.
		entity.setRoles(new HashSet<Role>());
		resource.getRoles().forEach(res -> {
			entity.getRoles().add(modelMapper.map(res, Role.class));
		});

		// Preenche a lista de grupos.
		entity.setGroups(new HashSet<Group>());
		resource.getGroups().forEach(res -> {
			entity.getGroups().add(modelMapper.map(res, Group.class));
		});

		return entity;
	}

	@Override
	public UserResource toResource(User entity) {
		return modelMapper.map(entity, UserResource.class);
	}

}
