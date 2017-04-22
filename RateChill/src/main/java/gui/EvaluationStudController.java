package gui;


import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
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
import javafx.scene.control.Hyperlink;
import javafx.scene.control.TextArea;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class EvaluationStudController extends CommonMethods implements Initializable {

	//fxml objects
	@FXML
	public TextArea evaluationComment;
	public ToggleButton rating1;
	public ToggleButton rating2;	
	public ToggleButton rating3;
	public ToggleButton rating4;
	public ToggleButton rating5;	
	public ToggleGroup group;
	public Button submit;
	public Text errorText;
	public Button home;
	public Button back;
	public Button logout;
	public Button exit;
	public Rectangle rec;
	public Hyperlink enableOverwrite;
	public Hyperlink overwriteBack;
	
	Student stud = MainController.getInstance().getStudents();
	Course course = MainController.getInstance().getCourse();
	Integer lectureID = MainController.getInstance().getChosenStudentLecture();
	
	//Creates an evaluation that can be inserted into the database
	private void createEvaluation(int lecID, String rating, String comment){
		MainController.getInstance().getStudents().giveEvaluation(lecID, rating, comment);
	}
	
	private void overwriting(int lecID, String rating, String comment) {
		MainController.getInstance().getStudents().overWriteEvaluation(lecID, rating, comment);
	}
	
	public void handleKeyActionStud(KeyEvent ke) throws IOException{
		if(ke.getCode().equals(KeyCode.ENTER)){
			submitButton();
		}
	}
	
	public void enableOverwriteAction(ActionEvent event){
		rating1.setDisable(false);
		rating2.setDisable(false);
		rating3.setDisable(false);
		rating4.setDisable(false);
		rating5.setDisable(false);
		evaluationComment.setDisable(false);
		submit.setDisable(false);
		enableOverwrite.setVisible(false);
		overwriteBack.setVisible(false);
		errorText.setText("Give a new evluation or press back to cancel. ");	
	}
	
	private void submitButton() {
		//checks if something is selected and gives error message
		if (!(rating4.isSelected() || rating5.isSelected() || rating3.isSelected() || rating2.isSelected()
				|| rating1.isSelected())) {
			errorText.setText("Choose a rating");
			Color myRed = new Color(0.937, 0.290, 0.290, 1);
			rec.setFill(myRed);
			return;
		}
		//if the user already has submitted one or more evaluations on the subject, it gets overwritten
		if (MainController.getInstance().getStudents().hasEvaluatedLecture(lectureID)) {
			String comment = evaluationComment.getText();
			overwriting(lectureID, selectedButton(), comment); //the actual overwriting function
			rec.setFill(Color.DARKSEAGREEN);
			String overwritten = "Your submission has been overwritten";
			errorText.setText(overwritten);
			return;
		}
		String comment = evaluationComment.getText(); //stores the evaluationComment comment
		createEvaluation(lectureID, selectedButton(), comment); //insertEvaluation into database
		
		//confirmation that the evaluation has been submitted
		rec.setFill(Color.DARKSEAGREEN);
		errorText.setText("Submitted!");
		
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
	
	
	public void userButtons(ActionEvent event, Stage stage) throws IOException{
		if(event.getSource() == home) {
			loadNextScene(home,  "CourseStud.fxml");
		}
		if (event.getSource() == back || event.getSource() == overwriteBack) {
			loadNextScene(back , "LectureStud.fxml");
		}
		if (event.getSource() == logout) {
			loadNextScene(logout,  "Login.fxml");
		}
	}
	
	
	@FXML
	private void handleButtonAction(ActionEvent event) throws IOException{
		Stage stage = null;
		userButtons(event, stage);
		if (event.getSource() == submit){
			submitButton();
		}		
	}
	
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		ArrayList<ToggleButton> buttons = new ArrayList<ToggleButton>(Arrays.asList(rating1,rating2,rating3,rating4,rating5));
		ArrayList<String> ratingValues = course.getRatingValues();
		for(int i =0;i<5;i++) {
			buttons.get(i).setText(ratingValues.get(i));
		}
		rec.setFill(Color.TRANSPARENT);
		if(!MainController.getInstance().getStudents().hasEvaluatedLecture(lectureID)) {
			submit.setText("Submit");
		}
		if (MainController.getInstance().getStudents().hasEvaluatedLecture(lectureID)) {
			rating1.setDisable(true);
			rating2.setDisable(true);
			rating3.setDisable(true);
			rating4.setDisable(true);
			rating5.setDisable(true);
			evaluationComment.setDisable(true);
			submit.setDisable(true);
			enableOverwrite.setVisible(true);
			overwriteBack.setVisible(true);
			
			Evaluation eval = new Evaluation(lectureID, stud.getUsername());
			evaluationComment.setText(eval.getComment());
			String lastRating = eval.getRating();
			for (int i = 0; i < 5; i++) {
				if (lastRating.equalsIgnoreCase(ratingValues.get(i))) {
					buttons.get(i).setSelected(true);
				}
			}
			submit.setText("Overwrite");
			rec.setFill(Color.LIGHTGOLDENRODYELLOW);
			errorText.setText("Evaluation already given, overwrite?");
		}
	}

}
