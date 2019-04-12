package com.nixsolutions;

import java.io.FileNotFoundException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.sql.SQLException;

import org.dbunit.DatabaseUnitException;
import org.dbunit.database.DatabaseConfig;
import org.dbunit.database.DatabaseConnection;
import org.dbunit.database.IDatabaseConnection;
import org.dbunit.dataset.DataSetException;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.ITable;
import org.dbunit.dataset.xml.FlatXmlDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSetBuilder;
import org.dbunit.ext.h2.H2DataTypeFactory;
import org.dbunit.operation.DatabaseOperation;
import org.h2.tools.RunScript;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.nixsolutions.connection.ConnectionPool;
import com.nixsolutions.exception.DBException;
import com.nixsolutions.exception.Messages;

public class DBUnitConfig {

	private static final Logger LOGGER = LoggerFactory.getLogger(DBUnitConfig.class);
	protected static final String SCHEMA = "LABA13_DB";
	protected static final String SCHEMA_PATH = "schema.sql";
	protected static final String TABLES_PATH = "tables.sql";
	private final ConnectionPool connectionPool;

	public DBUnitConfig() {
		LOGGER.trace("DBUnit Config");
		// DBResourceManager.setProperty("database");
		connectionPool = ConnectionPool.getInstance();
	}

	public void createSchema() throws FileNotFoundException {
		LOGGER.trace("createSchema");
		Connection connection = null;
		try {
			connection = connectionPool.getConnection();
			RunScript.execute(connection, new InputStreamReader(
					this.getClass().getClassLoader().getResourceAsStream(SCHEMA_PATH), StandardCharsets.UTF_8));
			connection.commit();
		} catch (SQLException ex) {
			LOGGER.error(Messages.ERR_CANNOT_CREATE_SCHEMA, ex);
			rollbackConnection(connection);
			throw new DBException(Messages.ERR_CANNOT_CREATE_SCHEMA, ex);
		} finally {
			close(connection);

		}
	}

	public void createTables() throws FileNotFoundException {
		LOGGER.trace("createTables");
		Connection connection = null;
		try {
			connection = connectionPool.getConnection();
			RunScript.execute(connection, new InputStreamReader(
					this.getClass().getClassLoader().getResourceAsStream(TABLES_PATH), StandardCharsets.UTF_8));
			connection.commit();
		} catch (SQLException ex) {
			LOGGER.error(Messages.ERR_CANNOT_CREATE_TABLES, ex);
			rollbackConnection(connection);
			throw new DBException(Messages.ERR_CANNOT_CREATE_TABLES, ex);
		} finally {
			close(connection);

		}
	}

	public void fillTableData(String dataSetPath) {
		LOGGER.debug("fill table from " + dataSetPath);
		Connection connection = null;
		try {
			connection = connectionPool.getConnection();

			IDataSet xmlDataSet = new FlatXmlDataSetBuilder()
					.build(Thread.currentThread().getContextClassLoader().getResourceAsStream(dataSetPath));
			DatabaseOperation.CLEAN_INSERT.execute(getIDBConnection(connection), xmlDataSet);
			connection.commit();
		} catch (Exception ex) {
			LOGGER.error(Messages.ERR_CANNOT_FILL_TABLE, ex);
			rollbackConnection(connection);
			throw new DBException(Messages.ERR_CANNOT_FILL_TABLE, ex);
		} finally {
			close(connection);

		}
	}

	public ITable getTableFromSchema(String tableName) {
		LOGGER.debug("get table  " + tableName);
		Connection connection = null;
		ITable dataSetTable = null;
		try {
			connection = connectionPool.getConnection();
			IDataSet dataSet;
			try {
				dataSet = getIDBConnection(connection).createDataSet();
				connection.commit();
				dataSetTable = dataSet.getTable(tableName);
			} catch (Exception e) {
				LOGGER.error("cannot get table from schema", e);
			}
			return dataSetTable;
		} catch (SQLException ex) {
			LOGGER.error("cannot get connection", ex);
			rollbackConnection(connection);
			throw new DBException();
		} finally {
			close(connection);

		}
	}

	public ITable getTableFromFile(String tableName, String xmlFilePath) {
		LOGGER.debug("get table  " + tableName + " from " + xmlFilePath);
		ITable dataSetTable = null;
		try {
			FlatXmlDataSet dataSet = new FlatXmlDataSetBuilder()
					.build(Thread.currentThread().getContextClassLoader().getResourceAsStream(xmlFilePath));
			dataSetTable = dataSet.getTable(tableName);
		} catch (DataSetException e) {
			LOGGER.error("DataSetException", e);
			throw new RuntimeException(e);

		}
		return dataSetTable;
	}

	private IDatabaseConnection getIDBConnection(Connection connection) {
		DatabaseConnection databaseConnection = null;
		try {
			databaseConnection = new DatabaseConnection(connection, SCHEMA);
		} catch (DatabaseUnitException e) {
			LOGGER.error("DatabaseUnitException", e);
			throw new RuntimeException(e);
		}
		DatabaseConfig databaseConfig = databaseConnection.getConfig();
		databaseConfig.setProperty(DatabaseConfig.PROPERTY_DATATYPE_FACTORY, new H2DataTypeFactory());
		return databaseConnection;
	}

	public void close(Connection con) {
		if (con != null) {
			try {
				con.close();
			} catch (SQLException ex) {
				LOGGER.error(Messages.ERR_CANNOT_CLOSE_CONNECTION, ex);
			}
		}
	}

	public void rollbackConnection(Connection conn) {
		if (conn != null) {
			try {
				conn.rollback();
			} catch (SQLException e) {
				LOGGER.error("Cannot rollback connection", e);
			}
		}
	}
}
