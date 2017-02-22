package databaseobjects;

import java.util.NoSuchElementException;

public class Evaluation extends DatabaseUser {

	private String rating;
	private String comment;
	private int lectureid;
	private String studentEmail;
	
	public Evaluation(int lectureid,String studentEmail) {
		this.lectureid = lectureid;
		this.studentEmail = studentEmail;
		loadInfo();
	}
	
	public Evaluation(String rating, String comment, int lectureid, String studentEmail) {
		this.rating = rating;
		this.comment = comment;
		this.lectureid = lectureid;
		this.studentEmail = studentEmail;
	}
	
	public void loadInfo(){
		try {
			rating = DBC.getEvaluationRatingAndComment(lectureid, studentEmail).get(0);
			comment = DBC.getEvaluationRatingAndComment(lectureid, studentEmail).get(1);
			
		} catch (Exception e) {
			// TODO: handle exception
			if (!existsInDB()) {
				throw new NoSuchElementException("Evaluation does not exist in database");
			}
			System.out.println(e.getMessage());
		}
				
	}

	public boolean existsInDB() {
		return DBC.evaluationExists(lectureid, studentEmail);
	}
	
	public String getRating() {
		return rating;
	}

	public String getComment() {
		return comment;
	}

	public int getLectureid() {
		return lectureid;
	}

	public String getStudentEmail() {
		return studentEmail;
	}
	
	public static void main(String[] args) {
		//Evaluation e = new Evaluation(2,"negative@stud.ntnu.no");
		//System.out.println(e.existsInDB());
		//System.out.println(e.getRating());
		//System.out.println(e.getComment());
	}
	
}
