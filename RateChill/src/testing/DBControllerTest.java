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
		dbc.insertStudent("testStudent", "BIT");
		
		Student stud = new Student("testStudent");
		dbc.loadStudentInfo(stud);
		
		assertEquals("testStudent", stud.getUsername());
		assertEquals("BIT", stud.getStudyProgram());
		
		assertTrue(dbc.studentExists("testStudent"));
		assertTrue(stud.existsInDB());
		
		//Update
		
		//Delete
		dbc.deleteStudent("testStudent");
		assertFalse(dbc.studentExists("testStudent"));
		assertFalse(stud.existsInDB());
	}
	
	@Test
	public void professorCRUD(){
		
		//Create
		dbc.insertProfessor("testProfessor", Professor.hashPassword("pass"));
		
		Professor prof = new Professor("testProfessor");
		dbc.loadProfessorInfo(prof);
		
		assertEquals("testProfessor", prof.getUsername());
		
		assertTrue(prof.existsInDB());
		assertTrue(dbc.professorExists("testProfessor"));
		
		//Update
		
		//Delete
		dbc.deleteProfessor("testProfessor");
		assertFalse(prof.existsInDB());
		assertFalse(dbc.professorExists("testProfessor"));
		
	}
	
	@Test 
	public void lectureCRUD() throws SQLException{
		
		TestData.createTestDataLecture();
		
		//Create
		
//		GregorianCalendar calendar = new GregorianCalendar(2016, 11, 10, 14, 15);
		ArrayList<String> dateTime = new ArrayList<>(Arrays.asList("2016-12-10","14:15:00"));
		dbc.insertLecture("2016-12-10", "14:15:00", "TTK4100", "tomgra");
		int lectureID = dbc.getLectureID(dateTime, "TTK4100");
		Lecture lect = new Lecture(lectureID);
		assertEquals("TTK4100", lect.getCourseCode());
		//assertEquals(calendar, lect.getLectureDateAndTime()); 
		assertEquals("tomgra", lect.getProfessor());
		
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
		int lectureID = dbc.getLectureID(dateTime,"IT2805");
		dbc.insertEvaluation("inela", lectureID, "Perfect", "Det var perfekt");
		Evaluation eval = new Evaluation(lectureID, "inela");
		
		assertEquals("Perfect", eval.getRating());
		assertEquals("Det var perfekt", eval.getComment());
		assertEquals("inela", eval.getstudentUsername());
		
		assertTrue(eval.existsInDB());
		

		//Update
		
		Student stud = new Student("inela");
		stud.overWriteEvaluation(lectureID, "Too Slow", "Nesten perfekt, men det gikk litt for sakte");
		Evaluation updatedEval = new Evaluation(lectureID, "inela");
		assertEquals("Too Slow", updatedEval.getRating());
		assertEquals("Nesten perfekt, men det gikk litt for sakte", updatedEval.getComment());
		assertEquals("inela", updatedEval.getstudentUsername());
		
		assertTrue(updatedEval.existsInDB());
		assertTrue(dbc.evaluationExists(lectureID, "inela"));
		
		//Delete
		
		TestData.deleteTestDataEvaluation();
		assertFalse(eval.existsInDB());
		assertFalse(updatedEval.existsInDB());
		assertFalse(dbc.evaluationExists(lectureID, "inela"));
	}
	
}