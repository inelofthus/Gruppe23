package gui;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.ResourceBundle;

import database.DBController;
import databaseobjects.Lecture;
import databaseobjects.Student;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.text.Text;
import javafx.stage.Stage;


public class customizeButtonsController implements Initializable {

	//fxml objects
	@FXML
	public Button home;
	public Button back;
	public Button logout;
	
	
	public Button submitChanges;
	public Button preview;
	public ToggleButton button1;
	public ToggleButton button2;
	public ToggleButton button3;
	public ToggleButton button4;
	public ToggleButton button5;
	public ToggleGroup previewGroup;
	public TextField buttonText1;
	public TextField buttonText2;
	public TextField buttonText3;
	public TextField buttonText4;
	public TextField buttonText5;
	
	//DBController DBC = new DBController();
	
	public void loadNextScene(Button button, Stage stage, String string) throws IOException{
		stage=(Stage) button.getScene().getWindow();
		Parent root;
		root = FXMLLoader.load(getClass().getResource(string));
		
		//create a new scene with root and set the stage
		Scene scene = new Scene(root);
		stage.setScene(scene);
		stage.show();	
	}
	
	
	public void userButtons(ActionEvent event, Stage stage) throws IOException{
		if (event.getSource() == home) {
			loadNextScene(home, stage, "courseProf.fxml");
		}
		if (event.getSource() == back) {
			loadNextScene(back, stage, "lectureProf.fxml");
		}
		if (event.getSource() == logout) {
			loadNextScene(logout, stage, "login.fxml");
		}
	}
	
	public void setPreviewTexts() {
		if (buttonText1.getText()!="") {
			button1.setText(buttonText1.getText());
		}
		else {
			//Set the button to the previous value (or standard value)
			//button1.setText("hent frå database");
		}
		if (buttonText2.getText()!="") {
			button2.setText(buttonText2.getText());
		}
		else {
			//Set the button to the previous value (or standard value)
			//button2.setText("hent frå database");
		}
		if (buttonText3.getText()!="") {
			button3.setText(buttonText3.getText());
		}
		else {
			//Set the button to the previous value (or standard value)
			//button3.setText("hent frå database");
		}
		if (buttonText4.getText()!="") {
			button4.setText(buttonText4.getText());
		}
		else {
			//Set the button to the previous value (or standard value)
			//button4.setText("hent frå database");
		}
		if (buttonText5.getText()!="") {
			button5.setText(buttonText5.getText());
		}
		else {
			//Set the button to the previous value (or standard value)
			//button5.setText("hent frå database");
		}
	}
	
	@FXML
	private void handleButtonAction(ActionEvent event) throws IOException{
		Stage stage = null;
		userButtons(event, stage);
		if(event.getSource() == preview) {
			setPreviewTexts();
			return;
		}
		else if (event.getSource() == submitChanges){
			
			return;
		}
	}
	
	
	
	
	
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method
		
	}

}
