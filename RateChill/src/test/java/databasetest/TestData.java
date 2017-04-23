package databasetest;

import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.GregorianCalendar;

import database.DBController;
import databaseobjects.Professor;

/*
 * Methods for creating and deleting rows in
 * each table of the database for testing
 * 
 */

public class TestData {
	
	public static void createTestData() throws SQLException{
		DBController dbc = new DBController();
		dbc.insertCourse("TEST0001", "TestName", 3,1,1);
		dbc.insertStudent("testStud", "BIT");
		dbc.insertCourseStudent("testStud", "TEST0001");
		dbc.insertProfessor("testProf", Professor.hashPassword("pass"));
		dbc.insertCourseProfessor("testProf", "TEST0001");
		dbc.insertLecture("2017-01-21", "08:00:00", "TEST0001", "testProf");
	}
	
	public static void deleteTestData(){
		DBController dbc = new DBController();
		dbc.deleteCourse("TEST0001");
		dbc.deleteStudent("testStud");
		dbc.deleteProfessor("testProf");
	}
	
	public static void createTestDataEvaluation() throws SQLException{
		DBController dbc = new DBController();
		dbc.insertCourse("TEST0001", "TestName", 3,1,1);
		dbc.insertStudent("testStud", "BIT");
		dbc.insertCourseStudent("testStud", "TEST0001");
		dbc.insertProfessor("testProf", Professor.hashPassword("pass"));
		dbc.insertCourseProfessor("testProf", "TEST0001");
		dbc.insertLecture("2017-04-21", "08:00:00", "TEST0001", "testProf");
	}
	
	
	public static void createTestDataLecture(){
		DBController dbc = new DBController();
		dbc.insertCourse("TEST0001", "TestName", 3,1,1);
		dbc.insertStudent("testStud", "BIT");
		dbc.insertCourseStudent("testStud", "TEST0001");
		dbc.insertProfessor("testProf", Professor.hashPassword("pass"));
		dbc.insertCourseProfessor("testProf", "TEST0001");
	}
	

}
