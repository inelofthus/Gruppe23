package gui;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import databaseobjects.*;
import javafx.scene.text.Text;

import gui.mainController;

public class courseController implements Initializable {

	
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
	
	
	public String loadCourseName(int x) {
		return mainController.getInstance().getStudents().getCourseIDs().get(x);
	}
	public void setLectures (Course course) {
		mainController.getInstance().setlastTwoLecturesStudent(course.getLastTwoCompletedLectures());
	}
	
	
	@FXML
	private void handleButtonAction(ActionEvent event) throws IOException{
		Stage stage = null; 
	    Parent root;
	    
	    int numberOfCourses = mainController.getInstance().getStudents().getCourseIDs().size();
	    
	    if(numberOfCourses == 0) {
	    	return;
	    }
	    
	    else if(event.getSource()==fag1 && numberOfCourses>0){
	    	//get reference to the button's stage
        
	    	Course course = new Course(mainController.getInstance().getStudents().getCourseIDs().get(0));
	        stage=(Stage) fag1.getScene().getWindow();
	        mainController.getInstance().setlastTwoLecturesStudent(course.getLastTwoCompletedLectures());
	       
/*
	    	Course course = new Course(loadCourseName(0));
	        stage=(Stage) fag1.getScene().getWindow();
	        setLectures(course);
*/
	    }
	    else if (event.getSource()==fag2 && numberOfCourses>1){
	    	Course course = new Course(loadCourseName(1));
	    	stage=(Stage) fag2.getScene().getWindow();

	    	 mainController.getInstance().setlastTwoLecturesStudent(course.getLastTwoCompletedLectures());

	    	//setLectures(course);

	    }
	    else if (event.getSource()==fag3 && numberOfCourses>2){
	    	Course course = new Course(loadCourseName(2));
	    	stage=(Stage) fag3.getScene().getWindow();

	    	 mainController.getInstance().setlastTwoLecturesStudent(course.getLastTwoCompletedLectures());

	    	//setLectures(course);

		}
	    else if (event.getSource()==fag3 && numberOfCourses>3){
	    	Course course = new Course(loadCourseName(3));
	    	stage=(Stage) fag4.getScene().getWindow();

	    	 mainController.getInstance().setlastTwoLecturesStudent(course.getLastTwoCompletedLectures());

	    	//setLectures(course);

		}
	    
	    //load up OTHER FXML document
        root = FXMLLoader.load(getClass().getResource("lecture.fxml"));
	    
        
	    //create a new scene with root and set the stage
	    Scene scene = new Scene(root);
	    stage.setScene(scene);
	    stage.show();
	    }
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		//System.out.println(mainController.getInstance().getStudents().getCourseIDs().get(0));
		int numberOfCourses = mainController.getInstance().getStudents().getCourseIDs().size();
		if(numberOfCourses == 0) {
	    	errorNumber.setText("You don't have any courses!");
		}
		else if (numberOfCourses<2) {
			fag1.setText(loadCourseName(0));
		}
		else if (numberOfCourses<3) {
			fag1.setText(loadCourseName(0));
			fag2.setText(loadCourseName(1));
		}
		else if (numberOfCourses<4) {
			fag1.setText(loadCourseName(0));
			fag2.setText(loadCourseName(1));
			fag3.setText(loadCourseName(2));
		}
		else {
			fag1.setText(loadCourseName(0));
			fag2.setText(loadCourseName(1));
			fag3.setText(loadCourseName(2));
			fag4.setText(loadCourseName(3));
		}
	}

}
