package CRUD;
import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;

import databasesquared.services.DatabaseConnectionService;

public class ReviewCRUD {
	private DatabaseConnectionService dbService = null;
	public ReviewCRUD(DatabaseConnectionService dbService) {
		this.dbService = dbService;
		// TODO Auto-generated constructor stub
	}
	/**
	 * adds a review to a ReviewList from a user
	 * @param username
	 * @param RLID ReviewList ID
	 * @param DBMS
	 * @param score
	 * @param reviewText can be null
	 * @return true if successful
	 */
	public boolean addReview(String username, int RLID, String DBMS, double score, String reviewText) {
		try {
			CallableStatement cs = dbService.getConnection().prepareCall("? = call addReview(?,?,?,?,?)");
			cs.registerOutParameter(1, Types.INTEGER);
			cs.setString(2, username);
			cs.setInt(3, RLID);
			cs.setString(4, DBMS);
			cs.setDouble(5, score);
			if(reviewText == null) {
				cs.setNull(6, Types.NULL);
			}else {
				cs.setString(6, reviewText);
			}
			cs.execute();
			int returnValue = cs.getInt(1);
			switch (returnValue) {
			case 1:
				System.out.println("addReview error 1");
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
	 * update a review from a reviewer
	 * @param username
	 * @param RLID ReviewList ID
	 * @param DBMS
	 * @param score -1 is null
	 * @param reviewText can be null
	 * @return true if successful 
	 */
	public boolean updateReview(String username, int RLID, String DBMS, double score,String reviewText) {
		try {
			CallableStatement cs = dbService.getConnection().prepareCall("? = call updateReview(?,?,?,?,?)");
			cs.registerOutParameter(1, Types.INTEGER);
			cs.setString(2, username);
			cs.setInt(3, RLID);
			cs.setString(4, DBMS);
			if(score == -1.0) {
				cs.setNull(5, Types.NULL);
			}else {
				cs.setDouble(5, score);
			}
			if(reviewText == null) {
				cs.setNull(6, Types.NULL);
			}else {
				cs.setString(6, reviewText);
			}
			cs.execute();
			int returnValue = cs.getInt(1);
			switch (returnValue) {
			case 1:
				System.out.println("updateReview error 1");
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
	 * deletes a review by a user from the database
	 * @param username
	 * @param RLID reviewList ID
	 * @param DBMS
	 * @return true if successful
	 */
	public boolean deleteReview(String username, int RLID, String DBMS) {
		try {
			CallableStatement cs = dbService.getConnection().prepareCall("? = call deleteReview(?)");
			cs.registerOutParameter(1, Types.INTEGER);
			cs.setString(2, username);
			cs.setInt(3, RLID);
			cs.setString(4, DBMS);
			cs.execute();
			int returnValue = cs.getInt(1);
			switch (returnValue) {
			case 1:
				System.out.println("deleteReview error 1");
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
	 * gets the reviews from a reviewer
	 * @param username
	 * @return String:DBMSName, double:Score, reviewText, ReviewList name

	 */
	public ResultSet getReviews(String username){
		try {
			CallableStatement cs = dbService.getConnection().prepareCall("? = call getReviews(?)");
			cs.registerOutParameter(1, Types.INTEGER);
			cs.setString(2, username);
			ResultSet rs = cs.executeQuery();
			int returnValue = cs.getInt(1);
			switch (returnValue) {
			case 1:
				System.out.println("getReviews error 1");
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
	/**
	 * 
	 * @param rs
	 * @return String:DBMSName, double:Score, reviewText, ReviewList name
	 */
	public ArrayList<ArrayList<String>> parceReviews(ResultSet rs){
		ArrayList<ArrayList<String>> list = new ArrayList<>();
		int index = 0;
		try {
			while(rs.next()) {
				list.add(new ArrayList<>());
				list.get(index).add(rs.getString(1));
				list.get(index).add(rs.getDouble(2)+"");
				list.get(index).add(rs.getString(3));
				list.get(index).add(rs.getString(4));
				index++;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}
	
	/**
	 * default filter 
	 * @return ResultSet Username, DBMS, Company, Score, reviewText
	 */
	public ResultSet getListedReviews() {
		try {
			CallableStatement cs = dbService.getConnection().prepareCall("? = call getListedReviews()");
			cs.registerOutParameter(1, Types.INTEGER);
			ResultSet rs = cs.executeQuery();
			int returnValue = cs.getInt(1);
			switch (returnValue) {
			case 1:
				System.out.println("getListedReviews error 1");
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
	
	/**
	 * 
	 * @param rs
	 * @return Username, DBMS, Company, Score, reviewText
	 */
	public ArrayList<ArrayList<String>> parceListedReviews(ResultSet rs){
		ArrayList<ArrayList<String>> list = new ArrayList<>();
		int index = 0;
		try {
			while(rs.next()) {
				list.add(new ArrayList<>());
				list.get(index).add(rs.getString(1));
				list.get(index).add(rs.getString(2));
				list.get(index).add(rs.getString(3));
				list.get(index).add(rs.getString(4));
				list.get(index).add(rs.getString(5));
				index++;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}
}
