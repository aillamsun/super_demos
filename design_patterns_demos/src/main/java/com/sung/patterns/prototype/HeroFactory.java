package com.sung.patterns.prototype;

/**
 * 
 * Interface for the factory class.
 * 
 */
public interface HeroFactory {

	Mage createMage();

	Warlord createWarlord();

	Beast createBeast();

}
