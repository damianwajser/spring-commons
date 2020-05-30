package com.github.damianwajser.service;

import org.springframework.data.redis.core.RedisOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.SessionCallback;

import java.util.List;
import java.util.function.Consumer;

public interface RedisService<K, V> {
	RedisTemplate<K, V> getRedisTemplate();

	//Ejecuta varias operaciones de redis en forma atomica
	default List<Object> executeMultiOperations(Consumer<RedisOperations<K, V>> consumer) {
		return getRedisTemplate().execute(
				new SessionCallback<List<Object>>() {
					@Override
					public List<Object> execute(RedisOperations operations) {
						operations.multi();
						consumer.accept(operations);
						return operations.exec();
					}
				}
		);
	}

	//Retorna true si habia alguna key para eliminar o false en el otro caso
	default Boolean delete(K key) {
		List<Object> results =
				this.executeMultiOperations(
						operations -> {
							operations.hasKey(key);
							operations.delete(key);
						}
				);
		return (Boolean) results.get(0);
	}

	//Retorna true si habia alguna key para eliminar o false en el otro caso
	default Boolean deleteFromHash(K key, Object... hKey) {
		List<Object> results =
				this.executeMultiOperations(
						operations -> {
							operations.opsForHash().hasKey(key, hKey);
							operations.opsForHash().delete(key, hKey);
						}
				);
		return (Boolean) results.get(0);
	}
}
