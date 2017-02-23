package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.util.ArrayList;


import databaseobjects.Evaluation;

public class DBController {
	
	Connection conn = null;

	public void connect() {
		try {
			conn = DriverManager.getConnection(
					"jdbc:mysql://mysql.stud.ntnu.no/segroup23_db?user=segroup23_user&password=pekkabot");
			//System.out.println("connection succesfull :) ");

		} catch (SQLException ex) {
			System.out.println("SQLeXCEPTION: " + ex.getMessage());

		}
	}

	public void close() {
		if (rs != null) {
	        try {
	            rs.close();
	        } catch (SQLException e) { /* ignored */}
	    }
	    
	    if (conn != null) {
	        try {
	            conn.close();
	            //System.out.println("CONNECTION CLOSED");
	        } catch (SQLException e) { /* ignored */}
	    }
		
	}
	
	Statement stmt = null;
	ResultSet rs = null;

	public ArrayList<String> getStringArray(String query){
		
		connect();
		
		ArrayList<String> list = new ArrayList<>();
		try {
			stmt = conn.createStatement();

			if (stmt.execute(query)) {
				rs = stmt.getResultSet();
			}
			
			while(rs.next()){
				list.add(rs.getString(1)); 
			}
			

		} catch (Exception e) {
			System.out.println("SQLException: " + e.getMessage());
		}
		
		close();
		//System.out.println(professor);
		return list;
	}
	
	public ArrayList<Integer> getIntArray(String query){
		
		connect();
		
		ArrayList<Integer> list = new ArrayList<>();
		try {
			stmt = conn.createStatement();

			if (stmt.execute(query)) {
				rs = stmt.getResultSet();
			}
			
			while(rs.next()){
				list.add(rs.getInt(1)); 
			}
			

		} catch (Exception e) {
			System.out.println("SQLException: " + e.getMessage());
		}
		
		close();
		//System.out.println(professor);
		return list;
	}
	
