package gui;

import java.io.IOException;
import java.net.URL;
import java.security.NoSuchAlgorithmException;
import java.util.ResourceBundle;

import database.DBController;
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
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class LoginController implements Initializable {

	
	@FXML
	public Button back;
	public Button student;
	public Button professor;
	public TextField profUsername; 
	public TextField studUsername; 
	public PasswordField password;
	public Text usernameError;
	public Text passwordError;
	public Hyperlink newStudent;
	public Hyperlink newProfessor;
	public Button loginStud;
	public Button loginProf;
	public Text errorText;
	public Rectangle errorBar;
	
	DBController dbc = new DBController();
	
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
			loadNextScene(student, stage, "LoginStud.fxml");
		}
		else if (event.getSource()==professor){
			loadNextScene(professor, stage, "LoginProf.fxml");
		}
		
	}
	
	public void handleKeyActionLogin(KeyEvent ke) throws IOException{
		Stage stage = null;
		if(ke.getCode().equals(KeyCode.ENTER)){
			if (professor.isFocused()){
				loadNextScene(professor, stage, "LoginProf.fxml");
			}
			else if (student.isFocused()) {
				loadNextScene(student, stage, "LoginStud.fxml");
			}
		}
	}
	
	//returns you to login.fxml
	
	public void userButtons(ActionEvent event, Stage stage) throws IOException{
		if (event.getSource() == back) {
			loadNextScene(back, stage, "Login.fxml");
		}
	} 
	
	public void handleKeyActionProf(KeyEvent ke) throws IOException{
		if(ke.getCode().equals(KeyCode.ENTER)){
			System.out.println("login as prof");
			loginProf();
		}
	}
	
	private void loginProf() throws IOException {
		Stage stage = null;
		usernameError.setText("");
		passwordError.setText("");
    	if (profUsername.getText().isEmpty() || password.getText().isEmpty() ){
    		if (profUsername.getText().isEmpty()){
    			usernameError.setText("Please type in a username");
    		}
    		if (password.getText().isEmpty()){
    			passwordError.setText("Please type in a password");
    		}
    		return;
    	}
		if (!dbc.professorExists(profUsername.getText())){
    		usernameError.setText("Wrong username or password");
    		return;
    	}
		Professor prof = new Professor(profUsername.getText());
		if(prof.existsInDB() && prof.hasPassword()) {
			if (prof.isCorrectPassword(Professor.hashPassword(password.getText()))) {
				MainController.getInstance().setProfessor(prof);
				loadNextScene(loginProf, stage, "CourseProf.fxml");
			}
			passwordError.setText("Incorrect password, try again");	
			return;
		}
		
		
	}

	public void handleKeyActionStud(KeyEvent ke) throws IOException{
		if(ke.getCode().equals(KeyCode.ENTER)){
			loginStud();
		}
	}
	
	private void loginStud() throws IOException {
		Stage stage = null;
		String errorMsg = "User does not exist. Try again";
		Student stud = new Student(studUsername.getText());
		
		
    	//checks if the student username exists
		if(stud.existsInDB()) {
			MainController.getInstance().setStudent(stud);
			loadNextScene(loginStud, stage, "CourseStud.fxml");
		}
		
		if (studUsername.getText().isEmpty()){
			errorMsg = "Please write in a username.";
		}
    	usernameError.setText(errorMsg);
    	return;
		
	}

	public void handleButtonAction(ActionEvent event) throws IOException, NoSuchAlgorithmException{
		Stage stage = null;
		userButtons(event, stage);
		String errorMsg = "User doesn't exist. Try again";
		
		if(event.getSource()==loginStud  ){
			loginStud();
	    }
	    
	    else if (event.getSource()==loginProf){
			loginProf();
		}
		
		else if (event.getSource()==newStudent){
			loadNextSceneHyperlink(newStudent, stage, "CreateUser.fxml");
			return;
		}
		
		else if (event.getSource()==newProfessor){
			loadNextSceneHyperlink(newProfessor, stage, "CreateProfUser.fxml");
			return;
		}
		
	}
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		if (MainController.getInstance().createUser){
			errorText.setText("User successfully created");
			errorBar.setFill(Color.DARKSEAGREEN);
			errorBar.setVisible(true);
			MainController.getInstance().createUser = false;
		}
	}

}
