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

	// Attributes and objects set by user activity
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

	/**
	 * @return student A student object for the student that has logged in to
	 *         the application
	 */
	public Student getStudent() {
		return student;
	}

	/**
	 * @param student
	 *            A student object with information about the logged in student
	 */
	public void setStudent(Student student) {
		this.student = student;
	}

	/**
	 * @return professor A Professor object for the professor that has logged in
	 *         to the application
	 */
	public Professor getProfessor() {
		return professor;
	}

	/**
	 * @param professor
	 *            A professor object with information about the logged in
	 *            professor
	 */
	public void setProfessor(Professor professor) {
		this.professor = professor;
	}

	/**
	 * @return course The course object being used in the application
	 */
	public Course getCourse() {
		return course;
	}

	/**
	 * @param course
	 *            A course object containing information about the chosen course
	 */
	public void setCourse(Course course) {
		this.course = course;
	}

	/**
	 * @return Lecture The lecture object currently in use in the application
	 */
	public Lecture getLecture() {
		return lecture;
	}

	/**
	 * @param lecture
	 *            The lecture object that is being used in the application
	 */
	public void setLecture(Lecture lecture) {
		this.lecture = lecture;
	}

	/**
	 * @return Stack The stack object that contains the previously accessed GUIs
	 *         (to be used with back button)
	 */
	public Stack<String> getStack() {
		return stack;
	}

	/**
	 * @param stack
	 *            The stack object that contains the previously accessed GUIs
	 */
	public void setStack(Stack<String> stack) {
		this.stack = stack;
	}

	/**
	 * @return title The title to be set in the InfoPopup
	 */
	public String getPopupTitle() {
		return popupTitle;
	}

	/**
	 * @param popupTitle
	 *            The title to be set in the InfoPopup
	 */
	public void setPopupTitle(String popupTitle) {
		this.popupTitle = popupTitle;
	}

	/**
	 * @return message The message to be displayed in the popup
	 */
	public String getPopupMessage() {
		return popupMessage;
	}

	/**
	 * @param popupMessage
	 *            The message to be displayed in the popup
	 */
	public void setPopupMessage(String popupMessage) {
		this.popupMessage = popupMessage;
	}

	/**
	 * @return boolean True if the connectionPopup (Popup.fxml) GUI is open.
	 *         False otherwise
	 */
	public boolean isConnectionPopupOpen() {
		return connectionPopupOpen;
	}

	/**
	 * @param connectionPopupOpen
	 *            Boolean: True if the connectionPopup (Popup.fxml) GUI is open.
	 *            False otherwise
	 */
	public void setConnectionPopupOpen(boolean connectionPopupOpen) {
		this.connectionPopupOpen = connectionPopupOpen;
	}

	/**
	 * @return boolean True if there was a problem connecting to the database.
	 *         False otherwise
	 */
	public boolean isConnectionFail() {
		return connectionFail;
	}

	/**
	 * @param connectionFail
	 *            Boolean True if there was a problem connecting to the
	 *            database. False otherwise
	 */
	public void setConnectionFail(boolean connectionFail) {
		this.connectionFail = connectionFail;
	}

	/**
	 * @return boolean. true If a user has just been created. false otherwise
	 */
	public boolean isCreateUser() {
		return createUser;
	}

	/**
	 * @param createUser
	 *            boolean. "true" If a user has just been created. "false"
	 *            otherwise
	 */
	public void setCreateUser(boolean createUser) {
		this.createUser = createUser;
	}

	/**
	 * @return createProfUsername "true" if a professor user was just created.
	 *         "false" if createUser page was accessed but no changes made. ""
	 *         otherwise
	 */
	public String getCreateProfUsername() {
		return createProfUsername;
	}

	/**
	 * @param createProfUsername
	 *            boolean. "true" if a professor user was just created. "false"
	 *            if createUser page was accessed but no changes made. ""
	 *            otherwise
	 */
	public void setCreateProfUsername(String createProfUsername) {
		this.createProfUsername = createProfUsername;
	}

	/**
	 * @return boolean. "true" if a student user was just created. "false" if
	 *         create User page was accessed but no changes made. "" otherwise
	 */
	public String getCreateStudUsername() {
		return createStudUsername;
	}

	/**
	 * @param createStudUsername
	 *            boolean. "true" if a student user was just created. "false" if
	 *            create User page was accessed but no changes made. ""
	 *            otherwise
	 */
	public void setCreateStudUsername(String createStudUsername) {
		this.createStudUsername = createStudUsername;
	}

	/**
	 * @return "true" if changes were made to the Evaluation buttons, "false" if
	 *         the page was accessed but no changes were made. "" otherwise
	 */
	public String getButtonsSaved() {
		return buttonsSaved;
	}

	/**
	 * @param buttonsSaved
	 *            "true" if changes were made to the Evaluation buttons, "false"
	 *            if the page was accessed but no changes were made. ""
	 *            otherwise
	 */
	public void setButtonsSaved(String buttonsSaved) {
		this.buttonsSaved = buttonsSaved;
	}

	/**
	 * @return buttonsSavedOrigin A string describing the .fxml file where the
	 *         customizeButtons page was accessed from
	 */
	public String getButtonsSavedOrigin() {
		return buttonsSavedOrigin;
	}

	/**
	 * @param buttonsSavedOrigin
	 *            A string describing the .fxml file where the customizeButtons
	 *            page was accessed from
	 */
	public void setButtonsSavedOrigin(String buttonsSavedOrigin) {
		this.buttonsSavedOrigin = buttonsSavedOrigin;
	}

	/**
	 * @return coursesUpdated "true" if changes were made to the student's
	 *         courses, "false" if the page was accessed but no changes were
	 *         made. "" otherwise
	 */
	public String getCoursesUpdated() {
		return coursesUpdated;
	}

	/**
	 * @param coursesUpdated
	 *            "true" if changes were made to the student's courses, "false"
	 *            if the page was accessed but no changes were made. ""
	 *            otherwise
	 */
	public void setCoursesUpdated(String coursesUpdated) {
		this.coursesUpdated = coursesUpdated;
	}

	/**
	 * @return lastTwoLecturesStudent a list containing the lectureIDS for the
	 *         last two completed lectures in the Student's chosen course
	 */
	public LinkedHashMap<Integer, ArrayList<String>> getLastTwoLecturesStudent() {
		return lastTwoLecturesStudent;
	}

	/**
	 * @param lastTwoLecturesStudent a list containing the lectureIDS for the
	 *         last two completed lectures in the Student's chosen course
	 */
	public void setLastTwoLecturesStudent(LinkedHashMap<Integer, ArrayList<String>> lastTwoLecturesStudent) {
		this.lastTwoLecturesStudent = lastTwoLecturesStudent;
	}

	/**
	 * @return lastTwoLecturesProfessor a list containing the lectureIDS for the
	 *         last two completed lectures in the Professor's chosen course
	 */
	public LinkedHashMap<Integer, ArrayList<String>> getLastTwoLecturesProfessor() {
		return lastTwoLecturesProfessor;
	}

	/**
	 * @param lastTwoLecturesProfessor a list containing the lectureIDS for the
	 *         last two completed lectures in the Professor's chosen course
	 */
	public void setLastTwoLecturesProfessor(LinkedHashMap<Integer, ArrayList<String>> lastTwoLecturesProfessor) {
		this.lastTwoLecturesProfessor = lastTwoLecturesProfessor;
	}

	/**
	 * @return lectureID the unique ID of the student's chosen lecture
	 */
	public Integer getChosenStudentLecture() {
		return chosenStudentLecture;
	}

	/**
	 * @param chosenStudentLecture the unique ID of the student's chosen lecture
	 */
	public void setChosenStudentLecture(Integer chosenStudentLecture) {
		this.chosenStudentLecture = chosenStudentLecture;
	}

	/**
	 * @return chosenProfessorLecture the unique ID of the Professor's chosen lecture
	 */
	public Integer getChosenProfessorLecture() {
		return chosenProfessorLecture;
	}
	
	/**
	 * @param chosenProfessorLecture the unique ID of the Professor's chosen lecture
	 */
	public void setChosenProfessorLecture(Integer chosenProfessorLecture) {
		this.chosenProfessorLecture = chosenProfessorLecture;
	}

}
