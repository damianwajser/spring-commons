# spring-commons-logger-logstash

## Overview

This module configures the connector to logstash async way.

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
        <artifactId>spring-commons-logger-logstash</artifactId>
        <version>${spring.commons}</version>
    </dependency>
 ...
</dependencies>
 ```

#### Gradle

 ```xml
 compile 'com.github.damianwajser:spring-commons-logger-logstash:{lastVersion}'
 ```

#### Properties

| Key | Posible Value | Reference | Default Value | 
|--|--|--|-- |
| spring.commons.logstash.destination | localhost:5000 | host and port of logstash server | localhost:5000 |
| spring.commons.logstash.enabled | true/false | Enable the Module. | false |

***Recommendation:*** spring.commons.logger.trace.id is the key header so it should be a custom header (X- {header})
since if we are in a microservices environment this header generates traceability thanks to the module ***
spring.commons.resttemplate.interceptor***

## License

The Spring Framework is released under version 2.0 of the [Apache License](http://www.apache.org/licenses/LICENSE-2.0).
