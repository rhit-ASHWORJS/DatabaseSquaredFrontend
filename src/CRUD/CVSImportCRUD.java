package CRUD;

import java.sql.CallableStatement;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;

import javax.swing.JOptionPane;

import databasesquared.services.DatabaseConnectionService;

public class CVSImportCRUD {
	private DatabaseConnectionService dbService = null;
	public CVSImportCRUD(DatabaseConnectionService dbService) {
		this.dbService = dbService;
		// TODO Auto-generated constructor stub
	}
	
	
	public void addRow(String dbName, String DBMSName, String desc, Date dbDate, int numOfTables, String comapanyName, int numEmployees, Date conpDate, String DBMSLang,String DBMSType, Date DBMSDate, String DBMSManf,int manfEmployees, Date manfDate ) {
		try {
			CallableStatement cs = dbService.getConnection().prepareCall("{? = call importCVSLine(?,?,?,?,?,?,?,?,?,?,?,?,?,?)}");
			cs.registerOutParameter(1, Types.INTEGER);
			cs.setString(2,dbName);
			cs.setString(3,DBMSName);
			cs.setString(4,desc);
			cs.setDate(5,dbDate);
			cs.setInt(6,numOfTables);
			cs.setString(7,comapanyName);
			cs.setInt(8,numEmployees);
			cs.setDate(9,conpDate);
			cs.setString(10,DBMSLang);
			cs.setString(11,DBMSType);
			cs.setDate(12,DBMSDate);
			cs.setString(13,DBMSManf);
			cs.setInt(14,manfEmployees);
			cs.setDate(15,manfDate);
			cs.execute();
			int returnValue = cs.getInt(1);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();	
		}
	}
	
	/**
	 * get the info about a company
	 * @param id
	 * @return resultSet String:Name, int:NEmployees, Date:DateFounded
	 * 	null if there is an error
	 */
	public ResultSet getAdminOutput() {
		try {
			CallableStatement cs = dbService.getConnection().prepareCall("{? = call adminExport()}");
			cs.registerOutParameter(1, Types.INTEGER);
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
	public ArrayList<ArrayList<String>> parseAdminExport(ResultSet rs){
		if(rs == null) {
			 return null;
			}
		ArrayList<ArrayList<String>> list = new ArrayList<>();
		int index = 0;
		try {
			while(rs.next()) {
				list.add(new ArrayList<>());
				list.get(index).add(rs.getString(1));
				list.get(index).add(rs.getString(2));
				list.get(index).add(rs.getString(3));
				list.get(index).add(rs.getDate(4).toString());
				list.get(index).add(rs.getInt(5)+"");
				list.get(index).add(rs.getString(6));
				list.get(index).add(rs.getInt(7)+"");
				list.get(index).add(rs.getDate(8).toString());
				list.get(index).add(rs.getString(9));
				list.get(index).add(rs.getString(10));
				list.get(index).add(rs.getDate(11).toString());
				list.get(index).add(rs.getString(12));
				list.get(index).add(rs.getInt(13)+"");
				list.get(index).add(rs.getDate(14).toString());
				index++;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}
}
