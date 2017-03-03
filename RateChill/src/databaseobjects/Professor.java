package databaseobjects;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.NoSuchElementException;


public class Professor extends DatabaseUser {
	String username;
	ArrayList<String> courseIDs;
	HashMap<String, String> courseIDNames;
	
	// Constructor1
	public Professor(String professorUsername) {
		this.username = professorUsername;
		DBC.loadProfessorInfo(this);
		
	}
	
	public boolean existsInDB(){
		return DBC.professorExists(username);
	}
	
	public void loadInfo(){
		try {
			courseIDs = DBC.getCoursesTaughtByProfessor(username);
			
		} catch (Exception e) {
			// TODO: handle exception
			if (!existsInDB()) {
				throw new NoSuchElementException("No courses exists for this professor");
			}
			System.out.println(e.getMessage());
		}
	}
	
	public HashMap<String, String> getCourseIDNames() {
		return courseIDNames;
	}

	public void setCourseIDNames(HashMap<String, String> courseIDNames) {
		this.courseIDNames = courseIDNames;
	}

	public void setCourseIDs(ArrayList<String> courseIDs) {
		this.courseIDs = courseIDs;
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


	public ArrayList<String> getCourseIDs() {
		return courseIDs;
	}
	
	public String getCourseNameForCourse(String courseCode) {
		// will give the corresponding courseName for the students Course
		return courseIDNames.get(courseCode);
	}

	
	
}
