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

/**
 * Controller for handling popup error for no connection
 * @author Kari M. Johannessen, Ine L. Arnesen, Magnus Tvilde, Nicolai C. Michelet
 */
public class PopupController implements Initializable {
	
	@FXML
	public Button closeButton;
	public Text title;
	public Label message;	
	
	/**
	 * Closes the popup when pressing the close button
	 * @param event
	 */
	public void handleButtonAction(ActionEvent event){
		if(event.getSource() == closeButton){
			Stage stage = (Stage) closeButton.getScene().getWindow();
			stage.close();
			MainController.getInstance().setConnectionPopupOpen(false);
		}
	}
	
	/**
	 * No changes made to default initialize
	 */
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {}	

}
