package gui;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * CommonMethods --- CommonMethods is an abstract class that contains all
 * methods that are common for the various guiControllers. All guiControllers
 * extend this class
 * 
 * @author Group 23: Ine Lofthus Arnesen, Kari Meling Johannessen, Nicolai
 *         Cappelen Michelet, Magnus Tvilde
 */
public abstract class CommonMethods {

	public Button home;
	public Button back;
	public Button logout;

	/*
	 * Helper function to loadNextScene methods
	 */
	private void loadNextScene(Stage stage, String fxmlName) throws IOException {
		Parent root;
		root = FXMLLoader.load(getClass().getResource(fxmlName));

		// create a new scene with root and set the stage
		Scene scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
	}

	/**
	 * Changes the scene from one fxml gui to the next. Uses a button to reference
	 * the stage
	 * 
	 * @param button
	 *            A JavaFX Button on the stage
	 * @param stage
	 *            The JavaFX stage that is being worked on
	 * @param fxmlName
	 *            the name of the next FXML scene to be loaded
	 * @throws IOException
	 *             is thrown if there is a problem loading the new scene
	 */
	public void loadNextScene(Button button, String fxmlName) throws IOException {
		Stage stage = (Stage) button.getScene().getWindow();
		loadNextScene(stage, fxmlName);
	}

	/**
	 * Changes the scene from one fxml gui to the next. Uses a hyperlink to
	 * reference the stage
	 * 
	 * @param hyper
	 *            A JavaFX Hyperlink on the stage
	 * @param stage
	 *            The JavaFX stage that is being worked on
	 * @param fxmlName
	 *            the name of the next FXML scene to be loaded
	 * @throws IOException
	 *             is thrown if there is a problem loading the new scene
	 */
	public void loadNextSceneHyperlink(Hyperlink hyper, String fxmlName) throws IOException {
		Stage stage = (Stage) hyper.getScene().getWindow();
		loadNextScene(stage, fxmlName);
	}

	/**
	 * Opens a popup on top of the new stage.
	 * @param fxmlName the name of the fxml gui to be opened
	 * @throws IOException
	 */
	public void loadPopupHyperLink(String fxmlName) throws IOException {
		Stage stage = new Stage();
		Parent root;
		root = FXMLLoader.load(getClass().getResource(fxmlName));

		// create a new scene with root and set the stage
		Scene scene = new Scene(root);
		stage.setScene(scene);
		stage.initModality(Modality.APPLICATION_MODAL);
		stage.show();
	}

	public void userButtons(ActionEvent event) throws IOException {
		if (event.getSource() == home) {
			loadNextScene(home, "CourseStud.fxml");
		}

		if (event.getSource() == back) {
			loadNextScene(back, "LectureStud.fxml");
		}

		if (event.getSource() == logout) {
			loadNextScene(logout, "Login.fxml");
		}
	}
}
