# spring-commons-exception-handler

## Overview

This module is responsible for generating error messages (REST) when an exception occurs, generating a unique interface
for these occurrences. It is also in charge of correctly setting the HTTP codes in the message.

On the other hand, the internationalization option is enabled, for which in all the exceptions found in ***
spring-commons-exceptions*** or the validations found in ***spring-commons-rest-validation*** We can enter placeholders
when we talk about errors.

The language is selected by the client based on the header ***Accept-Lenguage: $ {locale}*** and this module will take
it from the corresponding message.properties.

-----

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

Firts we create the i18n files in `src/main/resources`:

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

request: `curl -X POST {domain}/i18n/withproperties -H Accpet-Lenguaje:FR-fr`<br/>
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

request: `curl -X POST {domain}/i18n/withproperties -H Accpet-Lenguaje:ES-es`<br/>
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

Also, it's possible to add arguments from multiple sources to error message, based
on [MessageSource](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/context/MessageSource.html)

- messages_en.properties

```properties
spring.commons.message.with.args=English message with {0} on {1,date} at {2,time} and {3} as arguments
```

- application.properties

```properties
last.argument=final comment, for testing purpose
```

Example in Rest Exception:

```java
@RestController
@RequestMapping("/badrequest")
public class BadRequestController {

   @PostMapping("/message/{one}")
   private FooObject badRequest_message(
        @PathVariable String one,
        @Value("${last.argument}") String lastArgument)
     throws BadRequestException {
      throw new BadRequestException("400", "{spring.commons.message.with.args}", one, new Date(), Instant.now().toEpochMilli(), lastArgument);
   }

}
```

request: `curl -X POST {domain}/badrequest/message/Variable`<br/>
response:

```json
{
  "details":
  [
    {
      "error_code": "400",
      "error_message": "English message with Variable on Dec 23, 2021 at 6:30:39 PM and final comment, for testing purpose as arguments",
      "error_detail": null,
      "meta_data": {}
    }
  ],
  "path": "/badrequest/message/Variable",
  "timestamp": "2021-12-23T18:30:39.420240"
}
```

## License

The Spring Framework is released under version 2.0 of the [Apache License](http://www.apache.org/licenses/LICENSE-2.0).
