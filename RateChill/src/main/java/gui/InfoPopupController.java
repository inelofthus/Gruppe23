package gui;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class InfoPopupController implements Initializable{

	@FXML
	public Rectangle titleBar;
	public Button closeButton;
	public Button keepGoing;
	public Text title;
	public Label message;
	
	MainController mc = MainController.getInstance();

	@FXML
	private void handleButtonAction(ActionEvent event) throws IOException{
		
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		title.setText(mc.getPopupTitle());
		message.setText(mc.getPopupMessage());
		
	}


		
	}

