package databaseobjects;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

import database.DBController;


public class Professor extends DatabaseUser {
	String username;
	ArrayList<String> courseIDs;
	HashMap<String, String> courseIDNames;
	
	// Constructor 1
	public Professor(String professorUsername, String encryptedPassword) {
		this.username = professorUsername;
		try {
			if(DBC.checkProfessorPassword(professorUsername, encryptedPassword)){
			DBC.loadProfessorInfo(this);
			} else System.out.println("wrong password");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public boolean isCorrectPassword(String encryptedPassword) {
				
		try {
			return DBC.checkProfessorPassword(username, encryptedPassword);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}

	// Constructor2 for use in testing
	public Professor(String professorUsername, String encryptedPassword, DBController newDBC) {
		this.username = professorUsername;
		switchDBC(newDBC);
		DBC.loadProfessorInfo(this);
	}
	
	public boolean existsInDB(){
		return DBC.professorExists(username);
	}
	
	// Getters
	public HashMap<String, String> getCourseIDNames() {
		return courseIDNames;
	}

	public ArrayList<Integer> getLecturesForCourse(String courseCode){
		// returns lectureIDs for all lectures given by this professor that have already passed
		// Order is with the newest lecture first
		return DBC.getCompletedLecturesForCourseByProfessor(courseCode, username);
	}
	
	public ArrayList<Integer> getLastTwoCompletedLecturesForCourse(String courseCode){
		// returns lectureIDs for last two lectures given by this professor that have already passed in specified course
		// Order is with the newest lecture first
		ArrayList<Integer> lastTwoCompleteLectures = new ArrayList<>();
		ArrayList<Integer> allCompletedLectures = DBC.getCompletedLecturesForCourseByProfessor(courseCode, username);
		
		if(allCompletedLectures.size() ==1)
			lastTwoCompleteLectures.add(allCompletedLectures.get(0));
		else if(allCompletedLectures.size() >= 2){
			lastTwoCompleteLectures.add(allCompletedLectures.get(0));
			lastTwoCompleteLectures.add(allCompletedLectures.get(1));
		}
		
		return lastTwoCompleteLectures;
	}
	
	public String getUsername() {
		return username;
	}


	public ArrayList<String> getCourseIDs() {
		return courseIDs;
	}
	
	public String getCourseNameForCourse(String courseCode) {
		// will give the corresponding courseName for the students Course
		return courseIDNames.get(courseCode);
	}
	
	//Setters
	public void setCourseIDNames(HashMap<String, String> courseIDNames) {
		this.courseIDNames = courseIDNames;
	}

	public void setCourseIDs(ArrayList<String> courseIDs) {
		this.courseIDs = courseIDs;
	}

	
	public static String hashPassword(String password) {
		MessageDigest md = null;
		try {
			md = MessageDigest.getInstance("MD5");
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		md.update(password.getBytes());
		byte[] b = md.digest();
		StringBuffer sb = new StringBuffer();
		
		for(byte b1: b){
			sb.append(Integer.toHexString(b1 & 0xff).toString());
		}
		
		
		return sb.toString();
	}
	
	//////////////END OF USEFUL CODE ////////////////////////////
	
	//OLD LOAD FUNCTION	
	
	/*public void loadInfo(){
		try {
			courseIDs = DBC.getCoursesTaughtByProfessor(username);
			
		} catch (Exception e) {
			
			if (!existsInDB()) {
				throw new NoSuchElementException("No courses exists for this professor");
			}
			System.out.println(e.getMessage());
		}
	}*/
	
	
	
}
