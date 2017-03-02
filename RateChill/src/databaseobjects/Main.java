package databaseobjects;

import java.util.ArrayList;

import database.DBController;

public class Main {

	public static void main(String[] args) {
				
		Course course = new Course("tdt4140");
		Student stud = new Student("karimj");
		//Evaluation eval = new Evaluation(2, stud.getEmail());
		//Lecture lec = new Lecture( 2);
		
		System.out.println(course.getLastTwoCompletedLectures().keySet());
		stud.overWriteEvaluation(4, "Perfect", "hello I overwrite again");
		System.out.println(stud.hasEvaluatedLecture(5));

	}

}
