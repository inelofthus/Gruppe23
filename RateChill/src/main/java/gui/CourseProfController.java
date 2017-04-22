package gui;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import databaseobjects.Course;
import databaseobjects.Professor;
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
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class CourseProfController extends CommonMethods implements Initializable {

	
	@FXML
	public Button fag1;
	public Button fag2;
	public Button fag3;
	public Button fag4;
	public Button logout;
	public Text errorNumber;
	public Text errorText;
	public Rectangle errorBar;
	
	private Color myRed = new Color(0.937, 0.290, 0.290, 1);
	
	//A list of buttons to iterate over in initializer
	private ArrayList<Button> buttons = new ArrayList<Button>();
	
	private MainController mc = MainController.getInstance();
	private Professor prof =  mc.getProfessor();
	
	private String loadCourseName(int lecID){
		return prof.getCourseNameForCourse(prof.getCourseIDs().get(lecID));
	}
	
	private void setSubjectButtonText(int lecNumber, Button fagButton){
		String courseCode = prof.getCourseIDs().get(lecNumber);
		String courseCodeName = courseCode + "\n" + loadCourseName(lecNumber);
		fagButton.setText(courseCodeName);
	}
	
	/**
	 * decides what happens when the logout button is pressed
	 */
	public void userButtons(ActionEvent event) throws IOException{
		if (event.getSource() == logout) {
			loadNextScene(logout, "Login.fxml");
		}
	}
	
	
	@FXML
	private void handleButtonAction(ActionEvent event) throws IOException{
		//the number of courses a professor has
	    int numberOfCourses = MainController.getInstance().getProfessor().getCourseIDs().size();
	    userButtons(event);
	    
	    if(numberOfCourses == 0) {
	    	return;
	    }
	    
	    else if(event.getSource()==fag1 && numberOfCourses>0){
	    	
	    	Course course = new Course(prof.getCourseIDs().get(0));
	    	mc.setCourse(course);
	        
	        if (MainController.getInstance().getCourse().getLectureIDs().size() == 0) {
	    		loadNextScene(fag1, "AddLectures.fxml");
	    		return;
	    	}

	        loadNextScene(fag1, "LectureProf.fxml");
	    }
	    
	    else if (event.getSource()==fag2 && numberOfCourses>1){
	    	Course course = new Course(prof.getCourseIDs().get(1));
	    	mc.setCourse(course);

	    	if (MainController.getInstance().getCourse().getLectureIDs().size() == 0) {
	    		loadNextScene(fag2, "AddLectures.fxml");
	    		return;
	    	}

	    	loadNextScene(fag2,"LectureProf.fxml");
	    }
	    
	    else if (event.getSource()==fag3 && numberOfCourses>2){
	    	Course course = new Course(prof.getCourseIDs().get(2));
	    	mc.setCourse(course);
	    	
	    	if (MainController.getInstance().getCourse().getLectureIDs().size() == 0) {
	    		loadNextScene(fag3, "AddLectures.fxml");
	    		return;
	    	}

	    	loadNextScene(fag3, "LectureProf.fxml");
	    	
		}
	    
	    else if (event.getSource()==fag4 && numberOfCourses>3){
	    	Course course = new Course(prof.getCourseIDs().get(3));
	    	mc.setCourse(course);
	    	
	    
	    	if (MainController.getInstance().getCourse().getLectureIDs().size() == 0) {
	    		loadNextScene(fag4, "AddLectures.fxml");
	    		return;
	    	}
	    	
	    	loadNextScene(fag4, "LectureProf.fxml");
		}
	}
	
	public void handleKeyAction(KeyEvent ke) throws IOException{
		if(ke.getCode().equals(KeyCode.ENTER)){
			if (fag1.isFocused()){
				Course course = new Course(prof.getCourseIDs().get(0));
		    	mc.setCourse(course);
		    	loadNextScene(fag4,  "LectureProf.fxml");
			}
			else if (fag2.isFocused()){
				Course course = new Course(prof.getCourseIDs().get(1));
				mc.setCourse(course);
		    	loadNextScene(fag2,  "LectureProf.fxml");
			}
			else if (fag3.isFocused()){
				Course course = new Course(prof.getCourseIDs().get(2));
				mc.setCourse(course);
		    	loadNextScene(fag3,  "LectureProf.fxml");
			}else if (fag4.isFocused()){
				Course course = new Course(prof.getCourseIDs().get(3));
				mc.setCourse(course);
		    	loadNextScene(fag4,  "LectureProf.fxml");		
			}
		}
	}
	
	/**
	 * Initialises the CourseProf.fxml gui 
	 */
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		int numberOfCourses = prof.getCourseIDs().size();
		buttons.add(fag1);
		buttons.add(fag2);
		buttons.add(fag3);
		buttons.add(fag4);
		
		errorBar.setVisible(true);
		errorBar.setFill(Color.DARKSEAGREEN);
		errorText.setText("You are logged in as "+ prof.getUsername());
		
		if(numberOfCourses == 0) {
			errorNumber.setText("You don't have any courses!");
		}
		for (int x=0; x<numberOfCourses; x++) {
			if (numberOfCourses > 4){
				numberOfCourses = 4;
				errorText.setText("RateChill only supports 4 courses at the moment. More will be supported soon");
				errorBar.setFill(myRed);
			}
			setSubjectButtonText(x, buttons.get(x));
			buttons.get(x).setVisible(true);
		}
		
	}

}
