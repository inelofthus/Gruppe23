package gui;

import java.io.IOException;
import java.net.URL;
import java.text.DateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import databaseobjects.Lecture;

public class lectureProfController implements Initializable {

	
	@FXML
	public Button lecture1;
	public Button lecture2;
	public Button home;
	public Button logout;
	public Button exit;
	
	
	public void loadLecture(Lecture lecture) {
		mainController.getInstance().setLecture(lecture);
	}
	
	
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
			loadNextScene(home, stage, "courseProf.fxml");
		}
		if (event.getSource() == logout) {
			loadNextScene(logout, stage, "login.fxml");
		}
	}
	
	
	@FXML
	private void handleButtonAction(ActionEvent event) throws IOException{
		Stage stage = null;
		userButtons(event, stage);
		
		int numberOfLectures = mainController.getInstance().getLastTwoLecturesProfessor().size();
		
		if (numberOfLectures==0) {
			return;
		}
		else if(event.getSource()==lecture1 && numberOfLectures > 0){
			mainController.getInstance().setChosenProfessorLecture(getKeyLec1());
			loadNextScene(lecture1, stage, "individualCharts.fxml");
	    }
	    else if(event.getSource() == lecture2 && numberOfLectures > 1){
	    	mainController.getInstance().setChosenProfessorLecture(getKeyLec2());
	    	loadNextScene(lecture2, stage, "individualCharts.fxml");
	    }
	} 
	   
	
	
	private int getKeyLec1() {
		// helper method that returns the lectureID of the second lecture of lastTwoLectures		
		LinkedHashMap<Integer, GregorianCalendar> map = mainController.getInstance().getLastTwoLecturesProfessor();
		Iterator<Integer> entries = map.keySet().iterator();
		return entries.next();
	}
	
	private int getKeyLec2() {
		// helper method that returns the lectureID of the second lecture of lastTwoLectures		
		LinkedHashMap<Integer, GregorianCalendar> map = mainController.getInstance().getLastTwoLecturesProfessor();
		Iterator<Integer> entries = map.keySet().iterator();
		entries.next();
		return entries.next();
		
		
	}



	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// Set text of buttons to contain date of lecture
		// fagButton.setText(courseCodeName);
		
		//check how many lectures have been completed in the specific course and store them in an int
		int numberOfLectures = mainController.getInstance().getCourse().getLectureIDs().size();
		if (numberOfLectures==0) {
			//textfield.setText("You have not had any lectures in this course");
			return;
		}
		else if (numberOfLectures<2) {
			lecture1.setText(getLectureDateText(getKeyLec1()));			
		}
		else{
			lecture1.setText(getLectureDateText(getKeyLec1()));
			lecture2.setText(getLectureDateText(getKeyLec2()));
		}
	}



	private String getLectureDateText(int lecID) {
		// TODO Auto-generated method stub
		String date = "";
		
		
		try {
		GregorianCalendar gc = mainController.getInstance().getCourse().getLectureDate(lecID);
		date = DateFormat.getDateInstance(DateFormat.SHORT).format(gc.getTime());
		System.out.println(date);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
				
		return date;
	}
	
	
	

}
