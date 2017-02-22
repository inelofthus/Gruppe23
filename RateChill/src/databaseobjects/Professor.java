package databaseobjects;

import java.util.ArrayList;
import java.util.NoSuchElementException;

import javafx.collections.ArrayChangeListener;


public class Professor extends DatabaseUser {
	String username;
	ArrayList<String> courses;
	
	public Professor( String professorUsername) {
		this.username = professorUsername;
		loadInfo();
	}
	
	private void loadInfo() {
		
		try {
			courses = DBC.getCoursesTaughtByProfessor(username);
			
		} catch (Exception e){
			// TODO:handle exception
			if (!existsInDB()) {
				throw new NoSuchElementException("Lecture does not exist in database");
			}
			System.out.println(e.getMessage());
		}
		
	}

	public boolean existsInDB(){
		return DBC.professorExists(username);
	}
	
	public ArrayList<Integer> getLecturesForCourse(String courseCode){
		// returns lectureIDs for all lectures given by this professor that have already passed in specified course
		// Order is with the newest lecture first
		return DBC.getCompletedLecturesForCourseByProfessor(courseCode, username);
	}
	
	public ArrayList<Integer> getLastTwoCompletedLecturesForCourse(String courseCode){
		// returns lectureIDs for all lectures given by this professor that have already passed in specified course
		// Order is with the newest lecture first
		ArrayList<Integer> lastTwoCompleteLectures = new ArrayList<>();
		ArrayList<Integer> allCompletedLectures = DBC.getCompletedLecturesForCourseByProfessor(courseCode, username);
		lastTwoCompleteLectures.add(allCompletedLectures.get(0));
		lastTwoCompleteLectures.add(allCompletedLectures.get(1));
		
		return lastTwoCompleteLectures;
	}

	
	
	public String getUsername() {
		return username;
	}

	public ArrayList<String> getCourses() {
		return courses;
	}

	public static void main(String[] args) {
		Professor prof = new Professor("pekkaa");
		System.out.println(prof.getUsername());
		System.out.println(prof.getCourses());
		System.out.println(prof.getLecturesForCourse(prof.getCourses().get(0)));
		System.out.println(prof.getLastTwoCompletedLecturesForCourse(prof.getCourses().get(0)));
	}
	
}
