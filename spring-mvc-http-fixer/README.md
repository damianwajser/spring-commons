# spring-rest-commons-options

<p align="center"><img src="/images/Logo.png" width="100" height="80"><p> 

[![Build Status](https://travis-ci.org/damianwajser/spring-rest-commons-options.svg?branch=master)](https://travis-ci.org/damianwajser/spring-rest-commons-options) [![CII Best Practices](https://bestpractices.coreinfrastructure.org/projects/1400/badge)](https://bestpractices.coreinfrastructure.org/projects/1400) [![Maven Central](https://maven-badges.herokuapp.com/maven-central/com.github.damianwajser/spring-rest-commons-options/badge.svg)](https://maven-badges.herokuapp.com/maven-central/com.github.damianwajser/spring-rest-commons-options) [![Maintainability](https://api.codeclimate.com/v1/badges/dc020f5455c0b6f31089/maintainability)](https://codeclimate.com/github/damianwajser/spring-rest-commons-options/maintainability) [![Test Coverage](https://sonarcloud.io/api/badges/measure?key=com.github.damianwajser:spring-rest-commons-options&metric=coverage)](https://sonarcloud.io/dashboard?id=com.github.damianwajser%3Aspring-rest-commons-options)
[![Vulnerabilities](https://sonarcloud.io/api/badges/measure?key=com.github.damianwajser:spring-rest-commons-options&metric=vulnerabilities)](https://sonarcloud.io/dashboard?id=com.github.damianwajser%3Aspring-rest-commons-options)

## Overview

This project contains the general-purpose documentation to spring rest api http options.
Project is licensed under [Apache License 2.0](http://www.apache.org/licenses/LICENSE-2.0).

-----
## Roadmap

Consult the proyect for details on the current [spring-rest-commons-options roadmap](https://github.com/damianwajser/spring-rest-commons-options/projects/1).

## Get it!

### Install
#### Maven
Functionality of this package is contained in Java package `com.github.damianwajser`, and can be used using following Maven dependency:

```xml
<properties>
  ...
  <!-- Use the latest version whenever possible. -->
  <options.spring.docs>{lastversion}</options.spring.docs>
  ...
</properties>

<dependencies>
  ...
  <dependency>
    <groupId>com.github.damianwajser</groupId>
    <artifactId>spring-rest-commons-options</artifactId>
    <version>${options.spring.docs}</version>
  </dependency>
  ...
</dependencies>
```
#### Gradle

```xml
compile 'com.github.damianwajser:spring-rest-commons-options:0.0.17'
```
## Usage

Create a spring-boot application.

```java
//declare the package to create de options controllers
@ComponentScan({"com.github.damianwajser","{YOUR-PINCIPAL-PACKAGE}"})
@SpringBootApplication
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}
  
}
```

### Example

Create the model, if you required, add the validation with the hibernate-validators or java-validators:

```java
public class Example {
	@NotEmpty(message = "The field description is required")
	private String description;

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

}
```

Create a Controller:
```java
@RestController
@RequestMapping("/example")
public class ExapmleResource {
	
	@GetMapping("/{id}")
	public Example getById(@PathVariable("id") Integer id) {
		Example example = new Example();
		example.setDescription("description");
		return example;

	}
	
	@PostMapping("/")
	public Example post(@Valid Example example) {
		return example;

	}
}
```

Full example: https://github.com/damianwajser/spring-rest-commons-options-example

### Test It!!

The firts enpooint created:

```curl 
curl -X GET http://localhost:8080/endpoints
```

The response: 
```js
["/example","/endpoints"]
```
#### Check options
```curl
curl -X OPTIONS http://localhost:8080/example
```
Response:
```js
{
    "resources": {
        "/example": {
            "endpoints": [
                {
                    "endpoint": "GET - /example/{id}",
                    "httpMethod": "GET",
                    "relativeUrl": "/{id}",
                    "queryString": {
                        "params": []
                    },
                    "pathVariable": {
                        "params": [
                            {
                                "required": true,
                                "name": "id",
                                "type": "Integer"
                            }
                        ]
                    },
                    "bodyRequest": {
                        "fields": [],
                        "jsonSchema": null
                    },
                    "bodyResponse": {
                        "fields": [
                            {
                                "name": "description",
                                "type": "String",
                                "auditable": false
                            }
                        ],
                        "jsonSchema": {
                            "type": "object",
                            "id": "urn:jsonschema:com:test:damianwajser:model:Example",
                            "properties": {
                                "description": {
                                    "type": "string"
                                }
                            }
                        }
                    },
                    "headers": [],
                    "url": "/example/{id}"
                },
                {
                    "endpoint": "GET - /example/{id}",
                    "httpMethod": "GET",
                    "relativeUrl": "/{id}",
                    "queryString": {
                        "params": []
                    },
                    "pathVariable": {
                        "params": [
                            {
                                "required": true,
                                "name": "id",
                                "type": "Integer"
                            }
                        ]
                    },
                    "bodyRequest": {
                        "fields": [],
                        "jsonSchema": null
                    },
                    "bodyResponse": {
                        "fields": [
                            {
                                "name": "description",
                                "type": "String",
                                "auditable": false
                            }
                        ],
                        "jsonSchema": {
                            "type": "object",
                            "id": "urn:jsonschema:com:test:damianwajser:model:Example",
                            "properties": {
                                "description": {
                                    "type": "string"
                                }
                            }
                        }
                    },
                    "headers": [],
                    "url": "/example/{id}"
                },
                {
                    "endpoint": "POST - /example",
                    "httpMethod": "POST",
                    "relativeUrl": "",
                    "queryString": {
                        "params": []
                    },
                    "pathVariable": {
                        "params": []
                    },
                    "bodyRequest": {
                        "fields": [
                            {
                                "name": "description",
                                "type": "String",
                                "auditable": false,
                                "validation": [
                                    {
                                        "name": "NotEmpty",
                                        "message": "The field description is required"
                                    }
                                ]
                            }
                        ],
                        "jsonSchema": {
                            "type": "object",
                            "id": "urn:jsonschema:com:test:damianwajser:model:Example",
                            "properties": {
                                "description": {
                                    "type": "string"
                                }
                            }
                        }
                    },
                    "bodyResponse": {
                        "fields": [
                            {
                                "name": "description",
                                "type": "String",
                                "auditable": false
                            }
                        ],
                        "jsonSchema": {
                            "type": "object",
                            "id": "urn:jsonschema:com:test:damianwajser:model:Example",
                            "properties": {
                                "description": {
                                    "type": "string"
                                }
                            }
                        }
                    },
                    "headers": [],
                    "url": "/example"
                },
                {
                    "endpoint": "POST - /example",
                    "httpMethod": "POST",
                    "relativeUrl": "",
                    "queryString": {
                        "params": []
                    },
                    "pathVariable": {
                        "params": []
                    },
                    "bodyRequest": {
                        "fields": [
                            {
                                "name": "description",
                                "type": "String",
                                "auditable": false,
                                "validation": [
                                    {
                                        "name": "NotEmpty",
                                        "message": "The field description is required"
                                    }
                                ]
                            }
                        ],
                        "jsonSchema": {
                            "type": "object",
                            "id": "urn:jsonschema:com:test:damianwajser:model:Example",
                            "properties": {
                                "description": {
                                    "type": "string"
                                }
                            }
                        }
                    },
                    "bodyResponse": {
                        "fields": [
                            {
                                "name": "description",
                                "type": "String",
                                "auditable": false
                            }
                        ],
                        "jsonSchema": {
                            "type": "object",
                            "id": "urn:jsonschema:com:test:damianwajser:model:Example",
                            "properties": {
                                "description": {
                                    "type": "string"
                                }
                            }
                        }
                    },
                    "headers": [],
                    "url": "/example"
                }
            ]
        }
    },
    "httpCodes": {}
}
```
## License

The Spring Framework is released under version 2.0 of the
[Apache License](http://www.apache.org/licenses/LICENSE-2.0).
