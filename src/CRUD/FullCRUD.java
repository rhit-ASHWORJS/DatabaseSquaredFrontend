package CRUD;

import java.sql.Date;
import java.sql.ResultSet;
import java.util.ArrayList;

import databasesquared.services.DatabaseConnectionService;

/**
 * this is a class that hold all the CRUD operatiosn
 * 
 * @author slatere
 *
 */
public class FullCRUD {

	private DatabaseConnectionService dbService = null;
	private CompanyCRUD company;
	private DatabaseCRUD database;
	private DBMSCRUD DBMS;
	private ReviewCRUD review;
	private ReviewerCRUD reviewer;
	private ReviewListCRUD reviewList;
	private FilterCRUD filters;
	private CVSImportCRUD cvsImport;

	public FullCRUD(DatabaseConnectionService dbService) {
		this.dbService = dbService;
		// TODO Auto-generated constructor stub
		this.company = new CompanyCRUD(dbService);
		this.database = new DatabaseCRUD(dbService);
		this.DBMS = new DBMSCRUD(dbService);
		this.review = new ReviewCRUD(dbService);
		this.reviewer = new ReviewerCRUD(dbService);
		this.reviewList = new ReviewListCRUD(dbService);
		this.filters = new FilterCRUD(dbService);
		this.cvsImport = new CVSImportCRUD(dbService);
	}

	public void closeConnection() {
		dbService.closeConnection();
	}

	/**
	 * Add a company to the database
	 * 
	 * @param name
	 * @param numEmployees
	 * @param dateFounded
	 * @return ID of new company if sussesfull, -1 if did not work, -2 : company was
	 *         null, -3:numEmplyees was null, -4 company already existed
	 */
	public int addCompany(String name, int numEmployees, Date dateFounded) {
		return this.company.addCompany(name, numEmployees, dateFounded);
	}

	/**
	 * update a company info
	 * 
	 * @param id
	 * @param name         can be null
	 * @param numEmployees -1 for null
	 * @return 0 if successful, -1 if error, 1:ID was null, 2:Company does not exist
	 */
	public int updateCompanyInfo(int id, String name, int numEmployees) {
		return this.company.updateCompanyInfo(id, name, numEmployees);
	}

	public String[] getCompanyInfoHeader = { "Company", "Number of Employees", "Date Founded" };

	/**
	 * 
	 * @param id
	 * @return String list of the table, null if there is an error
	 */
	public ArrayList<ArrayList<String>> getCompanyInfo(int id) {
		return this.company.parseCompanyInfo(this.company.getCompanyInfo(id));
	}

	/**
	 * adds a database to to the table
	 * 
	 * @param name
	 * @param DBMSName
	 * @param tableCount
	 * @param date
	 * @param description nullable
	 * @return ID of added database, -1 if did not work -2:Name is null, -3:DBMS is
	 *         null, -4:table number is null, -5:Database already exists
	 */
	public int addDatabase(String name, String DBMSName, int tableCount, Date date, String description) {
		return this.database.addDatabase(name, DBMSName, tableCount, date, description);
	}

	/**
	 * update info on a database
	 * 
	 * @param id
	 * @param name        nullable
	 * @param description nullable
	 * @param tableCount  -1 for null
	 * @return 0 if successful, -1 if error 1: ID is null, 2:Does not exist
	 */
	public int updateDatabase(int id, String name, String description, int tableCount) {
		return this.database.updateDatabase(id, name, description, tableCount);
	}

	/**
	 * deletes a database from the table
	 * 
	 * @param id
	 * @return 0 if successful, -1 if error 1: ID is null, 2:Does not exist
	 */
	public int deleteDatabase(int id) {
		return this.database.deleteDatabase(id);
	}

