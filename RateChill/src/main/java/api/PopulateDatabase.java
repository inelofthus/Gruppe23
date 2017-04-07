package api;

import java.io.IOException;

public class PopulateDatabase {
	public static void main(String[] args) throws IOException {
		IME_API ime = new IME_API();
		ime.getApiInfo(); //Updates courses and lectures in database
	}
}
