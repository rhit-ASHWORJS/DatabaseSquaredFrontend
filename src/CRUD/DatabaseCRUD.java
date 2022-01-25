package CRUD;

import java.util.ArrayList;

import databasesquared.services.DatabaseConnectionService;

public class DatabaseCRUD {
	private DatabaseConnectionService dbService = null;
	public DatabaseCRUD(DatabaseConnectionService dbService) {
		this.dbService = dbService;
		// TODO Auto-generated constructor stub
	}
	
	public boolean addDatabase() {
		return false;
	}
	
	public boolean updateDatabase() {
		return false;
	}
	public boolean deleteDatabase() {
		return false;
	}
	public ArrayList<String> getDatabase(){
		return null;
	}
}
