package com.wangx.mapper;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.wangx.domain.User;

@Mapper
public interface UserMapper {

	@Select("select * from user where name = #{name}")
	User findByName(@Param("name") String name);

	@Insert("insert into user(name,age) values(#{name},#{age})")
	int insert(@Param("name") String name, @Param("age") Integer age);

	@Update("update user set age=#{age} where name=#{name}")
	int update(User user);

	@Delete("delete from user where id=#{id}")
	int delete(Long id);
}
