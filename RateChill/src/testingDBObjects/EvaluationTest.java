package testingDBObjects;

import static org.junit.Assert.assertEquals;
import org.junit.Test;
import database.DBController;
import databaseobjects.Evaluation;

public class EvaluationTest {

	// A dummy DBC has no interaction with database. It just sets values as described in exampleValues.txt
	DBController dummyDBC = new DummyDBController();
	Evaluation eval = new Evaluation(1, "karimj@stud.ntnu.no", dummyDBC);
	
	@Test
	public void testgetRating() {
		String actual = eval.getRating();
		String expected = "Ok";
		
		assertEquals(expected, actual);
	}
	
	@Test
	public void testgetComment() {
		String actual = eval.getComment();
		String expected = "yeah it was ok";
		
		assertEquals(expected, actual);
	}	
	
	@Test
	public void testgetLectureid() {
		int actual = eval.getLectureid();
		int expected = 1;
		
		assertEquals(expected, actual);
	}
	
	
}
