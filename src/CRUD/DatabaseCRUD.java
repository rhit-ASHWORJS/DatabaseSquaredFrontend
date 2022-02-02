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
	 * 
	 * @param name
	 * @param DBMSName
	 * @param tableCount
	 * @param date
	 * @param description nullable
	 * @return ID of added database, -1 if did not work
	 */
	public int addDatabase(String name, String DBMSName, int tableCount, Date date, String description) {
		try {
			CallableStatement cs = dbService.getConnection().prepareCall("{? = call addDatabase(?,?,?,?,?,?)}");
			cs.registerOutParameter(1, Types.INTEGER);
			cs.setString(2, name);
			cs.setString(3, DBMSName);
			cs.setInt(4, tableCount);
			cs.setDate(5, date);
			if(description == null) {
				cs.setNull(6, Types.NULL);
			}else {				
				cs.setString(6, description);
			}
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
	 * 
	 * @param id
	 * @param name nullable
	 * @param description nullable 
	 * @param tableCount -1 for null
	 * @return 0 if successful, -1 if error
	 */
	public int updateDatabase(int id, String name, String description, int tableCount) {
		try {
			CallableStatement cs = dbService.getConnection().prepareCall("{? = call updateDatabase(?,?,?,?)}");
			cs.registerOutParameter(1, Types.INTEGER);
			cs.setInt(2, id);
			if(name == null) {
				cs.setNull(3, Types.NULL);
			}else {
				cs.setString(3, name);
			}
			if(description == null) {
				cs.setNull(4, Types.NULL);				
			}else {
				cs.setString(4, description);				
			}
			if(tableCount == -1) {
				cs.setNull(5, Types.NULL);
			}else {
				cs.setInt(5, tableCount);				
			}
			cs.execute();
			int returnValue = cs.getInt(1);
			switch (returnValue) {
			case 1:
				System.out.println("updateDatabase error 1");
				break;
			default:
				return 0;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return -1;
	}

	/**
	 * deletes a database from the table
	 * 
	 * @param id
	 * @return 0 if successful, -1 if error
	 */
	public int deleteDatabase(int id) {
		try {
			CallableStatement cs = dbService.getConnection().prepareCall("{? = call deleteDatabase(?)}");
			cs.registerOutParameter(1, Types.INTEGER);
			cs.setInt(2, id);
			cs.execute();
			int returnValue = cs.getInt(1);
			switch (returnValue) {
			case 1:
				System.out.println("deleteDatabase error 1");
				break;
			default:
				return 0;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return -1;
	}

	/**
	 * gets a database entry from the table
	 * 
	 * @apiNote THIS IS NOT YET A STORED PROC IN THE DATABASE
	 * @param id
	 * @return ResultSet,
	 */
	public ResultSet getDatabase(int id) {
		return null;
//		try {
//			CallableStatement cs = dbService.getConnection().prepareCall("{? = call getDatabase(?)}");
//			cs.registerOutParameter(1, Types.INTEGER);
//			cs.setInt(2, id);
//			cs.execute();
//			int returnValue = cs.getInt(1);
//			ResultSet rs = cs.executeQuery();
//			switch (returnValue) {
//			case 1:
//				System.out.println("deleteDatabase error 1");
//				break;
//			default:
//				return rs;
//			}
//		} catch (SQLException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		return null;
	}

	/**
	 * takes resultset and converts it
	 * @param rs
	 * @return
	 */
	private ArrayList<ArrayList<String>> convertSet(ResultSet rs) {
		ArrayList<ArrayList<String>> list = new ArrayList<>();
		int index = 0;
		try {
			while (rs.next()) {
				list.add(new ArrayList<>());
				list.get(index).add(rs.getString(1));
				list.get(index).add(rs.getString(2));
				list.get(index).add(rs.getString(3));
				list.get(index).add(rs.getDate(4).toString());
				list.get(index).add(rs.getInt(5) + "");
				index++;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// TODO Auto-generated method stub
		return list;
	}
}
