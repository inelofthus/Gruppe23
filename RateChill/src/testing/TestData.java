package testing;

import java.util.GregorianCalendar;

import database.DBController;

/**
 * Methods for creating and deleting rows in
 * each table of the database for testing
 * 
 */

public class TestData {
	
	public static void createTestDataEvaluation(){
		DBController dbc = new DBController();
		dbc.insertCourse("IT2805", "Webteknologi", "Trondheim", 3);
		dbc.insertStudent("inela", "BIT");
		dbc.insertCourseStudent("inela@stud.ntnu.no", "IT2805");
		dbc.insertProfessor("michailg");
		dbc.insertCourseProfessor("michailg", "IT2805");
		dbc.insertLecture("2017-04-21", "08:00:00", "IT2805", "michailg");
	}
	
	public static void createTestDataLecture(){
		DBController dbc = new DBController();
		dbc.insertCourse("TTK4100", "Kybernetikk, introduksjon", "Trondheim", 3);
		dbc.insertStudent("inela", "BIT");
		dbc.insertCourseStudent("inela@stud.ntnu.no", "TTK4100");
		dbc.insertProfessor("tomgra");
		dbc.insertCourseProfessor("tomgra", "TTK4100");
	}
	
	public static void deleteTestData(){
		DBController dbc = new DBController();
		dbc.deleteCourse("IT2805");
		dbc.deleteStudent("inela@stud.ntnu.no");
		dbc.deleteProfessor("michailg");
		
		
	}
	
}
