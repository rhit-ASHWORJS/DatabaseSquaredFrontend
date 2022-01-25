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
	
	public boolean addDatabase(String name, String DBMSName, String description, Date date, int tableCount) {
		try {
			CallableStatement cs = dbService.getConnection().prepareCall("? = call addDatabase(?,?,?,?,?)");
			cs.registerOutParameter(1, Types.INTEGER);
			cs.setString(2, name);
			cs.setString(3, DBMSName);
			cs.setString(4, description);
			cs.setDate(5, date);
			cs.setInt(6, tableCount);
			cs.execute();
			int returnValue = cs.getInt(1);
			switch (returnValue) {
			case 1:
				System.out.println("addDatabase error 1");
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
	
	public boolean updateDatabase(int id, String name, String DBMSName, String description, Date date, int tableCount) {
		try {
			CallableStatement cs = dbService.getConnection().prepareCall("? = call updateDatabase(?,?,?,?,?,?)");
			cs.registerOutParameter(1, Types.INTEGER);
			cs.setInt(2, id);
			cs.setString(3, name);
			cs.setString(4, DBMSName);
			cs.setString(5, description);
			cs.setDate(6, date);
			cs.setInt(7, tableCount);
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
	public ArrayList<String> getDatabase(int id){
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
				return convertSet(rs);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();	
		}
		return null;
	}

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
