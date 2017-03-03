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
	private Lecture lecture;
	
	public Course getCourse() {
		return course;
	}
	

	public void setCourse(Course course) {
		this.course = course;
	}

	private LinkedHashMap<Integer, GregorianCalendar> lastTwoLecturesStudent;
	private LinkedHashMap<Integer, GregorianCalendar> lastTwoLecturesProfessor;
	private Integer chosenStudentLecture;
	private Integer chosenProfessorLecture;
	
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
	
	public void setProfessor(Professor professor) {
		this.professor = professor;
	}
	
	public LinkedHashMap<Integer, GregorianCalendar> getLastTwoLecturesProfessor() {
		return lastTwoLecturesProfessor;
	}
	
	public void setlastTwoLecturesProfessor(LinkedHashMap<Integer, GregorianCalendar> lastTwoLecturesProfessor) {
		this.lastTwoLecturesProfessor = lastTwoLecturesProfessor;
	}
	
	public Integer getChosenProfessorLecture() {
		return chosenProfessorLecture;
	}
	
	public void setChosenProfessorLecture(Integer chosenProfessorLecture) {
		this.chosenProfessorLecture = chosenProfessorLecture;
	}
	
	public Lecture getLecture() {
		return lecture;
	}
	
	
}
