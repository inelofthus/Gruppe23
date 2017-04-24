package gui;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import databaseobjects.Course;
import databaseobjects.Student;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

import gui.MainController;

/**
 * CourseStudController --- CourseStudController is a class that controls all
 * interaction user interaction with the CourseStud.fxml GUI
 * 
 * @author Group 23: Ine Lofthus Arnesen, Kari Meling Johannessen, Nicolai
 *         Cappelen Michelet, Magnus Tvilde
 */
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
	
	//A list of buttons to iterate over in initializer
	private ArrayList<Button> buttons = new ArrayList<Button>();	
	private MainController mc = MainController.getInstance();
	private Student stud = mc.getStudent();
	
	private void setLectures (Course course) {
		mc.setLastTwoLecturesStudent(course.getLastTwoCompletedLectures());
	}
	
	private String loadCourseName(int courseNum){
		return stud.getCourseNameForCourse(stud.getCourseIDs().get(courseNum));
	}
	
	private void setSubjectButtonText(int courseNum, Button fagButton){
		String courseCode = stud.getCourseIDs().get(courseNum);
		String courseCodeName = courseCode + "\n" + loadCourseName(courseNum);
		fagButton.setText(courseCodeName);
	}
	
	/**
	 * takes user to the correct page if user buttons (logout or chooseCourses) are pressed
	 */
	public void userButtons(ActionEvent event) throws IOException{
		if (event.getSource() == logout) {
			loadNextScene(logout,  "Login.fxml");
		}if (event.getSource() == chooseCourses) {
			loadNextScene(chooseCourses,  "SelectCourseStud.fxml");
		}
	}
	
	@FXML
	private void handleButtonAction(ActionEvent event) throws IOException{		
		//the number of courses a student has
	    int numberOfCourses = stud.getCourseIDs().size();	    
	    userButtons(event);	    
	    if(numberOfCourses == 0) {
	    	return;
	    }	    
	    else if(event.getSource()==fag1 && numberOfCourses>0){
	    	Course course = new Course(stud.getCourseIDs().get(0));
	        mc.setCourse(course);
	    	setLectures(course);
	        loadNextScene(fag1,  "LectureStud.fxml");       
	    }
	    else if (event.getSource()==fag2 && numberOfCourses>1){
	    	Course course = new Course(stud.getCourseIDs().get(1));
	    	mc.setCourse(course);	    	
	    	setLectures(course);
	    	loadNextScene(fag2,  "LectureStud.fxml");
	    }	    
	    else if (event.getSource()==fag3 && numberOfCourses>2){
	    	Course course = new Course(stud.getCourseIDs().get(2));
	    	mc.setCourse(course);
	    	setLectures(course);
	    	loadNextScene(fag3,  "LectureStud.fxml");	    	
		}
	    else if (event.getSource()==fag4 && numberOfCourses>3){
	    	Course course = new Course(stud.getCourseIDs().get(3));
	    	mc.setCourse(course);	    	
	    	setLectures(course);
	    	loadNextScene(fag4,  "LectureStud.fxml");	    	
		}
	}
	
	@FXML
	private void handleKeyAction(KeyEvent ke) throws IOException{
		if(ke.getCode().equals(KeyCode.ENTER)){
			if (fag1.isFocused()){
				Course course = new Course(stud.getCourseIDs().get(0));
		        mc.setCourse(course);		    	
		    	setLectures(course);
		        loadNextScene(fag1,  "LectureStud.fxml");
			}
			else if (fag2.isFocused()){
				Course course = new Course(stud.getCourseIDs().get(1));
		        mc.setCourse(course);		    	
		    	setLectures(course);
		        loadNextScene(fag2,  "LectureStud.fxml");
			}
			else if (fag3.isFocused()){
				Course course = new Course(stud.getCourseIDs().get(2));
		        mc.setCourse(course);		    	
		    	setLectures(course);
		        loadNextScene(fag3,  "LectureStud.fxml");
			}else if (fag4.isFocused()){
				Course course = new Course(stud.getCourseIDs().get(3));
		        mc.setCourse(course);		    	
		    	setLectures(course);
		        loadNextScene(fag4,  "LectureStud.fxml");
			}else if (chooseCourses.isFocused()){
				loadNextScene(chooseCourses,  "SelectCourseStud.fxml");
			}				
		}
	}
	
	/**
	 * Initialises the CourseStud.fxml GUI
	 */
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		int numberOfCourses = stud.getCourseIDs().size();
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
		errorText.setText("You are logged in as "+ stud.getUsername());
		
		String coursesUpdated = mc.getCoursesUpdated();
		if (!coursesUpdated.isEmpty()){
			if (coursesUpdated.equals("true")){
				errorText.setText("Your courses were updated");
			}
			else if (coursesUpdated.equals("false")){
				errorBar.setFill(Color.LIGHTGOLDENRODYELLOW);
				errorText.setText("No changes were made to your courses");
			}
			mc.setCoursesUpdated("");
		}
	}

}
