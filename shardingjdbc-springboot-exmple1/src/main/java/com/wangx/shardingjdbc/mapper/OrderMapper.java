package com.wangx.shardingjdbc.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.wangx.shardingjdbc.entity.Order;

@Mapper
public interface OrderMapper {

	void createIfNotExistsTable();

	void truncateTable();

	Long insert(Order model);

	void delete(Long orderId);

	void dropTable();
}
