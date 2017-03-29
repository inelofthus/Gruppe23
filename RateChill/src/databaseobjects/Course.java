package databaseobjects;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;

import database.DBController;

public class Course extends DatabaseUser{
	
	private ArrayList<String> ratingValues = new ArrayList<>(Arrays.asList("Perfect","Ok","Too Fast!","Too Slow!","Confused.. ?"));
	private String courseCode;
	private String courseName;
	private int numLectureHours;
	private ArrayList<String> professorUsernames;
	private ArrayList<Integer> lectureIDs;
	private ArrayList<Integer> completedLectureIDs;
	private LinkedHashMap<Integer, ArrayList<String>> completedLecturesIDDate;
	private String semester;
	private boolean taughtInSpring;
	private boolean taughtInAutumn;
	

	//Constructor1
	public Course(String courseCode) {
		// loads a course object with the newest semester
		this.courseCode = courseCode;
		DBC.loadCourseInfo(this);
		this.semester = getCurrentSemester();
	}
	
	public Course(String courseCode, String semester) {
		// loads a course object with the newest semester
		this.courseCode = courseCode;
		this.semester = semester;
		DBC.loadCourseInfoForSemester(this, semester);
	}
	
	//Constructor2 ONLY FOR USE WITH TESTING
	public Course(String courseCode, DBController newDBC) {
			this.courseCode = courseCode;
			switchDBC(newDBC);
			DBC.loadCourseInfo(this);
			this.semester = getCurrentSemester();
		}
		
	public boolean existsInDB(){
		return DBC.courseExists(courseCode);
	}
	
	//Getters
	
	public boolean isTaughtInSpring() {
		return taughtInSpring;
	}

	public void setTaughtInSpring(boolean taughtInSpring) {
		this.taughtInSpring = taughtInSpring;
	}

	public boolean isTaughtInAutumn() {
		return taughtInAutumn;
	}

	public void setTaughtInAutumn(boolean taughtInAutumn) {
		this.taughtInAutumn = taughtInAutumn;
	}

	public String getSemester() {
		return semester;
	}

	
	public ArrayList<Integer> getCompletedLectureIDs() {
		return completedLectureIDs;
	}
	
	public LinkedHashMap<Integer, ArrayList<String>> getCompletedLecturesIDDate() {
		return completedLecturesIDDate;
	}
	
	public ArrayList<Integer> getLastTwoCompletedLectureIDs() {
		ArrayList<Integer> lastTwo = new ArrayList<>();
		lastTwo.add(completedLectureIDs.get(0));
		lastTwo.add(completedLectureIDs.get(1));
		return lastTwo;
	}
	
	public String getCourseCode() {
		return courseCode;
	}
	
	public String getCourseName() {
		return courseName;
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
		return completedLecturesIDDate.get(lecID).get(1);
	}
	
	public String getLectureDate(int lecID){
		if(completedLectureIDs.contains(lecID))	{
			return completedLecturesIDDate.get(lecID).get(0);	
		}
		
		return null;
		
	}
	
	public LinkedHashMap<Integer, ArrayList<String>> getLastTwoCompletedLectures() {
		LinkedHashMap<Integer, ArrayList<String>> lastTwoHashMap = new LinkedHashMap<>();
		
		int id;
		ArrayList<String> dateTime;
		
		if(completedLectureIDs.size() >= 2){
			for (int i = 0; i<2; i++){
			id = completedLectureIDs.get(i);
			dateTime = completedLecturesIDDate.get(id);
			lastTwoHashMap.put(id, dateTime);	
		}
		}else if(completedLectureIDs.size() == 1){
			id = completedLectureIDs.get(0);
			dateTime = completedLecturesIDDate.get(id);
			lastTwoHashMap.put(id, dateTime);	
		}
		
		
		
		return lastTwoHashMap;
	}
	
	private String getCurrentSemester(){
		
		if(completedLectureIDs.size()>0){
			String date = getLectureDate(completedLectureIDs.get(0));
			String[] dateSplit = date.split("-");
			String year = dateSplit[0];
			String month = dateSplit[1];
			char semester = 'H';
			
			if(Integer.valueOf(month) < 7){
				semester = 'V';
			}
			
			return semester + year;
		} 	
		
		int Year = Calendar.getInstance().get(Calendar.YEAR);
		int Month = Calendar.getInstance().get(Calendar.MONTH) +1;
		char season;
		
		if(Month <= 7)
			season = 'V';
		else season = 'H';
		
		StringBuilder sb = new StringBuilder();
		sb.append(season).append(Year);
		
		return sb.toString();
		
	}
	
