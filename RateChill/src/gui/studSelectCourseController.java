package gui;


import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import database.DBController;
import databaseobjects.Student;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class studSelectCourseController implements Initializable {

	//fxml objects

	@FXML
	public Button home;
	public Button back;
	public Button logout;
	public Button searchName;
	public Button searchCode;
	public Button finish;
	public Button sendRight;
	public Button sendLeft;

	@FXML
	public TextField searchText;
	public Text badSearch;
	public Text badChoice;
	
	@FXML
	public ListView<String> options;
	public ListView<String> choices;
	
    private Student stud = mainController.getInstance().getStudents();
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
		if (event.getSource() == home) {
			loadNextScene(home, stage, "courseStud.fxml");
		}
		if (event.getSource() == back) {
			loadNextScene(back, stage, "courseStud.fxml");
		}
		if (event.getSource() == logout) {
			loadNextScene(logout, stage, "loginStud.fxml");
		}if (event.getSource() == finish) {
			loadNextScene(logout, stage, "courseStud.fxml");
		}
	}
	
//	public boolean isUserButtonPushed(ActionEvent event) {
//		if (event.getSource() == home || event.getSource() == back || event.getSource() == logout) {
//			return true;
//		}
//		return false;
//	}
	
	
	
	@FXML
	private void handleButtonAction(ActionEvent event) throws IOException{
		if(event.getSource()== searchName){
//			get a list of courses that matches search then add to listview
			badChoice.setText("");
			badSearch.setText("");
			ArrayList<String> searchResult = DBC.getCoursesStartingWith(searchText.getText(), "name");
			options.getItems().clear();
			if(searchResult.isEmpty()){
				badSearch.setText("no courses matching your search");
			}
			options.getItems().addAll(searchResult);
		}
		if(event.getSource()== searchCode){
			badChoice.setText("");
			badSearch.setText("");
//			get a list of courses that matches search then add to listview
			ArrayList<String> searchResult = DBC.getCoursesStartingWith(searchText.getText(), "code");
			options.getItems().clear();
			if(searchResult.isEmpty()){
				badSearch.setText("no courses matching your search");
			}
			options.getItems().addAll(searchResult);
		}
		if(event.getSource() == sendRight){
			badChoice.setText("");
			badSearch.setText("");
			System.out.println("sendRight pressed");
			String s = options.getSelectionModel().getSelectedItem();
			String[] stringSplit = s.split("\\s+"); //splits into array with courseCode and courseName
			String courseCode = stringSplit[0];
			String courseName = stringSplit[1];
			
		      if (s != null && checkCourseChoice(courseCode)) {
		          stud.addCourse(courseCode, courseName);
		    	  options.getSelectionModel().clearSelection();
		          choices.getItems().add(s);
		        }
		}
		if(event.getSource() == sendLeft){
			badChoice.setText("");
			badSearch.setText("");
			System.out.println("sendLeft pressed");
			String s = choices.getSelectionModel().getSelectedItem();
			String[] stringSplit = s.split("\\s+"); //splits into array with courseCode and courseName
			String courseCode = stringSplit[0];
//			String courseName = stringSplit[1];
			
		      if (s != null) {
		          stud.removeCourse(courseCode);
		    	  options.getSelectionModel().clearSelection();
		          choices.getItems().remove(s);
		        }
		}
		
		if(event.getSource() == finish){
			Stage stage = null; 
			loadNextScene(finish, stage, "courseStud.fxml");
		}
	}
	
	
	
	
	private boolean checkCourseChoice(String s) {
		boolean okChoice = true;
		

		if (stud.getCourseIDs().contains(s)) {
			badChoice.setText("This course is already added");
			okChoice = false;
		}
		if (stud.getCourseIDs().size() >= 4) {
			badChoice.setText("Max 4 courses");
			okChoice = false;
		}

		return okChoice;
	
}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		for(String cID : stud.getCourseIDs()){
			choices.getItems().add(cID + " " + stud.getCourseIDNames().get(cID));
		}
		

	}

}
