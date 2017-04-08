package databaseobjects;

import database.DBController;

public class Evaluation extends DatabaseUser {

	private String rating;
	private String comment;
	private int lectureid;
	private String studentUsername;
	
	//Constructor
	public Evaluation(int lectureid,String studentUsername) {
		this.lectureid = lectureid;
		this.studentUsername = studentUsername;
		DBC.loadEvaluationInfo(this);
		
	}
		
	//Constructor2 used in in LoadlectureInfo in DBC
	public Evaluation(String rating, String comment, int lectureid, String studentUsername) {
		this.rating = rating;
		this.comment = comment;
		this.lectureid = lectureid;
		this.studentUsername = studentUsername;
	}
	
	//Constructor3 used in testing
	public Evaluation(int lectureid,String studentUsername, DBController newDBC) {
		this.lectureid = lectureid;
		this.studentUsername = studentUsername;
		switchDBC(newDBC);
		DBC.loadEvaluationInfo(this);
		
	}
	
	public boolean existsInDB() {
		return DBC.evaluationExists(lectureid, studentUsername);
	}
	
	//getters
	
	public String getRating() {
		return rating;
	}

	public String getComment() {
		return comment;
	}

	public int getLectureid() {
		return lectureid;
	}

	public String getstudentUsername() {
		return studentUsername;
	}	
	//setters
	public void setRating(String rating) {
		this.rating = rating;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

}
