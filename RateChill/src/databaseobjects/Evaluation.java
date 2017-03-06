package databaseobjects;

public class Evaluation extends DatabaseUser {

	private String rating;
	private String comment;
	private int lectureid;
	private String studentEmail;
	
	//Constructor
	public Evaluation(int lectureid,String studentEmail) {
		this.lectureid = lectureid;
		this.studentEmail = studentEmail;
		DBC.loadEvaluationInfo(this);
		
	}
		
	//Constructor2 used in in LoadlectureInfo in DBC
	public Evaluation(String rating, String comment, int lectureid, String studentEmail) {
		this.rating = rating;
		this.comment = comment;
		this.lectureid = lectureid;
		this.studentEmail = studentEmail;
	}
	
	public boolean existsInDB() {
		return DBC.evaluationExists(lectureid, studentEmail);
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

	public String getStudentEmail() {
		return studentEmail;
	}	
	//setters
	public void setRating(String rating) {
		this.rating = rating;
	}


	public void setComment(String comment) {
		this.comment = comment;
	}

	
	//////////////////END OF USEFUL CODE /////////////////////
	//Old load function
	
	/*public void loadInfo(){
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
			
}*/
}
