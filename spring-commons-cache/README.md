# spring-commons-cache
## Overview

This module tries to solve the typical problems that we encounter when we use Redis as Cache in spring.
Configure:
- CacheManager
- RedisTemplate
It also improves the lifetime of the keys when we use @Cacheable.

It contains different functionalities:
- Generate a prefix for the cache keys, so that when we are in a microservices environment and a centralized cache is used, there is a logical separation between the keys and there is no overlap.
- Pre-configure a RedisTemplate
- Pre-configure the Spring CacheManager
- TTL by @Cacheable cache-name

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
        <artifactId>spring-commons-cache</artifactId>
        <version>${spring.commons}</version>
    </dependency>
 ...
</dependencies>
 ```
 #### Gradle
 ```xml
 compile 'com.github.damianwajser:spring-commons-cache:{lastVersion}'
 ```
#### Properties
| Key | Posible Value | Reference | Default Value
|--|--|--|--
|spring.commons.cache.enabled | true/false| enable the module| false
|spring.commons.cache.prefix.enabled | true/false | When you use a shared redis between different applications and you want to do a division on the domain of the keys, you can use a prefix to do this manually. | true
|spring.commons.cache.prefix.value | Any String | value of prefix  | ${spring.commons.app.name}
|spring-commons.cache.ttl.all=2| Any int | default time to @Cacheable|86400 (one day)
| spring-commons.cache.ttl.name.{name}| Any int | ttl value for each cache name| ${spring-commons.cache.ttl.all}

1. Set a Redis Properties in application.properties file
```properties
spring.commons.cache.enabled=true
spring.commons.cache.prefix.enabled=true
spring.commons.cache.prefix.value=ms-test
spring.redis.host=localhost
spring.redis.port=6379
```
2. Create a Redis ConnectionFactory, in this case I choose the jedis connector.
```java
@Configuration
public class RedisConfiguration {

   @Bean
   public RedisConnectionFactory redisConnectionFactory(RedisProperties redisProperties) {
      RedisStandaloneConfiguration config = new RedisStandaloneConfiguration(redisProperties.getRedisHost(), redisProperties.getRedisPort());
      JedisClientConfiguration clientConfiguration = JedisClientConfiguration.builder().readTimeout(Duration.ofMillis(0)).connectTimeout(Duration.ofMillis(0)).build();
      return new JedisConnectionFactory(config, clientConfiguration);
   }
}
```

## License
The Spring Framework is released under version 2.0 of the [Apache License](http://www.apache.org/licenses/LICENSE-2.0).
