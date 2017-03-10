package databaseobjects;

import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.LinkedHashMap;

import javax.naming.StringRefAddr;


public class Course extends DatabaseUser{
	
	private String courseCode;
	private String courseName;
	private String courseLocation;
	private int numLectureHours;
	private ArrayList<String> professorUsernames;
	
	private ArrayList<Integer> lectureIDs;
	private ArrayList<Integer> lastTwoCompletedLectureIDs;
	private ArrayList<Integer> completedLectureIDs;
	private LinkedHashMap<Integer, ArrayList<String>> completedLecturesIDDate;
	


	public ArrayList<Integer> getCompletedLectureIDs() {
		return completedLectureIDs;
	}

	public void setCompletedLectureIDs(ArrayList<Integer> completedLectureIDs) {
		this.completedLectureIDs = completedLectureIDs;
	}

	public LinkedHashMap<Integer, ArrayList<String>> getCompletedLecturesIDDate() {
		return completedLecturesIDDate;
	}

	public void setCompletedLecturesIDDate(LinkedHashMap<Integer, ArrayList<String>> completedLecturesIDDate) {
		this.completedLecturesIDDate = completedLecturesIDDate;
	}

	//Constructor1
	public Course(String courseCode) {
		this.courseCode = courseCode;
		DBC.loadCourseInfo(this);
		
//		setLastTwoCompletedLectureIDs();
	}
		
	public boolean existsInDB(){
		return DBC.courseExists(courseCode);
	}
	
	//Getters
	
	public ArrayList<Integer> getLastTwoCompletedLectureIDs() {
		return lastTwoCompletedLectureIDs;
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

	public ArrayList<Integer> getLectureIDs() {
		return lectureIDs;
	}
	
	public String getLectureTime(int lecID){
		return completedLecturesIDDate.get(lecID).get(0);
	}
	
	public String getLectureDate(int lecID){
		return completedLecturesIDDate.get(lecID).get(0);	
	}
	
	//Setters
	/*private void setLastTwoCompletedLectureIDs() {
		if(completedLectureIDs.size() >= 2){
			lastTwoCompletedLectureIDs.add(completedLectureIDs.get(0));
			lastTwoCompletedLectureIDs.add(completedLectureIDs.get(1));
		}
		else if(completedLectureIDs.size() == 1){
			lastTwoCompletedLectureIDs.add(completedLectureIDs.get(0));
		}
		
	}*/

	public void setLastTwoCompletedLectures() {
		
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

	public void setLectureIDs(ArrayList<Integer> lectureIDs) {
		this.lectureIDs = lectureIDs;
	}

	public LinkedHashMap<Integer, ArrayList<String>> getLastTwoCompletedLectures() {
		LinkedHashMap<Integer, ArrayList<String>> lastTwoHashMap = new LinkedHashMap<>();
		
		int id;
		ArrayList<String> dateTime;
		
		for (int i = 0; i<2; i++){
			id = completedLectureIDs.get(i);
			dateTime = completedLecturesIDDate.get(id);
			lastTwoHashMap.put(id, dateTime);	
		}
		
		return lastTwoHashMap;
	}
	
	//////////////////////////END OF USEFUL CODE ////////////////////////////////////////
	
	// Old load function:
	
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

}