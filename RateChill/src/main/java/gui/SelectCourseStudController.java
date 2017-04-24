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
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 * Controller for the gui where students select courses
 * @author Ine L. Arnesen, Kari M. Johannessen, Magnus Tvilde, Nicolai C. Michelet
 */
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
	private Color myRed = new Color(0.937, 0.290, 0.290, 1);
	
	private Student stud = MainController.getInstance().getStudent();
	private DBController DBC = new DBController();
	private MainController mc = MainController.getInstance();

	
	/**
	 * Loads the relevant fxml file
	 * @param button The button pressed calling the function
	 * @param stage The stage
	 * @param string The fxml file to be loaded
	 * @throws IOException
	 */
	public void loadNextScene(Button button, String string) throws IOException {
		Stage stage = (Stage) button.getScene().getWindow();
		Parent root;
		root = FXMLLoader.load(getClass().getResource(string));

		// create a new scene with root and set the stage
		Scene scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
	}
	
	/**
	 * Handles user buttons (home, back, logout, finish)
	 * @param event Button ActionEvent 
	 * @throws IOException
	 */
	@FXML
	public void userButtons(ActionEvent event) throws IOException {
		Stage stage = null;
		if (event.getSource() == home) {
			loadNextScene(home, "CourseStud.fxml");
		}
		if (event.getSource() == back) {
			loadNextScene(back, "CourseStud.fxml");
		}
		if (event.getSource() == logout) {
			loadNextScene(logout, "LoginStud.fxml");
		}
		if (event.getSource() == finish) {
			loadNextScene(logout, "CourseStud.fxml");
		}
	}
	
	public void handleKeyAction(KeyEvent ke) throws IOException{
		if(ke.getCode().equals(KeyCode.ENTER)){
			handleSearch();
		}
	}
	
	/**
	 * Retrieves a list of courses that matches the search in the search field
	 * and adds these to the listview
	 */
	private void handleSearch(){
		badChoice.setText("");
		badSearch.setText("");
		errorBar.setVisible(false);
		ArrayList<String> searchResult = getSearchresult(searchText.getText());
		options.getItems().clear();
		if (searchResult.isEmpty()) {
			badSearch.setText("No courses matching your search");
			errorBar.setFill(myRed);
			errorBar.setVisible(true);
		}
		options.getItems().addAll(searchResult);
	}

	@FXML
	private void handleButtonAction(ActionEvent event) throws IOException {
		errorBar.setVisible(false);
		if (event.getSource() == search) {
			handleSearch();
		}
		if (event.getSource() == sendRight) {
			badChoice.setText("");
			badSearch.setText("");
			String courseCodeAndName = options.getSelectionModel().getSelectedItem();
			String courseCode = "";
			String courseName = "";
			
			if (courseCodeAndName != null){
				String[] stringSplit = courseCodeAndName.split("\\s+",2);// splits into array with courseCode and courseName
				courseCode = stringSplit[0];
				courseName = stringSplit[1];
				if (checkCourseChoice(courseCode)) {
					stud.addCourse(courseCode, courseName);
					options.getSelectionModel().clearSelection();
					options.getItems().remove(courseCodeAndName);
					choices.getItems().add(courseCodeAndName);
					mc.setCoursesUpdated("true");
				}
			}
		}
		if (event.getSource() == sendLeft) {
			badChoice.setText("");
			badSearch.setText("");
			String courseCodeAndName = choices.getSelectionModel().getSelectedItem();
			if (courseCodeAndName == null){return;}
			String[] stringSplit = courseCodeAndName.split("\\s+"); // splits into array with courseCode and courseName
			String courseCode = stringSplit[0];
			if (courseCodeAndName != null) {
				stud.removeCourse(courseCode);
				options.getSelectionModel().clearSelection();
				choices.getItems().remove(courseCodeAndName);
				options.getItems().add(courseCodeAndName);
				mc.setCoursesUpdated("true");
			}
		}
		if (event.getSource() == finish) {
			Stage stage = null;
			loadNextScene(finish, "CourseStud.fxml");
		}
	}

	/**
	 * Helper method for {@link #handleSearch()}. Returns an ArrayList of courses that
	 * matches the text in the search field
	 * @param text The text in the search field
	 * @return An ArrayList of courses that matches search
	 */
	private ArrayList<String> getSearchresult(String text) {
		ArrayList<String> result = new ArrayList<>();
		
		for(String course: allCourses){
			if(course.toLowerCase().contains(text.toLowerCase())){
				result.add(course);
			}
		}
		return result;
	}

	/**
	 * Checks whether or not the course can be added to selected courses.
	 * If the course already is in the list of selected courses or four
	 * courses are already chosen, false is returned.
	 * @param courseCode the course's course code
	 * @return Whether or not the course can be added to selected courses
	 */
	private boolean checkCourseChoice(String courseCode) {
		boolean okChoice = true;

		if (stud.getCourseIDs().contains(courseCode)) {
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

	/**
	 * Initializes the SelectCourseStud.fxml gui
	 */
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		mc.setCoursesUpdated("false");
		allCourses = DBC.getAllCourses();
		options.getItems().clear();
		options.getItems().addAll(allCourses);

		for (String cID : stud.getCourseIDs()) {
			choices.getItems().add(cID + " " + stud.getCourseIDNames().get(cID));
			options.getItems().remove(cID + " " + stud.getCourseIDNames().get(cID));
		}

	}

}
