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

public class courseController implements Initializable {

	@FXML
	Button fag1;
	@FXML
	Button fag2;
	@FXML
	Button fag3;
	@FXML
	Button fag4;
	
	@FXML
	private void handleButtonAction(ActionEvent event) throws IOException{
		Stage stage; 
	    Parent root;
	    if(event.getSource()==fag1){
	    	//get reference to the button's stage         
	        stage=(Stage) fag1.getScene().getWindow();
	    }
	    else if (event.getSource()==fag2){
	    	stage=(Stage) fag2.getScene().getWindow();
	    }
	    else if (event.getSource()==fag3){
	    	stage=(Stage) fag3.getScene().getWindow();
		}
	    else {
	    	stage=(Stage) fag4.getScene().getWindow();
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
		
	}

}
