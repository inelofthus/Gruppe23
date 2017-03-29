package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.LinkedHashMap;

import com.mysql.jdbc.PreparedStatement;

import databaseobjects.Course;
import databaseobjects.Evaluation;
import databaseobjects.Lecture;
import databaseobjects.Professor;
import databaseobjects.Student;

public class DBController {

	Connection conn = null;

	public void connect() {
		try {
			conn = DriverManager.getConnection(
					"jdbc:mysql://mysql.stud.ntnu.no/segroup23_db?user=segroup23_user&password=pekkabot");
			// System.out.println("connection succesfull :) ");

		} catch (SQLException ex) {
			System.out.println("SQLeXCEPTION: " + ex.getMessage());

		}
	}

	public void close() {
		if (rs != null) {
			try {
				rs.close();
			} catch (SQLException e) {
				/* ignored */}
		}

		if (conn != null) {
			try {
				conn.close();
				// System.out.println("CONNECTION CLOSED");
			} catch (SQLException e) {
				/* ignored */}
		}

	}

	Statement stmt = null;
	java.sql.PreparedStatement prepStmt = null;
	ResultSet rs = null;

	// Frequently used SQL methods
	public ArrayList<String> getStringArray(String query) {
		// takes an SQL query as input. Returns a string that contains all data
		// from first column of table
		connect();

		ArrayList<String> list = new ArrayList<>();
		try {
			stmt = conn.createStatement();

			if (stmt.execute(query)) {
				rs = stmt.getResultSet();
			}

			while (rs.next()) {
				list.add(rs.getString(1));
			}

		} catch (Exception e) {
			System.out.println("SQLException: " + e.getMessage());
		}

		close();
		// System.out.println(professor);
		return list;
	}

	public ArrayList<Integer> getIntArray(String query) {
		// takes an sqlQuery as input and returns a list containing the String
		// elements of the first column
		connect();

		ArrayList<Integer> list = new ArrayList<>();
		try {
			stmt = conn.createStatement();

			if (stmt.execute(query)) {
				rs = stmt.getResultSet();
			}

			while (rs.next()) {
				list.add(rs.getInt(1));
			}

		} catch (Exception e) {
			System.out.println("SQLException: " + e.getMessage());
		}

		close();
		// System.out.println(professor);
		return list;
	}

	public int getInt(String query) {
		// takes an sqlQuery as input and returns a list containing the int
		// elements of the first column

		int ans = 0;
		connect();

		try {
			stmt = conn.createStatement();

			if (stmt.execute(query)) {
				rs = stmt.getResultSet();
			}

			rs.next();
			ans = rs.getInt(1);

		} catch (Exception e) {
			System.out.println("SQLException: " + e.getMessage());
		}

		close();
		return ans;

	}

	// Course info

	public void insertCourse(String courseCode, String courseName, int lectureHours, int taughtInSpring, int taughtInAutumn) {
		// inserts a new row in Course table of database
		connect();

		try {
			String query = "INSERT INTO Course VALUES(?,?,?,?,?)";

			prepStmt = conn.prepareStatement(query);
			prepStmt.setString(1, courseCode);
			prepStmt.setString(2, courseName);
			prepStmt.setInt(3, lectureHours);
			prepStmt.setInt(4, taughtInSpring);
			prepStmt.setInt(5, taughtInAutumn);
			int i = prepStmt.executeUpdate();
			System.out.println(i + " records inserted"); 

//			StringBuilder sb = new StringBuilder();
//			sb.append("INSERT INTO Course VALUES('").append(courseCode).append("','").append(courseName).append("','")
//					.append(courseLocation).append("',").append(lectureHours).append(");");
//
//			String query = sb.toString();
//
//			stmt = conn.createStatement();
//			stmt.executeUpdate(query);

		} catch (Exception e) {
			System.out.println("SQLException: " + e.getMessage());
		}

		close();

	}

	public void insertCourseCon(String courseCode, String courseName, int lectureHours, boolean taughtInSpring, boolean taughtInAutumn) {
		// inserts a new row in Course table of database when already connected
		try {
			String query = "INSERT INTO Course VALUES(?,?,?,?,?)";

			prepStmt = conn.prepareStatement(query);
			prepStmt.setString(1, courseCode);
			prepStmt.setString(2, courseName);
			prepStmt.setInt(3, lectureHours);
			prepStmt.setBoolean(4, taughtInSpring);
			prepStmt.setBoolean(5, taughtInAutumn);
			int i = prepStmt.executeUpdate();
			System.out.println(i + " records inserted"); 
			
		} catch (Exception e) {
			System.out.println("SQLException: " + e.getMessage());
		}

	}
	
	public boolean courseExists(String courseCode) {
		// Checks in the database if a course with given courseCode exists.
		connect();
		boolean hasNext = false;
		try {
			stmt = conn.createStatement();

			String query = "SELECT courseCode FROM Course WHERE courseCode = '" + courseCode + "';";

			if (stmt.execute(query)) {
				rs = stmt.getResultSet();
			}

			hasNext = rs.next();

		} catch (Exception e) {
			System.out.println("SQLException: " + e.getMessage());
		}
		close();
		return hasNext;
	}

	public void deleteCourse(String courseCode) {
		connect();
		try {
			String query = "DELETE FROM Course WHERE courseCode='" + courseCode + "'";

			stmt = conn.createStatement();
			stmt.executeUpdate(query);

		} catch (Exception e) {
			System.out.println("SQLException: " + e.getMessage());
		}
		close();

	}

