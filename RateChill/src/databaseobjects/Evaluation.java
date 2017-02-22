package databaseobjects;

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
			comment = DBC.getEvaluationRatingAndComment(lectureid, studentEmail).get(0);
			
		} catch (Exception e) {
			// TODO: handle exception
			if (!existsInDB()) {
				throw new IllegalAccessError("User does not exist in database");
			}
			System.out.println(e.getMessage());
		}
				
	}
	
}
