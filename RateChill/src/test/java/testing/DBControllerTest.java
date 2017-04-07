package testing;

import static org.junit.Assert.*;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
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
	public void professorCRUD(){
		
		//Create
		dbc.insertProfessor("testProf", Professor.hashPassword("pass"));
		
		Professor prof = new Professor("testProf");
		dbc.loadProfessorInfo(prof);
		
		assertEquals("testProf", prof.getUsername());
		
		assertTrue(prof.existsInDB());
		assertTrue(dbc.professorExists("testProf"));
		
		//Update
		
		//Delete
		dbc.deleteProfessor("testProf");
		assertFalse(prof.existsInDB());
		assertFalse(dbc.professorExists("testProf"));
		
		
	}
	
	@Test 
	public void lectureCRUD() throws SQLException{
		
		TestData.createTestDataLecture();
		
		//Create
		
//		GregorianCalendar calendar = new GregorianCalendar(2016, 11, 10, 14, 15);
		ArrayList<String> dateTime = new ArrayList<>(Arrays.asList("2016-12-10","14:15:00"));
		dbc.insertLecture("2016-12-10", "14:15:00", "TEST0001", "testProf");
		int lectureID = dbc.getLectureID(dateTime, "TEST0001");
		Lecture lect = new Lecture(lectureID);
		assertEquals("TEST0001", lect.getCourseCode());
		//assertEquals(calendar, lect.getLectureDateAndTime()); 
		assertEquals("testProf", lect.getProfessor());
		
		assertTrue(lect.existsInDB());
		assertTrue(dbc.lectureExists(lectureID));
		
		//Delete
		
		TestData.deleteTestDataLecture();
		assertFalse(lect.existsInDB());
		assertFalse(dbc.lectureExists(lectureID));
		
	}
	
	@Test
	public void evaluationCRUD() throws SQLException{
		
		TestData.createTestDataEvaluation();
		
		//Create
		
//		GregorianCalendar calendar = new GregorianCalendar(2017, 3, 21, 8, 0);
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
		assertTrue(dbc.evaluationExists(lectureID, "testStud"));
		
		//Delete
		TestData.deleteTestDataEvaluation();
		assertFalse(eval.existsInDB());
		assertFalse(updatedEval.existsInDB());
		assertFalse(dbc.evaluationExists(lectureID, "testStud"));
	}
	
}