package com.sung.patterns.builder2;

public class HawaiianPizzaBuilder extends PizzaBuilder {

	public void buildDough() {
		pizza.setDough("cross");
	}

	public void buildSauce() {
		pizza.setSauce("mild");
	}

	public void buildTopping() {
		pizza.setTopping("ham+pineapple");
	}

}
