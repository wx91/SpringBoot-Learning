package com.wangx.shardingjdbc.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wangx.shardingjdbc.entity.Order;
import com.wangx.shardingjdbc.entity.OrderItem;
import com.wangx.shardingjdbc.mapper.OrderItemMapper;
import com.wangx.shardingjdbc.mapper.OrderMapper;
import com.wangx.shardingjdbc.service.DemoService;

@Service
public class DemoServiceImpl implements DemoService {
	@Autowired
	private OrderMapper orderMapper;

	@Autowired
	private OrderItemMapper orderItemMapper;

	public void demo() {
		orderMapper.createIfNotExistsTable();
		orderItemMapper.createIfNotExistsTable();
		orderMapper.truncateTable();
		orderItemMapper.truncateTable();
		List<Long> orderIds = new ArrayList<>(10);
		System.out.println("1.Insert--------------");
		for (int i = 0; i < 10; i++) {
			Order order = new Order();
			order.setUserId(i);
			order.setStatus("INSERT_TEST");
			orderMapper.insert(order);
			long orderId = order.getOrderId();
			orderIds.add(orderId);

			OrderItem item = new OrderItem();
			item.setOrderId(orderId);
			item.setUserId(i);
			item.setStatus("INSERT_TEST");
			orderItemMapper.insert(item);
		}
		System.out.println(orderItemMapper.selectAll());
//		System.out.println("2.Delete--------------");
//		for (Long each : orderIds) {
//			orderMapper.delete(each);
//			orderItemMapper.delete(each);
//		}
//		System.out.println(orderItemMapper.selectAll());
//		orderItemMapper.dropTable();
//		orderMapper.dropTable();
	}
}
