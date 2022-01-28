package UI;

import javax.swing.*;
import javax.swing.table.TableColumn;

import CRUD.FullCRUD;

import java.awt.*;
import java.awt.event.*;
import java.lang.Exception;
import java.util.ArrayList;
import java.util.Vector;

public class UIGuest extends JFrame {
	
	JScrollPane dataPane;
	JTable dataTable;
	FullCRUD fc;

	UIGuest(FullCRUD fc) {
		this.fc = fc;
		this.setSize(700, 700);

		// Make the header panel
		JPanel headerPanel = new JPanel(new GridLayout(1, 2));
		JLabel currentUserLabel = new JLabel();
		currentUserLabel.setText("Current User: Guest");
		currentUserLabel.setFont(new Font("Serif", Font.PLAIN, 20));
		JLabel currentViewLabel = new JLabel();
		currentViewLabel.setText("Current View: Data View");
		currentViewLabel.setFont(new Font("Serif", Font.PLAIN, 20));
		headerPanel.add(currentUserLabel);
		headerPanel.add(currentViewLabel);
		headerPanel.setBackground(Color.gray);

		// Make the filter panel
		JPanel filterPanel = new JPanel();
		// Make the data display panel
		JPanel dataDisplayPanel = new JPanel();
		this.setDataReviews();
		dataDisplayPanel.add(dataPane);
//		dataTable = new
		// Make the interaction panel
		JPanel interactionPanel = new JPanel();
		JButton refreshButton = new JButton("REFRESH TABLE");
		refreshButton.addActionListener(new RefreshListener());
		interactionPanel.add(refreshButton);
		JButton csvButton = new JButton("GENERATE CSV REPORT");
		csvButton.addActionListener(new CSVListener());
		interactionPanel.add(csvButton);
		// Put all the panels in the frame

		add(headerPanel, BorderLayout.NORTH);
		
		add(dataDisplayPanel, BorderLayout.EAST);

		add(interactionPanel, BorderLayout.SOUTH);
	}

	public void setVisibility(boolean visible) {
		this.setVisible(visible);
	}
	
	int tableWidth = 100;
	
	private void setDataReviews() {
		String[] cols = fc.getListedReviewsHeader;
		ArrayList<ArrayList<String>> reviewsData = fc.getListedReviews();
		//One-line Arraylist to string conversion found here: https://stackoverflow.com/questions/10043209/convert-arraylist-into-2d-array-containing-varying-lengths-of-arrays
		String[][] data = reviewsData.stream().map(u -> u.toArray(new String[0])).toArray(String[][]::new);
		this.setTableData(data, cols);
	}
	
	private void setTableData(String[][] data, String[] cols)
	{
		int numCols = cols.length;
		int colWidth = tableWidth / numCols;
		dataTable = new JTable(data, cols);
		TableColumn column = null;
		for (int i = 0; i < numCols; i++) {
			column = dataTable.getColumnModel().getColumn(i);
			if (i == 2) {
				column.setPreferredWidth(colWidth); //third column is bigger
			} else {
				column.setPreferredWidth(colWidth);
			}
		}
		dataPane = new JScrollPane(dataTable);
		dataTable.setFillsViewportHeight(true);
	}

	class CSVListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent arg0) {
			System.out.println("CSV");

		}
	}

	class RefreshListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent arg0) {
			System.out.println("REFRESH");

		}
	}
}
