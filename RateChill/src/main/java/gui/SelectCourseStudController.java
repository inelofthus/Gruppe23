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
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class SelectCourseStudController implements Initializable {

	@FXML
	public Button home;
	public Button back;
	public Button logout;
	public Button search;
	public Button finish;
	public Button sendRight;
	public Button sendLeft;

	@FXML
	public TextField searchText;
	public Text badSearch;
	public Text badChoice;
	public Rectangle errorBar;

	@FXML
	public ListView<String> options;
	public ListView<String> choices;

	private ArrayList<String> allCourses;
	Color myRed = new Color(0.937, 0.290, 0.290, 1);
	
	private Student stud = MainController.getInstance().getStudents();
	DBController DBC = new DBController();

	public void loadNextScene(Button button, Stage stage, String string) throws IOException {
		stage = (Stage) button.getScene().getWindow();
		Parent root;
		root = FXMLLoader.load(getClass().getResource(string));

		// create a new scene with root and set the stage
		Scene scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
	}
	
	@FXML
	public void userButtons(ActionEvent event) throws IOException {
		Stage stage = null;
		if (event.getSource() == home) {
			loadNextScene(home, stage, "CourseStud.fxml");
		}
		if (event.getSource() == back) {
			loadNextScene(back, stage, "CourseStud.fxml");
		}
		if (event.getSource() == logout) {
			loadNextScene(logout, stage, "LoginStud.fxml");
		}
		if (event.getSource() == finish) {
			loadNextScene(logout, stage, "CourseStud.fxml");
		}
	}

	@FXML
	private void handleButtonAction(ActionEvent event) throws IOException {
		if (event.getSource() == search) {
			// get a list of courses that matches search then add to listview
			badChoice.setText("");
			badSearch.setText("");
			ArrayList<String> searchResult = getSearchresult(searchText.getText());
			options.getItems().clear();
			if (searchResult.isEmpty()) {
				badSearch.setText("No courses matching your search");
				errorBar.setFill(myRed);
				errorBar.setVisible(true);
			}
			options.getItems().addAll(searchResult);
		}

		if (event.getSource() == sendRight) {
			badChoice.setText("");
			badSearch.setText("");
			System.out.println("sendRight pressed");
			String s = options.getSelectionModel().getSelectedItem();
			String courseCode = "";
			String courseName = "";
			if (s != null){
				String[] stringSplit = s.split("\\s+",2); // splits into array with
														// courseCode and courseName
				courseCode = stringSplit[0];
				courseName = stringSplit[1];
			}
			if (s != null && checkCourseChoice(courseCode)) {
				stud.addCourse(courseCode, courseName);
				options.getSelectionModel().clearSelection();
				options.getItems().remove(s);
				choices.getItems().add(s);
			}
		}
		if (event.getSource() == sendLeft) {
			badChoice.setText("");
			badSearch.setText("");
			System.out.println("sendLeft pressed");
			String s = choices.getSelectionModel().getSelectedItem();
			String[] stringSplit = s.split("\\s+"); // splits into array with
													// courseCode and courseName
			String courseCode = stringSplit[0];
			// String courseName = stringSplit[1];

			if (s != null) {
				stud.removeCourse(courseCode);
				options.getSelectionModel().clearSelection();
				choices.getItems().remove(s);
				options.getItems().add(s);
			}
		}

		if (event.getSource() == finish) {
			Stage stage = null;
			loadNextScene(finish, stage, "CourseStud.fxml");
		}
	}

	private ArrayList<String> getSearchresult(String text) {
		ArrayList<String> result = new ArrayList<>();
		
		for(String course: allCourses){
			if(course.toLowerCase().contains(text.toLowerCase())){
				result.add(course);
			}
		}
	return result;
}

	private boolean checkCourseChoice(String s) {
		boolean okChoice = true;

		if (stud.getCourseIDs().contains(s)) {
			badChoice.setText("This course is already added");
			okChoice = false;
			errorBar.setFill(myRed);
			errorBar.setVisible(true);
		}
		if (stud.getCourseIDs().size() >= 4) {
			badChoice.setText("Max 4 courses");
			okChoice = false;
			errorBar.setFill(myRed);
			errorBar.setVisible(true);
		}

		return okChoice;

	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		allCourses = DBC.getAllCourses();
		options.getItems().clear();
		options.getItems().addAll(allCourses);

		for (String cID : stud.getCourseIDs()) {
			choices.getItems().add(cID + " " + stud.getCourseIDNames().get(cID));
			options.getItems().remove(cID + " " + stud.getCourseIDNames().get(cID));
		}

	}

}
