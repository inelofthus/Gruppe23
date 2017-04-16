package databaseobjects;

import static org.junit.Assert.assertEquals;
import java.util.ArrayList;
import java.util.Arrays;
import org.junit.Test;
import database.DBController;
import databaseobjects.Lecture;

public class LectureTest {
	
	// A dummy DBC has no interaction with database. It just sets values as described in exampleValues.txt
		DBController dummyDBC = new DummyDBController();
		Lecture lec = new Lecture(1, dummyDBC);
		
		@Test
		public void testgetLectureID() {
			int actual = lec.getLectureID();
			int expected = 1;
			
			assertEquals(expected, actual);
		}
		
		@Test
		public void testgetLectureDateAndTime() {
			ArrayList<String> actual = lec.getLectureDateAndTime();
			ArrayList<String> expected = new ArrayList<String>(Arrays.asList("2017-01-01","08:00:00"));
			
			assertEquals(expected, actual);
		}
		
		@Test
		public void testgetLectureDate() {
			String actual = lec.getLectureDate();
			String expected = "2017-01-01";
			
			assertEquals(expected, actual);
		}
		
		@Test
		public void testgetLectureTime() {
			String actual = lec.getLectureTime();
			String expected = "08:00:00";
			
			assertEquals(expected, actual);
		}
		
		@Test
		public void testgetCourseCode() {
			String actual = lec.getCourseCode();
			String expected = "TDT4140";
			
			assertEquals(expected, actual);
		}
		
		@Test
		public void testgetProfessor() {
			String actual = lec.getProfessor();
			String expected = "pekkaa";
			
			assertEquals(expected, actual);
		}

		@Test
		public void testgetEvaluations() {
			String actual = lec.getEvaluations().get(0).getRating();
			String expected = "Ok";		
			assertEquals(expected, actual);
			
			ArrayList<Evaluation> expectedEvalSpecific = new ArrayList<>();
			ArrayList<Evaluation> actualEvalSpecific = lec.getEvaluationsRating1();
			assertEquals(expectedEvalSpecific, actualEvalSpecific);

			actualEvalSpecific = lec.getEvaluationsRating3();
			assertEquals(expectedEvalSpecific, actualEvalSpecific);
			
			actualEvalSpecific = lec.getEvaluationsRating4();
			assertEquals(expectedEvalSpecific, actualEvalSpecific);
			
			actualEvalSpecific = lec.getEvaluationsRating5();
			assertEquals(expectedEvalSpecific, actualEvalSpecific);
			
			actual = lec.getEvaluationsRating2().get(0).getRating();
			expected = "Ok";		;
			assertEquals(expected, actual);
		}
		
		@Test
		public void testgetRatingCount() {
			int actual = lec.getRatingCount(1);
			int expected = 0;
			assertEquals(expected, actual);
				
			actual = lec.getRatingCount(2);
			expected = 1;
			assertEquals(expected, actual);
			
			actual = lec.getRatingCount(3);
			expected = 0;
			assertEquals(expected, actual);
			
			actual = lec.getRatingCount(4);
			expected = 0;
			assertEquals(expected, actual);
			
			actual = lec.getRatingCount(5);
			expected = 0;
			
			assertEquals(expected, actual);
		}
		
		@Test(expected=IllegalArgumentException.class)
		public void testgetRatingCountBadInput(){
			lec.getRatingCount(6);
		}
		
		@Test
		public void testgetRatingValues(){
			System.out.println(lec.getRatingValues());
		}

}
