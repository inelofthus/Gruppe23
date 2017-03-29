package gui;

import java.io.IOException;
import java.net.URL;
import java.security.NoSuchAlgorithmException;
import java.util.ResourceBundle;

import databaseobjects.Professor;
import databaseobjects.Student;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class loginController implements Initializable {

	
	@FXML
	public Button backButton;
	public Button student;
	public Button professor;
	public TextField username; 
	public PasswordField password;
	public Text usernameError;
	public Text passwordError;
	public Hyperlink newStudent;
	public Hyperlink newProfessor;
	public Button loginStud;
	public Button loginProf;
	
	
	public void loadNextScene(Button button, Stage stage, String string) throws IOException{
		stage=(Stage) button.getScene().getWindow();
		Parent root;
		root = FXMLLoader.load(getClass().getResource(string));
		
		//create a new scene with root and set the stage
		Scene scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
	}
	
	public void loadNextSceneHyperlink(Hyperlink hyper, Stage stage, String string) throws IOException{
			stage=(Stage) hyper.getScene().getWindow();
			Parent root;
			root = FXMLLoader.load(getClass().getResource(string));
			
			//create a new scene with root and set the stage
			Scene scene = new Scene(root);
			stage.setScene(scene);
			stage.show();
	}
	
	//method for loading next scene based on user 
	public void whichUser (ActionEvent event) throws IOException {
		Stage stage = null;
		if (event.getSource()==student){
			loadNextScene(student, stage, "loginStud.fxml");
		}
		else if (event.getSource()==professor){
			loadNextScene(professor, stage, "loginProf.fxml");
		}
		
	}
	
	//returns you to login.fxml
	
	public void userButtons(ActionEvent event, Stage stage) throws IOException{
		if (event.getSource() == backButton) {
			loadNextScene(backButton, stage, "login.fxml");
		}
	} 
	
	
	public void handleButtonAction(ActionEvent event) throws IOException, NoSuchAlgorithmException{
		Stage stage = null;
		userButtons(event, stage);
		String errorMsg = "User doesn't exist. Try again";
		
		
		if(event.getSource()==loginStud){
			Student stud = new Student(username.getText());
			
			
	    	//checks if the student username exists
			if(stud.existsInDB()) {
				mainController.getInstance().setStudent(stud);
				loadNextScene(loginStud, stage, "courseStud.fxml");
			}
			
	    	usernameError.setText(errorMsg);
	    	return;
	    }
	    
	    else if (event.getSource()==loginProf){
			Professor prof = new Professor(username.getText());
			if(prof.existsInDB() && prof.hasPassword()) {
				usernameError.setText("");
				if (prof.isCorrectPassword(Professor.hashPassword(password.getText()))) {
					mainController.getInstance().setProfessor(prof);
					loadNextScene(loginProf, stage, "courseProf.fxml");
				}
				passwordError.setText("Incorrect password, try again");
				return;
			}
			passwordError.setText("");
			usernameError.setText(errorMsg);
			return;
		}
		
		else if (event.getSource()==newStudent){
			loadNextSceneHyperlink(newStudent, stage, "createUser.fxml");
			return;
		}
		
		else if (event.getSource()==newProfessor){
			loadNextSceneHyperlink(newProfessor, stage, "createProfUser.fxml");
			return;
		}
		
	}
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
	}

}
