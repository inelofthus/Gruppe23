package gui;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.ResourceBundle;
import java.util.Stack;

import database.DBController;
import databaseobjects.Course;
import databaseobjects.Lecture;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.ListView;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class lectureProfController implements Initializable {

	
	@FXML
	public Button indivLecture;
	public Button allLectures;
	public Button customize;
	public Button editLectures;
	
	public Button home;
	public Button back;
	public Button logout;
	
	@FXML
	public Text month;
	public Hyperlink prevMonth;
	public Hyperlink nextMonth;
	public Hyperlink month1;
	public Hyperlink month2;
	public Hyperlink month3;
	public Hyperlink month4;
	public Hyperlink month5;
	public Hyperlink month6;
	public ListView<String> lectures;
	
	private boolean spring = true;
	private Course course;
	private int monthNum;
	private ArrayList<String> thisMonthsLectures;
	private Stack<String> stack = null;
	private DBController DBC = new DBController();
	
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
		if (event.getSource() == back) {
			loadNextScene(back, stage, "courseProf.fxml");
		}
		if (event.getSource() == logout) {
			loadNextScene(logout, stage, "login.fxml");
		}
	}
	
	
	@FXML
	private void handleButtonAction(ActionEvent event) throws IOException{
		Stage stage = null;
		userButtons(event, stage);
		
		if(event.getSource() == indivLecture){
			int lecID = course.getLectureIDsMonth(monthNum).get(lectures.getSelectionModel().getSelectedIndex()); 
			Lecture lec = new Lecture(lecID);
			loadLecture(lec);
			mainController.getInstance().setChosenProfessorLecture(lecID);
			stack.push("lectureProf.fxml");
			loadNextScene(indivLecture, stage, "individualCharts.fxml");
		}
		
		else if(event.getSource() == allLectures){
			System.out.println("Button pressed");
			stack.push("lectureProf.fxml");
			loadNextScene(allLectures, stage, "evaluationsOverTime.fxml");
		}
		
		else if (event.getSource() == customize) {
			stack.push("lectureProf.fxml");
			loadNextScene(customize, stage, "customizeButtons.fxml");
		}
		else if (event.getSource() == editLectures) {
			stack.push("lectureProf.fxml");
			loadNextScene(editLectures, stage, "addLectures.fxml");
		}
	}
	
	@FXML 
	private void handleHyperLinkAction(ActionEvent event) throws IOException{
		
		if(event.getSource() == nextMonth){
			if(monthNum != 6 && monthNum != 12){
	    		monthNum ++;
	    		updateMonth(monthNum);
	    	}
		}		if(event.getSource() == prevMonth){
			if(monthNum != 1 && monthNum != 7){
	    		monthNum --;
	    		updateMonth(monthNum);
	    	}
		}		if(event.getSource() == month1){
	    	if(monthNum != 1 && monthNum != 7){
	    		if(spring) monthNum = 1;
	    		else monthNum = 7;
	    		updateMonth(monthNum);
	    	}
	    }if(event.getSource() == month2){
	    	if(monthNum != 2 && monthNum != 8){
	    		if(spring) monthNum = 2;
	    		else monthNum = 8;
	    		updateMonth(monthNum);
	    	}
	    }if(event.getSource() == month3){
	    	if(monthNum != 3 && monthNum != 9){
	    		if(spring) monthNum = 3;
	    		else monthNum = 9;
	    		updateMonth(monthNum);
	    	}
	    }if(event.getSource() == month4){
	    	if(monthNum != 4 && monthNum != 10){
	    		if(spring) monthNum = 4;
	    		else monthNum = 10;
	    		updateMonth(monthNum);
	    	}
	    }if(event.getSource() == month5){
	    	if(monthNum != 5 && monthNum != 11){
	    		if(spring) monthNum = 5;
	    		else monthNum = 11;
	    		updateMonth(monthNum);
	    	}
	    }if(event.getSource() == month6){
	    	if(monthNum != 6 && monthNum != 12){
	    		if(spring) monthNum = 6;
	    		else monthNum = 12;
	    		updateMonth(monthNum);
	    	}
	    }
	}
	   

	private void updateMonth(int monthNum2) {
		// update this months lectures
		// change text in listview and month title
		
		month.setText(getMonthText(monthNum));
		ArrayList<Integer> lecIDs = course.getLectureIDsMonth(monthNum);
		thisMonthsLectures = getLectureDates(lecIDs);
		lectures.getItems().clear();
		lectures.getItems().addAll(thisMonthsLectures);
		
	}


	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// Set text of buttons to contain date of lecture
		this.course = mainController.getInstance().getCourse();
		this.monthNum = Calendar.getInstance().get(Calendar.MONTH) + 1;
		
		//initializing stack for back-buttons to come
		this.stack = mainController.getInstance().getStack();
		
		//resetting the stack
		if(!stack.isEmpty()) {
			stack.clear();
		}
		
		month.setText(getMonthText(monthNum));
		
		if(monthNum > 6){
			month1.setText("Jul");
			month2.setText("Aug");
			month3.setText("Sep");
			month4.setText("Oct");
			month5.setText("Nov");
			month6.setText("Des");
			spring = false;
		}
		if(course.getLectureIDsMonth(monthNum).isEmpty()){
			course.setLectureByMonth();
		}
		
		ArrayList<Integer> lecIDs = course.getLectureIDsMonth(monthNum);
		thisMonthsLectures = getLectureDates(lecIDs);
		lectures.getItems().clear();
		lectures.getItems().addAll(thisMonthsLectures);
		
		
	}

	private String getMonthText(int monthNum) {
		 String monthString;
	        switch (monthNum) {
	            case 1:  monthString = "January";
	                     break;
	            case 2:  monthString = "February";
	                     break;
	            case 3:  monthString = "March";
	                     break;
	            case 4:  monthString = "April";
	                     break;
	            case 5:  monthString = "May";
	                     break;
	            case 6:  monthString = "June";
	                     break;
	            case 7:  monthString = "July";
	                     break;
	            case 8:  monthString = "August";
	                     break;
	            case 9:  monthString = "September";
	                     break;
	            case 10: monthString = "October";
	                     break;
	            case 11: monthString = "November";
	                     break;
	            case 12: monthString = "December";
	                     break;
	            default: monthString = "Invalid month";
	                     break;
	        }
	        
	      return monthString;
	}

	private ArrayList<String> getLectureDates(ArrayList<Integer> lecIDs) {
		ArrayList<String> date = new ArrayList<>();
		
		for(int lecID:lecIDs){
			date.add(DBC.changeDateFormat( course.getLectureDate(lecID) ) ) ;
		}
		
				
		return date;
	}
	
	
	

}
