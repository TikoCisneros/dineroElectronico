package dinero.electronico.services;

public class Test {

	public static void main(String[] args) {
		try {
			Mailer.generateAndSendEmail("tiko-0489@hotmail.es","Saludos","Prueba");
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
