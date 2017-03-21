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
import javafx.scene.control.Hyperlink;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import gui.mainController;

public class loginController implements Initializable {

	
	@FXML
	public Button student;
	public Button professor;
	public TextField username; 
	public TextField password; 
	public Text error;
	public Hyperlink newUser;
	
	
	
	
	@FXML
	public void handleButtonAction(ActionEvent event) throws IOException{
		Stage stage;
		String errorMsg = "User doesn't exist. Try again";
		
		
		
		if(event.getSource()==student){
			Student stud = new Student(username.getText());
			
	    	//checks if the student username exists
			if(stud.existsInDB()) {
	    		
	    		mainController.getInstance().setStudent(stud);
	    		
	    		//get reference to the button's stage
		    	stage=(Stage) student.getScene().getWindow();
		    	//load up OTHER FXML document
				Parent root = FXMLLoader.load(getClass().getResource("courseStud.fxml"));
		    	//create a new scene with root and set the stage
	    		Scene scene = new Scene(root);
	    		stage.setScene(scene);
	    		stage.show();
			}
	    	
	    	error.setText(errorMsg);
	    }
	    
	    else if (event.getSource()==professor){
	    	Professor prof = new Professor(username.getText());
	    	
	    	if(prof.existsInDB()) {
	    		
	    		mainController.getInstance().setProfessor(prof);
	    		
	    		stage=(Stage) professor.getScene().getWindow();
	    		
	    		Parent root = FXMLLoader.load(getClass().getResource("courseProf.fxml"));
	    		Scene scene = new Scene(root);
	    		stage.setScene(scene);
	    		stage.show();
	    	}
	    	error.setText(errorMsg);
	    }
		
	    else if (event.getSource()==newUser){
	    	error.setText(":)");
	    }
		
	     
	} 
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		
	}

}
