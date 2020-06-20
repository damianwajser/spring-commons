# spring-commons-rest-validations
## Overview
This project override all annotations for standard JSR annotations.

Some annotations accept additional attributes like ***isNulleable***, but the message and the ***bussisnessCode*** attributes are common to all of them. message: This is the message that will usually be rendered when the value of the respective property fails validation.

***bussiness code:*** this the code that will usualle for ***spring-commons-exception-handler*** and generate a prettty message.

***isNulleable:*** this attribute indicate if the field can be nulleable.

-----

## Get it!
### Install
#### Maven
Functionality of this package is contained in Java package `com.github.damianwajser`, and can be used using following Maven dependency:

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

**@Size** – validates that the annotated property value has a size between the attributes min and max; can be applied to String, Collection, Map, and array properties

**@Min** – vValidates that the annotated property has a value no smaller than the value attribute

**@Max** – validates that the annotated property has a value no larger than the value attribute

**@Email** – validates that the annotated property is a valid email address

**@Pattern** – validate that a string matches with regex parameter.

**@NotEmpty** – validates that the property is not null or empty; can be applied to String, Collection, Map or Array values

**@NotBlank** – can be applied only to text values and validated that the property is not null or whitespace

**@Positive** and **@PositiveOrZero** – apply to numeric values and validate that they are strictly positive, or positive including 0

**@Negative** and **@NegativeOrZero** – apply to numeric values and validate that they are strictly negative, or negative including 0

**@Past** and **@PastOrPresent** – validate that a date value is in the past or the past including the present; can be applied to date types including those added in Java 8

**@Future** and **@FutureOrPresent** – validates that a date value is in the future, or in the future including the present

On the other hand some useful validations are added:

**@AlphaNumeric** - Use this annotation to validate alpha-numerics strings, this annotation use @Size constraint then yoy can add that parameters (min, max). Also you can add a boolean parameter allowSpaces, that default value is true.

**@Password** - Use this annotation to validate basic passwords rules.

**@CardToken** - Use this annotation to validate credit card Token

**@CardExpiration** - Use this annnotation to validate an Object Expirable, when expiration month and expiration year to be validate with actual date.

##### Examples

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
```
## License
The Spring Framework is released under version 2.0 of the [Apache License](http://www.apache.org/licenses/LICENSE-2.0).
