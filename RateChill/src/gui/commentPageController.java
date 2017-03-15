package gui;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

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
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
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
	public ScrollPane scrollPane;
	public AnchorPane anchor;
	public VBox vBox;
    //public TextField textField = new TextField();
	
    Integer lectureID = mainController.getInstance().getChosenProfessorLecture();
	
	private Lecture lecture = new Lecture(lectureID);
	//ArrayList evaluations = lec.getEvaluations();
	//public ArrayList<String> currEval = selectedButtons();
	
	
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
	
	/*public ArrayList<String> selectedButtons() {
		ArrayList<String> currentEvaluations = null;
		if (seePerfectComments.isSelected()){
			ArrayList<Evaluation> perfEvaluations = lecture.getPerfectEvaluations();
			for (Evaluation eval:perfEvaluations) {
				currentEvaluations.add(eval.getComment());
			} 
		}
		if (seeOkComments.isSelected()){
			ArrayList<Evaluation> okEvaluations = lecture.getOkEvaluations();
			for (Evaluation eval:okEvaluations) {
				currentEvaluations.add(eval.getComment());
			}
		}
		if (seeFastComments.isSelected()){
			ArrayList<Evaluation> fastEvaluations = lecture.getTooFastEvaluations();
			for (Evaluation eval:fastEvaluations) {
				currentEvaluations.add(eval.getComment());
			}
		}
		if (seeSlowComments.isSelected()){
			ArrayList<Evaluation> slowEvaluations = lecture.getTooSlowEvaluations();
			for (Evaluation eval:slowEvaluations) {
				currentEvaluations.add(eval.getComment());
			}
		}
		if (seeConfusedComments.isSelected()){
			ArrayList<Evaluation> confusedEvaluations = lecture.getConfusedEvaluations();
			for (Evaluation eval:confusedEvaluations) {
				currentEvaluations.add(eval.getComment());
			}
		}
		return currentEvaluations;
	}*/
	
	
	@FXML
	private void handleButtonAction(ActionEvent event) throws IOException{
		Stage stage = null;
		if (isUserButtonPushed(event)) {
			userButtons(event, stage);
			return;
		}
		/*ArrayList<Label> labels = new ArrayList<Label>();
		for (String string:currEval) {
			Label label = new Label();
			label.setText(string);
			labels.add(label);
		}
		for (Label label:labels) {
			vBox.getChildren().add(label);
		}
		anchor.getChildren().setAll(vBox);
		*/
		
		/*for (String string:currEval) {
			textField.appendText(string+ "/n");
		}
		vBox.getChildren().setAll(textField);
		
	    anchor.getChildren().setAll(vBox);
		scrollPane.setContent(anchor);
		return;*/
	}
	
	
	
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method
		
		//vBox.setPrefSize(anchor.getPrefWidth(), anchor.getPrefHeight());
	    
		
		//textField.setPrefSize(anchor.getPrefWidth(), anchor.getPrefHeight());
		//textField.setEditable(false);
		/*for (String string:currEval) {
			textField.appendText(string + "/n");
		}*/
		//anchor.getChildren().setAll(textField);
	    //anchor.getChildren().setAll(vBox);
		//scrollPane.setContent(anchor);
		
		
		
	}

}
