/*
 * 解决杰哥的D10四则运算结果尾数概率问题
 * Ver0.0.1
 */
package xhq.D10;

import java.text.DecimalFormat;
import java.util.*;

public class D10Calculator {
	public final static DecimalFormat FORMAT = new DecimalFormat("0.00%");
	private static ArrayList<Character> modes_ = new ArrayList<Character>();
	private static int numOfD10_;
	private static D10Frame frame = new D10Frame();
	private static int[] results = new int[11];
	private static int cursor;
	private static int total = 0;
	
	private static int next_(int pre, int next) {
		switch (modes_.get(cursor)) {
		case '+':
			return pre + next;
		case '-':
			return pre - next;
		case '*':
			return pre * next;
		case '/':
			return pre / next;
		default:
			return 1000000;
		}
	}
	public static void calc(int c) {
		cursor = 0;
		int temp = c % 10;
		if (c % 10 == 0)
			temp = 10;
		c /= 10;
		for (int i = 1; i < numOfD10_; i++) {
			if (c % 10 == 0)
				temp = next_(temp, 10);
			else
				temp = next_(temp, c % 10);
			c /= 10;
//			if (temp == 1000000) {
//				results[10]++;
//				total++;
//				return;
//			}
			cursor++;
		}
		total++;
		results[Math.abs(temp) % 10]++;
	}
	
	public static void main(String[] args) {
		frame.setVisible(true);
	}
	
	public static void go() {
		frame.textArea.setText("");
		total = 0;
		for (int i = 0; i <= 10; i++)
			results[i] = 0;
		modes_.clear();
		for (int i = 0; i < 5; i++) {
			switch (frame.lists[i].getSelectedIndex()) {
			case 0:
				modes_.add('+');
				break;
			case 1:
				modes_.add('-');
				break;
			case 2:
				modes_.add('*');
				break;
			case 3:
				modes_.add('/');
				break;
			case 4: case -1:
				i = 5;
				break;
			}
		}
		numOfD10_ = modes_.size() + 1;
		for (int i = 0; i < Math.pow(10, numOfD10_); i++) {
			calc(i);
		}
		frame.textArea.append("Total: " + total + "\r\n");
		for (int i = 0; i < 10; i++) {
			frame.textArea.append(i + ": " + results[i] + "\t" + FORMAT.format(((double)results[i] / total)) + "\r\n");
		}
		frame.textArea.append("/0: " + results[10] + "\t" + FORMAT.format(((double)results[10] / total)));
	}

}
