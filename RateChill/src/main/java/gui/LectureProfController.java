package gui;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.ResourceBundle;
import java.util.Stack;
import database.DBController;
import databaseobjects.Course;
import databaseobjects.Lecture;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.ListView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

/**
 * LectureProfController --- LectureProfController is a class that controls all
 * interaction user interaction with the LectureProf.fxml GUI
 * 
 * @author Group 23: Ine Lofthus Arnesen, Kari Meling Johannessen, Nicolai
 *         Cappelen Michelet, Magnus Tvilde
 */
public class LectureProfController extends CommonMethods implements Initializable {

	@FXML
	public Button indivLecture;
	public Button allLectures;
	public Button customize;
	public Button editLectures;
	public Button home;
	public Button back;
	public Button logout;
	public Text month;
	public Hyperlink prevMonth;
	public Hyperlink nextMonth;
	public Hyperlink month1;
	public Hyperlink month2;
	public Hyperlink month3;
	public Hyperlink month4;
	public Hyperlink month5;
	public Hyperlink month6;
	public ListView<String> lectures;
	public Hyperlink editLecturesLink;
	public Text errorText;
	public Rectangle errorBar;

	private boolean spring = true;
	private Course course;
	private int monthNum;
	private ArrayList<String> thisMonthsLectures;
	private Stack<String> stack = null;
	private MainController mc = MainController.getInstance();
	private DBController DBC = new DBController();

	/**
	 * takes user to the correct page if user button (back, logout or home) is
	 * pressed
	 */
	public void userButtons(ActionEvent event) throws IOException {
		if (event.getSource() == home) {
			loadNextScene(home, "CourseProf.fxml");
		}
		if (event.getSource() == back) {
			loadNextScene(back, "CourseProf.fxml");
		}
		if (event.getSource() == logout) {
			loadNextScene(logout, "Login.fxml");
		}
	}

	@FXML
	private void handleKeyAction(KeyEvent ke) throws IOException {
		if (ke.getCode().equals(KeyCode.ENTER)) {
			if (indivLecture.isFocused()) {
				loadIndividualLecture();
			} else if (allLectures.isFocused()) {
				loadAllLectures();
			}
		}
	}

	/* Sets relevant values in the MainController and opens the
	 * individualCharts.fxml GUI */	 
	private void loadIndividualLecture() {
		try {
			int lecID = course.getLectureIDsMonth(monthNum).get(lectures.getSelectionModel().getSelectedIndex());
			Lecture lec = new Lecture(lecID);
			mc.setLecture(lec);
			mc.setChosenProfessorLecture(lecID);
			stack.push("LectureProf.fxml");
			loadNextScene(indivLecture, "IndividualCharts.fxml");
		} catch (Exception e) {
			errorText.setText("Choose a lecture from the calendar, or see graph for lectures over time");
			Color myRed = new Color(0.937, 0.290, 0.290, 1);
			errorBar.setFill(myRed);
			errorBar.setVisible(true);
		}
	}

	/* Sets relevant values in the MainController and opens the
	 EvaluationsOverTime.fxml GUI */
	private void loadAllLectures() throws IOException {
		stack.push("LectureProf.fxml");
		loadNextScene(allLectures, "EvaluationsOverTime.fxml");
	}

	@FXML
	private void handleButtonAction(ActionEvent event) throws IOException {
		userButtons(event);
		if (event.getSource() == indivLecture) {
			loadIndividualLecture();
		} else if (event.getSource() == allLectures) {
			loadAllLectures();
		} else if (event.getSource() == customize) {
			stack.push("LectureProf.fxml");
			loadNextScene(customize, "CustomizeButtons.fxml");
		} else if (event.getSource() == editLectures || event.getSource() == editLecturesLink) {
			stack.push("LectureProf.fxml");
			loadNextScene(editLectures, "AddLectures.fxml");
		}
	}

	@FXML
	private void handleHyperLinkAction(ActionEvent event) throws IOException {
		if (event.getSource() == nextMonth) {
			if (monthNum != 6 && monthNum != 12) {
				monthNum++;
				updateMonth(monthNum);
			}
		}
		if (event.getSource() == prevMonth) {
			if (monthNum != 1 && monthNum != 7) {
				monthNum--;
				updateMonth(monthNum);
			}
		}
		if (event.getSource() == month1) {
			if (monthNum != 1 && monthNum != 7) {
				if (spring)
					monthNum = 1;
				else
					monthNum = 7;
				updateMonth(monthNum);
			}
		}
		if (event.getSource() == month2) {
			if (monthNum != 2 && monthNum != 8) {
				if (spring)
					monthNum = 2;
				else
					monthNum = 8;
				updateMonth(monthNum);
			}
		}
		if (event.getSource() == month3) {
			if (monthNum != 3 && monthNum != 9) {
				if (spring)
					monthNum = 3;
				else
					monthNum = 9;
				updateMonth(monthNum);
			}
		}
		if (event.getSource() == month4) {
			if (monthNum != 4 && monthNum != 10) {
				if (spring)
					monthNum = 4;
				else
					monthNum = 10;
				updateMonth(monthNum);
			}
		}
		if (event.getSource() == month5) {
			if (monthNum != 5 && monthNum != 11) {
				if (spring)
					monthNum = 5;
				else
					monthNum = 11;
				updateMonth(monthNum);
			}
		}
		if (event.getSource() == month6) {
			if (monthNum != 6 && monthNum != 12) {
				if (spring)
					monthNum = 6;
				else
					monthNum = 12;
				updateMonth(monthNum);
			}
		}
	}

