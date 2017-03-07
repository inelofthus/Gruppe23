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
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class evaluationProfController implements Initializable {

	//fxml objects
	@FXML
	public Button perfect;
	public Button ok;
	public Button tooFast;
	public Button tooSlow;
	public Button confused;
	public Button perfectNumber;
	public Button okNumber;
	public Button tooFastNumber;
	public Button tooSlowNumber;
	public Button confusedNumber;
	public Button seePerfectComments;
	public Button seeOkComments;
	public Button seeFastComments;
	public Button seeSlowComments;
	public Button seeConfusedComments;
	public Text debugText;
	public Text submitted;
	public Text overwriteText;
	
	@FXML
	public BarChart<String, Integer> barchart;
	public CategoryAxis xAxis;
	private ObservableList<String> evaluationTypes = FXCollections.observableArrayList();
	
	Integer lectureID = mainController.getInstance().getChosenProfessorLecture();
	
	private Lecture lecture = new Lecture(lectureID);
	//ArrayList evaluations = lec.getEvaluations();
	
	
	
	
	
	public void loadNextScene (Stage stage, Button button, String string) throws IOException{
		Parent root;
		stage = (Stage) button.getScene().getWindow();
		root = FXMLLoader.load(getClass().getResource(string));
		Scene scene = new Scene(root);
		stage.setScene(scene);
	    stage.show();
	}
	
	
	@FXML
	private void handleButtonAction(ActionEvent event) throws IOException{
		Stage stage = null;
		if (event.getSource() == seePerfectComments){
			loadNextScene(stage, seePerfectComments, "commentPage.fxml");
		}
		
		else if (event.getSource() == seeOkComments) {
			loadNextScene(stage, seeOkComments, "commentPage.fxml");
		}
		
		else if (event.getSource() == seeFastComments) {
			loadNextScene(stage, seeFastComments, "commentPage.fxml");
		}
		
		else if (event.getSource() == seeSlowComments) {
			loadNextScene(stage, seeSlowComments, "commentPage.fxml");
		}
		
		else if (event.getSource() == seeConfusedComments) {
			loadNextScene(stage, seeConfusedComments, "commentPage.fxml");
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
		evaluationTypes.addAll("Perfect", "Ok", "Too Fast!", "Too Slow!", "Confused.. ?");
		
		xAxis.setCategories(evaluationTypes);
		
		//storing the number of evaluations in integers
		Integer numOfPerfect = lecture.getRatingCount("Perfect");
		Integer numOfOk = lecture.getRatingCount("Ok");
		Integer numOfFast = lecture.getRatingCount("Too Fast!");
		Integer numOfSlow = lecture.getRatingCount("Too Slow!");
		Integer numOfConfused = lecture.getRatingCount("Confused.. ?");
		
		ArrayList<Integer> numbersList = new ArrayList<Integer>();
		numbersList.addAll(Arrays.asList(numOfPerfect, numOfOk,numOfFast, numOfSlow,numOfConfused));
		
		setEvaluationData(createSeries(numbersList));
		
		//setting the appropriate number on the textfields
		/*perfectNumber.setText(numOfPerfect.toString());
		okNumber.setText(numOfOk.toString());
		tooFastNumber.setText(numOfFast.toString());
		tooSlowNumber.setText(numOfSlow.toString());
		confusedNumber.setText(numOfConfused.toString());
		*/
	}

}
