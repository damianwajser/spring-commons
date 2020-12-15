# spring-commons-resttemplate-interceptor
## Overview

This project registers the RestTemplates to be used by the application.

At the same time, it incorporates an Interceptor to add custom headers (those that start with "X-"), to generate traceability between the microservices.

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
        <artifactId>spring-commons-resttemplate-interceptor</artifactId>
        <version>${spring.commons}</version>
    </dependency>
 ...
</dependencies>
 ```
 #### Gradle
 ```xml
 compile 'com.github.damianwajser:spring-commons-resttemplate-interceptor:{lastVersion}'
 ```
#### Properties
| Key | Posible Value | Reference | Default Value
|--|--|--|--
|spring.commons.rest.template.timeout.connection | any int | timeout connection | -1
|spring.commons.rest.template.timeout.write | any int | timeout connection | -1
|spring.commons.rest.template.timeout.read | any int | timeout connection | -1
|spring.commons.rest.template.ssl.enable|boolean|enable ssl certificate in ssl_restTemplate|false
|spring.commons.rest.template.ssl.protocol||any string|TLSv1.2
|spring.commons.rest.template.ssl.trustStorePassword||any string|empty
|spring.commons.rest.template.ssl.trustStore||any string|empty
|spring.commons.rest.template.http.connection.pool.maxTotal|any int||100
|spring.commons.rest.template.http.connection.pool.MaxDefaultPerRoute|any int||30


### RestTemplates

Inject by default the restTemplate with:

````java
@Autowired
private RestTemplate restTemplate;
````
This generate the message in cammelCase.

But, if you call a snake_case endpoint, you use:
````java
@Autowired
@Qualifier("snake_template")
private RestTemplate restTemplate;
````
If you want consume ssl services with certificates, configure the properties and use this:
````java
@Autowired
@Qualifier("ssl_camel_case_template")
private RestTemplate restTemplate;
````
````java
@Autowired
@Qualifier("ssl_snake_case_template")
private RestTemplate restTemplate;
````

### Example
For the example we will use an echo service, "https://httpbin.org/get", we will test it:
```shell script
curl https://httpbin.org/get -H x-some-header:test
```
Then response:
````json
{
  "args": {},
  "headers": {
    "Accept": "*/*",
    "Host": "httpbin.org",
    "Referer": "",
    "User-Agent": "Mozilla/5.0 (compatible; MSIE 9.0; Windows NT 6.1; Trident/5.0)",
    "X-Amzn-Trace-Id": "Root=1-5eee34b8-86b7abe8a99498589241626e",
    "X-Some-Header": "test"
  },
  "url": "https://httpbin.org/get"
}
````

As we see, we respond to everything we send you.

We generate a controller that injects the restTemplate of this project, and we will call our endpoint and see how the echo service returns the same X-headers that we send to the main request.
```java
@RestController
public class TestController {

	@Autowired
	private RestTemplate restTemplate;

	@GetMapping("/replayheaders")
	private Object test1() {
        // call a echo service
		return restTemplate.getForObject("https://httpbin.org/get", Object.class);
	}
}
```
Test de Endpoint
```shell script
curl localhost:{port}/replayheaders -H X-ClientId-ID:1 -H X-custom-header:some_string
```
The response
````json
{
   "args":{
   },
   "headers":{
      "Accept":"application/json, application/*+json",
      "Host":"httpbin.org",
      "User-Agent":"Java/1.8.0_131",
      "X-Amzn-Trace-Id":"Root=1-5eee3650-c72be076024159508e2c3d38",
      "X-Clientid-Id":"1",
      "X-Custom-Header":"some_string"
   },
   "url":"https://httpbin.org/get"
}
````
## License
The Spring Framework is released under version 2.0 of the [Apache License](http://www.apache.org/licenses/LICENSE-2.0).