	public Course loadCourseInfo(Course course) {
		// This method takes in a Course object with a specific courseCode. It
		// collects all the information about this course for the newest
		// semester and fills in the rest
		// of the course details. Finally It will return the loaded course
		// object.

		connect();

		try {
			stmt = conn.createStatement();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		setCourseNameLecHoursAndSemester(course);
		setProfessorIDsForCourse(course);
		setLectureIDsForCourse(course);
		setCompletedLecturesForCourse(course);

		close();

		return course;
	}

	public Course loadCourseInfoForSemester(Course course, String semester) {
		// This method takes in a Course object with a specific courseCode and
		// semester. It
		// collects all the information about this course and fills in the rest
		// of the course details. Finally It will return the loaded course
		// object.

		connect();

		try {
			stmt = conn.createStatement();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		setCourseNameLecHoursAndSemester(course);
		setProfessorIDsForCourse(course);
		setLectureIDsForCourse(course);
		setCompletedLecturesForCourse(course, semester);

		close();

		return course;
	}

	private void setCompletedLecturesForCourse(Course course, String semester) {
		// This creates the list and linked Hash map with the completed lectures
		// in a given semester and their dates and sets the result in the course
		// object

		char season = semester.charAt(0);
		String year = semester.substring(1);

		try {

			ArrayList<Integer> completedLectureIDs = new ArrayList<>();
			LinkedHashMap<Integer, ArrayList<String>> completedLecturesIDDate = new LinkedHashMap<>();

			StringBuilder sb = new StringBuilder();

			if (season == 'V') {
				sb.append("SELECT lectureID, lectureDate, lectureTime  FROM Lecture AS l WHERE courseCode = '")
						.append(course.getCourseCode()).append("' ")
						.append(" AND (lectureDate < now() OR (lectureDate = now()  AND lectureTime < now())) AND (MONTH(l.lectureDate) < 7) AND ( YEAR(l.lectureDate) = ")
						.append(year).append(") ORDER BY lectureDate DESC, lectureTime DESC;");

			} else {
				sb.append("SELECT lectureID, lectureDate, lectureTime  FROM Lecture AS l WHERE courseCode = '")
						.append(course.getCourseCode()).append("' ")
						.append(" AND (lectureDate < now() OR (lectureDate = now()  AND lectureTime < now())) AND (MONTH(l.lectureDate) > 7) AND ( YEAR(l.lectureDate) = ")
						.append(year).append(") ORDER BY lectureDate DESC, lectureTime DESC;");
			}

			String query4 = sb.toString();
			System.out.println(query4);

			if (stmt.execute(query4)) {
				rs = stmt.getResultSet();
			}

			while (rs.next()) {
				int lecID = rs.getInt(1);
				String date = rs.getString(2);
				String time = rs.getString(3);
				ArrayList<String> dateTime = new ArrayList<String>();
				dateTime.add(date);
				dateTime.add(time);

				completedLecturesIDDate.put(lecID, dateTime);
				completedLectureIDs.add(lecID);
			}

			course.setCompletedLectureIDs(completedLectureIDs);
			;
			course.setCompletedLecturesIDDate(completedLecturesIDDate);

		} catch (Exception e) {
			System.out.println("error in helper function DBC.setLastTwoCompletedLecturesForCourse:" + e.getMessage());
		}

	}

	private void setCompletedLecturesForCourse(Course course) {
		// This creates the list and linked Hash map with the last 2 completed
		// lectures and their dates and sets the result in the course object
		try {

			ArrayList<Integer> completedLectureIDs = new ArrayList<>();
			LinkedHashMap<Integer, ArrayList<String>> completedLecturesIDDate = new LinkedHashMap<>();
			int year = Calendar.getInstance().get(Calendar.YEAR);

			StringBuilder sb = new StringBuilder();
			sb.append("SELECT lectureID, lectureDate, lectureTime  FROM Lecture WHERE courseCode = '")
					.append(course.getCourseCode()).append("' ")
					.append(" AND (lectureDate < now() OR (lectureDate = now()  AND lectureTime < now()))")
					.append(" AND  YEAR(lectureDate) = ").append(year).append(" ORDER BY lectureDate DESC, lectureTime DESC;");

			String query4 = sb.toString();
			System.out.println(query4);

			if (stmt.execute(query4)) {
				rs = stmt.getResultSet();
			}

			while (rs.next()) {
				int lecID = rs.getInt(1);
				String date = rs.getString(2);
				String time = rs.getString(3);
				ArrayList<String> dateTime = new ArrayList<String>();
				dateTime.add(date);
				dateTime.add(time);

				completedLecturesIDDate.put(lecID, dateTime);
				completedLectureIDs.add(lecID);
			}

			course.setCompletedLectureIDs(completedLectureIDs);
			;
			course.setCompletedLecturesIDDate(completedLecturesIDDate);

		} catch (Exception e) {
			System.out.println("error in helper function DBC.setLastTwoCompletedLecturesForCourse:" + e.getMessage());
		}

	}

	private void setLectureIDsForCourse(Course course) {
		// This retrieves a list of lectureIDs for the course and sets the
		// results in the course object

		try {
			ArrayList<Integer> lectures = new ArrayList<>();

			String query3 = "SELECT lectureID FROM Lecture WHERE courseCode = '" + course.getCourseCode() + "';";
			if (stmt.execute(query3)) {
				rs = stmt.getResultSet();
			}

			while (rs.next()) {
				lectures.add(rs.getInt(1));
			}

			course.setLectureIDs(lectures);

		} catch (Exception e) {
			System.out.println("error in helper function DBC.setLectureIDsForCourse:" + e.getMessage());
		}

	}

	private void setProfessorIDsForCourse(Course course) {
		// This retrieves a list of all the professorIDs for this course and
		// sets result in course object
		ArrayList<String> professor = new ArrayList<>();

		try {
			String query2 = "SELECT professorUsername FROM CourseProfessor WHERE courseCode = '"
					+ course.getCourseCode() + "';";
			if (stmt.execute(query2)) {
				rs = stmt.getResultSet();
			}

			while (rs.next()) {
				professor.add(rs.getString(1));
			}

		} catch (Exception e) {
			System.out.println("error in helper function DBC.setProfessorIDsForCourse:" + e.getMessage());
		}

		course.setProfessorUsernames(professor);

	}

	private void setCourseNameLecHoursAndSemester(Course course) {
		// This sets courseName, location and number of lecture hours for this
		// specific course and sets result in course object.
		String query = "SELECT courseName, lectureHours, taughtInSpring, taughtInAutumn FROM Course WHERE courseCode = " + "'"
				+ course.getCourseCode() + "';";

		try {
			if (stmt.execute(query)) {
				rs = stmt.getResultSet();
			}

			while (rs.next()) {
				course.setCourseName(rs.getString(1));
				course.setNumLectureHours(rs.getInt(2));
				if(rs.getInt(3) == 1){
					course.setTaughtInSpring(true);
				}else course.setTaughtInSpring(false);
				if(rs.getInt(4) == 1){
					course.setTaughtInAutumn(true);
				}else course.setTaughtInAutumn(false);
				
			}
		} catch (Exception e) {
			System.out.println("error in helper function DBC.setCourseNameLecHoursAndSemester:" + e.getMessage());
		}

	}

	// Lecture info

	public void loadLectureInfo(Lecture lecture) {
		connect();

		// date format: "YYYY-MM-DD"
		// time format: "hh:mm:ss"
		// dateTime format: "YYYY-MM-DD#hh:mm:ss"

		try {
			stmt = conn.createStatement();

			String query = "SELECT lectureDate, lectureTime, courseCode, professorUsername FROM Lecture WHERE lectureID = "
					+ lecture.getLectureID() + ";";
			// System.out.println(query);
			if (stmt.execute(query)) {
				rs = stmt.getResultSet();
			}

			rs.next();
			String date = rs.getString(1);
			String time = rs.getString(2);
			ArrayList<String> dateTime = new ArrayList<String>();
			dateTime.add(date);
			dateTime.add(time);

			lecture.setLectureDateAndTime(dateTime);
			;
			lecture.setCourseCode(rs.getString(3));
			lecture.setProfessor(rs.getString(4));

		} catch (Exception e) {
			System.out.println("SQLException: " + e.getMessage());
		}

		// hjelpemetode tar seg av å hente og legge til evaluations:
		setEvaluationsForLecture(lecture);

		close();

	}

	private void setEvaluationsForLecture(Lecture lecture) {

		ArrayList<Evaluation> evaluations = new ArrayList<>();
		ArrayList<Evaluation> PerfectEvaluations = new ArrayList<>();
		ArrayList<Evaluation> OkEvaluations = new ArrayList<>();
		ArrayList<Evaluation> TooFastEvaluations = new ArrayList<>();
		ArrayList<Evaluation> TooSlowEvaluations = new ArrayList<>();
		ArrayList<Evaluation> ConfusedEvaluations = new ArrayList<>();

		try {

			String query = "select studentUsername, rating, studentComment from Evaluation where lectureID = "
					+ lecture.getLectureID() + ";";
			if (stmt.execute(query)) {
				rs = stmt.getResultSet();
			}

			while (rs.next()) {

				String studentUsername = rs.getString(1);
				String rating = rs.getString(2);
				String studentComment = rs.getString(3);
				Evaluation eval = new Evaluation(rating, studentComment, lecture.getLectureID(), studentUsername);
				evaluations.add(eval);

				switch (rating) {
				case "Perfect":
					PerfectEvaluations.add(eval);
					break;

				case "Ok":
					OkEvaluations.add(eval);
					break;

				case "Too Fast!":
					TooFastEvaluations.add(eval);
					break;

				case "Too Slow!":
					TooSlowEvaluations.add(eval);
					break;

				case "Confused.. ?":
					ConfusedEvaluations.add(eval);
					break;

				default:
					break;
				}

			}

		} catch (Exception e) {
			System.out.println("SQLException: " + e.getMessage());
		}

		lecture.setEvaluations(evaluations);
		lecture.setPerfectEvaluations(PerfectEvaluations);
		lecture.setOkEvaluations(OkEvaluations);
		lecture.setTooFastEvaluations(TooFastEvaluations);
		lecture.setTooSlowEvaluations(TooSlowEvaluations);
		lecture.setConfusedEvaluations(ConfusedEvaluations);

	}

	public void insertLecture(String date, String time, String courseCode, String professorUsername) {
		// Date format: "YYYY-MM-DD"
		// Time format: "HH:MM:SS"
		connect();
		try {

			String query = buildLectureQuery(date, time, courseCode, professorUsername);
			// System.out.println(query);

			stmt = conn.createStatement();
			stmt.executeUpdate(query);

		} catch (Exception e) {
			System.out.println("SQLException: " + e.getMessage());
		}
		close();
	}

	// Forutsetter at det kun er én lecture i
	// et fag per dag per tidspunkt. Brukes midlertidig til testing
	public int getLectureID(ArrayList<String> dateTime, String courseCode) {
		connect();
		int id = -1;
		try {
			stmt = conn.createStatement();

			String date = dateTime.get(0);
			String time = dateTime.get(1);
			String query = "SELECT lectureID FROM Lecture WHERE courseCode = '" + courseCode + "' and lectureDate='"
					+ date + "' and lectureTime = '" + time + "';";
			System.out.println(query);
			if (stmt.execute(query)) {
				rs = stmt.getResultSet();
			}
			rs.next();
			id = rs.getInt(1);

		} catch (Exception e) {
			System.out.println("SQLException: " + e.getMessage());

		} finally {
			close();
		}

		return id;
	}

	public void deleteLecture(int lectureID) {
		connect();
		try {

			String query = "DELETE FROM Lecture WHERE lectureID='" + lectureID + "'";

			stmt = conn.createStatement();
			stmt.executeUpdate(query);

		} catch (Exception e) {
			System.out.println("SQLException: " + e.getMessage());
		}
		close();
	}

	public boolean lectureExists(int lectureID) {
		connect();
		boolean hasNext = false;
		try {
			stmt = conn.createStatement();

			String query = "SELECT lectureID FROM Lecture WHERE lectureID = " + lectureID + ";";

			if (stmt.execute(query)) {
				rs = stmt.getResultSet();
			}

			hasNext = rs.next();

		} catch (Exception e) {
			System.out.println("SQLException: " + e.getMessage());
		}
		close();
		return hasNext;
	}

	private String buildLectureQuery(String date, String time, String courseCode, String professorUsername) {

		StringBuilder sb = new StringBuilder();
		sb.append("INSERT INTO Lecture (lectureDate, lectureTime, Lecture.courseCode, professorUsername) ")
				.append("SELECT '").append(date).append("','").append(time)
				.append("',Course.courseCode, Professor.professorUsername ").append("FROM Course, Professor ")
				.append("WHERE courseCode = '").append(courseCode).append("' AND professorUsername =  '")
				.append(professorUsername).append("';");

		String query = sb.toString();

		return query;

	}

	// Professor info
	public void loadProfessorInfo(Professor prof) {
		// Must retrieve and update the following info from DB:
		// courseIDs = list of all courses that professor teaches
		// courseIDNames = hashmap that maps courseIDs to corresponding names

		String username = prof.getUsername();
		ArrayList<String> courseIDs = new ArrayList<>();
		HashMap<String, String> courseIDNames = new HashMap<>();

		connect();
		try {
			stmt = conn.createStatement();

			String query = "SELECT c.courseCode, c.courseName FROM Course c INNER JOIN CourseProfessor cp ON c.courseCode = cp.courseCode  WHERE cp. professorUsername = '"
					+ username + "';";
			// System.out.println(query);
			if (stmt.execute(query)) {
				rs = stmt.getResultSet();
			}

			while (rs.next()) {
				String courseCode = rs.getString(1);
				String courseName = rs.getString(2);
				courseIDs.add(courseCode);
				courseIDNames.put(courseCode, courseName);
			}

		} catch (Exception e) {
			System.out.println("SQLException: " + e.getMessage());
		}

		prof.setCourseIDs(courseIDs);
		prof.setCourseIDNames(courseIDNames);

		close();

	}

	public ArrayList<String> getCoursesTaughtByProfessor(String professorUsername) {
		ArrayList<String> courses = new ArrayList<>();

		connect();
		try {
			stmt = conn.createStatement();

			String query = "SELECT courseCode FROM CourseProfessor WHERE professorUsername = '" + professorUsername
					+ "';";
			if (stmt.execute(query)) {
				rs = stmt.getResultSet();
			}

			while (rs.next()) {
				courses.add(rs.getString(1));
			}

		} catch (Exception e) {
			System.out.println("SQLException: " + e.getMessage());
		}
		close();
		// System.out.println(courses);
		return courses;
	}

	public void insertProfessor(String professorUsername, String password) {
		// inserts a new professor into database
		connect();
		try {
			
			
			String query = "INSERT INTO Professor VALUES(?,?)";
			
			prepStmt = conn.prepareStatement(query);
			prepStmt.setString(1, professorUsername);
			prepStmt.setString(2, password);
			int i = prepStmt.executeUpdate();
			System.out.println(i+" records inserted");  


		} catch (Exception e) {
			System.out.println("SQLException in InsertProfessor: " + e.getMessage());
		}
		close();
	}

	public void insertProfessorCon(String professorUsername, String password) {
		// inserts a new professor into database
		try {
			
			
			String query = "INSERT INTO Professor VALUES(?,?)";
			
			prepStmt = conn.prepareStatement(query);
			prepStmt.setString(1, professorUsername);
			prepStmt.setString(2, password);
			int i = prepStmt.executeUpdate();
			System.out.println(i+" records inserted");  


		} catch (Exception e) {
			System.out.println("SQLException in InsertProfessor: " + e.getMessage());
		}
	}
	
	public boolean professorExists(String professorUsername) {
		boolean hasNext = false;

		connect();
		try {
			stmt = conn.createStatement();

			String query = "SELECT professorUsername FROM Professor WHERE professorUsername = '" + professorUsername
					+ "';";

			if (stmt.execute(query)) {
				rs = stmt.getResultSet();
			}

			hasNext = rs.next();

		} catch (Exception e) {
			System.out.println("SQLException: " + e.getMessage());
		}
		close();
		return hasNext;
	}

	public ArrayList<Integer> getCompletedLecturesForCourseByProfessor(String courseCode, String professorUsername) {
		// returns lectureIDs for all lectures given by this professor that have
		// already passed in specified course
		// Order is with the newest lecture first

		ArrayList<Integer> lectures = new ArrayList<>();

		connect();
		try {
			stmt = conn.createStatement();

			StringBuilder sb = new StringBuilder();
			sb.append("SELECT * FROM Lecture WHERE courseCode = '").append(courseCode).append("' ")
					.append("AND professorUsername = '").append(professorUsername).append("' ")
					.append(" AND (lectureDate < now() OR (lectureDate = now()  AND lectureTime < now())) ORDER BY lectureDate DESC, lectureTime DESC;");

			String query = sb.toString();
			if (stmt.execute(query)) {
				rs = stmt.getResultSet();
			}

			while (rs.next()) {
				lectures.add(rs.getInt(1));
			}

		} catch (Exception e) {
			System.out.println("SQLException: " + e.getMessage());
		}
		close();

		// System.out.println(courses);
		return lectures;

	}

	public void deleteProfessor(String username) {
		connect();
		try {

			String query = "DELETE FROM Professor WHERE professorUsername='" + username + "'";

			stmt = conn.createStatement();
			stmt.executeUpdate(query);

		} catch (Exception e) {
			System.out.println("SQLException: " + e.getMessage());
		}
		close();
	}

	// Student info

	public void loadStudentInfo(Student student) {
		// Need to get following info about student and update Student Object
		// accordingly:

		// String studyProgram;
		// ArrayList<String> courseIDs;
		// HashMap<int, String> courseIDNames;

		connect();
		try {

			// First students studyProgram is retrieved from DB
			stmt = conn.createStatement();

			String query = "SELECT studyProgramCode FROM Student WHERE studentUsername = '" + student.getUsername() + "';";
			if (stmt.execute(query)) {
				rs = stmt.getResultSet();
			}

			rs.next();
			student.setStudyProgram(rs.getString(1));

			// next find the courseCodes and corresponding courseNames for this
			// student
			String query2 = "select c.courseCode, courseName from Course as c, CourseStudent as cs WHERE c.courseCode = cs.courseCode AND studentUsername = '"
					+ student.getUsername() + "';";

			if (stmt.execute(query2)) {
				rs = stmt.getResultSet();
			}

			String courseID = null;
			String courseName = null;
			ArrayList<String> courseIDs = new ArrayList<>();
			HashMap<String, String> courseIDNames = new HashMap<>();

			while (rs.next()) {
				courseID = rs.getString(1);
				courseName = rs.getString(2);
				courseIDs.add(courseID);
				courseIDNames.put(courseID, courseName);
			}

			student.setCourseIDs(courseIDs);
			student.setCourseIDNames(courseIDNames);

		} catch (Exception e) {
			System.out.println("SQLException: " + e.getMessage());
		}
		close();

	}

	public void insertStudent(String studentUsername, String studyProgramCode) {

		connect();
		try {
			
			String query = "INSERT INTO Student VALUES(?,?)";
			
			prepStmt = conn.prepareStatement(query);
			prepStmt.setString(1, studentUsername);
			prepStmt.setString(2, studyProgramCode);
			int i = prepStmt.executeUpdate();
			System.out.println(i+" records inserted");  

//			StringBuilder sb = new StringBuilder();
//			sb.append("INSERT INTO Student VALUES('").append(studentUsername).append("','")
//					.append(studyProgramCode).append("');");
//
//			String query = sb.toString();
//			// System.out.println(query);
//
//			stmt = conn.createStatement();
//			stmt.executeUpdate(query);

		} catch (Exception e) {
			System.out.println("SQLException: " + e.getMessage());
		}
		close();
	}

	public void deleteStudent(String studentUsername) {
		connect();
		try {

			String query = "DELETE FROM Student WHERE studentUsername='" + studentUsername + "'";

			stmt = conn.createStatement();
			stmt.executeUpdate(query);

		} catch (Exception e) {
			System.out.println("SQLException: " + e.getMessage());
		}
		close();
	}

	public boolean studentExists(String studentUsername) {
		boolean hasNext = false;
		connect();
		try {
			stmt = conn.createStatement();

			String query = "SELECT studentUsername FROM Student WHERE studentUsername = '" + studentUsername + "';";
			if (stmt.execute(query)) {
				rs = stmt.getResultSet();
			}

			hasNext = rs.next();

		} catch (Exception e) {
			System.out.println("SQLException: " + e.getMessage());
		}
		close();
		return hasNext;
	}

	public boolean studentHasEvaluatedLecture(String studentUsername, int lecID) {
		boolean hasNext = false;
		connect();
		try {
			stmt = conn.createStatement();

			String query = "SELECT * FROM Evaluation WHERE studentUsername = '" + studentUsername + "' AND lectureID ="
					+ lecID + ";";

			if (stmt.execute(query)) {
				rs = stmt.getResultSet();
			}

			hasNext = rs.next();

		} catch (Exception e) {
			System.out.println("SQLException: " + e.getMessage());
		}
		close();
		return hasNext;

	}

	// CourseProfessor info
	public void insertCourseProfessor(String professorUsername, String courseCode) {
		connect();
		try {

			StringBuilder sb = new StringBuilder();
			sb.append("Insert into CourseProfessor (professorUsername, courseCode)")
					.append(" SELECT Professor.professorUsername, Course.courseCode").append(" FROM Course, Professor")
					.append(" WHERE courseCode = '").append(courseCode).append("'").append("AND professorUsername = '")
					.append(professorUsername).append("'");

			String query = sb.toString();
			// System.out.println(query);

			stmt = conn.createStatement();
			stmt.executeUpdate(query);

		} catch (Exception e) {
			System.out.println("SQLException: " + e.getMessage());
		}
		close();
	}

	public void insertCourseProfessorCon(String professorUsername, String courseCode) {
		//already connected
		try {

			StringBuilder sb = new StringBuilder();
			sb.append("Insert into CourseProfessor (professorUsername, courseCode)")
					.append(" SELECT Professor.professorUsername, Course.courseCode").append(" FROM Course, Professor")
					.append(" WHERE courseCode = '").append(courseCode).append("'").append("AND professorUsername = '")
					.append(professorUsername).append("'");

			String query = sb.toString();
			// System.out.println(query);

			stmt = conn.createStatement();
			stmt.executeUpdate(query);

		} catch (Exception e) {
			System.out.println("SQLException: " + e.getMessage());
		}
		
	}	
	
	// CourseStudent info
	public void insertCourseStudent(String studentUsername, String courseCode) {
		connect();
		try {

			StringBuilder sb = new StringBuilder();
			sb.append("Insert into CourseStudent (studentUsername, courseCode)")
					.append(" SELECT Student.studentUsername, Course.courseCode").append(" FROM Course, Student")
					.append(" WHERE courseCode = '").append(courseCode).append("'").append("AND studentUsername = '")
					.append(studentUsername).append("'");

			String query = sb.toString();
			// System.out.println(query);

			stmt = conn.createStatement();
			stmt.executeUpdate(query);

		} catch (Exception e) {
			System.out.println("SQLException: " + e.getMessage());
		}
		close();
	}

	// Evaluation info
	public void loadEvaluationInfo(Evaluation evaluation) {

		connect();
		try {
			stmt = conn.createStatement();

			String query = "SELECT rating, studentComment FROM Evaluation WHERE lectureID = "
					+ evaluation.getLectureid() + " AND studentUsername ='" + evaluation.getstudentUsername() + "' ;";
			// System.out.println(query);
			if (stmt.execute(query)) {
				rs = stmt.getResultSet();
			}

			rs.next();
			evaluation.setRating(rs.getString(1));
			evaluation.setComment(rs.getString(2));

		} catch (Exception e) {
			System.out.println("SQLException: " + e.getMessage());
		}
		close();

	}

	public void overwriteEvaluation(String email, int lectureID, String rating, String comment) {
		connect();

		try {

			// delete Evaluation for this student for this lecture
			StringBuilder sb1 = new StringBuilder();
			sb1.append("DELETE FROM Evaluation WHERE studentUsername = '").append(email).append("' ")
					.append("AND lectureID =").append(lectureID).append(";");

			String query1 = sb1.toString();
			stmt = conn.createStatement();
			stmt.executeUpdate(query1);

			// insert new Evaluation
			StringBuilder sb = new StringBuilder();
			sb.append("Insert into Evaluation (studentUsername, lectureID, rating, studentComment)")
					.append(" SELECT Student.studentUsername, Lecture.lectureID, '").append(rating).append("', '")
					.append(comment).append("'").append(" FROM Student, Lecture").append(" WHERE studentUsername = '")
					.append(email).append("' AND lectureID = ").append(lectureID).append(";");

			String query2 = sb.toString();
			// System.out.println(query);

			stmt = conn.createStatement();
			stmt.executeUpdate(query2);

		} catch (Exception e) {
			System.out.println("SQLException: " + e.getMessage());
		}
		close();

	}

	public void insertEvaluation(String studentUsername, int lectureID, String rating, String studentComment) {
		connect();
		try {
			
//			String query = "Insert into Evaluation (studentUsername, lectureID, rating, studentComment) SELECT Student.studentUsername,"
//					+ " Lecture.lectureID, (?,?) FROM Student, Lecture WHERE studentUsername = (?) "
//					+ "AND lectureID = (?);";
			
			
			String query = "Insert into Evaluation (studentUsername, lectureID, rating, studentComment) VALUES(?,?,?,?)";

			prepStmt = conn.prepareStatement(query);
			prepStmt.setString(1, studentUsername);
			prepStmt.setInt(2, lectureID);
			prepStmt.setString(3, rating);
			prepStmt.setString(4, studentComment);
			
			int i = prepStmt.executeUpdate();
			System.out.println(i + " records inserted");

//			StringBuilder sb = new StringBuilder();
//			sb.append("Insert into Evaluation (studentUsername, lectureID, rating, studentComment)")
//					.append(" SELECT Student.studentUsername, Lecture.lectureID, '").append(rating).append("', '")
//					.append(studentComment).append("'").append(" FROM Student, Lecture")
//					.append(" WHERE studentUsername = '").append(studentUsername).append("' AND lectureID = ")
//					.append(lectureID).append(";");
//
//			String query = sb.toString();
//			System.out.println(query);
//
//			stmt = conn.createStatement();
//			stmt.executeUpdate(query);

		} catch (Exception e) {
			System.out.println("SQLException in insertEvaluation: " + e.getMessage());
		}
		close();
	}

	public boolean evaluationExists(int lectureid, String studentUsername) {
		boolean hasNext = false;
		connect();
		try {
			stmt = conn.createStatement();

			String query = "SELECT lectureID FROM Evaluation WHERE lectureID = " + lectureid + " AND studentUsername ='"
					+ studentUsername + "' ;";

			if (stmt.execute(query)) {
				rs = stmt.getResultSet();
			}

			hasNext = rs.next();

		} catch (Exception e) {
			System.out.println("SQLException: " + e.getMessage());
		}
		close();
		return hasNext;
	}

	// Main for testing
	public static void main(String[] args) throws ParseException {
		DBController test = new DBController();
		
//		test.deleteCourseStudent("stud1", "tdt4140");
//		System.out.println(test.getCoursesStartingWith("pr", "name"));
//		test.insertCourse("code", "name", 4, 0, 1);
//		test.insertStudent("c'); DROP TABLE Professor;", "test");
//		test.insertCourseStudent("karimj", "tdt4145");

//		test.insertProfessor("c'); DROP TABLE Professor;", "testPas");
//		test.overwriteEvaluation("stud21", 125, "Too Slow!", "This is easy");
//		test.overwriteEvaluation("stud22", 125, "Too Slow!", "was fine but a bit slow");
//		test.insertEvaluation("stud23", 125, "Ok", "c'); DROP TABLE Professor;");
//		test.insertEvaluation("stud24", 125, "Ok", "hello");
//		test.insertEvaluation("stud25", 125, "Ok", "I believe that I am a cat");
//		test.insertEvaluation("stud26", 125, "Ok", "bunnys are so cute");
//		test.insertEvaluation("stud27", 125, "Ok", "You are alright ");
//		test.insertEvaluation("stud28", 125, "Ok", "You are alright ");
		
//		test.insertProfessor("prompeProf", Professor.hashPassword("mittPassord"));

		 
		// test.getProfessorsForCoursse("tdt4140");
		// test.getCoursestaughtByProfessor("pekkaa");
		// test.getLectureHoursForCourse("tdt4140");
		// test.getStartDate();
//		 test.insertLecture("2017-02-22", "08:00:00", "tdt4140", "pekkaa");
//		 test.insertLecture("2017-02-23", "08:00:00", "tdt4140", "pekkaa");
//		 test.insertLecture("2017-02-24", "08:00:00", "tdt4140", "pekkaa");
//		 test.insertLecture("2017-02-25", "08:00:00", "tdt4140", "pekkaa");
//		 test.insertLecture("2017-02-26", "08:00:00", "tdt4140", "pekkaa");
//		 test.insertLecture("2017-02-28", "08:00:00", "tdt4140", "pekkaa");
		// test.insertCourseProfessor("sveinbra", "tdt4145");
		// test.insertStudent("negative","MTING");


		// test.deleteStudent("bolle@stud.ntnu.no");

		// test.insertCourseStudent("stud54@stud.ntnu.no ", "tdt4140");
		// test.insertStudent("stud55", "MLREAL");
		
//		 test.insertCourseStudent("stud5", "tdt4140");
		 

		// System.out.println(test.getLastTwoCompletedLecturesForCourse("tdt4145"));

		// test.insertLecture("2016-09-03", "08:00:00", "tdt4140", "pekkaa");
		


	}

	public void setCourseRatingsOverTime(Course course) {
		// must set: HashMap<Integer, Integer> lecIDtoRatingCount1-5 in courseObject
		// This creates the list and linked Hash map with the completed lectures in a given semester and their dates and sets the result in the course object
		
		HashMap<Integer, Integer> lecIDtoNumRatings = new HashMap<>();

		HashMap<Integer, Integer> lecIDtoRatingCount1 = new HashMap<>();
		HashMap<Integer, Integer> lecIDtoRatingCount2 = new HashMap<>();
		HashMap<Integer, Integer> lecIDtoRatingCount3 = new HashMap<>();
		HashMap<Integer, Integer> lecIDtoRatingCount4 = new HashMap<>();
		HashMap<Integer, Integer> lecIDtoRatingCount5 = new HashMap<>();

								
				try {
							
					connect();
					
						stmt = conn.createStatement();
						StringBuilder sb = new StringBuilder();
						
						sb.append("select e.rating, e.lectureID, count(*) As 'ratingCount' From Evaluation e JOIN Lecture l on e.lectureID = l.lectureID WHERE l.courseCode = '")
						.append(course.getCourseCode()).append("' AND ").append(SQLtimeConstraint(course))
						.append(" GROUP BY e.rating, e.lectureID ORDER BY e.lectureID;");
						
						
						String query4 = sb.toString();			
						System.out.println(query4);
						
						if (stmt.execute(query4)) {
							rs = stmt.getResultSet();
						}

						while(rs.next()){
							
							String rating = rs.getString(1);
							int lecID = rs.getInt(2);
							int count = rs.getInt(3);
							ArrayList<String> ratingValues = course.getRatingValues();
							
							int numRatingsForLec = lecIDtoNumRatings.containsKey(lecID) ? lecIDtoNumRatings.get(lecID) : 0;
							lecIDtoNumRatings.put(lecID, numRatingsForLec + count);
							
							if(rating.equals(ratingValues.get(0))){
								lecIDtoRatingCount1.put(lecID, count);
							}else if(rating.equals(ratingValues.get(1))) {
								lecIDtoRatingCount2.put(lecID, count);
							}else if(rating.equals(ratingValues.get(2))) {
								lecIDtoRatingCount3.put(lecID, count);
							}else if(rating.equals(ratingValues.get(3))) {
								lecIDtoRatingCount4.put(lecID, count);
							}else if(rating.equals(ratingValues.get(4))) {
								lecIDtoRatingCount5.put(lecID, count);
							}

						}
						
						course.setLecIDtoNumRatings(lecIDtoNumRatings);
						course.setLecIDtoRatingCount1(lecIDtoRatingCount1);
						course.setLecIDtoRatingCount2(lecIDtoRatingCount2);
						course.setLecIDtoRatingCount3(lecIDtoRatingCount3);
						course.setLecIDtoRatingCount4(lecIDtoRatingCount4);
						course.setLecIDtoRatingCount5(lecIDtoRatingCount5);
						
					} catch (SQLException e) {
						e.printStackTrace();
					}
						
					close();		
		
	}

	private String SQLtimeConstraint(Course course) {
		// generates appropriate sql query string for timeconstraint
		String timeConstraint = null;
		char season = course.getSemester().charAt(0);
		String year = course.getSemester().substring(1);

		if (course.isCurrentsemester()) {
			timeConstraint = "(lectureDate < now() OR (lectureDate = now()  AND lectureTime < now()))"
					+ "AND  YEAR(lectureDate) = " + year;
		} else {
			if (season == 'V') {
				timeConstraint = " (lectureDate < now() OR (lectureDate = now()  AND lectureTime < now())) AND (MONTH(lectureDate) < 7) AND YEAR(lectureDate) = "
						+ year;

			} else if (season == 'H') {
				timeConstraint = " (lectureDate < now() OR (lectureDate = now()  AND lectureTime < now())) AND (MONTH(lectureDate) >= 7) AND YEAR(lectureDate) = "
						+ year;
			}

		}

		System.out.println(timeConstraint);
		return timeConstraint;
	}

	public boolean checkProfessorPassword(String professorUsername, String encryptedPassword) throws SQLException {
		
		try {
			connect();
			
			stmt = conn.createStatement();
						
			String query = "select * from Professor where professorUsername = '" + professorUsername +"' and professorPassword = '" + encryptedPassword +"';";			
//			System.out.println(query);
			
			if (stmt.execute(query)) {
				rs = stmt.getResultSet();
			}
		} catch (Exception e) {
			System.out.println("error in DBC.checkProfessorPassword: " + e.getMessage());
		}
		
		return rs.next();
	}

	public ArrayList<String> getCoursesStartingWith(String searchText, String nameOrCode) {
		ArrayList<String> courses = new ArrayList<>();
		
		connect();
		try {
			String query = "";
			if(nameOrCode.equals("code")){
				query = "select courseCode, courseName from Course Where courseCode like ?";
			}else if(nameOrCode.equals("name")){
				query = "select courseCode, courseName from Course Where courseName like ?;";
			}
			 
			
			prepStmt = conn.prepareStatement(query);
			prepStmt.setString(1, searchText + "%");
			System.out.println(query);
			rs = prepStmt.executeQuery();
			
			while(rs.next()){
				courses.add(rs.getString(1) + " " + rs.getString(2));
			}

		} catch (Exception e) {
			System.out.println("SQLException in getCoursesStartingWith: " + e.getMessage());
		}
		
		close();
		
		return courses;
	}

	public void deleteCourseStudent(String username, String courseCode) {
		connect();
		try {
			String query = "DELETE FROM CourseStudent WHERE courseCode='" + courseCode + "' AND studentUsername ='" + username + "';";

			stmt = conn.createStatement();
			stmt.executeUpdate(query);

		} catch (Exception e) {
			System.out.println("SQLException: " + e.getMessage());
		}
		close();
		
	}

	public ArrayList<String> getAllCourses() {
		ArrayList<String> courses = new ArrayList<>();
		
		connect();
		try {
			String query = "select courseCode, courseName from Course"; 
			
			prepStmt = conn.prepareStatement(query);
			rs = prepStmt.executeQuery();
			
			while(rs.next()){
				courses.add(rs.getString(1) + " " + rs.getString(2));
			}

		} catch (Exception e) {
			System.out.println("SQLException in getCoursesStartingWith: " + e.getMessage());
		}
		
		close();
		
		return courses;
	}

	public void updateProfessor(String username, String hashPassword) {
		//  update Professor SET professorPassword = 'np' WHERE professorUsername = 'pekkaa';
		
		connect();
		try {
			
			
			String query = "update Professor SET professorPassword = ? WHERE professorUsername = ?;";
			
			prepStmt = conn.prepareStatement(query);
			prepStmt.setString(1, hashPassword);
			prepStmt.setString(2, username);
			int i = prepStmt.executeUpdate();
			System.out.println(i+" records inserted");  


		} catch (Exception e) {
			System.out.println("SQLException in InsertProfessor: " + e.getMessage());
		}
		close();
		
		
		
	}


}
