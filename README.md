
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
## License

The Spring Framework is released under version 2.0 of the
[Apache License](http://www.apache.org/licenses/LICENSE-2.0).

