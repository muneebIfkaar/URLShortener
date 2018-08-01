package com.url.db;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.slf4j.LoggerFactory;

import ch.qos.logback.classic.Logger;

public abstract class DB {

	private final static Logger log = (Logger) LoggerFactory.getLogger(DB.class);

	/**
	 * Creates DataBase connection
	 * 
	 * @throws
	 * 
	 */

	public Connection getDBConnection() throws Exception {

		Connection conn = DBConnectionPool.INSTANCE.getConnection();
		log.info("DataBase connection established Successfully ");
		return conn;

	}

	public abstract void createTable() throws Exception;

	public boolean isTableExists(Connection conn, String tableName) throws Exception {
		DatabaseMetaData metaData = conn.getMetaData();
		ResultSet rs = metaData.getTables(null, null, tableName, null);
		boolean result = false;

		if (rs.next())
			result = true;

		rs.close();

		return result;
	}

	/**
	 * Close dataBase Connection
	 * 
	 * @throws SQLException
	 */
	public void closeConnection(Connection conn) {

		try {
			conn.close(); // we are using pool that's we dont need to close
			// connection

			log.info("DataBase connection Closed ");

		} catch (Exception e) {

			log.error("Error while closing DB connection. ErrorMSG: {}", e.getMessage(), e);

		}
	}

	/**
	 * A helper method which can be used to close AutoClosable resources without
	 * throwing exception.
	 * 
	 * @param autoClosable
	 *            Auto closable resource.
	 */
	public void close(AutoCloseable autoClosable) {

		try {
			if (autoClosable != null) {
				autoClosable.close();
			}
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
	}

	public static void main(String[] args) {

		try {
			Connection con = DBConnectionPool.INSTANCE.getConnection();
			System.out.println(con.getHoldability());
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
