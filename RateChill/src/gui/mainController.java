package gui;

import java.util.ArrayList;

import databaseobjects.*;

public class mainController {

	//
	// The instance that every class will use
	//
	private static mainController instance = new mainController();

	private Student student; 
	private ArrayList<Integer> studentLectureIDs;
	private Integer chosenStudentLecture;
	
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
	
	public ArrayList<Integer> getStudentLectureIDs() {
		return studentLectureIDs;
	}


	public void setStudentLectureIDs(ArrayList<Integer> studentLectureIDs) {
		this.studentLectureIDs = studentLectureIDs;
	}


	public Integer getChosenStudentLecture() {
		return chosenStudentLecture;
	}


	public void setChosenStudentLecture(Integer chosenStudentLecture) {
		this.chosenStudentLecture = chosenStudentLecture;
	}
	
	
	
	
}
