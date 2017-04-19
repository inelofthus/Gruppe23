package database;

import java.sql.SQLException;
import gui.MainController;

public class CustomRunnable implements Runnable {

	DBController DBC = new DBController();
	MainController mc = MainController.getInstance();
	
	public CustomRunnable(DBController DBC) {
		this.DBC = DBC;
	}
	
	@Override
	public void run() {
		try {
			DBC.connectTry();
		} catch (SQLException e) {
			mc.setConnectionFail(true);
		}
	}

}
