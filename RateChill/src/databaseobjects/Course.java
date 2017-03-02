package databaseobjects;

import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.LinkedHashMap;


public class Course extends DatabaseUser{
	
	private String courseCode;
	private String courseName;
	private String courseLocation;
	private int numLectureHours;
	private ArrayList<String> professorUsernames;
	private ArrayList<String> lectureIDs;
	private LinkedHashMap<Integer, GregorianCalendar> lastTwoCompletedLectures;
	
	public void setLastTwoCompletedLectures(LinkedHashMap<Integer, GregorianCalendar> lastTwoCompletedLectures) {
		this.lastTwoCompletedLectures = lastTwoCompletedLectures;
	}

	//Constructor1
	public Course(String courseCode) {
		this.courseCode = courseCode;
		DBC.loadCourseInfo(this);
	}
		
	public boolean existsInDB(){
		return DBC.courseExists(courseCode);
	}
	
	/*public void loadInfo(){
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
	}*/
	
    /*	public ArrayList<Integer> getLastTwoCompletedLectures() {
	return DBC.getLastTwoCompletedLecturesForCourse(this.courseCode);
	}*/
	
	public String getCourseCode() {
		return courseCode;
	}

	public LinkedHashMap<Integer, GregorianCalendar> getLastTwoCompletedLectures() {
		return lastTwoCompletedLectures;
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
	
	
	
	public void setCourseCode(String courseCode) {
		this.courseCode = courseCode;
	}

	public void setCourseName(String courseName) {
		this.courseName = courseName;
	}

	public void setCourseLocation(String courseLocation) {
		this.courseLocation = courseLocation;
	}

	public void setNumLectureHours(int numLectureHours) {
		this.numLectureHours = numLectureHours;
	}

	public void setProfessorUsernames(ArrayList<String> professorUsernames) {
		this.professorUsernames = professorUsernames;
	}

	public void setLectureIDs(ArrayList<String> lectureIDs) {
		this.lectureIDs = lectureIDs;
	}
	
	public GregorianCalendar getLectureDate(int lecID){
		return lastTwoCompletedLectures.get(lecID);
	}

	public static void main(String[] args) {
		
	}

}