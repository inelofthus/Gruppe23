package databaseobjects;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;


public class Lecture extends DatabaseUser {
	
	int lectureID;
	GregorianCalendar lectureDateAndTime;
	String courseCode;
	String professor;
	ArrayList<Evaluation> evaluations;
	ArrayList<Evaluation> PerfectEvaluations;
	ArrayList<Evaluation> OkEvaluations;
	ArrayList<Evaluation> TooFastEvaluations;
	ArrayList<Evaluation> TooSlowEvaluations;
	ArrayList<Evaluation> ConfusedEvaluations;
	
	//Constructor 1
	public Lecture (int lectureID) {
		this.lectureID = lectureID;
		DBC.loadLectureInfo(this);
	}
	
	public boolean existsInDB() {
		return DBC.lectureExists(lectureID);
	}
	
	//Getters


	
	public int getLectureID() {
		return lectureID;
	}

	public GregorianCalendar getLectureDateAndTime() {
		return lectureDateAndTime;
	}

	public String getCourseCode() {
		return courseCode;
	}

	public String getProfessor() {
		return professor;
	}

	public ArrayList<Evaluation> getEvaluations() {
		return evaluations;
	}

	public int getRatingCount(String ratingValue){
		
		String query = "SELECT Count(Distinct e.rating) as ratingCount, e.rating From Evaluation e WHERE lectureID =" + lectureID + " AND rating = '"+ ratingValue +"';";
		//System.out.println(query);
		return DBC.getInt(query);
	}
		
	
	//Setters
	public void setLectureID(int lectureID) {
		this.lectureID = lectureID;
	}


	public void setLectureDateAndTime(GregorianCalendar lectureDateAndTime) {
		this.lectureDateAndTime = lectureDateAndTime;
	}


	public void setCourseCode(String courseCode) {
		this.courseCode = courseCode;
	}


	public void setProfessor(String professor) {
		this.professor = professor;
	}


	public void setEvaluations(ArrayList<Evaluation> evaluations) {
		this.evaluations = evaluations;
	}

	public ArrayList<Evaluation> getPerfectEvaluations() {
		return PerfectEvaluations;
	}

	public void setPerfectEvaluations(ArrayList<Evaluation> perfectEvaluations) {
		PerfectEvaluations = perfectEvaluations;
	}

	public ArrayList<Evaluation> getOkEvaluations() {
		return OkEvaluations;
	}

	public void setOkEvaluations(ArrayList<Evaluation> okEvaluations) {
		OkEvaluations = okEvaluations;
	}

	public ArrayList<Evaluation> getTooFastEvaluations() {
		return TooFastEvaluations;
	}

	public void setTooFastEvaluations(ArrayList<Evaluation> tooFastEvaluations) {
		TooFastEvaluations = tooFastEvaluations;
	}

	public ArrayList<Evaluation> getTooSlowEvaluations() {
		return TooSlowEvaluations;
	}

	public void setTooSlowEvaluations(ArrayList<Evaluation> tooSlowEvaluations) {
		TooSlowEvaluations = tooSlowEvaluations;
	}

	public ArrayList<Evaluation> getConfusedEvaluations() {
		return ConfusedEvaluations;
	}

	public void setConfusedEvaluations(ArrayList<Evaluation> confusedEvaluations) {
		ConfusedEvaluations = confusedEvaluations;
	}

	
	
	///////////////////////END OF USEFUL CODE //////////////////////////
	//Old load Funcion:
	
	/*public void loadInfo(){
		try {
			String date = DBC.getLectureDateTimeCourseCodeAndProfessor(lectureID).get(0);
			String time = DBC.getLectureDateTimeCourseCodeAndProfessor(lectureID).get(1);
			courseCode = DBC.getLectureDateTimeCourseCodeAndProfessor(lectureID).get(2);
			professor = DBC.getLectureDateTimeCourseCodeAndProfessor(lectureID).get(3);
			lectureDateAndTime = stringToCalender(date, time);
			evaluations = DBC.getEvaluationsForLecture(lectureID, DBC);
			
		} catch (Exception e){
			// TODO:handle exception
			if (!existsInDB()) {
				throw new NoSuchElementException("Lecture does not exist in database");
			}
			System.out.println(e.getMessage());
		}
				
	}*/
	
	/*private GregorianCalendar stringToCalender(String date, String time){
		// date format: "YYYY-MM-DD"
		// time format: "hh:mm:ss"
		String[] dateSplit = date.split("-");
		int YYYY = Integer.valueOf(dateSplit[0]);
		int MM = Integer.valueOf(dateSplit[1]);
		int DD = Integer.valueOf(dateSplit[2]);
		
		String[] timeSplit = time.split(":");
		int hh = Integer.valueOf(timeSplit[0]);
		int mm = Integer.valueOf(timeSplit[1]);
		int ss = Integer.valueOf(timeSplit[2]);
		
		GregorianCalendar calendar = new GregorianCalendar(YYYY, MM, DD, hh, mm, ss);
		
		return calendar;
		
	}*/
	
}