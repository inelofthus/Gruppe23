package databaseobjects;

import static org.junit.Assert.assertEquals;
import org.junit.Test;
import database.DBController;
import databaseobjects.Evaluation;

public class EvaluationTest {

	// A dummy DBC has no interaction with database. It just sets values as described in exampleValues.txt
	DBController dummyDBC = new DummyDBController();
	Evaluation eval = new Evaluation(1, "karimj@stud.ntnu.no", dummyDBC);
	Evaluation eval2 = new Evaluation(1, "stupidStud", dummyDBC);
	
	@Test
	public void TestgetRating() {
		String actual = eval.getRating();
		String expected = "Ok";
		
		assertEquals(expected, actual);
	}
	
	@Test
	public void TestgetComment() {
		String actual = eval.getComment();
		String expected = "yeah it was ok";
		
		assertEquals(expected, actual);
	}	
	
	@Test
	public void TestgetLectureid() {
		int actual = eval.getLectureID();
		int expected = 1;
		
		assertEquals(expected, actual);
	}
	
	@Test
	public void TestgetstudentUsername(){
		String actual = eval.getstudentUsername();
		String expected = "karimj@stud.ntnu.no";
		
		assertEquals(expected, actual);
	}
	
	
}
