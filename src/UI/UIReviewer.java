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
	//DB interaction
	FullCRUD fc;
	
	//Current User
	String username;
	
	//UI Elements
	JScrollPane dataPane;
	JTable dataTable;
	JComboBox reportTypes;
	JLabel[] filters = new JLabel[4];
	JTextField[] filterText = new JTextField[4];
	
	//Data options for dropdown	
	String[] dataViewOptions = {"Reviews", "DBMS", "Companies"};

	//Maximum allowed length of text box entry
	public static final int MAXIMUM_FILTER_INPUT = 20;
	UIReviewer(FullCRUD fc, String username) {
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
		currentViewLabel.setText("Current View: Data View");
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
		
		//Make the Report Type drop down
		filterPanel.add(new JLabel("Report Type"));
		reportTypes = new JComboBox(dataViewOptions);
		filterPanel.add(reportTypes);
		
		//Make each filter box
		filters[0] = new JLabel("DBMS");
		filterText[0] = new JTextField(MAXIMUM_FILTER_INPUT);
		filters[1] = new JLabel("Company");
		filterText[1] = new JTextField(MAXIMUM_FILTER_INPUT);
		filters[2] = new JLabel("Reviewer");
		filterText[2] = new JTextField(MAXIMUM_FILTER_INPUT);
		filters[3] = new JLabel("Minimum Score");
		filterText[3] = new JTextField(MAXIMUM_FILTER_INPUT);

		//Add the filter boxes to the panel
		for(int i = 0; i < 4; i++)
		{
			filterPanel.add(filters[i]);
			filterPanel.add(filterText[i]);
		}

		// Set the display to default
		this.setDataReviews();

		// Make the interaction panel
		JPanel interactionPanel = new JPanel();
		
		//Make the interaction panel buttons
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
	
	//Makes sure all UI elements update
	private void refreshUI() {
		this.pack();
	}
	
	//Logic for getting 'Reviews' data
	private String[] dataReviewFilters = {"Reviewer", "DBMS", "Company", "Minimum Score"};
	private void setDataReviews() {
		setTextBoxTypes(dataReviewFilters);
		String[] cols = fc.getListedReviewsHeader;
		
		String reviewerFilter = filterText[0].getText();
		String DBMSFilter = filterText[1].getText();
		String CompanyFilter = filterText[2].getText();
		String ScoreFilter = filterText[3].getText();
		int scoreFilter = -1;
		
		if(reviewerFilter.isBlank())
		{
			reviewerFilter = null;
		}
		if(DBMSFilter.isBlank())
		{
			DBMSFilter = null;
		}
		if(CompanyFilter.isBlank())
		{
			CompanyFilter = null;
		}
		if(!ScoreFilter.isBlank())
		{
			scoreFilter = Integer.parseInt(ScoreFilter);
		}
		
		ArrayList<ArrayList<String>> reviewsData = fc.filterReviews(reviewerFilter, DBMSFilter, CompanyFilter, scoreFilter, null);
		if(reviewsData == null)
		{
			return;
		}
		//One-line Arraylist to string conversion found here: https://stackoverflow.com/questions/10043209/convert-arraylist-into-2d-array-containing-varying-lengths-of-arrays
		String[][] data = reviewsData.stream().map(u -> u.toArray(new String[0])).toArray(String[][]::new);
		this.setTableData(data, cols);
	}
	
	//Logic for getting 'DBMS' data
	private String[] DBMSFilters = {"Company", "Minimum Score"};
	private void setDataDBMS() {
		setTextBoxTypes(DBMSFilters);
		String[] cols = fc.getFilteredDBMSHeader;
		
		String CompanyFilter = filterText[0].getText();
		String ScoreFilter = filterText[1].getText();
		int scoreFilter = -1;
		
		if(CompanyFilter.isBlank())
		{
			CompanyFilter = null;
		}
		if(!ScoreFilter.isBlank())
		{
			scoreFilter = Integer.parseInt(ScoreFilter);
		}
		
		
		ArrayList<ArrayList<String>> DBMSData = fc.filterDBMS(CompanyFilter, scoreFilter);
		if(DBMSData == null)
		{
			return;
		}
		String[][] data = DBMSData.stream().map(u -> u.toArray(new String[0])).toArray(String[][]::new);
		this.setTableData(data, cols);
	}
	
	//Logic for getting 'Companies' data
	private String[] CompaniesFilter = {"DBMS Used", "DBMS Created"};
	private void setDataCompanies() {
		setTextBoxTypes(CompaniesFilter);
		String[] cols = fc.getFilteredCompaniesHeader;
		
		String UsedFilter = filterText[0].getText();
		String CreatedFilter = filterText[1].getText();
		
		if(UsedFilter.isBlank())
		{
			UsedFilter = null;
		}
		if(CreatedFilter.isBlank())
		{
			CreatedFilter = null;
		}
		
		ArrayList<ArrayList<String>> CompanyData = fc.filterCompanies(UsedFilter, CreatedFilter);
		if(CompanyData == null)
		{
			return;
		}
		String[][] data = CompanyData.stream().map(u -> u.toArray(new String[0])).toArray(String[][]::new);
		this.setTableData(data, cols);
	}
	
	//Set filter boxes to correct types
	private void setTextBoxTypes(String[] filterStrings)
	{
		for(int i = 0; i < 4; i++)
		{
			if(i < filterStrings.length)
			{
				if(!filters[i].getText().equals(filterStrings[i]))
				{
					filterText[i].setText("");
				}
				filters[i].setText(filterStrings[i]);
				filterText[i].setEnabled(true);
			}
			else
			{
				filters[i].setText("N/A");
				filterText[i].setText("");
				filterText[i].setEnabled(false);
			}
		}
	}
	
	//Set our table's data
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

	
	//Button Interactions
	//CSV Button
	class CSVListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent arg0) {
			System.out.println("CSV");
			
		}
	}
	
	//Refresh Button
	class RefreshListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent arg0) {
			String selected = reportTypes.getSelectedItem().toString();
//			System.out.println("REFRESH " + selected);

			if(selected.equals("Reviews"))
			{
				setDataReviews();
			}
			else if(selected.equals("DBMS"))
			{
				setDataDBMS();
			}
			else if(selected.equals("Companies"))
			{
				setDataCompanies();
			}
		}
	}
	
	//ReviewLists Button
	class ReviewListsListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent arg0) {
			UIReviewList reviewList = new UIReviewList(fc, username);
			reviewList.setVisible(true);
		}
	}
}
