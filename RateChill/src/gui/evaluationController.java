package gui;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javax.swing.ButtonModel;
import javafx.application.Platform;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.text.Text;
import gui.mainController;
import databaseobjects.*;

public class evaluationController implements Initializable {

	//fxml objects
	@FXML
	public TextArea feedback; 
	public ToggleButton tooSlow;
	public ToggleButton confusing;
	public ToggleButton perfect;
	public ToggleButton toofast;
	public ToggleButton ok;
	public ToggleGroup group;
	public Button submit;
	public Text debugText;
	public Text submitted;
	public Text overwriteText;
	
	
	//Creates an evaluation that can be inserted into the database
	private void insertEvaluation(int lecID, String rating, String comment){
		mainController.getInstance().getStudents().giveEvaluation(lecID, rating, comment);
	}
	
	
	
	@FXML
	private void handleButtonAction(ActionEvent event) throws IOException{
		String rating = "";
		if (event.getSource() == submit){
			String error = "";
			String completed = "";
			
			//checks if something is selected and gives error message
			if (!(tooSlow.isSelected() || confusing.isSelected() || toofast.isSelected() || ok.isSelected()
					|| perfect.isSelected())) {
				submitted.setText("");
				error = "Choose a rating";
				debugText.setText(error);
				return;
			}
			
			//checks which rating-button is selected and sets the rating to the selected value
			if (tooSlow.isSelected()){
				rating = tooSlow.getText(); 
			}
			else if (confusing.isSelected()){
				rating = confusing.getText();
			}
			else if (toofast.isSelected()){
				rating = toofast.getText();
			}
			else if (ok.isSelected()){
				rating = ok.getText();
			}
			else if (perfect.isSelected()){
				rating = perfect.getText();
			}
			//stores the feedback comment and the lecture ID into variables
			String comment = feedback.getText();
			Integer lectureID = mainController.getInstance().getChosenStudentLecture();
			
			//insertEvaluation into database
			insertEvaluation(lectureID, rating, comment);
			
			//visual confirmation that the evaluation has been submitted
			debugText.setText("");
			completed += "Submitted!";
			submitted.setText(completed);
		}
		

		
/*		//submit-button gets clicked again, gives option to overwrite.
		if (event.getSource() == submit) {
			submitted.setText("");
			String alreadySubmitted = "You already submitted a response, if you want to overwrite, click again!";
			overwriteText.setText(alreadySubmitted);
		}
		if (event.getSource() == submit) {
			String comment = feedback.getText();
			Integer lectureID = mainController.getInstance().getChosenStudentLecture();
			//here we need some code to go past the duplicate in the database and overwrite it somehow
			mainController.getInstance().getStudents().giveEvaluation(lectureID, rating, comment);
			overwriteText.setText("");
			String completed = "Overwritten!";
			submitted.setText(completed);
			return;
		}*/
	}
	
	
	
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method
		
	}

}
