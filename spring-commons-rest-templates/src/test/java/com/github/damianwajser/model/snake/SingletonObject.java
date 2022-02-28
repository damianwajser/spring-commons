package com.github.damianwajser.model.snake;

public class SingletonObject {

	private static SingletonObject instance;
	private int value;

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

	public void restart() {
		value = 0;
	}
}
