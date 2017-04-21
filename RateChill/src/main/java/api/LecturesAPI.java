package api;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import database.DBController;

/**
 * <h1>Lectures API</h1>
 * This class contains methods to retrieve lectures for a given course from the
 * 1024 calendar API and insert them into the RateChill database.
 * 
 * @author Ine L. Arnesen, Kari M. Johannessen, Nicolai C. Michelet, Magnus Tvilde
 */
public class LecturesAPI {
	DBController dbc = new DBController();
	
	/**
	 * This method retrieves all lectures for the specified course
	 * and inserts them into the database 
	 * @param courseCode
	 * @param professorUsername
	 * @throws IOException
	 */
	public void getApiInfoAndInsertToDB(String courseCode, String professorUsername) throws IOException{
		int year = Calendar.getInstance().get(Calendar.YEAR);
		if (!courseCode.contains("/")){
			courseCode = URLEncoder.encode(courseCode, "utf-8");
		}
		String sURL = "https://www.ntnu.edu/web/studies/courses?p_p_id="
				+ "coursedetailsportlet_WAR_courselistportlet&p_p_lifecycle=2&p_p_state=normal"
				+ "&p_p_mode=view&p_p_resource_id=timetable&p_p_cacheability=cacheLevelPage"
				+ "&p_p_col_id=column-1&p_p_col_count=1&_coursedetailsportlet_WAR_"
				+ "courselistportlet_"
				+ "courseCode="+courseCode
				+ "&year="+year
				+ "&version=1";
	    // Connect to the URL using java's native library
	    URL url = new URL(sURL);
	    HttpURLConnection request = (HttpURLConnection) url.openConnection();
	    request.connect();
	    
	    // Convert to a JSON object to print data
	    JsonParser jp = new JsonParser(); //from gson
	    JsonElement root = jp.parse(new InputStreamReader((InputStream) request.getContent())); //Convert the input stream to a json element
	    JsonObject rootobj = root.getAsJsonObject(); //May be an array, may be an object. 
	    
	    dbc.connect();
	    try {
		    JsonArray lectures = rootobj.getAsJsonObject("course").getAsJsonArray("summarized");
		    
		    for (int i = 0; i < lectures.size(); i++){
	    		if (lectures.get(i).getAsJsonObject().get("acronym").getAsString().equals("FOR")){ //Do not want lab hours
		    		String lectureStart = lectures.get(i).getAsJsonObject().get("from").getAsString();
		    		JsonArray weeks = lectures.get(i).getAsJsonObject().get("weeks").getAsJsonArray();
		    		int day = lectures.get(i).getAsJsonObject().get("dayNum").getAsInt();
		    		ArrayList<String> lectureDates = getLectureDates(weeks, day);
		    		
		    		for (int j = 0; j < lectureDates.size(); j++){
		    			try {
		    				dbc.insertLecture(lectureDates.get(j), lectureStart, courseCode, professorUsername);
						} catch (Exception e) {
							System.out.println(e);
						}
		    		}

			    }
		    }
		} catch (Exception e) {
			System.out.println("Course not in API");
		}
	    dbc.close();    
	}
	
	/**
	 * This method takes in an JsonArray of weeks and/or week intervals and the day of week 
	 * a lecture is taught. It outputs an ArrayList of lecture dates
	 * @param weeksList List of week numbers and/or week number intervals separated by hyphen 
	 * @param day Day of the week where 1 is Monday and 7 is Sunday
	 * @return lecture dates
	 */
	public ArrayList<String> getLectureDates(JsonArray weeksList, int day){
	    ArrayList<String> lectureDates = new ArrayList<String>();
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	    Calendar cal = Calendar.getInstance();
		
	    switch(day){
		case 1: cal.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
				break;
		case 2: cal.set(Calendar.DAY_OF_WEEK, Calendar.TUESDAY);
				break;
		case 3: cal.set(Calendar.DAY_OF_WEEK, Calendar.WEDNESDAY);
				break;
		case 4: cal.set(Calendar.DAY_OF_WEEK, Calendar.THURSDAY);
				break;
		case 5: cal.set(Calendar.DAY_OF_WEEK, Calendar.FRIDAY);
				break;
		case 6: cal.set(Calendar.DAY_OF_WEEK, Calendar.SATURDAY);
				break;
		case 7: cal.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
				break;
		}
		for (int i = 0; i < weeksList.size(); i++){
			if (weeksList.get(i).getAsString().contains("-")){
				String[] weeks = weeksList.get(i).getAsString().split("-");
				int weekStart = Integer.parseInt(weeks[0]);
				int weekEnd = Integer.parseInt(weeks[1]);
				for (int week = weekStart; week <= weekEnd; week++){
					cal.set(Calendar.WEEK_OF_YEAR, week);   
					lectureDates.add(sdf.format(cal.getTime()));
				}
			}
			else{
				cal.set(Calendar.WEEK_OF_YEAR, weeksList.get(i).getAsInt());
				lectureDates.add(sdf.format(cal.getTime()));
			}
		}
		return lectureDates;
	}
}
