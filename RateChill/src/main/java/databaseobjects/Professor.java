package databaseobjects;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

import database.DBController;


/**
 * 
 * @author Ine L. Arnesen, Kari M. Johannessen, Magnus Tvilde, Nicolai C. Michelet
 */
public class Professor extends DatabaseUser {
	private String username;
	private String encryptedPassword;
	private ArrayList<String> courseIDs;
	private HashMap<String, String> courseIDNames;
	
	/**
	 * Professor constructor.
	 * @param professorUsername The professor's username
	 */
	public Professor(String professorUsername) {
		this.username = professorUsername;
		DBC.loadProfessorInfo(this);
		this.encryptedPassword = DBC.getStringArray("select professorPassword from Professor Where professorUsername = '" + professorUsername + "';").get(0);
	}
	
	/**
	 * @param encryptedPassword The encrypted password 
	 * @return Whether or not the encrypted password was correct
	 */
	public boolean isCorrectPassword(String encryptedPassword) {
		boolean ans = false;
		
		try {
			ans = DBC.checkProfessorPassword(username, encryptedPassword);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return ans;
	}

	/**
	 * Professor constructor used in testing.
	 * @param professorUsername Professor's username
	 * @param encryptedPassword The professor's encrypted password
	 * @param newDBC The database controller switched to
	 */
	public Professor(String professorUsername, String encryptedPassword, DBController newDBC) {
		this.username = professorUsername;
		switchDBC(newDBC);
		DBC.loadProfessorInfo(this);
		this.encryptedPassword = DBC.getStringArray("select professorPassword from Professor Where professorUsername = '" + professorUsername + "';").get(0);
	}
	
	/**
	 * @return Whether or not the professor exists in the database
	 */
	public boolean existsInDB(){
		return DBC.professorExists(username);
	}
	
	
	// Getters
	
	public HashMap<String, String> getCourseIDNames() {
		return courseIDNames;
	}

	/**
	 * Returns lecture IDs for all lectures given by this professor in the specified course
	 * that have already passed ordered by newest lecture
	 * @param courseCode The course's course code
	 * @return Lecture IDs for all completed lectures ordered by newest lecture
	 */
	public ArrayList<Integer> getLecturesForCourse(String courseCode){
		return DBC.getCompletedLecturesForCourseByProfessor(courseCode, username);
	}
	
	/**
	 * Returns lecture IDs for last two lectures given by this professor that have 
	 * already passed in specified course ordered by newest lecture 
	 * @param courseCode The course's course code
	 * @return Lecture IDs for last two lectures for the specified course
	 */
	public ArrayList<Integer> getLastTwoCompletedLecturesForCourse(String courseCode){
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
	
	/**
	 * @param courseCode The course's course code
	 * @return The course's name
	 */
	public String getCourseNameForCourse(String courseCode) {
		return courseIDNames.get(courseCode);
	}
	
	
	//Setters
	
	public void setCourseIDNames(HashMap<String, String> courseIDNames) {
		this.courseIDNames = courseIDNames;
	}

	public void setCourseIDs(ArrayList<String> courseIDs) {
		this.courseIDs = courseIDs;
	}

	/**
	 * Method for hashing passwords
	 * @param password The password to be hashed
	 * @return The password in hashed form
	 */
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

	/**
	 * Checks if the professor user has set a password yet
	 * @return Whether or not professor has set a password
	 */
	public boolean hasPassword() {
		return !encryptedPassword.equals("np");
	}
	
}
