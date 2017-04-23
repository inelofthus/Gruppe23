package database;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.LinkedHashMap;

import databaseobjects.Course;
import databaseobjects.Evaluation;
import databaseobjects.Lecture;
import databaseobjects.Professor;
import databaseobjects.Student;
import gui.MainController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * DBController --- DBController (DBC) is a class that performs all interactions
 * with the database. Other classes can use an instance of this class to read
 * and write to the database
 * 
 * @author Group 23: Ine Lofthus Arnesen, Kari Meling Johannessen, Nicolai
 *         Cappelen Michelet, Magnus Tvilde
 */

public class DBController {

	Connection conn = null;
	MainController mc = MainController.getInstance();

	/**
	 * The connect method makes a connection to the database. If the connection
	 * takes too long or an SQL exception is thrown, a popup with information is
	 * shown to the user.
	 */
	public void connect() {
		Thread thread = new Thread(new CustomRunnable(this));
		thread.start();
		long endTimeMillis = System.currentTimeMillis() + 2000;
		while (thread.isAlive()) {
			if (System.currentTimeMillis() > endTimeMillis) {
				mc.setConnectionFail(true);
				conn = null;
				break;
			}

			if (mc.isConnectionFail()) {
				try {
					showPopup();
				} catch (IOException e) {
					System.out.println("error in connect " + e.getMessage());
				} finally {
					mc.setConnectionFail(false);
				}
			}

		}
	}

	/**
	 * helper method to connect().This is the function run by the Runnable class
	 * CustomRunnable.
	 * 
	 * @throws SQLException
	 *             if there is a problem establishing a connection
	 */
	public void connectTry() throws SQLException {
		conn = DriverManager
				.getConnection("jdbc:mysql://mysql.stud.ntnu.no/segroup23_db?user=segroup23_user&password=pekkabot");
	}

	/**
	 * The showPopup method is a helper function to connect(). It is called if
	 * there are connection problems and will give user information about
	 * connecting to the database.
	 */
	private void showPopup() throws IOException {
		if (!mc.isConnectionPopupOpen()) {
			final FXMLLoader loader = new FXMLLoader(getClass().getResource("Popup.fxml"));
			Parent root = loader.load();
			Scene scene = new Scene(root);
			Stage primaryStage = new Stage();
			primaryStage.setTitle("RateChill");
			primaryStage.setScene(scene);
			primaryStage.initModality(Modality.APPLICATION_MODAL);
			primaryStage.setResizable(false);
			primaryStage.show();
			mc.setConnectionPopupOpen(true);
		}

	}

	/**
	 * The close method closes the connection to the database. This method
	 * should be called at the end of every method that makes a connection.
	 */
	public void close() {
		if (rs != null) {
			try {
				rs.close();
			} catch (SQLException e) {
				System.out.println("error in close() " + e.getMessage());
			}
		}

		if (conn != null) {
			try {
				conn.close();
			} catch (SQLException e) {
				/* ignored */}
		}

	}

	Statement stmt = null;
	java.sql.PreparedStatement prepStmt = null;
	ResultSet rs = null;

	// ----- Frequently used SQL methods ----- //

	/*
	 * Helper method that executes a query in the database and returns void
	 */

	private void executeVoidQuery(String query, String callerFunction) {
		connect();
		try {
			stmt = conn.createStatement();
			stmt.executeUpdate(query);
		} catch (Exception e) {
			System.out.println("SQLException: " + callerFunction + e.getMessage());
		}
		close();
	}

	/*
	 * Helper function for various exist methods (courseExists, professorExists
	 * etc.) Can be used to see if a particular sql query has any resulting
	 * rows.
	 */
	private boolean hasResult(String query) {
		connect();
		boolean exists = false;

		try {
			stmt = conn.createStatement();

			if (stmt.execute(query)) {
				rs = stmt.getResultSet();
			}

			exists = rs.next(); // rs.next() will be true if the given query
								// returns any rows

		} catch (Exception e) {
			System.out.println("SQLException in courseExists: " + e.getMessage());
		}
		close();
		return exists;

	}

	/**
	 * takes an SQL query as input. Returns a string that contains all data from
	 * first column of table
	 * 
	 * @param query
	 *            SQL query to be executed
	 * @return List A list of strings that result from SQL query execution
	 */
	public ArrayList<String> getStringArray(String query) {
		connect();
		ArrayList<String> list = getStringArrayNC(query);
		close();
		return list;
	}

	/**
	 * NC stands for no connect takes an SQL query as input. Returns a string
	 * that contains all data from first column of table
	 * 
	 * @param query
	 *            SQL query to be executed
	 * @return List A list of strings that result from SQL query execution
	 */
	public ArrayList<String> getStringArrayNC(String query) {

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
			System.out.println("SQLException in getStringArrayNC: " + e.getMessage());
		}

