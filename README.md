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
This method accept three parameters, the first one is the name of the field we want to validate. The second parameter is the value of the field under validation, and the third is all rules this field
value must pass.
All the tree parameter are of type **String**.
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
