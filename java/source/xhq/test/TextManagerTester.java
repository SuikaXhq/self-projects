package xhq.test;

import xhq.common.TextManager;

public class TextManagerTester {

	public static void main(String[] args) {
//		TextManager m = new TextManager("123.txt", TextManager.READ);
		TextManager m = new TextManager("/folder/123.txt", TextManager.READ);
		System.out.println("Start:");
		while (!m.isEnd())System.out.println(m.readLine());
	}
}
