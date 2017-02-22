package databaseobjects;

import java.util.NoSuchElementException;

import database.DBController;

public class Evaluation extends DatabaseUser {

	private String rating;
	private String comment;
	private int lectureid;
	private String studentEmail;
	
	public Evaluation() {
		// TODO Auto-generated constructor stub
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

	private boolean existsInDB() {
		return DBC.evaluationExists(lectureid, studentEmail);
	}
	
}
