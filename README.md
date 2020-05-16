
# spring-commons

## Overview

This project contains the general-purpose tools to spring.
Project is licensed under [Apache License 2.0](http://www.apache.org/licenses/LICENSE-2.0).

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
## Usage

Create a spring-boot application.

```java
//declare the package to create de options controllers
@SpringBootApplication
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}
 
}
```
#### Configuration:
| Key | Posible Value  | Module | Reference | Default Value
|--|--|--|--|--|
| logging.pattern.level | "Request  ID:  %X{requestId}  Client  IP:  %X{clientIp}" | spring-commons-logstash-logger | log pattern | Empty
| logstash.appName | ms-users | spring-commons-logstash-logger | the name of microservice | test
logstash.destination | localhost:5000 | spring-commons-logstash-logger | host and port of logstash server| localhost:5000
| logstash.trace.id.key | any string | spring-commons-logstash-logger | Header key from get the request Id if is empty generate a new UUID to replace RequestId | UUID
| spring.jackson.property-naming-strategy | com.fasterxml.jackson.databind. PropertyNamingStrategy.SnakeCaseStrategy | Spring web | format JSON response when Object is return in a controller | N/A
## License

The Spring Framework is released under version 2.0 of the
[Apache License](http://www.apache.org/licenses/LICENSE-2.0).
