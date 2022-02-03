package CRUD;

import java.sql.CallableStatement;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;

import javax.swing.JOptionPane;

import databasesquared.services.DatabaseConnectionService;

public class CompanyCRUD {
	private DatabaseConnectionService dbService = null;
	public CompanyCRUD(DatabaseConnectionService dbService) {
		this.dbService = dbService;
		// TODO Auto-generated constructor stub
	}
	/**
	 * Add a company to the database
	 * @param name
	 * @param numEmployees
	 * @param dateFounded
	 * @return ID of new company if sussesfull, -1 if did not work, -2 : company was null, -3:numEmplyees was null, -4 company already existed
	 */
	public int addCompany(String name, int numEmployees, Date dateFounded) {
		try {
			CallableStatement cs = dbService.getConnection().prepareCall("{? = call addCompany(?,?,?,?)}");
			cs.registerOutParameter(1, Types.INTEGER);
			cs.setString(2, name);
			cs.setInt(3, numEmployees);
			cs.setDate(4,dateFounded);
			cs.registerOutParameter(5, Types.INTEGER);
			cs.execute();
			int returnValue = cs.getInt(1);
			switch (returnValue) {
			case 1:
				JOptionPane.showMessageDialog(null, "Company name cannot be empty.");
				return -2;
			case 2:
				JOptionPane.showMessageDialog(null, "Number of employees cannot be empty.");
				return -3;
			case 3:
				JOptionPane.showMessageDialog(null, "Company already exists.");
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
	 *  update a company info
	 * @param id
	 * @param name can be null
	 * @param numEmployees -1 for null 
	 * @return 0 if successful, -1 if error, 1:ID was null, 2:Company does not exist
	 */
	public int updateCompanyInfo(int id, String name, int numEmployees) {
		try {
			CallableStatement cs = dbService.getConnection().prepareCall("{? = call updateCompanyInfo(?,?,?)}");
			cs.registerOutParameter(1, Types.INTEGER);
			cs.setInt(2, id);
			if(name == null) {
				cs.setNull(3, Types.NULL);
			}else {				
				cs.setString(3, name);
			}
			if(numEmployees == -1) {
				cs.setNull(4, Types.NULL);
			}else {				
				cs.setInt(4, numEmployees);
			}
			cs.execute();
			int returnValue = cs.getInt(1);
			switch (returnValue) {
			case 1:
				JOptionPane.showMessageDialog(null, "Company ID cannot be Empty.");
				return 1;
			case 2:
				JOptionPane.showMessageDialog(null, "Company does not exist in the database.");
				return 2;
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
	 * get the info about a company
	 * @param id
	 * @return resultSet String:Name, int:NEmployees, Date:DateFounded
	 * 	null if there is an error
	 */
	public ResultSet getCompanyInfo(int id) {
		try {
			CallableStatement cs = dbService.getConnection().prepareCall("{? = call getCompanyInfo(?)}");
			cs.registerOutParameter(1, Types.INTEGER);
			cs.setInt(2, id);
			cs.execute();
			int returnValue = cs.getInt(1);
			switch (returnValue) {
			case 1:
				JOptionPane.showMessageDialog(null, "Company ID cannot be empty.");
				break;
			case 2:
				JOptionPane.showMessageDialog(null, "Company does not exist in the database.");
				break;
			default:
				ResultSet r = cs.executeQuery();
				return r;
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
	 * @return String:Name, int:NEmployees, Date:DateFounded
	 */
	public ArrayList<ArrayList<String>> parseCompanyInfo(ResultSet rs){
		if(rs == null) {
			 return null;
			}
		ArrayList<ArrayList<String>> list = new ArrayList<>();
		int index = 0;
		try {
			while(rs.next()) {
				list.add(new ArrayList<>());
				list.get(index).add(rs.getString(1));
				list.get(index).add(rs.getInt(2)+"");
				list.get(index).add(rs.getDate(3).toString());
				index++;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}
}
