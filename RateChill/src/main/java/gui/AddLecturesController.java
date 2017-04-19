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

//import GUItest.DummyMainController;
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
//import testingDBObjects.DummyDBController;

public class AddLecturesController extends CommonMethods implements Initializable {

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
	
	MainController mainCon = MainController.getInstance();
	Course course = mainCon.getCourse();
	String courseCode = course.getCourseCode();
	String prof = mainCon.getProfessor().getUsername();
	DBController dbc = new DBController(); 
	
	private Pattern pattern;
	private Matcher matcher;
	
	private static final String TIME24HOURS_PATTERN =
	           "([01]?[0-9]|2[0-3]):[0-5][0-9]";
	
	Color myRed = new Color(0.937, 0.290, 0.290, 1);


	public void userButtons(ActionEvent event, Stage stage) throws IOException {
		if (event.getSource() == home) {
			loadNextScene(home, stage, "CourseProf.fxml");
		}
		if (event.getSource() == back) {
			loadNextScene(back, stage, "LectureProf.fxml");
			//loadNextScene(back, stage, mainController.getInstance().getStack().pop());
		}
		if (event.getSource() == logout) {
			loadNextScene(logout, stage, "LoginProf.fxml");
		}
		if (event.getSource() == finish) {
			loadNextScene(logout, stage, "LectureProf.fxml");
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
				|| !validate(startTime.getText()) || (repeat.isSelected() && endDate.getValue() == null) || startDate.getValue().isAfter( endDate.getValue())){
			String errorText = "";
			if (startDate.getValue() == null){
				errorText += "Pick a date (dd.mm.yyyy). ";
			}
			if (startDate.getValue().isAfter(endDate.getValue())){
				errorText += "Start date must be before end date. ";
			}
			if  (startTime.getText().length() == 0){
				errorText += "Pick a time. ";
			}
			else if (!validate(startTime.getText())){
				errorText += "Write time on format hh:mm. ";
			}
			if (repeat.isSelected() && endDate.getValue() == null){
				errorText += "Select an end date or uncheck weekly repetition. ";
			}
			errorBar.setFill(myRed);
			errorMessage.setText(errorText);
			errorBar.setVisible(true);
			
		}
		
		else{
			if(!repeat.isSelected()){
				endDate.setValue(startDate.getValue());
			}
			try {
				course.addLectures(startTime.getText(), startDate.getValue().toString(), endDate.getValue().toString(), repeat.isSelected(), prof);
				MainController.getInstance().setCourse(new Course(courseCode));
				listView.getItems().clear();
				listView.getItems().addAll(dbc.getLectureDateAndTimeForCourse(courseCode));
				errorBar.setFill(Color.DARKSEAGREEN);
				if (repeat.isSelected()){
					errorMessage.setText("Lectures successfully added");
				}else{
					errorMessage.setText("Lecture successfully added");
				}
				errorBar.setVisible(true);
			} catch (SQLException e) {
				System.out.println("Lecture already exists for this date and time" + e.getMessage());
				errorBar.setFill(myRed);
				errorBar.setVisible(true);
				errorMessage.setText("Lecture already exists for this date and time");
			}
		}
	}
	
	@FXML
	public void removeLecturesInPeriod(ActionEvent e){
		if (removeStart.getValue() == null || removeEnd.getValue() == null){
			errorMessage.setText("Select a period");
			errorBar.setFill(myRed);
			errorBar.setVisible(true);
			
		} else if (removeStart.getValue().isAfter( removeEnd.getValue())){
			errorMessage.setText("Start date must be before end date. ");
			errorBar.setFill(myRed);
			errorBar.setVisible(true);
		}
		else{
			dbc.deleteLecturesForPeriod(courseCode, removeStart.getValue().toString(), removeEnd.getValue().toString());
			errorMessage.setText("Lectures successfully deleted");
			errorBar.setFill(Color.DARKSEAGREEN);
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
		errorBar.setFill(Color.DARKSEAGREEN);
		errorMessage.setText("Lecture successfully deleted");
		errorBar.setVisible(true);
		
	}
	
	public boolean validate(final String time){
	  matcher = pattern.matcher(time);
	  return matcher.matches();
	}
	
	public AddLecturesController() {

	}
	
	//Constructor for testing
	/*public addLecturesController(String str) {
	// dummyDBC is only for testing. It does not access the database
		this.dbc = new DummyDBController();
		this.mainCon = DummyMainController().getInstance();
	}
	*/
	
	
	
	
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
