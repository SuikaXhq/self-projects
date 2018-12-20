/*
 * Class TextManager
 * Version 0.0.2
 * Features: 不持续占用文件，支持使用相对文件名
 */

//TODO: APPEND MODE, WRITE MODE, MODE SWITCHER, FILE CHANGE
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
	private int cursor = -1;//光标
	private int currentMode;
	
	public TextManager(File file, int mark) {
		/**
		 * 12/19 Ver0.0.1
		 * 
		 * 	根据file参数给定的File类创建一个TextManager实例进行操作：
		 * 		READ - 只读
		 * 		APPEND - 在文件末尾添加
		 * 		WRITE - 覆盖写入新文件
		 * 		DEFAULT - 上述三种操作都可使用
		 * 
		 * Parameter:
		 * 		file - 要操作的File类型。
		 * 		mark - 模式参数，是下列字段之一：READ, APPEND, WRITE, DEFAULT。
		 */
		
		try {
			file_ = file;
			switch (mark) {
			case READ:
				currentMode = READ;
				readInitialize_();
				break;
			case APPEND:
				currentMode = APPEND;
				appendInitialize_();
				break;
			case WRITE:
				currentMode = WRITE;
				writeInitialize_();
				break;
			case DEFAULT:
				currentMode = DEFAULT;
				readInitialize_();
				appendInitialize_();
				writeInitialize_();
				break;
			}
		} catch(IOException e) {
			e.printStackTrace();
		}
	}
	public TextManager(String name, int mark) {
		/**
		 * 12/19 Ver0.0.1
		 * 
		 * 	根据name给定的相对文件名创建一个TextManager实例进行操作：
		 * 		详见TextManager(File, int)
		 * 
		 * Parameter:
		 * 		name - 相对于jar包当前目录的路径。
		 * 		mark - 模式参数，是下列字段之一：READ, APPEND, WRITE, DEFAULT。
		 */
		
		try {
			//获取当前jar包目录
			String classPath = getClass().getProtectionDomain().getCodeSource().getLocation().getPath();
			classPath = classPath.substring(1,classPath.lastIndexOf('/'));
			//确保所有父目录存在
			int folderIndex = name.lastIndexOf('/');
			if (folderIndex != -1) {
				String folderPath = classPath + name.substring(0, folderIndex);
				File folder = new File(folderPath);
				if (!folder.exists())folder.mkdirs();
			}
			//生成文件（若不存在）
			String filePath = classPath + '/' + name;
			file_ = new File(filePath);
			file_.createNewFile();
			
			switch (mark) {
			case READ:
				currentMode = READ;
				readInitialize_();
				break;
			case APPEND:
				currentMode = APPEND;
				appendInitialize_();
				break;
			case WRITE:
				currentMode = WRITE;
				writeInitialize_();
				break;
			case DEFAULT:
				currentMode = DEFAULT;
				readInitialize_();
				appendInitialize_();
				writeInitialize_();
				break;
			}
		} catch(IOException e) {
			e.printStackTrace();
		}
	}
	
	//初始化方法
	private void writeInitialize_() throws IOException{
		rewriter_ = new BufferedWriter(new FileWriter(file_));
	}
	private void readInitialize_() throws IOException{
		contentLines_ = new ArrayList<String>();
		content_ = new StringBuffer();
		BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(file_), "utf-8"));
		readAll_(reader);
		reader.close();
	}
	private void appendInitialize_() throws IOException{
		appendWriter_ = new BufferedWriter(new FileWriter(file_, true));
	}
	private void readAll_(BufferedReader reader) throws IOException {
		String temp = null;
		while ((temp = reader.readLine()) != null) {
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
		/**
		 * 
		 * 	在单行读取模式下，确认是否读到文件末尾。
		 * 
		 * Returns: 
		 * 		READ或DEFAULT模式下，当且仅当读取到末尾时返回true，否则返回false。
		 * 		其他模式下，返回true。
		 */
		
		if (checkMode_(READ))
			return cursor >= contentLines_.size() - 1;
		return true;
	}
	public String readLine() {
		/**
		 * 
		 * 	读取下一行字符。
		 * 
		 * Returns: 
		 * 		READ或DEFAULT模式下，若未抵达文件末尾，返回下一行字符串（不含有换行符），否则返回null。
		 * 		其他模式下，返回null。
		 */
		
		if (checkMode_(READ) && !isEnd()) {
			cursor++;
			return contentLines_.get(cursor);
		} else {
			return null;
		}
	}
	public String getContent() {
		/**
		 * 
		 * 	读取所有文件内容。
		 * 
		 * Returns: 
		 * 		READ或DEFAULT模式下，返回文件的内容。
		 * 		其他模式下，返回null。
		 */
		
		if (checkMode_(READ))
			return content_.toString();
		return null;
	}
	
	//APPEND MODE
	
	//WRITE MODE
	
	//MODE SWITCH
}