		return list;
	}

	/**
	 * generates appropriate SQL query string for time constraint. The time
	 * constraint makes sure you only get results that have passed and that are
	 * in the same semester as the course.
	 * 
	 * @param {@link
	 * 			databaseobjects.Course course} is a Course object
	 * @return
	 */
	private String SQLtimeConstraint(Course course) {
		String timeConstraint = null;
		char season = course.getSemester().charAt(0);
		String year = course.getSemester().substring(1);

		if (course.isCurrentsemester()) {
			timeConstraint = "(lectureDate < now() OR (lectureDate = now()  AND lectureTime < now()))"
					+ "AND  YEAR(lectureDate) = " + year;
		} else {
			if (season == 'S') {
				timeConstraint = " (lectureDate < now() OR (lectureDate = now()  AND lectureTime < now())) AND (MONTH(lectureDate) < 7) AND YEAR(lectureDate) = "
						+ year;
			} else if (season == 'F') {
				timeConstraint = " (lectureDate < now() OR (lectureDate = now()  AND lectureTime < now())) AND (MONTH(lectureDate) >= 7) AND YEAR(lectureDate) = "
						+ year;
			}
		}
		System.out.println(timeConstraint);
		return timeConstraint;
	}

	/**
	 * The method changeDateFormat changes the format of a date string from the
	 * database to the preferred format presented in the program
	 * 
	 * @param date
	 *            is a String in SQLformat (YYYY-MM-DD)
	 * @return a string with format DD.MM.YYY
	 */
	public String changeDateFormat(String date) {
		String[] dateSplit = date.split("-");
		String yyyy = dateSplit[0];
		String mm = dateSplit[1];
		String dd = dateSplit[2];

		return dd + "." + mm + "." + yyyy;
	}

	// ----- COURSE ----- //

	/**
	 * The insertCourse method inserts a new row in Course table of database
	 * with the values specified in parameters
	 * 
	 * @param courseCode The course code for the course
	 * @param courseName The name of the course
	 * @param lectureHours The number of lecture hours per week for this course
	 * @param taughtInSpring int 0 for false, int 1 for true
	 * @param taughtInAutumn int 0 for false, int 1 for true
	 */
	public void insertCourse(String courseCode, String courseName, int lectureHours, int taughtInSpring,
			int taughtInAutumn) {
		connect();
		boolean spring = true;
		boolean autumn = true;

		if (taughtInSpring == 0) {
			spring = false;
		}
		if (taughtInAutumn == 0) {
			autumn = false;
		}

		insertCourseNC(courseCode, courseName, lectureHours, spring, autumn);

		close();
	}

	/**
	 * The insertCourse method inserts a new row in Course table of database. To
	 * use this method, a connection to the database must have already been
	 * made.
	 * 
	 * @param courseCode
	 *            The course's course code
	 * @param courseName
	 *            The name of the course
	 * @param lectureHours
	 *            The number of lecture hours each week in this course
	 * @param taughtInSpring
	 *            boolean true or false
	 * @param taughtInAutumn
	 *            boolean true or false
	 */
	public void insertCourseNC(String courseCode, String courseName, int lectureHours, boolean taughtInSpring,
			boolean taughtInAutumn) {
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

	/**
	 * Checks in the database if a course with given courseCode exists.
	 * 
	 * @param courseCode
	 *            course code for the course
	 * @return boolean true if course exists. False otherwise
	 */
	public boolean courseExists(String courseCode) {
		String query = "SELECT courseCode FROM Course WHERE courseCode = '" + courseCode + "';";
		return hasResult(query);
	}

	/**
	 * Removes the course with the given courseCode from the database
	 * 
	 * @param courseCode
	 *            The course code for the course
	 */
	public void deleteCourse(String courseCode) {
		String query = "DELETE FROM Course WHERE courseCode='" + courseCode + "'";
		executeVoidQuery(query, "deleteCourse");
	}

	/**
	 * This method takes in a Course object with a specific courseCode. It
	 * collects all the information about this course for the newest semester
	 * and fills in the rest of the course details. Finally It will return the
	 * loaded course object.
	 * 
	 * @param course
	 *            a course object with a preset courseCode
	 * @return course a course object with all course details for the specified
	 *         course.
	 */
	public Course loadCourseInfo(Course course) {
		connect();

		try {
			stmt = conn.createStatement();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		setCourseNameLecHoursAndSemester(course);
		setLectureIDsForCourse(course);
		setCompletedLecturesForCourse(course);
		setRatingValues(course);

		close();

		return course;
	}

	private void setRatingValues(Course course) {
		ArrayList<String> ratingValues = new ArrayList<>();

		connect();
		try {
			String query = "select rating1, rating2, rating3, rating4, rating5 from CourseRatingValues where courseCode = ? order by setDate desc;";

			prepStmt = conn.prepareStatement(query);
			prepStmt.setString(1, course.getCourseCode());
			rs = prepStmt.executeQuery();

			if (rs.next()) {
				for (int i = 1; i < 6; i++) {
					ratingValues.add(rs.getString(i));
				}

				course.setRatingValues(ratingValues);
			}

		} catch (Exception e) {
			System.out.println("SQLException in setRatingValues: " + e.getMessage());
		}

		close();

	}

	/*
	 * This creates the list and linked Hash map with the last 2 completed
	 * lectures and their dates and sets the result in the course object
	 */
	private void setCompletedLecturesForCourse(Course course) {
		try {

			ArrayList<Integer> completedLectureIDs = new ArrayList<>();
			LinkedHashMap<Integer, ArrayList<String>> completedLecturesIDDate = new LinkedHashMap<>();
			int year = Calendar.getInstance().get(Calendar.YEAR);

			StringBuilder sb = new StringBuilder();
			sb.append("SELECT lectureID, lectureDate, lectureTime  FROM Lecture WHERE courseCode = '")
					.append(course.getCourseCode()).append("' ")
					.append(" AND (lectureDate < now() OR (lectureDate = now()  AND lectureTime < now()))")
					.append(" AND  YEAR(lectureDate) = ").append(year)
					.append(" ORDER BY lectureDate DESC, lectureTime DESC;");

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

	/*
	 * This retrieves a list of lectureIDs for the course and sets the results
	 * in the course object
	 */
	private void setLectureIDsForCourse(Course course) {

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

	/*
	 * setCourseNameLecHoursAndSemester sets courseName, location and number of
	 * lecture hours for this specific course and sets result in course object.
	 */
	private void setCourseNameLecHoursAndSemester(Course course) {
		String query = "SELECT courseName, lectureHours, taughtInSpring, taughtInAutumn FROM Course WHERE courseCode = "
				+ "'" + course.getCourseCode() + "';";

		try {
			if (stmt.execute(query)) {
				rs = stmt.getResultSet();
			}

			while (rs.next()) {
				course.setCourseName(rs.getString(1));
				course.setNumLectureHours(rs.getInt(2));
				if (rs.getInt(3) == 1) {
					course.setTaughtInSpring(true);
				} else
					course.setTaughtInSpring(false);
				if (rs.getInt(4) == 1) {
					course.setTaughtInAutumn(true);
				} else
					course.setTaughtInAutumn(false);
			}
		} catch (Exception e) {
			System.out.println("error in helper function DBC.setCourseNameLecHoursAndSemester:" + e.getMessage());
		}

	}

	/**
	 * Retrieves the lecture dates and times for a lectures in a course. Returns
	 * the result as a list of strings with the newest lecture first.
	 * 
	 * @param courseCode the course code for the course
	 * @return list a list of strings in format DD.MM.YYY hh:mm:ss
	 */
	public ArrayList<String> getLectureDateAndTimeForCourse(String courseCode) {
		connect();

		ArrayList<String> lectures = new ArrayList<>();
		String query = "select lectureDate, lectureTime from Lecture where courseCode = ? ORDER BY lectureDate ASC";

		try {
			prepStmt = conn.prepareStatement(query);
			prepStmt.setString(1, courseCode);
			rs = prepStmt.executeQuery();

			String date;
			String time;
			String result;

			while (rs.next()) {
				date = rs.getString(1);
				time = rs.getString(2);
				result = String.format("%s \t \t %s", changeDateFormat(date), time);
				lectures.add(result);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		close();
		return lectures;
	}

	/**
	 * Retrieves all the courses that exist in the database
	 * 
	 * @return courses a list of course codes and names.
	 */
	public ArrayList<String> getAllCourses() {
		ArrayList<String> courses = new ArrayList<>();

		connect();
		try {
			String query = "select courseCode, courseName from Course";

			prepStmt = conn.prepareStatement(query);
			rs = prepStmt.executeQuery();

			while (rs.next()) {
				courses.add(rs.getString(1) + " " + rs.getString(2));
			}

		} catch (Exception e) {
			System.out.println("SQLException in getAllCourses: " + e.getMessage());
		}

		close();

		return courses;
	}

	/**
	 * inserts new rating values for a given course into the database. Database
	 * automatically places a time stamp on when it was inserted.
	 * 
	 * @param courseCode
	 *            The course code for this course
	 * @param rating1
	 *            the first rating value
	 * @param rating2
	 *            the second rating value
	 * @param rating3
	 *            the third rating value
	 * @param rating4
	 *            the fourth rating value
	 * @param rating5
	 *            the fifth rating value
	 */
	public void insertCourseRatingValues(String courseCode, String rating1, String rating2, String rating3,
			String rating4, String rating5) {

		connect();
		try {
			String query = "insert into CourseRatingValues (courseCode, rating1, rating2, rating3, rating4, rating5) VALUES(?,?,?,?,?,?)";

			prepStmt = conn.prepareStatement(query);
			prepStmt.setString(1, courseCode);
			prepStmt.setString(2, rating1);
			prepStmt.setString(3, rating2);
			prepStmt.setString(4, rating3);
			prepStmt.setString(5, rating4);
			prepStmt.setString(6, rating5);

			int i = prepStmt.executeUpdate();
			System.out.println(i + " records inserted");

		} catch (Exception e) {
			System.out.println("SQLException in InsertCourseRatingValues: " + e.getMessage());
		}
		close();
	}

	// ----- LECTURE ----- //

	/**
	 * This method takes in a Lecture object with a specific lectureID. It
	 * collects all the information about this lecture and fills in the rest of
	 * the course details. Finally It will return the loaded leture object.
	 * 
	 * @param lecture
	 *            A lecture object with a specified lectureId
	 */
	public void loadLectureInfo(Lecture lecture) {
		connect();

		String courseCode = null;

		try {
			stmt = conn.createStatement();

			String query = "SELECT lectureDate, lectureTime, courseCode, professorUsername FROM Lecture WHERE lectureID = "
					+ lecture.getLectureID() + ";";

			if (stmt.execute(query)) {
				rs = stmt.getResultSet();
			}

			rs.next();
			String date = rs.getString(1); // date format: "YYYY-MM-DD"
			String time = rs.getString(2); // time format: "hh:mm:ss"
			ArrayList<String> dateTime = new ArrayList<String>();
			dateTime.add(date);
			dateTime.add(time);

			lecture.setLectureDateAndTime(dateTime);
			courseCode = rs.getString(3);
			lecture.setCourseCode(courseCode);
			lecture.setProfessor(rs.getString(4));

		} catch (Exception e) {
			System.out.println("SQLException loadLectureInfo: " + e.getMessage());
		}

		setEvaluationsForLecture(lecture, courseCode);

		close();

	}

	private void setEvaluationsForLecture(Lecture lecture, String courseCode) {

		Course course = new Course(courseCode);
		ArrayList<String> ratingValues = course.getRatingValues();

		ArrayList<Evaluation> evaluations = new ArrayList<>();
		ArrayList<Evaluation> Evaluations1 = new ArrayList<>();
		ArrayList<Evaluation> Evaluations2 = new ArrayList<>();
		ArrayList<Evaluation> Evaluations3 = new ArrayList<>();
		ArrayList<Evaluation> Evaluations4 = new ArrayList<>();
		ArrayList<Evaluation> Evaluations5 = new ArrayList<>();

		try {

			String query = "select rating1, rating2, rating3, rating4, rating5 from CourseRatingValues where setDate < ? order by setDate desc;";
			prepStmt = conn.prepareStatement(query);
			String lecDateTime = lecture.getLectureDate() + " " + lecture.getLectureTime();
			prepStmt.setString(1, lecDateTime);
			rs = prepStmt.executeQuery();
			if (rs.next()) {
				ratingValues.clear();
				ratingValues.add(rs.getString(1));
				ratingValues.add(rs.getString(2));
				ratingValues.add(rs.getString(3));
				ratingValues.add(rs.getString(4));
				ratingValues.add(rs.getString(5));
			}

			query = "select studentUsername, rating, studentComment from Evaluation where lectureID = "
					+ lecture.getLectureID() + ";";
			if (stmt.execute(query)) {
				rs = stmt.getResultSet();
			}

			String studentUsername;
			String rating;
			String studentComment;
			Evaluation eval;

			while (rs.next()) {
				studentUsername = rs.getString(1);
				rating = rs.getString(2);
				studentComment = rs.getString(3);
				eval = new Evaluation(rating, studentComment, lecture.getLectureID(), studentUsername);
				evaluations.add(eval);

				if (rating.equals(ratingValues.get(0))) {
					Evaluations1.add(eval);
				}
				if (rating.equals(ratingValues.get(1))) {
					Evaluations2.add(eval);
				}
				if (rating.equals(ratingValues.get(2))) {
					Evaluations3.add(eval);
				}
				if (rating.equals(ratingValues.get(3))) {
					Evaluations4.add(eval);
				}
				if (rating.equals(ratingValues.get(4))) {
					Evaluations5.add(eval);
				}

			}

		} catch (Exception e) {
			System.out.println("SQLException setEvaluationsForLecture: " + e.getMessage());
		}

		lecture.setEvaluations(evaluations);
		lecture.setEvaluationsRating1(Evaluations1);
		lecture.setEvaluationsRating2(Evaluations2);
		lecture.setEvaluationsRating3(Evaluations3);
		lecture.setEvaluationsRating4(Evaluations4);
		lecture.setEvaluationsRating5(Evaluations5);
		lecture.setRatingValues(ratingValues);

	}

	/**
	 * Inserts a new Lecture into the database with the specified parameters
	 * 
	 * @param date
	 *            String of format (YYYY-MM-DD)
	 * @param time
	 *            String of format (HH:MM:SS)
	 * @param courseCode
	 *            courseCode for the course
	 * @param professorUsername
	 *            professor username from NTNU
	 * @throws SQLException
	 *             is thrown if there is an SQL exception
	 */
	public void insertLecture(String date, String time, String courseCode, String professorUsername)
			throws SQLException {
		// Date format: "YYYY-MM-DD"
		// Time format: "HH:MM:SS"
		connect();
		insertLectureNC(date, time, courseCode, professorUsername);
		close();
	}

	public void insertLectureNC(String date, String time, String courseCode, String professorUsername)
			throws SQLException {
		// Date format: "YYYY-MM-DD"
		// Time format: "HH:MM:SS"

		String query = buildLectureQuery(date, time, courseCode, professorUsername);

		stmt = conn.createStatement();
		stmt.executeUpdate(query);

	}

	/*
	 * Helper function for insertLecture. Builds the required query.
	 */
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

	/**
	 * @param dateTime
	 *            a list where first element is date (format: YYYY-MM-DD) and
	 *            second element is time (format: HH:MM:SS)
	 * @param courseCode
	 *            courseCode for the course
	 * @return lectureID lectureID for this particular lecture
	 */
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
			System.out.println("SQLException in getLectureID: " + e.getMessage());

		} finally {
			close();
		}

		return id;
	}

	/**
	 * Removes the lecture with the specified lectureID from the database
	 * 
	 * @param lectureID
	 *            lecture ID for the lecture to be deleted
	 */
	public void deleteLecture(int lectureID) {
		String query = "DELETE FROM Lecture WHERE lectureID='" + lectureID + "'";
		executeVoidQuery(query, "deleteLecture");
	}

	/**
	 * Checks in the database if there exists a lecture with the specified
	 * lectureID
	 * 
	 * @param lectureID
	 *            lecture ID for lecture to be checked
	 * @return lectureExists (true or false)
	 */
	public boolean lectureExists(int lectureID) {
		String query = "SELECT lectureID FROM Lecture WHERE lectureID = " + lectureID + ";";
		return hasResult(query);

	}

	/**
	 * deletes all of this courses lectures during this period.
	 * 
	 * @param courseCode
	 *            courseCode for the course
	 * @param startDate
	 *            The date of the first lecture to be deleted.
	 *            Format(YYYY-MM-DD)
	 * @param endDate
	 *            The date of the last lecture to be deleted. Format(YYYY-MM-DD)
	 */
	public void deleteLecturesForPeriod(String courseCode, String startDate, String endDate) {
		connect();

		String query = "DELETE FROM Lecture WHERE courseCode = ? AND lectureDate >= ? AND lectureDate <= ? ";

		try {
			prepStmt = conn.prepareStatement(query);
			prepStmt.setString(1, courseCode);
			prepStmt.setString(2, startDate);
			prepStmt.setString(3, endDate);

			prepStmt.executeUpdate();

		} catch (SQLException e) {
			System.out.println("SQL exception in deleteLecturesForPeriod: " + e.getMessage());
		}

		close();
	}

	/**
	 * Adds new lectures into the database. Can add single lectures or lectures
	 * that repeat weekly.
	 * 
	 * @param courseCode
	 *            the course's course code
	 * @param startTime
	 *            the startTime of the lecture. Format (HH:MM:SS)
	 * @param startDate
	 *            the date of the first lecture to be added. Format (YYYY-MM-DD)
	 * @param endDate If lectures repeat weakly, the date that they will repeat until. Format(YYYY-MM-DD)
	 * @param repeat:
	 *            a boolean value that specifies if the lecture should repeat
	 *            weekly
	 * @param professorUsername
	 *            The professor's username from NTNU
	 * @throws SQLException
	 *             is thrown if the query throws an sql exception.
	 */
	public void addLectures(String courseCode, String startTime, String startDate, String endDate, boolean repeat,
			String professorUsername) throws SQLException {

		int numWeeks = 0;
		String[] startDateSplit = startDate.split("-");
		int mmStart = Integer.valueOf(startDateSplit[1]);
		int ddStart = Integer.valueOf(startDateSplit[2]);
		int year = Calendar.getInstance().get(Calendar.YEAR);
		LocalDate start = LocalDate.of(year, mmStart, ddStart);

		if (repeat) { // if lectures repeat weekly
			String[] endDateSplit = endDate.split("-");
			int mmEnd = Integer.valueOf(endDateSplit[1]);
			int ddEnd = Integer.valueOf(endDateSplit[2]);

			LocalDate end = LocalDate.of(year, mmEnd, ddEnd);
			numWeeks = (int) ChronoUnit.WEEKS.between(start, end);
		}

		int MM;
		int DD;

		connect();
		for (int i = 0; i < numWeeks + 1; i++) {
			MM = start.getMonthValue();
			DD = start.getDayOfMonth();
			String date = year + "-" + MM + "-" + DD;
			insertLectureNC(date, startTime, courseCode, professorUsername);

			start = start.plusWeeks(1);
		}
		close();
	}

	// ----- PROFESSOR ----- //

	/**
	 * Retrieves relevant professor information from the database and updates
	 * the professor object accordingly.
	 * 
	 * @param prof
	 *            professor object with a specified username.
	 */
	public void loadProfessorInfo(Professor prof) {

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

	/**
	 * Inserts a new professor into database with the specified username and
	 * password. Password should already be encrypted.
	 * 
	 * @param professorUsername
	 *            The professor's username from NTNU
	 * @param password
	 *            Professors password that has been hashed.
	 */
	public void insertProfessor(String professorUsername, String password) {
		connect();
		insertProfessorNC(professorUsername, password);
		close();
	}

	/**
	 * Inserts a new professor into database with the specified username and
	 * password. A connection must already have been made in order to use this
	 * method.
	 * 
	 * @param professorUsername
	 *            The professor's username from NTNU.
	 * @param password
	 *            Professors password that has been hashed.
	 */
	public void insertProfessorNC(String professorUsername, String password) {
		try {

			String query = "INSERT INTO Professor VALUES(?,?)";

			prepStmt = conn.prepareStatement(query);
			prepStmt.setString(1, professorUsername);
			prepStmt.setString(2, password);
			int i = prepStmt.executeUpdate();
			System.out.println(i + " records inserted");

		} catch (Exception e) {
			System.out.println("SQLException in InsertProfessor: " + e.getMessage());
		}
	}

	/**
	 * Checks if a professor with the given username exists in the database.
	 * 
	 * @param professorUsername
	 *            The professor's username from NTNU.
	 * @return boolean true if the professor exists in the database. False
	 *         otherwise.
	 */
	public boolean professorExists(String professorUsername) {
		String query = "SELECT professorUsername FROM Professor WHERE professorUsername = '" + professorUsername + "';";
		return hasResult(query);
	}

	/**
	 * Returns lectureIDs for all lectures given by this professor that have
	 * already passed in specified course ordered by the newest lecture first
	 * 
	 * @param courseCode
	 *            The courseCode of the course that lectures should be retrieved
	 *            from
	 * @param professorUsername
	 *            The professors username from NTNU
	 * @return list A list for lectureIDs for lectures that have been compeleted
	 */
	public ArrayList<Integer> getCompletedLecturesForCourseByProfessor(String courseCode, String professorUsername) {

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
			System.out.println("SQLException in getCompletedLecturesForCourseByProfessor: " + e.getMessage());
		}
		close();

		return lectures;

	}

	/**
	 * Deletes the professor from the database
	 * 
	 * @param username
	 *            The professor's username from NTNU
	 */
	public void deleteProfessor(String username) {
		String query = "DELETE FROM Professor WHERE professorUsername='" + username + "'";
		executeVoidQuery(query, "deleteProfessor");
	}

	/**
	 * Checks the given encrypted password and compares it to the encrypted
	 * password that is saved in the database.
	 * 
	 * @param professorUsername
	 *            The professor's username from NTNU
	 * @param encryptedPassword
	 *            Password to be checked. The password should have already been
	 *            encrypted with a hash function
	 * @return boolean True if the password is correct and false otherwise.
	 * @throws SQLException
	 *             An SQLException is thrown if the running the query gives an
	 *             SQL exception
	 */
	public boolean checkProfessorPassword(String professorUsername, String encryptedPassword) throws SQLException {
		String query = "select * from Professor where professorUsername = '" + professorUsername
				+ "' and professorPassword = '" + encryptedPassword + "';";
		return hasResult(query);
	}

	/**
	 * Updates the professor's password in the database with a new encrypted
	 * password
	 * 
	 * @param username
	 *            The professor's username from NTNU
	 * @param hashPassword
	 *            The new password that has been hashed with a hash function
	 */
	public void updateProfessor(String username, String hashPassword) {
		connect();
		try {
			String query = "update Professor SET professorPassword = ? WHERE professorUsername = ?;";

			prepStmt = conn.prepareStatement(query);
			prepStmt.setString(1, hashPassword);
			prepStmt.setString(2, username);
			prepStmt.executeUpdate();

		} catch (Exception e) {
			System.out.println("SQLException in InsertProfessor: " + e.getMessage());
		}
		close();
	}

	// ----- STUDENT ----- //

	/**
	 * Takes in a Student object with a preset username. This Method then reads
	 * all relevant information about this student and sets the instance's
	 * attributes accordingly.
	 * 
	 * @param student
	 *            A Student object with a preset username.
	 */
	public void loadStudentInfo(Student student) {
		connect();
		try {
			// First students studyProgram is retrieved from DB
			stmt = conn.createStatement();

			String query = "SELECT studyProgramCode FROM Student WHERE studentUsername = '" + student.getUsername()
					+ "';";
			if (stmt.execute(query)) {
				rs = stmt.getResultSet();
			}

			rs.next();
			student.setStudyProgram(rs.getString(1));

			// Find the courseCodes and corresponding courseNames for this
			// student
			String query2 = "select c.courseCode, courseName FROM Course c JOIN CourseStudent cs ON c.courseCode = cs.courseCode WHERE studentUsername = '"
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

	/**
	 * Inserts a new student into the database with the given username and study
	 * program code.
	 * 
	 * @param studentUsername
	 *            The Student's username
	 * @param studyProgramCode
	 *            The code for this student's program of study
	 */
	public void insertStudent(String studentUsername, String studyProgramCode) {
		connect();
		try {

			String query = "INSERT INTO Student VALUES(?,?)";

			prepStmt = conn.prepareStatement(query);
			prepStmt.setString(1, studentUsername);
			prepStmt.setString(2, studyProgramCode);
			int i = prepStmt.executeUpdate();
			System.out.println(i + " records inserted");

		} catch (Exception e) {
			System.out.println("SQLException: " + e.getMessage());
		}
		close();
	}

	/**
	 * Removes the student with the specified username from the database
	 * 
	 * @param studentUsername
	 *            The student's username
	 */
	public void deleteStudent(String studentUsername) {
		String query = "DELETE FROM Student WHERE studentUsername='" + studentUsername + "'";
		executeVoidQuery(query, "deleteStudent");
	}

	/**
	 * Checks in the database if a student with the specified username exists in
	 * the database
	 * 
	 * @param studentUsername
	 *            the student's username
	 * @return boolean True if the student exists and false otherwise
	 */
	public boolean studentExists(String studentUsername) {
		String query = "SELECT studentUsername FROM Student WHERE studentUsername = '" + studentUsername + "';";
		return hasResult(query);
	}

	/**
	 * Checks in the database whether a student has given an evaluation for a
	 * specific lecture
	 * 
	 * @param studentUsername
	 *            The student's usernmae
	 * @param lecID
	 *            the lecture ID of the lecture to be checked
	 * @return boolean True if the student has evaluated the lecture and false
	 *         otherwise
	 */
	public boolean studentHasEvaluatedLecture(String studentUsername, int lecID) {
		String query = "SELECT * FROM Evaluation WHERE studentUsername = '" + studentUsername + "' AND lectureID ="
				+ lecID + ";";
		return hasResult(query);
	}

	// ----- COURSE PROFESSOR ----- //

	/**
	 * Inserts a new course-professor relationship in the database
	 * 
	 * @param professorUsername
	 *            The professor's username
	 * @param courseCode
	 *            the course code for the course taught by the professor
	 */
	public void insertCourseProfessor(String professorUsername, String courseCode) {
		connect();
		insertCourseProfessorNC(professorUsername, courseCode);
		close();
	}

	/**
	 * Does the same as insertCourseProfessor except making and closing the
	 * connection to the database. To use the function, you must already be
	 * connected.
	 * 
	 * @param professorUsername
	 *            the Professor's username
	 * @param courseCode
	 *            the course code for the course
	 */
	public void insertCourseProfessorNC(String professorUsername, String courseCode) {
		// already connected
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

	// ----- COURSE STUDENT ----- //

	/**
	 * Inserts a new course-student relationship in the database
	 * 
	 * @param studentUsername
	 *            the students username
	 * @param courseCode
	 *            the course code for the course
	 */
	public void insertCourseStudent(String studentUsername, String courseCode) {
		StringBuilder sb = new StringBuilder();
		sb.append("Insert into CourseStudent (studentUsername, courseCode)")
				.append(" SELECT Student.studentUsername, Course.courseCode").append(" FROM Course, Student")
				.append(" WHERE courseCode = '").append(courseCode).append("'").append("AND studentUsername = '")
				.append(studentUsername).append("'");

		String query = sb.toString();
		executeVoidQuery(query, "insertCourseStudent");
	}

	/**
	 * Removes the course- student relationship in the database
	 * 
	 * @param username
	 *            the student's username
	 * @param courseCode
	 *            the course code for the course
	 */
	public void deleteCourseStudent(String username, String courseCode) {
		String query = "DELETE FROM CourseStudent WHERE courseCode='" + courseCode + "' AND studentUsername ='"
				+ username + "';";
		executeVoidQuery(query, "deleteCourseStudent");
	}

	// ----- EVALUATION ----- //

	/**
	 * Takes in an evaluation object with a preset studentUsername and
	 * lectureID. The relevant information about this evaluation is read from
	 * the database and set to the evaluation objects attributes accordingly.
	 * 
	 * @param evaluation
	 *            An evaluation object with a preset studentUsername and
	 *            lectureID
	 */
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

	/**
	 * Overwrites a previously given evaluation for a lecture
	 * 
	 * @param username
	 *            the student's username
	 * @param lectureID
	 *            the lecture ID for the lecture
	 * @param rating
	 *            the rating that was given
	 * @param comment
	 *            the comment about the lecture given by the student
	 */
	public void overwriteEvaluation(String username, int lectureID, String rating, String comment) {
		connect();

		try {

			// delete Evaluation for this student for this lecture
			StringBuilder sb1 = new StringBuilder();
			sb1.append("DELETE FROM Evaluation WHERE studentUsername = '").append(username).append("' ")
					.append("AND lectureID =").append(lectureID).append(";");

			String query1 = sb1.toString();
			stmt = conn.createStatement();
			stmt.executeUpdate(query1);

			// insert new Evaluation
			StringBuilder sb = new StringBuilder();
			sb.append("Insert into Evaluation (studentUsername, lectureID, rating, studentComment)")
					.append(" SELECT Student.studentUsername, Lecture.lectureID, '").append(rating).append("', '")
					.append(comment).append("'").append(" FROM Student, Lecture").append(" WHERE studentUsername = '")
					.append(username).append("' AND lectureID = ").append(lectureID).append(";");

			String query2 = sb.toString();
			// System.out.println(query);

			stmt = conn.createStatement();
			stmt.executeUpdate(query2);

		} catch (Exception e) {
			System.out.println("SQLException: " + e.getMessage());
		}
		close();

	}

	/**
	 * Inserts a new evaluation into the database
	 * 
	 * @param studentUsername
	 *            the student's username
	 * @param lectureID
	 *            the lectureID for the lecture that was evaluated
	 * @param rating
	 *            the rating that was given
	 * @param studentComment
	 *            the students comment about the lecture
	 */
	public void insertEvaluation(String studentUsername, int lectureID, String rating, String studentComment) {
		connect();
		try {

			String query = "Insert into Evaluation (studentUsername, lectureID, rating, studentComment) VALUES(?,?,?,?)";

			prepStmt = conn.prepareStatement(query);
			prepStmt.setString(1, studentUsername);
			prepStmt.setInt(2, lectureID);
			prepStmt.setString(3, rating);
			prepStmt.setString(4, studentComment);

			int i = prepStmt.executeUpdate();
			System.out.println(i + " records inserted");

		} catch (Exception e) {
			System.out.println("SQLException in insertEvaluation: " + e.getMessage());
		}
		close();
	}

	/**
	 * Checks if an evaluation by a specific student for a specific lecture
	 * exists in the database
	 * 
	 * @param lectureid
	 *            the lecture ID for the lecture
	 * @param studentUsername
	 *            the student's username
	 * @return boolean true if the evaluation exists and false otherwise
	 */
	public boolean evaluationExists(int lectureid, String studentUsername) {
		String query = "SELECT lectureID FROM Evaluation WHERE lectureID = " + lectureid + " AND studentUsername ='"
				+ studentUsername + "' ;";
		return hasResult(query);
	}

	/**
	 * Sets values to the course attributes needed to generate the lectures over
	 * time graph
	 * 
	 * @param course
	 *            A course object for the course to be generated a graph for
	 */
	public void setCourseRatingsOverTime(Course course) {

		// Maps lectureID to the total number of evaluations given for that
		// lecture
		HashMap<Integer, Integer> lecIDtoNumRatings = new HashMap<>();

		// Maps lectureID to the total number of evaluations given for that
		// lecture with a specific rating
		HashMap<Integer, Integer> lecIDtoRatingCount1 = new HashMap<>();
		HashMap<Integer, Integer> lecIDtoRatingCount2 = new HashMap<>();
		HashMap<Integer, Integer> lecIDtoRatingCount3 = new HashMap<>();
		HashMap<Integer, Integer> lecIDtoRatingCount4 = new HashMap<>();
		HashMap<Integer, Integer> lecIDtoRatingCount5 = new HashMap<>();

		try {

			connect();

			stmt = conn.createStatement();
			StringBuilder sb = new StringBuilder();

			sb.append(
					"select e.rating, e.lectureID, count(*) As 'ratingCount' From Evaluation e JOIN Lecture l on e.lectureID = l.lectureID WHERE l.courseCode = '")
					.append(course.getCourseCode()).append("' AND ").append(SQLtimeConstraint(course))
					.append(" GROUP BY e.rating, e.lectureID ORDER BY e.lectureID;");

			String query4 = sb.toString();
			System.out.println(query4);

			if (stmt.execute(query4)) {
				rs = stmt.getResultSet();
			}

			while (rs.next()) {

				String rating = rs.getString(1);
				int lecID = rs.getInt(2);
				int count = rs.getInt(3);
				ArrayList<String> ratingValues = course.getRatingValues();

				int numRatingsForLec = lecIDtoNumRatings.containsKey(lecID) ? lecIDtoNumRatings.get(lecID) : 0;
				lecIDtoNumRatings.put(lecID, numRatingsForLec + count);

				if (rating.equals(ratingValues.get(0))) {
					lecIDtoRatingCount1.put(lecID, count);
				} else if (rating.equals(ratingValues.get(1))) {
					lecIDtoRatingCount2.put(lecID, count);
				} else if (rating.equals(ratingValues.get(2))) {
					lecIDtoRatingCount3.put(lecID, count);
				} else if (rating.equals(ratingValues.get(3))) {
					lecIDtoRatingCount4.put(lecID, count);
				} else if (rating.equals(ratingValues.get(4))) {
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

}
