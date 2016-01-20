package com.sung.patterns.builder;

public enum Profession {
	
	WARRIOR, THIEF, MAGE, PRIEST;
	
	@Override
	public String toString() {
		return name().toLowerCase();
	}
	
}
