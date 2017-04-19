package gui;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import api.*;
import databaseobjects.Course;
import databaseobjects.Student;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.scene.text.Text;

import gui.MainController;

public class CourseStudController extends CommonMethods implements Initializable {

	
	@FXML
	public Button fag1;
	public Button fag2;
	public Button fag3;
	public Button fag4;
	public Text errorNumber;
	public Button logout;
	public Button chooseCourses;
	public Button exit;
	public Text errorText;
	public Rectangle errorBar;
	
	
	//creates a list of buttons to iterate over in initializer
	ArrayList<Button> buttons = new ArrayList<Button>();
	
	
	public String loadCourseCode(int x) {
		return MainController.getInstance().getStudents().getCourseIDs().get(x);
	}
	
	public void setLectures (Course course) {
		MainController.getInstance().setlastTwoLecturesStudent(course.getLastTwoCompletedLectures());
	}
	
	public void loadCourse(Course course) {
		MainController.getInstance().setCourse(course);
	}
	
	public String loadCourseName(int x){
		Student stud = MainController.getInstance().getStudents();
		return stud.getCourseNameForCourse(stud.getCourseIDs().get(x));
	}
	
	public void setSubjectButtonText(int x, Button fagButton){
		String courseCode = loadCourseCode(x);
		String courseCodeName = courseCode + "\n" + loadCourseName(x);
		fagButton.setText(courseCodeName);
	}
	
	
	
	public void userButtons(ActionEvent event, Stage stage) throws IOException{
		if (event.getSource() == logout) {
			loadNextScene(logout, stage, "Login.fxml");
		}if (event.getSource() == chooseCourses) {
			loadNextScene(chooseCourses, stage, "SelectCourseStud.fxml");
		}
	}
	
	@FXML
	private void handleButtonAction(ActionEvent event) throws IOException{
		Stage stage = null; 
		
		//the number of courses a student has
	    int numberOfCourses = MainController.getInstance().getStudents().getCourseIDs().size();
	    
	    userButtons(event, stage);
	    
	    if(numberOfCourses == 0) {
	    	return;
	    }
	    
	    else if(event.getSource()==fag1 && numberOfCourses>0){
	    	
	    	Course course = new Course(loadCourseCode(0));
	        loadCourse(course);
	    	
	    	setLectures(course);
	        loadNextScene(fag1, stage, "LectureStud.fxml");
	        
	    }
	    else if (event.getSource()==fag2 && numberOfCourses>1){
	    	Course course = new Course(loadCourseCode(1));
	    	loadCourse(course);
	    	
	    	setLectures(course);
	    	loadNextScene(fag2, stage, "LectureStud.fxml");
	    }
	    
	    else if (event.getSource()==fag3 && numberOfCourses>2){
	    	Course course = new Course(loadCourseCode(2));
	    	loadCourse(course);

	    	setLectures(course);
	    	loadNextScene(fag3, stage, "LectureStud.fxml");
	    	
		}
	    else if (event.getSource()==fag4 && numberOfCourses>3){
	    	Course course = new Course(loadCourseCode(3));
	    	loadCourse(course);
	    	
	    	setLectures(course);
	    	loadNextScene(fag4, stage, "LectureStud.fxml");
	    	
		}
	}
	
	public void handleKeyAction(KeyEvent ke) throws IOException{
		Stage stage = null;
		if(ke.getCode().equals(KeyCode.ENTER)){
			if (fag1.isFocused()){
				Course course = new Course(loadCourseCode(0));
		        loadCourse(course);
		    	
		    	setLectures(course);
		        loadNextScene(fag1, stage, "LectureStud.fxml");
			}
			else if (fag2.isFocused()){
				Course course = new Course(loadCourseCode(1));
		        loadCourse(course);
		    	
		    	setLectures(course);
		        loadNextScene(fag2, stage, "LectureStud.fxml");
			}
			else if (fag3.isFocused()){
				Course course = new Course(loadCourseCode(2));
		        loadCourse(course);
		    	
		    	setLectures(course);
		        loadNextScene(fag3, stage, "LectureStud.fxml");
			}else if (fag4.isFocused()){
				Course course = new Course(loadCourseCode(3));
		        loadCourse(course);
		    	
		    	setLectures(course);
		        loadNextScene(fag4, stage, "LectureStud.fxml");
			}else if (chooseCourses.isFocused()){
				loadNextScene(chooseCourses, stage, "SelectCourseStud.fxml");
			}
				
		}
	}
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		//System.out.println(mainController.getInstance().getStudents().getCourseIDs().get(0));
		int numberOfCourses = MainController.getInstance().getStudents().getCourseIDs().size();
		if(numberOfCourses == 0) {
	    	errorNumber.setText("You don't have any courses yet!");
		}
		buttons.add(fag1);
		buttons.add(fag2);
		buttons.add(fag3);
		buttons.add(fag4);
		
		for (int x=0; x<numberOfCourses; x++) {
			setSubjectButtonText(x, buttons.get(x));
			buttons.get(x).setVisible(true);
		}
		errorBar.setVisible(true);
		errorBar.setFill(Color.DARKSEAGREEN);
		errorText.setText("You are logged in as "+ MainController.getInstance().getStudents().getUsername());
	}

}
