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


public class createUserController implements Initializable {

	//fxml objects
	@FXML
	public Text badUsername;
	public Text badStudyProgramCode;
	public Text userCreated;
	public Button back;
	public Button finish;
	public ToggleButton student;
	public ToggleButton professor;
	public ToggleGroup toggleGroup;
	public TextField username;
	public TextField studyProgramCode;
	
	DBController DBC = new DBController();
	
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
		if (event.getSource() == back) {
			loadNextScene(back, stage, "login.fxml");
		}
	}

	/*
	public boolean isValidStudyProgramCode() {
		ArrayList<String> studyPrograms = getAllStudyPrograms();
		for (String string:studyPrograms) {
			if (studyProgramCode.getText() == string) {
				return true;
			}
		}
		return false;
	}*/
	
	public void createStudentUser(String name, String programCode) {
		DBC.insertStudent(name, programCode);
	}
	
	@FXML
	private void handleButtonAction(ActionEvent event) throws IOException{
		Stage stage = null;
		userButtons(event, stage);
		
		
		if (event.getSource() == finish){
			Student stud = new Student(username.getText());
			if(stud.existsInDB()) {
				badUsername.setText("Username taken, please make a new one");
				return;
			}
			/*if (!isValidStudyProgramCode()) {
				badStudyProgramCode.setText("Invalid studyprogram-code");
				return;
			}*/
			createStudentUser(username.getText(), studyProgramCode.getText());
			loadNextScene(finish, stage, "login.fxml");
		}			
	}
	
	
	
	
	
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method
		
	}

}
