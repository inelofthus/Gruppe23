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
    public TextField textField;
	
    Integer lectureID = mainController.getInstance().getChosenProfessorLecture();
	
	private Lecture lecture = new Lecture(lectureID);
	//ArrayList evaluations = lec.getEvaluations();
	
	
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
	
	/*@SuppressWarnings("null")
	private ArrayList<String> selectedButtons() {
		ArrayList<String> currentEvaluations = null;
		if (seePerfectComments.isSelected()){
			ArrayList<Evaluation> perfEvaluations = mainController.getInstance().getLecture().getPerfectEvaluations();
			for (int i=0;i<perfEvaluations.size();i++) {
				currentEvaluations.add(perfEvaluations.get(i).getComment());
			} 
		}
		if (seeOkComments.isSelected()){
			ArrayList<Evaluation> okEvaluations = mainController.getInstance().getLecture().getOkEvaluations();
			for (int i=0;i<okEvaluations.size();i++) {
				currentEvaluations.add(okEvaluations.get(i).getComment());
			}
		}
		if (seeFastComments.isSelected()){
			ArrayList<Evaluation> fastEvaluations = mainController.getInstance().getLecture().getTooFastEvaluations();
			for (int i=0;i<fastEvaluations.size();i++) {
				currentEvaluations.add(fastEvaluations.get(i).getComment());
			}
		}
		if (seeSlowComments.isSelected()){
			ArrayList<Evaluation> slowEvaluations = mainController.getInstance().getLecture().getTooSlowEvaluations();
			for (int i=0;i<slowEvaluations.size();i++) {
				currentEvaluations.add(slowEvaluations.get(i).getComment());
			}
		}
		if (seeConfusedComments.isSelected()){
			ArrayList<Evaluation> confusedEvaluations = mainController.getInstance().getLecture().getConfusedEvaluations();
			for (int i=0;i<confusedEvaluations.size();i++) {
				currentEvaluations.add(confusedEvaluations.get(i).getComment());
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
		/*ArrayList<String> currEval = selectedButtons();
		ArrayList<Label> labels = new ArrayList<Label>();
		for (int i = 0;i<currEval.size();i++) {
			Label label = new Label();
			label.setText(currEval.get(i));
			labels.add(label);
		}
		for (Label label:labels) {
			textField.appendText(label.getText());
		}
		vBox.getChildren().setAll(textField);
		
	    anchor.getChildren().setAll(vBox);
		scrollPane.setContent(anchor);
		return;*/
	}
	
	
	
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method
		
	    /*textField.setPrefSize(anchor.getPrefWidth(), anchor.getPrefHeight());
		textField.setEditable(false);
		ArrayList<String> currEval = selectedButtons();
		ArrayList<Label> labels = new ArrayList<Label>();
		for (int i = 0;i<currEval.size();i++) {
			Label label = new Label();
			label.setText(currEval.get(i));
			labels.add(label);
		}
		for (Label label:labels) {
			textField.appendText(label.getText());
		}
	    vBox.getChildren().setAll(textField);
	    anchor.getChildren().setAll(vBox);
		scrollPane.setContent(anchor);*/
		
		
		
	}

}
