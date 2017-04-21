package api;

import java.io.IOException;

/**
 * This class contains a main method which is intended to be run
 * once between every semester in order to populate the database with 
 * professors, courses and lectures for that semester.
 * @author Ine L. Arnesen, Kari M. Johannessen, Nicolai C. Michelet, Magnus Tvilde
 *
 */
public class PopulateDatabase {
	public static void main(String[] args) throws IOException {
		IME_API ime = new IME_API();
		ime.getApiInfo(); //Updates courses, professors and lectures in database
	}
}
