package com.sung.patterns.builder;

public enum Weapon {
	
	DAGGER, SWORD, AXE, WARHAMMER, BOW;

	@Override
	public String toString() {
		return name().toLowerCase();
	}
	
}
