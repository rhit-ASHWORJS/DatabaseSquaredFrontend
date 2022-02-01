package CRUD;

import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;

import databasesquared.services.DatabaseConnectionService;

public class FilterCRUD {
	private DatabaseConnectionService dbService = null;

	public FilterCRUD(DatabaseConnectionService dbService) {
		this.dbService = dbService;
		// TODO Auto-generated constructor stub
	}

	/**
	 * filters the reviews
	 * 
	 * @param reviewer nullable
	 * @param DBMS     nullable
	 * @param company  nullable
	 * @param score    nullable -1 is null
	 * @return rs
	 */
	public ResultSet filterReviews(String reviewer, String DBMS, String company, int score) {
		try {
			CallableStatement cs = dbService.getConnection().prepareCall("{? = call filterReviews(?,?,?,?)}");
			cs.registerOutParameter(1, Types.INTEGER);
			cs.setString(2, reviewer);

			cs.setString(3, DBMS);

			cs.setString(4, company);

			cs.setInt(5, score);

			cs.execute();
			int returnValue = cs.getInt(1);
			ResultSet rs = cs.executeQuery();
			switch (returnValue) {
			case 1:
				System.out.println("filterReviews error 1");
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
	 * @return ReviewerName, DBMS Company, score, text
	 */
	public ArrayList<ArrayList<String>> parceFilteredReviews(ResultSet rs) {
		ArrayList<ArrayList<String>> list = new ArrayList<>();
		int index = 0;
		try {
			System.out.println(rs.toString());
			while (rs.next()) {
				list.add(new ArrayList<>());
				list.get(index).add(rs.getString(1));
				list.get(index).add(rs.getString(2));
				list.get(index).add(rs.getString(3));
				list.get(index).add(rs.getInt(4) + "");
				list.get(index).add(rs.getString(5));
				index++;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}

	/**
	 * 
	 * @param company nullable
	 * @param score   nullable -1 is null
	 * @return
	 */
	public ResultSet filterDBMS(String company, int score) {
		try {
			CallableStatement cs = dbService.getConnection().prepareCall("{? = call filterDBMS(?,?)}");
			cs.registerOutParameter(1, Types.INTEGER);
			cs.setString(2, company);
			cs.setInt(3, score);
			cs.execute();
			int returnValue = cs.getInt(1);
			ResultSet rs = cs.executeQuery();
			switch (returnValue) {
			case 1:
				System.out.println("filterDBMS error 1");
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
	 * @return DBMS, Company, NumFatabases, AvgScore
	 */
	public ArrayList<ArrayList<String>> parceFilteredDBMS(ResultSet rs) {
		ArrayList<ArrayList<String>> list = new ArrayList<>();
		int index = 0;
		try {
			while (rs.next()) {
				list.add(new ArrayList<>());
				list.get(index).add(rs.getString(1));
				list.get(index).add(rs.getString(2));
				list.get(index).add(rs.getInt(3) + "");
				list.get(index).add(rs.getDouble(4) + "");
				index++;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}

	/**
	 * 
	 * @param usedDBMS nullable
	 * @param madeDBMS nullable
	 * @return
	 */
	public ResultSet filterCompanies(String usedDBMS, String madeDBMS) {
		try {
			CallableStatement cs = dbService.getConnection().prepareCall("{? = call filterCompanies(?,?)}");
			cs.registerOutParameter(1, Types.INTEGER);
			cs.setString(2, usedDBMS);
			cs.setString(3, madeDBMS);
			cs.execute();
			int returnValue = cs.getInt(1);
			ResultSet rs = cs.executeQuery();
			switch (returnValue) {
			case 1:
				System.out.println("filterCompanies error 1");
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
	 * @return Company, NumEmployees, UsedDBMS, MadeDBMS
	 */
	public ArrayList<ArrayList<String>> parceFilteredCompanies(ResultSet rs) {
		ArrayList<ArrayList<String>> list = new ArrayList<>();
		int index = 0;
		try {
			while (rs.next()) {
				list.add(new ArrayList<>());
				list.get(index).add(rs.getString(1));
				list.get(index).add(rs.getInt(2) + "");
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
}
