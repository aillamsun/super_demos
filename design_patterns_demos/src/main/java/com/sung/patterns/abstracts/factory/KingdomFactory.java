package com.sung.patterns.abstracts.factory;
/**
* �����ӿ� 
* @author sungang
* @create_time 2015��3��4�� ����11:25:11
 */
public interface KingdomFactory {

	Castle createCastle();
	
	King createKing();
	
	Army createArmy();
	
}