	/**
	 * add a DMBS to the table
	 * 
	 * @param name
	 * @param MID
	 * @param language
	 * @param type
	 * @param dateCreated can be null
	 * @return 0 if successful, -1 if error 1:Name is null, 2:Manf ID is null, 3:
	 *         Language is null, 4:Type is null, 5: Already exists
	 */
	public int addDBMS(String name, int MID, String language, String type, Date dateCreated) {
		return this.DBMS.addDBMS(name, MID, language, type, dateCreated);
	}

	/**
	 * update a DMBS in the table
	 * 
	 * @param name
	 * @param MID
	 * @param language nullable
	 * @param type     nullable
	 * @return 0 if successful, -1 if error 1:Name is null, 2:Manf ID is null,
	 *         3:DBMS does not exist, 4:do not have permission to do this
	 */
	public int updateDBMS(String name, int MID, String language, String type) {
		return this.DBMS.updateDBMS(name, MID, language, type);
	}

	/**
	 * deletes a DBMS from the table
	 * 
	 * @param name
	 * @param MID
	 * @return 0 if successful, -1 if error 1:Name is null, 2:Manf ID is null,
	 *         3:DBMS does not exist, 4:do not have permission to do this
	 */
	public int deleteDBMS(String name, int MID) {
		return this.DBMS.deleteDBMS(name, MID);
	}

	/**
	 * adds a review to a ReviewList from a user
	 * 
	 * @param username
	 * @param RLID       ReviewList ID
	 * @param DBMS
	 * @param score
	 * @param reviewText can be null
	 * @return 0 if successful, -1 if error 1:Reviewer name is null, 2:ListID is
	 *         null, 3:DBMS name is null, 4: already exists, 5:Do not have
	 *         permission
	 */
	public int addReview(String username, String RLID, String DBMS, double score, String reviewText) {
		return this.review.addReview(username, RLID, DBMS, score, reviewText);
	}

	/**
	 * update a review from a reviewer
	 * 
	 * @param username
	 * @param RLID       ReviewList ID
	 * @param DBMS
	 * @param score      -1 is null
	 * @param reviewText can be null
	 * @return 0 if successful, -1 if error 1:Reviewer name is null, 2:ListID is
	 *         null, 3:DBMS name is null, 4: already exists, 5:Do not have
	 *         permission
	 */
	public int updateReview(String username, String RLID, String DBMS, double score, String reviewText) {
		return this.review.updateReview(username, RLID, DBMS, score, reviewText);
	}

	/**
	 * deletes a review by a user from the database
	 * 
	 * @param username
	 * @param RLID     reviewList ID
	 * @param DBMS
	 * @return 0 if successful, -1 if error 1:Reviewer name is null, 2:ListID is
	 *         null, 3:DBMS name is null, 4: does not exists, 5:Do not have
	 *         permission
	 */
	public int deleteReview(String username, String RLID, String DBMS) {
		return this.review.deleteReview(username, RLID, DBMS);
	}

	public String[] getReviewsHeader = { "DBMS", "Score", "Text", "Review List name" };

	/**
	 * 
	 * @param username
	 * @return String list of the table, null if there is an error
	 */
	public ArrayList<ArrayList<String>> getReviews(String username) {
		return this.review.parceReviews(this.review.getReviews(username));
	}

	public String[] getListedReviewsHeader = { "Username", "DBMS", "Company", "Score", "Review Text" };

	/**
	 * defult list of the database
	 * 
	 * @return String list of the table, null if there is an error
	 */
	public ArrayList<ArrayList<String>> getListedReviews() {
		return this.review.parceListedReviews(this.review.getListedReviews());
	}

	/**
	 * Adds a reviewer to the database
	 * 
	 * @param Username
	 * @param YoE
	 * @return 0 if successful, -1 if error 1:name is null, 2:Experience is null,
	 *         3:already exists
	 */
	public int addReviewer(String Username, Date YoE) {
		return this.reviewer.addReviewer(Username, YoE);
	}

