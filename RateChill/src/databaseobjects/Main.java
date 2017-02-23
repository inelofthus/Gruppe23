package databaseobjects;

import java.util.ArrayList;

import database.DBController;

public class Main {

	public static void main(String[] args) {
		
		DBController DBC = new DBController();
		DBC.connect();
		Course course = new Course(DBC, "tdt4140");
		Student stud = new Student(DBC, "karimj");
		Evaluation eval = new Evaluation(DBC, 2, stud.getEmail());
		Lecture lec = new Lecture(DBC, 2);
		
		ArrayList<Evaluation> evals = lec.getEvaluations();
		
		for(Evaluation evaluation : evals){
			System.out.println(evaluation.getComment());
		}
		
		System.out.println(course.getLastTwoCompletedLectures());
		System.out.println(eval.getStudentEmail());
		System.out.println(stud.getCourseNames());

	}

}
