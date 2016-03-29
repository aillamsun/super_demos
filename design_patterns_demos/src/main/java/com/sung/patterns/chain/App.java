package com.sung.patterns.chain;
/**
 * 
 * Chain of Responsibility organizes request handlers (RequestHandler) into a
 * chain where each handler has a chance to act on the request on its turn. In
 * this example the king (OrcKing) makes requests and the military orcs
 * (OrcCommander, OrcOfficer, OrcSoldier) form the handler chain.
 * 
 */
public class App {

	public static void main(String[] args) {
		OrcKing king = new OrcKing();
		king.makeRequest(new Request(RequestType.TORTURE_PRISONER, "defend castle"));
	}
}
