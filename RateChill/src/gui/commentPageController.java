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
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.AnchorPane;
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
	public ToggleGroup commentToggle;
	public Text debugText;
	public Text submitted;
	public Text overwriteText;
	public Button home;
	public Button back;
	public Button logout;
	
	@FXML
	public ScrollPane scrollPane;
	public AnchorPane anchor;
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
			loadNextScene(back, stage, "lectureProf.fxml");
		}
		if (event.getSource() == logout) {
			loadNextScene(logout, stage, "login.fxml");
		}
	}
	
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
	}
	
	
	@FXML
	private void handleButtonAction(ActionEvent event) throws IOException{
		Stage stage = null;
		userButtons(event, stage);
		
		ArrayList<String> show = selectedButtons();
		for (int i=0;i<show.size(); i++) {
			textField.setText(show.get(i));
		}
		
	}
	
	
	
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method
		
		VBox vBox = new VBox();
	    TextField textField = new TextField();
	    textField.setPrefSize(anchor.getPrefWidth(), anchor.getPrefHeight());
		vBox.getChildren().setAll(textField);
	    anchor.getChildren().setAll(vBox);
		scrollPane.setContent(anchor);
		
		
		
	}

}
