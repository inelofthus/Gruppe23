package gui;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

/**
 * InfoPopupController --- InfoPopupController is a class that controls all
 * interaction user interaction with the InfoPopup.fxml GUI
 * 
 * @author Group 23: Ine Lofthus Arnesen, Kari Meling Johannessen, Nicolai
 *         Cappelen Michelet, Magnus Tvilde
 */
public class InfoPopupController implements Initializable {

	@FXML
	public Rectangle titleBar;
	public Text title;
	public Label message;

	MainController mc = MainController.getInstance();

	/**
	 * Initialises the InfoPopup.fxml GUI
	 */
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		title.setText(mc.getPopupTitle());
		message.setText(mc.getPopupMessage());

	}
}
