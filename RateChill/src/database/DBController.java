package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

public class DBController {
	
	Connection conn = null;

	public void connect() {
		try {
			conn = DriverManager.getConnection(
					"jdbc:mysql://mysql.stud.ntnu.no/segroup23_db?user=segroup23_user&password=pekkabot");
			System.out.println("connection succesfull :) ");

		} catch (SQLException ex) {
			System.out.println("SQLeXCEPTION: " + ex.getMessage());

		}
	}

	Statement stmt = null;
	ResultSet rs = null;

	//Course info
	
	public void getCourseInfo() {
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
	}
	
	public List<String> getProfessorsForCourse(String courseCode){
		
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
		
		//System.out.println(professor);
		return professor;
	}
	
	public void insertCourse(String courseCode, String courseName, String courseLocation, int lectureHours) {
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

	}
	
	//Lecture info
	public int getLectureHoursForCourse(String courseCode){
		
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
		
		//System.out.println(hours);
		return hours;
		
	}
	
	public void insertLecture(String date, String time, String courseCode, String professorUsername){
		// Date format: "YYYY-MM-DD"
		// Time format: "HH:MM:SS"
		
		try {

			String query = buildLectureQuery(date, time, courseCode, professorUsername);
			//System.out.println(query);

			stmt = conn.createStatement();
			stmt.executeUpdate(query);

		} catch (Exception e) {
			System.out.println("SQLException: " + e.getMessage());
		}
		
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
	
	private List<String> getStartDate(){
		//Return SQL friendly list of date Strings of next Monday, Wednesday and Friday  YYYY-MM-DD
		ArrayList<String> startDates = new ArrayList<>();
		String YYYY, MM, DD, Monday, Wednesday, Friday;
		
		GregorianCalendar date =new GregorianCalendar();            
		//String CurMonth = String.valueOf(date.get(GregorianCalendar.MONTH) + 1);            
		//String CurDay = String.valueOf(date.get(GregorianCalendar.DAY_OF_MONTH));
		//String CurYear = String.valueOf(date.get(GregorianCalendar.YEAR));
		
		//System.out.println("year: " + CurYear + " month: " + CurMonth + " Day: " + CurDay);
		
		for(int i = 0; i<7; i++ ){
			if(date.get(Calendar.DAY_OF_WEEK ) == Calendar.MONDAY){
				YYYY = String.valueOf(date.get(GregorianCalendar.YEAR));
				MM = String.valueOf(date.get(GregorianCalendar.MONTH) + 1);  
				DD = String.valueOf(date.get(GregorianCalendar.DAY_OF_MONTH));
				Monday = YYYY + "-" + MM + "-" + DD;
				startDates.add(Monday);
				//System.out.println("Monday: " +Monday);
			}if(date.get(Calendar.DAY_OF_WEEK ) == Calendar.WEDNESDAY){
				YYYY = String.valueOf(date.get(GregorianCalendar.YEAR));
				MM = String.valueOf(date.get(GregorianCalendar.MONTH) + 1);  
				DD = String.valueOf(date.get(GregorianCalendar.DAY_OF_MONTH));
				Wednesday = YYYY + "-" + MM + "-" + DD;
				startDates.add(Wednesday);
				//System.out.println("Wednesday: " + Wednesday);
			}if(date.get(Calendar.DAY_OF_WEEK ) == Calendar.FRIDAY){
				YYYY = String.valueOf(date.get(GregorianCalendar.YEAR));
				MM = String.valueOf(date.get(GregorianCalendar.MONTH) + 1);  
				DD = String.valueOf(date.get(GregorianCalendar.DAY_OF_MONTH));
				Friday = YYYY + "-" + MM + "-" + DD;
				startDates.add(Friday);
				//System.out.println("Friday: " + Friday);
			}
			
			date.roll(Calendar.DAY_OF_MONTH, true);		
		}
		
		//System.out.println(startDates);
		return startDates;
	}
			
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
	public List<String> getCoursestaughtByProfessor(String professorUsername){
		ArrayList<String> courses = new ArrayList<>();
		
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
		
		//System.out.println(courses);
		return courses;
	}
	
	public void insertProfessor(String professorUsername) {
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
	}

	//Student info
	public void insertStudent(String studentUsername, String studyProgramCode){
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
	}
	public boolean studentExists(String studentEmail){
		boolean hasNext = false;
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
		return hasNext;
	}
	public String getStudyProgram(String studentEmail){
		String studyProgram = "";
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
		return studyProgram;
	}
	public ArrayList<String> getStudentCourses(String studentEmail){
		ArrayList<String> studentCourses = new ArrayList<>();
		
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
		
		return studentCourses;
	}
	
	
	//CourseProfessor info
	public void insertCourseProfessor (String professorUsername, String courseCode){
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
	}
	
	//CourseStudent info
	public void insertCourseStudent (String studentEmail, String courseCode){
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
	}
	
	//Evaluation info
	public void insertEvaluation(String studentEmail, int lectureID, String rating, String studentComment){
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
		//test.getProfessorsForCourse("tdt4140");
		//test.getCoursestaughtByProfessor("pekkaa");
		//test.getLectureHoursForCourse("tdt4140");
		//test.getStartDate();
		//test.insertLecture("2017-02-21", "08:00:00", "tdt4145", "sveinbra");
		//test.insertCourseProfessor("sveinbra", "tdt4145");
		//test.insertStudent("negative","MTING");
		//test.insertEvaluation("negative@stud.ntnu.no", 2 , "Confusing", "wow this is the most boring and stupid lecture ever");
		//test.insertCourseStudent("karimj@stud.ntnu.no ", "tdt4145");
		//test.insertStudent("magnutvi", "MLREAL");
		
		
		System.out.println(test.getStudentCourses("karimj@stud.ntnu.no"));
	}

}
