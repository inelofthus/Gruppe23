package databaseobjects;

import java.util.ArrayList;
import java.util.HashMap;

import database.DBController;


public class Student extends DatabaseUser{
	private String username;
	private ArrayList<String> courseIDs;
	private HashMap<String, String> courseIDNames;
	
	/**
	 * Student constructor
	 * @param username The student's username
	 */
	public Student(String username) {
		this.username = username;
		DBC.loadStudentInfo(this);
	}
	
	/**
	 * Student constructor used in testing
	 * @param username The student's username
	 * @param newDBC The database controller switched to
	 */
	public Student(String username, DBController newDBC) {
		switchDBC(newDBC);
		this.username = username;
		DBC.loadStudentInfo(this);
	}
	
	//Getters
	
	public HashMap<String, String> getCourseIDNames() {
		return courseIDNames;
	}

	public String getCourseNameForCourse(String courseCode) {
		return courseIDNames.get(courseCode);
	}
	
	public String getUsername() {
		return username;
	}

	public ArrayList<String> getCourseIDs() {
		return courseIDs;
	}
	
	
	//Setters
	
	public void setCourseIDNames(HashMap<String, String> courseIDNames) {
		this.courseIDNames = courseIDNames;
	}

	public void setCourseIDs(ArrayList<String> courseIDs) {
		this.courseIDs = courseIDs;
	}
	
	/**
	 * Calls {@link database.DBController#studentExists(String)}
	 * @return Whether or not the student exists in the database
	 */
	public boolean existsInDB(){
		return DBC.studentExists(username);
	}

	/**
	 * Calls {@link database.DBController#insertEvaluation(String, int, String, String)}
	 * @param lectureID The evaluated lecture's ID
	 * @param rating The rating in the evaluation
	 * @param comment The comment in the evaluation
	 */
	public void giveEvaluation(int lectureID, String rating, String comment){
		DBC.insertEvaluation(username, lectureID, rating, comment);
	}
	
	/**
	 * Calls {@link database.DBController#overwriteEvaluation(String, int, String, String)}
	 * @param lectureID The evaluated lecture's ID
	 * @param rating The rating chosen in evaluation
	 * @param comment The comment chosen in evaluation
	 */
	public void overWriteEvaluation(int lectureID, String rating, String comment){
		DBC.overwriteEvaluation(username, lectureID, rating, comment);
	}

	/**
	 * Calls {@link database.DBController#studentHasEvaluatedLecture(String, int)}
	 * @param lecID The lecture's ID
	 * @return Whether or not a student has evaluated a given lecture
	 */
	public boolean hasEvaluatedLecture(int lecID){
		return DBC.studentHasEvaluatedLecture(username, lecID);
	}

	/**
	 * Inserts a given course to the CourseStudent table and to 
	 * the list of course IDs and names for the student object
	 * @param courseCode The course's course code
	 * @param courseName The course's english name
	 */
	public void addCourse(String courseCode, String courseName) {
		courseIDs.add(courseCode);
		courseIDNames.put(courseCode, courseName);
		DBC.insertCourseStudent(username, courseCode);
	}
	
	/**
	 * Removes a course from the CourseStudent table and the student's
	 * course IDs and names lists.
	 * @param courseCode The course's course code
	 */
	public void removeCourse(String courseCode){
		courseIDs.remove(courseCode);
		courseIDNames.remove(courseCode);
		DBC.deleteCourseStudent(username, courseCode);
	}
}
