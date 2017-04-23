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
import javafx.stage.Stage;

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

	DBController DBC = new DBController();

	public void userButtons(ActionEvent event, Stage stage) throws IOException {
		if (event.getSource() == back) {
			loadNextScene(back, "LoginStud.fxml");
		}
	}

	public void createStudentUser(String name) {
		DBC.insertStudent(name, "");
	}

	public void handleKeyAction(KeyEvent ke) throws IOException {
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
			MainController.getInstance().createStudUsername = username.getText();
			MainController.getInstance().createUser = true;
			loadNextScene(finish, "LoginStud.fxml");
		}
	}

	@FXML
	private void handleButtonAction(ActionEvent event) throws IOException {
		Stage stage = null;
		userButtons(event, stage);

		if (event.getSource() == finish) {
			handleCreateUser();
		}
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method

	}

}
