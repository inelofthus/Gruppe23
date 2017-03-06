package databaseobjects;
import java.util.ArrayList;
import java.util.HashMap;


public class Professor extends DatabaseUser {
	String username;
	ArrayList<String> courseIDs;
	HashMap<String, String> courseIDNames;
	
	// Constructor
	public Professor(String professorUsername) {
		this.username = professorUsername;
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
	
	//Setters
	public void setCourseIDNames(HashMap<String, String> courseIDNames) {
		this.courseIDNames = courseIDNames;
	}

	public void setCourseIDs(ArrayList<String> courseIDs) {
		this.courseIDs = courseIDs;
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
