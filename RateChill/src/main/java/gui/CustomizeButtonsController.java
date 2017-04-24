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
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

/**
 * CustomizeButtonsController --- CustomizeButtonsController is a class that controls all
 * interaction user interaction with the CustomizeButtons.fxml GUI
 * 
 * @author Group 23: Ine Lofthus Arnesen, Kari Meling Johannessen, Nicolai
 *         Cappelen Michelet, Magnus Tvilde
 */
public class CustomizeButtonsController extends CommonMethods implements Initializable {

	// fxml objects
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

	private MainController mc = MainController.getInstance();
	private DBController DBC = new DBController();
	private Course course = mc.getCourse();
	private ArrayList<String> ratings = course.getRatingValues();
	private ArrayList<TextField> texts = new ArrayList<TextField>();
	private ArrayList<ToggleButton> buttons = new ArrayList<ToggleButton>();
	private Stack<String> stack = mc.getStack();

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

	/**
	 * takes user to the correct page if user button (back, cancel, logout or
	 * home) is pressed
	 */
	public void userButtons(ActionEvent event) throws IOException {
		if (event.getSource() == home) {
			loadNextScene(home, "CourseProf.fxml");
		}
		if (event.getSource() == back || event.getSource() == cancel) {
			mc.setButtonsSaved("false"); 
			mc.setButtonsSavedOrigin(stack.pop());
			loadNextScene(back, mc.getButtonsSavedOrigin());
			return;
		}
		if (event.getSource() == logout) {
			loadNextScene(logout, "Login.fxml");
		}
	}

	private boolean isUserButtonPushed(ActionEvent event) {
		if (event.getSource() == home || event.getSource() == back || event.getSource() == cancel
				|| event.getSource() == logout) {
			return true;
		}
		return false;
	}

	private boolean haveMadeButtonChanges() {
		if (buttonIsChanged(buttonText1) || buttonIsChanged(buttonText2) || buttonIsChanged(buttonText3)
				|| buttonIsChanged(buttonText4) || buttonIsChanged(buttonText5)) {
			return true;
		}
		return false;
	}

	private void setPreviewTexts(ArrayList<String> array) {
		for (int i = 0; i < 5; i++) {
			buttons.get(i).setText(array.get(i));
		}
	}

	private boolean buttonIsChanged(TextField text) {
		if (text.getText().length() > 0) {
			return true;
		}
		return false;
	}

	/*
	 * Makes a list of the new rating values: New value if something is typed in that field or old value otherwise
	 */
	private ArrayList<String> makeListOfValues() {
		ArrayList<String> list = new ArrayList<String>();
		for (int i = 0; i < 5; i++) {
			if (buttonIsChanged(texts.get(i))) {
				list.add(texts.get(i).getText());
			} else {
				list.add(ratings.get(i));
			}
		}
		return list;
	}

	/*
	 * Checks if the input list has duplicate values
	 */
	private boolean equalRatings(ArrayList<String> checkList) {
		Set<String> set = new HashSet<String>(checkList);
		if (set.size() < checkList.size()) {
			return true;
		}
		return false;
	}

	/*
	 * Loads an info popup if the info hyperlink is pressed
	 */
	@FXML
	private void handleHyperLinkAction(ActionEvent event) throws IOException {
		if (event.getSource() == info) {
			mc.setPopupTitle("Customize Student Buttons Info");
			mc.setPopupMessage(
					"Customizing student buttons means that you change the rating values that the students can evaluate your lecture based on. The changes will take effect starting from your next lecture. You will still be able to see the individual lecture graphs for old rating values. \n NB: After changing rating values, you will no longer be able to see the lectures over time graph for old rating values");
			loadPopupHyperLink("InfoPopup.fxml");
		}
	}

	@FXML
	private void handleButtonAction(ActionEvent event) throws IOException {
		errorText.setText("");
		errorBar.setVisible(false);
		if (isUserButtonPushed(event)) {
			userButtons(event);
			return;
		} else if (event.getSource() == preview) {
			setPreviewTexts(makeListOfValues());
			return;
		} else if (event.getSource() == revert) {
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
		} else if (event.getSource() == submitChanges) {
			if (!haveMadeButtonChanges()) {
				errorBar.setFill(Color.LIGHTGOLDENRODYELLOW);
				errorBar.setVisible(true);
				errorText.setText("You have not made any changes");
				return;
			}
			ArrayList<String> inputValues = makeListOfValues();
			if (tooLongInput(inputValues)) {
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

			DBC.insertCourseRatingValues(course.getCourseCode(), inputValues.get(0), inputValues.get(1),
					inputValues.get(2), inputValues.get(3), inputValues.get(4));
			mc.setButtonsSaved("true");
			mc.setButtonsSavedOrigin(stack.pop());
			loadNextScene(submitChanges, mc.getButtonsSavedOrigin());
			course.setRatingValues(inputValues);
			mc.setCourse(course);
		}
		return;
	}

	private boolean tooLongInput(ArrayList<String> inputValues) {
		for (String rv : inputValues) {
			if (rv.length() > 20) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Initialises the CustomizeButtons.fxml GUI
	 */
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		createHelpingLists();
		setPreviewTexts(ratings);
	}

}
