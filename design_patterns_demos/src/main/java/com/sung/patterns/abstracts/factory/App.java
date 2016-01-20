package com.sung.patterns.abstracts.factory;
/**
 * 
 * ���󹤳�ģʽ�ı�����һ�������ӿ�
 * (KingdomFactory)�ӿڵ���֪ʵ���� (ElfKingdomFactory,OrcKingdomFactory).
 * ʾ��ʹ����������ʵ�ִ���һ��king,һ��castle��һ��army
 * 
 * author��sungang
 */
public class App {
	
	public static void main(String[] args) {
		createKingdom(new ElfKingdomFactory());
		createKingdom(new OrcKingdomFactory());
	}
	
	public static void createKingdom(KingdomFactory factory) {
		King king = factory.createKing();
		Castle castle = factory.createCastle();
		Army army = factory.createArmy();
		System.out.println("The kingdom was created.");
		System.out.println(king);
		System.out.println(castle);
		System.out.println(army);
	}
	
}
