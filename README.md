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
### 1.1 [spring-commons-exception](https://github.com/damianwajser/spring-commons/tree/master/spring-commons-exception "spring-commons-exception")
Collection of exceptions for the most common cases when rest apis are built, and override the http code. In addition, they request additional information for errors. They can be used on their own, or they are caught by spring-commons-exception-handler and this information is used to generate a nice error message. For example:
|Exception|Http Code |
|--|--|
| ForbiddenException | 403 |
| PermissionDeniedException | 403
| BadRequestException | 400
| NotFoundException | 404
| ConflictException | 409

### [spring-commons-rest-validation](https://github.com/damianwajser/spring-commons/tree/master/spring-commons-rest-validation "spring-commons-rest-validation")
Override all annotations for standard JSR annotations:

**@NotNull** – validates that the annotated property value is not null

**@AssertTrue** – validates that the annotated property value is true

**@Size** – validates that the annotated property value has a size between the attributes min and max; can be applied to String, Collection, Map, and array properties

**@Min** – vValidates that the annotated property has a value no smaller than the value attribute

**@Max** – validates that the annotated property has a value no larger than the value attribute

**@Email** – validates that the annotated property is a valid email address

**@NotEmpty** – validates that the property is not null or empty; can be applied to String, Collection, Map or Array values

**@NotBlank** – can be applied only to text values and validated that the property is not null or whitespace

**@Positive** and **@PositiveOrZero** – apply to numeric values and validate that they are strictly positive, or positive including 0

**@Negative** and **@NegativeOrZero** – apply to numeric values and validate that they are strictly negative, or negative including 0

**@Past** and **@PastOrPresent** – validate that a date value is in the past or the past including the present; can be applied to date types including those added in Java 8

**@Future** and **@FutureOrPresent** – validates that a date value is in the future, or in the future including the present

On the other hand some useful validations are added:

**@CardToken** - 

**@CardExpiration** - 

Some annotations accept additional attributes, but the message and the bussisness code attributes are common to all of them. message: This is the message that will usually be rendered when the value of the respective property fails validation.
bussiness code: this the code that will usualle for ***spring-commons-exception-handler*** and generate a prettty message.

### [spring-commons-exception-handler](https://github.com/damianwajser/spring-commons/tree/master/spring-commons-exception-handler "spring-commons-exception-handler")
### [spring-commons-http-fixer](https://github.com/damianwajser/spring-commons/tree/master/spring-commons-http-fixer "spring-commons-http-fixer")
### [spring-commons-resttemplate-interceptor](https://github.com/damianwajser/spring-commons/tree/master/spring-commons-resttemplate-interceptor "spring-commons-resttemplate-interceptor")
### [spring-commons-logstash-logger](https://github.com/damianwajser/spring-commons/tree/master/spring-commons-logstash-logger "spring-commons-logstash-logger")

#### Properties
| Key | Posible Value | Reference | Default Value
|--|--|--|--
|logging.pattern. level | "Request  ID:  %X{requestId}  Client  IP:  %X{clientIp}" | log pattern | Empty
|logstash. appName | ms-users | the name of microservice | test
logstash. destination | localhost:5000 | host and port of logstash server| localhost:5000
|logstash. trace.id.key | any string | Header key from get the request Id if is empty generate a new UUID to replace RequestId | UUID
|logstash. duration. request.enabled| true/false | For each request log the duration.| false

### [spring-commons-idempotency](https://github.com/damianwajser/spring-commons/tree/master/spring-commons-idempotency "spring-commons-idempotency")
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

Remember that this module works with redis, with which you should first configure your redis, for which I leave you an example:
1. Set a Redis Properties in application.properties file
```properties
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
3. Suppose we have the following domain object
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
4. So we want some properties of the object to be part of the idempotence key, for which we should create our own KeyGenerator  and override the "generateKey" method. The declaration of the generics is important, since the request will be stopped and a mapping will be made towards the declared object, it can return InternalErrorOfServer in case of a ClassCastException.

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
