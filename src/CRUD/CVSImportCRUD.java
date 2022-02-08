package CRUD;

import java.sql.CallableStatement;
import java.sql.Date;
import java.sql.SQLException;
import java.sql.Types;

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
}
