/*
 * Class LogAnalyzer
 * Version 0.0.2.1
 * Feature: �ظ�����ʽ��qq��Ϣ������������ͳ���ض����ַ���
 * 
 * Ver0.0.2.1 12/22 Updated:
 * 		- �����޸��������ֶν����ִ�Сд
 * 
 * Ver0.0.2 12/21 Updated:
 * 		- �ṩ���ڷ�Χ֧��
 * 		- �ṩ�л��ļ�֧��
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
		 * 	����file���ֶ�finding����һ��LogAnalyzer����׼��������
		 * 
		 * Parameter:
		 * 		file - ��Ҫ��ȡ���ļ�
		 * 		finding - ��Ҫ���ҵ�����
		 */
		
		findingPattern_ = Pattern.compile(finding);
		finder_ = findingPattern_.matcher("");
		manager_ = new TextManager(file, TextManager.READ);
	}
	LogAnalyzer() {
		/**
		 * 12/21
		 * 	Σ�յĳ�ʼ������������Ϊ���԰��ݴ�
		 */
	}
	
	public void setDateRange(String startDate, String endDate) {
		/**
		 * 12/21 Ver0.0.2
		 * 	
		 * 	���ö�ȡ��ʱ�䷶Χ��
		 * 
		 * Parameter:
		 * 		startDate - ��ʼ�����ڣ�����������ʽΪ����-��-�ա������������վ�Ϊ���֡�
		 * 		endDate - ���������ڣ�����������ʽΪ����-��-�ա������������վ�Ϊ���֡�
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
		 * 	���ó�Ĭ��ģʽ����ȡ�����ļ���
		 * 
		 */
		dateRange_ = null;
		mode_ = DEFAULT_MODE;
	}
	public boolean changeFile(File file) {
		/**
		 * 12/21 Ver0.0.1
		 * 
		 * 	�������������ļ������ҽ��������ɹ�ʱ����true�����򷵻�false��
		 * 
		 * Parameter:
		 * 		file - ������Ŀ���ļ���
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
		 * 	 ���ݴ˶�����ļ��Ͳ����ֶν��з�����������һ��������顣
		 * 
		 * Return:
		 * 		int[][] - һ��������飬��ʽΪ{ ��Ա�������ҵ����ֶ��� int[n], ��Ա�ܷ����� int[n], ȫԱ�ܷ�����int[1]}������nΪ��Ա����
		 * 
		 * TODO:�����÷�Χ��չ������Ⱥ��Ϣ�ļ�
		 */
		
		messageCount = new int[6];
		totalMessageCount = new int[6];
		String temp;
		boolean marker = false;//ָʾ��һ���Ƿ���������
		boolean dateMarker = false;
		int name = 0;//ָʾ����
		int messageSum = 0;//��Ϣ��ͳ��
		
		//ͳ��
		while (!manager_.isEnd()) {
			temp = manager_.readLine();
			
			//����ʱ�䷶Χ�����Ϣ(����DATE_MODE)
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
			
			//��ȡ��Ϣ����¼
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
		 * 	���Ĵ˶������е��ļ��������������е��ֶΡ�
		 * 
		 * Parameter:
		 * 		file - ������Ŀ���ļ���
		 * 		finding - �������ֶΡ�
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
		 * 	compare_()���Ӻ������õݹ�ķ�ʽ���رȽϽ����
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
