package testingDBObjects;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
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
	public Course loadCourseInfoForSemester(Course course, String semester) {
		// TODO Auto-generated method stub
		return super.loadCourseInfoForSemester(course, semester);
	}

	@Override
	public boolean checkProfessorPassword(String professorUsername, String encryptedPassword) throws SQLException {
		return encryptedPassword.equals(Professor.hashPassword("thePassword")) ;
	}

	@Override
	public void setCourseRatingsOverTime(Course course) {
		HashMap<Integer, Integer> IDRatingHash1 = new HashMap<>();
		HashMap<Integer, Integer> IDRatingHash2 = new HashMap<>();
		IDRatingHash2.put(1, 1);
		course.setLecIDtoRatingCount1(IDRatingHash1);
		course.setLecIDtoRatingCount2(IDRatingHash2);
		course.setLecIDtoRatingCount3(IDRatingHash1);
		course.setLecIDtoRatingCount4(IDRatingHash1);
		course.setLecIDtoRatingCount5(IDRatingHash1);
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
	public void insertCourse(String courseCode, String courseName, int lectureHours, int taughtInSpring, int taughtInAutumn) {
		//does nothing
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
	public Course loadCourseInfo(Course course) {
		course.setCourseName("Programvareutvikling");
		course.setNumLectureHours(4);
		course.setProfessorUsernames(new ArrayList<>(Arrays.asList("pekkaa")));
		course.setLectureIDs(new ArrayList<>(Arrays.asList(1, 2, 3)));
		course.setTaughtInAutumn(true);
		course.setTaughtInSpring(false);
		
		ArrayList<Integer> completedLectureIDs = new ArrayList<>(Arrays.asList(1, 2, 3));
		LinkedHashMap<Integer, ArrayList<String>> completedLecturesIDDate = new LinkedHashMap<>();
		completedLecturesIDDate.put(1, new ArrayList<String>(Arrays.asList("2017-01-01", "08:00:00")));
		completedLecturesIDDate.put(2, new ArrayList<String>(Arrays.asList("2017-01-02", "08:00:00")));
		completedLecturesIDDate.put(3, new ArrayList<String>(Arrays.asList("2017-01-03", "08:00:00")));
		
		course.setCompletedLectureIDs(completedLectureIDs);
		course.setCompletedLecturesIDDate(completedLecturesIDDate);
		
		return course;
	}

	@Override
	public void loadLectureInfo(Lecture lecture) {
		ArrayList<String> dateTime = new ArrayList<>(Arrays.asList("2017-01-01","08:00:00"));
		lecture.setLectureDateAndTime(dateTime);
		lecture.setCourseCode("TDT4140");
		lecture.setProfessor("pekkaa");
		
		Evaluation eval = new Evaluation("Ok", "yeah it was ok", 1, "karimj@stud.ntnu.no");
		lecture.setEvaluations(new ArrayList<>(Arrays.asList(eval)));
		lecture.setEvaluationsRating1(new ArrayList<Evaluation>());
		lecture.setEvaluationsRating2(new ArrayList<Evaluation>(Arrays.asList(eval)));
		lecture.setEvaluationsRating3(new ArrayList<Evaluation>());
		lecture.setEvaluationsRating4(new ArrayList<Evaluation>());
		lecture.setEvaluationsRating5(new ArrayList<Evaluation>());
		
	}

	@Override
	public void insertLecture(String date, String time, String courseCode, String professorUsername) {
		// do nothing
	}

	@Override
	public int getLectureID(ArrayList<String> dateTime, String courseCode) {
		// do nothing
		return 0;
	}

	@Override
	public void deleteLecture(int lectureID) {
		// do nothing
	}

	@Override
	public boolean lectureExists(int lectureID) {
		boolean ans = false;
		
		if(lectureID == 1)
			ans = true;
		
		return ans;
	}

	@Override
	public void loadProfessorInfo(Professor prof) {
		ArrayList<String> courseIDs = new ArrayList<>(Arrays.asList("TDT4140"));
		HashMap<String, String> courseIDNames = new HashMap<>();
		courseIDNames.put("TDT4140", "Programvareutvikling");
		
		prof.setCourseIDs(courseIDs);
		prof.setCourseIDNames(courseIDNames);
		
		
	}

	@Override
	public ArrayList<String> getCoursesTaughtByProfessor(String professorUsername) {
		return null;
	}

	@Override
	public void insertProfessor(String professorUsername, String password) {
		// do nothing
	}

	@Override
	public boolean professorExists(String professorUsername) {
		
		boolean ans = false;
		if(professorUsername == "pekkaa" )
			ans = true;
		
		return ans;
	}

	@Override
	public ArrayList<Integer> getCompletedLecturesForCourseByProfessor(String courseCode, String professorUsername) {
		return new ArrayList<>(Arrays.asList(1));
	}

	@Override
	public void deleteProfessor(String username) {
		// do nothing
	}

	@Override
	public void loadStudentInfo(Student student) {
		ArrayList<String> courseIDs = new ArrayList<>(Arrays.asList("TDT4140"));
		HashMap<String, String> courseIDNames = new HashMap<>();
		courseIDNames.put("TDT4140", "Programvareutvikling");
		
		student.setStudyProgram("MTING");
		student.setCourseIDs(courseIDs);
		student.setCourseIDNames(courseIDNames);
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
		boolean ans = false;
		
		if(studentEmail == "karimj@stud.ntnu.no")
			ans = true;
		
		return ans;
	}

	@Override
	public boolean studentHasEvaluatedLecture(String studentEmail, int lecID) {
		boolean ans = false;
			
			if(studentEmail == "karimj@stud.ntnu.no" && lecID == 1)
				ans = true;
			
		return ans;
		
	
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
		evaluation.setRating("Ok");
		evaluation.setComment("yeah it was ok");
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
		boolean ans = false;
		
		if(lectureid == 1 && studentEmail == "karimj@stud.ntnu.no")
			ans = true;
		
		return ans;
	}
	

}
