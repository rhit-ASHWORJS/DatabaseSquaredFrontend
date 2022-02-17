package UI;

import java.awt.event.ActionEvent;

import javax.swing.JFrame;
import javax.swing.JTable;

import CRUD.FullCRUD;

public class AdminCSVListener extends CSVListener {
	FullCRUD fc;
	public AdminCSVListener(JFrame frame, JTable dataTable, FullCRUD fc) {
		super(frame, dataTable);
		this.fc = fc;
		// TODO Auto-generated constructor stub
	}
	@Override
	public void actionPerformed(ActionEvent arg0) {
		String[][] data = fc.getAdminOutput().stream().map(u -> u.toArray(new String[0])).toArray(String[][]::new);
		String[] noNeeded = {"Database","DBMS","Description","Database Date Created","Number of Tables","Company Name","Company Employees ","Company Founded","DBMS Languauge","DBMS Type","DBMS Date Created","DBMS Maunfacturer","Manf Employee count","Manf Datefounded"};
		
		this.dataTable = new JTable(data, noNeeded);
		super.actionPerformed(arg0);
	}
}
