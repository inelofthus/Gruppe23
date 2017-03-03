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

public class lectureProfController implements Initializable {

	
	@FXML
	Button lecture1;
	@FXML
	Button lecture2;
	
	@FXML
	private void handleButtonAction(ActionEvent event) throws IOException{
		Stage stage; 
	    Parent root;
	    if(event.getSource()==lecture1){
	    	//get reference to the button's stage         
	        stage=(Stage) lecture1.getScene().getWindow();
	        mainController.getInstance().setChosenProfessorLecture(getKeyLec1());
	        
	        //here we want to create a 
	    }
	    else {
	    	stage=(Stage) lecture2.getScene().getWindow();
	    	mainController.getInstance().setChosenProfessorLecture(getKeyLec2());
	    }
	    
	    
	    
	    //load up OTHER FXML document
		root = FXMLLoader.load(getClass().getResource("evaluationProf.fxml"));
	    
	    //create a new scene with root and set the stage
		Scene scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
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
		lecture1.setText(getLectureDateText(getKeyLec1()));
		//lecture2.setText(getLectureDateText(getKeyLec2()));
		
		
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
