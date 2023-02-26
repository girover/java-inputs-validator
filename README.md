# Java User Inputs Validator

[![Latest Version on Packagist](https://img.shields.io/packagist/v/girover/tree.svg?style=flat-square)](https://packagist.org/packages/girover/tree)
[![GitHub Tests Action Status](https://img.shields.io/github/workflow/status/girover/tree/run-tests?label=tests)](https://github.com/girover/tree/actions?query=workflow%3Arun-tests+branch%3Amain)
[![GitHub Code Style Action Status](https://img.shields.io/github/workflow/status/girover/tree/Check%20&%20fix%20styling?label=code%20style)](https://github.com/girover/tree/actions?query=workflow%3A"Check+%26+fix+styling"+branch%3Amain)


---
## Content

  - [Introduction](#introduction)
  - [prerequisites](#prerequisites)
  - [Usage](#usage)
    - [Validator Class](#validator-class)
    - [How to write rules](#how-to-write-rules)
    - [Displaying error messages](#displaying-error-messages)
    - [Customizing error messages](#customizing-error-messages)
    - [All available rules](#all-available-rules)
  - [Class Diagram](#class-diagram)
  - [Changelog](#changelog)
  - [Contributing](#contributing)
  - [Security Vulnerabilities](#security-vulnerabilities)
  - [Credits](#credits)
  - [License](#license)


## Introduction
**girover/java-inputs-validator** is a package that allows you to validate all user's inputs.   
With this package it will be very simple to validate inputs without writing much code.   


## prerequisites

- Java
- Ms SQL Server

# Usage
---
## Validator Class
To start validating user inputs, you must create an instance of **validation.Validator** class.   

```java
import validation.Validator;

public class Main {
	private static String email = "example@domain.com";

	public static void main(String[] args) {
		
		Validator validator = new Validator();
		
	}
}
```

And now to make **validator** starts validating a field, we should first add the required rules   
to this field.    
This can be done by calling the method **` addFieldRules(fieldName, fieldValue, rules) `** on  `validator`.   
This method accepts three parameters, the first one is the name of the field under validation.   
The second parameter is the value of that field, and the third one is all rules this field's
value must pass.
All the tree parameter are of type **String**.   
> **Note** 
> The field name does not have to be the same as the variable name.

```java

	@FXML
	TextField nameTextField;
	...
    	validator.addFieldRules("name", nameTextField.getText(), "required|min:3|max:20");
    
```

To tell validator to start validating the field for the added rules, you must call the method `pass()` or `pass(boolean)`.


```java
	if(validator.pass())
		System.out.println("all fields are valid");
	else
		System.out.println("Failed to pass all rules");
```

The method pass will also generate all **error messages** for all rules that the field does not pass.   
But when a field fails to pass a specific rule, why to check all rules that come after this rule.   
In this case you can pass `true` as argument to the method `pass(boolean)` to tell the validator to stop checking rules when the first failure occurs.   

**Example**

```java
	boolean stopOnFirstFailure = true;
	if(validator.pass(stopOnFirstFailure))
		System.out.println("all fields are valid");
	else
		System.out.println("Failed to pass all rules");
```
.
.
.

## How to write rules

This package uses two types of rules `explicit` and `parameterized`.   
The difference between these two types is that **parameterized** rules accept parameters,   
while `explicit` rules do not.   

As mentioned in the section [Validator Class](#validator-class), **rules** are passed    
to the method `addFieldRules(fieldName, fieldValue, rules)` as a **String**.   
But this string has a special form. Rules are separated from each other by using ` | ` character.   
For instance:

```ruby 

validator.addFieldRules("email", "example@domain.com", "required|email")

```

As we can see the field **email** has two **rules** to pass. These rules are `required` and `email`.   
**required** means that the value of this field can not be null or empty. **email** means that the value of this field must be a valid email address.   
There is no limitation for how many rules you write in one string.
***But what if a rule takes parameters. How to pass parameters to the rule?***
It is very simple to pass parameters to the rule by using ` : ` character between rule name and parameters.
For example: `addFieldRules("name", "Majed", "required|min:2|max:20")`.   
This means name must be provided because of **required**, the length of the name can not be shorter than 2 letters   
and the length of name can not be longer than 20.   
There are also some rules accept more than one parameter, so how to pass these parameters?   
It is also very simple by using ` , ` character between parameters.   
For example: `addFieldRules("userRole", "admin", "required|in:admin,student,teacher")`.   
This means that the value of **userRole** must be **admin**, **student** or **teacher**.

> **Note**
> All fields are optional by default, which means no rules will be applied to them, if their values are null or empty string. But when using `required` rule, so the value can not be null or empty and all other rules also will be applied. Look at this example.

```ruby
import validation.Validator;

public class Main {

	private static String name = "";

	public static void main(String[] args) {
		
		try {
		Validator validator = new Validator();
			validator.addFieldRules("name", name, "length:10");
			
			if(validator.pass())
				System.out.println("Passed successfully");
				
		} catch (ValidationException e) {
			e.printStackTrace();
		}
	}
}
```
The value of field `name` is an empty string and the rule `required` is not given to it, so the rule `length:10` will not be applied to this field. But if the value of `name` is not empty, then the rule `length:10` will be applied and ***error message*** will be generated if this value is not exactly 10 letters.

## Displaying error messages

To get all ***error messages*** after calling the method `pass()`, you can call the method `getErrorMessages`.   
This method will get all error messages generated by `pass()` method as `ArrayList<String>`.   

Let us take a look at an example.

```ruby
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
```
The result of this code will be:  
```
The length of name must be 10.   
The date format of birth date must be dd-mm-yyyy.   
The CPR Field must be 10 digits.***   
The email address must be a valid email address.   
```

## Customizing error messages

The messages we got from the example in the previous section are default messages.   
But you can write your own messages for any rule on any field.   
To do that you have to call the method `addRuleMessage(fieldName, ruleName, message)` on `validator`.   
Assuming you want to change the error message for `email` rule.

```java 

	try {
		String email = "non-valid-email";
		validator.addFieldRules("email address", email, "required|email");
		validator.addRuleMessage("email address", "email", "du skal angive en gyldig e-mailadresse");
			
			if(validator.pass())
				System.out.println("Passed successfully");
			else
				for(String errorMessage : validator.getErrorMessages())
					System.out.println(errorMessage);
					
		} catch (ValidationException e) {
			e.printStackTrace();
		
```

The result of this code will be:  

```
du skal angive en gyldig e-mailadresse. 
```

## All available rules

There are two types of rules `explicit` and `parameterized`   
`explicit` rules do not take any parameter, 
.
.
.

**Explicit Rules**
| #   | Explicit rules    		   | Description                                           |
| --- | -------------------------- | ----------------------------------------------------- |
| 1   | **`required`**          | The field under validation is required and can not be null or empty| 
| 2   | **`alphaNumeric`** 		| The field must only contain letters and numbers.|
| 3   | **`alphaDash`**    | The field under validation must only contain letters, numbers, dashes and underscores.|
| 4   | **`numeric`**                    | The field under validation must be a number. |
| 5   | **`email`**                  | The field under validation must be a valid email address. |
| 6   | **`date`**               | The field under validation must be a valid date. |
| 7   | **`boolean`**                 | The field must be true or false.|


**Parameterized Rules**
| #   | Explicit rules    		   | Description                                           |
| --- | -------------------------- | ----------------------------------------------------- |
|1    | **`digits`**| The `field` must be `parameter` digits. |
|2    | **`between`** | The `field` must be between `param-1` and `param-2`. |
|3    | **`in`**  | The `field` must be in `parameters`. |
|4    | **`notIn`**| The `fiend` cannot be in `parameters`.  |
|6	  |	**`max`**    |  The `field` must not be greater than `param`.  |
|7	  |	**`min`**  | The `field` must be at least `param`. |
|8	  |	**`digits_max`**     | The `field` must not have more than `param` digits.  |
|9	  |	**`digits_min`**     | The `field` must have at least `param` digits. |
|10   |	**`length`**   | The length of `field` must be `param`. |
|11	  | **`length_max`**   | The length of `field` must not be longer than `param`. |
|12	  | **`length_min`**  | The length of `field` must not be shorter than `param`. |
|13	  | **`gt`**    | The `field` must be greater than `param`. |
|14	  |	**`gte`**   | The `field` must be greater than or equal to `param`. |
|15	  | **`lt`**    | The `field` must be less than `param`. |
|16	  | **`lte`**  | The `field` must be less than or equal to `param`. |
|17	  | **`mime`** | The `field` accepts only extensions: `parameters`. |
|18	  | **`format`** | The format of `field` must be `param`. |
|19	  | **`regex`** | The `field` not matches the Regular Expression `param`. |
|20	  | **`same`** | The `field-1` doesn't match `field-2`. |




## Class Diagram

![Screenshot_20230226_113949](https://user-images.githubusercontent.com/53403538/221405583-d9e4a9a4-065f-49b2-bec2-67010c1b83b7.png)

## Changelog

Please see [CHANGELOG](CHANGELOG.md) for more information on what has changed recently.

## Contributing

Please see [CONTRIBUTING](.github/CONTRIBUTING.md) for details.

## Security Vulnerabilities

Please review [our security policy](../../security/policy) on how to report security vulnerabilities.

## Credits

- [Majed Girover](https://github.com/girover)
- [All Contributors](../../contributors)

## License

The MIT License (MIT). Please see [License File](LICENSE.md) for more information.
