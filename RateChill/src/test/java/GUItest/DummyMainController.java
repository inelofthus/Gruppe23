package GUItest;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Stack;

import databaseobjects.Course;
import databaseobjects.Lecture;
import databaseobjects.Professor;
import databaseobjects.Student;
import gui.MainController;

public class DummyMainController extends MainController {

	@Override
	public Stack<String> getStack() {
		// TODO Auto-generated method stub
		return super.getStack();
	}

	@Override
	public Course getCourse() {
		// TODO Auto-generated method stub
		return super.getCourse();
	}

	@Override
	public void setCourse(Course course) {
		// TODO Auto-generated method stub
		super.setCourse(course);
	}

	@Override
	public Student getStudents() {
		// TODO Auto-generated method stub
		return super.getStudents();
	}

	@Override
	public void setStudent(Student student) {
		// TODO Auto-generated method stub
		super.setStudent(student);
	}

	@Override
	public LinkedHashMap<Integer, ArrayList<String>> getLastTwoLecturesStudent() {
		// TODO Auto-generated method stub
		return super.getLastTwoLecturesStudent();
	}

	@Override
	public void setlastTwoLecturesStudent(LinkedHashMap<Integer, ArrayList<String>> lastTwoLecturesStudent) {
		// TODO Auto-generated method stub
		super.setlastTwoLecturesStudent(lastTwoLecturesStudent);
	}

	@Override
	public Integer getChosenStudentLecture() {
		// TODO Auto-generated method stub
		return super.getChosenStudentLecture();
	}

	@Override
	public void setChosenStudentLecture(Integer chosenStudentLecture) {
		// TODO Auto-generated method stub
		super.setChosenStudentLecture(chosenStudentLecture);
	}

	@Override
	public Professor getProfessor() {
		// TODO Auto-generated method stub
		return super.getProfessor();
	}

	@Override
	public void setProfessor(Professor professor) {
		// TODO Auto-generated method stub
		super.setProfessor(professor);
	}

	@Override
	public LinkedHashMap<Integer, ArrayList<String>> getLastTwoLecturesProfessor() {
		// TODO Auto-generated method stub
		return super.getLastTwoLecturesProfessor();
	}

	@Override
	public void setlastTwoLecturesProfessor(LinkedHashMap<Integer, ArrayList<String>> lastTwoLecturesProfessor) {
		// TODO Auto-generated method stub
		super.setlastTwoLecturesProfessor(lastTwoLecturesProfessor);
	}

	@Override
	public Integer getChosenProfessorLecture() {
		// TODO Auto-generated method stub
		return super.getChosenProfessorLecture();
	}

	@Override
	public void setChosenProfessorLecture(Integer chosenProfessorLecture) {
		// TODO Auto-generated method stub
		super.setChosenProfessorLecture(chosenProfessorLecture);
	}

	@Override
	public Lecture getLecture() {
		// TODO Auto-generated method stub
		return super.getLecture();
	}

	@Override
	public void setLecture(Lecture lecture) {
		// TODO Auto-generated method stub
		super.setLecture(lecture);
	}

}
