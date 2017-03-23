package testing;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class TestAPI {
	public static void main(String args[]) throws IOException{
		String sURL = "http://www.ime.ntnu.no/api/course/en/tdt4140";
	    // Connect to the URL using java's native library
	    URL url = new URL(sURL);
	    HttpURLConnection request = (HttpURLConnection) url.openConnection();
	    request.connect();
	
	    // Convert to a JSON object to print data
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
	    System.out.println("Brukernavn, professor: " + 
	    		rootobj.getAsJsonObject("course").
	    		getAsJsonArray("educationalRole").get(0).getAsJsonObject().
	    		get("person").getAsJsonObject().get("username").getAsString());
	    
	    
	}
}
