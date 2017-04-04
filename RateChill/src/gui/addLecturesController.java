package gui;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import database.DBController;
import databaseobjects.Course;
import databaseobjects.Student;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class addLecturesController implements Initializable {


	Course course = mainController.getInstance().getCourse();

	// fxml objects
	
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
	public TableView<String[]> tableView;
	public TableColumn<String, String> startDateCol;
	public TableColumn<String, String> startTimeCol;
	public TableColumn<String, String> endTimeCol;
	public TableColumn<String, String> endDateCol;
	public TableColumn<String, String> weeklyCol;

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
		ObservableList<String[]> lectureInfo = FXCollections.observableArrayList();
		//startDateCol.setCellValueFactory(new PropertyValueFactory<Lecture, String>("startDate"));
        //startDateCol.setCellValueFactory(new PropertyValueFactory<Lecture, String>("startTime"));
		//lectureInfo.add(new LectureItem("2017-04-01", "08:15", "10:00", "2017-04-01", 0));
		//tableView.setItems(lectureInfo);
	}

}
