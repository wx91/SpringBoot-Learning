package com.wangx.Mapper;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.wangx.domain.User;
import com.wangx.mapper.UserMapper;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@SpringBootApplication
public class UserMapperTests {
	
	@Autowired
	private UserMapper userMapper;
	@Test
	@Rollback
	public void findByName() throws Exception{
//		userMapper.insert("AAA", 20);
		User u = userMapper.findByName("AAA");
		Assert.assertEquals(20, u.getAge().intValue());
	}
}
