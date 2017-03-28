package gui;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.ResourceBundle;

import databaseobjects.Course;
import databaseobjects.Lecture;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class evaluationsOverTimeController implements Initializable {

	//fxml objects
	@FXML
	public Text debugText;
	public Button home;
	public Button logout;
	public Button back;
	
	
	@FXML
	public LineChart<String, Integer> lineChart;
	public CategoryAxis xAxis;
	public NumberAxis yAxis;
	private ObservableList<String> lectureDates = FXCollections.observableArrayList();
	
	Course course = mainController.getInstance().getCourse();
	
	
	
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
			loadNextScene(home, stage, "lectureProf.fxml");
		}
		
		if (event.getSource() == logout) {
			loadNextScene(logout, stage, "login.fxml");
		}
	}
	
	
	//needs a fix
	@FXML
	private void handleButtonAction(ActionEvent event) throws IOException{
		Stage stage = null;
		userButtons(event, stage);
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
		
		
		ArrayList<Integer> perfectCount = course.getLecRatingCounts(1);
		
		ArrayList<Integer> okCount = course.getLecRatingCounts(2);
		
		ArrayList<Integer> tooFastCount = course.getLecRatingCounts(3);
		
		ArrayList<Integer> tooSlowCount = course.getLecRatingCounts(4);
		
		ArrayList<Integer> confusedCount = course.getLecRatingCounts(5);
		
		setEvaluationData(createSeries(perfectCount, "Perfect"));	
		setEvaluationData(createSeries(okCount, "Ok"));		
		setEvaluationData(createSeries(tooFastCount, "Too Fast"));	
		setEvaluationData(createSeries(tooSlowCount, "Too Slow"));		
		setEvaluationData(createSeries(confusedCount, "Confused"));	
		
		lineChart.setCreateSymbols(false);
		
		xAxis.setLabel("Date");
		yAxis.setLabel("Percent");
		
		
		
		
	}

}