	// update this months lectures: change text in listview and month title
	private void updateMonth(int monthNum2) {
		nextMonth.setDisable(false);
		prevMonth.setDisable(false);
		month.setText(getMonthText(monthNum));
		ArrayList<Integer> lecIDs = course.getLectureIDsMonth(monthNum);
		thisMonthsLectures = getLectureDateTimes(lecIDs);
		lectures.getItems().clear();
		lectures.getItems().addAll(thisMonthsLectures);

		if (course.getLectureIDsMonth(monthNum + 1).isEmpty() || monthNum == 6 || monthNum == 12) {
			nextMonth.setDisable(true);
		}
		if (course.getLectureIDsMonth(monthNum - 1).isEmpty() || monthNum == 1 || monthNum == 7) {
			prevMonth.setDisable(true);
		}
	}

	private String getMonthText(int monthNum) {
		String monthString;
		switch (monthNum) {
		case 1:
			monthString = "January";
			break;
		case 2:
			monthString = "February";
			break;
		case 3:
			monthString = "March";
			break;
		case 4:
			monthString = "April";
			break;
		case 5:
			monthString = "May";
			break;
		case 6:
			monthString = "June";
			break;
		case 7:
			monthString = "July";
			break;
		case 8:
			monthString = "August";
			break;
		case 9:
			monthString = "September";
			break;
		case 10:
			monthString = "October";
			break;
		case 11:
			monthString = "November";
			break;
		case 12:
			monthString = "December";
			break;
		default:
			monthString = "Invalid month";
			break;
		}

		return monthString;
	}

	private ArrayList<String> getLectureDateTimes(ArrayList<Integer> lecIDs) {
		ArrayList<String> dateTime = new ArrayList<>();
		String time = "";

		for (int lecID : lecIDs) {
			time = course.getCompletedLecturesIDDate().get(lecID).get(1).substring(0, 5);
			dateTime.add(DBC.changeDateFormat(course.getLectureDate(lecID)) + "\t" + time);
		}
		return dateTime;
	}

	// disables links to months that don't have any completed lectures
	private void disableEmptyMonths() {

		if (monthNum > 6) {
			month1.setText("Jul");
			if (course.getLectureIDsMonth(7).isEmpty()) {
				month1.setDisable(true);
			}
			month2.setText("Aug");
			if (course.getLectureIDsMonth(8).isEmpty()) {
				month2.setDisable(true);
			}
			month3.setText("Sep");
			if (course.getLectureIDsMonth(9).isEmpty()) {
				month3.setDisable(true);
			}
			month4.setText("Oct");
			if (course.getLectureIDsMonth(10).isEmpty()) {
				month4.setDisable(true);
			}
			month5.setText("Nov");
			if (course.getLectureIDsMonth(11).isEmpty()) {
				month5.setDisable(true);
			}
			month6.setText("Des");
			if (course.getLectureIDsMonth(12).isEmpty()) {
				month6.setDisable(true);
			}
			spring = false;
		} else {
			if (course.getLectureIDsMonth(1).isEmpty()) {
				month1.setDisable(true);
			}
			if (course.getLectureIDsMonth(2).isEmpty()) {
				month2.setDisable(true);
			}
			if (course.getLectureIDsMonth(3).isEmpty()) {
				month3.setDisable(true);
			}
			if (course.getLectureIDsMonth(4).isEmpty()) {
				month4.setDisable(true);
			}
			if (course.getLectureIDsMonth(5).isEmpty()) {
				month5.setDisable(true);
			}
			if (course.getLectureIDsMonth(6).isEmpty()) {
				month6.setDisable(true);
			}
		}
	}
	
	private void goToFirstNonEmptyMonth() {
		ArrayList<Integer> lecIDs = course.getLectureIDsMonth(monthNum);
		thisMonthsLectures = getLectureDateTimes(lecIDs);

		// If the current month doesn't have any lectures, go back one month
		// until you find one that does
		while (thisMonthsLectures.isEmpty()) {
			if (monthNum == 1 || monthNum == 7 || monthNum < 0) {
				break;
			}
			monthNum--;
			updateMonth(monthNum);
		}
	}
	
	/**
	 * Initialises the LectureProf.fxml GUI
	 */
	@Override
	public void initialize(URL location, ResourceBundle resources) {

		this.course = mc.getCourse();
		this.monthNum = Calendar.getInstance().get(Calendar.MONTH) + 1;

		course.setLectureByMonth();
		disableEmptyMonths();
		goToFirstNonEmptyMonth();
		updateMonth(monthNum);

		// initialising stack for back-buttons to come
		this.stack = MainController.getInstance().getStack();

		// resetting the stack
		if (!stack.isEmpty()) {
			stack.clear();
		}

		// Gives feedback for updated evaluation buttons
		if (mc.getButtonsSaved() == "true"
				&& mc.getButtonsSavedOrigin().equals("LectureProf.fxml")) {
			errorBar.setVisible(true);
			errorBar.setFill(Color.DARKSEAGREEN);
			errorText.setText("Evaluation buttons successfully customized.");
		} else if (mc.getButtonsSaved() == "false"
				&& mc.getButtonsSavedOrigin().equals("LectureProf.fxml")) {
			errorBar.setVisible(true);
			errorBar.setFill(Color.LIGHTGOLDENRODYELLOW);
			errorText.setText("No changes were made to student evaluation options");
		}
		mc.setButtonsSaved("");

	}





}
