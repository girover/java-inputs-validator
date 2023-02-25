import validation.Field;
import validation.ValidationException;
import validation.Validator;
import validation.rules.Rule;

public class Main {
	private static String email = "majedgmail.com";
	private static String emailConfirm = "jaungmail.com";
	private static String name  = "name";
	private static String phone = "11111111";
	private static String cpr   = "62314566544";
	private static String userRole = "userr";
	private static String age = "50";
	private static String date = "12/11-2023";
	private static String tall = "12A";
	private static String required = "fsdfsd";

	public static void main(String[] args) {
		
		test();
		
	}

	private static void test() {
		try {
			Validator validator = new Validator();
			
			validator.addFieldRules("cpr", cpr, "length_min:6|length_max:10|min:111111|max:555555");
			validator.addFieldRules("userRole", userRole, "alpha|in:user,admin,student");
			validator.addFieldRules("age", age, "numeric|gte:18|lte:50");
			validator.addFieldRules("date", date, "date|format:dd/mm/yyyy");
			validator.addFieldRules("email", email, "required|email");
			validator.addFieldRules("emailConfirm", emailConfirm, "same:email");
			validator.addFieldRules("tall", tall, "required|regex:[0-9A-Z]+");
			validator.addFieldRules("required", required, "required");
			validator.addFieldRules("name", "majed", "e");
			
			validator.addRuleMessage("date", "date", "date skal vaere en valid dato.");
			validator.addRuleMessage("email", "email", "Fuck you please provide a valid email address.");
			
			if(validator.pass(false)) 
				System.out.println("passed successfully");
			else
				for (String string : validator.getErrorMessages())
					System.out.println(string);
			for (Field field : validator.getFailedFields()) {
				System.out.print(field.getName()+" : ");
				for (Rule rule : field.getFailedRules()) {
					System.out.print(rule.getName()+" - ");
				}
			}
			
		} catch (ValidationException e) {
			e.printStackTrace();
		}
	}
}
