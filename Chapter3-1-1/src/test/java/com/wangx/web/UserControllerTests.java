package com.wangx.web;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMultipartHttpServletRequestBuilder;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.hamcrest.Matchers.equalTo;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@WebAppConfiguration
public class UserControllerTests {
	private MockMvc mvc;
	
	@Before
	public void setUp() throws Exception {
		mvc = MockMvcBuilders.standaloneSetup(new UserController()).build();
		
	}
	
	@Test
	public void testGetUsers() throws Exception {
		RequestBuilder request =  get("/users/");
		mvc.perform(request).
		andExpect(status().isOk()).
		andExpect(content().string(equalTo("[]")));
		//上传文件测试
		fileUpload("23423").file("23423", "34334".getBytes()).param("234234", "23423");
	}
	
	@Test
	public void testPostUser() throws Exception{
	
		RequestBuilder request = post("/users/")
				.param("id", "2")
				.param("name", "测试大师")
				.param("age", "20");
		
		mvc.perform(request)
			.andDo(MockMvcResultHandlers.print())
			.andExpect(content().string(equalTo("success")));
	}
	
	@Test
	public void testGetUsersAgain() throws Exception {
		RequestBuilder request =  get("/users/");
		mvc.perform(request)
			.andExpect(status().isOk())
			.andExpect(content().string(equalTo("[]")));
	}
	@Test
	public void testPutUser() throws Exception{
		RequestBuilder request = post("/users/1")
				.param("name", "测试终极大师")
				.param("age", "30");
		mvc.perform(request)
			.andDo(MockMvcResultHandlers.print())
			.andExpect(content().string(equalTo("success")));
	}
	@Test
	public void testGetUser() throws Exception{
		RequestBuilder request =  get("/users/1");
		mvc.perform(request)
		.andDo(MockMvcResultHandlers.print())
		.andExpect(content().string(equalTo("{\"id\":1,\"name\":\"测试终极大师\",\"age\":30}")));
	}
	@Test
	public void testDeleteUser() throws Exception{
		RequestBuilder request =  delete("/users/1");
		mvc.perform(request)
		.andExpect(content().string(equalTo("success")));
	}
	
	@Test
	public void testGetUsersAgain1() throws Exception {
		RequestBuilder request =  get("/users/");
		mvc.perform(request)
			.andExpect(status().isOk())
			.andExpect(content().string(equalTo("[]")));
	}
	

}
