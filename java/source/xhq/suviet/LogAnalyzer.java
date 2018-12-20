/*
 * Class LogAnalyzer
 * Ver0.0.1
 * Feature: 重复利用式的qq消息分析器，用于统计特定的字符串
 */

package xhq.suviet;

import java.io.*;
import java.util.regex.*;
import xhq.common.TextManager;

public class LogAnalyzer
{
	public static final Pattern NAME_PATTERN = Pattern.compile("^([0-9]{4,}-[0-9]{1,2}-[0-9]{1,2})\\s[0-9]{1,2}\\:[0-9]{1,2}\\:[0-9]{1,2}\\s(.+)[\\(\\<]([0-9a-zA-Z_@\\.]+)[\\)\\>]$",Pattern.CASE_INSENSITIVE);
	private Matcher nameMatcher_ = NAME_PATTERN.matcher("");
	private int[] messageCount = new int[6];//0-shm 1-sjx 2-nhx 3-xhq 4-njh 5-ycx
	private int[] totalMessageCount = new int[6];
	private Pattern findingPattern_;
	private TextManager manager_;
	private Matcher finder_;
	
	LogAnalyzer(File file, String finding) {
		findingPattern_ = Pattern.compile(finding, Pattern.CASE_INSENSITIVE);
		finder_ = findingPattern_.matcher("");
		manager_ = new TextManager(file, TextManager.READ);
	}
	LogAnalyzer() {
	}
	
	public int[][] analyze() {
		messageCount = new int[6];
		totalMessageCount = new int[6];
		String temp;
		boolean marker = false;//指示上一行是否是名字行
		int name = 1;//指示名字
		int messageSum = 0;//消息数统计
		while (!manager_.isEnd()) {
			temp = manager_.readLine();
			if (marker) {
				marker = false;
				finder_.reset(temp);
				while (finder_.find())messageCount[name]++;
			} else {
				nameMatcher_.reset(temp);
				if (nameMatcher_.find()) {
					marker = true;
					messageSum++;
					if (nameMatcher_.group(3).equals(QQBook.shm.getQQ()))
						name = 0;
					else if (nameMatcher_.group(3).equals(QQBook.sjx.getQQ()))
						name = 1;
					else if (nameMatcher_.group(3).equals(QQBook.nhx.getQQ()))
						name = 2;
					else if (nameMatcher_.group(3).equals(QQBook.xhq.getQQ()))
						name = 3;
					else if (nameMatcher_.group(3).equals(QQBook.njh.getQQ()))
						name = 4;
					else if (nameMatcher_.group(3).equals(QQBook.ycx.getQQ()))
						name = 5;
					totalMessageCount[name]++;
				} else {
					finder_.reset(temp);
					while (finder_.find())messageCount[name]++;
				}
			}
		}
		int[][] result = {messageCount, totalMessageCount, {messageSum}};
		return result;
	}
	
	public void reset(File file, String finding) {
		findingPattern_ = Pattern.compile(finding, Pattern.CASE_INSENSITIVE);
		finder_ = findingPattern_.matcher("");
		manager_ = new TextManager(file, TextManager.READ);
	}
	
	
}
