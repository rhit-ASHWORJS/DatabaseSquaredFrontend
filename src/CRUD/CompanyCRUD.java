package CRUD;

import java.sql.CallableStatement;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;

import databasesquared.services.DatabaseConnectionService;

public class CompanyCRUD {
	private DatabaseConnectionService dbService = null;
	public CompanyCRUD(DatabaseConnectionService dbService) {
		this.dbService = dbService;
		// TODO Auto-generated constructor stub
	}
	/**
	 * Add a company to the database
	 * @param name
	 * @param numEmployees
	 * @param dateFounded
	 * @return ID of new company if sussesfull, -1 if did not work
	 */
	public int addCompany(String name, int numEmployees, Date dateFounded) {
		try {
			CallableStatement cs = dbService.getConnection().prepareCall("? = call addCompany(?,?,?,?)");
			cs.registerOutParameter(1, Types.INTEGER);
			cs.setString(2, name);
			cs.setInt(3, numEmployees);
			cs.setDate(4,dateFounded);
			cs.registerOutParameter(5, Types.INTEGER);
			cs.execute();
			int returnValue = cs.getInt(1);
			switch (returnValue) {
			case 1:
				System.out.println("add Company error 1");
				break;
			default:
				return cs.getInt(5);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();	
		}
		return -1;
	}
	
	/**
	 *  update a company info
	 * @param id
	 * @param name
	 * @param numEmployees
	 * @return true if successfull 
	 */
	public boolean updateCompanyInfo(int id, String name, int numEmployees) {
		try {
			CallableStatement cs = dbService.getConnection().prepareCall("? = call updateCompanyInfo(?,?,?)");
			cs.registerOutParameter(1, Types.INTEGER);
			cs.setInt(2, id);
			cs.setString(3, name);
			cs.setInt(4, numEmployees);
			cs.execute();
			int returnValue = cs.getInt(1);
			switch (returnValue) {
			case 1:
				System.out.println("updateCompany error 1");
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
	 * get the info about a company
	 * @param id
	 * @return resultSet {String:Name, int:NEmployees, Date:DateFounded
	 */
	public ResultSet getCompanyInfo(int id) {
		try {
			CallableStatement cs = dbService.getConnection().prepareCall("? = call getCompanyInfo(?)");
			cs.registerOutParameter(1, Types.INTEGER);
			cs.setInt(2, id);
			ResultSet r = cs.executeQuery();
			int returnValue = cs.getInt(1);
			switch (returnValue) {
			case 1:
				System.out.println("getCompanyInfo error 1");
				break;
			default:
				return r;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();	
		}
		return null;
	}
}
