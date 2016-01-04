package call;

import java.util.Arrays;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;

public class Server {

	public boolean cookTomatoWithEgg(Money money) {
		System.out.println("有人要西红柿操蛋了,先给我钱,8元以上我才给你炒");
		if (money.getMoney() >= 8) {
			System.out.println("开始洗西红柿");
			System.out.println("开始炒菜");
			System.out.println("完成了");
			return true;
		}

		return false;
	}

	public static int sum(int... numbers) {
		// inside the method a variable argument is similar to an array.
		// number can be treated as if it is declared as int[] numbers;
		int sum = 0;
		for (int number : numbers) {
			sum += number;
		}
		return sum;
	}

	public static void main(String[] args) {
		Hashtable<String, String> ht = new Hashtable<String, String>();
		// ht.put(null, null); //hashtable 不允许null key and value

		HashMap<String, String> hm = new HashMap<String, String>();
		hm.put(null, null);
		hm.put("1", "1");
		hm.put("2", "2");
		Iterator<Entry<String, String>> it = hm.entrySet().iterator();
		while (it.hasNext()) {
			Entry<String, String> entry = it.next();
			//System.out.println("key = " + entry.getKey() + ", value = "
			//		+ entry.getValue());
			it.remove();
		}

		String arr[] = { "1", "2", "3" };
		List<String> list = Arrays.asList(arr);
		for (String s : list) {
			//System.out.println(s);
		}
		
		System.out.println(sum(1,2));
		System.out.println(sum(1,2,3,4));
		
		Calendar calendar = new GregorianCalendar(2000,10,30);
	    System.out.println(calendar);
	}

}
