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
import databaseobjects.Course;
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
	
	
	//an attempt to make a 
	/*public courseController() {
		Student currentUser = getCurrentUser();
	}*/
	
	
	
	@FXML
	private void handleButtonAction(ActionEvent event) throws IOException{
		Stage stage; 
	    Parent root;
	    
	    //int numberOfCourses
	    
	    if(event.getSource()==fag1){
	    	//get reference to the button's stage
	    	System.out.println(mainController.getInstance().getStudents().getCourseIDs().get(0));
	    	Course course = new Course(mainController.getInstance().getStudents().getCourseIDs().get(0));
	        stage=(Stage) fag1.getScene().getWindow();
	        course.getLastTwoCompletedLectures();
	    }
	    else if (event.getSource()==fag2){
	    	Course course = new Course(mainController.getInstance().getStudents().getCourseIDs().get(1));
	    	stage=(Stage) fag2.getScene().getWindow();
	    	course.getLastTwoCompletedLectures();
	    }
	    else if (event.getSource()==fag3){
	    	Course course = new Course(mainController.getInstance().getStudents().getCourseIDs().get(2));
	    	stage=(Stage) fag3.getScene().getWindow();
	    	course.getLastTwoCompletedLectures();
		}
	    else {
	    	Course course = new Course(mainController.getInstance().getStudents().getCourseIDs().get(3));
	    	stage=(Stage) fag4.getScene().getWindow();
	    	course.getLastTwoCompletedLectures();
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
		fag1.setText(mainController.getInstance().getStudents().getCourseIDs().get(0));
		fag2.setText(mainController.getInstance().getStudents().getCourseIDs().get(1));
		//fag3.setText(mainController.getInstance().getStudents().getCourseIDs().get(2));
		//fag4.setText(mainController.getInstance().getStudents().getCourseIDs().get(3));
	}

}
