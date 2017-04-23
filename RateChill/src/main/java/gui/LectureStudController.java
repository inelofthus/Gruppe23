package gui;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import database.DBController;
import databaseobjects.Course;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.text.Text;

/**
 * LectureStudController --- LectureStudController is a class that controls all
 * interaction user interaction with the LectureStud.fxml GUI
 * 
 * @author Group 23: Ine Lofthus Arnesen, Kari Meling Johannessen, Nicolai
 *         Cappelen Michelet, Magnus Tvilde
 */
public class LectureStudController extends CommonMethods implements Initializable {

	@FXML
	public Button lecture1;
	public Button lecture2;
	public Button home;
	public Button back;
	public Button logout;
	public Text errorMsg;

	private DBController DBC = new DBController();
	private MainController mc = MainController.getInstance();
	private Course course = mc.getCourse();
	private ArrayList<Integer> last2CompletedLectures = course.getLastTwoCompletedLectureIDs();
	private int keyLec1, keyLec2;

	/**
	 * takes user to the correct page if user button (back, logout or home) is
	 * pressed
	 */
	public void userButtons(ActionEvent event) throws IOException {
		if (event.getSource() == home) {
			loadNextScene(home, "CourseStud.fxml");
		}
		if (event.getSource() == back) {
			loadNextScene(back, "CourseStud.fxml");
		}
		if (event.getSource() == logout) {
			loadNextScene(logout, "Login.fxml");
		}
	}

	@FXML
	private void handleButtonAction(ActionEvent event) throws IOException {
		userButtons(event);
		int numberOfLectures = course.getLectureIDs().size();
		if (numberOfLectures == 0) {
			return;
		} else if (event.getSource() == lecture1 && numberOfLectures > 0) {
			mc.setChosenStudentLecture(keyLec1);
			loadNextScene(lecture1, "EvaluationStud.fxml");
		} else if (event.getSource() == lecture2 && numberOfLectures > 1) {
			mc.setChosenStudentLecture(keyLec2);
			loadNextScene(lecture2, "EvaluationStud.fxml");
		}
	}

	@FXML
	private void handleKeyAction(KeyEvent ke) throws IOException {
		if (ke.getCode().equals(KeyCode.ENTER)) {
			if (lecture1.isFocused()) {
				mc.setChosenStudentLecture(keyLec1);
				loadNextScene(lecture1, "EvaluationStud.fxml");
			} else if (lecture2.isFocused()) {
				mc.setChosenStudentLecture(keyLec2);
				loadNextScene(lecture2, "EvaluationStud.fxml");
			}
		}
	}

	private String getLectureTimeText(int lecID) {
		String time = "";
		try {
			ArrayList<String> dateTime = course.getCompletedLecturesIDDate().get(lecID);
			time = dateTime.get(1).substring(0, 5);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return time;
	}

	private String getLectureDateText(int lecID) {
		String date = "";
		try {
		date = course.getLectureDate(lecID);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return date;
	}
	
	/**
	 * Initialises the LectureStud.fxml GUI
	 */
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// Set text of buttons to contain date of lecture
		int numberOfLectures = last2CompletedLectures.size();
		if (numberOfLectures == 0) {
			errorMsg.setText("No completed lectures have been registered for this course yet");
			lecture1.setVisible(false);
			lecture2.setVisible(false);
			return;
		} else if (numberOfLectures < 2) {
			keyLec1 = last2CompletedLectures.get(0);
			lecture1.setText(DBC.changeDateFormat(getLectureDateText(keyLec1)));
		} else {
			// If both lectures have the same date, the lecture time must also
			// be presented
			keyLec1 = last2CompletedLectures.get(1);
			keyLec2 = last2CompletedLectures.get(0); 
			if (getLectureDateText(keyLec1).equals(getLectureDateText(keyLec2))) {
				lecture1.setText(
						DBC.changeDateFormat(getLectureDateText(keyLec1)) + "\n" + getLectureTimeText(keyLec1));
				lecture2.setText(
						DBC.changeDateFormat(getLectureDateText(keyLec2)) + "\n" + getLectureTimeText(keyLec2));
			} else {
				lecture1.setText(DBC.changeDateFormat(getLectureDateText(keyLec1)));
				lecture2.setText(DBC.changeDateFormat(getLectureDateText(keyLec2)));
			}
			course.getLectureDate(keyLec1);
		}

	}

}
