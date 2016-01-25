package com.sung.rabbitmq.rpc;

public class Test {
	public static void main(String[] args) throws Exception {
		RPCClient fibonacciRpc = new RPCClient();
		String response = fibonacciRpc.call("30");
		System.out.println("相应数据: " + response);

		fibonacciRpc.close();
	}
}
