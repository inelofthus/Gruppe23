package databaseobjects;

import database.DBController;

public class Lecture {
	
	int lectureID;
	
	DBController DBC;
	
	public Lecture () {
		DBC = new DBController();
		DBC.connect();
	}
}
