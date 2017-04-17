package gui;

import java.io.IOException;
import java.net.URL;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.ResourceBundle;

import database.DBController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class LectureStudController implements Initializable {

	
	@FXML
	public Button lecture1;
	public Button lecture2;
	public Button home;
	public Button back;
	public Button logout;
	public Text errorMsg;
	
	private DBController DBC = new DBController();
	
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
		if(event.getSource() == home) {
			loadNextScene(home, stage, "CourseStud.fxml");
		}
		if (event.getSource() == back) {
			loadNextScene(back, stage, "CourseStud.fxml");
		}
		if (event.getSource() == logout) {
			loadNextScene(logout, stage, "Login.fxml");
		}
	}
	
	
	@FXML
	private void handleButtonAction(ActionEvent event) throws IOException{
		Stage stage = null;
		userButtons(event, stage);
		
		int numberOfLectures = MainController.getInstance().getCourse().getLectureIDs().size();
		
		if (numberOfLectures==0) {
			
			return;
		}
		else if(event.getSource()==lecture1 && numberOfLectures>0){
	    	//get reference to the button's stage         
	        MainController.getInstance().setChosenStudentLecture(getKeyLec1());
	        loadNextScene(lecture1, stage, "EvaluationStud.fxml");
	    }
	    else if(event.getSource() == lecture2 && numberOfLectures>1){
	    	MainController.getInstance().setChosenStudentLecture(getKeyLec2());
	    	loadNextScene(lecture2, stage, "EvaluationStud.fxml");
	    } 
	} 
	
	public void handleKeyAction(KeyEvent ke) throws IOException{
		Stage stage = null;
		if(ke.getCode().equals(KeyCode.ENTER)){
			if (lecture1.isFocused()){
				MainController.getInstance().setChosenStudentLecture(getKeyLec1());
				loadNextScene(lecture1, stage, "EvaluationStud.fxml");
			}
			else if (lecture2.isFocused()) {
				MainController.getInstance().setChosenStudentLecture(getKeyLec2());
				loadNextScene(lecture2, stage, "EvaluationStud.fxml");
			}
		}
	}
	   
	private int getKeyLec2() {
		// helper method that returns the lectureID of the first lecture of lastTwoLectures		
		LinkedHashMap<Integer, ArrayList<String>> map = MainController.getInstance().getLastTwoLecturesStudent();
		Iterator<Integer> entries = map.keySet().iterator();
		return entries.next();
	}
	
	private int getKeyLec1() {
		// helper method that returns the lectureID of the second lecture of lastTwoLectures		
		LinkedHashMap<Integer, ArrayList<String>> map = MainController.getInstance().getLastTwoLecturesStudent();
		Iterator<Integer> entries = map.keySet().iterator();
		entries.next();
		return entries.next();
		
		
	}



	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// Set text of buttons to contain date of lecture
		int numberOfLectures = MainController.getInstance().getCourse().getLastTwoCompletedLectureIDs().size();
		if (numberOfLectures==0) {
			errorMsg.setText("No completed lectures have been registered for this course yet");
			lecture1.setVisible(false);
			lecture2.setVisible(false);
			return;
		}
		else if(numberOfLectures<2) {
			lecture1.setText(DBC.changeDateFormat(getLectureDateText(getKeyLec1()))  );
		}
		else {
			lecture1.setText( DBC.changeDateFormat(getLectureDateText(getKeyLec1())) );
			lecture2.setText( DBC.changeDateFormat(getLectureDateText(getKeyLec2())) );
			
		}
		
		
	}



	private String getLectureDateText(int lecID) {
		// TODO Auto-generated method stub
		String date = "";
		
		try {
		date = MainController.getInstance().getCourse().getLectureDate(lecID);
		System.out.println(date);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
				
		return date;
	}
	
	
	

}
