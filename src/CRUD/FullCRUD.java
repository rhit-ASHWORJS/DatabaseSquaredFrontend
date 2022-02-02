package CRUD;

import java.sql.Date;
import java.sql.ResultSet;
import java.util.ArrayList;

import databasesquared.services.DatabaseConnectionService;

/**
 * this is a class that hold all the CRUD operatiosn
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
	}
	/**
	 * 
	 * @param name
	 * @param numEmployees
	 * @param dateFounded
	 * @return the conpany ID -1 if there is an error
	 */
	public int addCompany(String name, int numEmployees, Date dateFounded) {
		return this.company.addCompany(name, numEmployees, dateFounded);
	}
	public int updateCompanyInfo(int id, String name, int numEmployees) {
		return this.company.updateCompanyInfo(id, name, numEmployees);
	}
	public String[] getCompanyInfoHeader = {"Company", "Number of Employees", "Date Founded"};
	public ArrayList<ArrayList<String>>  getCompanyInfo(int id) {
		return this.company.parseCompanyInfo(this.company.getCompanyInfo(id));
	}
	
	/**
	 * 
	 * @param name
	 * @param DBMSName
	 * @param tableCount
	 * @param date
	 * @param description
	 * @return database ID return -1 if there is an error
	 */
	public int addDatabase(String name, String DBMSName, int tableCount, Date date, String description) {
		return this.database.addDatabase(name, DBMSName, tableCount, date, description);
	}
	public int updateDatabase(int id, String name, String description, int tableCount) {
		return this.database.updateDatabase(id, name, description, tableCount);
	}
	public int deleteDatabase(int id) {
		return this.database.deleteDatabase(id);
	}
	public int addDBMS(String name, int MID, String language, String type, Date dateCreated) {
		return this.DBMS.addDBMS(name, MID, language, type, dateCreated);
	}
	public int updateDBMS(String name, int MID, String language, String type) {
		return this.DBMS.updateDBMS(name, MID, language, type);
	}
	public int deleteDBMS(String name, int MID) {
		return this.DBMS.deleteDBMS(name, MID);
	}
	public int addReview(String username, int RLID, String DBMS, double score, String reviewText) {
		return this.review.addReview(username, RLID, DBMS, score, reviewText);
	}
	public int updateReview(String username, int RLID, String DBMS, double score,String reviewText) {
	return this.review.updateReview(username, RLID, DBMS, score, reviewText);	
	}
	public int deleteReview(String username, int RLID, String DBMS) {
		return this.review.deleteReview(username, RLID, DBMS);
	}
	public String[] getReviewsHeader = {"DBMS", "Score", "Text", "Review List name"};
	public  ArrayList<ArrayList<String>> getReviews(String username){
		return this.review.parceReviews(this.review.getReviews(username));
	}
	
	public String[] getListedReviewsHeader = {"Username", "DBMS", "Company", "Score", "Review Text"};
	public  ArrayList<ArrayList<String>> getListedReviews() {
		return this.review.parceListedReviews(this.review.getListedReviews());
	}
	
	public int addReviewer(String Username, Date YoE) {
		return this.reviewer.addReviewer(Username, YoE);
	}
	/**
	 * 
	 * @param reviewer
	 * @param listName
	 * @param dateCreated
	 * @return ReviewList ID, -1 if it fails 
	 */
	public int addReviewList(String reviewer, String listName, Date dateCreated) {
		return this.reviewList.addReviewList(reviewer, listName, dateCreated);
	}
	public int updateReviewList(String username, int RLID, String name) {
		return this.reviewList.updateReviewList(username, RLID, name);
	}
	public int deleteReviewList(String username, int RLID) {
		return this.reviewList.deleteReviewList(username, RLID);
	}
	
	public String[] getReviewListHeader = {"Name", "Date Created"};
	public  ArrayList<ArrayList<String>> getReviewList(String username){
		return this.reviewList.parceReviewList(this.reviewList.getReviewList(username));
	}
	
	public String[] getFilteredReviewsHeader = {"Name", "DBMS","Company","Score","Text"};
	public ArrayList<ArrayList<String>> filterReviews(String reviewer, String DBMS, String company, int score) {
		return this.filters.parceFilteredReviews(this.filters.filterReviews(reviewer, DBMS, company, score));
	}
	public String[] getFilteredDBMSHeader = {"DBMS", "Company","Number of Databases","Average Score"};
	public ArrayList<ArrayList<String>> filterDBMS(String company, int score) {
		return this.filters.parceFilteredDBMS(this.filters.filterDBMS(company, score));
	}
	public String[] getFilteredCompaniesHeader = {"Company", "Number of Employees", "DBMS Used", "DBMS Created"};
	public ArrayList<ArrayList<String>> filterCompanies(String usedDBMS,String madeDBMS) {
		return this.filters.parceFilteredCompanies(this.filters.filterCompanies(usedDBMS, madeDBMS));
	}
	
}
