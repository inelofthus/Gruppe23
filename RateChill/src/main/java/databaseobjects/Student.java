package databaseobjects;

import java.util.ArrayList;
import java.util.HashMap;

import database.DBController;


public class Student extends DatabaseUser{
	private String username;
	private String studyProgram;
	private ArrayList<String> courseIDs;
	private HashMap<String, String> courseIDNames;
	
	//Constructor1
	public Student(String username) {
		this.username = username;
		DBC.loadStudentInfo(this);
	}
	
	//Constructor2 used in testing
	public Student(String username, DBController newDBC) {
		switchDBC(newDBC);
		this.username = username;
		DBC.loadStudentInfo(this);
	}
	
	
	public HashMap<String, String> getCourseIDNames() {
		return courseIDNames;
	}


	public void setCourseIDNames(HashMap<String, String> courseIDNames) {
		this.courseIDNames = courseIDNames;
	}

	
	public boolean existsInDB(){
		return DBC.studentExists(username);
	}
	

	
	public void setStudyProgram(String studyProgram) {
		this.studyProgram = studyProgram;
	}

	public void setCourseIDs(ArrayList<String> courseIDs) {
		this.courseIDs = courseIDs;
	}

	public String getCourseNameForCourse(String courseCode) {
		// will give the corresponding courseName for the students Course
		return courseIDNames.get(courseCode);
	}

	public void giveEvaluation(int lectureID, String rating, String comment){
		DBC.insertEvaluation(username, lectureID, rating, comment);
		//TODO: Form validation
	}
	
	public void overWriteEvaluation(int lectureID, String rating, String comment){
		DBC.overwriteEvaluation(username, lectureID, rating, comment);
	}

	public String getUsername() {
		return username;
	}

	public String getStudyProgram() {
		return studyProgram;
	}

	public ArrayList<String> getCourseIDs() {
		return courseIDs;
	}
	
	public boolean hasEvaluatedLecture(int lecID){
		return DBC.studentHasEvaluatedLecture(username, lecID);
	}

	public void addCourse(String courseCode, String courseName) {
		// adds course to list and then database
		courseIDs.add(courseCode);
		courseIDNames.put(courseCode, courseName);
		DBC.insertCourseStudent(username, courseCode);
	}
	
	public void removeCourse(String courseCode){
		courseIDs.remove(courseCode);
		courseIDNames.remove(courseCode);
		DBC.deleteCourseStudent(username, courseCode);
	}
}
