package gui;

import java.net.URL;
import java.util.ResourceBundle;

import javax.swing.ButtonModel;
import database.*;
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
	
	private void createEvaluation(ActionEvent event){
		System.out.println(event.getSource());
		debugText.setText("");
		if (group.getSelectedToggle() == null) {
			debugText.setText("Choose a rating");
			return;
		}
	}
	
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		
	}

}
