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

public class evaluationStudController implements Initializable {

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
	
	Integer lectureID = mainController.getInstance().getChosenStudentLecture();
	
	//Creates an evaluation that can be inserted into the database
	private void createEvaluation(int lecID, String rating, String comment){
		mainController.getInstance().getStudents().giveEvaluation(lecID, rating, comment);
	}
	
	private void overwriting(int lecID, String rating, String comment) {
		mainController.getInstance().getStudents().overWriteEvaluation(lecID, rating, comment);
	}
	
	
	private String selectedButton() {
		String rating = "";
		if (tooSlow.isSelected()){
			rating = tooSlow.getText();
		}
		else if (confusing.isSelected()){
			rating += confusing.getText();
		}
		else if (toofast.isSelected()){
			rating += toofast.getText();
		}
		else if (ok.isSelected()){
			rating += ok.getText();
		}
		else if (perfect.isSelected()){
			rating += perfect.getText();
		}
		return rating;
	}
	
	
	@FXML
	private void handleButtonAction(ActionEvent event) throws IOException{
		if (event.getSource() == submit){
			
			//checks if something is selected and gives error message
			if (!(tooSlow.isSelected() || confusing.isSelected() || toofast.isSelected() || ok.isSelected()
					|| perfect.isSelected())) {
				submitted.setText("");
				debugText.setText("Choose a rating");
				return;
			}
			
			
			//if the user already has submitted one or more evaluations on the subject, it gets overwritten
			if (mainController.getInstance().getStudents().hasEvaluatedLecture(lectureID)) {
				
				
				String comment = feedback.getText();
				
				//the actual overwriting function
				overwriting(lectureID, selectedButton(), comment);
				
				//removing other messages the GUI displays and setting the overwritten text
				debugText.setText("");
				submitted.setText("");
				String overwritten = "Your submission has been overwritten";
				overwriteText.setText(overwritten);
				return;
			}
			//checks which rating-button is selected and sets the rating to the selected value
			//stores the feedback comment
			String comment = feedback.getText();
			
			//insertEvaluation into database
			createEvaluation(lectureID, selectedButton(), comment);
			
			//visual confirmation that the evaluation has been submitted
			debugText.setText("");
			submitted.setText("Submitted!");
			
			//sets the text on the submit-button to overwrite
			submit.setText("Overwrite");
		}
	}
	//
	
	
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method
		if (mainController.getInstance().getStudents().hasEvaluatedLecture(lectureID)) {
			submit.setText("Overwrite");
			overwriteText.setText("Evaluation already given, overwrite?");
		}
	}

}
