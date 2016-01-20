package com.sung.patterns.bridge;

/**
 * 
 * In Bridge pattern both abstraction (MagicWeapon) and implementation
 * (MagicWeaponImp) have their own class hierarchies. The interface of the
 * implementations can be changed without affecting the clients.
 * 
 */
public class App {

	public static void main(String[] args) {

		BlindingMagicWeapon blindingMagicWeapon = new BlindingMagicWeapon(
				new Excalibur());
		blindingMagicWeapon.blind();
		blindingMagicWeapon.wield();
		blindingMagicWeapon.swing();
		blindingMagicWeapon.unwield();
		
		
		FlyingMagicWeapon flyingMagicWeapon = new FlyingMagicWeapon(
				new Mjollnir());
		flyingMagicWeapon.fly();
		flyingMagicWeapon.wield();
		flyingMagicWeapon.swing();
		flyingMagicWeapon.unwield();
		
		
		SoulEatingMagicWeapon soulEatingMagicWeapon = new SoulEatingMagicWeapon(
				new Stormbringer());
		soulEatingMagicWeapon.eatSoul();
		soulEatingMagicWeapon.wield();
		soulEatingMagicWeapon.swing();
		soulEatingMagicWeapon.unwield();
	}
}
