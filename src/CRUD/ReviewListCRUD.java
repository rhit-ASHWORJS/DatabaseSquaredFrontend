package CRUD;
import java.sql.CallableStatement;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;

import databasesquared.services.DatabaseConnectionService;

public class ReviewListCRUD {
	private DatabaseConnectionService dbService = null;
	public ReviewListCRUD(DatabaseConnectionService dbService) {
		this.dbService = dbService;
		// TODO Auto-generated constructor stub
	}
	/**
	 * adds a review list to the databse
	 * @param reviewer
	 * @param maxScore
	 * @param ratingSystem
	 * @param dateCreated
	 * @return ID of the new reviewList, -1 if it fails 
	 */
	public int addReviewList(String reviewer, double maxScore, String ratingSystem, Date dateCreated) {
		try {
			CallableStatement cs = dbService.getConnection().prepareCall("? = call addReviewList(?,?,?,?,?)");
			cs.registerOutParameter(1, Types.INTEGER);
			cs.setString(2, reviewer);
			cs.setDouble(3, maxScore);
			cs.setString(4, ratingSystem);
			cs.setDate(5, dateCreated);
			cs.registerOutParameter(6, Types.INTEGER);
			cs.execute();
			int returnValue = cs.getInt(1);
			switch (returnValue) {
			case 1:
				System.out.println("addReviewList error 1");
				break;
			default:
				return cs.getInt(6);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();	
		}
		return -1;
	}
	/**
	 * updates a ReviewList in the database
	 * @param username
	 * @param RLID ReviewList ID
	 * @param maxScore
	 * @param ratingSystem
	 * @return true is successful
	 */
	public boolean updateReviewList(String username, int RLID, double maxScore, String ratingSystem) {
		try {
			CallableStatement cs = dbService.getConnection().prepareCall("? = call updateReviewList(?,?,?,?)");
			cs.registerOutParameter(1, Types.INTEGER);
			cs.setString(2, username);
			cs.setInt(3, RLID);
			cs.setDouble(4, maxScore);
			cs.setString(5, ratingSystem);
			cs.execute();
			int returnValue = cs.getInt(1);
			switch (returnValue) {
			case 1:
				System.out.println("updateReviewList error 1");
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
	 * deletes a review list from of a users 
	 * @param username
	 * @param RLID ReviewList ID
	 * @return true is successful
	 */
	public boolean deleteReviewList(String username, int RLID) {
		try {
			CallableStatement cs = dbService.getConnection().prepareCall("? = call deleteReviewList(?,?)");
			cs.registerOutParameter(1, Types.INTEGER);
			cs.setString(2, username);
			cs.setInt(3, RLID);
			cs.execute();
			int returnValue = cs.getInt(1);
			switch (returnValue) {
			case 1:
				System.out.println("deleteReviewList error 1");
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
	 * gets the reviewList from a user 
	 * @param username
	 * @return resultset (MaxScore, RatingSystem, DateCreated)
	 */
	public ResultSet getReviewList(String username){
		try {
			CallableStatement cs = dbService.getConnection().prepareCall("? = call getReviewList(?)");
			cs.registerOutParameter(1, Types.INTEGER);
			cs.setString(2, username);
			ResultSet rs = cs.executeQuery();
			int returnValue = cs.getInt(1);
			switch (returnValue) {
			case 1:
				System.out.println("getReviewList error 1");
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
}
