package databaseobjects;

import java.util.ArrayList;
import java.util.NoSuchElementException;

import database.DBController;

public class Course extends DatabaseUser{
	
	private String courseCode;
	private String courseName;
	private String courseLocation;
	private int numLectureHours;
	private ArrayList<String> professorUsernames;
	private ArrayList<String> lectureIDs;
	
	//Constructor1
	public Course(String courseCode) {
		this.courseCode = courseCode;
		loadInfo();
	}
	
	//Constructor2
	/*public Course(DBController DBC, String courseCode) {
		super(DBC);
		this.courseCode = courseCode;
		loadInfo();
	}*/
	
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
	
	public ArrayList<Integer> getLastTwoCompletedLectures() {
		return DBC.getLastTwoCompletedLecturesForCourse(this.courseCode);
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
	
	public static void main(String[] args) {
		Course course = new Course("tdt4140");
		System.out.println(course.getLastTwoCompletedLectures());
	}

}