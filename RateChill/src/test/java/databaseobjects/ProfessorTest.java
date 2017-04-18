package databaseobjects;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

import org.junit.Test;
import database.DBController;
import databaseobjects.Professor;

public class ProfessorTest {

	// A dummy DBC has no interaction with database. It just sets values as described in exampleValues.txt
		DBController dummyDBC = new DummyDBController();
		Professor prof = new Professor("pekkaa", Professor.hashPassword("thePassword"), dummyDBC);
		Professor prof2 = new Professor("testProf", "np", dummyDBC);
		
		
		@Test
		public void testgetCourseIDNames() {
			HashMap<String, String> actual = prof.getCourseIDNames();
			HashMap<String, String> expected = new HashMap<>();
			expected.put("TDT4140", "Programvareutvikling");
			
			assertEquals(expected, actual);
		}
		
		@Test
		public void testgetLecturesForCourse() {
			ArrayList<Integer> actual = prof.getLecturesForCourse("TDT4140");
			ArrayList<Integer> expected = new ArrayList<>(Arrays.asList(1));
			
			assertEquals(expected, actual);
		}
		
		@Test
		public void testgetLastTwoCompletedLecturesForCourse() {
			ArrayList<Integer> actual = prof.getLastTwoCompletedLecturesForCourse("TDT4140");
			ArrayList<Integer> expected = new ArrayList<>(Arrays.asList(1));
			
			assertEquals(expected, actual);
			
			actual = prof2.getLastTwoCompletedLecturesForCourse("TDT4140");
			expected = new ArrayList<>(Arrays.asList(1,2));
			
			assertEquals(expected, actual);
		}

		@Test
		public void testgetCourseIDs() {
			ArrayList<String> actual = prof.getCourseIDs();
			ArrayList<String> expected = new ArrayList<>(Arrays.asList("TDT4140"));
			
			assertEquals(expected, actual);
		}
		
		@Test
		public void testgetCourseNameForCourse() {
			String actual = prof.getCourseNameForCourse("TDT4140");
			String expected =  "Programvareutvikling";
			
			assertEquals(expected, actual);
		}
		
		@Test
		public void testhashPassword() {
			// just checks that the same value comes out every time
			String actual = Professor.hashPassword("thePassword");
			String expected =  Professor.hashPassword("thePassword");
			
			assertEquals(expected, actual);
		}
		
		@Test
		public void testisCorrectPassword() {
			
			boolean actual = prof.isCorrectPassword(Professor.hashPassword("thePassword"));
			boolean expected =  true;
			
			assertEquals(expected, actual);
			
			boolean actual2 = prof.isCorrectPassword(Professor.hashPassword("theWrongPassword"));
			boolean expected2 =  false;
			
			assertEquals(expected2, actual2);
		}
		
		@Test
		public void testhasPassword(){
			assertTrue(prof.hasPassword());
		}
}
