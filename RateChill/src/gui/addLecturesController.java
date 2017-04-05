package gui;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import database.DBController;
import databaseobjects.Course;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class addLecturesController implements Initializable {


	

	// fxml objects
	
	@FXML
	public Button home;
	public Button back;
	public Button logout;
	public Button addLecture;
	public Button finish;

	@FXML
	public TextField startTime;
	
	@FXML
	public DatePicker startDate;
	public DatePicker endDate;
	public DatePicker holidayStart;
	public DatePicker holidayEnd;
	
	@FXML CheckBox repeat;

	@FXML
	public ListView<String> listView;
	
	Course course = mainController.getInstance().getCourse();
	String prof = mainController.getInstance().getProfessor().getUsername();
	DBController DBC = new DBController(); 
	
	private Pattern pattern;
	private Matcher matcher;
	
	private static final String TIME24HOURS_PATTERN =
	           "([01]?[0-9]|2[0-3]):[0-5][0-9]";


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
			loadNextScene(back, stage, "lectureProf.fxml");
			//loadNextScene(back, stage, mainController.getInstance().getStack().pop());
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
	
	@FXML
	private void submitAddLecture(ActionEvent event){
		if (startDate.getValue() == null || startTime.getText().length() == 0 || !validate(startTime.getText())){
			System.out.println("Feil");
			if (startDate.getValue() == null){
				System.out.println("Skriv inn dato");
			}
			if (!validate(startTime.getText())){
				System.out.println("Feil klokkeslettformat");
			}
			
		}
		
		else{
			if(!repeat.isSelected()){
				endDate.setValue(startDate.getValue());
			}
			try {
				course.addLectures(startTime.getText(), startDate.getValue().toString(), endDate.getValue().toString(), repeat.isSelected(), prof);
				mainController.getInstance().setCourse(new Course(course.getCourseCode()) );
			} catch (SQLException e) {
				System.out.println("Lecture already exists for this date and time" + e.getMessage());
			}
		}
	}
	
	public boolean validate(final String time){
	  matcher = pattern.matcher(time);
	  return matcher.matches();
	}
	
	
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		pattern = Pattern.compile(TIME24HOURS_PATTERN);
		ArrayList<String> lectureInfo = new ArrayList<String>(Arrays.asList("03.04.17 08:00 04.04.17", ("03.04.17 08:00 04.04.17")));
		listView.getItems().clear();
		listView.getItems().addAll(lectureInfo);
		
		
		repeat.selectedProperty().addListener(new ChangeListener<Boolean>() {
		    @Override
		    public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
		    	endDate.setDisable(oldValue);
		    	endDate.setValue(startDate.getValue());
		    }
		});
	}

}
