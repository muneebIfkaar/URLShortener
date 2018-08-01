package com.url.db;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

import org.slf4j.LoggerFactory;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import ch.qos.logback.classic.Logger;

public enum DBConnectionPool {

	INSTANCE;

	private final Logger log;

	final private HikariDataSource connectionPool;
	private Properties PROP;

	private DBConnectionPool() {

		log = (Logger) LoggerFactory.getLogger(DBConnectionPool.class);
		// try {
		loadPropertiesFile();
		connectionPool = createConnectionPoolInstance();

	}

	private void init() throws Exception {

		userName = PROP.getProperty("db.username");
		password = PROP.getProperty("db.password");
		hostName = PROP.getProperty("db.hostname");
		port = PROP.getProperty("db.port");
		dbName = PROP.getProperty("db.dbname");

		log.info("Usernamae: {}, pwd: {}, HostName: {}, port: {}, dbName: {}", userName, password, hostName, port,
				dbName);
	}

	private void loadPropertiesFile()  {
		
		PROP = new Properties();
		try {
			//String stringPath = getClass().getResource("db.conf").getFile();
			//File file = new File(stringPath);
			//PROP.load(new FileInputStream(file));
			//PROP.load(getClass().getResourceAsStream("db.conf"));
			
			//init();
		} catch (Exception e) {
			e.printStackTrace();
			log.error("Error! While reading prp file. ErrorMsg: {}", e.getMessage(), e);
		}

		
	}

	private String userName= "root"; //=
							// Global.INSTANCE.getPropertyValue("db.cab_service.username");
	private String password= "root"; //=
							// Global.INSTANCE.getPropertyValue("db.cab_service.password");
	private String hostName= "localhost"; //=
							// Global.INSTANCE.getPropertyValue("db.cab_service.hostname");
	private String port= "3306"; //=
						// Global.INSTANCE.getPropertyValue("db.cab_service.port");
	private String dbName= "urls"; //=
							// Global.INSTANCE.getPropertyValue("db.cab_service.dbname");
	// private String url = "jdbc:mysql//"+hostName+":"+port+"/"+dbName;

	private HikariDataSource createConnectionPoolInstance() {

		log.info("creating database connection pool ");

		log.debug("Config Properties. userName: {}, , hostName: {}, port : {}, dbName: {}",
				new Object[] { userName, hostName, port, dbName });
		HikariConfig config = new HikariConfig();

		config.setMaximumPoolSize(10);
		config.setDataSourceClassName("com.mysql.jdbc.jdbc2.optional.MysqlDataSource");
		config.addDataSourceProperty("serverName", hostName);
		config.addDataSourceProperty("port", port);
		config.addDataSourceProperty("databaseName", dbName);
		config.addDataSourceProperty("user", userName);
		config.addDataSourceProperty("password", password);

		HikariDataSource connectionPool = new HikariDataSource(config);

		log.info("DataBase connection pool has been created");

		return connectionPool;
	}

	public Connection getConnection() throws SQLException {

		return connectionPool.getConnection();

	}
}
