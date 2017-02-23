package gui;

import databaseobjects.*;

public class mainController {

	//
	// The instance that every class will use
	//
	private static mainController instance = new mainController();

	private Student student; 
	
	//
	// Getter for the instance
	//
	public static mainController getInstance() {
		if (instance == null) instance = new mainController();
		return instance;
	}
	
	
	public Student getStudents(){
		return student;
	}


	public void setStudent(Student student) {
		this.student = student;
	}
	
	
	
}
