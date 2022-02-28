# spring-commons-http-fixer

## Overview

This project is in charge of solving the most common problems related to http codes in spring-mvc REST responses. Among
other things, set the default http 201 code in the response when we are using the POST verb.

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
        <artifactId>spring-commons-http-fixer</artifactId>
        <version>${spring.commons}</version>
    </dependency>
 ...
</dependencies>
 ```

#### Gradle

 ```xml
 compile 'com.github.damianwajser:spring-commons-http-fixer:{lastVersion}'
 ```

## Properties

| Key | Posible Value | Reference | Default Value |--|--|--|-- |spring.commons.http.fixer.enabled|boolean|enable the
module|true

## License

The Spring Framework is released under version 2.0 of the [Apache License](http://www.apache.org/licenses/LICENSE-2.0).
