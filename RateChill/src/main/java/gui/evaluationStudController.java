package gui;


import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import databaseobjects.Course;
import databaseobjects.Evaluation;
import databaseobjects.Student;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class evaluationStudController implements Initializable {

	//fxml objects
	@FXML
	public TextArea feedback;
	public ToggleButton rating1;
	public ToggleButton rating2;	
	public ToggleButton rating3;
	public ToggleButton rating4;
	public ToggleButton rating5;	
	public ToggleGroup group;
	public Button submit;
	public Text debugText;
	public Text submitted;
	public Text overwriteText;
	public Text welcomeText;
	public Button home;
	public Button back;
	public Button logout;
	public Button exit;
	public Rectangle rec;
	
	Student stud = mainController.getInstance().getStudents();
	Course course = mainController.getInstance().getCourse();
	Integer lectureID = mainController.getInstance().getChosenStudentLecture();
	
	//Creates an evaluation that can be inserted into the database
	private void createEvaluation(int lecID, String rating, String comment){
		mainController.getInstance().getStudents().giveEvaluation(lecID, rating, comment);
	}
	
	private void overwriting(int lecID, String rating, String comment) {
		mainController.getInstance().getStudents().overWriteEvaluation(lecID, rating, comment);
	}
	
	public void handleKeyActionStud(KeyEvent ke) throws IOException{
		if(ke.getCode().equals(KeyCode.ENTER)){
			submitButton();
		}
	}
	
	
	private void submitButton() {
		Stage stage = null;
		welcomeText.setVisible(false);
		
		//checks if something is selected and gives error message
		if (!(rating4.isSelected() || rating5.isSelected() || rating3.isSelected() || rating2.isSelected()
				|| rating1.isSelected())) {
			overwriteText.setText("Choose a rating");
			submitted.setText("");
			rec.setFill(Color.RED);
			debugText.setText("");
			return;
		}
		
		
		//if the user already has submitted one or more evaluations on the subject, it gets overwritten
		if (mainController.getInstance().getStudents().hasEvaluatedLecture(lectureID)) {
			
			
			String comment = feedback.getText();
			
			//the actual overwriting function
			overwriting(lectureID, selectedButton(), comment);
			
			//removing other messages the GUI displays and setting the overwritten text
			debugText.setText("");
			submitted.setText("");
			rec.setFill(Color.BLUE);
			String overwritten = "Your submission has been overwritten";
			overwriteText.setText(overwritten);
			return;
		}
		//checks which rating-button is selected and sets the rating to the selected value
		//stores the feedback comment
		String comment = feedback.getText();
		
		//insertEvaluation into database
		createEvaluation(lectureID, selectedButton(), comment);
		
		//visual confirmation that the evaluation has been submitted
		rec.setFill(Color.GREEN);
		debugText.setText("");
		submitted.setText("Submitted!");
		
		//sets the text on the submit-button to overwrite
		submit.setText("Overwrite");
		
		
	}

	private String selectedButton() {
		String rating = "";
		if (rating4.isSelected()){
			rating = rating4.getText();
		}
		else if (rating5.isSelected()){
			rating += rating5.getText();
		}
		else if (rating3.isSelected()){
			rating += rating3.getText();
		}
		else if (rating2.isSelected()){
			rating += rating2.getText();
		}
		else if (rating1.isSelected()){
			rating += rating1.getText();
		}
		return rating;
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
		if(event.getSource() == home) {
			loadNextScene(home, stage, "courseStud.fxml");
		}
		
		if (event.getSource() == back) {
			loadNextScene(back ,stage, "lectureStud.fxml");
		}
		
		if (event.getSource() == logout) {
			loadNextScene(logout, stage, "login.fxml");
		}
	}
	
	@FXML
	private void handleButtonAction(ActionEvent event) throws IOException{
		Stage stage = null;
		userButtons(event, stage);
		welcomeText.setVisible(false);
		
		if (event.getSource() == submit){
			
			submitButton();
			

			}
			

		
	}
	//
	
	
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method
		rec.setFill(Color.TRANSPARENT);
		debugText.setText("");
		if(!mainController.getInstance().getStudents().hasEvaluatedLecture(lectureID)) {
			submit.setText("Submit");
			overwriteText.setText("");
		}
		if (mainController.getInstance().getStudents().hasEvaluatedLecture(lectureID)) {
			
			Evaluation eval = new Evaluation(lectureID, stud.getUsername());
			feedback.setText(eval.getComment());
			
			submit.setText("Overwrite");
			rec.setFill(Color.BLUE);
			welcomeText.setVisible(false);
			overwriteText.setText("Evaluation already given, overwrite?");
		}
		ArrayList<String> ratingValues = course.getRatingValues();
		rating1.setText(ratingValues.get(0));
		rating2.setText(ratingValues.get(1));
		rating3.setText(ratingValues.get(2));
		rating4.setText(ratingValues.get(3));
		rating5.setText(ratingValues.get(4));
	}

}
