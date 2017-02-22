package databaseobjects;
import java.util.ArrayList;
import java.util.NoSuchElementException;


public class Professor extends DatabaseUser {
	String username;
	ArrayList<String> courses;
	
	public Professor(String username) {
		this.username = username;
		loadInfo();	
	}
	
	public boolean existsInDB(){
		return DBC.professorExists(username);
	}
	
	public void loadInfo(){
		try {
			courses = DBC.getCoursesTaughtByProfessor(username);
			
		} catch (Exception e) {
			// TODO: handle exception
			if (!existsInDB()) {
				throw new NoSuchElementException("No courses exists for this professor");
			}
			System.out.println(e.getMessage());
		}
	}

	public String getUsername() {
		return username;
	}

	public ArrayList<String> getCourses() {
		return courses;
	}
	
	public static void main(String[] args) {
		Professor prof = new Professor("pekkaa");
		System.out.println(prof.getCourses());
		System.out.println(prof.getUsername());
	}
	
}
