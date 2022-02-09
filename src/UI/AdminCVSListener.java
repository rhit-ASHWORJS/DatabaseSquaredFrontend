package UI;

import java.awt.event.ActionEvent;

import javax.swing.JFrame;
import javax.swing.JTable;

import CRUD.FullCRUD;

public class AdminCVSListener extends CSVListener {
	FullCRUD fc;
	public AdminCVSListener(JFrame frame, JTable dataTable, FullCRUD fc) {
		super(frame, dataTable);
		this.fc = fc;
		// TODO Auto-generated constructor stub
	}
	@Override
	public void actionPerformed(ActionEvent arg0) {
		String[][] data = fc.getAdminOutput().stream().map(u -> u.toArray(new String[0])).toArray(String[][]::new);
		String[] noNeeded = {"1","2","3","4","5","6","7 ","8","9","10","11","12","13","14"};
		
		this.dataTable = new JTable(data, noNeeded);
		super.actionPerformed(arg0);
	}
}
