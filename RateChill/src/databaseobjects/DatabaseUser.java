package databaseobjects;

import database.DBController;

public abstract class DatabaseUser {
	
	DBController DBC;
	
	public DatabaseUser() {
		DBC = new DBController();
	}
	
	/*public DatabaseUser (DBController DBC) {
		this.DBC = DBC;
	}*/
}
