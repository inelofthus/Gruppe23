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
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class EvaluationsOverTimeController implements Initializable {

	//fxml objects
	@FXML
	public Text debugText;
	public Button home;
	public Button logout;
	public Button back;
	public Button customize;
	
	
	@FXML
	public LineChart<String, Integer> lineChart;
	public CategoryAxis xAxis;
	public NumberAxis yAxis;
	private ObservableList<String> lectureDates = FXCollections.observableArrayList();
	private Stack<String> stack = MainController.getInstance().getStack();
	
	Course course = MainController.getInstance().getCourse();
	
	
	
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
			loadNextScene(home, stage, "CourseProf.fxml");
		}
		
		if (event.getSource() == back) {
			loadNextScene(back, stage, stack.pop());
		}
		
		if (event.getSource() == logout) {
			loadNextScene(logout, stage, "Login.fxml");
		}
	}
	
	public boolean isUserButtonPushed(ActionEvent event) {
		if (event.getSource() == home || event.getSource() == back || event.getSource() == logout) {
			return true;
		}
		return false;
	}
	
	
	//needs a fix
	@FXML
	private void handleButtonAction(ActionEvent event) throws IOException{
		Stage stage = null;
		if (isUserButtonPushed(event)) {
			userButtons(event, stage);
			return;
		}
		else if (event.getSource() == customize) {
			stack.push("EvaluationsOverTime.fxml");
			loadNextScene(customize, stage, "CustomizeButtons.fxml");
			return;
		}
	}
	
	
	public XYChart.Series<String, Integer> createSeries(ArrayList<Integer> numbersList) {
		
		XYChart.Series<String, Integer> series = new XYChart.Series<String, Integer>();
		for (int i = 0;i<lectureDates.size();i++) {
			XYChart.Data<String, Integer> ratingData = new XYChart.Data<String,Integer>(lectureDates.get(i), numbersList.get(i));
			series.getData().add(ratingData);
		}
		return series;
	}
	
	public XYChart.Series<String, Integer> createSeries(ArrayList<Integer> numbersList, String label) {
		
		XYChart.Series<String, Integer> series = new XYChart.Series<String, Integer>();
		for (int i = 0;i<lectureDates.size();i++) {
			XYChart.Data<String, Integer> ratingData = new XYChart.Data<String,Integer>(lectureDates.get(i), numbersList.get(i));
			series.getData().add(ratingData);
			series.setName(label);
		}
		return series;
	}
	
	public void setEvaluationData(XYChart.Series<String, Integer> list) {
		lineChart.getData().add(list);
	}
	
	
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
		
		course.setRatingsOverTime();
		
		lectureDates = FXCollections.observableArrayList(course.getDateArrayForGraph());
		
		xAxis.setCategories(lectureDates);
		
		
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
		
		xAxis.setLabel("Date");
		yAxis.setLabel("Percent");
		
		
		
		
	}

}
