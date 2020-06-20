# spring-commons-exception-handler
## Overview
This module is responsible for generating error messages (REST) when an exception occurs, generating a unique interface for these occurrences. It is also in charge of correctly setting the HTTP codes in the message.

On the other hand, the internationalization option is enabled, for which in all the exceptions found in ***spring-commons-exceptions*** or the validations found in ***spring-commons-rest-validation*** We can enter placeholders when we talk about errors.

The language is selected by the client based on the header ***Accept-Lenguage: $ {locale}*** and this module will take it from the corresponding message.properties.

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
        <artifactId>spring-commons-exception-handler</artifactId>
        <version>${spring.commons}</version>
    </dependency>
 ...
</dependencies>
 ```
 #### Gradle
 ```xml
 compile 'com.github.damianwajser:spring-commons-exception-handler:{lastVersion}'
 ```
### Steps
Firts we create the i18n files in src/main/resources:

- messages_en.properties
```properties
spring.commons.validation.constraints.NotEmpty.message=Engilsh message
```
- messages_es.properties
```properties
spring.commons.validation.constraints.NotEmpty.message=Spanish message
```
- messages_fr.properties
```properties
spring.commons.validation.constraints.NotEmpty.message=French message
```
Example in Rest Validation:
```java
public class FooObject {

   @NotEmpty(businessCode = "noStringMessage", message = "{spring.commons}")
   private String string;

}
```
```java
@RestController
public class BadRequestValidationController {

	@PostMapping("/badrequest")
	private FooObject badRequest(@Valid FooObject object) throws BadRequestException {
		return null;
	}
}
```
Example in Rest Exception:
```java
@RestController
@RequestMapping("/i18n")
public class I18nBadRequestController {

   @PostMapping("/withproperties")
   private FooObject withproperties() throws BadRequestException {
     throw new BadRequestException("withproperties", "{spring.commons.validation.constraints.NotEmpty.message}", Optional.empty());
   }

}
```
request: curl -X POST {domain}/i18n/withproperties -H Accpet-Lenguaje:FR-fr
response:
```json
{
   "details":[
      {
         "error_code":"withproperties",
         "error_message":"French message",
         "error_detail":null,
         "meta_data":{

         }
      }
   ],
   "path":"/i18n/withproperties",
   "timestamp":"2020-06-08T17:47:32.988"
}
```
request: curl -X POST {domain}/i18n/withproperties -H Accpet-Lenguaje:ES-es
response:
```json
{
   "details":[
      {
         "error_code":"withproperties",
         "error_message":"Spanish message",
         "error_detail":null,
         "meta_data":{

         }
      }
   ],
   "path":"/i18n/withproperties",
   "timestamp":"2020-06-08T17:47:32.988"
}
```

## License
The Spring Framework is released under version 2.0 of the [Apache License](http://www.apache.org/licenses/LICENSE-2.0).
