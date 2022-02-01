import CRUD.FullCRUD;
import UI.UIMain;
import databasesquared.services.DatabaseConnectionService;

public class Main {

	public static void main(String[] args) {
		System.out.println("Connecting...");
		DatabaseConnectionService test = new DatabaseConnectionService("titan.csse.rose-hulman.edu","DatabaseSquared");
		test.connect("dbSquareUser", "ilikedatabases");
		FullCRUD fc = new FullCRUD(test);
		System.out.println("Connected");
		UIMain ui = new UIMain(fc);
		ui.beginUI();
	}

}
