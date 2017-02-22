package databaseobjects;

import java.util.ArrayList;


public class Professor extends DatabaseUser {
	String username;
	ArrayList<String> courses;
	
	public Professor() {
		// TODO Auto-generated constructor stub
		
	}
	
	public boolean existsInDB(){
		return DBC.professorExists(username);
	}
	

}
