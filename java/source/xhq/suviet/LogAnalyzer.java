/*
 * Class LogAnalyzer
 * Version 0.0.2.1
 * Feature: 重复利用式的qq消息分析器，用于统计特定的字符串
 * 
 * Ver0.0.2.1 12/22 Updated:
 * 		- 紧急修复：查找字段将区分大小写
 * 
 * Ver0.0.2 12/21 Updated:
 * 		- 提供日期范围支持
 * 		- 提供切换文件支持
 */

package xhq.suviet;

import java.io.*;
import java.util.regex.*;
import xhq.common.TextManager;

public class LogAnalyzer
{
	public static final Pattern NAME_PATTERN = Pattern.compile("^(([0-9]{4,})-([0-9]{1,2})-([0-9]{1,2}))\\s[0-9]{1,2}\\:[0-9]{1,2}\\:[0-9]{1,2}\\s(.+)[\\(\\<]([0-9a-zA-Z_@\\.]+)[\\)\\>]$",Pattern.CASE_INSENSITIVE);
	public static final Pattern DATE_PATTERN = Pattern.compile("^(([0-9]{4,})-([0-9]{1,2})-([0-9]{1,2}))",Pattern.CASE_INSENSITIVE);
	public static final int DEFAULT_MODE = 0;
	public static final int DATE_MODE = 1;
	private static final int DATE_GROUP = 1;
	private static final int YEAR_GROUP = 2;
	private static final int MONTH_GROUP = 3;
	private static final int DAY_GROUP = 4;
	private static final int NAME_GROUP = 5;
	private static final int QQ_GROUP = 6;
	private Matcher nameMatcher_ = NAME_PATTERN.matcher("");
	private int[] messageCount = new int[6];//0-shm 1-sjx 2-nhx 3-xhq 4-njh 5-ycx
	private int[] totalMessageCount = new int[6];
	private Pattern findingPattern_;
	private TextManager manager_;
	private Matcher finder_;
	private String[] dateRange_;
	private int mode_ = DEFAULT_MODE;
	
	LogAnalyzer(File file, String finding) {
		/**
		 * 12/21 Ver0.0.1
		 * 
		 * 	根据file和字段finding创建一个LogAnalyzer对象准备分析。
		 * 
		 * Parameter:
		 * 		file - 所要读取的文件
		 * 		finding - 所要查找的内容
		 */
		
		findingPattern_ = Pattern.compile(finding);
		finder_ = findingPattern_.matcher("");
		manager_ = new TextManager(file, TextManager.READ);
	}
	LogAnalyzer() {
		/**
		 * 12/21
		 * 	危险的初始化函数，仅作为测试版暂存
		 */
	}
	
