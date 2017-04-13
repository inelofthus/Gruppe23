package api;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Arrays;

import org.junit.Test;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;

import api.LecturesAPI;

public class LecturesAPITest {
	LecturesAPI lapi = new LecturesAPI();
	
	@Test
	public void getLectureDates() {
		//For 2017
		for (int i = 1; i <= 7; i++){
			JsonArray weeksList = new JsonArray();
			JsonElement week1 = new JsonPrimitive(1);
			JsonElement week2 = new JsonPrimitive(2);
			int day = i;
			weeksList.add(week1);
			weeksList.add(week2);
			ArrayList<String> lecDates = new ArrayList<String>();
			lecDates = lapi.getLectureDates(weeksList, day);
			int dd1 = 1 + i;
			int dd2 = 8 + i;
			String date1 = "";
			String date2 = "";
			if (dd1 < 10){
				date1 = "2017-01-0" + dd1;
			}else{date1 = "2017-01-" + dd1;}
			
			if (dd2 < 10){
				date2 = "2017-01-0" + dd2;
			}else{date2 = "2017-01-" + dd2;}
			
			ArrayList<String> expLecDates = new ArrayList<String>(Arrays.asList(date1, date2));
			assertEquals(expLecDates, lecDates);
		}
		
		JsonArray weeksList = new JsonArray();
		JsonElement week1 = new JsonPrimitive("1-2");
		JsonElement week2 = new JsonPrimitive("4-5");
		weeksList.add(week1);
		weeksList.add(week2);
		int day = 1; //Monday
		ArrayList<String> exp = new ArrayList<String>(Arrays.asList("2017-01-02", 
				"2017-01-09", "2017-01-23", "2017-01-30"));
		assertEquals(exp, lapi.getLectureDates(weeksList, day));
		
		
	}

}
