package gui;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class PopupController implements Initializable {
	
	@FXML
	public Button closeButton;
	public Text title;
	public Label message;	
	
	public void handleButtonAction(ActionEvent event){
		if(event.getSource() == closeButton){
			Stage stage = (Stage) closeButton.getScene().getWindow();
			stage.close();
			MainController.getInstance().setConnectionPopupOpen(false);
		}
	}
	
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		
	}
	
}
