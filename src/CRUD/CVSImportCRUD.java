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
	
	
	public void addRow(String dbName, String DBMSName, String desc, Date dbDate, int numOfTables, String comapanyName, int numEmployees, Date conpDate, String DBMSLang,String DBMSType, Date DBMSDate, String DBMSManf) {
		try {
			CallableStatement cs = dbService.getConnection().prepareCall("{? = call addCompany(?,?,?,?)}");
			cs.registerOutParameter(1, Types.INTEGER);
			cs.registerOutParameter(5, Types.INTEGER);
			cs.execute();
			int returnValue = cs.getInt(1);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();	
		}
	}
}
