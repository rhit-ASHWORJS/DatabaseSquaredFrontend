package CRUD;
import java.sql.CallableStatement;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;

import javax.swing.JOptionPane;

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
	 * -2:Reviewer name is null, -3:List name is null, -4:already exists 
	 */
	public int addReviewList(String reviewer, String listName, Date dateCreated) {
		try {
			CallableStatement cs = dbService.getConnection().prepareCall("{? = call addReviewList(?,?,?,?)}");
			cs.registerOutParameter(1, Types.INTEGER);
			cs.setString(2, reviewer);
			cs.setString(3, listName);			
			cs.setDate(4, dateCreated);
			cs.registerOutParameter(5, Types.INTEGER);
			
			System.out.println(cs.toString());
			cs.execute();
			int returnValue = cs.getInt(1);
			switch (returnValue) {
			case 1:
				JOptionPane.showMessageDialog(null, "Reviewer username cannot be empty");
				return -2;
			case 2:
				JOptionPane.showMessageDialog(null, "ReviewList name cannot be empty");
				return -3;
			case 3:
				JOptionPane.showMessageDialog(null, "Review List already exists");
				return -4;
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
	 * updates a ReviewList in the database
	 * @param username
	 * @param RLID ReviewList ID
	 * @param name
	 * @return 0 if successful, -1 if error
	 * 1:Reviewer name is null, 2:List ID is null, 3:list name is null, 4:List does not exist
	 */
	public int updateReviewList(String username, int RLID, String name) {
		try {
			CallableStatement cs = dbService.getConnection().prepareCall("{? = call updateReviewList(?,?,?)}");
			cs.registerOutParameter(1, Types.INTEGER);
			cs.setString(2, username);
			cs.setInt(3, RLID);
			cs.setString(4, name);
			cs.execute();
			int returnValue = cs.getInt(1);
			switch (returnValue) {
			case 1:
				JOptionPane.showMessageDialog(null, "Reviewer username cannot be empty");
				return 1;
			case 2:
				JOptionPane.showMessageDialog(null, "ReviewList ID cannot be empty");
				return 2;
			case 3:
				JOptionPane.showMessageDialog(null, "Review List name cannot be empty");
				return 3;
			case 4:
				JOptionPane.showMessageDialog(null, "ReviewList does not exist in the database");
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
	 * deletes a review list from of a users 
	 * @param username
	 * @param RLID ReviewList ID
	 * @return 0 if successful, -1 if error
	 * 1:Reviewer name is null, 2:List ID is null, 3:List does not exist
	 */
	public int deleteReviewList(String username, int RLID) {
		try {
			CallableStatement cs = dbService.getConnection().prepareCall("{? = call deleteReviewList(?,?)}");
			cs.registerOutParameter(1, Types.INTEGER);
			cs.setString(2, username);
			cs.setInt(3, RLID);
			cs.execute();
			int returnValue = cs.getInt(1);
			switch (returnValue) {
			case 1:
				JOptionPane.showMessageDialog(null, "Reviewer username cannot be empty");
				return 1;
			case 2:
				JOptionPane.showMessageDialog(null, "ReviewList ID cannot be empty");
				return 2;
			case 3:
				JOptionPane.showMessageDialog(null, "ReviewList does not exist in the database");
				return 3;
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
	 * gets the reviewList from a user 
	 * @param username
	 * @return resultset name, date created
	 * null if there is an error
	 */
	public ResultSet getReviewList(String username){
		try {
			CallableStatement cs = dbService.getConnection().prepareCall("{? = call getReviewList(?)}");
			cs.registerOutParameter(1, Types.INTEGER);
			cs.setString(2, username);
			cs.execute();
			int returnValue = cs.getInt(1);
			switch (returnValue) {
			case 1:
				JOptionPane.showMessageDialog(null, "Reviewer username cannot be empty");
				break;
			case 2:
				JOptionPane.showMessageDialog(null, "Reviewer does not exist in the database");
				break;
			default:
				ResultSet rs = cs.executeQuery();
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
	public ArrayList<ArrayList<String>> parceReviewList(ResultSet rs) {
		if(rs == null) {
			 return null;
			}
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
