package com.sung.patterns.bridge;

public class FlyingMagicWeapon extends MagicWeapon{

	public FlyingMagicWeapon(FlyingMagicWeaponImp imp) {
		super(imp);
	}

	@Override
	public FlyingMagicWeaponImp getImp() {
		return (FlyingMagicWeaponImp) imp;
	}
	
	@Override
	public void wield() {
		getImp().wieldImp();
	}

	@Override
	public void swing() {
		getImp().swingImp();
	}

	@Override
	public void unwield() {
		getImp().unwieldImp();
	}

	public void fly(){
		getImp().flyImp();
	}
	
}
