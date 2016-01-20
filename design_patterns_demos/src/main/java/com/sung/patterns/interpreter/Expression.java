package com.sung.patterns.interpreter;

public abstract class Expression {

	public abstract int interpret();

	@Override
	public abstract String toString();
}
