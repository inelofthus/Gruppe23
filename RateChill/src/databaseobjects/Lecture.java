package databaseobjects;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.NoSuchElementException;

import database.DBController;


public class Lecture extends DatabaseUser {
	
	int lectureID;
	GregorianCalendar lectureDateAndTime;
	String courseCode;
	String professor;
	ArrayList<Evaluation> evaluations;
	
	//Constructor 1
	public Lecture (int lectureID) {
		this.lectureID = lectureID;
		loadInfo();
	}
	
	//Constructor 2
	public Lecture (DBController DBC, int lectureID) {
		super(DBC);
		this.lectureID = lectureID;
		loadInfo();
	}
	
	
	public void loadInfo(){
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
				
	}
	
	public boolean existsInDB() {
		return DBC.lectureExists(lectureID);
	}
	
	private GregorianCalendar stringToCalender(String date, String time){
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
		
	}
	
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

	public static void main(String[] args) {
		Lecture lec = new Lecture(2);
		System.out.println(lec.getCourseCode());
		System.out.println(lec.getLectureID());
		System.out.println(lec.getProfessor());
		System.out.println(lec.getEvaluations().get(0).getComment());
		System.out.println(lec.getEvaluations().get(1).getComment());
		System.out.println(lec.getLectureDateAndTime().get(Calendar.YEAR));
		
	}
	
}
