import validation.ValidationException;
import validation.Validator;

public class Main {
	
	private static String name      = "yourName";
	private static String birthDate = "01/01/1990";
	private static String CPR       = "123456789";
	private static String email     = "example@domain@com";

	public static void main(String[] args) {
		
		Validator validator = new Validator();
		
		try {
			validator.addFieldRules("name", name, "length:10");
			validator.addFieldRules("birth date", birthDate, "required|format:dd-mm-yyyy");
			validator.addFieldRules("CPR Field", CPR, "required|digits:10");
			validator.addFieldRules("email address", email, "required|email");
			
			if(validator.pass())
				System.out.println("Passed successfully");
			else
				for(String errorMessage : validator.getErrorMessages())
					System.out.println(errorMessage);
		} catch (ValidationException e) {
			e.printStackTrace();
		}
	}
}
