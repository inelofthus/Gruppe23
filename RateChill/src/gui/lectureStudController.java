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

public class lectureStudController implements Initializable {

	
	@FXML
	Button lecture1;
	@FXML
	Button lecture2;
	
	public void loadNextScene(Stage stage) throws IOException{
		Parent root;
		root = FXMLLoader.load(getClass().getResource("evaluationStud.fxml"));
		
		//create a new scene with root and set the stage
		Scene scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
		
	}
	
	@FXML
	private void handleButtonAction(ActionEvent event) throws IOException{
		Stage stage = null;
		
	    if(event.getSource()==lecture1){
	    	//get reference to the button's stage         
	        stage=(Stage) lecture1.getScene().getWindow();
	        mainController.getInstance().setChosenStudentLecture(getKeyLec1());
	        loadNextScene(stage);
	    }
	    else {
	    	stage=(Stage) lecture2.getScene().getWindow();
	    	mainController.getInstance().setChosenStudentLecture(getKeyLec2());
	    	loadNextScene(stage);
	    }
	    
	} 
	   
	
	
	private int getKeyLec1() {
		// helper method that returns the lectureID of the second lecture of lastTwoLectures		
		LinkedHashMap<Integer, GregorianCalendar> map = mainController.getInstance().getLastTwoLecturesStudent();
		Iterator<Integer> entries = map.keySet().iterator();
		return entries.next();
	}
	
	private int getKeyLec2() {
		// helper method that returns the lectureID of the second lecture of lastTwoLectures		
		LinkedHashMap<Integer, GregorianCalendar> map = mainController.getInstance().getLastTwoLecturesStudent();
		Iterator<Integer> entries = map.keySet().iterator();
		entries.next();
		return entries.next();
		
		
	}



	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// Set text of buttons to contain date of lecture
		// fagButton.setText(courseCodeName);
		int numberOfLectures = mainController.getInstance().getLastTwoLecturesStudent().size();
		if (numberOfLectures==0) {
			//textfield.setText("You have not had any lectures yet");
			return;
		}
		else if(numberOfLectures<2) {
			lecture1.setText(getLectureDateText(getKeyLec1()));
		}
		else {
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
