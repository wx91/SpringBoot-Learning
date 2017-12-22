package com.wangx.shardingjdbc.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.sql.DataSource;

import io.shardingjdbc.core.api.HintManager;

public class RawJdbcRepository {

	private final DataSource dataSource;

	public RawJdbcRepository(final DataSource dataSource) {
		this.dataSource = dataSource;
	}

	public void demo() throws SQLException {
		createTable();
		insertData();
		System.out.println("1.Equals Select--------------");
		printEqualsSelect();
		System.out.println("2.In Select--------------");
		printInSelect();
//		System.out.println("3.Hint Select--------------");
//		printHintSimpleSelect();
		dropTable();
	}

	private void execute(final DataSource dataSource, final String sql) throws SQLException {
		Connection connection = dataSource.getConnection();
		Statement statement = connection.createStatement();
		statement.execute(sql);
	}

	private long executeAndGetGeneratedKey(final DataSource dataSource, final String sql) throws SQLException {
		long result = -1;
		Connection connection = dataSource.getConnection();
		Statement statement = connection.createStatement();
		statement.executeUpdate(sql, Statement.RETURN_GENERATED_KEYS);
		ResultSet resultSet = statement.getGeneratedKeys();
		if (resultSet.next()) {
			result = resultSet.getLong(1);
		}
		return result;
	}

	public void createTable() throws SQLException {
		execute(dataSource,
				"CREATE TABLE IF NOT EXISTS t_order (order_id BIGINT NOT NULL AUTO_INCREMENT, user_id INT NOT NULL, status VARCHAR(50), PRIMARY KEY (order_id))");
		execute(dataSource,
				"CREATE TABLE IF NOT EXISTS t_order_item (order_item_id BIGINT NOT NULL AUTO_INCREMENT, order_id BIGINT NOT NULL, user_id INT NOT NULL, PRIMARY KEY (order_item_id))");
	}

	public void dropTable() throws SQLException {
		execute(dataSource, "DROP TABLE t_order_item");
		execute(dataSource, "DROP TABLE t_order");
	}

	public void insertData() throws SQLException {
		for (int i = 1; i < 10; i++) {
			long orderId = executeAndGetGeneratedKey(dataSource,
					"INSERT INTO t_order (user_id, status) VALUES (10, 'INIT')");
			execute(dataSource, String.format("INSERT INTO t_order_item (order_id, user_id) VALUES (%d, 10)", orderId));
			orderId = executeAndGetGeneratedKey(dataSource,
					"INSERT INTO t_order (user_id, status) VALUES (11, 'INIT')");
			execute(dataSource, String.format("INSERT INTO t_order_item (order_id, user_id) VALUES (%d, 11)", orderId));
		}
	}

	public void printEqualsSelect() throws SQLException {
		String sql = "SELECT i.* FROM t_order o JOIN t_order_item i ON o.order_id=i.order_id WHERE o.user_id=?";
		try (Connection conn = dataSource.getConnection();
				PreparedStatement preparedStatement = conn.prepareStatement(sql)) {
			preparedStatement.setInt(1, 10);
			printSimpleSelect(preparedStatement);
		}
	}

	public void printInSelect() throws SQLException {
		String sql = "SELECT i.* FROM t_order o JOIN t_order_item i ON o.order_id=i.order_id WHERE o.user_id in (?, ?)";
		try (Connection conn = dataSource.getConnection();
				PreparedStatement preparedStatement = conn.prepareStatement(sql)) {
			preparedStatement.setInt(1, 10);
			preparedStatement.setInt(2, 11);
			printSimpleSelect(preparedStatement);
		}
	}

	public void printHintSimpleSelect() throws SQLException {
		String sql = "SELECT i.* FROM t_order o JOIN t_order_item i ON o.order_id=i.order_id";
		try (HintManager hintManager = HintManager.getInstance();
				Connection conn = dataSource.getConnection();
				PreparedStatement preparedStatement = conn.prepareStatement(sql)) {
			hintManager.addDatabaseShardingValue("t_order", "user_id", 11);
			printSimpleSelect(preparedStatement);
		}
	}

	private void printSimpleSelect(final PreparedStatement preparedStatement) throws SQLException {
		try (ResultSet rs = preparedStatement.executeQuery()) {
			while (rs.next()) {
				System.out.print("order_item_id:" + rs.getLong(1) + ", ");
				System.out.print("order_id:" + rs.getLong(2) + ", ");
				System.out.print("user_id:" + rs.getInt(3));
				System.out.println();
			}
		}
	}

}
