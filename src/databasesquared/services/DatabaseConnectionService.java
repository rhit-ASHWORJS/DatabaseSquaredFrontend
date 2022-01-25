package databasesquared.services;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;

public class DatabaseConnectionService {

	//DO NOT EDIT THIS STRING, YOU WILL RECEIVE NO CREDIT FOR THIS TASK IF THIS STRING IS EDITED
	private final String SampleURL = "jdbc:sqlserver://${dbServer};databaseName=${dbName};user=${user};password={${pass}}";

	private Connection connection = null;

	private String databaseName;
	private String serverName;

	public DatabaseConnectionService(String serverName, String databaseName) {
		//DO NOT CHANGE THIS METHOD
		this.serverName = serverName;
		this.databaseName = databaseName;
	}

	public boolean connect(String user, String pass) {
		
		String fullUrl = SampleURL
				.replace("${dbServer}", serverName)
				.replace("${dbName}", databaseName)
				.replace("${user}", user)
				.replace("${pass}", pass);
		
		try {
			connection = DriverManager.getConnection(fullUrl);
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
	

	public Connection getConnection() {
		return this.connection;
	}
	
	public void closeConnection() {
		try {
			if(connection != null)
			{
				connection.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}
