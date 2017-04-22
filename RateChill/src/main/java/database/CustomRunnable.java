package database;

import java.sql.SQLException;
import gui.MainController;

/**
 * This class is a Runnable class, which can be used in a thread to run the
 * connect method in the DBController method
 * 
 * @author Ine L. Arnesen, Kari M. Johannessen, Nicolai C. Michelet, Magnus
 *         Tvilde
 */

public class CustomRunnable implements Runnable {

	DBController DBC = new DBController();
	MainController mc = MainController.getInstance();

	public CustomRunnable(DBController DBC) {
		this.DBC = DBC;
	}

	/**
	 * Run method runs the connectTry() method from the DBController. If an SQL
	 * exception is thrown, this updates a variable in Maincontroller mc, which
	 * will trigger a warning popup to appear
	 */
	@Override
	public void run() {
		try {
			DBC.connectTry();
		} catch (SQLException e) {
			mc.setConnectionFail(true);
		}
	}

}
