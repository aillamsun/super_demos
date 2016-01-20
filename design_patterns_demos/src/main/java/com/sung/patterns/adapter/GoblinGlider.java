package com.sung.patterns.adapter;
/**
* Device class (adaptee in the pattern). 
* @author sungang
* @create_time 2015��3��4�� ����11:49:13
 */
public class GoblinGlider {
	
	public void attachGlider() {
		System.out.println("Glider attached.");
	}

	public void gainSpeed() {
		System.out.println("Gaining speed.");
	}

	public void takeOff() {
		System.out.println("Lift-off!");
	}
	
}
