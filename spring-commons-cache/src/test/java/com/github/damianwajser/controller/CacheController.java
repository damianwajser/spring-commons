package com.github.damianwajser.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;

import java.util.Collection;
import java.util.Optional;

@RestController
public class CacheController {

	@Autowired
	private Servicet servicet;


	@GetMapping("/cache")
	private String get() {
		return servicet.generateUUID();
	}

	@GetMapping("/cache_all")
	private Collection<Integer> getAll() {
		return servicet.getAll();
	}

	@GetMapping("/cache_all/{id}")
	private Integer getById(@PathVariable Integer id) {

		Integer customer = servicet.getId(1,id);
		if(customer==null){
			throw new RuntimeException("asdasd");
		}
		return customer;
	}

	@PutMapping("/cache_all/{id}")
	private Integer put(@PathVariable Integer id) {
		return servicet.update(1,id);
	}

	@DeleteMapping("/cache_all/{id}")
	private void delete(@PathVariable Integer id) {
		servicet.delete(1,id);
	}

	@DeleteMapping("/cache")
	private String post() {
		return servicet.delete();
	}


}
