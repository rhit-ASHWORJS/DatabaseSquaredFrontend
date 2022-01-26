package CRUD;

import java.sql.CallableStatement;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;

import databasesquared.services.DatabaseConnectionService;

public class DatabaseCRUD {
	private DatabaseConnectionService dbService = null;
	public DatabaseCRUD(DatabaseConnectionService dbService) {
		this.dbService = dbService;
		// TODO Auto-generated constructor stub
	}
	
	/**
	 * adds a database to to the table
	 * @param name
	 * @param DBMSName
	 * @param tableCount
	 * @param date
	 * @param description
	 * @return ID of added database, -1 if did not work
	 */
	public int addDatabase(String name, String DBMSName,  int tableCount,  Date date,String description) {
		try {
			CallableStatement cs = dbService.getConnection().prepareCall("? = call addDatabase(?,?,?,?,?,?)");
			cs.registerOutParameter(1, Types.INTEGER);
			cs.setString(2, name);
			cs.setString(3, DBMSName);
			cs.setInt(4, tableCount);
			cs.setDate(5, date);
			cs.setString(6, description);
			cs.registerOutParameter(7, Types.INTEGER);
			cs.execute();
			int returnValue = cs.getInt(1);
			switch (returnValue) {
			case 1:
				System.out.println("addDatabase error 1");
				break;
			default:
				return cs.getInt(7);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();	
		}
		return -1;
	}
	
	/**
	 * update info on a database 
	 * @param id
	 * @param name
	 * @param description
	 * @param tableCount
	 * @return true if successful 
	 */
	public boolean updateDatabase(int id, String name, String description, int tableCount) {
		try {
			CallableStatement cs = dbService.getConnection().prepareCall("? = call updateDatabase(?,?,?,?)");
			cs.registerOutParameter(1, Types.INTEGER);
			cs.setInt(2, id);
			cs.setString(3, name);
			cs.setString(4, description);
			cs.setInt(5, tableCount);
			cs.execute();
			int returnValue = cs.getInt(1);
			switch (returnValue) {
			case 1:
				System.out.println("updateDatabase error 1");
				break;
			default:
				return true;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();	
		}
		return false;
	}
	
	/**
	 * deletes a database from the table
	 * @param id
	 * @return true if successful
	 */
	public boolean deleteDatabase(int id) {
		try {
			CallableStatement cs = dbService.getConnection().prepareCall("? = call deleteDatabase(?)");
			cs.registerOutParameter(1, Types.INTEGER);
			cs.setInt(2, id);
			cs.execute();
			int returnValue = cs.getInt(1);
			switch (returnValue) {
			case 1:
				System.out.println("deleteDatabase error 1");
				break;
			default:
				return true;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();	
		}
		return false;
	}
	
	
	/**
	 * gets a database entry from the table 
	 * @apiNote THIS IS NOT YET A STORED PROC IN THE DATABASE
	 * @param id
	 * @return ResultSet, 
	 */
	public ResultSet getDatabase(int id){
		try {
			CallableStatement cs = dbService.getConnection().prepareCall("? = call getDatabase(?)");
			cs.registerOutParameter(1, Types.INTEGER);
			cs.setInt(2, id);
			ResultSet rs = cs.executeQuery();
			int returnValue = cs.getInt(1);
			switch (returnValue) {
			case 1:
				System.out.println("deleteDatabase error 1");
				break;
			default:
				return rs;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();	
		}
		return null;
	}

	//may make public, will do when output is finalized
	private ArrayList<String> convertSet(ResultSet rs) {
		ArrayList<String> list = new ArrayList<>();
		try {
			while(rs.next()) {
				StringBuilder sb = new StringBuilder();
				sb.append(rs.getString(1));
				sb.append(rs.getString(2));
				sb.append(rs.getString(3));
				sb.append(rs.getDate(4));
				sb.append(rs.getInt(5));
				sb.append("\n");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// TODO Auto-generated method stub
		return list;
	}
}
