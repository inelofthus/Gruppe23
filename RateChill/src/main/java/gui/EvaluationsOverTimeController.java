package gui;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.Stack;
import databaseobjects.Course;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

public class EvaluationsOverTimeController extends CommonMethods implements Initializable {

	@FXML
	public Button home;
	public Button logout;
	public Button back;
	public Button customize;
	public Text errorText;
	public Rectangle errorBar;
	public LineChart<String, Integer> lineChart;
	public CategoryAxis xAxis;
	public NumberAxis yAxis;
	private ObservableList<String> lectureDates = FXCollections.observableArrayList();
	private Stack<String> stack = MainController.getInstance().getStack();
	
	private MainController mc = MainController.getInstance();
	private Course course = mc.getCourse();
	
	/**
	 * takes user to the correct page if user button (back, logout or home) is
	 * pressed
	 */
	public void userButtons(ActionEvent event) throws IOException{
		if (event.getSource() == home) {
			loadNextScene(home,  "CourseProf.fxml");
		}
		if (event.getSource() == back) {
			loadNextScene(back,  stack.pop());
		}
		if (event.getSource() == logout) {
			loadNextScene(logout,  "Login.fxml");
		}
	}
	
	private boolean isUserButtonPushed(ActionEvent event) {
		if (event.getSource() == home || event.getSource() == back || event.getSource() == logout) {
			return true;
		}
		return false;
	}
	
	@FXML
	private void handleButtonAction(ActionEvent event) throws IOException{
		if (isUserButtonPushed(event)) {
			userButtons(event);
			return;
		}
		else if (event.getSource() == customize) {
			stack.push("EvaluationsOverTime.fxml");
			loadNextScene(customize,  "CustomizeButtons.fxml");
			return;
		}
	}
	
	private XYChart.Series<String, Integer> createSeries(ArrayList<Integer> numbersList, String label) {
		XYChart.Series<String, Integer> series = new XYChart.Series<String, Integer>();
		for (int i = 0;i<lectureDates.size();i++) {
			XYChart.Data<String, Integer> ratingData = new XYChart.Data<String,Integer>(lectureDates.get(i), numbersList.get(i));
			series.getData().add(ratingData);
			series.setName(label);
		}
		return series;
	}
	
	private void setEvaluationData(XYChart.Series<String, Integer> list) {
		lineChart.getData().add(list);
	}
	
	
	/**
	 * Initialises the EvaluationsOverTime.fxml GUI
	 */
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
		course.setRatingsOverTime();		
		lectureDates = FXCollections.observableArrayList(course.getDateArrayForGraph());		
		xAxis.setCategories(lectureDates);		
		
		//retrieves the percentage values given each lecture for each of the 5 different rating values
		ArrayList<Integer> count1 = course.getLecRatingCounts(1);		
		ArrayList<Integer> count2 = course.getLecRatingCounts(2);		
		ArrayList<Integer> count3 = course.getLecRatingCounts(3);		
		ArrayList<Integer> count4 = course.getLecRatingCounts(4);		
		ArrayList<Integer> count5 = course.getLecRatingCounts(5);
		
		setEvaluationData(createSeries(count1, course.getRatingValues().get(0)));	
		setEvaluationData(createSeries(count2, course.getRatingValues().get(1)));		
		setEvaluationData(createSeries(count3, course.getRatingValues().get(2)));	
		setEvaluationData(createSeries(count4, course.getRatingValues().get(3)));		
		setEvaluationData(createSeries(count5, course.getRatingValues().get(4)));	;	
		
		lineChart.setCreateSymbols(false);
		xAxis.setLabel("Lecture Date and Time");
		yAxis.setLabel("Percent");
		
		if (mc.buttonsSaved == "true" && mc.buttonsSavedOrigin.equals("EvaluationsOverTime.fxml")) {
			errorBar.setVisible(true);
			errorBar.setFill(Color.DARKSEAGREEN);
			errorText.setText("Evaluation buttons successfully customized.");
		} else if (mc.buttonsSaved == "false" && mc.buttonsSavedOrigin.equals("EvaluationsOverTime.fxml")) {
			errorBar.setVisible(true);
			errorBar.setFill(Color.LIGHTGOLDENRODYELLOW);
			errorText.setText("No changes were made to student evaluation options");
		}
		mc.buttonsSaved = "";		
	}

}
