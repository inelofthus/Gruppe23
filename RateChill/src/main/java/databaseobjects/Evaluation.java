package databaseobjects;

import database.DBController;

/**
 * Class defining the Evaluation object, containing
 * methods and member variables relating to an evaluation.
 * @author Ine L. Arnesen, Kari M. Johannessen, Magnus Tvilde, Nicolai C. Michelet
 */
public class Evaluation extends DatabaseUser {

	private String rating;
	private String comment;
	private int lectureID;
	private String studentUsername;
	
	/**
	 * Evaluation constructor
	 * @param lectureID The ID of the lecture that is evaluated
	 * @param studentUsername The username of the student evaluating the lecture
	 */
	public Evaluation(int lectureID,String studentUsername) {
		this.lectureID = lectureID;
		this.studentUsername = studentUsername;
		DBC.loadEvaluationInfo(this);	
	}
		
	/**
	 * Evaluation constructor used in {@link database.DBController#loadLectureInfo(Lecture)}
	 * @param rating The rating chosen
	 * @param comment The student's comment
	 * @param lectureID The ID of the lecture evaluated
	 * @param studentUsername The username of the student giving the evaluation
	 */
	public Evaluation(String rating, String comment, int lectureID, String studentUsername) {
		this.rating = rating;
		this.comment = comment;
		this.lectureID = lectureID;
		this.studentUsername = studentUsername;
	}
	
	//Constructor3 used in testing
	/**
	 * Constructor used in testing
	 * @param lectureID The ID of the lecture evaluated
	 * @param studentUsername The username of the student giving the evaluation
	 * @param newDBC The database controller switched to
	 */
	public Evaluation(int lectureID,String studentUsername, DBController newDBC) {
		this.lectureID = lectureID;
		this.studentUsername = studentUsername;
		switchDBC(newDBC);
		DBC.loadEvaluationInfo(this);	
	}
	
	
	//Getters
	
	/**
	 * @return The rating chosen in the evaluation
	 */
	public String getRating() {
		return rating;
	}

	/**
	 * @return The comment in the evaluation
	 */
	public String getComment() {
		return comment;
	}

	/**
	 * @return the ID of the lecture evaluated
	 */
	public int getLectureID() {
		return lectureID;
	}

	/**
	 * @return The username of the student evaluating the lecture
	 */
	public String getstudentUsername() {
		return studentUsername;
	}	
	
	
	// Setters
	
	/**
	 * @param rating The chosen rating in the evaluation
	 */
	public void setRating(String rating) {
		this.rating = rating;
	}

	/**
	 * @param comment The comment in the evaluation
	 */
	public void setComment(String comment) {
		this.comment = comment;
	}

}
