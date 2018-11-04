/*
 * Class TextManager
 * Version 0.0.1
 * Features: 不持续占用文件
 */

//TODO: APPEND MODE, WRITE MODE, MODE SWITCHER
package xhq.common;

import java.io.*;
import java.util.*;

public class TextManager {
	public static final int READ = 0;
	public static final int APPEND = 1;
	public static final int WRITE = 2;
	public static final int DEFAULT = 3;
	private File file_;
	private BufferedWriter appendWriter_;
	private BufferedWriter rewriter_;
	private StringBuffer content_;//内容
	private ArrayList<String> contentLines_;
	private Scanner scanner_;
	private int cursor = -1;//光标
	private int currentMode;
	
	public TextManager(File file, int mark) {
		try {
			file_ = file;
			switch (mark) {
			case READ:
				currentMode = 0;
				readInitialize_();
				break;
			case APPEND:
				currentMode = 1;
				appendInitialize_();
				break;
			case WRITE:
				currentMode = 2;
				writeInitialize_();
				break;
			case DEFAULT:
				currentMode = 3;
				readInitialize_();
				appendInitialize_();
				writeInitialize_();
				break;
			}
		} catch(IOException e) {
			e.printStackTrace();
		}
	}
	private void writeInitialize_() throws IOException{
		rewriter_ = new BufferedWriter(new FileWriter(file_));
	}
	private void readInitialize_() throws IOException{
		BufferedReader reader = new BufferedReader(new FileReader(file_));
		scanner_ = new Scanner(new FileInputStream(file_));
		readAll_(reader);
		reader.close();
	}
	private void appendInitialize_() throws IOException{
		appendWriter_ = new BufferedWriter(new FileWriter(file_, true));
	}
	private void readAll_(BufferedReader reader) throws IOException {
		String temp = null;
		while (scanner_.hasNextLine()) {
			temp = reader.readLine();
			content_.append(temp);
			content_.append("\r\n");
			contentLines_.add(temp);
		}
	}
	private boolean checkMode_(int mode) {
		return currentMode == mode || currentMode == DEFAULT;
	}
	//READ MODE
	public boolean isEnd() {
		if (checkMode_(READ))
			return cursor >= contentLines_.size() - 1;
		return true;
	}
	public String readLine() {
		if (checkMode_(READ) && !isEnd()) {
			cursor++;
			return contentLines_.get(cursor);
		} else {
			return null;
		}
	}
	public String getContent() {
		if (checkMode_(READ))
			return content_.toString();
		return null;
	}
	
	//APPEND MODE
	
	//WRITE MODE
	
	//MODE SWITCH
}
