package com.sung.patterns.adapter;
/**
* GnomeEngineering manager uses Engineer to operate devices. 
* @author sungang
* @create_time 2015��3��4�� ����11:46:28
 */
public class GnomeEngineeringManager implements Engineer{
	
	private Engineer engineer;
	
	public GnomeEngineeringManager(){
		engineer = new GnomeEngineer();
	}
	
	@Override
	public void operateDevice() {
		engineer.operateDevice();
	}
	
}