	public int getInt(String query){
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
	
	//Course info
	
	public void getCourseInfo() {
		connect();
		
		try {
			stmt = conn.createStatement();

			String query = "SELECT * FROM Course";
			if (stmt.execute(query)) {
				rs = stmt.getResultSet();
			}

			while (rs.next()) {
				String courseCode = rs.getString(1);
				String courseName = rs.getString(2);
				String courseLocation = rs.getString(3);
				String lectureHours = rs.getString(4);

				System.out.println(courseCode + " " + courseName + " is a course taught in " + courseLocation
						+ " It has " + lectureHours + " lecture hours each week ");
			}

		} catch (Exception e) {
			System.out.println("SQLException: " + e.getMessage());
		}
		close();
	}
	
	public ArrayList<String> getProfessorsForCourse(String courseCode){
		
		connect();
		
		ArrayList<String> professor = new ArrayList<>();
		
		try {
			stmt = conn.createStatement();

			String query = "SELECT professorUsername FROM CourseProfessor WHERE courseCode = '" + courseCode +"';";
			if (stmt.execute(query)) {
				rs = stmt.getResultSet();
			}
			
			while(rs.next()){
				professor.add(rs.getString(1)); 
			}
			

		} catch (Exception e) {
			System.out.println("SQLException: " + e.getMessage());
		}
		
		close();
		//System.out.println(professor);
		return professor;
	}
	
	public ArrayList<String> getCourseNameAndLocation(String courseCode) {
			
		connect();
			ArrayList<String> result = new ArrayList<>();
			
			try {
				stmt = conn.createStatement();
	
				String query = "SELECT courseName, courseLocation FROM Course WHERE courseCode = " + "'" + courseCode +"';";
				
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
	
	public void insertCourse(String courseCode, String courseName, String courseLocation, int lectureHours) {
		connect();
		
		try {

			StringBuilder sb = new StringBuilder();
			sb.append("INSERT INTO Course VALUES('")
			.append(courseCode).append("','")
			.append(courseName).append("','")
			.append(courseLocation).append("',")
			.append(lectureHours).append(");");

			String query = sb.toString();

			stmt = conn.createStatement();
			stmt.executeUpdate(query);

		} catch (Exception e) {
			System.out.println("SQLException: " + e.getMessage());
		}
		
		close();

	}
	
	public ArrayList<String> getLecturesForCourse(String courseCode){
		ArrayList<String> lectures = new ArrayList<>();
		
		connect();
		
		try {
			stmt = conn.createStatement();

			String query = "SELECT lectureID FROM Lecture WHERE courseCode = '" + courseCode +"';";
			if (stmt.execute(query)) {
				rs = stmt.getResultSet();
			}
			
			while(rs.next()){
				lectures.add(rs.getString(1)); 
			}
			

		} catch (Exception e) {
			System.out.println("SQLException: " + e.getMessage());
		}
		
		close();
		return lectures;
	}
	
	public boolean courseExists(String courseCode){
		
		connect();
		boolean hasNext = false;
		try {
			stmt = conn.createStatement();

			String query = "SELECT courseCode FROM Course WHERE courseCode = '" + courseCode +"';";
			
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
	
	public ArrayList<Integer> getLastTwoCompletedLecturesForCourse(String courseCode){
		ArrayList<Integer> lectures = new ArrayList<>();
		
		connect();
		try {
			stmt = conn.createStatement();
			
			StringBuilder sb = new StringBuilder();
			sb.append("SELECT * FROM Lecture WHERE courseCode = '").append(courseCode).append("' ")
			.append(" AND (lectureDate < now() OR (lectureDate = now()  AND lectureTime < now())) ORDER BY lectureDate DESC, lectureTime DESC;");
			
			String query = sb.toString();
			if (stmt.execute(query)) {
				rs = stmt.getResultSet();
			}
			
			rs.next();
			lectures.add(rs.getInt(1)); 
			rs.next();
			lectures.add(rs.getInt(1));	
			

		} catch (Exception e) {
			System.out.println("SQLException: " + e.getMessage());
		}
	
		close();		
		return lectures;
		
	}
	
	//Lecture info
	public ArrayList<String> getLectureDateTimeCourseCodeAndProfessor(int lectureID){
		connect();		
		
		ArrayList<String> dateTimeAndCourseCode = new ArrayList<>();
		
		try {
			stmt = conn.createStatement();

			String query = "SELECT lectureDate, lectureTime, courseCode, professorUsername FROM Lecture WHERE lectureID = " + lectureID + ";";
			//System.out.println(query);
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
		
	}
	
	public int getLectureHoursForCourse(String courseCode){
		connect();
		int hours =0;
		
		try {
			stmt = conn.createStatement();

			String query = "SELECT lectureHours FROM Course WHERE courseCode = '" + courseCode +"';";
			if (stmt.execute(query)) {
				rs = stmt.getResultSet();
			}
			
			rs.next();
			hours = rs.getInt(1);
			
						

		} catch (Exception e) {
			System.out.println("SQLException: " + e.getMessage());
		}
		
		close();
		//System.out.println(hours);
		return hours;
		
	}
	
	public ArrayList<Evaluation> getEvaluationsForLecture(int lectureID, DBController DBC){
		
		connect();
		ArrayList<Evaluation> evaluations = new ArrayList<>();
		
		try {
			stmt = conn.createStatement();

			String query = "select studentEmail, rating, studentComment from Evaluation where lectureID = " + lectureID + ";";
			if (stmt.execute(query)) {
				rs = stmt.getResultSet();
			}

			while (rs.next()) {
				
				String studentEmail  = rs.getString(1);
				String rating = rs.getString(2);
				String studentComment = rs.getString(3);
				Evaluation eval = new Evaluation(DBC, rating, studentComment, lectureID, studentEmail);
				evaluations.add(eval);
			
			}

		} catch (Exception e) {
			System.out.println("SQLException: " + e.getMessage());
		}
		
		close();
		return evaluations;
	}
	
	public void insertLecture(String date, String time, String courseCode, String professorUsername){
		// Date format: "YYYY-MM-DD"
		// Time format: "HH:MM:SS"
		connect();
		try {

			String query = buildLectureQuery(date, time, courseCode, professorUsername);
			//System.out.println(query);

			stmt = conn.createStatement();
			stmt.executeUpdate(query);

		} catch (Exception e) {
			System.out.println("SQLException: " + e.getMessage());
		}
		close();
	}
	
	public boolean lectureExists(int lectureID){
		connect();
		boolean hasNext = false;
		try {
			stmt = conn.createStatement();

			String query = "SELECT lectureID FROM Lecture WHERE lectureID = " + lectureID +";";
			
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
	
	/*public void insertLectures(int weeksUntilExam, String courseCode){
		//TODO Insert number of lectures per week for a whole semester. Automatically set day to Monday, Wednesday, (Friday if 3 lectures per week) 08:00. 
		//Add lectures until exam date: needs function that gets Exam date from API and calculates number of weeks until Exam.
		//If a course is taught by more than 1 professer this function will automatically add just the 1st professor
		
		int numLectures = getLectureHoursForCourse(courseCode)/2;
		System.out.println("numLectures: " + numLectures);
		List<String> firstLec = getStartDate();
		String Monday = firstLec.get(0);
		String Wednesday = firstLec.get(1);
		String Friday = firstLec.get(2);
		String professor = getProfessorsForCourse(courseCode).get(0);
		String query;
		String time = "08:00:00";
				
		if (numLectures == 1) {
						
			//Add lectures every monday
			
			
		}if (numLectures == 2) {
				
			//Add lectures every wednesday
			
		}
		
	} */
	
//	private List<String> getStartDate(){
//		//Return SQL friendly list of date Strings of next Monday, Wednesday and Friday  YYYY-MM-DD
//		ArrayList<String> startDates = new ArrayList<>();
//		String YYYY, MM, DD, Monday, Wednesday, Friday;
//		
//		GregorianCalendar date =new GregorianCalendar();            
//		//String CurMonth = String.valueOf(date.get(GregorianCalendar.MONTH) + 1);            
//		//String CurDay = String.valueOf(date.get(GregorianCalendar.DAY_OF_MONTH));
//		//String CurYear = String.valueOf(date.get(GregorianCalendar.YEAR));
//		
//		//System.out.println("year: " + CurYear + " month: " + CurMonth + " Day: " + CurDay);
//		
//		for(int i = 0; i<7; i++ ){
//			if(date.get(Calendar.DAY_OF_WEEK ) == Calendar.MONDAY){
//				YYYY = String.valueOf(date.get(GregorianCalendar.YEAR));
//				MM = String.valueOf(date.get(GregorianCalendar.MONTH) + 1);  
//				DD = String.valueOf(date.get(GregorianCalendar.DAY_OF_MONTH));
//				Monday = YYYY + "-" + MM + "-" + DD;
//				startDates.add(Monday);
//				//System.out.println("Monday: " +Monday);
//			}if(date.get(Calendar.DAY_OF_WEEK ) == Calendar.WEDNESDAY){
//				YYYY = String.valueOf(date.get(GregorianCalendar.YEAR));
//				MM = String.valueOf(date.get(GregorianCalendar.MONTH) + 1);  
//				DD = String.valueOf(date.get(GregorianCalendar.DAY_OF_MONTH));
//				Wednesday = YYYY + "-" + MM + "-" + DD;
//				startDates.add(Wednesday);
//				//System.out.println("Wednesday: " + Wednesday);
//			}if(date.get(Calendar.DAY_OF_WEEK ) == Calendar.FRIDAY){
//				YYYY = String.valueOf(date.get(GregorianCalendar.YEAR));
//				MM = String.valueOf(date.get(GregorianCalendar.MONTH) + 1);  
//				DD = String.valueOf(date.get(GregorianCalendar.DAY_OF_MONTH));
//				Friday = YYYY + "-" + MM + "-" + DD;
//				startDates.add(Friday);
//				//System.out.println("Friday: " + Friday);
//			}
//			
//			date.roll(Calendar.DAY_OF_MONTH, true);		
//		}
		
//		//System.out.println(startDates);
//		return startDates;
//	}
			
	private String buildLectureQuery(String date, String time, String courseCode, String professorUsername){
		
		StringBuilder sb = new StringBuilder();
		sb.append("INSERT INTO Lecture (lectureDate, lectureTime, Lecture.courseCode, professorUsername) ")
		.append("SELECT '").append(date)
		.append("','").append(time).append("',Course.courseCode, Professor.professorUsername ")
		.append("FROM Course, Professor ")
		.append("WHERE courseCode = '").append(courseCode)
		.append("' AND professorUsername =  '").append(professorUsername)
		.append("';")
		;

		String query = sb.toString();
		//System.out.println(query);
		
		return query;
		
	}
	
	//Professor info
	public ArrayList<String> getCoursesTaughtByProfessor(String professorUsername){
		ArrayList<String> courses = new ArrayList<>();
		
		connect();
			try {
				stmt = conn.createStatement();
	
				String query = "SELECT courseCode FROM CourseProfessor WHERE professorUsername = '" + professorUsername +"';";
				if (stmt.execute(query)) {
					rs = stmt.getResultSet();
				}
				
				while(rs.next()){
					courses.add(rs.getString(1)); 
				}
				
	
			} catch (Exception e) {
				System.out.println("SQLException: " + e.getMessage());
			}
		close();
		//System.out.println(courses);
		return courses;
	}
	
	public void insertProfessor(String professorUsername) {
		connect();
		try {

			StringBuilder sb = new StringBuilder();
			sb.append("INSERT INTO Professor VALUES('")
			.append(professorUsername).append("');");

			String query = sb.toString();
			//System.out.println(query);

			stmt = conn.createStatement();
			stmt.executeUpdate(query);

		} catch (Exception e) {
			System.out.println("SQLException: " + e.getMessage());
		}
		close();
	}

	public boolean professorExists(String professorUsername){
		boolean hasNext = false;
		
		connect();
		try {
			stmt = conn.createStatement();

			String query = "SELECT professorUsername FROM Professor WHERE professorUsername = '" + professorUsername +"';";
			
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
	
	public ArrayList<Integer> getCompletedLecturesForCourseByProfessor(String courseCode, String professorUsername){
		// returns lectureIDs for all lectures given by this professor that have already passed in specified course
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
			
			while(rs.next()){
				lectures.add(rs.getInt(1)); 
			}
			

		} catch (Exception e) {
			System.out.println("SQLException: " + e.getMessage());
		}
		close();
	
	//System.out.println(courses);
	return lectures;
		
		
	}
	
	//Student info
	public void insertStudent(String studentUsername, String studyProgramCode){
		
		connect();
		try {

			StringBuilder sb = new StringBuilder();
			sb.append("INSERT INTO Student VALUES('")
			.append(studentUsername).append("@stud.ntnu.no").append("','")
			.append(studyProgramCode).append("');");

			String query = sb.toString();
			//System.out.println(query);

			stmt = conn.createStatement();
			stmt.executeUpdate(query);

		} catch (Exception e) {
			System.out.println("SQLException: " + e.getMessage());
		}
		close();
	}
	public boolean studentExists(String studentEmail){
		boolean hasNext = false;
		connect();
		try {
			stmt = conn.createStatement();

			String query = "SELECT studentEmail FROM Student WHERE studentEmail = '" + studentEmail +"';";
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
	public String getStudyProgram(String studentEmail){
		String studyProgram = "";
		connect();
		try {
			stmt = conn.createStatement();

			String query = "SELECT studyProgramCode FROM Student WHERE studentEmail = '" + studentEmail +"';";
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
	public ArrayList<String> getStudentCourses(String studentEmail){
		ArrayList<String> studentCourses = new ArrayList<>();
		
		connect();
		try {
			stmt = conn.createStatement();

			String query = "SELECT courseCode FROM CourseStudent WHERE studentEmail = '" + studentEmail +"';";
			if (stmt.execute(query)) {
				rs = stmt.getResultSet();
			}
			
			while(rs.next()){
				studentCourses.add(rs.getString(1)); 
			}
			

		} catch (Exception e) {
			System.out.println("SQLException: " + e.getMessage());
		}
		
		close();
		return studentCourses;
	}
	
	
	
	//CourseProfessor info
	public void insertCourseProfessor (String professorUsername, String courseCode){
		connect();
		try {

			StringBuilder sb = new StringBuilder();
			sb.append("Insert into CourseProfessor (professorUsername, courseCode)")
			.append(" SELECT Professor.professorUsername, Course.courseCode")
			.append(" FROM Course, Professor")
			.append(" WHERE courseCode = '").append(courseCode).append("'")
			.append("AND professorUsername = '").append(professorUsername).append("'")
			;

			String query = sb.toString();
			//System.out.println(query);

			stmt = conn.createStatement();
			stmt.executeUpdate(query);

		} catch (Exception e) {
			System.out.println("SQLException: " + e.getMessage());
		}
		close();
	}
	
	//CourseStudent info
	public void insertCourseStudent (String studentEmail, String courseCode){
		connect();
		try {

			StringBuilder sb = new StringBuilder();
			sb.append("Insert into CourseStudent (studentEmail, courseCode)")
			.append(" SELECT Student.studentEmail, Course.courseCode")
			.append(" FROM Course, Student")
			.append(" WHERE courseCode = '").append(courseCode).append("'")
			.append("AND studentEmail = '").append(studentEmail).append("'")
			;

			String query = sb.toString();
			//System.out.println(query);

			stmt = conn.createStatement();
			stmt.executeUpdate(query);

		} catch (Exception e) {
			System.out.println("SQLException: " + e.getMessage());
		}
		close();
	}
	
	//Evaluation info
	public void insertEvaluation(String studentEmail, int lectureID, String rating, String studentComment){
		connect();
		try {

			StringBuilder sb = new StringBuilder();
			sb.append("Insert into Evaluation (studentEmail, lectureID, rating, studentComment)")
			.append(" SELECT Student.studentEmail, Lecture.lectureID, '")
			.append(rating).append("', '")
			.append(studentComment).append("'")
			.append(" FROM Student, Lecture")
			.append(" WHERE studentEmail = '").append(studentEmail)
			.append("' AND lectureID = ").append(lectureID).append(";")
			;

			String query = sb.toString();
			//System.out.println(query);

			stmt = conn.createStatement();
			stmt.executeUpdate(query);

		} catch (Exception e) {
			System.out.println("SQLException: " + e.getMessage());
		}
		close();
	}

	public ArrayList<String> getEvaluationRatingAndComment(int lectureid, String studentEmail){
		ArrayList<String> evaluation = new ArrayList<>();
		
		connect();
		try {
			stmt = conn.createStatement();

			String query = "SELECT rating, studentComment FROM Evaluation WHERE lectureID = " + lectureid + " AND studentEmail ='" + studentEmail +"' ;";
			//System.out.println(query);
			if (stmt.execute(query)) {
				rs = stmt.getResultSet();
			}
			
			rs.next();
			evaluation.add(rs.getString(1));
			evaluation.add(rs.getString(2));
			

		} catch (Exception e) {
			System.out.println("SQLException: " + e.getMessage());
		}
		close();
		return evaluation;
	}
	
	public boolean evaluationExists(int lectureid, String studentEmail){
		boolean hasNext = false;
		connect();
		try {
			stmt = conn.createStatement();

			String query = "SELECT lectureID FROM Evaluation WHERE lectureID = " + lectureid + " AND studentEmail ='" + studentEmail +"' ;";
			
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
		//test.insertCourse("tdt4145", "Datamodellering og databaser","Trondheim", 4);
		//test.insertCourse("tdt4180", "Menneske-maskin interaksjon","Trondheim", 4);
		//test.insertProfessor("sveinbra");
		//test.getCourseInfo();
		//test.insertStudent("karimj","MTING");
		//test.getProfessorsForCoursse("tdt4140");
		//test.getCoursestaughtByProfessor("pekkaa");
		//test.getLectureHoursForCourse("tdt4140");
		//test.getStartDate();
		//test.insertLecture("2017-02-21", "08:00:00", "tdt4145", "sveinbra");
		//test.insertCourseProfessor("sveinbra", "tdt4145");
		//test.insertStudent("negative","MTING");
		//test.insertEvaluation("negative@stud.ntnu.no", 2 , "Confusing", "wow this is the most boring and stupid lecture ever");
		//test.insertCourseStudent("karimj@stud.ntnu.no ", "tdt4145");
		//test.insertStudent("magnutvi", "MLREAL");
		//test.insertEvaluation("karimj@stud.ntnu.no", 2, "Perfect", "This was a very informative lecture. I like it when you write on the blackboard");
		
		System.out.println(test.getEvaluationRatingAndComment(2, "karimj@stud.ntnu.no"));
	}

}
