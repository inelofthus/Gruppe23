package gui;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Stack;

import databaseobjects.Course;
import databaseobjects.Lecture;
import databaseobjects.Professor;
import databaseobjects.Student;

public class MainController {

	//
	// The instance that every class will use
	//
	private static MainController instance = new MainController();

	private Student student; 
	private Professor professor;
	private Course course;
	private Lecture lecture;
	private Stack<String> stack = new Stack<String>();
	
	public boolean isConnectionPopupOpen() {
		return connectionPopupOpen;
	}


	public void setConnectionPopupOpen(boolean connectionPopupOpen) {
		this.connectionPopupOpen = connectionPopupOpen;
	}

	private boolean connectionPopupOpen = false;
	private boolean connectionFail = false;
	
	public boolean isConnectionFail() {
		return connectionFail;
	}


	public void setConnectionFail(boolean connectionFail) {
		this.connectionFail = connectionFail;
	}

	boolean createUser = false;
	String createProfUsername = "";
	String createStudUsername = "";
	boolean buttonsSaved = false;
	String buttonsSavedOrigin = "";
	String coursesUpdated = "";
	
	public Stack<String> getStack() {
		return stack;
	}
	
	
	public Course getCourse() {
		return course;
	}
	
	public void setCourse(Course course) {
		this.course = course;
	}

	private LinkedHashMap<Integer, ArrayList<String>> lastTwoLecturesStudent = new LinkedHashMap<>();
	private LinkedHashMap<Integer, ArrayList<String>> lastTwoLecturesProfessor;
	private Integer chosenStudentLecture;
	private Integer chosenProfessorLecture;
	
	//
	// Getter for the instance
	//
	public static MainController getInstance() {
		if (instance == null) instance = new MainController();
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
