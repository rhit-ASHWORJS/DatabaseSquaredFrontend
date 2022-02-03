package CRUD;

import java.sql.CallableStatement;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;

import javax.swing.JOptionPane;

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
	 * @return 0 if successful, -1 if error
	 * 1:Name is null, 2:Manf ID is null, 3: Language is null, 4:Type is null, 5: Already exists 
	 */
	public int addDBMS(String name, int MID, String language, String type, Date dateCreated) {
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
				JOptionPane.showMessageDialog(null, "DBMS name cannot be empty");
				return 1;
			case 2:
				JOptionPane.showMessageDialog(null, "Manufacturer ID cannot be empty");
				return 2;
			case 3:
				JOptionPane.showMessageDialog(null, "Language cannot be empty");
				return 3;
			case 4:
				JOptionPane.showMessageDialog(null, "type cannont be empty");
				return 4;
			case 5:
				JOptionPane.showMessageDialog(null, "DBMS already exists");
				return 5;
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
	 * update a DMBS in the table
	 * 
	 * @param name
	 * @param MID
	 * @param language nullable
	 * @param type     nullable
	 * @return 0 if successful, -1 if error
	 * 1:Name is null, 2:Manf ID is null, 3:DBMS does not exist, 4:do not have permission to do this
	 */
	public int updateDBMS(String name, int MID, String language, String type) {
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
				JOptionPane.showMessageDialog(null, "DBMS name cannot be empty");
				return 1;
			case 2:
				JOptionPane.showMessageDialog(null, "Manufacturer ID cannot be empty");
				return 2;
			case 3:
				JOptionPane.showMessageDialog(null, "DBMS does not exist in the database");
				return 3;
			case 4:
				JOptionPane.showMessageDialog(null, "Company doesn not have permission to edit this DBMS");
				return 4;
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
	 * deletes a DBMS from the table
	 * 
	 * @param name
	 * @param MID
	 * @return 0 if successful, -1 if error
	 * 1:Name is null, 2:Manf ID is null, 3:DBMS does not exist, 4:do not have permission to do this
	 */
	public int deleteDBMS(String name, int MID) {
		try {
			CallableStatement cs = dbService.getConnection().prepareCall("{? = call deleteDBMS(?,?)}");
			cs.registerOutParameter(1, Types.INTEGER);
			cs.setString(2, name);
			cs.setInt(3, MID);
			cs.execute();
			int returnValue = cs.getInt(1);
			switch (returnValue) {
			case 1:
				JOptionPane.showMessageDialog(null, "DBMS name cannot be empty");
				return 1;
			case 2:
				JOptionPane.showMessageDialog(null, "Manufacturer ID cannot be empty");
				return 2;
			case 3:
				JOptionPane.showMessageDialog(null, "DBMS does not exist in the database");
				return 3;
			case 4:
				JOptionPane.showMessageDialog(null, "Company doesn not have permission to delete this DBMS");
				return 4;
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
