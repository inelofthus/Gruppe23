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

public class loginController implements Initializable {

	
	@FXML
	Button student;
	@FXML
	Button professor;
	
	@FXML
	 private void handleButtonAction(ActionEvent event) throws IOException{
	     Stage stage; 
	     Parent root;
	     if(event.getSource()==student){
	        //get reference to the button's stage         
	        stage=(Stage) student.getScene().getWindow();
	        //load up OTHER FXML document
	  root = FXMLLoader.load(getClass().getResource("course.fxml"));
	      }
	     else{
	       stage=(Stage) professor.getScene().getWindow();
	  root = FXMLLoader.load(getClass().getResource("course.fxml"));
	      }
	      //create a new scene with root and set the stage
	      Scene scene = new Scene(root);
	      stage.setScene(scene);
	      stage.show();
	    } 
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		
	}

}
