package testing;

import static org.junit.Assert.*;

import org.junit.Test;

import database.DBController;
import databaseobjects.Course;
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
		
		//Update
		
		//Delete
			
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
		
	}
	
	/*@Test 
	public void lectureCRUD(){
		
		//Create
		dbc.insertLecture("10-03-2017", "14:15", "TDT4140", "pekkaa");
		
	}*/
	
	/*@Test
	public void evaluationCRUD(){
		
		//Create
		dbc.insertEvaluation(studentEmail, lectureID, rating, studentComment);
		
		//Update
		 
		//Delete
	
	}*/
	
}