	//Setters
	
	public void setCompletedLectureIDs(ArrayList<Integer> completedLectureIDs) {
		this.completedLectureIDs = completedLectureIDs;
	}

	public void setCompletedLecturesIDDate(LinkedHashMap<Integer, ArrayList<String>> completedLecturesIDDate) {
		this.completedLecturesIDDate = completedLecturesIDDate;
	}
	
	public void setCourseCode(String courseCode) {
		this.courseCode = courseCode;
	}

	public void setCourseName(String courseName) {
		this.courseName = courseName;
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
	
	//To be used to generate lectures over time graph:
	
	public HashMap<Integer, Integer> lecIDtoNumRatings;
	private HashMap<Integer, Integer> lecIDtoRatingCount1;
	private HashMap<Integer, Integer> lecIDtoRatingCount2;
	private HashMap<Integer, Integer> lecIDtoRatingCount3;
	private HashMap<Integer, Integer> lecIDtoRatingCount4;
	private HashMap<Integer, Integer> lecIDtoRatingCount5;

	public void setRatingsOverTime(){
		DBC.setCourseRatingsOverTime(this);
	}
	
	public ArrayList<Integer> getLecRatingCounts(int i){
		ArrayList<Integer> count = new ArrayList<>();
		
		
			switch (i) {
			case 1:
				count = createCountArray(lecIDtoRatingCount1);
				break;
			case 2:
				count = createCountArray(lecIDtoRatingCount2);
				break;	
			case 3:
				count = createCountArray(lecIDtoRatingCount3);
				break;
			case 4:
				count = createCountArray(lecIDtoRatingCount4);
				break;
			case 5:
				count = createCountArray(lecIDtoRatingCount5);
				break;

			default:
				break;
			}
		return count;
	}

	public ArrayList<String> getDateArrayForGraph(){
		ArrayList<String> dates = new ArrayList<String>(); 
		Collections.reverse(completedLectureIDs);
		for(Integer lecID: completedLectureIDs){
			String date = completedLecturesIDDate.get(lecID).get(0);
			String[] dateSplit = date.split("-");
			String MM = dateSplit[1];
			String DD = dateSplit[2];
			date = DD + "." + MM;
			
			dates.add(date);
		}
		Collections.reverse(completedLectureIDs);
		return dates;
	}
	
	private ArrayList<Integer> createCountArray(HashMap<Integer, Integer> lecIDtoRatingCountHash) {
		ArrayList<Integer> count = new ArrayList<>();
		
		if(completedLectureIDs.size()>0){
			Collections.reverse(completedLectureIDs);

			for (Integer lecID : completedLectureIDs) {

				if (lecIDtoRatingCountHash.containsKey(lecID)) {
					count.add((lecIDtoRatingCountHash.get(lecID) * 100 / lecIDtoNumRatings.get(lecID)));
				} else
					count.add(0);
			}

			Collections.reverse(completedLectureIDs);
		}
		

		return count;
	}

	public ArrayList<String> getRatingValues() {
		return ratingValues;
	}
	
	public void setLecIDtoNumRatings(HashMap<Integer, Integer> lecIDtoNumRatings) {
		this.lecIDtoNumRatings = lecIDtoNumRatings;
	}

	public void setRatingValues(ArrayList<String> ratingValues) {
		this.ratingValues = ratingValues;
	}

	public void setLecIDtoRatingCount1(HashMap<Integer, Integer> lecIDtoRatingCount1) {
		this.lecIDtoRatingCount1 = lecIDtoRatingCount1;
	}

	public void setLecIDtoRatingCount2(HashMap<Integer, Integer> lecIDtoRatingCount2) {
		this.lecIDtoRatingCount2 = lecIDtoRatingCount2;
	}

	public void setLecIDtoRatingCount3(HashMap<Integer, Integer> lecIDtoRatingCount3) {
		this.lecIDtoRatingCount3 = lecIDtoRatingCount3;
	}

	public void setLecIDtoRatingCount4(HashMap<Integer, Integer> lecIDtoRatingCount4) {
		this.lecIDtoRatingCount4 = lecIDtoRatingCount4;
	}

	public void setLecIDtoRatingCount5(HashMap<Integer, Integer> lecIDtoRatingCount5) {
		this.lecIDtoRatingCount5 = lecIDtoRatingCount5;
	}
	
	public boolean isCurrentsemester(){
		
		char CourseSeason = semester.charAt(0);
		int CourseYear = Integer.valueOf(semester.substring(1));
		int ActualYear = Calendar.getInstance().get(Calendar.YEAR);
		int Month = Calendar.getInstance().get(Calendar.MONTH) +1;
		char ActualSeason;
		
		if(Month <= 7)
			ActualSeason = 'V';
		else ActualSeason = 'H';
		
		return (CourseSeason == ActualSeason && CourseYear == ActualYear);
		
	}

	//To be used in professor SEE EVALUATIONS:
	
	private ArrayList<Integer> lectureIDsMonth1 = new ArrayList<>();
	private ArrayList<Integer> lectureIDsMonth2 = new ArrayList<>();
	private ArrayList<Integer> lectureIDsMonth3 = new ArrayList<>();
	private ArrayList<Integer> lectureIDsMonth4 = new ArrayList<>();
	private ArrayList<Integer> lectureIDsMonth5 = new ArrayList<>();
	private ArrayList<Integer> lectureIDsMonth6 = new ArrayList<>();
	private ArrayList<Integer> lectureIDsMonth7 = new ArrayList<>();
	private ArrayList<Integer> lectureIDsMonth8 = new ArrayList<>();
	private ArrayList<Integer> lectureIDsMonth9 = new ArrayList<>();
	private ArrayList<Integer> lectureIDsMonth10 = new ArrayList<>();
	private ArrayList<Integer> lectureIDsMonth11= new ArrayList<>();
	private ArrayList<Integer> lectureIDsMonth12 = new ArrayList<>();
	
	public void setLectureByMonth(){
		for(int lecID: completedLectureIDs){
				String date = completedLecturesIDDate.get(lecID).get(0);
				String[] dateSplit = date.split("-");
				String MM = dateSplit[1];
				
				switch (MM) {
				case "01":
					lectureIDsMonth1.add(lecID);
					break;
				case "02":
					lectureIDsMonth2.add(lecID);
					break;
				case "03":
					lectureIDsMonth3.add(lecID);
					break;
				case "04":
					lectureIDsMonth4.add(lecID);
					break;
				case "05":
					lectureIDsMonth5.add(lecID);
					break;
				case "06":
					lectureIDsMonth6.add(lecID);
					break;
				case "07":
					lectureIDsMonth7.add(lecID);
					break;
				case "08":
					lectureIDsMonth8.add(lecID);
					break;
				case "09":
					lectureIDsMonth9.add(lecID);
					break;
				case "10":
					lectureIDsMonth10.add(lecID);
					break;
				case "11":
					lectureIDsMonth11.add(lecID);
					break;
				case "12":
					lectureIDsMonth12.add(lecID);
					break;

				default:
					break;
				}
			
		}
	}

	public ArrayList<Integer> getLectureIDsMonth(int monthNum){
		ArrayList<Integer> result = new ArrayList<>();
		
		switch (monthNum) {
		case 1:
			result = lectureIDsMonth1;
			break;
		case 2:
			result = lectureIDsMonth2;
			break;
		case 3:
			result = lectureIDsMonth3;
			break;
		case 4:
			result = lectureIDsMonth4;
			break;
		case 5:
			result = lectureIDsMonth5;
			break;
		case 6:
			result = lectureIDsMonth6;
			break;
		case 7:
			result = lectureIDsMonth7;
			break;
		case 8:
			result = lectureIDsMonth8;
			break;
		case 9:
			result = lectureIDsMonth9;
			break;
		case 10:
			result = lectureIDsMonth10;
			break;
		case 11:
			result = lectureIDsMonth11;
			break;
		case 12:
			result = lectureIDsMonth12;
			break;

		default:
			break;
		}
		return result;
	}

}