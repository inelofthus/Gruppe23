package gui;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import database.DBController;
import databaseobjects.Student;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.text.Text;

/**
 * CreateUserController --- CreateUserController is a class that controls all
 * interaction user interaction with the CreateUser.fxml GUI
 * 
 * @author Group 23: Ine Lofthus Arnesen, Kari Meling Johannessen, Nicolai
 *         Cappelen Michelet, Magnus Tvilde
 */
public class CreateUserController extends CommonMethods implements Initializable {

	// fxml objects
	@FXML
	public Text badUsername;
	public Text userCreated;
	public Button back;
	public Button finish;
	public ToggleButton student;
	public ToggleButton professor;
	public ToggleGroup toggleGroup;
	public TextField username;

	private DBController DBC = new DBController();
	private MainController mc = MainController.getInstance();
	
	/**
	 * takes user to the correct page if user button (back) is pressed
	 */
	public void userButtons(ActionEvent event) throws IOException {
		if (event.getSource() == back) {
			loadNextScene(back, "LoginStud.fxml");
		}
	}

	private void createStudentUser(String name) {
		DBC.insertStudent(name, "");
	}

	@FXML
	private void handleKeyAction(KeyEvent ke) throws IOException {
		if (ke.getCode().equals(KeyCode.ENTER)) {
			handleCreateUser();
		}
	}

	private void handleCreateUser() throws IOException {
		boolean validInput = true;
		Student stud = new Student(username.getText());
		if (stud.existsInDB()) {
			validInput = false;
			System.out.println(validInput);
			badUsername.setText("Username taken, please make a new one");
			return;
		}
		if (stud.getUsername().length() > 30) {
			validInput = false;
			System.out.println(validInput);
			badUsername.setText("Username too long, please make a new one");
			return;
		}
		if (username.getText().isEmpty()) {
			validInput = false;
			System.out.println(validInput);
			badUsername.setText("Please write a username");
			return;
		}

		if (validInput) {
			createStudentUser(username.getText());
			mc.setCreateStudUsername(username.getText()); 
			mc.setCreateUser(true); 
			loadNextScene(finish, "LoginStud.fxml");
		}
	}

	@FXML
	private void handleButtonAction(ActionEvent event) throws IOException {
		userButtons(event);
		if (event.getSource() == finish) {
			handleCreateUser();
		}
	}

	/**
	 * Initialises the CreateUser.fxml GUI
	 */
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method
	}

}
