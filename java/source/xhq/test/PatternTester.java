/*
 * 正则表达式测试器
 */

package xhq.test;

import java.util.regex.*;

public class PatternTester 
{
	public final static Pattern PATTERN1 = Pattern.compile("\\d+:\\t",Pattern.CASE_INSENSITIVE);
	public final static Pattern PATTERN2 = Pattern.compile("[a-zA-Z0-9]+(\\.[a-zA-Z0-9]+)*",Pattern.CASE_INSENSITIVE);
	public static void main(String[] args) 
	{
		String s = "1:\t127.0.0.1";
		Matcher m = PATTERN1.matcher("");
		m.reset(s);
		System.out.println(s + "\r\n" + m.find());
		int left = m.end();
		System.out.println(m.end());
		m = PATTERN2.matcher(s);
		System.out.println(m.find());
		int right = m.end();
		System.out.println(m.end());
		System.out.println(s.substring(left, right));
	}
}
