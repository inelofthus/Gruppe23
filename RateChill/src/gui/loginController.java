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
	public Button student;
	public Button professor;
	public TextField username; 
	public PasswordField password;
	public Text usernameError;
	public Text passwordError;
	public Hyperlink newUser;
	
	String noPass = password.getText();
	
	
	
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
	
	public void handleButtonAction(ActionEvent event) throws IOException, NoSuchAlgorithmException{
		Stage stage = null;
		String errorMsg = "User doesn't exist. Try again";
		
		
		if(event.getSource()==student){
			Student stud = new Student(username.getText());
			
			
	    	//checks if the student username exists
			if(stud.existsInDB()) {
				//if(password.getText() == noPass) {
					mainController.getInstance().setStudent(stud);
					loadNextScene(student, stage, "courseStud.fxml");
				//}
				//passwordError.setText("Leave empty if student.");
				//return;
			}
			
	    	usernameError.setText(errorMsg);
	    	return;
	    }
	    
	    else if (event.getSource()==professor){
	    	Professor prof = new Professor(username.getText(), Professor.hashPassword(password.getText()));
	    	if(prof.existsInDB()) {
	    		//if (prof.isCorrectPassword(Professor.hashPassword(password.getText()))) {
	    			mainController.getInstance().setProfessor(prof);
	    			loadNextScene(professor, stage, "courseProf.fxml");
	    		//}
	    		//passwordError.setText("Incorrect password, try again");
	    		//return;
	    	}
	    	passwordError.setText("");
	    	usernameError.setText(errorMsg);
	    	return;
	    }
		
	    else if (event.getSource()==newUser){
	    	loadNextSceneHyperlink(newUser, stage, "createUser.fxml");
	    	return;
	    }
		
	    
	}
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		String noPass = password.getText();
		
	}

}
