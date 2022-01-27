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
	 * @param listName
	 * @param dateCreated can be null
	 * @return ID of the new reviewList, -1 if it fails 
	 */
	public int addReviewList(String reviewer, String listName, Date dateCreated) {
		try {
			CallableStatement cs = dbService.getConnection().prepareCall("? = call addReviewList(?,?,?,?)");
			cs.registerOutParameter(1, Types.INTEGER);
			cs.setString(2, reviewer);
			cs.setString(3, listName);
			if(dateCreated == null) {
				cs.setNull(4, Types.NULL);
			}else {				
				cs.setDate(4, dateCreated);
			}
			cs.registerOutParameter(5, Types.INTEGER);
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
	 * @param name
	 * @return true is successful
	 */
	public boolean updateReviewList(String username, int RLID, String name) {
		try {
			CallableStatement cs = dbService.getConnection().prepareCall("? = call updateReviewList(?,?,?)");
			cs.registerOutParameter(1, Types.INTEGER);
			cs.setString(2, username);
			cs.setInt(3, RLID);
			cs.setString(4, name);
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
	 * @return resultset name, date created
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
	
	/**
	 * takes resultset and converts it
	 * @param rs 
	 * @return name, date created
	 */
	private ArrayList<ArrayList<String>> convertReviewList(ResultSet rs) {
		ArrayList<ArrayList<String>> list = new ArrayList<>();
		int index = 0;
		try {
			while (rs.next()) {
				list.add(new ArrayList<>());
				list.get(index).add(rs.getString(1));
				list.get(index).add(rs.getDate(2).toString());
				index++;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// TODO Auto-generated method stub
		return list;
	}
}
