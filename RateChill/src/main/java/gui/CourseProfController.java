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
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class CourseProfController implements Initializable {

	
	@FXML
	public Button fag1;
	public Button fag2;
	public Button fag3;
	public Button fag4;
	public Text errorNumber;
	
	public Button logout;
	
	//creates a list of buttons to iterate over in initializer
	ArrayList<Button> buttons = new ArrayList<Button>();
	
	
	public String loadCourseCode(int x) {
		return MainController.getInstance().getProfessor().getCourseIDs().get(x);
	}
	
	public void loadCourse(Course course) {
		MainController.getInstance().setCourse(course);
	}
	
	public String loadCourseName(int x){
		Professor prof = MainController.getInstance().getProfessor();
		return prof.getCourseNameForCourse(prof.getCourseIDs().get(x));
	}
	
	public void setSubjectButtonText(int x, Button fagButton){
		String courseCode = loadCourseCode(x);
		String courseCodeName = courseCode + "\n" + loadCourseName(x);
		fagButton.setText(courseCodeName);
		System.out.println(courseCodeName);
	}
	
	//method to go to next GUI
	public void loadNextScene(Button button, Stage stage, String string) throws IOException{
		stage=(Stage) button.getScene().getWindow();
		Parent root;
		root = FXMLLoader.load(getClass().getResource(string));
		
		//create a new scene with root and set the stage
		Scene scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
	}
	
	
	public void userButtons(ActionEvent event, Stage stage) throws IOException{
		if (event.getSource() == logout) {
			loadNextScene(logout, stage, "Login.fxml");
		}
	}
	
	
	@FXML
	private void handleButtonAction(ActionEvent event) throws IOException{
		Stage stage = null;
	    
		//the number of courses a professor has
	    int numberOfCourses = MainController.getInstance().getProfessor().getCourseIDs().size();
	    userButtons(event, stage);
	    
	    if(numberOfCourses == 0) {
	    	return;
	    }
	    
	    else if(event.getSource()==fag1 && numberOfCourses>0){
	    	
	    	Course course = new Course(loadCourseCode(0));
	    	loadCourse(course);
	        
	        if (MainController.getInstance().getCourse().getLectureIDs().size() == 0) {
	    		loadNextScene(fag1, stage, "AddLectures.fxml");
	    		return;
	    	}

	        loadNextScene(fag1, stage, "LectureProf.fxml");
	    }
	    
	    else if (event.getSource()==fag2 && numberOfCourses>1){
	    	Course course = new Course(loadCourseCode(1));
	    	loadCourse(course);

	    	if (MainController.getInstance().getCourse().getLectureIDs().size() == 0) {
	    		loadNextScene(fag2, stage, "AddLectures.fxml");
	    		return;
	    	}

	    	loadNextScene(fag2, stage, "LectureProf.fxml");
	    }
	    
	    else if (event.getSource()==fag3 && numberOfCourses>2){
	    	Course course = new Course(loadCourseCode(2));
	    	loadCourse(course);
	    	
	    	if (MainController.getInstance().getCourse().getLectureIDs().size() == 0) {
	    		loadNextScene(fag3, stage, "AddLectures.fxml");
	    		return;
	    	}

	    	loadNextScene(fag3, stage, "LectureProf.fxml");
	    	
		}
	    
	    else if (event.getSource()==fag4 && numberOfCourses>3){
	    	Course course = new Course(loadCourseCode(3));
	    	loadCourse(course);
	    	
	    
	    	if (MainController.getInstance().getCourse().getLectureIDs().size() == 0) {
	    		loadNextScene(fag4, stage, "AddLectures.fxml");
	    		return;
	    	}
	    	
	    	loadNextScene(fag4, stage, "LectureProf.fxml");
		}
	}
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		int numberOfCourses = MainController.getInstance().getProfessor().getCourseIDs().size();
		buttons.add(fag1);
		buttons.add(fag2);
		buttons.add(fag3);
		buttons.add(fag4);
		if(numberOfCourses == 0) {
			errorNumber.setText("You don't have any courses!");
		}
		for (int x=0; x<numberOfCourses; x++) {
			setSubjectButtonText(x, buttons.get(x));
		}
	}

}