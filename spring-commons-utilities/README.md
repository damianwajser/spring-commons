# spring-commons-payment-utilities

## Overview

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
	<artifactId>spring-commons-utilities</artifactId>
	<version>${spring.commons}</version>
</dependency>
...
</dependencies>
 ```

#### Gradle

 ```xml
 compile 'com.github.damianwajser:spring-commons-utilities:{lastVersion}'
 ```

## Features

### OptionalWrapper
Sometimes we need to execute some method of the Optional<?> api and throw exceptions, for this we have created a wrapper for the Optional object.
```java
Optional<String> optional = Optional.of("some object");
OptionalWrapper optionalWrapper = OptionalWrapper.of(optional);
optionalWrapper.ifPresent(s -> {
    throw new BadRequestException("", "", Optional.empty());
});
```

### JsonToObjectConverter
Sometimes we need to convert a Json that does not exactly match our target Object, for which we have designed a converter based on JsonPaths.
```java
String json = "{'amount':'5','origin':'damian','destination':'owen'}";
Mapper mapper = new Mapper();
mapper.addMapping("body", "$.destination, $.origin sent you $$.amount dollars");
mapper.addMapping("title", "Congrats! $.destination sent you $$.amount dollars");
mapper.addMapping("footer", "Regards!");
JsonToObjectConverter parser = new JsonToObjectConverter(mapper);
NotificationMapperModel result = parser.convert(json, NotificationMapperModel.class);
assertThat(result.getBody()).isEqualTo("owen, damian sent you $5 dollars");
assertThat(result.getTitle()).isEqualTo("Congrats! owen sent you $5 dollars");
assertThat(result.getFooter()).isEqualTo("Regards!");
```
### FactoryCriteriaJsonBased

Sometimes we need to look for configuration objects based on configurable rules, for this we create the FactoryCriteriaJsonBased.
Let's imagine the following scenario:

We have the following JsonMessage that symbolizes sending money:
```json
{
  'amount':5,
  'origin':'damian',
  'destination':'owen'
}
```
Based on that Json we want to obtain different results:
1) if Damian is the origin then send an SMS message
2) if owen is the destination send message by push notification

```java
Criterion<Sender> criterionSms = CriterionBuilder.build("$.origin=damian", new SmsSender());
Criterion<Sender> criterionPush = CriterionBuilder.build("$.destintion=owen", new PushSender());
Criteria<Sender> criteria = new Criteria(new DefaultSender(), Arrays.asList(criterionSms,criterionPush));
FactoryCriteriaJsonBased<Sender> factory = new FactoryCriteriaJsonBased<>(criteria);
Collection<Sender> sendersBasedCriteria = factory.find(json);
```
#### Complete Example: [noticication-service](https://github.com/damianwajser/spring-commons-example/tree/master/spring-commons-example-notification-service)

## License

The Spring Framework is released under version 2.0 of the [Apache License](http://www.apache.org/licenses/LICENSE-2.0).
