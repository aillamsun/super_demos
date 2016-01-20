package com.sung.patterns.prototype;

public abstract class Warlord extends Prototype {

	@Override
	public abstract Warlord clone() throws CloneNotSupportedException;

}
