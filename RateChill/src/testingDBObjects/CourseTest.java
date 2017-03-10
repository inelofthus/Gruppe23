package testingDBObjects;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;

import org.junit.Test;

import database.DBController;
import databaseobjects.Course;

public class CourseTest {
	
	// A dummy DBC has no interaction with database. It just sets values as described in exampleValues.txt
	DBController dummyDBC = new DummyDBController();
	Course course = new Course("TDT4140", dummyDBC);
	
	@Test
	public void testGetCompletedLectureIDs() {
		ArrayList<Integer> actual = course.getCompletedLectureIDs();
		ArrayList<Integer> expected = new ArrayList<>(Arrays.asList(1, 2, 3));
		assertEquals(expected, actual);
	}
	
	@Test
	public void  TestgetCompletedLecturesIDDate() {
		LinkedHashMap<Integer, ArrayList<String>> actual = course.getCompletedLecturesIDDate();
		LinkedHashMap<Integer, ArrayList<String>> expected = new LinkedHashMap<>();
		expected.put(1, new ArrayList<String>(Arrays.asList("01-01-2017", "08:00:00")));
		expected.put(2, new ArrayList<String>(Arrays.asList("02-01-2017", "08:00:00")));
		expected.put(3, new ArrayList<String>(Arrays.asList("03-01-2017", "08:00:00")));
		
		assertEquals(expected, actual);
	}
	
	@Test
	public void  TestgetLastTwoCompletedLectureIDs() {
		ArrayList<Integer> actual = course.getLastTwoCompletedLectureIDs();
		ArrayList<Integer> expected = new ArrayList<Integer>(Arrays.asList(1,2));
		
		assertEquals(expected, actual);
	}
	
	@Test
	public void  TestgetLastTwoCompletedLectures() {
		LinkedHashMap<Integer, ArrayList<String>> actual = course.getLastTwoCompletedLectures();
		
		LinkedHashMap<Integer, ArrayList<String>> expected = new LinkedHashMap<>();
		expected.put(1, new ArrayList<String>(Arrays.asList("01-01-2017", "08:00:00")));
		expected.put(2, new ArrayList<String>(Arrays.asList("02-01-2017", "08:00:00")));
		
		assertEquals(expected, actual);
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
	public void  TestgetCourseLocation() {
		String actual = course.getCourseLocation();
		String expected = "Trondheim";
		
		assertEquals(expected, actual);
	}
	
	@Test
	public void  TestgetNumLectureHours() {
		int actual = course.getNumLectureHours();
		int expected = 4;
		
		assertEquals(expected, actual);
	}

	@Test
	public void  TestgetProfessorUsernames() {
		ArrayList<String> actual = course.getProfessorUsernames();
		ArrayList<String> expected = new ArrayList<>(Arrays.asList("pekkaa")) ;
		
		assertEquals(expected, actual);
	}

	@Test
	public void  TestgetLectureIDs() {
		
		ArrayList<Integer> actual = course.getLectureIDs();
		ArrayList<Integer> expected = new ArrayList<>(Arrays.asList(1,2,3));
		
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
		String expected = "01-01-2017";
		
		assertEquals(expected, actual);
	}
	
	
}
