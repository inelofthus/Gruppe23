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
import javafx.scene.control.DatePicker;
import javafx.scene.control.ListView;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class addLecturesController implements Initializable {

	// fxml objects
	private ArrayList<String> allCourses;

	@FXML
	public Button home;
	public Button back;
	public Button logout;
	public Button addLecture;
	public Button finish;

	@FXML
	public TextField startTime;
	public TextField endTime;
	
	@FXML
	public DatePicker startDate;
	public DatePicker endDate;
	public DatePicker holidayStart;
	public DatePicker holidayEnd;

	@FXML
	public TableView<String> tableView;

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

	public void userButtons(ActionEvent event, Stage stage) throws IOException {
		if (event.getSource() == home) {
			loadNextScene(home, stage, "courseProf.fxml");
		}
		if (event.getSource() == back) {
			loadNextScene(back, stage, mainController.getInstance().getPreviousView());
		}
		if (event.getSource() == logout) {
			loadNextScene(logout, stage, "loginProf.fxml");
		}
		if (event.getSource() == finish) {
			loadNextScene(logout, stage, "lectureProf.fxml");
		}
	}


	@FXML
	private void handleButtonAction(ActionEvent event) throws IOException {
		Stage stage = null;
		userButtons(event, stage);
	}
	@Override
	public void initialize(URL location, ResourceBundle resources) {

	}

}
