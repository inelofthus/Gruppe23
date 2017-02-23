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

	public void setButtonText(){
		fag1.setText(mainController.getInstance().getStudents().getCourseIDs().get(0));
	}
	
	public void loadView(){
		setButtonText();
	}
	
	
	@FXML
	private void handleButtonAction(ActionEvent event) throws IOException{
		Stage stage; 
	    Parent root;
	    
	    if(event.getSource()==fag1){
	    	//get reference to the button's stage
	    	System.out.println(mainController.getInstance().getStudents().getCourseIDs().get(0));
	    	Course course1 = new Course(mainController.getInstance().getStudents().getCourseIDs().get(0));
	        stage=(Stage) fag1.getScene().getWindow();
	    }
	    else if (event.getSource()==fag2){
	    	Course course2 = new Course("fag2.getCourseID()");
	    	stage=(Stage) fag2.getScene().getWindow();
	    }
	    else if (event.getSource()==fag3){
	    	Course course3 = new Course("fag3.getCourseID()");
	    	stage=(Stage) fag3.getScene().getWindow();
		}
	    else {
	    	Course course4 = new Course("fag4.getCourseID()");
	    	stage=(Stage) fag4.getScene().getWindow();
		}
	    
	    //load up OTHER FXML document
        root = FXMLLoader.load(getClass().getResource("lecture.fxml"));
	    
        
        /*course.getLastTwoCompletedLectures
        */
        
	    //create a new scene with root and set the stage
	    Scene scene = new Scene(root);
	    stage.setScene(scene);
	    loadView();
	    stage.show();
	    }
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		fag1.setText(mainController.getInstance().getStudents().getCourseIDs().get(0));
		
	}

}
