package gui;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import database.DBController;
import databaseobjects.Professor;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.text.Text;

/**
 * CreateProfUserController --- CreateProfUserController is a class that controls all
 * interaction user interaction with the CreateProfUser.fxml GUI
 * 
 * @author Group 23: Ine Lofthus Arnesen, Kari Meling Johannessen, Nicolai
 *         Cappelen Michelet, Magnus Tvilde
 */
public class CreateProfUserController extends CommonMethods implements Initializable {

	//fxml objects
	@FXML
	public Text badUsername;
	public Text passwordNoMatch;
	public Text userCreated;
	public Button back;
	public Button finish;
	public TextField username;
	public PasswordField password;
	public PasswordField RepeatPassword;
	
	private DBController DBC = new DBController();	
	private MainController mc = MainController.getInstance();
	
	/**
	 * takes user to the correct page if user button (back) is
	 */
	public void userButtons(ActionEvent event) throws IOException{
		if (event.getSource() == back) {
			loadNextScene(back,  "LoginProf.fxml");
		}
	}
	
	@FXML
	private void handleKeyAction(KeyEvent ke) throws IOException{
		if(ke.getCode().equals(KeyCode.ENTER)){
			handleCreateProf();
		}
	}
	
	private void handleCreateProf() throws IOException{
		boolean errors = false;
		if (username.getText().isEmpty()) {
			badUsername.setText("Please write your NTNU username");
			errors = true;
			return;
		}else badUsername.setText("");
		if (password.getText().isEmpty()) {
			passwordNoMatch.setText("Please set a password");
			errors = true;
			return;
		}else passwordNoMatch.setText("");
		if (RepeatPassword.getText().isEmpty()) {
			passwordNoMatch.setText("Please repeat the password");
			errors = true;
			return;
		}else passwordNoMatch.setText("");
		if(username.getText().length() > 10){
			badUsername.setText("Username too long (max 10 characters)");
			errors = true;
			return;
		}else badUsername.setText("");
		if (!password.getText().equals(RepeatPassword.getText())) {
			passwordNoMatch.setText("Passwords don't match");
			errors = true;
			return;
		}else passwordNoMatch.setText("");
		if (password.getText().length()<3) {
			passwordNoMatch.setText("Your password is too short");
			errors = true;
			return;
		}else passwordNoMatch.setText("");
		if (!errors){	
			if (DBC.professorExists(username.getText())) {
				Professor prof = new Professor(username.getText());
				if (prof.hasPassword()) {
					badUsername.setText("This professor already has a user");
					return;
				} else
					badUsername.setText("");
				DBC.updateProfessor(username.getText(), Professor.hashPassword(password.getText()));
				mc.createProfUsername = username.getText();
				mc.createUser = true;
				loadNextScene(finish, "LoginProf.fxml");
				
			}else{
				badUsername.setText("The professor username is not valid. Please write in your ntnu username.");
					return;
			}			
		}
	}
	
	@FXML
	private void handleButtonAction(ActionEvent event) throws IOException{
		userButtons(event);
		if (event.getSource() == finish){
			handleCreateProf();
		}			
	}

	/**
	 * Initialises the CreateProfUser.fxml GUI
	 */
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// No changes needed to default		
	}

}
