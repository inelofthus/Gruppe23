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


	public String getEmail(){
		return username + "@stud.ntnu.no";
	}
	public boolean existsInDB(){
		return DBC.studentExists(getEmail());
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
		DBC.insertEvaluation(getEmail(), lectureID, rating, comment);
		//TODO: Form validation
	}
	
	public void overWriteEvaluation(int lectureID, String rating, String comment){
		DBC.overwriteEvaluation(getEmail(), lectureID, rating, comment);
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
		return DBC.studentHasEvaluatedLecture(getEmail(), lecID);
	}
	
	//Old load function:
	/*public void loadInfo(){
	try {
		studyProgram = DBC.getStudyProgram(getEmail());
		courseIDs = DBC.getStudentCourses(getEmail());
		String query = "select courseName from Course as c, CourseStudent as cs WHERE c.courseCode = cs.courseCode AND studentEmail = '"+ getEmail() + "';";
		courseNames = DBC.getStringArray(query);
		
	} catch (Exception e) {
		// TODO: handle exception
		if (!existsInDB()) {
			throw new IllegalAccessError("User does not exist in database");
		}
		System.out.println(e.getMessage());
	}
			
}*/

}
