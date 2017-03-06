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
import javafx.scene.text.Text;
import javafx.stage.Stage;

import databaseobjects.*;

public class evaluationProfController implements Initializable {

	//fxml objects
	@FXML
	public Text perfect;
	public Text ok;
	public Text tooFast;
	public Text tooSlow;
	public Text confused;
	public Text perfectNumber;
	public Text okNumber;
	public Text tooFastNumber;
	public Text tooSlowNumber;
	public Text confusedNumber;
	public Button seePerfectComments;
	public Button seeOkComments;
	public Button seeFastComments;
	public Button seeSlowComments;
	public Button seeConfusedComments;
	public Text debugText;
	public Text submitted;
	public Text overwriteText;
	
	Integer lectureID = mainController.getInstance().getChosenStudentLecture();
	
	//ArrayList evaluations = lec.getEvaluations();
	
	
	@FXML
	private void handleButtonAction(ActionEvent event) throws IOException{
		Stage stage;
		Parent root;
		if (event.getSource() == seePerfectComments){
			stage = (Stage) seePerfectComments.getScene().getWindow();
			root = FXMLLoader.load(getClass().getResource("commentPage.fxml"));
			Scene scene = new Scene(root);
			stage.setScene(scene);
		    stage.show();
		}
		
		else if (event.getSource() == seeOkComments) {
			stage = (Stage) seeOkComments.getScene().getWindow();
			root = FXMLLoader.load(getClass().getResource("commentPage.fxml"));
			Scene scene = new Scene(root);
			stage.setScene(scene);
		    stage.show();
		}
		
		else if (event.getSource() == seeFastComments) {
			stage = (Stage) seeFastComments.getScene().getWindow();
			root = FXMLLoader.load(getClass().getResource("commentPage.fxml"));
			Scene scene = new Scene(root);
			stage.setScene(scene);
		    stage.show();
		}
		
		else if (event.getSource() == seeSlowComments) {
			stage = (Stage) seeSlowComments.getScene().getWindow();
			root = FXMLLoader.load(getClass().getResource("commentPage.fxml"));
			Scene scene = new Scene(root);
			stage.setScene(scene);
		    stage.show();
		}
		
		else if (event.getSource() == seeConfusedComments) {
			stage = (Stage) seeConfusedComments.getScene().getWindow();
			root = FXMLLoader.load(getClass().getResource("commentPage.fxml"));
			Scene scene = new Scene(root);
			stage.setScene(scene);
		    stage.show();
		}
		
	}
	
	
	//
	
	
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method
		
		//setting the appropriate number on the textfields
		//perfectNumber.setText(mainController.getInstance().getCourse().getLecture().getPerfectScore());
		//okNumber.setText(mainController.getInstance().getCourse().getLecture().getPerfectScore());
		//tooFastNumber.setText(mainController.getInstance().getCourse().getLecture().getPerfectScore());
		//tooSlowNumber.setText(mainController.getInstance().getCourse().getLecture().getPerfectScore());
		//confusedNumber.setText(mainController.getInstance().getCourse().getLecture().getPerfectScore());
	}

}
