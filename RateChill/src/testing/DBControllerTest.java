package testing;

import static org.junit.Assert.*;

import java.util.ArrayList;

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
	
	/*@Test 
	public void lectureCRUD(){
		
		//Create
		dbc.insertLecture("10-03-2017", "14:15", "TDT4140", "pekkaa");
		
		//Delete
		dbc.deleteLecture 
		
		
	}*/
	
	/*@Test
	public void evaluationCRUD(){
		
		TestData.createTestDataEvaluation();
		//Create
		int lectureID = dbc.getLectureID("04-11-2017", "IT2805");
		dbc.insertEvaluation("inela@stud.ntnu.no", lectureID, "Perfect", "Det var perfekt");
		
		ArrayList<String> RatingComment = dbc.getEvaluationRatingAndComment(lectureID, "inela@stud.ntnu.no");
		assertEquals("Perekt", RatingComment.get(0));
		assertEquals("Det var pefekt", RatingComment.get(1));
		//Update
		 
		//Delete
		
		
		TestData.deleteTestData();
	}*/
	
}