package databaseobjects;

import java.util.ArrayList;

public class Main {

	public static void main(String[] args) {
		
		
		Course course = new Course("tdt4140");
		Student stud = new Student("karimj");
		Evaluation eval = new Evaluation(2, stud.getEmail());
		Lecture lec = new Lecture( 2);
		
		ArrayList<Evaluation> evals = lec.getEvaluations();
		
		for(Evaluation evaluation : evals){
			System.out.println(evaluation.getComment());
		}
		
		System.out.println(course.getLastTwoCompletedLectures());
		System.out.println(eval.getStudentEmail());
		System.out.println(stud.getCourseNames());

	}

}
