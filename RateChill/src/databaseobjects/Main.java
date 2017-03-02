package databaseobjects;

public class Main {

	public static void main(String[] args) {
				
		Course course = new Course("tdt4140");
		Student stud = new Student("karimj");
		//Evaluation eval = new Evaluation(2, stud.getEmail());
		//Lecture lec = new Lecture(2);
			
		
		System.out.println(stud.getCourseIDs());
		System.out.println(stud.getCourseNameForCourse(stud.getCourseIDs().get(0)));
		System.out.println(stud.getStudyProgram());

	}

}