	public void setDateRange(String startDate, String endDate) {
		/**
		 * 12/21 Ver0.0.2
		 * 	
		 * 	设置读取的时间范围。
		 * 
		 * Parameter:
		 * 		startDate - 开始的日期（包括），格式为“年-月-日”，其中年月日均为数字。
		 * 		endDate - 结束的日期（包括），格式为“年-月-日”，其中年月日均为数字。
		 */
		
		dateRange_ = new String[2];
		dateRange_[0] = startDate;
		dateRange_[1] = endDate;
		mode_ = DATE_MODE;
	}
	public void setToDefaultMode() {
		/**
		 * 12/21 Ver0.0.1
		 * 
		 * 	设置成默认模式，读取整个文件。
		 * 
		 */
		dateRange_ = null;
		mode_ = DEFAULT_MODE;
	}
	public boolean changeFile(File file) {
		/**
		 * 12/21 Ver0.0.1
		 * 
		 * 	更换所分析的文件。当且仅当更换成功时返回true，否则返回false。
		 * 
		 * Parameter:
		 * 		file - 更换的目标文件。
		 */
		
		if (manager_ == null) {
			manager_ = new TextManager(file, TextManager.READ);
			return true;
		} else
			return manager_.changeFile(file);
	}
	public int[][] analyze() {
		/**
		 * 12/21 Ver0.0.1
		 * 
		 * 	 根据此对象的文件和查找字段进行分析，并返回一个结果数组。
		 * 
		 * Return:
		 * 		int[][] - 一个结果数组，格式为{ 成员发言中找到的字段数 int[n], 成员总发言数 int[n], 全员总发言量int[1]}，其中n为成员数。
		 * 
		 * TODO:将适用范围扩展到任意群消息文件
		 */
		
		messageCount = new int[6];
		totalMessageCount = new int[6];
		String temp;
		boolean marker = false;//指示上一行是否是名字行
		boolean dateMarker = false;
		int name = 0;//指示名字
		int messageSum = 0;//消息数统计
		
		//统计
		while (!manager_.isEnd()) {
			temp = manager_.readLine();
			
			//跳过时间范围外的消息(仅限DATE_MODE)
			if (!dateMarker && mode_ == DATE_MODE) {
				nameMatcher_.reset(temp);
				if (!nameMatcher_.find())
					continue;
				else {
					int temp1 = compare_(nameMatcher_.group(DATE_GROUP), dateRange_[0]);
					int temp2 = compare_(nameMatcher_.group(DATE_GROUP), dateRange_[1]);
					if (temp2 > 0)break;
					if (temp1 < 0)continue;
					dateMarker = true;
				}
			}
			
			//读取消息并记录
			if (marker) {
				marker = false;
				finder_.reset(temp);
				while (finder_.find())messageCount[name]++;
			} else {
				nameMatcher_.reset(temp);
				if (nameMatcher_.find()) {
					switch (mode_) {
					case DATE_MODE:
						int temp2 = compare_(nameMatcher_.group(DATE_GROUP), dateRange_[1]);
						if (temp2 > 0) {
							dateMarker = false;
							break;
						}
					case DEFAULT_MODE:
						marker = true;
						messageSum++;
						String qq = nameMatcher_.group(QQ_GROUP);
						if (qq.equals(QQBook.shm.getQQ()))
							name = 0;
						else if (qq.equals(QQBook.sjx.getQQ()))
							name = 1;
						else if (qq.equals(QQBook.nhx.getQQ()))
							name = 2;
						else if (qq.equals(QQBook.xhq.getQQ()))
							name = 3;
						else if (qq.equals(QQBook.njh.getQQ()))
							name = 4;
						else if (qq.equals(QQBook.ycx.getQQ()))
							name = 5;
						totalMessageCount[name]++;
						break;
					}
					
				} else {
					finder_.reset(temp);
					while (finder_.find())messageCount[name]++;
				}
			}
			
			if (mode_ == DATE_MODE && !dateMarker)break;
		}//end while
		
		int[][] result = {messageCount, totalMessageCount, {messageSum}};
		return result;
	}
	public void reset(File file, String finding) {
		/**
		 * 12/21 Ver0.0.1
		 * 
		 * 	更改此对象处理中的文件，并更换查找中的字段。
		 * 
		 * Parameter:
		 * 		file - 更换的目标文件。
		 * 		finding - 更换的字段。
		 */
		findingPattern_ = Pattern.compile(finding);
		finder_ = findingPattern_.matcher("");
		manager_ = new TextManager(file, TextManager.READ);
	}
	public String[] getDateRange() {
		String[] temp = dateRange_;
		return temp;
	}
	public int getMode() {
		return mode_;
	}
	
	private int compare_(String date1, String date2) {
		Matcher dateMatcher1 = DATE_PATTERN.matcher(date1);
		Matcher dateMatcher2 = DATE_PATTERN.matcher(date2);
		if (dateMatcher1.find() && dateMatcher2.find())
			return compare__(dateMatcher1, dateMatcher2, YEAR_GROUP);
		else
			throw new IllegalArgumentException("date1 or date2 is illegal.");
	}
	private int compare__(Matcher m1, Matcher m2, int group) {
		/**
		 * 12/21 Ver0.0.1
		 * 	compare_()的子函数。用递归的方式返回比较结果。
		 */
		if (group > DAY_GROUP)return 0;
		int temp1 = Integer.parseInt(m1.group(group));
		int temp2 = Integer.parseInt(m2.group(group));
		if (temp1 == temp2)return compare__(m1, m2, group + 1);
		if (temp1 > temp2)
			return 1;
		else
			return -1;
	}
}
