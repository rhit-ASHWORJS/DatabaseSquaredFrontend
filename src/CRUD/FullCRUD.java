package CRUD;

import java.sql.Date;
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
	public FullCRUD(DatabaseConnectionService dbService) {
		this.dbService = dbService;
		// TODO Auto-generated constructor stub
		this.company = new CompanyCRUD(dbService);
		this.database = new DatabaseCRUD(dbService);
		this.DBMS = new DBMSCRUD(dbService);
		this.review = new ReviewCRUD(dbService);
		this.reviewer = new ReviewerCRUD(dbService);
		this.reviewList = new ReviewListCRUD(dbService);
	}
	public int addCompany(String name, int numEmployees, Date dateFounded) {
		return this.company.addCompany(name, numEmployees, dateFounded);
	}
	public boolean updateCompanyInfo(int id, String name, int numEmployees) {
		return this.company.updateCompanyInfo(id, name, numEmployees);
	}
	public ArrayList<ArrayList<String>>  getCompanyInfo(int id) {
		return this.company.parseCompanyInfo(this.company.getCompanyInfo(id));
	}
	
	public int addDatabase(String name, String DBMSName, int tableCount, Date date, String description) {
		return this.database.addDatabase(name, DBMSName, tableCount, date, description);
	}
	public boolean updateDatabase(int id, String name, String description, int tableCount) {
		return this.database.updateDatabase(id, name, description, tableCount);
	}
	public boolean deleteDatabase(int id) {
		return this.database.deleteDatabase(id);
	}
	public boolean addDBMS(String name, int MID, String language, String type, Date dateCreated) {
		return this.DBMS.addDBMS(name, MID, language, type, dateCreated);
	}
	public boolean updateDBMS(String name, int MID, String language, String type) {
		return this.DBMS.updateDBMS(name, MID, language, type);
	}
	public boolean deleteDBMS(String name, int MID) {
		return this.DBMS.deleteDBMS(name, MID);
	}
	public boolean addReview(String username, int RLID, String DBMS, double score, String reviewText) {
		return this.review.addReview(username, RLID, DBMS, score, reviewText);
	}
	public boolean updateReview(String username, int RLID, String DBMS, double score,String reviewText) {
	return this.review.updateReview(username, RLID, DBMS, score, reviewText);	
	}
	public boolean deleteReview(String username, int RLID, String DBMS) {
		return this.review.deleteReview(username, RLID, DBMS);
	}
	public  ArrayList<ArrayList<String>> getReviews(String username){
		return this.review.parceReviews(this.review.getReviews(username));
	}
	
	public String[] getListedReviewsHeader = {"Username", "DBMS", "Company", "Score", "Review Text"};
	public  ArrayList<ArrayList<String>> getListedReviews() {
		return this.review.parceListedReviews(this.review.getListedReviews());
	}
	public boolean addReviewer(String Username, Date YoE) {
		return this.reviewer.addReviewer(Username, YoE);
	}
	public int addReviewList(String reviewer, String listName, Date dateCreated) {
		return this.reviewList.addReviewList(reviewer, listName, dateCreated);
	}
	public boolean updateReviewList(String username, int RLID, String name) {
		return this.reviewList.updateReviewList(username, RLID, name);
	}
	public boolean deleteReviewList(String username, int RLID) {
		return this.reviewList.deleteReviewList(username, RLID);
	}
	
	public String[] getReviewListHeader = {"Name", "Date Created"};
	public  ArrayList<ArrayList<String>> getReviewList(String username){
		return this.reviewList.parceReviewList(this.reviewList.getReviewList(username));
	}
}
