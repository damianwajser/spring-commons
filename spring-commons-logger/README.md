# spring-commons-logger

## Overview

This module generates useful tools for logging.

Among its features we find:

- StatsFilter (generates a log with the duration for each request)
- MDCFilter (it incorporates parameters in the MDC to be used in the loggin.pattern)
- RequestIdGenerator (a UUID is generated for each request in order to have traceability)

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
        <artifactId>spring-commons-logger</artifactId>
        <version>${spring.commons}</version>
    </dependency>
 ...
</dependencies>
 ```

#### Gradle

 ```xml
 compile 'com.github.damianwajser:spring-commons-logger:{lastVersion}'
 ```

#### Properties

| Key | Posible Value | Reference | Default Value |
|--|--|--|-- |
| logging.pattern.level | [%-5level] AppName: %X{appName} Request ID: %X{requestId} | log pattern | Empty |
| spring.commons.logger.app.name | ms-x | the name of microservice | ${spring.commons.app.name} |
| spring.commons.logger.trace.id | any string | Header key from get the request Id if is empty generate a new UUID to replace RequestId | X-Trace-Id
| spring.commons.logger.duration.request.enabled | true/false | For each request log the duration. | false
| spring.commons.logger.payload.max | Any integer | size of the max payload in the log | 16000

***Recommendation:*** spring.commons.logger.trace.id is the key header so it should be a custom header (X- {header})
since if we are in a microservices environment this header generates traceability thanks to the module ***
spring.commons.resttemplate.interceptor***

Context Variables:

* %X{clientIp}
* %X{path}
* %X{appName}
* %X{requestId}

## License

The Spring Framework is released under version 2.0 of the [Apache License](http://www.apache.org/licenses/LICENSE-2.0).
