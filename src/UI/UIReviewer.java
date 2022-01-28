package UI;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.TableColumn;

import CRUD.FullCRUD;

import java.awt.*;
import java.awt.event.*;
import java.lang.Exception;
import java.util.ArrayList;
import java.util.Vector;

public class UIReviewer extends JFrame {
	
	JScrollPane dataPane;
	JTable dataTable;
	FullCRUD fc;
	JComboBox reportTypes;
	String username;
	
	String[] dataViewOptions = {"Reviews", "DBMS", "Companies"};

	public static final int MAXIMUM_FILTER_INPUT = 20;
	UIReviewer(FullCRUD fc, String username) {
		this.username = username;
		this.fc = fc;
		this.setSize(800, 550);
		this.setTitle("Reviewer View");

		// Make the header panel
		JPanel headerPanel = new JPanel(new GridLayout(1, 2));
		JLabel currentUserLabel = new JLabel();
		currentUserLabel.setText("Current User: " + username);
		currentUserLabel.setFont(new Font("Serif", Font.PLAIN, 20));
		JLabel currentViewLabel = new JLabel();
		currentViewLabel.setText("Current View: Data View");
		currentViewLabel.setFont(new Font("Serif", Font.PLAIN, 20));
		headerPanel.add(currentUserLabel);
		headerPanel.add(currentViewLabel);
		headerPanel.setBackground(Color.gray);

		// Make the filter panel
		JPanel filterPanel = new JPanel(new GridLayout(12, 1));
		filterPanel.setBorder(new EmptyBorder(0,20,0,20));
		JLabel filtersLabel = new JLabel("Filters:");
		filtersLabel.setFont(new Font("Serif", Font.PLAIN, 20));
		filterPanel.add(filtersLabel);
		
		filterPanel.add(new JLabel("Report Type"));
		
		reportTypes = new JComboBox(dataViewOptions);
		filterPanel.add(reportTypes);
		
		filterPanel.add(new JLabel("DBMS"));
		filterPanel.add(new JTextField(MAXIMUM_FILTER_INPUT));
		filterPanel.add(new JLabel("Company"));
		filterPanel.add(new JTextField(MAXIMUM_FILTER_INPUT));
		filterPanel.add(new JLabel("Reviewer"));
		filterPanel.add(new JTextField(MAXIMUM_FILTER_INPUT));
		filterPanel.add(new JLabel("Minimum Score"));
		filterPanel.add(new JTextField(MAXIMUM_FILTER_INPUT));

		// Make the data display panel
		this.setDataReviews();
//		dataTable = new
		// Make the interaction panel
		JPanel interactionPanel = new JPanel();
		JButton refreshButton = new JButton("REFRESH TABLE");
		refreshButton.addActionListener(new RefreshListener());
		interactionPanel.add(refreshButton);
		JButton csvButton = new JButton("GENERATE CSV REPORT");
		csvButton.addActionListener(new CSVListener());
		interactionPanel.add(csvButton);
		JButton viewReviewListsButton = new JButton("VIEW MY REVIEW LISTS");
		viewReviewListsButton.addActionListener(new ReviewListsListener());
		interactionPanel.add(viewReviewListsButton);
		
		// Put all the panels in the frame

		add(headerPanel, BorderLayout.NORTH);

		add(filterPanel, BorderLayout.WEST);

		add(interactionPanel, BorderLayout.SOUTH);
		this.pack();
	}

	public void setVisibility(boolean visible) {
		this.setVisible(visible);
	}
	
	
	private void setDataReviews() {
		String[] cols = fc.getListedReviewsHeader;
		ArrayList<ArrayList<String>> reviewsData = fc.getListedReviews();
		//One-line Arraylist to string conversion found here: https://stackoverflow.com/questions/10043209/convert-arraylist-into-2d-array-containing-varying-lengths-of-arrays
		String[][] data = reviewsData.stream().map(u -> u.toArray(new String[0])).toArray(String[][]::new);
		this.setTableData(data, cols);
	}
	
	private void refreshUI() {
		this.pack();
	}
	
	private void setTableData(String[][] data, String[] cols)
	{
		int numCols = cols.length;
		if(dataPane != null)
		{
			dataPane.setVisible(false);
		}
		dataTable = new JTable(data, cols);
		dataPane = new JScrollPane(dataTable);
		dataTable.setFillsViewportHeight(true);
		add(dataPane, BorderLayout.EAST);
		this.pack();
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
			String selected = reportTypes.getSelectedItem().toString();
			System.out.println("REFRESH " + selected);

			if(selected.equals("Reviews"))
			{
				setDataReviews();
				
			}
		}
	}
	
	class ReviewListsListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent arg0) {
			UIReviewList reviewList = new UIReviewList(fc, username);
			reviewList.setVisibility(true);
		}
	}
}
