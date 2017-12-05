package com.wangx.shardingjdbc.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.wangx.shardingjdbc.service.DemoService;

@RestController
public class DemoController {

	@Autowired
	private DemoService demoService;

	@RequestMapping("/hello")
	public String index() {
		demoService.demo();
		return "Hello World";
	}
}
