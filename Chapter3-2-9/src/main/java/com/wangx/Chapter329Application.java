package com.wangx;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@MapperScan("com.wangx.mapper")
//@ComponentScan({"com.wangx."})
public class Chapter329Application {

	public static void main(String[] args) {
		SpringApplication.run(Chapter329Application.class, args);
	}
}
