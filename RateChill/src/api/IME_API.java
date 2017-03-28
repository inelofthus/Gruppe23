package api;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class IME_API {
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
	    for (int i = 0; i < course.size(); i++){
	    	
	    	System.out.println("#" + (i+1) + " " + course.get(i).getAsJsonObject().get("code").getAsString());
	    	loadAndSetCourseInfo(course.get(i).getAsJsonObject().get("code").getAsString());
	    	
	    }
	    
	    request.disconnect();
	    /*System.out.println("Fagkode:" + rootobj.getAsJsonObject("course").get("code").getAsString());
	    System.out.println("Navn:" + rootobj.getAsJsonObject("course").get("norwegianName").getAsString());
	   
	    String semester = "";
	    if (rootobj.getAsJsonObject("course").get("taughtInAutumn").getAsBoolean()){
	    	semester = "Høst";
	    }
	    else {semester = "Vår";}
	    System.out.println("Semester: " + semester);
	    //this iz best codez
	    System.out.println("Brukernavn, professor: " + 
	    		rootobj.getAsJsonObject("course").
	    		getAsJsonArray("educationalRole").get(0).getAsJsonObject().
	    		get("person").getAsJsonObject().get("username").getAsString());
	    
	    */
	}
	
	public void loadAndSetCourseInfo(String courseCode) throws IOException{
		
	    
		String sURLe = "http://www.ime.ntnu.no/api/course/en/" + URLEncoder.encode(courseCode, "utf-8");
	    String sURL = "http://www.ime.ntnu.no/api/course/en/" + courseCode;

	    
	    URL url = new URL(sURLe);
	    
	    HttpURLConnection request = (HttpURLConnection) url.openConnection();
	    request.connect();

	    JsonParser jp = new JsonParser(); //from gson
	    JsonElement root = jp.parse(new InputStreamReader((InputStream) request.getContent())); //Convert the input stream to a json element
	    JsonObject rootobj = root.getAsJsonObject(); //May be an array, may be an object. 
	    
	    System.out.println("Fagkode:" + rootobj.getAsJsonObject("course").get("code").getAsString());
	    System.out.println("Navn:" + rootobj.getAsJsonObject("course").get("norwegianName").getAsString());
	   
	    String semester = "";
	    if (rootobj.getAsJsonObject("course").get("taughtInAutumn").getAsBoolean()){
	    	semester = "Høst";
	    }
	    else {semester = "Vår";}
	    System.out.println("Semester: " + semester);
	    //this iz best codez
	    try {
		    System.out.println("Brukernavn, professor: " + 
		    		rootobj.getAsJsonObject("course").
		    		getAsJsonArray("educationalRole").get(0).getAsJsonObject().
		    		get("person").getAsJsonObject().get("username").getAsString());
		} catch (Exception e) {
			// TODO: handle exception
		}

	    
	}
	
	public static void main(String[] args) throws IOException {
		IME_API api = new IME_API();
		api.getApiInfo();
	}
}
