package gui;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
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
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
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
	public Button removeLectures;
	
	@FXML
	public TextField startTime;
	
	@FXML
	public DatePicker startDate;
	public DatePicker endDate;
	public DatePicker removeStart;
	public DatePicker removeEnd;
	
	@FXML 
	public CheckBox repeat;
	
	@FXML
	public Rectangle errorBar;
	
	@FXML
	public Text errorMessage;

	@FXML
	public ListView<String> listView;
	
	Course course = mainController.getInstance().getCourse();
	String courseCode = course.getCourseCode();
	String prof = mainController.getInstance().getProfessor().getUsername();
	DBController dbc = new DBController(); 
	
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
		if (startDate.getValue() == null || startTime.getText().length() == 0 
				|| !validate(startTime.getText()) || (repeat.isSelected() && endDate.getValue() == null)){
			System.out.println("Feil");
			String errorText = "";
			if (startDate.getValue() == null){
				System.out.println("Skriv inn dato");
				errorText += "Pick a date. ";
			}
			if  (startTime.getText().length() == 0){
				errorText += "Pick a time. ";
			}
			else if (!validate(startTime.getText())){
				System.out.println("Feil klokkeslettformat");
				errorText += "Write time on format hh:mm. ";
			}
			if (repeat.isSelected() && endDate.getValue() == null){
				errorText += "Select an end date or uncheck weekly repetition. ";
			}
			errorBar.setFill(Color.RED);
			errorMessage.setText(errorText);
			errorBar.setVisible(true);
			
		}
		
		else{
			if(!repeat.isSelected()){
				endDate.setValue(startDate.getValue());
			}
			try {
				course.addLectures(startTime.getText(), startDate.getValue().toString(), endDate.getValue().toString(), repeat.isSelected(), prof);
				mainController.getInstance().setCourse(new Course(courseCode));
				listView.getItems().clear();
				listView.getItems().addAll(dbc.getLectureDateAndTimeForCourse(courseCode));
				errorBar.setFill(Color.PALEGREEN);
				errorMessage.setText("Lecture successfully added");
				errorBar.setVisible(true);
			} catch (SQLException e) {
				System.out.println("Lecture already exists for this date and time" + e.getMessage());
				errorBar.setFill(Color.RED);
				errorBar.setVisible(true);
				errorMessage.setText("Lecture already exists for this date and time");
			}
		}
	}
	
	@FXML
	public void removeLecturesInPeriod(ActionEvent e){
		if (removeStart.getValue() == null || removeEnd.getValue() == null){
			errorMessage.setText("Select a period");
			errorBar.setFill(Color.RED);
			errorBar.setVisible(true);
			
		}
		else{
			dbc.deleteLecturesForPeriod(courseCode, removeStart.getValue().toString(), removeEnd.getValue().toString());
			errorMessage.setText("Lectures successfully deleted");
			errorBar.setFill(Color.PALEGREEN);
			errorBar.setVisible(true);
			listView.getItems().clear();
			listView.getItems().addAll(dbc.getLectureDateAndTimeForCourse(courseCode));
		}
	}
	
	@FXML
	public void deleteLectureAction(ActionEvent e){
		String row = listView.getSelectionModel().getSelectedItem();
		String[] rowArr = row.split("\t \t");
		SimpleDateFormat fromUser = new SimpleDateFormat("dd.MM.yyyy");
		SimpleDateFormat myFormat = new SimpleDateFormat("yyyy-MM-dd");
		try {
			rowArr[0] = myFormat.format(fromUser.parse(rowArr[0]));
		} catch (ParseException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		ArrayList<String> dateTime = new ArrayList<String>();
		dateTime.addAll(Arrays.asList(rowArr));
		dbc.deleteLecture(dbc.getLectureID(dateTime, courseCode));
		listView.getItems().clear();
		listView.getItems().addAll(dbc.getLectureDateAndTimeForCourse(courseCode));
		errorBar.setFill(Color.PALEGREEN);
		errorMessage.setText("Lecture successfully deleted");
		errorBar.setVisible(true);
		
	}
	
	public boolean validate(final String time){
	  matcher = pattern.matcher(time);
	  return matcher.matches();
	}
	
	
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		pattern = Pattern.compile(TIME24HOURS_PATTERN);
		ArrayList<String> lectures = dbc.getLectureDateAndTimeForCourse(courseCode);
		listView.getItems().clear();
		listView.getItems().addAll(lectures);
		
		
		repeat.selectedProperty().addListener(new ChangeListener<Boolean>() {
		    @Override
		    public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
		    	endDate.setDisable(oldValue);
		    	endDate.setValue(startDate.getValue());
		    }
		});
	}

}
