package databaseobjects;

import java.util.ArrayList;

import database.DBController;

public class Student extends DatabaseUser{
	private String username;
	private String studyProgram;
	private ArrayList<String> courseIDs;
	private ArrayList<String> courseNames;
	
	//Constructor1
	public Student(String username) {
		this.username = username;
		loadInfo();
	}
	
//	//Constructor2
//	public Student(DBController DBC ,String username) {
//		super(DBC);
//		this.username = username;
//		loadInfo();
//	}
	
	
	public String getEmail(){
		return username + "@stud.ntnu.no";
	}
	public boolean existsInDB(){
		return DBC.studentExists(getEmail());
	}
	public void loadInfo(){
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
				
	}
	
	public ArrayList<String> getCourseNames() {
		return courseNames;
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
