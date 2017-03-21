package gui;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import com.sun.javafx.scene.control.SelectedCellsMap;

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
	public ToggleButton seePerfectComments;
	public ToggleButton seeOkComments;
	public ToggleButton seeFastComments;
	public ToggleButton seeSlowComments;
	public ToggleButton seeConfusedComments;
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
		if (seePerfectComments.isSelected() || seeOkComments.isSelected() || seeFastComments.isSelected() || seeSlowComments.isSelected() || seeConfusedComments.isSelected()) {
			return true;
		}
		return false;
	}
	
	public ArrayList<String> selectedButtons() {
		ArrayList<String> currentEvaluations = new ArrayList<String>();
		if (seePerfectComments.isSelected()){
			currentEvaluations.add("Perfect comments:");
			ArrayList<Evaluation> perfEvaluations = lecture.getPerfectEvaluations();
			for (Evaluation eval:perfEvaluations) {
				currentEvaluations.add(eval.getComment());
			}
			currentEvaluations.add("");
		}
		if (seeOkComments.isSelected()){
			currentEvaluations.add("Ok comments:");
			ArrayList<Evaluation> okEvaluations = lecture.getOkEvaluations();
			for (Evaluation eval:okEvaluations) {
				currentEvaluations.add(eval.getComment());
			}
			currentEvaluations.add("");
		}
		if (seeFastComments.isSelected()){
			currentEvaluations.add("Too fast comments:");
			ArrayList<Evaluation> fastEvaluations = lecture.getTooFastEvaluations();
			for (Evaluation eval:fastEvaluations) {
				currentEvaluations.add(eval.getComment());
			}
			currentEvaluations.add("");
		}
		if (seeSlowComments.isSelected()){
			currentEvaluations.add("Too slow comments:");
			ArrayList<Evaluation> slowEvaluations = lecture.getTooSlowEvaluations();
			for (Evaluation eval:slowEvaluations) {
				currentEvaluations.add(eval.getComment());
			}
			currentEvaluations.add("");
		}
		if (seeConfusedComments.isSelected()){
			currentEvaluations.add("Confused comments:");
			ArrayList<Evaluation> confusedEvaluations = lecture.getConfusedEvaluations();
			for (Evaluation eval:confusedEvaluations) {
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
	}

}
