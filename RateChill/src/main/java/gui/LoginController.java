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
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

/**
 * LoginController --- LoginController is a class that controls all
 * interaction user interaction with the login GUIs
 * 
 * @author Group 23: Ine Lofthus Arnesen, Kari Meling Johannessen, Nicolai
 *         Cappelen Michelet, Magnus Tvilde
 */
public class LoginController extends CommonMethods implements Initializable {

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
	
	private DBController dbc = new DBController();
	private MainController mc = MainController.getInstance();
	
	//method for loading next scene based on user 
	@FXML
	private void whichUser (ActionEvent event) throws IOException {
		if (event.getSource()==student){
			loadNextScene(student,  "LoginStud.fxml");
		}
		else if (event.getSource()==professor){
			loadNextScene(professor,  "LoginProf.fxml");
		}
		
	}
	
	@FXML
	private void handleKeyActionLogin(KeyEvent ke) throws IOException{
		if(ke.getCode().equals(KeyCode.ENTER)){
			if (professor.isFocused()){
				loadNextScene(professor,  "LoginProf.fxml");
			}
			else if (student.isFocused()) {
				loadNextScene(student,  "LoginStud.fxml");
			}
		}
	}
	
	/**
	 * returns you to login.fxml
	 */
	public void userButtons(ActionEvent event) throws IOException{
		if (event.getSource() == back) {
			loadNextScene(back,  "Login.fxml");
		}
	} 
	
	// --- PROFESSOR LOGIN ---//
	@FXML
	private void handleKeyActionProf(KeyEvent ke) throws IOException{
		if(ke.getCode().equals(KeyCode.ENTER)){
			System.out.println("login as prof");
			loginProf();
		}
	}
		
	private void loginProf() throws IOException {
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
				loadNextScene(loginProf,  "CourseProf.fxml");
			}
			passwordError.setText("Incorrect password, try again");	
			return;
		}
		else{
			usernameError.setText("User does not exist. Create a user. ");
		}
	}

	// --- STUDENT LOGIN ---//
	@FXML
	private void handleKeyActionStud(KeyEvent ke) throws IOException{
		if(ke.getCode().equals(KeyCode.ENTER)){
			loginStud();
		}
	}
	
	private void loginStud() throws IOException {
		String errorMsg = "User does not exist. Try again";
		Student stud = new Student(studUsername.getText());
		
		if(stud.existsInDB()) {
			mc.setStudent(stud);
			loadNextScene(loginStud,  "CourseStud.fxml");
		}
		
		if (studUsername.getText().isEmpty()){
			errorMsg = "Please write in a username.";
		}
    	usernameError.setText(errorMsg);
    	return;
		
	}

	@FXML
	private void handleButtonAction(ActionEvent event) throws IOException, NoSuchAlgorithmException{
		userButtons(event);
		if(event.getSource()==loginStud  ){
			loginStud();
	    }
	    
	    else if (event.getSource()==loginProf){
			loginProf();
		}
		
		else if (event.getSource()==newStudent){
			loadNextSceneHyperlink(newStudent,  "CreateUser.fxml");
			return;
		}
		
		else if (event.getSource()==newProfessor){
			loadNextSceneHyperlink(newProfessor,  "CreateProfUser.fxml");
			return;
		}
		
	}

	/**
	 * Initialises the login GUIs
	 */
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		try {
			if (mc.isCreateUser()){
				errorText.setText("User successfully created");
				errorBar.setFill(Color.DARKSEAGREEN);
				errorBar.setVisible(true);
				mc.setCreateUser(false);
			}
			if (!mc.getCreateProfUsername().isEmpty()){
				profUsername.setText(mc.getCreateProfUsername());
			}
			if (!mc.getCreateStudUsername().isEmpty()){
				studUsername.setText(mc.getCreateStudUsername());
			}
			
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

	}

}
