package testing;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.GregorianCalendar;

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
		dbc.insertCourse("TDT4102", "Prosedyre- og objektorientert programmering", "Trondheim", 4);
		
		Course course = new Course("TDT4102");
		dbc.loadCourseInfo(course);
		
		assertEquals("TDT4102", course.getCourseCode());
		assertEquals("Prosedyre- og objektorientert programmering", course.getCourseName());
		assertEquals("Trondheim", course.getCourseLocation());
		assertEquals(4, course.getNumLectureHours());
		assertTrue(course.existsInDB());
		
		//Update
		
		//Delete
		dbc.deleteCourse("TDT4102");
		assertFalse(dbc.courseExists("TDT4102"));
			
	}
	
	@Test
	public void studentCRUD(){
		
		//Create
		dbc.insertStudent("inela", "BIT");
		
		Student stud = new Student("inela");
		dbc.loadStudentInfo(stud);
		
		assertEquals("inela", stud.getUsername());
		assertEquals("BIT", stud.getStudyProgram());
		assertEquals("inela@stud.ntnu.no", stud.getEmail());
		
		//Update
		
		//Delete
		dbc.deleteStudent("inela@stud.ntnu.no");
		assertFalse(dbc.studentExists("inela@stud.ntnu.no"));
	}
	
	@Test
	public void professorCRUD(){
		
		//Create
		dbc.insertProfessor("mariusth");
		
		Professor prof = new Professor("mariusth");
		dbc.loadProfessorInfo(prof);
		
		assertEquals("mariusth", prof.getUsername());
		
		//Update
		
		//Delete
		dbc.deleteProfessor("mariusth");
		assertFalse(dbc.professorExists("mariusth"));
		
	}
	
	@Test 
	public void lectureCRUD(){
		
		TestData.createTestDataLecture();
		
		//Create
		
		GregorianCalendar calendar = new GregorianCalendar(2016, 11, 10, 14, 15);
		dbc.insertLecture("2016-12-10", "14:15:00", "TTK4100", "tomgra");
		int lectureID = dbc.getLectureID(calendar, "TTK4100");
		Lecture lect = new Lecture(lectureID);
		assertEquals("TTK4100", lect.getCourseCode());
		//assertEquals(calendar, lect.getLectureDateAndTime()); 
		assertEquals("tomgra", lect.getProfessor());
		
		//Delete
		
		TestData.deleteTestData();
		
		
	}
	
	@Test
	public void evaluationCRUD(){
		
		TestData.createTestDataEvaluation();
		
		//Create
		
		GregorianCalendar calendar = new GregorianCalendar(2017, 3, 21, 8, 0);
		int lectureID = dbc.getLectureID(calendar,"IT2805");
		dbc.insertEvaluation("inela@stud.ntnu.no", lectureID, "Perfect", "Det var perfekt");
		Evaluation eval = new Evaluation(lectureID, "inela@stud.ntnu.no");
		
		assertEquals("Perfect", eval.getRating());
		assertEquals("Det var perfekt", eval.getComment());
		assertEquals("inela@stud.ntnu.no", eval.getStudentEmail());
		
		

		//Update
		 
		//Delete
		
		TestData.deleteTestData();
		assertFalse(eval.existsInDB());
	}
	
}