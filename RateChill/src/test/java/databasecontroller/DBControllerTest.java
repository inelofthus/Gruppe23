package databasecontroller;

import static org.junit.Assert.*;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

import org.junit.Test;
import database.DBController;
import databaseobjects.Course;
import databaseobjects.Evaluation;
import databaseobjects.Lecture;
import databaseobjects.Professor;
import databaseobjects.Student;

/** This class contains CRUD tests for 
 * 	the tables in the database.
 */

public class DBControllerTest {
	
	DBController dbc  = new DBController();
	
	@Test
	public void courseCRUD(){
		TestData.deleteTestData();
				
		//Create
		dbc.insertCourse("TEST0001", "TestName", 4, 1, 1);
		
		Course course = new Course("TEST0001");
		dbc.loadCourseInfo(course);
		
		assertEquals("TEST0001", course.getCourseCode());
		assertEquals("TestName", course.getCourseName());
		assertEquals(4, course.getNumLectureHours());
		
		assertTrue(course.existsInDB());
		assertTrue(dbc.courseExists("TEST0001"));
		
		//Update
		dbc.insertCourseRatingValues("TEST0001", "Good", "Very good", "Bad", "Very bad", "OK");
		dbc.loadCourseInfo(course);
		ArrayList<String> expRateVal = new ArrayList<String>(Arrays.asList
				("Good", "Very good", "Bad", "Very bad", "OK")); 
		assertEquals(expRateVal, course.getRatingValues());
		
		
		//Delete
		dbc.deleteCourse("TEST0001");
		assertFalse(dbc.courseExists("TEST0001"));
		assertFalse(course.existsInDB());
			
	}
	
	@Test
	public void studentCRUD(){
		
		//Create
		dbc.insertStudent("testStud", "BIT");
		
		Student stud = new Student("testStud");
		dbc.loadStudentInfo(stud);
		
		assertEquals("testStud", stud.getUsername());
		assertEquals("BIT", stud.getStudyProgram());
		
		assertTrue(dbc.studentExists("testStud"));
		assertTrue(stud.existsInDB());
		
		//Update
		
		//Delete
		dbc.deleteStudent("testStud");
		assertFalse(dbc.studentExists("testStud"));
		assertFalse(stud.existsInDB());
	}
	
	@Test
	public void professorCRUD() throws SQLException{
		
		//Create
		dbc.insertProfessor("testProf", Professor.hashPassword("pass"));
		
		Professor prof = new Professor("testProf");
		dbc.loadProfessorInfo(prof);
		
		assertEquals("testProf", prof.getUsername());
		
		assertTrue(prof.existsInDB());
		assertTrue(dbc.professorExists("testProf"));
		
		//Update
		dbc.updateProfessor("testProf", Professor.hashPassword("hei"));
		assertTrue(dbc.checkProfessorPassword("testProf", Professor.hashPassword("hei")));
		
		//Delete
		dbc.deleteProfessor("testProf");
		assertFalse(prof.existsInDB());
		assertFalse(dbc.professorExists("testProf"));
		
		
	}
	
	@Test 
	public void lectureCRUD() throws SQLException{
		
		TestData.createTestDataLecture();
		
		//Create
		
		ArrayList<String> dateTime = new ArrayList<>(Arrays.asList("2016-12-10","14:15:00"));
		dbc.insertLecture("2016-12-10", "14:15:00", "TEST0001", "testProf");
		int lectureID = dbc.getLectureID(dateTime, "TEST0001");
		Lecture lect = new Lecture(lectureID);
		assertEquals("TEST0001", lect.getCourseCode());
		//assertEquals(calendar, lect.getLectureDateAndTime()); 
		assertEquals("testProf", lect.getProfessor());
		
		assertTrue(lect.existsInDB());
		assertTrue(dbc.lectureExists(lectureID));
		
		dbc.addLectures("TEST0001", "08:00:00", "2017-01-01", "2017-01-16", true, "testProf");
		ArrayList<String> lecDT = new ArrayList<String>();
		lecDT.addAll(Arrays.asList("10.12.2016 \t \t 14:15:00", "01.01.2017 \t \t 08:00:00",
				"08.01.2017 \t \t 08:00:00", "15.01.2017 \t \t 08:00:00"));
		assertEquals(dbc.getLectureDateAndTimeForCourse("TEST0001"), lecDT);
		//Delete
		
		dbc.deleteLecture(lectureID);
		TestData.deleteTestData();
		assertFalse(lect.existsInDB());
		assertFalse(dbc.lectureExists(lectureID));
		
	}
	
	@Test
	public void evaluationCRUD() throws SQLException{
		
		TestData.createTestDataEvaluation();
		
		//Create
		
		ArrayList<String> dateTime = new ArrayList<>(Arrays.asList("2017-04-21","08:00:00"));
		int lectureID = dbc.getLectureID(dateTime,"TEST0001");
		dbc.insertEvaluation("testStud", lectureID, "Perfect", "Det var perfekt");
		Evaluation eval = new Evaluation(lectureID, "testStud");
		
		assertEquals("Perfect", eval.getRating());
		assertEquals("Det var perfekt", eval.getComment());
		assertEquals("testStud", eval.getstudentUsername());
		
		assertTrue(eval.existsInDB());
		

		//Update
		
		Student stud = new Student("testStud");
		stud.overWriteEvaluation(lectureID, "Too Slow", "Nesten perfekt, men det gikk litt for sakte");
		Evaluation updatedEval = new Evaluation(lectureID, "testStud");
		assertEquals("Too Slow", updatedEval.getRating());
		assertEquals("Nesten perfekt, men det gikk litt for sakte", updatedEval.getComment());
		assertEquals("testStud", updatedEval.getstudentUsername());
		
		assertTrue(updatedEval.existsInDB());
		assertTrue(dbc.studentHasEvaluatedLecture("testStud", lectureID));
		assertTrue(dbc.evaluationExists(lectureID, "testStud"));
		
		//Delete
		dbc.deleteCourseStudent("testStud", "TEST0001");
		TestData.deleteTestData();
		assertFalse(eval.existsInDB());
		assertFalse(updatedEval.existsInDB());
		assertFalse(dbc.evaluationExists(lectureID, "testStud"));
	}
	
	@Test
	public void professorCourseCRUD() throws SQLException{
		TestData.createTestData();
		ArrayList<String> profCourses = new ArrayList<String>();
		profCourses.add("TEST0001");
		
		assertEquals(dbc.getCoursesTaughtByProfessor("testProf"), profCourses);
		
		ArrayList<String> dateTime = new ArrayList<>(Arrays.asList("2017-01-21","08:00:00"));
		int lectureID = dbc.getLectureID(dateTime, "TEST0001");
		ArrayList<Integer> compLec = new ArrayList<Integer>();
		compLec.add(lectureID);
		assertEquals(compLec, dbc.getCompletedLecturesForCourseByProfessor("TEST0001", "testProf"));
		
		TestData.deleteTestData();
	}
	
	@Test
	public void dbcMethods(){
		assertEquals(dbc.changeDateFormat("2017-05-01"), "01.05.2017");
	}
	
}