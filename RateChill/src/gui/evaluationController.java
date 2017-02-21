package gui;

import java.net.URL;
import java.util.ResourceBundle;

import javax.swing.ButtonModel;

import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;

public class evaluationController implements Initializable {

	
	@FXML
	TextArea feedback; 
	@FXML
	ToggleButton tooSlow;
	@FXML
	ToggleButton confusing;
	@FXML
	ToggleButton perfect;
	@FXML
	ToggleButton toofast;
	@FXML
	ToggleButton ok;
	@FXML
	Button submit;
	
	
	/*
	 final ToggleGroup group = new ToggleGroup();

     
     addListener(new ChangeListener<Toggle>() {
                 public void changed(ObservableValue<? extends Toggle> ov,Toggle oldToggle, Toggle newToggle) {
                     if (null != newToggle) {
                         System.out.println(group.getSelectedToggle().getUserData());
                 }
             });

     tooslow.setToggleGroup(group);
     confusing.setToggleGroup(group);
     perfect.setToggleGroup(group);
     toofast.setToggleGroup(group);
     ok.setToggleGroup(group);
	*/
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		
	}

}
