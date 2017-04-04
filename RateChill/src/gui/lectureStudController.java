package gui;

import java.io.IOException;
import java.net.URL;
import java.text.DateFormat;
import java.util.ArrayList;
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
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class lectureStudController implements Initializable {

	
	@FXML
	public Button lecture1;
	public Button lecture2;
	public Button home;
	public Button back;
	public Button logout;
	public Text errorMsg;
	
	
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
			loadNextScene(home, stage, "courseStud.fxml");
		}
		if (event.getSource() == back) {
			loadNextScene(back, stage, "courseStud.fxml");
		}
		if (event.getSource() == logout) {
			loadNextScene(logout, stage, "login.fxml");
		}
	}
	
	
	@FXML
	private void handleButtonAction(ActionEvent event) throws IOException{
		Stage stage = null;
		userButtons(event, stage);
		
		int numberOfLectures = mainController.getInstance().getCourse().getLectureIDs().size();
		
		if (numberOfLectures==0) {
			
			return;
		}
		else if(event.getSource()==lecture1 && numberOfLectures>0){
	    	//get reference to the button's stage         
	        mainController.getInstance().setChosenStudentLecture(getKeyLec1());
	        loadNextScene(lecture1, stage, "evaluationStud.fxml");
	    }
	    else if(event.getSource() == lecture2 && numberOfLectures>1){
	    	mainController.getInstance().setChosenStudentLecture(getKeyLec2());
	    	loadNextScene(lecture2, stage, "evaluationStud.fxml");
	    }
	    
	} 
	   
	
	
	private int getKeyLec2() {
		// helper method that returns the lectureID of the first lecture of lastTwoLectures		
		LinkedHashMap<Integer, ArrayList<String>> map = mainController.getInstance().getLastTwoLecturesStudent();
		Iterator<Integer> entries = map.keySet().iterator();
		return entries.next();
	}
	
	private int getKeyLec1() {
		// helper method that returns the lectureID of the second lecture of lastTwoLectures		
		LinkedHashMap<Integer, ArrayList<String>> map = mainController.getInstance().getLastTwoLecturesStudent();
		Iterator<Integer> entries = map.keySet().iterator();
		entries.next();
		return entries.next();
		
		
	}



	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// Set text of buttons to contain date of lecture
		int numberOfLectures = mainController.getInstance().getCourse().getLectureIDs().size();
		if (numberOfLectures==0) {
			errorMsg.setText("No lectures registered for this course");
			lecture1.setVisible(false);
			lecture2.setVisible(false);
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
		date = mainController.getInstance().getCourse().getLectureDate(lecID);
		System.out.println(date);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
				
		return date;
	}
	
	
	

}
