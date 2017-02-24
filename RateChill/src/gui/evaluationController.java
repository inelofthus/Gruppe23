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
	
	
	//Creates an evaluation that can be inserted into the database
	
	private String createEvaluation(ActionEvent event){
		return null;
	}
	
	@FXML
	private void handleButtonAction(ActionEvent event) throws IOException{
		String rating = "";
		if (event.getSource() == submit){
			String error = "";
			if (group.getSelectedToggle() == null) {
				error += "Choose a rating";
				debugText.setText(error);
			}
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
			String comment = feedback.getText();
			Integer lectureID = mainController.getInstance().getChosenStudentLecture();
			//insertEvaluation into database
			mainController.getInstance().getStudents().giveEvaluation(lectureID, rating, comment);
		}
	}
	
	
	
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method
		
	}

}
