package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.regex.Pattern;

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
	ResultSet rs = null;

	// Frequently used SQL methods
	public ArrayList<String> getStringArray(String query) {
		//takes an SQL query as input. Returns a string that contains all data from first column of table
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
		// takes an sqlQuery as input and returns a list containing the String elements of the first column
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
		// takes an sqlQuery as input and returns a list containing the int elements of the first column
				
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

	public void insertCourse(String courseCode, String courseName, String courseLocation, int lectureHours) {
		// inserts a new row in Course table of database
		connect();

		try {

			StringBuilder sb = new StringBuilder();
			sb.append("INSERT INTO Course VALUES('").append(courseCode).append("','").append(courseName).append("','")
					.append(courseLocation).append("',").append(lectureHours).append(");");

			String query = sb.toString();

			stmt = conn.createStatement();
			stmt.executeUpdate(query);

		} catch (Exception e) {
			System.out.println("SQLException: " + e.getMessage());
		}

		close();

	}
	
	public boolean courseExists(String courseCode) {
		//Checks in the database if a course with given courseCode exists.
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
	
	public void deleteCourse(String courseCode){
		connect();
		try {
			String query = "DELETE FROM Course WHERE courseCode='" + courseCode +"'";

			stmt = conn.createStatement();
			stmt.executeUpdate(query);

		} catch (Exception e) {
			System.out.println("SQLException: " + e.getMessage());
		}
		close();
		
	}

	public ArrayList<Integer> getLastTwoCompletedLecturesForCourse(String courseCode) {
		//retruns a list containing the lectureIDs of the last two completed lectures for the specified course.
		ArrayList<Integer> lectures = new ArrayList<>();

		connect();
		try {
			stmt = conn.createStatement();

			StringBuilder sb = new StringBuilder();
			sb.append("SELECT lectureID FROM Lecture WHERE courseCode = '").append(courseCode).append("' ").append(
					" AND (lectureDate < now() OR (lectureDate = now()  AND lectureTime < now())) ORDER BY lectureDate DESC, lectureTime DESC;");

			String query = sb.toString();
			if (stmt.execute(query)) {
				rs = stmt.getResultSet();
			}

			if(rs.next()){
				lectures.add(rs.getInt(1));	
			}
			
			if(rs.next()){
				lectures.add(rs.getInt(1));	
			}
			
			
		} catch (Exception e) {
			System.out.println("SQLException: " + e.getMessage());
		}

		close();
		return lectures;

	}

	public Course loadCourseInfo(Course course) {
		// This method takes in a Course object with a specific courseCode. It
		// collects all the information about this course and fills in the rest
		// of the course details. Finally It will return the loaded course object.
		
		connect();
		
		try {
			stmt = conn.createStatement();
		} catch (SQLException e) {
			e.printStackTrace();
		}
			setCourseNameLocationAndLecHours(course);
			setProfessorIDsForCourse(course);
			setLectureIDsForCourse(course);
			setLastTwoCompletedLecturesForCourse(course); 		
		
		close();
		
		
		return course;
	}
	
	private void setLastTwoCompletedLecturesForCourse(Course course) {
			// This creates the list and linked Hash map with the last 2 completed lectures and their dates and sets the result in the course object
		try {
						
			ArrayList<Integer> lastTwoCompletedLectureIDs = new ArrayList<>();
			LinkedHashMap<Integer, GregorianCalendar> lastTwolectures = new LinkedHashMap<>();
			
			StringBuilder sb = new StringBuilder();
			sb.append("SELECT lectureID, lectureDate, lectureTime  FROM Lecture WHERE courseCode = '").append(course.getCourseCode()).append("' ").append(
					" AND (lectureDate < now() OR (lectureDate = now()  AND lectureTime < now())) ORDER BY lectureDate DESC, lectureTime DESC;");

			String query4 = sb.toString();
			
			if (stmt.execute(query4)) {
				rs = stmt.getResultSet();
			}

			if(rs.next()){
				int lecID = rs.getInt(1);
				String date = rs.getString(2);
				System.out.println(date);
				String time = rs.getString(3);

				lastTwolectures.put(lecID, stringToCalender(date, time));
				lastTwoCompletedLectureIDs.add(lecID);
			}
			
			if(rs.next()){
				int lecID = rs.getInt(1);
				String date = rs.getString(2);
				String time = rs.getString(3);

				lastTwolectures.put(lecID, stringToCalender(date, time));
				lastTwoCompletedLectureIDs.add(lecID);
			}
							
			course.setLastTwoCompletedLectures(lastTwolectures);
			course.setLastTwoCompletedLectureIDs(lastTwoCompletedLectureIDs);
		} catch (Exception e) {
			System.out.println("error in helper function DBC.setLastTwoCompletedLecturesForCourse:" + e.getMessage());
		}
		
	}

	private void setLectureIDsForCourse(Course course) {
		// This retrieves a list of lectureIDs for the course and sets the results in the course object
		
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
		// This retrieves a list of all the professorIDs for this course and sets result in course object
		ArrayList<String> professor = new ArrayList<>();

		try {
			String query2 = "SELECT professorUsername FROM CourseProfessor WHERE courseCode = '" + course.getCourseCode()
				+ "';";
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

	private void setCourseNameLocationAndLecHours(Course course) {
		//This sets courseName, location and number of lecture hours for this specific course and sets result in course object.
		String query = "SELECT courseName, courseLocation, lectureHours  FROM Course WHERE courseCode = " + "'" + course.getCourseCode()
				+ "';";
		
		try {
				if (stmt.execute(query)) {
				rs = stmt.getResultSet();
			}
	
			while (rs.next()) {
				course.setCourseName(rs.getString(1));
				course.setCourseLocation(rs.getString(2));
				course.setNumLectureHours(rs.getInt(3));
			}
		} catch (Exception e) {
			System.out.println("error in helper function DBC.setCourseNameLocationAndLecHours:" + e.getMessage());
		}
		
		
	}

	private GregorianCalendar stringToCalender(String date, String time){
		// helper function that converts SQL strings of date and time to Gregorian Calendar objects
		
		// date format: "YYYY-MM-DD"
		// time format: "hh:mm:ss"
		String[] dateSplit = date.split("-");
		int YYYY = Integer.valueOf(dateSplit[0]);
		int MM = Integer.valueOf(dateSplit[1]);
		int DD = Integer.valueOf(dateSplit[2]);
		
		String[] timeSplit = time.split(":");
		int hh = Integer.valueOf(timeSplit[0]);
		int mm = Integer.valueOf(timeSplit[1]);
		int ss = Integer.valueOf(timeSplit[2]);
		
		GregorianCalendar calendar = new GregorianCalendar(YYYY, MM, DD, hh, mm, ss);
		
		return calendar;
		
	}
	
	
	// Lecture info

	public void loadLectureInfo(Lecture lecture) {
		connect();

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
			lecture.setLectureDateAndTime(stringToCalendar(date, time));
			lecture.setCourseCode(rs.getString(3));
			lecture.setProfessor(rs.getString(4));			
			
		} catch (Exception e) {
			System.out.println("SQLException: " + e.getMessage());
		}
		
			ArrayList<Evaluation> evaluations= getEvaluationsForLecture(lecture.getLectureID());
			lecture.setEvaluations(evaluations);

		close();
		
	}
	
	private ArrayList<Evaluation> getEvaluationsForLecture(int lectureID) {

		ArrayList<Evaluation> evaluations = new ArrayList<>();

		try {
			
			String query = "select studentEmail, rating, studentComment from Evaluation where lectureID = " + lectureID
					+ ";";
			if (stmt.execute(query)) {
				rs = stmt.getResultSet();
			}

			while (rs.next()) {

				String studentEmail = rs.getString(1);
				String rating = rs.getString(2);
				String studentComment = rs.getString(3);
				Evaluation eval = new Evaluation(rating, studentComment, lectureID, studentEmail);
				evaluations.add(eval);

			}

		} catch (Exception e) {
			System.out.println("SQLException: " + e.getMessage());
		}

		return evaluations;
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
	
	//Forutsetter at det kun er én lecture i
	// et fag per dag. Brukes midlertidig til testing
	public int getLectureID(GregorianCalendar dateTime, String courseCode){
		connect();
		int id = -1;
		try {
			stmt = conn.createStatement();
			
			ArrayList<String> dateTimeList = calendarToStringDateTime(dateTime);
			String date = dateTimeList.get(0);
			String time = dateTimeList.get(1);
			
			String query = "SELECT lectureID FROM Lecture WHERE courseCode = '" 
					+ courseCode + "' and lectureDate='" + date + 
					"' and lectureTime = '" + time + "';";
			if (stmt.execute(query)) {
				rs = stmt.getResultSet();
			}
			rs.next();
			id = rs.getInt(1);
			return id;
			

		} catch (Exception e) {
			System.out.println("SQLException: " + e.getMessage());
		}
		
		close();
		return id;
	}
	
	private ArrayList<String> calendarToStringDateTime(GregorianCalendar dateTime){
		String year = DateFormat.getDateInstance(DateFormat.YEAR_FIELD).format(dateTime.getTime());
		ArrayList<String> dateTimeList = new ArrayList<>();
		String[] dateSplit = year.split(" ");
		String YYYY = dateSplit[2];
		
		String monthDay = DateFormat.getDateInstance(DateFormat.SHORT).format(dateTime.getTime());
		
		String[] dateSplit2 = monthDay.split("[.]");
		
		String DD = dateSplit2[0];
		String MM = dateSplit2[1];
		
		String date = YYYY + "-" + MM + "-" + DD;
		
		String time = DateFormat.getTimeInstance(DateFormat.DEFAULT).format(dateTime.getTime());
		
		dateTimeList.add(date);
		dateTimeList.add(time);
		
		System.out.println(dateTimeList);
		return dateTimeList;
	}
	
	public void deleteLecture(int lectureID){
		connect();
		try {

			String query = "DELETE FROM Lecture WHERE lectureID='" + lectureID +"'";

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
	
	private GregorianCalendar stringToCalendar(String date, String time) {
		// date format: "YYYY-MM-DD"
				// time format: "hh:mm:ss"
				String[] dateSplit = date.split("-");
				int YYYY = Integer.valueOf(dateSplit[0]);
				int MM = Integer.valueOf(dateSplit[1]);
				int DD = Integer.valueOf(dateSplit[2]);
				
				String[] timeSplit = time.split(":");
				int hh = Integer.valueOf(timeSplit[0]);
				int mm = Integer.valueOf(timeSplit[1]);
				int ss = Integer.valueOf(timeSplit[2]);
				
				GregorianCalendar calendar = new GregorianCalendar(YYYY, MM, DD, hh, mm, ss);
				
				return calendar;
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

			String query = "SELECT c.courseCode, c.courseName FROM Course c INNER JOIN CourseProfessor cp ON c.courseCode = cp.courseCode  WHERE cp. professorUsername = '" + username
					+ "';";
			//System.out.println(query);
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

	public void insertProfessor(String professorUsername) {
		// inserts a new professor into database
		connect();
		try {

			StringBuilder sb = new StringBuilder();
			sb.append("INSERT INTO Professor VALUES('").append(professorUsername).append("');");

			String query = sb.toString();
			// System.out.println(query);

			stmt = conn.createStatement();
			stmt.executeUpdate(query);

		} catch (Exception e) {
			System.out.println("SQLException: " + e.getMessage());
		}
		close();
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

	public void deleteProfessor(String username){
		connect();
		try {

			String query = "DELETE FROM Professor WHERE professorUsername='" + username +"'";

			stmt = conn.createStatement();
			stmt.executeUpdate(query);

		} catch (Exception e) {
			System.out.println("SQLException: " + e.getMessage());
		}
		close();
	}
	
	// Student info
	
	public void loadStudentInfo(Student student) {
		// Need to get following info about student and update Student Object accordingly:
		
		// String studyProgram;
		// ArrayList<String> courseIDs;
		// HashMap<int, String> courseIDNames;
		
				
		connect();
		try {
			
			//First students studyProgram is retrieved from DB
			stmt = conn.createStatement();

			String query = "SELECT studyProgramCode FROM Student WHERE studentEmail = '" + student.getEmail() + "';";
			if (stmt.execute(query)) {
				rs = stmt.getResultSet();
			}

			rs.next();
			student.setStudyProgram(rs.getString(1)); 
			
			// next find the courseCodes and corresponding courseNames for this student
			String query2 = "select c.courseCode, courseName from Course as c, CourseStudent as cs WHERE c.courseCode = cs.courseCode AND studentEmail = '"+ student.getEmail() + "';";
			
			
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

			StringBuilder sb = new StringBuilder();
			sb.append("INSERT INTO Student VALUES('").append(studentUsername).append("@stud.ntnu.no").append("','")
					.append(studyProgramCode).append("');");

			String query = sb.toString();
			// System.out.println(query);

			stmt = conn.createStatement();
			stmt.executeUpdate(query);

		} catch (Exception e) {
			System.out.println("SQLException: " + e.getMessage());
		}
		close();
	}
	
	public void deleteStudent(String studentEmail){
		connect();
		try {

			String query = "DELETE FROM Student WHERE studentEmail='" + studentEmail +"'";

			stmt = conn.createStatement();
			stmt.executeUpdate(query);

		} catch (Exception e) {
			System.out.println("SQLException: " + e.getMessage());
		}
		close();
	}
	
	public boolean studentExists(String studentEmail) {
		boolean hasNext = false;
		connect();
		try {
			stmt = conn.createStatement();

			String query = "SELECT studentEmail FROM Student WHERE studentEmail = '" + studentEmail + "';";
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

	public boolean studentHasEvaluatedLecture(String studentEmail, int lecID) {
		boolean hasNext = false;
		connect();
		try {
			stmt = conn.createStatement();

			String query = "SELECT * FROM Evaluation WHERE studentEmail = '" + studentEmail + "' AND lectureID =" + lecID + ";";
			
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
	
	// CourseStudent info
	public void insertCourseStudent(String studentEmail, String courseCode) {
		connect();
		try {

			StringBuilder sb = new StringBuilder();
			sb.append("Insert into CourseStudent (studentEmail, courseCode)")
					.append(" SELECT Student.studentEmail, Course.courseCode").append(" FROM Course, Student")
					.append(" WHERE courseCode = '").append(courseCode).append("'").append("AND studentEmail = '")
					.append(studentEmail).append("'");

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

			String query = "SELECT rating, studentComment FROM Evaluation WHERE lectureID = " + evaluation.getLectureid()
					+ " AND studentEmail ='" + evaluation.getStudentEmail() + "' ;";
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

			//delete Evaluation for this student for this lecture
			StringBuilder sb1 = new StringBuilder();
			sb1.append("DELETE FROM Evaluation WHERE studentEmail = '").append(email).append("' ")
			.append( "AND lectureID =").append(lectureID).append(";");
			
			String query1 = sb1.toString();
			stmt = conn.createStatement();
			stmt.executeUpdate(query1);
			
			//insert  new Evaluation
			StringBuilder sb = new StringBuilder();
			sb.append("Insert into Evaluation (studentEmail, lectureID, rating, studentComment)")
					.append(" SELECT Student.studentEmail, Lecture.lectureID, '").append(rating).append("', '")
					.append(comment).append("'").append(" FROM Student, Lecture")
					.append(" WHERE studentEmail = '").append(email).append("' AND lectureID = ")
					.append(lectureID).append(";");

			String query2 = sb.toString();
			// System.out.println(query);

			stmt = conn.createStatement();
			stmt.executeUpdate(query2);

		} catch (Exception e) {
			System.out.println("SQLException: " + e.getMessage());
		}
		close();
		
	}
	
	public void insertEvaluation(String studentEmail, int lectureID, String rating, String studentComment) {
		connect();
		try {

			StringBuilder sb = new StringBuilder();
			sb.append("Insert into Evaluation (studentEmail, lectureID, rating, studentComment)")
					.append(" SELECT Student.studentEmail, Lecture.lectureID, '").append(rating).append("', '")
					.append(studentComment).append("'").append(" FROM Student, Lecture")
					.append(" WHERE studentEmail = '").append(studentEmail).append("' AND lectureID = ")
					.append(lectureID).append(";");

			String query = sb.toString();
			// System.out.println(query);

			stmt = conn.createStatement();
			stmt.executeUpdate(query);

		} catch (Exception e) {
			System.out.println("SQLException: " + e.getMessage());
		}
		close();
	}

	public boolean evaluationExists(int lectureid, String studentEmail) {
		boolean hasNext = false;
		connect();
		try {
			stmt = conn.createStatement();

			String query = "SELECT lectureID FROM Evaluation WHERE lectureID = " + lectureid + " AND studentEmail ='"
					+ studentEmail + "' ;";

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
	
	
	//Main for testing
	public static void main(String[] args) throws ParseException {
		DBController test = new DBController();
		test.connect();
		// test.insertCourse("tdt4145", "Datamodellering og
		// databaser","Trondheim", 4);
		// test.insertCourse("tdt4180", "Menneske-maskin
		// interaksjon","Trondheim", 4);
		// test.insertProfessor("sveinbra");
		// test.getCourseInfo();
		// test.insertStudent("karimj","MTING");
		// test.getProfessorsForCoursse("tdt4140");
		// test.getCoursestaughtByProfessor("pekkaa");
		// test.getLectureHoursForCourse("tdt4140");
		// test.getStartDate();
		// test.insertLecture("2017-02-22", "08:00:00", "tdt4145", "sveinbra");
		// test.insertCourseProfessor("sveinbra", "tdt4145");
		// test.insertStudent("negative","MTING");
		// test.insertEvaluation("negative@stud.ntnu.no", 2 , "Confusing", "wow
		// this is the most boring and stupid lecture ever");
		// test.insertCourseStudent("karimj@stud.ntnu.no ", "tdt4145");
		// test.insertStudent("magnutvi", "MLREAL");
		//System.out.println(test.getEvaluationRatingAndComment(2, "karimj@stud.ntnu.no"));
		
		
		//System.out.println(test.getLastTwoCompletedLecturesForCourse("tdt4145"));
		GregorianCalendar gc = new GregorianCalendar(2017, 0, 21, 8, 1);
		test.calendarToStringDateTime(gc);
		
		
		test.close();
		
	}

	

	
	///////////////END OF USEFUL CODE ////////////////////////////////7
	
/*	//Old Load Functions:
	
	public ArrayList<String> getStudentCourses(String studentEmail) {
		ArrayList<String> studentCourses = new ArrayList<>();

		connect();
		try {
			stmt = conn.createStatement();

			String query = "SELECT courseCode FROM CourseStudent WHERE studentEmail = '" + studentEmail + "';";
			if (stmt.execute(query)) {
				rs = stmt.getResultSet();
			}

			while (rs.next()) {
				studentCourses.add(rs.getString(1));
			}

		} catch (Exception e) {
			System.out.println("SQLException: " + e.getMessage());
		}

		close();
		return studentCourses;
	}
	
	public String getStudyProgram(String studentEmail) {
		String studyProgram = "";
		connect();
		try {
			stmt = conn.createStatement();

			String query = "SELECT studyProgramCode FROM Student WHERE studentEmail = '" + studentEmail + "';";
			if (stmt.execute(query)) {
				rs = stmt.getResultSet();
			}

			rs.next();
			studyProgram = rs.getString(1);

		} catch (Exception e) {
			System.out.println("SQLException: " + e.getMessage());
		}
		close();
		return studyProgram;
	}
	
	
	public ArrayList<String> getCourseNameAndLocation(String courseCode) {

		connect();
		ArrayList<String> result = new ArrayList<>();

		try {
			stmt = conn.createStatement();

			String query = "SELECT courseName, courseLocation FROM Course WHERE courseCode = " + "'" + courseCode
					+ "';";

			if (stmt.execute(query)) {
				rs = stmt.getResultSet();
			}

			while (rs.next()) {
				result.add(rs.getString(1));
				result.add(rs.getString(2));

			}

		} catch (Exception e) {
			System.out.println("SQLException: " + e.getMessage());
		}

		close();
		return result;
	}

	public int getLectureHoursForCourse(String courseCode) {
		connect();
		int hours = 0;

		try {
			stmt = conn.createStatement();

			String query = "SELECT lectureHours FROM Course WHERE courseCode = '" + courseCode + "';";
			if (stmt.execute(query)) {
				rs = stmt.getResultSet();
			}

			rs.next();
			hours = rs.getInt(1);

		} catch (Exception e) {
			System.out.println("SQLException: " + e.getMessage());
		}

		close();
		// System.out.println(hours);
		return hours;

	}
	
	public ArrayList<String> getProfessorsForCourse(String courseCode) {

		connect();

		ArrayList<String> professor = new ArrayList<>();

		try {
			stmt = conn.createStatement();

			String query = "SELECT professorUsername FROM CourseProfessor WHERE courseCode = '" + courseCode + "';";
			if (stmt.execute(query)) {
				rs = stmt.getResultSet();
			}

			while (rs.next()) {
				professor.add(rs.getString(1));
			}

		} catch (Exception e) {
			System.out.println("SQLException: " + e.getMessage());
		}

		close();
		// System.out.println(professor);
		return professor;
	}
	
	public ArrayList<String> getLecturesForCourse(String courseCode) {
		ArrayList<String> lectures = new ArrayList<>();

		connect();

		try {
			stmt = conn.createStatement();

			String query = "SELECT lectureID FROM Lecture WHERE courseCode = '" + courseCode + "';";
			if (stmt.execute(query)) {
				rs = stmt.getResultSet();
			}

			while (rs.next()) {
				lectures.add(rs.getString(1));
			}

		} catch (Exception e) {
			System.out.println("SQLException: " + e.getMessage());
		}

		close();
		return lectures;
	}
	
	*/
	
	/*	public ArrayList<String> getLectureDateTimeCourseCodeAndProfessor(int lectureID) {
	connect();

	ArrayList<String> dateTimeAndCourseCode = new ArrayList<>();

	try {
		stmt = conn.createStatement();

		String query = "SELECT lectureDate, lectureTime, courseCode, professorUsername FROM Lecture WHERE lectureID = "
				+ lectureID + ";";
		// System.out.println(query);
		if (stmt.execute(query)) {
			rs = stmt.getResultSet();
		}

		rs.next();
		dateTimeAndCourseCode.add(rs.getString(1));
		dateTimeAndCourseCode.add(rs.getString(2));
		dateTimeAndCourseCode.add(rs.getString(3));
		dateTimeAndCourseCode.add(rs.getString(4));

	} catch (Exception e) {
		System.out.println("SQLException: " + e.getMessage());
	}

	close();
	return dateTimeAndCourseCode;

} */


	
	}

	

	

	


