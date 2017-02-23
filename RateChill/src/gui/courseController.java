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
import javafx.scene.text.Text;
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
	@FXML
	public Text errorNumber;
	
	
	//an attempt to make a 
	/*public courseController() {
		Student currentUser = getCurrentUser();
	}*/
	
	public String loadCourseName(int x) {
		return mainController.getInstance().getStudents().getCourseIDs().get(x);
	}
	
	@FXML
	private void handleButtonAction(ActionEvent event) throws IOException{
		Stage stage = null; 
	    Parent root;
	    
	    
	    int numberOfCourses = mainController.getInstance().getStudents().getCourseIDs().size();
	    
	    if((event.getSource()==fag1 || event.getSource()==fag2 || event.getSource()==fag3 ||
	    		event.getSource()==fag4) && numberOfCourses == 0) {
	    	String errorMsg = "You don't have any courses!";
	    	errorNumber.setText(errorMsg);
	    }
	    
	    else if(event.getSource()==fag1 && numberOfCourses>0){
	    	//get reference to the button's stage
	    	Course course = new Course(loadCourseName(0));
	        stage=(Stage) fag1.getScene().getWindow();
	        course.getLastTwoCompletedLectures();
	    }
	    else if (event.getSource()==fag2 && numberOfCourses>1){
	    	Course course = new Course(loadCourseName(1));
	    	stage=(Stage) fag2.getScene().getWindow();
	    	course.getLastTwoCompletedLectures();
	    }
	    else if (event.getSource()==fag3 && numberOfCourses>2){
	    	Course course = new Course(loadCourseName(2));
	    	stage=(Stage) fag3.getScene().getWindow();
	    	course.getLastTwoCompletedLectures();
		}
	    else if (event.getSource()==fag3 && numberOfCourses>3){
	    	Course course = new Course(loadCourseName(3));
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
		fag1.setText(loadCourseName(0));
		fag2.setText(loadCourseName(1));
		//fag3.setText(mainController.getInstance().getStudents().getCourseIDs().get(2));
		//fag4.setText(mainController.getInstance().getStudents().getCourseIDs().get(3));
	}

}
