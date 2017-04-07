package testing;

import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.GregorianCalendar;

import database.DBController;
import databaseobjects.Professor;

/**
 * Methods for creating and deleting rows in
 * each table of the database for testing
 * 
 */

public class TestData {
	
	public static void createTestDataEvaluation() throws SQLException{
		DBController dbc = new DBController();
		dbc.insertCourse("TEST0001", "TestName", 3,1,1);
		dbc.insertStudent("testStudent", "BIT");
		dbc.insertCourseStudent("testStudent", "TEST0001");
		dbc.insertProfessor("testProfessor", Professor.hashPassword("pass"));
		dbc.insertCourseProfessor("testProfessor", "TEST0001");
		dbc.insertLecture("2017-04-21", "08:00:00", "TEST0001", "testProfessor");
	}
	
	public static void deleteTestDataEvaluation(){
		DBController dbc = new DBController();
		dbc.deleteCourse("TEST0001");
		dbc.deleteStudent("testStudent");
		dbc.deleteProfessor("testProfessor");
	}
	
	public static void createTestDataLecture(){
		DBController dbc = new DBController();
		dbc.insertCourse("TEST0001", "TestName", 3,1,1);
		dbc.insertStudent("testStudent", "BIT");
		dbc.insertCourseStudent("testStudent", "TEST0001");
		dbc.insertProfessor("testProfessor", Professor.hashPassword("pass"));
		dbc.insertCourseProfessor("testProfessor", "TEST0001");
	}
	
	public static void deleteTestDataLecture(){
		DBController dbc = new DBController();
		dbc.deleteCourse("TEST0001");
		dbc.deleteStudent("testStudent");
		dbc.deleteProfessor("testProfessor");
	}
	

}
