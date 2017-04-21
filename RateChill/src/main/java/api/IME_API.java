package api;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import database.DBController;

/**
 * This class contains the necessary methods to insert all information about courses and lectures
 * into the database.
 * @author Ine L. Arnesen, Kari M. Johannessen, Nicolai C. Michelet, Magnus Tvilde
 */
public class IME_API {
	private DBController dbc = new DBController();
	private LecturesAPI lecAPI = new LecturesAPI();
	
	/**
	 * This method retrieves the list of courses from the IME API
	 * and calls helper methods to retrieve information about courses from the IME API 
	 * and information about lectures from the LectureAPI class
	 * and inserts into the database
	 */
	public void getApiInfo() throws IOException{
		String sURL = "http://www.ime.ntnu.no/api/course/en/-";
	    // Connect to the URL using java's native library
	    URL url = new URL(sURL);
	    HttpURLConnection request = (HttpURLConnection) url.openConnection();
	    request.connect();
	
	    // Convert to a JSON object to print data
	    JsonParser jp = new JsonParser(); //from gson
	    JsonElement root = jp.parse(new InputStreamReader((InputStream) request.getContent())); //Convert the input stream to a json element
	    JsonObject rootobj = root.getAsJsonObject(); //May be an array, may be an object. 
	    JsonArray course = rootobj.getAsJsonArray("course");
	    dbc.connect();
	    for (int i = 0; i < course.size(); i++){
	    	loadAndSetCourseInfo(course.get(i).getAsJsonObject().get("code").getAsString());
	    }
	    dbc.close();
	    request.disconnect();
	}
	
	
	/**
	 * This method is a helper method for getApiInfo that retrieves course info from
	 * the IME API, calls the getApiInfo from the LectureAPI class to retrieve lecture information
	 * and inserts into the database
	 * @param courseCode
	 * @throws IOException
	 */
	private void loadAndSetCourseInfo(String courseCode) throws IOException{
	    String sURL = "";
		
		if (courseCode.contains("/")){
			sURL = "http://www.ime.ntnu.no/api/course/en/" + courseCode;
		}else{
			sURL = "http://www.ime.ntnu.no/api/course/en/" + URLEncoder.encode(courseCode, "utf-8");
		}
	    URL url = new URL(sURL);
	    HttpURLConnection request = (HttpURLConnection) url.openConnection();
	    request.connect();

	    JsonParser jp = new JsonParser(); //from gson
	    JsonElement root = jp.parse(new InputStreamReader((InputStream) request.getContent())); //Convert the input stream to a json element
	    JsonObject rootobj = root.getAsJsonObject(); //May be an array, may be an object. 
	    
	    try {
		    String courseName = rootobj.getAsJsonObject("course").get("englishName").getAsString();
		    int lectureHours = -1;
		    try {
			    lectureHours = rootobj.getAsJsonObject("course").
			    		getAsJsonArray("educationTerm").get(0).getAsJsonObject().
			    		get("lectureHours").getAsInt();
			} catch (Exception e) {
				System.out.println("No lecture hours for this course");
			}
		    
		    String location = rootobj.getAsJsonObject("course").get("location").getAsString();
		    if (!location.equals("Trondheim")){
		    	throw new IllegalArgumentException();
		    }
		    boolean taughtInSpring = rootobj.getAsJsonObject("course").get("taughtInSpring").getAsBoolean();
		    boolean taughtInAutumn = rootobj.getAsJsonObject("course").get("taughtInAutumn").getAsBoolean();
		    String professorUsername = rootobj.getAsJsonObject("course").
		    		getAsJsonArray("educationalRole").get(0).getAsJsonObject().
		    		get("person").getAsJsonObject().get("username").getAsString();
		    dbc.insertCourseNC(courseCode, courseName, lectureHours, taughtInSpring, taughtInAutumn);
		    dbc.insertProfessorNC(professorUsername, "np");
		    dbc.insertCourseProfessorNC(professorUsername, courseCode);
	    	
		    lecAPI.getApiInfoAndInsertToDB(courseCode, professorUsername);
	    } catch (IllegalArgumentException iae) {
	    	System.out.println("Course not taught in Trondheim");
		} catch (Exception e) {
			System.out.println("One or more fields are missing");
		}    
	}
}

