# spring-commons-idempotency
## Overview
This module tries to solve the problems associated with idempotence. For them, create a filter within the spring chain of responsibilities. When the first request is made, it saves in redis the request sent by the client associated with an idempotence key. When another request is made two things can happen:
 1. The first request finished executing, which returns the same response that was obtained in the first call.
 2. In case the first request is still running, a message will be returned indicating the conflict.
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
        <artifactId>spring-commons-idempotency</artifactId>
        <version>${spring.commons}</version>
    </dependency>
 ...
</dependencies>
 ```
 #### Gradle
 ```xml
 compile 'com.github.damianwajser:spring-commons-idempotency:{lastVersion}'
 ```

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
