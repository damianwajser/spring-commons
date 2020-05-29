package com.github.damianwajser;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableAutoConfiguration
public class TestApplication {
	 public static void main(String[] args) {
	        SpringApplication.run(TestApplication.class, args);
	    }
}
