package gui;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.Stack;

import database.DBController;
import databaseobjects.Course;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Stage;


public class CustomizeButtonsController extends CommonMethods implements Initializable {

	//fxml objects
	@FXML
	public Button home;
	public Button back;
	public Button logout;
	
	public Button submitChanges;
	public Button cancel;
	public Button preview;
	public Button revert;
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
	public Rectangle errorBar;
	public Hyperlink info;
	
	public Color MYRED = new Color(0.937, 0.290, 0.290, 1);
	
	MainController mc = MainController.getInstance();
	DBController DBC = new DBController();
	Course course = MainController.getInstance().getCourse();
	ArrayList<String> ratings = course.getRatingValues();
	ArrayList<TextField> texts = new ArrayList<TextField>();
	ArrayList<ToggleButton> buttons = new ArrayList<ToggleButton>();
	private Stack<String> stack = MainController.getInstance().getStack();
	
	
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
	
	public void userButtons(ActionEvent event, Stage stage) throws IOException{
		if (event.getSource() == home) {
			loadNextScene(home, stage, "CourseProf.fxml");
		}
		if (event.getSource() == back || event.getSource() == cancel) {
			MainController.getInstance().buttonsSaved = "false";
			MainController.getInstance().buttonsSavedOrigin = stack.pop();
			loadNextScene(back, stage, MainController.getInstance().buttonsSavedOrigin);
			return;
		}
		if (event.getSource() == logout) {
			loadNextScene(logout, stage, "Login.fxml");
		}
	}
	
	public boolean isUserButtonPushed(ActionEvent event) {
		if (event.getSource() == home || event.getSource() == back || event.getSource() == cancel || event.getSource() == logout) {
			return true;
		}
		return false;
	}
	
	public boolean haveMadeButtonChanges() {
		if (buttonIsChanged(buttonText1) || buttonIsChanged(buttonText2) || buttonIsChanged(buttonText3) || buttonIsChanged(buttonText4) || buttonIsChanged(buttonText5)) {
			return true;
		}
		return false;
	}
	
	
	public void setPreviewTexts(ArrayList<String> array) {
		for (int i = 0; i<5;i++) {
			buttons.get(i).setText(array.get(i));
		}
	}
	
	
	public void initializePreview() {
		for (int i = 0; i<5;i++) {
			buttons.get(i).setText(ratings.get(i));
		}
	}
	
	public boolean buttonIsChanged(TextField text) {
		if(text.getText().length()>0) {
			return true;
		}
		return false;
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
	private void handleHyperLinkAction(ActionEvent event) throws IOException{
		if(event.getSource() == info){
			Stage stage = new Stage();
			mc.setPopupTitle("Customize Student Buttons Info");
			mc.setPopupMessage("Customizing student buttons means that you change the rating values that the students can evaluate your lecture based on.");
			loadPopupHyperLink(info, stage, "InfoPopup.fxml");
		}
	}
	
	@FXML
	private void handleButtonAction(ActionEvent event) throws IOException{
		Stage stage = null;
		errorText.setText("");
		errorBar.setVisible(false);
		if (isUserButtonPushed(event)) {
			userButtons(event, stage);
			return;
		}
		else if(event.getSource() == preview) {
			setPreviewTexts(makeListOfValues());
			return;
		}else if (event.getSource() == revert){
			ArrayList<String> defaultRatings = course.getDefaultRatingValues();
			buttonText1.setText(defaultRatings.get(0));
			buttonText2.setText(defaultRatings.get(1));
			buttonText3.setText(defaultRatings.get(2));
			buttonText4.setText(defaultRatings.get(3));
			buttonText5.setText(defaultRatings.get(4));
			
			errorBar.setFill(Color.LIGHTGOLDENRODYELLOW);
			errorBar.setVisible(true);
			setPreviewTexts(makeListOfValues());
			errorText.setText("Previewing default rating values. Press submit to confirm this change");
		}
		else if (event.getSource() == submitChanges){
			if (!haveMadeButtonChanges()) {
				errorBar.setFill(Color.LIGHTGOLDENRODYELLOW);
				errorBar.setVisible(true);
				errorText.setText("You have not made any changes");
				return;
			}			
			ArrayList<String> inputValues = makeListOfValues();
			if(tooLongInput(inputValues)){
				errorBar.setFill(MYRED);
				errorBar.setVisible(true);
				errorText.setText("Text is too long. The maximum length of a rating value is 20 characters.");
				return;
			}
			if (equalRatings(inputValues)) {
				errorBar.setFill(MYRED);
				errorBar.setVisible(true);
				errorText.setText("There exists duplicate rating-values, please make them unique");
				return;
			}
			
			DBC.insertCourseRatingValues(course.getCourseCode(), inputValues.get(0), inputValues.get(1), inputValues.get(2), inputValues.get(3), inputValues.get(4));
			MainController.getInstance().buttonsSaved = "true";
			MainController.getInstance().buttonsSavedOrigin = stack.pop();
			loadNextScene(submitChanges, stage, MainController.getInstance().buttonsSavedOrigin); 
			course.setRatingValues(inputValues);
			MainController.getInstance().setCourse(course);
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
		initializePreview();
	}

}
