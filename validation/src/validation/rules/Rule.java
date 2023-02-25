package validation.rules;

import validation.ValidationException;

public abstract class Rule {

	protected String type;       // {explicit, parameterized}
	protected String name;       // required
	protected String message;    // field is required
	protected boolean passed = false;  // check if rule passed
	
	protected Matcher matcher;
	
	public Rule(String name, String type) {
		this.name = name;
		this.type = type;
	}

	/**
	 * Set a matcher for this rule
	 * This matcher is responsible for matching the value for a given rule
	 * @param matcher
	 */
	public void setMatcher(Matcher matcher) {
		this.matcher = matcher;
	}
	
	public Matcher getMatcher() {
		return matcher;
	}
	
	public boolean pass(String fieldValue) throws ValidationException {
		passed = matcher.matches(fieldValue);
		return isPassed();
	}
	
	public boolean isPassed() {
		return passed;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
	
	public boolean isExplicit() {
		return type.equals("explicit") ? true : false;
	}
	
	public boolean isParameterized() {
		return !isExplicit();
	}
}
