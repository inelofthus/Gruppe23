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
	public Text userCreated;
	public Button back;
	public Button finish;
	public ToggleButton student;
	public ToggleButton professor;
	public ToggleGroup toggleGroup;
	public TextField username;
	
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
			loadNextScene(back, stage, "loginStud.fxml");
		}
	}


	
	public void createStudentUser(String name) {
		DBC.insertStudent(name,"");
	}
	
	@FXML
	private void handleButtonAction(ActionEvent event) throws IOException{
		Stage stage = null;
		userButtons(event, stage);
		boolean validInput = true;
		
		if (event.getSource() == finish){
			Student stud = new Student(username.getText());
			if(stud.existsInDB()) {
				validInput = false;
				System.out.println(validInput);
				badUsername.setText("Username taken, please make a new one");
				return;
			}if(stud.getUsername().length() > 30) {
				validInput = false;
				System.out.println(validInput);
				badUsername.setText("Username too long, please make a new one");
				return;
			}if(username.getText().isEmpty()){
				validInput = false;
				System.out.println(validInput);
				badUsername.setText("Please write a username");
				return;
			}
			
			if(validInput){
				createStudentUser(username.getText());
				loadNextScene(finish, stage, "loginStud.fxml");
			}
			
		}			
	}
	
	
	
	
	
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method
		
	}

}
