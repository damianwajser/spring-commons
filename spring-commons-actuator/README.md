# spring-commons-actuator
## Overview

This module add developers functionality.

It contains different functionalities:
- Inspect the Caches

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
        <artifactId>spring-commons-actuator</artifactId>
        <version>${spring.commons}</version>
    </dependency>
 ...
</dependencies>
 ```
 #### Gradle
 ```xml
 compile 'com.github.damianwajser:spring-commons-cache:{lastVersion}'
 ```
Exopose endpoints:

1) curl /actuator/caches/{cacheName}/detail
2) curl /actuator/cache-keys
3) curl /actuator/cache-keys/{key}
4) curl -X DELETE /actuator/cache-keys/{key}

## License
The Spring Framework is released under version 2.0 of the [Apache License](http://www.apache.org/licenses/LICENSE-2.0).
