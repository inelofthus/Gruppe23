package gui;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.ResourceBundle;
import java.util.Set;

import database.DBController;
import databaseobjects.Course;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.text.Text;
import javafx.stage.Stage;


public class customizeButtonsController implements Initializable {

	//fxml objects
	@FXML
	public Button home;
	public Button back;
	public Button logout;
	
	
	public Button submitChanges;
	public Button preview;
	public ToggleButton button1;
	public ToggleButton button2;
	public ToggleButton button3;
	public ToggleButton button4;
	public ToggleButton button5;
	public TextField buttonText1;
	public TextField buttonText2;
	public TextField buttonText3;
	public TextField buttonText4;
	public TextField buttonText5;
	public Text errorText;
	
	DBController DBC = new DBController();
	Course course = mainController.getInstance().getCourse();
	ArrayList<String> ratings = course.getRatingValues();
	ArrayList<TextField> texts = new ArrayList<TextField>();
	ArrayList<ToggleButton> buttons = new ArrayList<ToggleButton>();
	
	
	private void createHelpingLists() {
		texts.add(buttonText1);
		texts.add(buttonText2);
		texts.add(buttonText3);
		texts.add(buttonText4);
		texts.add(buttonText5);
		buttons.add(button1);
		buttons.add(button2);
		buttons.add(button3);
		buttons.add(button4);
		buttons.add(button5);
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
			loadNextScene(back, stage, "lectureProf.fxml");
		}
		if (event.getSource() == logout) {
			loadNextScene(logout, stage, "login.fxml");
		}
	}
	
	
	public boolean haveMadeButtonChanges() {
		if (buttonIsChanged(buttonText1) || buttonIsChanged(buttonText2) || buttonIsChanged(buttonText3) || buttonIsChanged(buttonText4) || buttonIsChanged(buttonText5)) {
			return true;
		}
		return false;
	}
	
	
	public void setPreviewTexts() {
		for (int i = 0; i<5;i++) {
			if (buttonIsChanged(texts.get(i))) {
				buttons.get(i).setText(texts.get(i).getText());
			}
			else {
				buttons.get(i).setText(ratings.get(i));
			}
		}
	}
	
	public boolean buttonIsChanged(TextField text) {
		if(text.getText()=="") {
			return false;			
		}
		return true;
	}
	
	public ArrayList<String> makeListOfValues() {
		ArrayList<String> list = new ArrayList<String>();
		for(int i = 0;i<5;i++) {
			if (buttonIsChanged(texts.get(i))) {
				list.add(texts.get(i).getText());
			}
			else {
				list.add(ratings.get(i));
			}
		}
		return list;
	}
	
	public boolean equalRatings(ArrayList<String> checkList) {
		Set<String> set = new HashSet<String>(checkList);
		if(set.size()<checkList.size()) {
			return true;
		}
		return false;
	}
	
	@FXML
	private void handleButtonAction(ActionEvent event) throws IOException{
		Stage stage = null;
		errorText.setText("");
		userButtons(event, stage);
		if(event.getSource() == preview) {
			setPreviewTexts();
			return;
		}
		else if (event.getSource() == submitChanges){
			if (!haveMadeButtonChanges()) {
				errorText.setText("You have not made any changes");
				return;
			}			
			ArrayList<String> inputValues = makeListOfValues();
			if(tooLongInput(inputValues)){
				errorText.setText("Too long text. There max length of a rating value is 20characters. ");
				return;
			}
			if (equalRatings(inputValues)) {
				errorText.setText("There exists duplicate rating-values, please make them unique");
				return;
			}
			
			DBC.insertCourseRatingValues(course.getCourseCode(), inputValues.get(0), inputValues.get(1), inputValues.get(2), inputValues.get(3), inputValues.get(4));
			loadNextScene(submitChanges, stage, "lectureProf.fxml");
		}
		return;
	}
	
	
	
	
	
	
	private boolean tooLongInput(ArrayList<String> inputValues) {
		
		for(String rv: inputValues){
			if(rv.length() > 20){
				return true;
			}
		}
		return false;
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method
		createHelpingLists();
		setPreviewTexts();
	}

}
