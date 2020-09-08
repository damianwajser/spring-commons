package com.github.damianwajser.model.snake;

public class SingletonObject {

	private int value;

	private static SingletonObject instance;

	private SingletonObject() {
	}

	public static SingletonObject getInstance() {
		if (instance == null) {
			instance = new SingletonObject();
		}
		return instance;
	}

	public int getValue() {
		return value;
	}

	public void increment() {
		value++;
	}
}
