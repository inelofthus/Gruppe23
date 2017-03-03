package gui;

import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.LinkedHashMap;

import databaseobjects.*;

public class mainController {

	//
	// The instance that every class will use
	//
	private static mainController instance = new mainController();

	private Student student; 
	private Professor professor;
	private Course course;
	private LinkedHashMap<Integer, GregorianCalendar> lastTwoLecturesStudent;
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
	
	public LinkedHashMap<Integer, GregorianCalendar> getLastTwoLecturesStudent() {
		return lastTwoLecturesStudent;
	}


	public void setlastTwoLecturesStudent(LinkedHashMap<Integer, GregorianCalendar> lastTwoLecturesStudent) {
		this.lastTwoLecturesStudent = lastTwoLecturesStudent;
	}


	public Integer getChosenStudentLecture() {
		return chosenStudentLecture;
	}


	public void setChosenStudentLecture(Integer chosenStudentLecture) {
		this.chosenStudentLecture = chosenStudentLecture;
	}

	public Professor getProfessor() {
		return professor;
	}
	
	
	
}
