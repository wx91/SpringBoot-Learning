package com.wangx.shardingjdbc;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import javax.sql.DataSource;

import com.wangx.shardingjdbc.repository.RawJdbcRepository;
import com.wangx.shardingjdbc.util.DataSourceUtil;

import io.shardingjdbc.core.api.ShardingDataSourceFactory;
import io.shardingjdbc.core.api.config.ShardingRuleConfiguration;
import io.shardingjdbc.core.api.config.TableRuleConfiguration;
import io.shardingjdbc.core.api.config.strategy.InlineShardingStrategyConfiguration;

public class RawJdbcJavaShardingTableOnlyMain {
	// CHECKSTYLE:OFF
	public static void main(final String[] args) throws SQLException {
		// CHECKSTYLE:ON
		new RawJdbcRepository(getShardingDataSource()).demo();
	}

	// 组合对应的数据源
	private static DataSource getShardingDataSource() throws SQLException {
		ShardingRuleConfiguration shardingRuleConfig = new ShardingRuleConfiguration();
		shardingRuleConfig.getTableRuleConfigs().add(getOrderTableRuleConfiguration());
		shardingRuleConfig.getTableRuleConfigs().add(getOrderItemTableRuleConfiguration());
		shardingRuleConfig.getBindingTableGroups().add("t_order, t_order_item");
		DataSource dataSource = ShardingDataSourceFactory.createDataSource(createDataSourceMap(), shardingRuleConfig,
				new HashMap<String, Object>(), new Properties());
		return dataSource;
	}

	// 创建数据源
	private static Map<String, DataSource> createDataSourceMap() {
		Map<String, DataSource> result = new HashMap<String, DataSource>(1, 1);
		result.put("demo_ds", DataSourceUtil.createDataSource("demo_ds"));
		return result;
	}

	// 创建针对t_order表的分表规则
	private static TableRuleConfiguration getOrderTableRuleConfiguration() {
		TableRuleConfiguration orderTableRuleConfig = new TableRuleConfiguration();
		orderTableRuleConfig.setLogicTable("t_order");
		orderTableRuleConfig.setActualDataNodes("demo_ds.t_order_${[0,1]}");
		// 针对t_order的分表是根据user_id
		InlineShardingStrategyConfiguration shardingStrategy = new InlineShardingStrategyConfiguration("user_id",
				" t_order_${user_id % 2}");
		orderTableRuleConfig.setKeyGeneratorColumnName("order_id");
		orderTableRuleConfig.setTableShardingStrategyConfig(shardingStrategy);
		return orderTableRuleConfig;
	}

	// 创建针对t_order_item表的分表规则
	private static TableRuleConfiguration getOrderItemTableRuleConfiguration() {
		TableRuleConfiguration orderItemTableRuleConfig = new TableRuleConfiguration();
		orderItemTableRuleConfig.setLogicTable("t_order_item");
		orderItemTableRuleConfig.setActualDataNodes("demo_ds.t_order_item_${[0, 1]}");
		// 针对t_order_item的分表是根据order_id的奇偶
		InlineShardingStrategyConfiguration shardingStrategy = new InlineShardingStrategyConfiguration("order_id",
				" t_order_item_${order_id % 2}");
		orderItemTableRuleConfig.setTableShardingStrategyConfig(shardingStrategy);
		return orderItemTableRuleConfig;
	}

}
