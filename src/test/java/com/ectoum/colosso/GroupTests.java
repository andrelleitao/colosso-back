package com.ectoum.colosso;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.ectoum.colosso.administration.constants.RESTService;
import com.ectoum.colosso.administration.group.Group;
import com.ectoum.colosso.administration.group.GroupService;

@TestMethodOrder(OrderAnnotation.class)
public class GroupTests extends ColossoBackendApplicationTests {
	private static String accessToken;
	private static String groupName;
	
	@Autowired
	private GroupService groupService;
		
	@BeforeEach
	public void setup() throws Exception {
		if(accessToken == null) {
			accessToken = obtainAccessToken();
		}						
		if(groupName == null) {
			groupName = RandomStringUtils.randomAlphabetic(10);
		}
	}
	
	@Test
	@Order(1)
	public void givenGroupDoesNotExists_whenIsNotSavedGroupName_then201IsReceived() throws Exception {	
		mockMvc.perform(MockMvcRequestBuilders.post(RESTService.GROUP).contentType(MediaType.APPLICATION_JSON)
				.header("Authorization", "Bearer " + accessToken).content("{\"name\":\"" + groupName + "\"}"))
				.andExpect(status().isCreated());
	}
	
	@Test
	@Order(2)
	public void givenGroupExists_whenIsSavedGroupName_then400IsReceived() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.post(RESTService.GROUP).contentType(MediaType.APPLICATION_JSON)
				.header("Authorization", "Bearer " + accessToken).content("{\"name\":\"" + groupName + "\"}"))
				.andExpect(status().isBadRequest());
	}
	
	@Test
	@Order(3)
	public void givenGroupExists_whenGroupNameChanged_then200IsReceived() throws Exception {
		Group group = groupService.findByName(groupName);
		groupName = group.getName() + " Changed";
		
		mockMvc.perform(MockMvcRequestBuilders.put(RESTService.GROUP + "/" + group.getId()).contentType(MediaType.APPLICATION_JSON)
				.header("Authorization", "Bearer " + accessToken).content("{\"name\":\"" + groupName + "\"}"))
				.andExpect(status().isOk());
	}
	
	@Test
	@Order(4)
	public void givenGroupExists_whenGroupIsIndentifedById_then200IsReceived() throws Exception {
		Group group = groupService.findByName(groupName);	
				
		mockMvc.perform(MockMvcRequestBuilders.get(RESTService.GROUP + "/" + group.getId()).contentType(MediaType.APPLICATION_JSON)
				.header("Authorization", "Bearer " + accessToken))
				.andExpect(status().isOk());
	}
	
	@Test
	@Order(5)
	public void givenGroupExists_whenGroupIsRemoved_then200IsReceived() throws Exception {
		Group group = groupService.findByName(groupName);
		
		mockMvc.perform(MockMvcRequestBuilders.delete(RESTService.GROUP + "/" + group.getId()).contentType(MediaType.APPLICATION_JSON)
				.header("Authorization", "Bearer " + accessToken))
				.andExpect(status().isOk());
	}
}
