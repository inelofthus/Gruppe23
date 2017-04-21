package databaseobjects;

import static org.junit.Assert.assertEquals;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.LinkedHashMap;

import org.junit.Test;

import database.DBController;
import databaseobjects.Course;

public class CourseTest {
	
	// A dummy DBC has no interaction with database. It just sets values as described in exampleValues.txt
	DBController dummyDBC = new DummyDBController();
	Course course = new Course("TDT4140", dummyDBC);
	Course course2 = new Course("TDT1234", dummyDBC);
	
	@Test
	public void testGetCompletedLectureIDs() {
		ArrayList<Integer> actual = course.getCompletedLectureIDs();
		ArrayList<Integer> expected = new ArrayList<>(Arrays.asList(1, 2, 3, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15));
		assertEquals(expected, actual);
	}
	
	@Test
	public void  TestgetCompletedLecturesIDDate() {
		LinkedHashMap<Integer, ArrayList<String>> actual = course.getCompletedLecturesIDDate();
		LinkedHashMap<Integer, ArrayList<String>> expected = new LinkedHashMap<>();
		expected.put(1, new ArrayList<String>(Arrays.asList("2017-01-01", "08:00:00")));
		expected.put(2, new ArrayList<String>(Arrays.asList("2017-01-02", "08:00:00")));
		expected.put(3, new ArrayList<String>(Arrays.asList("2017-01-03", "08:00:00")));
		expected.put(5, new ArrayList<String>(Arrays.asList("2017-02-02", "08:00:00")));
		expected.put(6, new ArrayList<String>(Arrays.asList("2017-03-03", "08:00:00")));
		expected.put(7, new ArrayList<String>(Arrays.asList("2017-04-04", "08:00:00")));
		expected.put(8, new ArrayList<String>(Arrays.asList("2017-05-05", "08:00:00")));
		expected.put(9, new ArrayList<String>(Arrays.asList("2017-06-06", "08:00:00")));
		expected.put(10, new ArrayList<String>(Arrays.asList("2017-07-07", "08:00:00")));
		expected.put(11, new ArrayList<String>(Arrays.asList("2017-08-08", "08:00:00")));
		expected.put(12, new ArrayList<String>(Arrays.asList("2017-09-09", "08:00:00")));
		expected.put(13, new ArrayList<String>(Arrays.asList("2017-10-10", "08:00:00")));
		expected.put(14, new ArrayList<String>(Arrays.asList("2017-11-11", "08:00:00")));
		expected.put(15, new ArrayList<String>(Arrays.asList("2017-12-12", "08:00:00")));
		
		assertEquals(expected, actual);
	}
	
	@Test
	public void  TestgetLastTwoCompletedLectureIDs() {
		ArrayList<Integer> actual = course.getLastTwoCompletedLectureIDs();
		ArrayList<Integer> expected = new ArrayList<Integer>(Arrays.asList(1,2));
		
		assertEquals(expected, actual);
		
		actual = course2.getLastTwoCompletedLectureIDs();
		expected = new ArrayList<Integer>(Arrays.asList(4));
		
		assertEquals(expected, actual);
	}
	
	@Test
	public void  TestgetLastTwoCompletedLectures() {
		LinkedHashMap<Integer, ArrayList<String>> actual = course.getLastTwoCompletedLectures();
		
		LinkedHashMap<Integer, ArrayList<String>> expected = new LinkedHashMap<>();
		expected.put(1, new ArrayList<String>(Arrays.asList("2017-01-01", "08:00:00")));
		expected.put(2, new ArrayList<String>(Arrays.asList("2017-01-02", "08:00:00")));
		
		assertEquals(expected, actual);
		
		actual = course2.getLastTwoCompletedLectures();
		expected = new LinkedHashMap<>();
		expected.put(4, new ArrayList<String>(Arrays.asList("2017-01-01", "08:00:00")));
	}
	
	
	@Test
	public void  TestgetCourseCode() {
		String actual = course.getCourseCode();
		String expected = "TDT4140";
		
		assertEquals(expected, actual);
	}
	
	@Test
	public void  TestgetCourseName() {
		String actual = course.getCourseName();
		String expected = "Programvareutvikling";
		
		assertEquals(expected, actual);
	}
	
