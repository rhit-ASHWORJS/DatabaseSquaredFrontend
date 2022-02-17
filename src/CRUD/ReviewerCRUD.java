package CRUD;
import java.sql.CallableStatement;
import java.sql.Date;
import java.sql.SQLException;
import java.sql.Types;

import javax.swing.JOptionPane;

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
	 * @return 0 if successful, -1 if error
	 * 1:name is null, 2:Experience is null, 3:already exists : -2 if they are admin
	 */
	public int addReviewer(String Username, Date YoE) {
		try {
			CallableStatement cs = dbService.getConnection().prepareCall("{? = call addReviewer(?,?)}");
			cs.registerOutParameter(1, Types.INTEGER);
			cs.setString(2, Username);
			cs.setDate(3, YoE);
			cs.execute();
			int returnValue = cs.getInt(1);
			switch (returnValue) {
			case 1:
				JOptionPane.showMessageDialog(null, "Reviewer username cannot be empty");
				return 1;
			case 2:
				JOptionPane.showMessageDialog(null, "Experience cannot be empty");
				return 2;
			case 3:
				JOptionPane.showMessageDialog(null, "Reviewer already exists");
				return 3;
			case -2:
				return -2;
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
	 * not implemented
	 * @return
	 */
	public boolean updateReviewer() {
		return false;
	}
}
