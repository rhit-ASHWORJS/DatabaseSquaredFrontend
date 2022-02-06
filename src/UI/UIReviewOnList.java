package UI;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.TableColumn;

import CRUD.FullCRUD;

import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.lang.Exception;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Vector;
import java.sql.Date;

public class UIReviewOnList extends JFrame {
	//DB interaction
	FullCRUD fc;

	//UI Elements
	JScrollPane dataPane;
	JTable dataTable;
	JComboBox reviewListSelections;
	String username;
	String reviewListName;
	CSVListener csvListener;
	
	//Data options for dropdown
	String[] dataViewOptions = {"Reviews", "DBMS", "Companies"};

	//Maximum allowed length of text box entry
	public static final int MAXIMUM_FILTER_INPUT = 20;
	UIReviewOnList(FullCRUD fc, String username, String reviewListName) {
		//Make the UI look okay
		try
        {
            UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
        }    
        catch (Exception e)
        {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }   
		
		csvListener = new CSVListener(this, null);
		
		//Save DB interaction & set screen size
		this.username = username;
		this.reviewListName = reviewListName;
		this.fc = fc;
		this.setSize(800, 550);
		this.setTitle("Reviewer View");

		// Make the header panel
		JPanel headerPanel = new JPanel(new GridLayout(1, 2));
		
		//Make current user & current view labels for header
		JLabel currentUserLabel = new JLabel();
		currentUserLabel.setText("Current User: " + username);
		currentUserLabel.setFont(new Font("Serif", Font.PLAIN, 20));
		JLabel currentViewLabel = new JLabel();
		currentViewLabel.setText("Current View: Review List "+reviewListName+" View");
		currentViewLabel.setFont(new Font("Serif", Font.PLAIN, 20));
		
		//Add labels to header
		headerPanel.add(currentUserLabel);
		headerPanel.add(currentViewLabel);
		headerPanel.setBackground(Color.gray);

		// Make the filter panel
		JPanel filterPanel = new JPanel(new GridLayout(12, 1));
		filterPanel.setBorder(new EmptyBorder(0,20,0,20));
		
		//Make the filter panel label
		JLabel filtersLabel = new JLabel("Filters:");
		filtersLabel.setFont(new Font("Serif", Font.PLAIN, 20));
		filterPanel.add(filtersLabel);
		
		filterPanel.add(new JLabel("Review Selector"));
		reviewListSelections = new JComboBox(getMyReviewsOnLists());
		filterPanel.add(reviewListSelections);


		// Set display to default
		this.setDataReviewOnList();

		// Make the interaction panel
		JPanel interactionPanel = new JPanel();
		
		// Make interaction panel buttons
		JButton refreshButton = new JButton("REFRESH TABLE");
		refreshButton.addActionListener(new RefreshListener());
		interactionPanel.add(refreshButton);
		
		JButton csvButton = new JButton("GENERATE CSV REPORT");
		csvButton.addActionListener(csvListener);
		interactionPanel.add(csvButton);
		
		JButton createReviewButton = new JButton("CREATE REVIEW");
		createReviewButton.addActionListener(new CreateReviewListener());
		interactionPanel.add(createReviewButton);
		
		JButton deleteListButton = new JButton("DELETE REVIEW");
		deleteListButton.addActionListener(new DeleteReviewListener());
		interactionPanel.add(deleteListButton);
		
		// Put all the panels in the frame
		add(headerPanel, BorderLayout.NORTH);
		add(filterPanel, BorderLayout.WEST);
		add(interactionPanel, BorderLayout.SOUTH);
		this.pack();
	}

	public void setVisibility(boolean visible) {
		this.setVisible(visible);
	}
	
	
	private void setDataReviewOnList() {
		String[] cols = fc.getFilteredReviewsHeader;
		ArrayList<ArrayList<String>> reviewsData = fc.filterReviews(username, null, null, -1, reviewListName);
		if(reviewsData != null)
		{
			//One-line Arraylist to string conversion found here: https://stackoverflow.com/questions/10043209/convert-arraylist-into-2d-array-containing-varying-lengths-of-arrays
			String[][] data = reviewsData.stream().map(u -> u.toArray(new String[0])).toArray(String[][]::new);
			this.setTableData(data, cols);
		}
		else
		{
			this.setTableData(new String[0][0], new String[0]);
		}
	}
	
	private String[] getMyReviewsOnLists() {
		String[] cols = fc.getFilteredReviewsHeader;
		ArrayList<ArrayList<String>> reviewsData = fc.filterReviews(username, null, null, -1, reviewListName);
		if(reviewsData == null)
		{
			return null;
		}
		
		String[] myReviewLists = new String[reviewsData.size()];
		int i = 0;
		for(ArrayList<String> data : reviewsData)
		{
			myReviewLists[i] = data.get(1);
			i++;
		}
		return myReviewLists;
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
		csvListener.setTable(dataTable);
		add(dataPane, BorderLayout.EAST);
		updateComboBox();
		this.pack();
	}
	
	private void updateComboBox() {
		DefaultComboBoxModel<String> model = new DefaultComboBoxModel<>(getMyReviewsOnLists());
		reviewListSelections.setModel(model);
	}

	class RefreshListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent arg0) {
			setDataReviewOnList();
		}
	}
	
	class CreateReviewListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent arg0) {
//			String input = JOptionPane.showInputDialog("Enter Name For New Review List");
//			fc.addReviewList(username, input, new Date(Calendar.getInstance().getTimeInMillis()));
//			setDataReviewOnList();
			System.out.println("Create");
		}
	}
	
	class DeleteReviewListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent arg0) {
//			String listToDelete = (String) reviewListSelections.getSelectedItem();
//			fc.deleteReviewList(username, listToDelete);
//			setDataReviewOnList();
			System.out.println("Delete");
		}
		
	}
}
