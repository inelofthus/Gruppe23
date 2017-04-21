package gui;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.Stack;

import com.sun.javafx.scene.control.SelectedCellsMap;

import databaseobjects.Course;
import databaseobjects.Evaluation;
import databaseobjects.Lecture;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.ToggleButton;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class CommentPageController extends CommonMethods implements Initializable {

	//fxml objects
	@FXML
	public ToggleButton seeComments1;
	public ToggleButton seeComments2;
	public ToggleButton seeComments3;
	public ToggleButton seeComments4;
	public ToggleButton seeComments5;
	@FXML
	public Button home;
	public Button back;
	public Button logout;
	public Button customize;
	public Button customize1;
	public Button showAllComments;
	
	@FXML
	public TextArea textArea;
	public Text text = null;
	public VBox vBox;
	
    Integer lectureID = MainController.getInstance().getChosenProfessorLecture();
	
	private Lecture lecture = new Lecture(lectureID);
	private Course course = MainController.getInstance().getCourse();
	private ArrayList<String> ratingValues = lecture.getRatingValues();
	private Stack<String> stack = MainController.getInstance().getStack();
	
	
	public String createString() {
		ArrayList<String> als = selectedButtons();
		String allComment = ""; 
		for (String string:als){
			if (!string.equals("")) {
				allComment += string + "\n";	
			}
		}
		return allComment;
	}
	
	public String createStringAll() {
		ArrayList<String> als = showAllComments();
		String comments = ""; 
		for (String string:als){
			comments += string + "\n";
		}
		return comments;
	}	
	
	public void userButtons(ActionEvent event, Stage stage) throws IOException{
		if (event.getSource() == home) {
			loadNextScene(home, stage, "CourseProf.fxml");
		}
		if (event.getSource() == back) {
			loadNextScene(back, stage, stack.pop());
		}
		if (event.getSource() == logout) {
			loadNextScene(logout, stage, "Login.fxml");
		}
	}
	
	public boolean isUserButtonPushed(ActionEvent event) {
		if (event.getSource() == home || event.getSource() == back || event.getSource() == logout) {
			return true;
		}
		return false;
	}
	
	public boolean isCommentButtonsPushed() {
		if (seeComments1.isSelected() || seeComments2.isSelected() || seeComments3.isSelected() || seeComments4.isSelected() || seeComments5.isSelected()) {
			return true;
		}
		return false;
	}
	
	public ArrayList<String> selectedButtons() {
		ArrayList<String> currentEvaluations = new ArrayList<String>();
		if (seeComments1.isSelected()){
			currentEvaluations.add(ratingValues.get(0) + " comments:");
			ArrayList<Evaluation> Evaluations1 = lecture.getEvaluationsRating1();
			for (Evaluation eval:Evaluations1) {
				currentEvaluations.add(eval.getComment());
			}
			currentEvaluations.add("");
		}
		if (seeComments2.isSelected()){
			currentEvaluations.add(ratingValues.get(1) + " comments:");
			ArrayList<Evaluation> Evaluations2 = lecture.getEvaluationsRating2();
			for (Evaluation eval:Evaluations2) {
				currentEvaluations.add(eval.getComment());
			}
			currentEvaluations.add("");
		}
		if (seeComments3.isSelected()){
			currentEvaluations.add(ratingValues.get(2) + " comments:");
			ArrayList<Evaluation> Evaluations3 = lecture.getEvaluationsRating3();
			for (Evaluation eval:Evaluations3) {
				currentEvaluations.add(eval.getComment());
			}
			currentEvaluations.add("");
		}
		if (seeComments4.isSelected()){
			currentEvaluations.add(ratingValues.get(3) + " comments:");
			ArrayList<Evaluation> Evaluations4 = lecture.getEvaluationsRating4();
			for (Evaluation eval:Evaluations4) {
				currentEvaluations.add(eval.getComment());
			}
			currentEvaluations.add("");
		}
		if (seeComments5.isSelected()){
			currentEvaluations.add(ratingValues.get(4) + " comments:");
			ArrayList<Evaluation> Evaluations5 = lecture.getEvaluationsRating5();
			for (Evaluation eval:Evaluations5) {
				currentEvaluations.add(eval.getComment());
			}
			currentEvaluations.add("");
			
		}
		return currentEvaluations;
	}
	
	
	public ArrayList<String> showAllComments() {
		ArrayList<String> current = new ArrayList<String>();
		ArrayList<Evaluation> Evaluations1 = lecture.getEvaluationsRating1();
		ArrayList<Evaluation> Evaluations2 = lecture.getEvaluationsRating2();
		ArrayList<Evaluation> Evaluations3 = lecture.getEvaluationsRating3();
		ArrayList<Evaluation> Evaluations4 = lecture.getEvaluationsRating4();
		ArrayList<Evaluation> Evaluations5 = lecture.getEvaluationsRating5();
		if (!Evaluations1.isEmpty()) {
			current.add(ratingValues.get(0) + " comments:");
			for (Evaluation eval:Evaluations1) {
				current.add(eval.getComment());
			}
			current.add("");
		}
		if (!Evaluations2.isEmpty()) {
			current.add(ratingValues.get(1) + " comments:");
			for (Evaluation eval:Evaluations2) {
				current.add(eval.getComment());
			}
			current.add("");
		}
		if (!Evaluations3.isEmpty()) {
			current.add(ratingValues.get(2) + " comments:");
			for (Evaluation eval:Evaluations3) {
				current.add(eval.getComment());
			}
			current.add("");
		}
		if (!Evaluations4.isEmpty()) {
			current.add(ratingValues.get(3) + " comments:");
			for (Evaluation eval:Evaluations4) {
				current.add(eval.getComment());
			}
			current.add("");
		}
		if (!Evaluations5.isEmpty()) {
			current.add(ratingValues.get(4) + " comments:");
			for (Evaluation eval:Evaluations5) {
				current.add(eval.getComment());
			}
			current.add("");
		}
		return current;
	}
	
	@FXML
	private void handleButtonAction(ActionEvent event) throws IOException{
		Stage stage = null;
		if (isUserButtonPushed(event)) {
			userButtons(event, stage);
			return;
		}
		else if (event.getSource() == customize) {
			stack.push("CommentPage.fxml");
			loadNextScene(customize, stage, "CustomizeButtons.fxml");
			return;
		}
		else if (event.getSource()==customize1) {
			stack.push("CommentPage.fxml");
			loadNextScene(customize1, stage, "CustomizeButtons.fxml");
			return;
		}
		else if (event.getSource() == showAllComments) {
			textArea.setText(createStringAll());
			return;
		}
		
		if (isCommentButtonsPushed()){
			textArea.setText(createString());
		}
		else {
			textArea.setText("You have not selected to show any comments.");
		}
	}
	
	
	
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method
				textArea.setEditable(false);
				if(!isCommentButtonsPushed()) {
					textArea.setText("You have not selected to show any comments.");
				}
				if(lecture.getEvaluationsRating1().isEmpty()) {
					seeComments1.setDisable(true);
				}
				if(lecture.getEvaluationsRating2().isEmpty()) {
					seeComments2.setDisable(true);
				}
				if(lecture.getEvaluationsRating3().isEmpty()) {
					seeComments3.setDisable(true);
				}
				if(lecture.getEvaluationsRating4().isEmpty()) {
					seeComments4.setDisable(true);
				}
				if(lecture.getEvaluationsRating5().isEmpty()) {
					seeComments5.setDisable(true);
				}
				
				seeComments1.setText(ratingValues.get(0) + " comments");
				if(seeComments1.getText().contains("nix")){
					seeComments1.setText("");
					seeComments1.setDisable(true);
				}
				seeComments2.setText(ratingValues.get(1) + " comments");
				if(seeComments2.getText().contains("nix")){
					seeComments2.setText("");
					seeComments2.setDisable(true);
				}
				seeComments3.setText(ratingValues.get(2) + " comments");
				if(seeComments3.getText().contains("nix")){
					seeComments3.setText("");
					seeComments3.setDisable(true);
				}
				seeComments4.setText(ratingValues.get(3) + " comments");
				if(seeComments4.getText().contains("nix")){
					seeComments4.setText("");
					seeComments4.setDisable(true);
				}
				seeComments5.setText(ratingValues.get(4) + " comments");
				if(seeComments5.getText().contains("nix")){
					seeComments5.setText("");
					seeComments5.setDisable(true);
				}
				
			}

}
