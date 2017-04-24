package databaseobjectstest;

import static org.junit.Assert.assertEquals;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import org.junit.Test;
import database.DBController;
import databaseobjects.Student;

public class StudentTest {

	// A dummy DBC has no interaction with database. It just sets values as described in exampleValues.txt
		DBController dummyDBC = new DummyDBController();
		Student stud = new Student("karimj", dummyDBC);
		
		
		@Test
		public void testgetCourseIDNames() {
			HashMap<String, String> actual = stud.getCourseIDNames();
			HashMap<String, String> expected = new HashMap<>();
			expected.put("TDT4140", "Programvareutvikling");
			
			assertEquals(expected, actual);
		}
	
		@Test
		public void testgetCourseNameForCourse() {
			String actual = stud.getCourseNameForCourse("TDT4140");
			String expected = "Programvareutvikling";
			
			assertEquals(expected, actual);
		}
		
		@Test
		public void testgetUsername() {
			String actual = stud.getUsername();
			String expected = "karimj";
			
			assertEquals(expected, actual);
		}
		
		@Test
		public void testgetCourseIDs() {
			ArrayList<String> actual = stud.getCourseIDs();
			ArrayList<String> expected = new ArrayList<>(Arrays.asList("TDT4140"));
			
			assertEquals(expected, actual);
		}
		
		@Test
		public void testaddCourse(){
			stud.addCourse("TDT1234", "testCourse");
			ArrayList<String> expectedCourseIDs = new ArrayList<>(Arrays.asList("TDT4140", "TDT1234"));
			ArrayList<String> actualCourseIDs = stud.getCourseIDs();
			
			assertEquals(expectedCourseIDs, actualCourseIDs);
		}
		
		@Test
		public void testremoveCourse(){
			stud.removeCourse("TDT1234");
			ArrayList<String> expectedCourseIDs = new ArrayList<>(Arrays.asList("TDT4140" ));
			ArrayList<String> actualCourseIDs = stud.getCourseIDs();
			
			assertEquals(expectedCourseIDs, actualCourseIDs);
		}
		
		@Test
		public void testhasEvaluationLecture(){
			boolean actual = stud.hasEvaluatedLecture(1);
			boolean expected = true;
			
			assertEquals(expected, actual);
		}
		
		
}
