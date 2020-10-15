package com.ectoum.colosso;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.json.JacksonJsonParser;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;

@SpringBootTest
@AutoConfigureMockMvc
class ColossoBackendApplicationTests {
	@Autowired
	public MockMvc mockMvc;
	
	public String obtainAccessToken() throws Exception {	
		String username = "andrelleitao@gmail.com";
		String password = "123456";
		
		ResultActions resultActions = mockMvc.perform(post("/auth")
				.contentType(MediaType.APPLICATION_JSON)
				.content("{\"username\":\"" + username + "\",\"password\":\"" + password + "\"}"))				
		        .andDo(print())
		        .andExpect(status().isOk());

		MvcResult result = resultActions.andReturn();
		String contentAsString = result.getResponse().getContentAsString();
		
		JacksonJsonParser jsonParser = new JacksonJsonParser();
	    return jsonParser.parseMap(contentAsString).get("token").toString();
	}
}
