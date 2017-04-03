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
	private String previousView;
	
	public String getPreviousView() {
		return previousView;
	}

	public void setPreviousView(String previousView) {
		this.previousView = previousView;
	}

	public Course getCourse() {
		return course;
	}
	
	public void setCourse(Course course) {
		this.course = course;
	}

	private LinkedHashMap<Integer, ArrayList<String>> lastTwoLecturesStudent;
	private LinkedHashMap<Integer, ArrayList<String>> lastTwoLecturesProfessor;
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
	
	public LinkedHashMap<Integer, ArrayList<String>> getLastTwoLecturesStudent() {
		return lastTwoLecturesStudent;
	}


	public void setlastTwoLecturesStudent(LinkedHashMap<Integer, ArrayList<String>> lastTwoLecturesStudent) {
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
	
	public LinkedHashMap<Integer, ArrayList<String>> getLastTwoLecturesProfessor() {
		return lastTwoLecturesProfessor;
	}
	
	public void setlastTwoLecturesProfessor(LinkedHashMap<Integer, ArrayList<String>> lastTwoLecturesProfessor) {
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
	
	public void setLecture(Lecture lecture) {
		this.lecture = lecture;
	}
	
}
