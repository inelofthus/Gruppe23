package gui;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.ResourceBundle;

import databaseobjects.Lecture;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.AreaChart;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class evaluationsOverTimeController implements Initializable {

	//fxml objects
	@FXML
	public Button seeComments;
	public Text debugText;
	public Button home;
	public Button logout;
	public Button back;
	
	
	@FXML
	public AreaChart<String, Integer> areaChart;
	public CategoryAxis xAxis;
	private ObservableList<String> lectureDates = FXCollections.observableArrayList();
	
	//Integer lectureID = mainController.getInstance().getChosenProfessorLecture();
	
	//private Lecture lecture = new Lecture(lectureID);
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
		
		
		if (event.getSource() == seeComments){
			loadNextScene(seeComments, stage, "commentPage.fxml");
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
	
	public void setEvaluationData(XYChart.Series<String, Integer> list) {
		areaChart.getData().add(list);
	}
	
	
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method
		//evaluationTypes.addAll("Perfect", "Ok", "Too Fast!", "Too Slow!", "Confused.. ?");
		
		
		
		lectureDates.addAll("02.02", "16.02", "02.03", "16.03", "02.04", "16.04", "02.05");
		//xAxis.setCategories(evaluationTypes);
		xAxis.setCategories(lectureDates);
		//storing the number of evaluations in integers
		/*Integer numOfPerfect = lecture.getRatingCount("Perfect");
		Integer numOfOk = lecture.getRatingCount("Ok");
		Integer numOfFast = lecture.getRatingCount("Too Fast!");
		Integer numOfSlow = lecture.getRatingCount("Too Slow!");
		Integer numOfConfused = lecture.getRatingCount("Confused.. ?");*/
		
		Integer numOfPerfect = 3;
		Integer numOfOk = 1;
		Integer numOfFast = 2;
		Integer numOfSlow = 7;
		Integer numOfConfused = 2;
		
		ArrayList<Integer> numbersList = new ArrayList<Integer>();
		numbersList.addAll(Arrays.asList(numOfPerfect, numOfOk,numOfFast, numOfSlow,numOfConfused, 4, 2));
		
		ArrayList<Integer> numList2 = new ArrayList<Integer>();
		numList2.addAll(Arrays.asList(1, 10, 19, 2, 3, 4, 3));
		
		setEvaluationData(createSeries(numbersList));
		setEvaluationData(createSeries(numList2));
		
		//setting the appropriate number on the textfields
		/*perfectNumber.setText(numOfPerfect.toString());
		okNumber.setText(numOfOk.toString());
		tooFastNumber.setText(numOfFast.toString());
		tooSlowNumber.setText(numOfSlow.toString());
		confusedNumber.setText(numOfConfused.toString());
		*/
	}

}
