package CRUD;
import java.sql.CallableStatement;
import java.sql.Date;
import java.sql.SQLException;
import java.sql.Types;

import databasesquared.services.DatabaseConnectionService;

public class ReviewerCRUD {
	private DatabaseConnectionService dbService = null;
	public ReviewerCRUD(DatabaseConnectionService dbService) {
		this.dbService = dbService;
		// TODO Auto-generated constructor stub
	}
	/**
	 * Adds a reviewer to the database
	 * @param Username
	 * @param YoE
	 * @return true if successful
	 */
	public boolean addReviewer(String Username, Date YoE) {
		try {
			CallableStatement cs = dbService.getConnection().prepareCall("{? = call addReviewer(?,?)}");
			cs.registerOutParameter(1, Types.INTEGER);
			cs.setString(2, Username);
			cs.setDate(3, YoE);
			cs.execute();
			int returnValue = cs.getInt(1);
			switch (returnValue) {
			case 1:
				System.out.println("addReviewer error 1");
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
	 * not implemented
	 * @return
	 */
	public boolean updateReviewer() {
		return false;
	}
}
