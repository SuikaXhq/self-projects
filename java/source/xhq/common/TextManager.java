/*
 * Class TextManager
 * Version 0.0.3
 * Features: 不持续占用文件，支持使用相对文件名
 * 
 * Ver0.0.3 12/21 Updated:
 * 		- 提供切换文件支持
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
	private int cursor;//光标, 指示上一次读取的行索引
	private int currentMode;//指示当前模式
	
	public TextManager(File file, int mark) {
		/**
		 * 12/19 Ver0.0.1
		 * 
		 * 	根据file参数给定的File类创建一个TextManager实例进行操作：
		 * 		READ - 只读且单行读取
		 * 		APPEND - 在文件末尾添加
		 * 		WRITE - 覆盖写入新文件
		 * 		DEFAULT - 上述三种操作都可使用
		 * 
		 * Parameter:
		 * 		file - 要操作的File类型。
		 * 		mark - 模式参数，是下列字段之一：READ, APPEND, WRITE, DEFAULT。
		 */
		
		currentMode = mark;
		changeFile(file);
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
		
		currentMode = mark;
		changeFile(name);
	}
	
	//初始化方法
	private void writeInitialize_() throws IOException{
		rewriter_ = new BufferedWriter(new FileWriter(file_));
	}
	private void readInitialize_() throws IOException{
		contentLines_ = new ArrayList<String>();
		content_ = new StringBuffer();
		cursor = -1;
		BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(file_), "utf-8"));
		readAll_(reader);
		reader.close();
	}
	private void appendInitialize_() throws IOException{
		appendWriter_ = new BufferedWriter(new FileWriter(file_, true));
	}
	private void readAll_(BufferedReader reader) throws IOException {
		String temp;
		while ((temp = reader.readLine()) != null) {
			content_.append(temp);
			content_.append("\r\n");
			contentLines_.add(temp);
		}
	}
	private boolean checkMode_(int mode) {
		return currentMode == mode || currentMode == DEFAULT;
	}
	
	//更换操作中文件
	public boolean changeFile(File file) {
		/**
		 * 12/21 Ver0.0.1
		 * 
		 * 	根据file给定的File对象更换该TextManager操作中的文件，
		 * 	如果file与当前操作中的文件相同则不进行更换并返回true。
		 * 	若file与操作中文件不同则当且仅当更换成功返回true，更换失败返回false。
		 * 
		 * Parameter:
		 * 		file - 将要更换的目标文件
		 */

		try {
			if (file == file_)return true;
			file_ = file;
			
			//确保文件及所有父目录存在
			String filePath = file.getPath();
			filePath = filePath.substring(0, filePath.lastIndexOf('\\'));
			File folder = new File(filePath);
			if (!folder.exists())folder.mkdirs();
			file_.createNewFile();
			
			switch (currentMode) {
			case READ:
				readInitialize_();
				break;
			case APPEND:
				appendInitialize_();
				break;
			case WRITE:
				writeInitialize_();
				break;
			case DEFAULT:
				readInitialize_();
				appendInitialize_();
				writeInitialize_();
				break;
			}
			
			return true;
		} catch(IOException e) {
			e.printStackTrace();
			return false;
		}
	}
	public boolean changeFile(String fileName) {
		/**
		 * 12/21 Ver0.0.1
		 * 
		 * 	根据fileName给定的相对于程序根目录的路径更换操作中的文件。
		 * 			详见changeFile(File)
		 * 
		 * Parameter:
		 * 		fileName - 目标文件相对于程序根目录的路径
		 */
		
		//获取当前jar包目录
		String classPath = getClass().getProtectionDomain().getCodeSource().getLocation().getPath();
		classPath = classPath.substring(1,classPath.lastIndexOf('/'));
		String filePath = classPath + '/' + fileName;
		return changeFile(new File(filePath));
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
	public void reset() {
		/**
		 * 12/21 Ver0.0.1
		 * 
		 * 	重置此TextManager，与初始化完成后状态相同。
		 * 		等效于reset(0)
		 */
		
		if (checkMode_(READ))cursor = -1;
	}
	public void reset(int index) {
		/**
		 * 12/21 Ver0.0.1
		 * 
		 * 	将TextManager的光标移至index处，下一次调用readLine()将读取该行内容。
		 * 
		 * Parameter:
		 * 		index - 下一次读取希望得到的行索引，范围为0到总行数-1（包括两端）。
		 * 
		 * Throws:
		 * 		IllegalArgumentException: index不符合预期的范围。
		 */
		
		if (checkMode_(READ))
			if (index < 0 || index > contentLines_.size() - 1)
				throw new IllegalArgumentException("index out of range.");
			else
				cursor = index - 1;
	}
	
	//APPEND MODE
	
	//WRITE MODE
	
	//MODE SWITCH
}