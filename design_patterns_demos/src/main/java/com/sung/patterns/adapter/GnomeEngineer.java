package com.sung.patterns.adapter;
/**
* Adapter class. Adapts the interface of the device (GoblinGlider) into
* Engineer interface expected by the client (GnomeEngineeringManager).
* @author sungang
* @create_time 2015��3��4�� ����11:47:59
 */
public class GnomeEngineer implements Engineer{
	
	private GoblinGlider glider;
	
	public GnomeEngineer() {
		glider = new GoblinGlider();
	}

	@Override
	public void operateDevice() {
		glider.attachGlider();
		glider.gainSpeed();
		glider.takeOff();
	}
}
