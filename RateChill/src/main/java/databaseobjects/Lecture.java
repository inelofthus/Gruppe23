package databaseobjects;

import java.util.ArrayList;
import database.DBController;


public class Lecture extends DatabaseUser {
	
	private int lectureID;
	private ArrayList<String> lectureDateAndTime;
	private ArrayList<String> ratingValues;
	private String courseCode;
	private String professor;
	private ArrayList<Evaluation> evaluations;
	private ArrayList<Evaluation> EvaluationsRating1 = new ArrayList<>();
	private ArrayList<Evaluation> EvaluationsRating2 = new ArrayList<>();
	private ArrayList<Evaluation> EvaluationsRating3 = new ArrayList<>();
	private ArrayList<Evaluation> EvaluationsRating4 = new ArrayList<>();
	private ArrayList<Evaluation> EvaluationsRating5 = new ArrayList<>();
	
	/**
	 * Lecture constructor
	 * @param lectureID ID of the lecture
	 */
	public Lecture (int lectureID) {
		setLectureID(lectureID);
		DBC.loadLectureInfo(this);
	}
	
	/**
	 * Constructor used in testing
	 * @param lectureID ID of the lecture
	 * @param newDBC The database controller switched to
	 */
	public Lecture (int lectureID, DBController newDBC) {
		this.lectureID = lectureID;
		switchDBC(newDBC);
		DBC.loadLectureInfo(this);
	}

		
	//Getters

	public int getLectureID() {
		return lectureID;
	}

	public ArrayList<String> getRatingValues() {
		return ratingValues;
	}
	public ArrayList<String> getLectureDateAndTime() {
		return lectureDateAndTime;
	}
	
	public String getLectureDate(){
		return lectureDateAndTime.get(0);
	}
	
	public String getLectureTime(){
		return lectureDateAndTime.get(1);
	}

	public String getCourseCode() {
		return courseCode;
	}

	public String getProfessor() {
		return professor;
	}

	public ArrayList<Evaluation> getEvaluations() {
		return evaluations;
	}

	/**
	 * Returns the number of ratings for a given rating type for the lecture
	 * @param ratingNum The rating type (button)
	 * @return Number of ratings for a given rating type
	 */
	public int getRatingCount(int ratingNum){
		int count = -1;
		
		switch (ratingNum) {
		case 1:
			count = EvaluationsRating1.size();
			break;
		case 2:
			count =  EvaluationsRating2.size();
			break;
		case 3:
			count =  EvaluationsRating3.size();
			break;
		case 4:
			count =  EvaluationsRating4.size();
			break;
		case 5:
			count =  EvaluationsRating5.size();
			break;
		default:
			break;
		}
		if(count == -1){
			throw new IllegalArgumentException("Invalid Rating value");
		}
		return count;
	}
	
	public ArrayList<Evaluation> getEvaluationsRating1() {
		return EvaluationsRating1;
	}
	
	public ArrayList<Evaluation> getEvaluationsRating2() {
		return EvaluationsRating2;
	}
	
	public ArrayList<Evaluation> getEvaluationsRating3() {
		return EvaluationsRating3;
	}
	
	public ArrayList<Evaluation> getEvaluationsRating4() {
		return EvaluationsRating4;
	}
	
	public ArrayList<Evaluation> getEvaluationsRating5() {
		return EvaluationsRating5;
	}
	
	
	//Setters
	
	public void setLectureID(int lectureID) {
		this.lectureID = lectureID;
	}

	public void setRatingValues(ArrayList<String> ratingValues) {
		this.ratingValues = ratingValues;
	}

	public void setLectureDateAndTime(ArrayList<String> dateTime) {
		this.lectureDateAndTime = dateTime;
	}

	public void setCourseCode(String courseCode) {
		this.courseCode = courseCode;
	}

	public void setProfessor(String professor) {
		this.professor = professor;
	}

	public void setEvaluations(ArrayList<Evaluation> evaluations) {
		this.evaluations = evaluations;
	}

	public void setEvaluationsRating1(ArrayList<Evaluation> EvaluationsRating1) {
		this.EvaluationsRating1 = EvaluationsRating1;
	}

	public void setEvaluationsRating2(ArrayList<Evaluation> EvaluationsRating2) {
		this.EvaluationsRating2 = EvaluationsRating2;
	}

	public void setEvaluationsRating3(ArrayList<Evaluation> EvaluationsRating3) {
		this.EvaluationsRating3 = EvaluationsRating3;
	}

	public void setEvaluationsRating4(ArrayList<Evaluation> EvaluationsRating4) {
		this.EvaluationsRating4 = EvaluationsRating4;
	}

	public void setEvaluationsRating5(ArrayList<Evaluation> EvaluationsRating5) {
		this.EvaluationsRating5 = EvaluationsRating5;
	}
	
}