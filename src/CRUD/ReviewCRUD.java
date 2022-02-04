package CRUD;

import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;

import javax.swing.JOptionPane;

import databasesquared.services.DatabaseConnectionService;

public class ReviewCRUD {
	private DatabaseConnectionService dbService = null;

	public ReviewCRUD(DatabaseConnectionService dbService) {
		this.dbService = dbService;
		// TODO Auto-generated constructor stub
	}

	/**
	 * adds a review to a ReviewList from a user
	 * 
	 * @param username
	 * @param RLID       ReviewList ID
	 * @param DBMS
	 * @param score
	 * @param reviewText can be null
	 * @return 0 if successful, -1 if error 1:Reviewer name is null, 2:ListID is
	 *         null, 3:DBMS name is null, 4: already exists, 5:Do not have
	 *         permission
	 */
	public int addReview(String username, String RLID, String DBMS, double score, String reviewText) {
		try {
			CallableStatement cs = dbService.getConnection().prepareCall("{? = call addReview(?,?,?,?,?)}");
			cs.registerOutParameter(1, Types.INTEGER);
			cs.setString(2, username);
			cs.setString(3, RLID);
			cs.setString(4, DBMS);
			cs.setDouble(5, score);
			if (reviewText == null) {
				cs.setNull(6, Types.NULL);
			} else {
				cs.setString(6, reviewText);
			}
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
				JOptionPane.showMessageDialog(null, "DMBS Name cannot be empty");
				return 3;
			case 4:
				JOptionPane.showMessageDialog(null, "Review already exists");
				return 4;
			case 5:
				JOptionPane.showMessageDialog(null, "Reviwer does not have permission to add a review to this list");
				return 5;
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
	 * update a review from a reviewer
	 * 
	 * @param username
	 * @param RLID       ReviewList ID
	 * @param DBMS
	 * @param score      -1 is null
	 * @param reviewText can be null
	 * @return 0 if successful, -1 if error 1:Reviewer name is null, 2:ListID is
	 *         null, 3:DBMS name is null, 4: already exists, 5:Do not have
	 *         permission
	 */
	public int updateReview(String username, String RLID, String DBMS, double score, String reviewText) {
		try {
			CallableStatement cs = dbService.getConnection().prepareCall("{? = call updateReview(?,?,?,?,?)}");
			cs.registerOutParameter(1, Types.INTEGER);
			cs.setString(2, username);
			cs.setString(3, RLID);
			cs.setString(4, DBMS);
			if (score == -1.0) {
				cs.setNull(5, Types.NULL);
			} else {
				cs.setDouble(5, score);
			}
			if (reviewText == null) {
				cs.setNull(6, Types.NULL);
			} else {
				cs.setString(6, reviewText);
			}
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
				JOptionPane.showMessageDialog(null, "DMBS Name cannot be empty");
				return 3;
			case 4:
				JOptionPane.showMessageDialog(null, "Review does not exist in the database");
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
	 * deletes a review by a user from the database
	 * 
	 * @param username
	 * @param RLID     reviewList ID
	 * @param DBMS
	 * @return 0 if successful, -1 if error 1:Reviewer name is null, 2:ListID is
	 *         null, 3:DBMS name is null, 4: does not exists, 5:Do not have
	 *         permission
	 */
	public int deleteReview(String username, String RLID, String DBMS) {
		try {
			CallableStatement cs = dbService.getConnection().prepareCall("{? = call deleteReview(?)}");
			cs.registerOutParameter(1, Types.INTEGER);
			cs.setString(2, username);
			cs.setString(3, RLID);
			cs.setString(4, DBMS);
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
				JOptionPane.showMessageDialog(null, "DMBS Name cannot be empty");
				return 3;
			case 4:
				JOptionPane.showMessageDialog(null, "Review does not exist in the database");
				return 4;
			case 5:
				JOptionPane.showMessageDialog(null, "Reviwer does not have permission to delete this review");
				return 5;
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
	 * gets the reviews from a reviewer
	 * 
	 * @param username
	 * @return String:DBMSName, double:Score, reviewText, ReviewList name
	 * 
	 */
	public ResultSet getReviews(String username) {
		try {
			CallableStatement cs = dbService.getConnection().prepareCall("{? = call getReviews(?)}");
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
	 * 
	 * @param rs
	 * @return String:DBMSName, double:Score, reviewText, ReviewList name
	 */
	public ArrayList<ArrayList<String>> parceReviews(ResultSet rs) {
		if (rs == null) {
			return null;
		}
		ArrayList<ArrayList<String>> list = new ArrayList<>();
		int index = 0;
		try {
			while (rs.next()) {
				list.add(new ArrayList<>());
				list.get(index).add(rs.getString(1));
				list.get(index).add(rs.getDouble(2) + "");
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
	 * 
	 * @return ResultSet Username, DBMS, Company, Score, reviewText
	 */
	public ResultSet getListedReviews() {
		try {
			CallableStatement cs = dbService.getConnection().prepareCall("{? = call getListedReviews()}");
			cs.registerOutParameter(1, Types.INTEGER);
			cs.execute();
			int returnValue = cs.getInt(1);
			switch (returnValue) {
			case 1:
				System.out.println("getListedReviews error 1");
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
	 * 
	 * @param rs
	 * @return Username, DBMS, Company, Score, reviewText
	 */
	public ArrayList<ArrayList<String>> parceListedReviews(ResultSet rs) {
		if (rs == null) {
			return null;
		}
		ArrayList<ArrayList<String>> list = new ArrayList<>();
		int index = 0;
		try {
			while (rs.next()) {
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
