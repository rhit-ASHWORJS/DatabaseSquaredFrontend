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

public class UIReviewList extends JFrame {
	//DB interaction
	FullCRUD fc;

	//Current User
	String username;
	
	//UI Elements
	JScrollPane dataPane;
	JTable dataTable;
	JComboBox reviewListSelections;
	CSVListener csvListener;
	
	//Data options for dropdown
	String[] dataViewOptions = {"Reviews", "DBMS", "Companies"};

	//Maximum allowed length of text box entry
	public static final int MAXIMUM_FILTER_INPUT = 20;
	UIReviewList(FullCRUD fc, String username) {
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
		currentViewLabel.setText("Current View: Review Lists View");
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
		
		//Make the Review List Selector Box
		filterPanel.add(new JLabel("Review List Selector"));
		reviewListSelections = new JComboBox(getMyReviewLists());
		filterPanel.add(reviewListSelections);

		//Set default data
		this.setDataReviewList();

		// Make the interaction panel
		JPanel interactionPanel = new JPanel();
		
		//Make the buttons for the interaction panel
		JButton refreshButton = new JButton("REFRESH TABLE");
		refreshButton.addActionListener(new RefreshListener());
		interactionPanel.add(refreshButton);
		
		JButton csvButton = new JButton("GENERATE CSV REPORT");
		csvButton.addActionListener(csvListener);
		interactionPanel.add(csvButton);
		
		JButton createReviewButton = new JButton("CREATE REVIEW LIST");
		createReviewButton.addActionListener(new CreateReviewListener());
		interactionPanel.add(createReviewButton);
		
		JButton editReviewButton = new JButton("EDIT REVIEW LIST");
		editReviewButton.addActionListener(new EditReviewListener());
		interactionPanel.add(editReviewButton);
		
		JButton deleteListButton = new JButton("DELETE REVIEW LIST");
		deleteListButton.addActionListener(new DeleteReviewListener());
		interactionPanel.add(deleteListButton);
		
		// Put all the panels in the frame
		add(headerPanel, BorderLayout.NORTH);
		add(filterPanel, BorderLayout.WEST);
		add(interactionPanel, BorderLayout.SOUTH);
		
		//Make sure everything fits in the frame
		this.pack();
	}
	
	//Logic for getting data of review lists
	private void setDataReviewList() {
		String[] cols = fc.getReviewListHeader;
		ArrayList<ArrayList<String>> reviewsData = fc.getReviewList(username);
		
		//One-line Arraylist to string conversion found here: https://stackoverflow.com/questions/10043209/convert-arraylist-into-2d-array-containing-varying-lengths-of-arrays
		if(reviewsData != null)
		{
			String[][] data = reviewsData.stream().map(u -> u.toArray(new String[0])).toArray(String[][]::new);
			this.setTableData(data, cols);
		}
		else
		{
			this.setTableData(new String[0][0], new String[0]);
		}
	}
	
	//Logic for getting a array of strings of review lists (used to update dropdown)
	private String[] getMyReviewLists() {
		String[] cols = fc.getReviewListHeader;
		ArrayList<ArrayList<String>> reviewsData = fc.getReviewList(username);
		if(reviewsData == null)
		{
			return null;
		}
		
		String[] myReviewLists = new String[reviewsData.size()];
		int i = 0;
		for(ArrayList<String> data : reviewsData)
		{
			myReviewLists[i] = data.get(0);
			i++;
		}
		return myReviewLists;
	}
	
	//Refreshes User Interface
	private void refreshUI() {
		this.pack();
	}
	
	//Sets the data on the table
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
	
	//Updates the drop-down box
	private void updateComboBox() {
		DefaultComboBoxModel<String> model = new DefaultComboBoxModel<>(getMyReviewLists());
		reviewListSelections.setModel(model);
	}

	//Button logic

	//Refresh button
	class RefreshListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent arg0) {
			setDataReviewList();
		}
	}
	
	//Create button
	class CreateReviewListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent arg0) {
			String input = JOptionPane.showInputDialog("Enter Name For New Review List");
			fc.addReviewList(username, input, new Date(Calendar.getInstance().getTimeInMillis()));
			setDataReviewList();
		}
	}
	
	//Edit button
	class EditReviewListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent arg0) {
			if((String) reviewListSelections.getSelectedItem() == null)
			{
				JOptionPane.showMessageDialog(null, "You must select a review list to edit");
				return;
			}
			UIReviewOnList ui = new UIReviewOnList(fc, username, (String) reviewListSelections.getSelectedItem());
			ui.setVisibility(true);
		}
		
	}
	
	//Delete button
	class DeleteReviewListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent arg0) {
			if((String) reviewListSelections.getSelectedItem() == null)
			{
				JOptionPane.showMessageDialog(null, "You must select a review list to edit");
				return;
			}
			String listToDelete = (String) reviewListSelections.getSelectedItem();
			fc.deleteReviewList(username, listToDelete);
			setDataReviewList();
		}
		
	}
}
