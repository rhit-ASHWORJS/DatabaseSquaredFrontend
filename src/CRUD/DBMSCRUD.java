package CRUD;

import java.sql.CallableStatement;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;

import databasesquared.services.DatabaseConnectionService;

public class DBMSCRUD {
	private DatabaseConnectionService dbService = null;

	public DBMSCRUD(DatabaseConnectionService dbService) {
		this.dbService = dbService;
		// TODO Auto-generated constructor stub
	}

	/**
	 * add a DMBS to the table
	 * 
	 * @param name
	 * @param MID
	 * @param language
	 * @param type
	 * @param dateCreated can be null
	 * @return true is successful
	 */
	public boolean addDBMS(String name, int MID, String language, String type, Date dateCreated) {
		try {
			CallableStatement cs = dbService.getConnection().prepareCall("{? = call addDBMS(?,?,?,?,?)}");
			cs.registerOutParameter(1, Types.INTEGER);
			cs.setString(2, name);
			cs.setInt(3, MID);
			cs.setString(4, language);
			cs.setString(5, type);
			cs.setDate(6, dateCreated);
			cs.execute();
			int returnValue = cs.getInt(1);
			switch (returnValue) {
			case 1:
				System.out.println("addDBMS error 1");
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
	 * update a DMBS in the table
	 * 
	 * @param name
	 * @param MID
	 * @param language nullable
	 * @param type     nullable
	 * @return true is successful
	 */
	public boolean updateDBMS(String name, int MID, String language, String type) {
		try {
			CallableStatement cs = dbService.getConnection().prepareCall("{? = call updateDBMS(?,?,?,?)}");
			cs.registerOutParameter(1, Types.INTEGER);
			cs.setString(2, name);
			cs.setInt(3, MID);
			if (language == null) {
				cs.setNull(4, Types.NULL);
			} else {
				cs.setString(4, language);
			}
			if (type == null) {
				cs.setNull(5, Types.NULL);
			} else {
				cs.setString(5, type);
			}
			cs.execute();
			int returnValue = cs.getInt(1);
			switch (returnValue) {
			case 1:
				System.out.println("updateDBMS error 1");
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
	 * deletes a DBMS from the table
	 * 
	 * @param name
	 * @param MID
	 * @return true is successful
	 */
	public boolean deleteDBMS(String name, int MID) {
		try {
			CallableStatement cs = dbService.getConnection().prepareCall("{? = call deleteDBMS(?,?)}");
			cs.registerOutParameter(1, Types.INTEGER);
			cs.setString(2, name);
			cs.setInt(3, MID);
			cs.execute();
			int returnValue = cs.getInt(1);
			switch (returnValue) {
			case 1:
				System.out.println("deleteDBMS error 1");
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
	 * gets a DBMS from the table
	 * 
	 * @apiNote NOT YET IMPLEMENTED
	 * @return
	 */
	public ResultSet getDBMS() {
		return null;
//		try {
//			CallableStatement cs = dbService.getConnection().prepareCall("{? = call nope(?)}");
//			cs.registerOutParameter(1, Types.INTEGER);
//			
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
}
