package gui;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Stack;

import databaseobjects.Course;
import databaseobjects.Lecture;
import databaseobjects.Professor;
import databaseobjects.Student;

/**
 * MainController --- MainController has a Static instance, which keeps track of
 * all the user input received while the program is running. the GUI classes use
 * this instance to communicate amongst each other
 * 
 * @author Group 23: Ine Lofthus Arnesen, Kari Meling Johannessen, Nicolai
 *         Cappelen Michelet, Magnus Tvilde
 */
public class MainController {

	// The instance that every class will use
	private static MainController instance = new MainController();
	private Student student;
	private Professor professor;
	private Course course;
	private Lecture lecture;
	private Stack<String> stack = new Stack<String>();
	private String popupTitle;
	private String popupMessage;
	private boolean connectionPopupOpen = false;
	private boolean connectionFail = false;
	private boolean createUser = false;
	private String createProfUsername = "";
	private String createStudUsername = "";
	private String buttonsSaved = "";
	private String buttonsSavedOrigin = "";
	private String coursesUpdated = "";
	private LinkedHashMap<Integer, ArrayList<String>> lastTwoLecturesStudent = new LinkedHashMap<>();
	private LinkedHashMap<Integer, ArrayList<String>> lastTwoLecturesProfessor;
	private Integer chosenStudentLecture;
	private Integer chosenProfessorLecture;

	// Getter for the instance
	public static MainController getInstance() {
		if (instance == null)
			instance = new MainController();
		return instance;
	}

	public Student getStudent() {
		return student;
	}

	public void setStudent(Student student) {
		this.student = student;
	}

	public Professor getProfessor() {
		return professor;
	}

	public void setProfessor(Professor professor) {
		this.professor = professor;
	}

	public Course getCourse() {
		return course;
	}

	public void setCourse(Course course) {
		this.course = course;
	}

	public Lecture getLecture() {
		return lecture;
	}

	public void setLecture(Lecture lecture) {
		this.lecture = lecture;
	}

	public Stack<String> getStack() {
		return stack;
	}

	public void setStack(Stack<String> stack) {
		this.stack = stack;
	}

	public String getPopupTitle() {
		return popupTitle;
	}

	public void setPopupTitle(String popupTitle) {
		this.popupTitle = popupTitle;
	}

	public String getPopupMessage() {
		return popupMessage;
	}

	public void setPopupMessage(String popupMessage) {
		this.popupMessage = popupMessage;
	}

	public boolean isConnectionPopupOpen() {
		return connectionPopupOpen;
	}

	public void setConnectionPopupOpen(boolean connectionPopupOpen) {
		this.connectionPopupOpen = connectionPopupOpen;
	}

	public boolean isConnectionFail() {
		return connectionFail;
	}

	public void setConnectionFail(boolean connectionFail) {
		this.connectionFail = connectionFail;
	}

	public boolean isCreateUser() {
		return createUser;
	}

	public void setCreateUser(boolean createUser) {
		this.createUser = createUser;
	}

	public String getCreateProfUsername() {
		return createProfUsername;
	}

	public void setCreateProfUsername(String createProfUsername) {
		this.createProfUsername = createProfUsername;
	}

	public String getCreateStudUsername() {
		return createStudUsername;
	}

	public void setCreateStudUsername(String createStudUsername) {
		this.createStudUsername = createStudUsername;
	}

	public String getButtonsSaved() {
		return buttonsSaved;
	}

	public void setButtonsSaved(String buttonsSaved) {
		this.buttonsSaved = buttonsSaved;
	}

	public String getButtonsSavedOrigin() {
		return buttonsSavedOrigin;
	}

	public void setButtonsSavedOrigin(String buttonsSavedOrigin) {
		this.buttonsSavedOrigin = buttonsSavedOrigin;
	}

	public String getCoursesUpdated() {
		return coursesUpdated;
	}

	public void setCoursesUpdated(String coursesUpdated) {
		this.coursesUpdated = coursesUpdated;
	}

	public LinkedHashMap<Integer, ArrayList<String>> getLastTwoLecturesStudent() {
		return lastTwoLecturesStudent;
	}

	public void setLastTwoLecturesStudent(LinkedHashMap<Integer, ArrayList<String>> lastTwoLecturesStudent) {
		this.lastTwoLecturesStudent = lastTwoLecturesStudent;
	}

	public LinkedHashMap<Integer, ArrayList<String>> getLastTwoLecturesProfessor() {
		return lastTwoLecturesProfessor;
	}

	public void setLastTwoLecturesProfessor(LinkedHashMap<Integer, ArrayList<String>> lastTwoLecturesProfessor) {
		this.lastTwoLecturesProfessor = lastTwoLecturesProfessor;
	}

	public Integer getChosenStudentLecture() {
		return chosenStudentLecture;
	}

	public void setChosenStudentLecture(Integer chosenStudentLecture) {
		this.chosenStudentLecture = chosenStudentLecture;
	}

	public Integer getChosenProfessorLecture() {
		return chosenProfessorLecture;
	}

	public void setChosenProfessorLecture(Integer chosenProfessorLecture) {
		this.chosenProfessorLecture = chosenProfessorLecture;
	}


}
