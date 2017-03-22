package testing;

import java.security.NoSuchAlgorithmException;
import java.util.GregorianCalendar;

import database.DBController;
import databaseobjects.Professor;

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
		dbc.insertCourseStudent("inela", "IT2805");
		dbc.insertProfessor("michailg", Professor.hashPassword("I like pizza"));
		dbc.insertCourseProfessor("michailg", "IT2805");
		dbc.insertLecture("2017-04-21", "08:00:00", "IT2805", "michailg");
	}
	
	public static void deleteTestDataEvaluation(){
		DBController dbc = new DBController();
		dbc.deleteCourse("IT2805");
		dbc.deleteStudent("inela");
		dbc.deleteProfessor("michailg");
	}
	
	public static void createTestDataLecture(){
		DBController dbc = new DBController();
		dbc.insertCourse("TTK4100", "Kybernetikk, introduksjon", "Trondheim", 3);
		dbc.insertStudent("inela", "BIT");
		dbc.insertCourseStudent("inela@stud.ntnu.no", "TTK4100");
		dbc.insertProfessor("tomgra", Professor.hashPassword("passSkidoodle"));
		dbc.insertCourseProfessor("tomgra", "TTK4100");
	}
	
	public static void deleteTestDataLecture(){
		DBController dbc = new DBController();
		dbc.deleteCourse("TTK4100");
		dbc.deleteStudent("inela");
		dbc.deleteProfessor("tomgra");
	}
	

}
