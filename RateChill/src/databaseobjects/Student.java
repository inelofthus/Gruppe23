package databaseobjects;

import java.util.ArrayList;

public class Student extends DatabaseUser{
	private String username;
	private String studyProgram;
	private ArrayList<String> courseIDs;
	
	//Constructor
	public Student(String username) {
		this.username = username;
		loadInfo();
	}
	
	private String getEmail(){
		return username + "@stud.ntnu.no";
	}
	private boolean existsInDB(){
		return DBC.studentExists(getEmail());
	}
	public void loadInfo(){
		try {
			studyProgram = DBC.getStudyProgram(getEmail());
			courseIDs = DBC.getStudentCourses(getEmail());
			
		} catch (Exception e) {
			// TODO: handle exception
			if (!existsInDB()) {
				throw new IllegalAccessError("User does not exist in database");
			}
			System.out.println(e.getMessage());
		}
				
	}
	public void giveEvaluation(int lectureID, String rating, String comment){
		DBC.insertEvaluation(getEmail(), lectureID, rating, comment);
		//TODO: Form validation
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

}
