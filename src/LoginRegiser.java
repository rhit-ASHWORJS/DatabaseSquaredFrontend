import java.sql.CallableStatement;
import java.sql.SQLException;
import java.sql.Types;

import databasesquared.services.DatabaseConnectionService;

public class LoginRegiser {
	//this should hold the place where we do the log in and register people
	private DatabaseConnectionService dbService = null;
	public LoginRegiser(DatabaseConnectionService dbService) {
		this.dbService = dbService;
		// TODO Auto-generated constructor stub
	}

	/**
	 * have a reviewer log in
	 * @param rUser
	 * @param rPass
	 * @return true if correct
	 */
	public boolean reviewerLogin(String rUser, String rPass) {
		try {
			CallableStatement cs = dbService.getConnection().prepareCall("? = call loginReviewer(?,?)");
			cs.registerOutParameter(1, Types.INTEGER);
			cs.setString(2, rUser);
			cs.setString(3, rPass);
			cs.execute();
			int returnValue = cs.getInt(1);
			switch (returnValue) {
			case 1:
				System.out.println("loginReviewer error 1");
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
	 * have a reviewer register
	 * @param rUser
	 * @param rHash
	 * @param rSalt
	 * @return true is successful
	 */
	public boolean reviewerRegister(String rUser, String rHash, byte[] rSalt) {
		try {
			CallableStatement cs = dbService.getConnection().prepareCall("? = call registerReviewer(?,?)");
			cs.registerOutParameter(1, Types.INTEGER);
			cs.setString(2, rUser);
			cs.setString(3, rHash);
			cs.setBytes(4, rSalt);
			cs.execute();
			int returnValue = cs.getInt(1);
			switch (returnValue) {
			case 1:
				System.out.println("registerReviewer error 1");
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
