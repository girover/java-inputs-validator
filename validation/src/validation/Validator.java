package validation;

import java.util.ArrayList;

import validation.rules.ExplicitRule;
import validation.rules.Messages;
import validation.rules.ParameterizedRule;
import validation.rules.Rule;
import validation.rules.RuleMessage;
import validation.rules.RuleMessagesBag;

public class Validator {

	private RuleMessagesBag messagesBag;
	
	private ArrayList<Field> fieldsUnderValidation;
	private ArrayList<Field> failedFields;
	
	public Validator() {
		messagesBag  = new RuleMessagesBag();
		fieldsUnderValidation = new ArrayList<>();
		failedFields = new ArrayList<>();
	}
	
	public ArrayList<Field> getFieldsUnderValidation() {
		return fieldsUnderValidation;
	}
	
	private Field getFieldUnderValidationByName(String fieldName) {
		for (Field field : fieldsUnderValidation) {
			if(field.getName().equals(fieldName))
				return field;
		}
		return null;
	}

	public ArrayList<Field> getFailedFields(){
		return failedFields;
	}
	
	/**
	 * Add rules for the given field to validate the given value.
	 * @param fieldName
	 * @param fieldValue
	 * @param rules
	 * @throws ValidationException
	 */
	public void addFieldRules(String fieldName, String fieldValue, String rules) throws ValidationException {
		
		if(fieldName == null || fieldName.isBlank() || rules == null || rules.isBlank())
			throw new ValidationException("Bad arguments: field name and rules can not be null or empty");
		
		fieldsUnderValidation.add(new Field(fieldName, fieldValue, rules));
	}
	
	public void addRuleMessage(String fieldName, String ruleName, String message) {
		/**
		 * If the rule and message already exist for the given field,
		 * we will only change the message.
		 */
		RuleMessage m = messagesBag.get(fieldName, ruleName);
		if(m != null) {
			m.setMessage(message);
			return;
		}
		
		/**
		 * Otherwise we will add the new RuleMessage to the RuleMessagesBag
		 */
		messagesBag.add(new RuleMessage(fieldName, ruleName, message));
	}

	/**
	 * Determine if all specified rules passes the user inputs
	 * @param boolean When one field fails to pass one rule, it will stop checking more.
	 * @return boolean
	 * @throws ValidationException
	 */
	public boolean pass(boolean stopOnFirstFailure) throws ValidationException {

		passFieldsUnderValidation(stopOnFirstFailure);
		
		mergeMessages();
		
		return failedFields.size() > 0 ? false : true;
	}
	
	/**
	 * As default, the validator will check all the rules for a field
	 * under validation despite all failures.
	 * @return
	 * @throws ValidationException
	 */
	public boolean pass() throws ValidationException {
		return pass(false);
	}
	
	private void passFieldsUnderValidation(boolean stopOnFirstFailure) throws ValidationException {

		for (Field field : fieldsUnderValidation) {
			/**
			 * Check if the field must have the same value as another field has.
			 */
			if(field.hasRule("same")) {
				Rule rule = field.getRuleByName("same");
				if(!(rule instanceof ParameterizedRule))
					throw new ValidationException("[same] rule has no parameters.");
				
				String fieldName = ((ParameterizedRule)rule).getParameters().get(0);

				field.setSameField(getFieldUnderValidationByName(fieldName));
			}
			
			field.pass(stopOnFirstFailure);
			
			if(!field.hasPassedRules()) {
				failedFields.add(field);
				if(stopOnFirstFailure)
					break;
			}
		}
	}

	/**
	 * Here we retrieve error messages from the RuleMessagesBag, and inject them
	 * to the failed rules objects.
	 * If there are error messages of particular rules specified by the user,
	 * then we replace the original messages with the user's.
	 */
	private void mergeMessages() {

		for (Field field : failedFields)
			for (Rule rule : field.getFailedRules())
				if(messagesBag.contains(field.getName(), rule.getName()))
					rule.setMessage(messagesBag.get(rule.getName(), rule.getName()).getMessage());
				else if(rule.getMessage() == null || rule.getMessage().isBlank()) {
					if(rule instanceof ExplicitRule) {
						String message = Messages.getExplicitRuleMessage(rule.getName());
						message = String.format(message, field.getName());
						rule.setMessage(message);
					}else if(rule instanceof ParameterizedRule) {
						String message = Messages.getParameterizedRuleMessage(rule.getName());
						message = String.format(message, getFiledNameAndParametersAsArray(field, (ParameterizedRule)rule));
						rule.setMessage(message);
					}
				}
	}

	private Object[] getFiledNameAndParametersAsArray(Field field, ParameterizedRule rule) {
		
		ArrayList<String> params = new ArrayList<>();
		params.add(field.getName());
		
		if (rule.getParameters().size() > 2)
			params.add(rule.getParameters().toString());
		else
			params.addAll(rule.getParameters());
		
		return params.toArray();
	}
	
	public RuleMessagesBag getMessages() {
		return messagesBag;
	}
	
	/**
	 * We get all error messages for failed rules.
	 * @return ArrayList<String>
	 */
	public ArrayList<String> getErrorMessages() {
		
		ArrayList<String> errorMessages = new ArrayList<>();
		
		for (Field field : failedFields)
			for (Rule rule : field.getFailedRules()) {
				errorMessages.add(rule.getMessage());
			}
		
		return errorMessages;
	}
}
