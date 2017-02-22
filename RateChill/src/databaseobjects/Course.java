package databaseobjects;

import java.util.ArrayList;
import java.util.NoSuchElementException;

public class Course extends DatabaseUser{
	
	private String courseCode;
	private String courseName;
	private String courseLocation;
	private int numLectureHours;
	private ArrayList<String> professorUsernames;
	private ArrayList<String> lectureIDs;
	
	//Constructor
	public Course(String courseCode) {
		this.courseCode = courseCode;
		loadInfo();
	}
	
	public boolean existsInDB(){
		return DBC.courseExists(courseCode);
	}
	
	public void loadInfo(){
		try {
			ArrayList<String> nameLocation = DBC.getCourseNameAndLocation(courseCode);
			courseName = nameLocation.get(0);
			courseLocation = nameLocation.get(1);
			numLectureHours = DBC.getLectureHoursForCourse(courseCode);
			professorUsernames = DBC.getProfessorsForCourse(courseCode);
			lectureIDs = DBC.getLecturesForCourse(courseCode);
		} catch (Exception e) {
			if (!existsInDB()) {
				throw new NoSuchElementException("The course does not exist");
			}
			System.out.println(e.getMessage());	
		}
	}

	public String getCourseCode() {
		return courseCode;
	}

	public String getCourseName() {
		return courseName;
	}

	public String getCourseLocation() {
		return courseLocation;
	}

	public int getNumLectureHours() {
		return numLectureHours;
	}

	public ArrayList<String> getProfessorUsernames() {
		return professorUsernames;
	}

	public ArrayList<String> getLectureIDs() {
		return lectureIDs;
	}

}