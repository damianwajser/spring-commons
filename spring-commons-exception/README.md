# spring-commons-Exception
## Overview
This project contains the general-purpose exceptios to spring web.
Collection of exceptions for the most common cases when rest apis are built, and override the http code.
In addition, they request additional information for errors.
They can be used on their own, or they are caught by spring-commons-exception-handler and this information is used to generate a nice error message.

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
        <artifactId>spring-commons-exception</artifactId>
        <version>${spring.commons}</version>
    </dependency>
 ...
</dependencies>
 ```
 #### Gradle
 ```xml
 compile 'com.github.damianwajser:spring-commons-exception:{lastVersion}'
 ```
### Exceptions
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
| UnprocessableEntityException | 422|
| LockedException | 423
| InternalServerErrorException | 500

## License
The Spring Framework is released under version 2.0 of the [Apache License](http://www.apache.org/licenses/LICENSE-2.0).
