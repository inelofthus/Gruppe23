package gui;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import gui.mainController;
import databaseobjects.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class lectureController implements Initializable {

	
	@FXML
	Button lecture1;
	@FXML
	Button lecture2;
	
	@FXML
	private void handleButtonAction(ActionEvent event) throws IOException{
		Stage stage; 
	    Parent root;
	    if(event.getSource()==lecture1){
	    	//get reference to the button's stage         
	        stage=(Stage) lecture1.getScene().getWindow();
	        mainController.getInstance().setChosenStudentLecture(mainController.getInstance().getStudentLectureIDs().get(0));
	        
	        //here we want to create a 
	    }
	    else {
	    	stage=(Stage) lecture2.getScene().getWindow();
	    	mainController.getInstance().setChosenStudentLecture(mainController.getInstance().getStudentLectureIDs().get(1));
	    }
	    
	    
	    
	    //load up OTHER FXML document
		root = FXMLLoader.load(getClass().getResource("evaluation.fxml"));
	    
	    //create a new scene with root and set the stage
		Scene scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
		} 
	   
	
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		
		
	}
	
	

}
