package databaseobjects;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;

import com.mysql.fabric.xmlrpc.base.Array;

public class Lecture extends DatabaseUser {
	
	int lectureID;
	GregorianCalendar lectureDateAndTime;
	String courseCode;
	String Professor;
	ArrayList<Evaluation> Evaluations;
	
	//Constructor
	public Lecture () {
		
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
	
	public static void main(String[] args) {
		Lecture lec = new Lecture();
		String date = "2017-02-20";
	
		GregorianCalendar greg = lec.stringToCalender("2017-02-20", "00:00:00");
		System.out.println(greg.get(Calendar.MONTH));
	}
	
}
