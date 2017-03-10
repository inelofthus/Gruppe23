package testingDBObjects;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.LinkedHashMap;
import database.DBController;
import databaseobjects.Course;
import databaseobjects.Evaluation;
import databaseobjects.Lecture;
import databaseobjects.Professor;
import databaseobjects.Student;

public class DummyDBController extends DBController{

	@Override
	public void connect() {
		//Does nothing
	}

	@Override
	public void close() {
		// does nothing
	}

	@Override
	public ArrayList<String> getStringArray(String query) {
		// TODO Auto-generated method stub
		return super.getStringArray(query);
	}

	@Override
	public ArrayList<Integer> getIntArray(String query) {
		// TODO Auto-generated method stub
		return super.getIntArray(query);
	}

	@Override
	public int getInt(String query) {
		// TODO Auto-generated method stub
		return super.getInt(query);
	}

	@Override
	public void insertCourse(String courseCode, String courseName, String courseLocation, int lectureHours) {
		
	}

	@Override
	public boolean courseExists(String courseCode) {
		boolean ans = false;
		
		if(courseCode == "TDT4140")
			return true;
		
		return ans;
	}

	@Override
	public void deleteCourse(String courseCode) {
		// do nothing
	}

	@Override
	public ArrayList<Integer> getLastTwoCompletedLecturesForCourse(String courseCode) {
		return null;
	}

	@Override
	public Course loadCourseInfo(Course course) {
		course.setCourseName("Programvareutvikling");
		course.setCourseLocation("Trondheim");
		course.setNumLectureHours(4);
		course.setProfessorUsernames(new ArrayList<>(Arrays.asList("pekkaa")));
		course.setLectureIDs(new ArrayList<>(Arrays.asList(1, 2, 3)));
		
		ArrayList<Integer> completedLectureIDs = new ArrayList<>(Arrays.asList(1, 2, 3));
		LinkedHashMap<Integer, ArrayList<String>> completedLecturesIDDate = new LinkedHashMap<>();
		completedLecturesIDDate.put(1, new ArrayList<String>(Arrays.asList("01-01-2017", "08:00:00")));
		completedLecturesIDDate.put(2, new ArrayList<String>(Arrays.asList("02-01-2017", "08:00:00")));
		completedLecturesIDDate.put(3, new ArrayList<String>(Arrays.asList("03-01-2017", "08:00:00")));
		
		course.setCompletedLectureIDs(completedLectureIDs);
		course.setCompletedLecturesIDDate(completedLecturesIDDate);
		
		return course;
	}

	@Override
	public void loadLectureInfo(Lecture lecture) {
		// do nothing
	}

	@Override
	public void insertLecture(String date, String time, String courseCode, String professorUsername) {
		// do nothing
	}

	@Override
	public int getLectureID(Calendar dateTime, String courseCode) {
		// do nothing
		return 0;
	}


	@Override
	public void deleteLecture(int lectureID) {
		// do nothing
	}

	@Override
	public boolean lectureExists(int lectureID) {
		return true;
	}

	@Override
	public void loadProfessorInfo(Professor prof) {
		// do nothing
	}

	@Override
	public ArrayList<String> getCoursesTaughtByProfessor(String professorUsername) {
		return null;
	}

	@Override
	public void insertProfessor(String professorUsername) {
		// do nothing
	}

	@Override
	public boolean professorExists(String professorUsername) {
		return true;
	}

	@Override
	public ArrayList<Integer> getCompletedLecturesForCourseByProfessor(String courseCode, String professorUsername) {
		return null;
	}

	@Override
	public void deleteProfessor(String username) {
		// do nothing
	}

	@Override
	public void loadStudentInfo(Student student) {
		// do nothing
	}

	@Override
	public void insertStudent(String studentUsername, String studyProgramCode) {
		// do nothing
	}

	@Override
	public void deleteStudent(String studentEmail) {
		// do nothing
	}

	@Override
	public boolean studentExists(String studentEmail) {
		return true;
	}

	@Override
	public boolean studentHasEvaluatedLecture(String studentEmail, int lecID) {
		return true;
	}

	@Override
	public void insertCourseProfessor(String professorUsername, String courseCode) {
		// do nothing
	}

	@Override
	public void insertCourseStudent(String studentEmail, String courseCode) {
		// do nothing
	}

	@Override
	public void loadEvaluationInfo(Evaluation evaluation) {
		// do nothing
	}

	@Override
	public void overwriteEvaluation(String email, int lectureID, String rating, String comment) {
		// do nothing
	}

	@Override
	public void insertEvaluation(String studentEmail, int lectureID, String rating, String studentComment) {
		// do nothing
	}

	@Override
	public boolean evaluationExists(int lectureid, String studentEmail) {
		return true;
	}

	@Override
	protected Object clone() throws CloneNotSupportedException {
		// TODO Auto-generated method stub
		return super.clone();
	}

	@Override
	public boolean equals(Object obj) {
		// TODO Auto-generated method stub
		return super.equals(obj);
	}

	@Override
	protected void finalize() throws Throwable {
		// TODO Auto-generated method stub
		super.finalize();
	}

	@Override
	public int hashCode() {
		// TODO Auto-generated method stub
		return super.hashCode();
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return super.toString();
	}
	
	
	
	

}
