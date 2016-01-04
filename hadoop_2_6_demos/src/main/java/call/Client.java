package call;

public class Client {

	// 吃中饭了想要西红柿操蛋，
	public static void haveLunch(Server server) {
		boolean result = server.cookTomatoWithEgg(new Money() {

			public float getMoney() {
				System.out.println("回调方法，我付5元，人民币");
				return 5;
			}
		});

		if (!result) {
			System.out.println("钱不够");
		}

		result = server.cookTomatoWithEgg(new Money() {
			public float getMoney() {
				System.out.println("回调方法，我付10元，人民币");
				return 10;
			}
		});
		if (result) {
			System.out.println("终于吃到吃西红柿操蛋");
		}
	}

	public static void main(String[] args) {
		Client client = new Client();
		Server server = new Server();
		client.haveLunch(server);
	}
}
