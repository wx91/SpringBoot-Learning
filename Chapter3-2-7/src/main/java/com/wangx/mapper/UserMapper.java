package com.wangx.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.wangx.domain.User;

@Mapper
public interface UserMapper {

	@Select("select * from user where name = #{name}")
	User findByName(@Param("name") String name);

	@Insert("insert into user(name,age) values(#{name},#{age})")
	int insert(@Param("name") String name, @Param("age") Integer age);

}
