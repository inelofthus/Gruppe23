package gui;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import databaseobjects.Student;
import databaseobjects.Professor;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import gui.mainController;

public class loginController implements Initializable {

	
	@FXML
	public Button student;
	@FXML
	public Button professor;
	public TextField username; 
	public Text error;
	
	
	
	
	@FXML
	public void handleButtonAction(ActionEvent event) throws IOException{
		Stage stage;
		String errorMsg = "User doesn't exist. Try again";
		
		
		
		if(event.getSource()==student){
			Student stud = new Student(username.getText());
			
	    	//checks if the student username exists
			if(stud.existsInDB()) {
	    		//creates list of courses and an int for number of courses
				ArrayList<String> courses = stud.getCourseIDs();
	    		
	    		
	    		mainController.getInstance().setStudent(stud);
	    		
	    		//get reference to the button's stage
		    	stage=(Stage) student.getScene().getWindow();
		    	//load up OTHER FXML document
				Parent root = FXMLLoader.load(getClass().getResource("course.fxml"));
		    	//create a new scene with root and set the stage
	    		Scene scene = new Scene(root);
	    		stage.setScene(scene);
	    		stage.show();
			}
	    	
	    	error.setText(errorMsg);
	    }
	    
	    else{
	    	Professor prof = new Professor(username.getText());
	    	
	    	if(prof.existsInDB()) {
	    		ArrayList<String> courses = prof.getCourseIDs();
	    		int number = courses.size();
	    		stage=(Stage) professor.getScene().getWindow();
	    		Parent root = FXMLLoader.load(getClass().getResource("courseProf.fxml"));
	    		Scene scene = new Scene(root);
	    		stage.setScene(scene);
	    		stage.show();
	    	}
	    	error.setText(errorMsg);
	    }
		
	     
	} 
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		
	}

}
