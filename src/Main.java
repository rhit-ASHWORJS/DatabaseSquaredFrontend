import databasesquared.services.DatabaseConnectionService;

public class Main {

	public static void main(String[] args) {
		System.out.println("Hello World");
//		ApplicationRunner appRunner = new ApplicationRunner();
//		appRunner.runApplication(args);
		DatabaseConnectionService test = new DatabaseConnectionService("titan.csse.rose-hulman.edu","DatabaseSquared");
	}

}
