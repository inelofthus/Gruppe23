package gui;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import databaseobjects.Student;
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
	
	
	//String loginInfo;
	
	public Student getCurrentUser() {
		return new Student(username.getText());
	}
	
	
	
	@FXML
	public void handleButtonAction(ActionEvent event) throws IOException{
		Stage stage;
		
		Student stud = new Student(username.getText());
		
		if(event.getSource()==student){
	    	
			
	    	//here we want to create a new student object and print error msg if it doesn't exist    	
			if(stud.existsInDB()) {
	    		ArrayList<String> courses = stud.getCourseIDs();
	    		int number = courses.size();
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
	    	
	    	String errorMsg = "User doesn't exist. Try again";
	    	error.setText(errorMsg);
	    }
	    	
	    else{
	    	stage=(Stage) professor.getScene().getWindow();
	    	Parent root = FXMLLoader.load(getClass().getResource("course.fxml"));
	    	root = FXMLLoader.load(getClass().getResource("course.fxml"));
	    	/*
	    	//here we wanna create a new professor object if the professor does not already exist
	    	if(!("textfield" == "existing professor")) {
	    		Professor professorObj = new Professor();
	    	}
	    	
	    	//we want to store the info and use it later, perhaps in a String or use a get method?
	    	loginInfo = "textfield";
	    	*/
	    }
		
	     
	} 
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		
	}

}