	@Test
	public void  TestgetNumLectureHours() {
		int actual = course.getNumLectureHours();
		int expected = 4;
		
		assertEquals(expected, actual);
	}
	
	@Test
	public void  TestisTaughtInSpring() {
		boolean actual = course.isTaughtInSpring();
		boolean expected = false;
		
		assertEquals(expected, actual);
	}
	
	@Test
	public void  TestisTaughtInAutumn() {
		boolean actual = course.isTaughtInAutumn();
		boolean expected = true;
		
		assertEquals(expected, actual);
	}

	@Test
	public void  TestgetLectureIDs() {
		
		ArrayList<Integer> actual = course.getLectureIDs();
		ArrayList<Integer> expected = new ArrayList<>(Arrays.asList(1,2,3,5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15));
		
		assertEquals(expected, actual);
	}

	@Test
	public void  TestgetLectureTime() {
		String actual = course.getLectureTime(1);
		String expected = "08:00:00";
		
		assertEquals(expected, actual);
	}
	
	@Test
	public void  TestgetLectureDate() {
		String actual = course.getLectureDate(1);
		String expected = "2017-01-01";
		
		assertEquals(expected, actual);
	}
	
	@Test
	public void  TestgetLecRatingCounts() {
		course.setRatingsOverTime();
		ArrayList<Integer> actual = course.getLecRatingCounts(1);
		ArrayList<Integer>  expected = new ArrayList<>(Arrays.asList(0,0,0,0,0,0,0,0,0,0,0,0,0,0));
		assertEquals(expected, actual);
		
		actual = course.getLecRatingCounts(2);
		expected = new ArrayList<>(Arrays.asList(0,0,0,0,0,0,0,0,0,0,0,0,0,100));
		assertEquals(expected, actual);
		
		actual = course.getLecRatingCounts(3);
		expected = new ArrayList<>(Arrays.asList(0,0,0,0,0,0,0,0,0,0,0,0,0,0));
		assertEquals(expected, actual);
		
		actual = course.getLecRatingCounts(4);
		assertEquals(expected, actual);
		
		actual = course.getLecRatingCounts(5);
		assertEquals(expected, actual);
	}
	
	@Test
	public void  TestgetDateArrayForGraph() {
		ArrayList<String> actual = course.getDateArrayForGraph();
		ArrayList<String> expected = new ArrayList<>(Arrays.asList("12/12\n08:00", "11/11\n08:00",
				"10/10\n08:00","09/09\n08:00","08/08\n08:00","07/07\n08:00","06/06\n08:00",
				"05/05\n08:00", "04/04\n08:00", "03/03\n08:00","02/02\n08:00", "03/01\n08:00",
				"02/01\n08:00","01/01\n08:00" ));
		
		assertEquals(expected, actual);
	}
	
	@Test
	public void  TestgetSemester() {
		String actual = course.getSemester();
		String expected = "V2017";
		
		assertEquals(expected, actual);
	}
	
	@Test
	public void TestisCurrentsemester(){
		boolean actual = course.isCurrentsemester();
		boolean expected = false;
		
		if(Calendar.getInstance().get(Calendar.YEAR) == 2017 && Calendar.getInstance().get(Calendar.MONTH) +1 < 7){
			expected = true;
		}

		assertEquals(expected, actual);
	}
	
	@Test
	public void TestgetRatingValues(){
		
		ArrayList<String> actual = course.getRatingValues();
		ArrayList<String> expected = new ArrayList<>(Arrays.asList("Excellent","Good","Ok","Poor","Unsatisfactory"));
		
		assertEquals(expected, actual);
	}

	@Test
	public void TestgetLectureIDsMonth(){
		
		course.setLectureByMonth();
		
		ArrayList<Integer> actual  = course.getLectureIDsMonth(1);
		ArrayList<Integer> expected = new ArrayList<>(Arrays.asList(1,2,3));
		assertEquals(expected, actual);
		
		for(int i = 2; i < 13; i++ ){
			expected = new ArrayList<>(Arrays.asList(i + 3));
			actual = course.getLectureIDsMonth(i);
			assertEquals(expected, actual);
		}
	}
	
}
