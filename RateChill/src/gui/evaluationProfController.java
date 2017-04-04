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
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class evaluationProfController implements Initializable {

	//fxml objects
	@FXML
	public Button seeComments;
	public Text debugText;
	public Text submitted;
	public Text overwriteText;
	public Button home;
	public Button back;
	public Button logout;
	public Button customize;

	Course course = mainController.getInstance().getCourse();
	
	@FXML
	public BarChart<String, Integer> barchart;
	public CategoryAxis xAxis;
	private ObservableList<String> evaluationTypes = FXCollections.observableArrayList();
	
	private Lecture lecture = mainController.getInstance().getLecture();
	
//	private Lecture lecture = new Lecture(lectureID);
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
			mainController.getInstance().setNextPreviousView(mainController.getInstance().getPreviousView());
			mainController.getInstance().setPreviousView("individualCharts.fxml");
			return;
		}
		else if (event.getSource() == seeComments){
			loadNextScene(seeComments, stage, "commentPage.fxml");
			mainController.getInstance().setNextPreviousView(mainController.getInstance().getPreviousView());
			mainController.getInstance().setPreviousView("individualCharts.fxml");
			return;
		}
		else if (event.getSource() == customize) {
			loadNextScene(customize, stage, "customizeButtons.fxml");
			mainController.getInstance().setNextPreviousView(mainController.getInstance().getPreviousView());
			mainController.getInstance().setPreviousView("individualCharts.fxml");
			return;
		}
	}
	
	
	public XYChart.Series<String, Integer> createSeries(ArrayList<Integer> numbersList) {
		
		XYChart.Series<String, Integer> series = new XYChart.Series<String, Integer>();
		for (int i = 0;i<evaluationTypes.size();i++) {
			XYChart.Data<String, Integer> ratingData = new XYChart.Data<String,Integer>(evaluationTypes.get(i), numbersList.get(i));
			series.getData().add(ratingData);
		}
		return series;
	}
	
	public void setEvaluationData(XYChart.Series<String, Integer> list) {
		barchart.getData().add(list);
	}
	
	
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method
		ArrayList<String> ratingValues = lecture.getRatingValues();
		
		if(ratingValues.size() ==6){
			ratingValues.remove(5);
		}
		
		evaluationTypes.addAll(ratingValues);
		
		xAxis.setCategories(evaluationTypes);
		
		//storing the number of evaluations in integers
		Integer numRating1 = lecture.getRatingCount(1);
		Integer numRating2 = lecture.getRatingCount(2);
		Integer numRating3 = lecture.getRatingCount(3);
		Integer numRating4 = lecture.getRatingCount(4);
		Integer numRating5 = lecture.getRatingCount(5);
		
		ArrayList<Integer> numbersList = new ArrayList<Integer>();
		numbersList.addAll(Arrays.asList(numRating1, numRating2,numRating3, numRating4,numRating5));
		
		setEvaluationData(createSeries(numbersList));
	}

}
