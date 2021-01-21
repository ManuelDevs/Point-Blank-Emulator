package ru.pb.global.dao.jdbc;

import org.apache.commons.dbcp.BasicDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ru.pb.global.properties.DataBaseProperty;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * @author Felixx
 */
public class DatabaseFactory {
	private static DatabaseFactory _instance;
	private BasicDataSource _dataSource;
	private static final Logger _log = LoggerFactory.getLogger(DatabaseFactory.class);

	public static DatabaseFactory getInstance() throws SQLException {
		if (_instance == null) {
			_instance = new DatabaseFactory();
		}
		return _instance;
	}

	private DatabaseFactory() {
		init();
		_log.info("Database connected successfully!");
	}

	private void init() {
		_dataSource = new BasicDataSource();
		_dataSource.setDriverClassName(DataBaseProperty.getInstance().DRIVER);
		_dataSource.setUsername(DataBaseProperty.getInstance().USER);
		_dataSource.setPassword(DataBaseProperty.getInstance().PASS);
		_dataSource.setUrl(DataBaseProperty.getInstance().URL);
		_dataSource.setMaxActive(DataBaseProperty.getInstance().MAX_CONNECTIONS);
		_dataSource.setMaxIdle(DataBaseProperty.getInstance().MAX_CONNECTIONS);
		_dataSource.setValidationQuery("SELECT 1");
	}

	public Connection newConnection() throws SQLException {
		return DatabaseUtils.newConnection(_dataSource);
	}

	public void shutdown() {
		try {
			_dataSource.close();
		} catch (Exception e) {
		}
	}
}