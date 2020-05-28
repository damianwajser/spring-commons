### Uso básico

#### Cache en memoria

###### En properties o yml

> Esta property puede setear una lista de nombres de cache ***ehcache.cache.names: cache-name***

```java
ehcache.cache.names: cache-name
ehcache.cache.cache-name.ttl: 120
ehcache.cache.cache-name.maxEntries: 2
```

```java
 @Cacheable(value="cache-name", key="#id")
 ```