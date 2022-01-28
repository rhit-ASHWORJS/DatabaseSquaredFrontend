import java.sql.Date;
import java.time.LocalDate;
import java.util.Calendar;

import CRUD.FullCRUD;
import UI.UIMain;
import databasesquared.services.DatabaseConnectionService;

public class Main {

	public static void main(String[] args) {
		System.out.println("Connecting...");
//		ApplicationRunner appRunner = new ApplicationRunner();
//		appRunner.runApplication(args);
		DatabaseConnectionService test = new DatabaseConnectionService("titan.csse.rose-hulman.edu","DatabaseSquared");
		test.connect("dbSquareUser", "ilikedatabases");
		FullCRUD fc = new FullCRUD(test);
//		System.out.println(fc.getReviewList("bob"));
//		System.out.println(fc.getListedReviews());
//		System.out.println(fc.getCompanyInfo(1));
		System.out.println("Connected");
//		
		UIMain ui = new UIMain(fc);
		ui.beginUI();
	}

}
