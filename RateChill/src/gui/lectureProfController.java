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
	Button lecture1;
	@FXML
	Button lecture2;
	
	
	public void loadLecture(Lecture lecture) {
		mainController.getInstance().setLecture(lecture);
	}
	
	public void loadNextScene(Stage stage) throws IOException{
		Parent root;
		root = FXMLLoader.load(getClass().getResource("individualCharts.fxml"));
	    
	    //create a new scene with root and set the stage
		Scene scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
	}
	
	@FXML
	private void handleButtonAction(ActionEvent event) throws IOException{
		Stage stage = null;
		
		int numberOfLectures = mainController.getInstance().getLastTwoLecturesProfessor().size();
		
		if (numberOfLectures<1) {
			return;
		}
		
		else if(event.getSource()==lecture1 && numberOfLectures > 0){
			
			mainController.getInstance().setChosenProfessorLecture(getKeyLec1());
			//get reference to the button's stage
			stage=(Stage) lecture1.getScene().getWindow();
			loadNextScene(stage);
	    }
	    else if(event.getSource() == lecture2 && numberOfLectures > 1){
	    	mainController.getInstance().setChosenProfessorLecture(getKeyLec2());

	    	stage=(Stage) lecture2.getScene().getWindow();
	    	loadNextScene(stage);
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
		
		//here i want to check how many lectures have been completed in the specific course and store them in an int
		//int numberOfLectures = mainController.getInstance().getProfessor().getLastTwoCompletedLecturesForCourse(courseCode);
		//int numberOfLectures = mainController.getInstance().getProfessor().getCourses().size();
		//int numberOfLectures = mainController.getInstance().getProfessor().getCourseID().getLectures().size();
		int numberOfLectures = mainController.getInstance().getLastTwoLecturesProfessor().size();
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
