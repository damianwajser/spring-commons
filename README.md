# spring-commons 
## Overview
This project contains the general-purpose tools to spring.  Project is licensed under [Apache License 2.0](http://www.apache.org/licenses/LICENSE-2.0).

-----
## Roadmap
Consult the proyect for details on the current [spring-commons roadmap]([https://github.com/damianwajser/spring-commons/projects/1](https://github.com/damianwajser/spring-commons/projects/1)).

## Get it!
### Install
#### Maven
Functionality of this package is contained in Java package `com.github.damianwajser`, and can be used using following Maven dependency:

```xml
<parent>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-parent</artifactId>
    <version>2.3.0.RELEASE</version>
</parent>
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
        <artifactId>spring-commons</artifactId>
        <version>${spring.commons}</version>
    </dependency>
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-web</artifactId>
    </dependency>
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-data-redis</artifactId>
    </dependency>
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-validation</artifactId>
    </dependency>
 ...
</dependencies>
 ```
 #### Gradle
 ```xml
 compile 'com.github.damianwajser:spring-commons:{lastVersion}'
 ```
 ## Usage Create a spring-boot application.
```java
@SpringBootApplication
@EnableAutoConfiguration
@ComponentScan(basePackages = {"com.github.damianwajser","{YOUR-PACKAGE}"})
public class Application {
   public static void main(String[] args) {
      SpringApplication.run(Application.class, args);
   }
}
```
#### Recommended configuration:
| Key | Module | Reference
|--|--|--
PropertyNamingStrategy.SnakeCaseStrategy | Spring web | format JSON response when Object is return in a controller

## Modules
## 1 [spring-commons-exception](https://github.com/damianwajser/spring-commons/tree/master/spring-commons-exception "spring-commons-exception")

Collection of exceptions for the most common cases when rest apis are built, and override the http code. In addition, they request additional information for errors. They can be used on their own, or they are caught by spring-commons-exception-handler and this information is used to generate a nice error message. For example:
|Exception|Http Code |
|--|--|
| BadRequestException | 400
| PaymentRequiredException | 402
| ForbiddenException | 403
| PermissionDeniedException | 403
| NotFoundException | 404
| MethodNotAllowedException | 405
| NotAcceptableException|406
| ConflictException | 409
| PreconditionFailedException | 412
| UnsuportedMediaTypeException | 415
| UnprocessableEntityException | 422

## 2 [spring-commons-rest-validation](https://github.com/damianwajser/spring-commons/tree/master/spring-commons-rest-validation "spring-commons-rest-validation")

Override all annotations for standard JSR annotations:

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

Some annotations accept additional attributes like ***isNulleable***, but the message and the ***bussisnessCode*** attributes are common to all of them. message: This is the message that will usually be rendered when the value of the respective property fails validation.
bussiness code: this the code that will usualle for ***spring-commons-exception-handler*** and generate a prettty message.

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
## 3 [spring-commons-exception-handler](https://github.com/damianwajser/spring-commons/tree/master/spring-commons-exception-handler "spring-commons-exception-handler")

This module is responsible for generating error messages (REST) when an exception occurs, generating a unique interface for these occurrences. It is also in charge of correctly setting the HTTP codes in the message.

On the other hand, the internationalization option is enabled, for which in all the exceptions found in ***spring-commons-exceptions*** or the validations found in ***spring-commons-rest-validation*** We can enter placeholders when we talk about errors.

The language is selected by the client based on the header ***Accept-Lenguage: $ {locale}*** and this module will take it from the corresponding message.properties.

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
         "errorCode":"withproperties",
         "errorMessage":"French message",
         "errorDetail":null,
         "metaData":{

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
         "errorCode":"withproperties",
         "errorMessage":"Spanish message",
         "errorDetail":null,
         "metaData":{

         }
      }
   ],
   "path":"/i18n/withproperties",
   "timestamp":"2020-06-08T17:47:32.988"
}
```

## 4 [spring-commons-http-fixer](https://github.com/damianwajser/spring-commons/tree/master/spring-commons-http-fixer "spring-commons-http-fixer")
## 5 [spring-commons-resttemplate-interceptor](https://github.com/damianwajser/spring-commons/tree/master/spring-commons-resttemplate-interceptor "spring-commons-resttemplate-interceptor")
## 6 [spring-commons-logstash-logger](https://github.com/damianwajser/spring-commons/tree/master/spring-commons-logstash-logger "spring-commons-logstash-logger")

#### Properties
| Key | Posible Value | Reference | Default Value
|--|--|--|--
|logging.pattern. level | "Request  ID:  %X{requestId}  Client  IP:  %X{clientIp}" | log pattern | Empty
|logstash. appName | ms-users | the name of microservice | test
logstash. destination | localhost:5000 | host and port of logstash server| localhost:5000
|logstash. trace.id.key | any string | Header key from get the request Id if is empty generate a new UUID to replace RequestId | UUID
|logstash. duration. request.enabled| true/false | For each request log the duration.| false

## 7 [spring-commons-cache](https://github.com/damianwajser/spring-commons/tree/master/spring-commons-idempotency "spring-commons-cache")

#### Properties
| Key | Posible Value | Reference | Default Value
|--|--|--|--
|spring.commons.cache.enabled | true/false| enable the module| false
|spring.commons.cache.prefix.enabled | true/false | When you use a shared redis between different applications and you want to do a division on the domain of the keys, you can use a prefix to do this manually. | true
|spring.commons.cache.prefix.value | Any String | value of prefix  | null
|spring-commons.cache.ttl.all=2| Any int | default time to @Cacheable|86400 (one day)
1. Set a Redis Properties in application.properties file
```properties
spring.commons.cache.enabled=true
spring.commons.cache.prefix.enabled=true
spring.commons.cache.prefix.value=ms-test
spring.redis.host=localhost
spring.redis.port=6379
```
2. Create a Redis ConnectionFactory, in this case I choose the jedis connector.
```java
@Configuration
public class RedisConfiguration {

   @Bean
   public RedisConnectionFactory redisConnectionFactory(RedisProperties redisProperties) {
      RedisStandaloneConfiguration config = new RedisStandaloneConfiguration(redisProperties.getRedisHost(), redisProperties.getRedisPort());
      JedisClientConfiguration clientConfiguration = JedisClientConfiguration.builder().readTimeout(Duration.ofMillis(0)).connectTimeout(Duration.ofMillis(0)).build();
      return new JedisConnectionFactory(config, clientConfiguration);
   }
}
```

## 8 [spring-commons-idempotency](https://github.com/damianwajser/spring-commons/tree/master/spring-commons-idempotency "spring-commons-idempotency")
This module tries to solve the problems associated with idempotence. For them, create a filter within the spring chain of responsibilities. When the first request is made, it saves in redis the request sent by the client associated with an idempotence key. When another request is made two things can happen:
 1. The first request finished executing, which returns the same response that was obtained in the first call.
 2. In case the first request is still running, a message will be returned indicating the conflict.

This configuration is done by registering some beans and properties, you can see the following example:

#### Properties
| Key | Posible Value | Reference | Default Value
|--|--|--|--
spring.commons.idempotency.enabled | true/false | Enable the module | false
spring.commons.idempotency.message | Any String | |
spring.commons.idempotency.ttl | Any nunmber ||
spring.commons.idempotency.badrequest.code| Any String | | 400
spring.commons.idempotency.conflict.code| Any String | | 409
spring.commons.idempotency.conflict.mesasge| Any String | |idempotency key is bussy
```java
@Configuration
public class IdempotencyConfiguration {

  @Bean
  public IdempotencyEndpoints idempotencyEndpoints() {
    IdempotencyEndpoints idempotencyEndpoints = new IdempotencyEndpoints();
    // register endpoint by all Http Methods and generic Key generator
    // (The idempotence key is generated based on the header sent by the client, X-Idempotency-Key)
    idempotencyEndpoints.addIdempotencyEndpoint("/idempotency_generic");
    //Customize another enpoint only by POST method and custom keyGenerator
    idempotencyEndpoints.addIdempotencyEndpoint("/idempotency_by_custom", new FooIdempotencyKeyGenerator(), HttpMethod.POST);
    return idempotencyEndpoints;
  }
}
```
Eneable the module
```properties
spring.commons.idempotency.enabled=true
```

Remember that this module works with cache, with which you should first configure your ***spring-commons-cache***, for which I leave you an example:

1. Suppose we have the following domain object
```java
public class FooObject {

   private String value;
   //The Empty Constructor is required
   public FooObject(){ }

   public String getValue() {
      return value;
   }

   public void setValue(String value) {
      this.value = value;
   }
```
2. So we want some properties of the object to be part of the idempotence key, for which we should create our own KeyGenerator  and override the "generateKey" method. The declaration of the generics is important, since the request will be stopped and a mapping will be made towards the declared object, it can return InternalErrorOfServer in case of a ClassCastException.

```java
                                                                           //very important
									  //generic
public class FooIdempotencyKeyGenerator<T> implements IdempotencyKeyGenerator<FooObject> {

   private static final String IDEMPOTENCY_DEFALUT_HEADER = "X-Idempotency-Key";

   @Override
   public String generateKey(HttpHeaders headers, HttpMethod method, String path, FooObject request) {
      String key = getHeaderValue(headers, IDEMPOTENCY_DEFALUT_HEADER);
      return path + "-" + key + "-" + method.toString() + "-" + request.getValue();
   }

   protected String getHeaderValue(HttpHeaders headers, String headerKey) {
      List<String> idempotencyHeader = headers.get(headerKey);
      String key;
      if (idempotencyHeader != null) {
         key = idempotencyHeader.stream().collect(Collectors.joining("-"));
      } else {
         // ArgumentNotFoundException is used to return a bad request indicating that the field is mandatory
         throw new ArgumentNotFoundException(headerKey);
      }
      return key;
   }
}
```
## License
The Spring Framework is released under version 2.0 of the [Apache License](http://www.apache.org/licenses/LICENSE-2.0).