	/**
	 * adds a review list to the databse
	 * 
	 * @param reviewer
	 * @param listName
	 * @param dateCreated can be null
	 * @return ID of the new reviewList, -1 if it fails -2:Reviewer name is null,
	 *         -3:List name is null, -4:already exists
	 */
	public int addReviewList(String reviewer, String listName, Date dateCreated) {
		return this.reviewList.addReviewList(reviewer, listName, dateCreated);
	}

	/**
	 * updates a ReviewList in the database
	 * 
	 * @param username
	 * @param RLID     ReviewList ID
	 * @param name
	 * @return 0 if successful, -1 if error 1:Reviewer name is null, 2:List ID is
	 *         null, 3:list name is null, 4:List does not exist
	 */
	public int updateReviewList(String username, int RLID, String name) {
		return this.reviewList.updateReviewList(username, RLID, name);
	}

	/**
	 * deletes a review list from of a users
	 * 
	 * @param username
	 * @param RLID     ReviewList ID
	 * @return 0 if successful, -1 if error 1:Reviewer name is null, 2:List ID is
	 *         null, 3:List does not exist
	 */
	public int deleteReviewList(String username, String RLName) {
		return this.reviewList.deleteReviewList(username, RLName);
	}

	public String[] getReviewListHeader = { "Name", "Date Created" };

	/**
	 * 
	 * @param username
	 * @return String list of the table, null if there is an error
	 */
	public ArrayList<ArrayList<String>> getReviewList(String username) {
		return this.reviewList.parceReviewList(this.reviewList.getReviewList(username));
	}

	public String[] getFilteredReviewsHeader = { "Name", "DBMS", "Company", "Score", "Text" };

	/**
	 * all are nullable
	 * 
	 * @param reviewer
	 * @param DBMS
	 * @param company
	 * @param score    -1 for null
	 * @param RLname
	 * @return String list of the table, null if there is an error
	 */
	public ArrayList<ArrayList<String>> filterReviews(String reviewer, String DBMS, String company, int score,
			String RLname) {
		return this.filters.parceFilteredReviews(this.filters.filterReviews(reviewer, DBMS, company, score, RLname));
	}

	public String[] getFilteredDBMSHeader = { "DBMS", "Company", "Number of Databases", "Average Score" };

	/**
	 * all are nullable
	 * 
	 * @param company
	 * @param score   -1 for null
	 * @return String list of the table, null if there is an error
	 */
	public ArrayList<ArrayList<String>> filterDBMS(String company, int score) {
		return this.filters.parceFilteredDBMS(this.filters.filterDBMS(company, score));
	}

	public String[] getFilteredCompaniesHeader = { "Company", "Number of Employees", "DBMS Used", "DBMS Created" };

	/**
	 * all are nullable
	 * 
	 * @param usedDBMS
	 * @param madeDBMS
	 * @return String list of the table, null if there is an error
	 */
	public ArrayList<ArrayList<String>> filterCompanies(String usedDBMS, String madeDBMS) {
		return this.filters.parceFilteredCompanies(this.filters.filterCompanies(usedDBMS, madeDBMS));
	}
	/**
	 * 
	 * @param dbName
	 * @param DBMSName
	 * @param desc
	 * @param dbDate
	 * @param numOfTables
	 * @param comapanyName
	 * @param numEmployees
	 * @param conpDate
	 * @param DBMSLang
	 * @param DBMSType
	 * @param DBMSDate
	 * @param DBMSManf
	 */
	public void addImportRow(String dbName, String DBMSName, String desc, Date dbDate, int numOfTables, String comapanyName, int numEmployees, Date conpDate, String DBMSLang, String DBMSType, Date DBMSDate, String DBMSManf,int manfEmployees, Date manfDate) {
		this.cvsImport.addRow(dbName,DBMSName,desc,dbDate,numOfTables,comapanyName,numEmployees,conpDate,DBMSLang,DBMSType,DBMSDate,DBMSManf,manfEmployees,manfDate);		
	}
}
