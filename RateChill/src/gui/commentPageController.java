package gui;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

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

public class commentPageController implements Initializable {

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
	
	@FXML
	public TextArea textArea;
	public Text text = null;
	public VBox vBox;
	
    Integer lectureID = mainController.getInstance().getChosenProfessorLecture();
	
	private Lecture lecture = new Lecture(lectureID);
	private Course course = mainController.getInstance().getCourse();
	private ArrayList<String> ratingValues = lecture.getRatingValues();
	
	public String createString() {
		ArrayList<String> als = selectedButtons();
		String allComment = ""; 
		for (String string:als){
			allComment += string + "\n";
		}
		return allComment;
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
			loadNextScene(back, stage, "individualCharts.fxml");
		}
		if (event.getSource() == logout) {
			loadNextScene(logout, stage, "login.fxml");
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
	
	
	
	
	@FXML
	private void handleButtonAction(ActionEvent event) throws IOException{
		Stage stage = null;
		if (isUserButtonPushed(event)) {
			userButtons(event, stage);
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
