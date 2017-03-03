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

public class courseProfController implements Initializable {

	
	@FXML
	public Button fag1;
	@FXML
	public Button fag2;
	@FXML
	public Button fag3;
	@FXML
	public Button fag4;
	@FXML
	public Text errorNumber;
	
	//creates a list of buttons to iterate over in initializer
	ArrayList<Button> buttons = new ArrayList<Button>();
	
	
	public String loadCourseCode(int x) {
		return mainController.getInstance().getProfessor().getCourseIDs().get(x);
	}
	
	public void setLectures (Course course) {
		mainController.getInstance().setlastTwoLecturesProfessor(course.getLastTwoCompletedLectures());
	}
	
	public void loadCourse(Course course) {
		mainController.getInstance().setCourse(course);
	}
	
	public String loadCourseName(int x){
		Professor prof = mainController.getInstance().getProfessor();
		return prof.getCourseNameForCourse(prof.getCourseIDs().get(x));
	}
	
	public void setSubjectButtonText(int x, Button fagButton){
		String courseCode = loadCourseCode(x);
		String courseCodeName = courseCode + "\n" + loadCourseName(x);
		fagButton.setText(courseCodeName);
		System.out.println(courseCodeName);
	}
	
	//method to go to next GUI
	public void loadNextScene(Stage stage)  throws IOException{
		//load up OTHER FXML document
		Parent root;
		root = FXMLLoader.load(getClass().getResource("lectureProf.fxml"));
		
	    //create a new scene with root and set the stage
	    Scene scene = new Scene(root);
	    stage.setScene(scene);
	    stage.show();
	}
	
	@FXML
	private void handleButtonAction(ActionEvent event) throws IOException{
		Stage stage = null;
	    
		//the number of courses a professor has
	    int numberOfCourses = mainController.getInstance().getProfessor().getCourseIDs().size();
	    
	    
	    if(numberOfCourses == 0) {
	    	return;
	    }
	    else if(event.getSource()==fag1 && numberOfCourses>0){
	    	
	    	Course course = new Course(loadCourseCode(0));
	    	loadCourse(course);
	        stage=(Stage) fag1.getScene().getWindow();
	        
	        setLectures(course);
	        loadNextScene(stage);
	    }
	    
	    else if (event.getSource()==fag2 && numberOfCourses>1){
	    	Course course = new Course(loadCourseCode(1));
	    	loadCourse(course);
	    	stage=(Stage) fag2.getScene().getWindow();

	    	setLectures(course);
	    	loadNextScene(stage);
	    }
	    
	    else if (event.getSource()==fag3 && numberOfCourses>2){
	    	Course course = new Course(loadCourseCode(2));
	    	loadCourse(course);
	    	stage=(Stage) fag3.getScene().getWindow();
	    	
	    	setLectures(course);
	    	loadNextScene(stage);
	    	
		}
	    
	    else if (event.getSource()==fag3 && numberOfCourses>3){
	    	Course course = new Course(loadCourseCode(3));
	    	loadCourse(course);
	    	stage=(Stage) fag4.getScene().getWindow();
	    	
	    	setLectures(course);
	    	loadNextScene(stage);
		}
	}
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		int numberOfCourses = mainController.getInstance().getProfessor().getCourseIDs().size();
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
