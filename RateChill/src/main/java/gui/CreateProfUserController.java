package gui;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import database.DBController;
import databaseobjects.Professor;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class CreateProfUserController implements Initializable {

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
	
	DBController DBC = new DBController();
	
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
		if (event.getSource() == back) {
			loadNextScene(back, stage, "LoginProf.fxml");
		}
	}
	
	@FXML
	private void handleButtonAction(ActionEvent event) throws IOException{
		Stage stage = null;
		userButtons(event, stage);
		
		
		if (event.getSource() == finish){
			boolean errors = false;
			if (username.getText().isEmpty()) {
				badUsername.setText("Please create a username");
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
				Professor prof = new Professor(username.getText());
				if (prof.hasPassword()) {
					badUsername.setText("This professor already has a user");
					return;
				}else badUsername.setText("");
				if(!prof.existsInDB()) {
					badUsername.setText("Not a valid professor username");
					return;
				}else badUsername.setText("");
				DBC.updateProfessor(username.getText(), Professor.hashPassword(password.getText()));
				MainController.getInstance().createUser = true;
				loadNextScene(finish, stage, "LoginProf.fxml");
			}
		}			
	}
	
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method
		
	}

}
