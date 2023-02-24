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
    - [Customizing error messages](#customizing-error-messages)
    - [All available rules](#all-available-rules)
  - [Changelog](#changelog)
  - [Contributing](#contributing)
  - [Security Vulnerabilities](#security-vulnerabilities)
  - [Credits](#credits)
  - [License](#license)


## Introduction
**girover/java-user-inputs** is a package that allows you to validate all user inputs.
    With this package it will be very simple to validate inputs without writing much code.   


## prerequisites

- Java

# Usage
---
## Validator Class
To start validating user inputs, you must create an instance of **validation.Validator**. 
This class takes no parameters.    

```java
import validation.Validator;

public class Main {
	private static String email = "example@domain.com";

	public static void main(String[] args) {
		
		Validator validator = new Validator();
		
	}
}
```

And now to make **validator** starts validating a field, we should first add the wanted rules   
to this field. This can be done by using the method ```addFieldRules(fieldName, fieldValue, rules)```.
This method accept three parameters, the first one is the name of the field we want to validate. The second parameter is the value of the field under validation, and the third is all rules this field's
value must pass.
All the tree parameter are of type **String**.
.
.
.

## How to write rules

There are two types of rules ```explicit``` and ```parameterized```   
The difference between these two types is that **parameterized** rules accept parameter,   
while ```explicit``` rules do not take any parameter.

As mentioned in the section [Validator Class](#validator-class), **rules** are passed    
to the method ```addFieldRules(fieldName, fieldValue, rules)``` as a **String**.   
But this string has a special form. Rules are separated form each other by using ``` | ``` character.   
For instance ```addFieldRules("email", "example@domain.com", "required|email")```.   
As we can see the field **email** has two **rules** to pass. These rules are ```required``` and ```email```.   
**required** means that this field can not be null or empty. **email** means that this field must be a valid email address.
There is no limitation for how many rules you write in one string.
But what if a rule takes parameters. How to pass parameters to the rule?
It is very simple to pass parameters to the rule by using ``` : ``` character between rule name and parameters.
For example: ```addFieldRules("name", "Majed", "required|min:2|max:20")```.   
This means name must be provided because of **required**, the length of the name can not be shorter than 2 letters   
and the length of name can not be longer than 20.   
There are also some rules accepts more than one parameter, so how to pass these parameters?   
It is also very simple by using ``` , ``` character between parameters.   
For example: ```addFieldRules("userRole", "admin", "required|in:admin,student,teacher")```. 

.
.
.
## Customizing error messages
.
.
.
## All available rules

There are two types of rules ```explicit``` and ```parameterized```   
```explicit``` rules do not take any parameter, 
.
.
.

You can call the following method on an object of Tree 
| #   | Explicit rules    		   | Description                                           |
| --- | -------------------------- | ----------------------------------------------------- |
| 1   | `required`          | The field under validation is required and can not be null or empty| 
| 2   | `alphaNumeric` 		| The %s must only contain letters and numbers.|
| 3   | `alphaDash`    | The field under validation must only contain letters, numbers, dashes and underscores.|
| 4   | `numeric`                    | The field under validation must be a number. |
| 5   | `email`                  | The field under validation must be a valid email address. |
| 6   | `date`               | The field under validation must be a valid date. |
| 7   | `boolean`                 | The field must be true or false.|
|-----|----------------------------|--------------------------------------------------------|



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
