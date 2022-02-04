package UI;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.TableColumn;

import CRUD.FullCRUD;

import java.awt.*;
import java.awt.event.*;
import java.lang.Exception;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Vector;
import java.sql.Date;

public class UIReviewOnList extends JFrame {
	
	JScrollPane dataPane;
	JTable dataTable;
	FullCRUD fc;
	JComboBox reviewListSelections;
	String username;
	String reviewListName;
	
	String[] dataViewOptions = {"Reviews", "DBMS", "Companies"};

	public static final int MAXIMUM_FILTER_INPUT = 20;
	UIReviewOnList(FullCRUD fc, String username, String reviewListName) {
		try
        {
            UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
        }    
        catch (Exception e)
        {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }   
		
		this.username = username;
		this.fc = fc;
		this.reviewListName = reviewListName;
		this.setSize(800, 550);
		this.setTitle("Reviewer View");

		// Make the header panel
		JPanel headerPanel = new JPanel(new GridLayout(1, 2));
		JLabel currentUserLabel = new JLabel();
		currentUserLabel.setText("Current User: " + username);
		currentUserLabel.setFont(new Font("Serif", Font.PLAIN, 20));
		JLabel currentViewLabel = new JLabel();
		currentViewLabel.setText("Current View: Review List "+reviewListName+" View");
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
		
		filterPanel.add(new JLabel("Review Selector"));
		reviewListSelections = new JComboBox(getMyReviewsOnLists());
		filterPanel.add(reviewListSelections);
//		filterPanel.add(new JLabel("Company"));
//		filterPanel.add(new JTextField(MAXIMUM_FILTER_INPUT));
//		filterPanel.add(new JLabel("Reviewer"));
//		filterPanel.add(new JTextField(MAXIMUM_FILTER_INPUT));
//		filterPanel.add(new JLabel("Minimum Score"));
//		filterPanel.add(new JTextField(MAXIMUM_FILTER_INPUT));

		// Make the data display panel
		this.setDataReviewOnList();
//		dataTable = new
		// Make the interaction panel
		JPanel interactionPanel = new JPanel();
		JButton refreshButton = new JButton("REFRESH TABLE");
		refreshButton.addActionListener(new RefreshListener());
		interactionPanel.add(refreshButton);
		JButton csvButton = new JButton("GENERATE CSV REPORT");
		csvButton.addActionListener(new CSVListener());
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
		//One-line Arraylist to string conversion found here: https://stackoverflow.com/questions/10043209/convert-arraylist-into-2d-array-containing-varying-lengths-of-arrays
		String[][] data = reviewsData.stream().map(u -> u.toArray(new String[0])).toArray(String[][]::new);
		this.setTableData(data, cols);
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
		add(dataPane, BorderLayout.EAST);
		updateComboBox();
		this.pack();
	}
	
	private void updateComboBox() {
		DefaultComboBoxModel<String> model = new DefaultComboBoxModel<>(getMyReviewsOnLists());
		reviewListSelections.setModel(model);
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
