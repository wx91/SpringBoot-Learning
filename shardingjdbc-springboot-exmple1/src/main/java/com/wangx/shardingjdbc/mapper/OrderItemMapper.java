package com.wangx.shardingjdbc.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.wangx.shardingjdbc.entity.OrderItem;

@Mapper
public interface OrderItemMapper {
	void createIfNotExistsTable();

	void truncateTable();

	Long insert(OrderItem model);

	void delete(Long orderItemId);

	List<OrderItem> selectAll();

	void dropTable();
}
