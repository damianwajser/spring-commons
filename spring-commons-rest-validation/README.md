# spring-commons-rest-validations

## Overview

This project override all annotations for standard JSR annotations.

Some annotations accept additional attributes like ***isNulleable***, but the message and the ***bussisnessCode***
attributes are common to all of them. message: This is the message that will usually be rendered when the value of the
respective property fails validation.

***bussiness code:*** this the code that will usualle for ***spring-commons-exception-handler*** and generate a prettty
message.

***isNulleable:*** this attribute indicate if the field can be nulleable.

***excludes:*** HttpMethod array indicating the http methods that have no effect from this validation.

***onlyIn:*** HttpMethod array indicating the only http methods that have effect from this validation, leaving without effect excludes property.

## Get it!

### Install

#### Maven

Functionality of this package is contained in Java package `com.github.damianwajser`, and can be used using following
Maven dependency:

```xml
...
<properties>
	...
	<!-- Use the latest version whenever possible. -->
	<spring.commons>{lastversion}</spring.commons>
	...
</properties>

<dependencies>
...
<dependency>
	<groupId>com.github.damianwajser</groupId>
	<artifactId>spring-commons-rest-validations</artifactId>
	<version>${spring.commons}</version>
</dependency>
...
</dependencies>
 ```

#### Gradle

 ```xml
 compile 'com.github.damianwajser:spring-commons-rest-validations:{lastVersion}'
 ```

**@NotNull** – validates that the annotated property value is not null

**@AssertTrue** – validates that the annotated property value is true

**@Size** – validates that the annotated property value has a size between the attributes min and max; can be applied to
String, Collection, Map, and array properties

**@Min** – vValidates that the annotated property has a value no smaller than the value attribute

**@Max** – validates that the annotated property has a value no larger than the value attribute

**@Base64** – validates that the annotated property is a valid base64 encoded string

**@Email** – validates that the annotated property is a valid email address

**@Pattern** – validate that a string matches with regex parameter.

**@NotEmpty** – validates that the property is not null or empty; can be applied to String, Collection, Map or Array
values

**@NotBlank** – can be applied only to text values and validated that the property is not null or whitespace

**@Positive** and **@PositiveOrZero** – apply to numeric values and validate that they are strictly positive, or
positive including 0

**@Negative** and **@NegativeOrZero** – apply to numeric values and validate that they are strictly negative, or
negative including 0

**@Past** and **@PastOrPresent** – validate that a date value is in the past or the past including the present; can be
applied to date types including those added in Java 8

**@Future** and **@FutureOrPresent** – validates that a date value is in the future, or in the future including the
present

On the other hand some useful validations are added:

**@AlphaNumeric** - Use this annotation to validate alpha-numerics strings, this annotation use @Size constraint then
yoy can add that parameters (min, max). Also you can add a boolean parameter allowSpaces, that default value is true.

**@Word** - Use this annotation to validate word strings, works like @AlphaNumeric but doesn't accept numbers.

**@Password** - Use this annotation to validate basic passwords rules.

**@CardToken** - Use this annotation to validate credit card Tokens of TokenEx, DataFast and More.

**@CardExpiration** - Use this annotation to validate an Object Expirable, when expiration month and expiration year to
be validate with actual date.

**@MatchEnum**

**@Country_ISO3166**

**@UUID**

**OneNotNull** - Use this annotation to validate that at least one of a list of fields is not null

```java
@OneNotNull(
fields = {"idCommerce","idContact"},
message="{commercefile.onenotnull.required}",
businessCode = "commercefile.onenotnull.required"
)
public class ObjectDto implements Serializable {
    
    private String idCommerce;
    
    private String idContact;
}
```

**@Digits** - Use this annotation to validate integer and fraction, that for specifying the number of allowed digits in
the integral part and fraction part of the number.

```java
import com.github.damianwajser.validator.annotation.number.Digits;

import java.math.BigDecimal;

public class DigitObject {

    @Digits(integer = 4, fraction = 0, multipleOf = 10, businessCode = "a-400")    
    private BigDecimal value;

}
```
**@DecimalMin** - Use this annotation to validate a number whose value is higher or equal to the specified minimum.

##### Examples

##### @Max

```java
public class MaxObject {

	//Check max when HTTP Method not match with PUT and POST and ignore validation if value is null
	@Max(value = 3, businessCode = "a-400", exclude = {HttpMethod.PUT, HttpMethod.POST}, nulleable = true)
	//Check max when HTTP Method not match with PATCH and check if value equals null
	@Max(value = 3, businessCode = "a-400", exclude = HttpMethod.PATCH)
	//Check max only when HTTP Method match with PUT and check if value equals null
	@Max(value = 3, businessCode = "a-400", onlyIn = HttpMethod.PUT)
	private Long value;

}
```

##### @CardToken

```java
public class CardTokenObject {

	@CardToken(provider = CardToken.Tokenizer.TOKEN_EX, message = "some message", businessCode = "c-400")
	private String value;

}
```

##### @CardExpiration

Implements the interface **CardExpirable** and override the methods: getExpirationMonth and getExpirationYear.

```java

@CardExpiration(message = "{some.message}", businessCode = "c-400")
public class ExpirationObject implements CardExpirable {

    private int expirationMonth;
    private int expirationYear;

    @Override
    public int getExpirationMonth() {
        return this.expirationMonth;
    }

    @Override
    public int getExpirationYear() {
        return this.expirationYear;
    }
}
```

## License

The Spring Framework is released under version 2.0 of the [Apache License](http://www.apache.org/licenses/LICENSE-2.0).
