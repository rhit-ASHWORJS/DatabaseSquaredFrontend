package CRUD;

import java.sql.CallableStatement;
import java.sql.SQLException;
import java.sql.Types;

import databasesquared.services.DatabaseConnectionService;

public class CompanyCRUD {
	private DatabaseConnectionService dbService = null;
	public CompanyCRUD(DatabaseConnectionService dbService) {
		this.dbService = dbService;
		// TODO Auto-generated constructor stub
	}
	
	public boolean updateCompany(int id, String name, int employeeCount) {
		try {
			CallableStatement cs = dbService.getConnection().prepareCall("? = call updateCompany(?,?,?)");
			cs.registerOutParameter(1, Types.INTEGER);
			cs.setInt(2, id);
			cs.setString(3, name);
			cs.setInt(4, employeeCount);
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
}
