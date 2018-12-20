/*
 * Class TextManager
 * Version 0.0.2
 * Features: ������ռ���ļ���֧��ʹ������ļ���
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
	private StringBuffer content_;//����
	private ArrayList<String> contentLines_;
	private int cursor = -1;//���
	private int currentMode;
	
	public TextManager(File file, int mark) {
		/**
		 * 12/19 Ver0.0.1
		 * 
		 * 	����file����������File�ഴ��һ��TextManagerʵ�����в�����
		 * 		READ - ֻ��
		 * 		APPEND - ���ļ�ĩβ���
		 * 		WRITE - ����д�����ļ�
		 * 		DEFAULT - �������ֲ�������ʹ��
		 * 
		 * Parameter:
		 * 		file - Ҫ������File���͡�
		 * 		mark - ģʽ�������������ֶ�֮һ��READ, APPEND, WRITE, DEFAULT��
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
		 * 	����name����������ļ�������һ��TextManagerʵ�����в�����
		 * 		���TextManager(File, int)
		 * 
		 * Parameter:
		 * 		name - �����jar����ǰĿ¼��·����
		 * 		mark - ģʽ�������������ֶ�֮һ��READ, APPEND, WRITE, DEFAULT��
		 */
		
		try {
			//��ȡ��ǰjar��Ŀ¼
			String classPath = getClass().getProtectionDomain().getCodeSource().getLocation().getPath();
			classPath = classPath.substring(1,classPath.lastIndexOf('/'));
			//ȷ�����и�Ŀ¼����
			int folderIndex = name.lastIndexOf('/');
			if (folderIndex != -1) {
				String folderPath = classPath + name.substring(0, folderIndex);
				File folder = new File(folderPath);
				if (!folder.exists())folder.mkdirs();
			}
			//�����ļ����������ڣ�
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
	
	//��ʼ������
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
		 * 	�ڵ��ж�ȡģʽ�£�ȷ���Ƿ�����ļ�ĩβ��
		 * 
		 * Returns: 
		 * 		READ��DEFAULTģʽ�£����ҽ�����ȡ��ĩβʱ����true�����򷵻�false��
		 * 		����ģʽ�£�����true��
		 */
		
		if (checkMode_(READ))
			return cursor >= contentLines_.size() - 1;
		return true;
	}
	public String readLine() {
		/**
		 * 
		 * 	��ȡ��һ���ַ���
		 * 
		 * Returns: 
		 * 		READ��DEFAULTģʽ�£���δ�ִ��ļ�ĩβ��������һ���ַ����������л��з��������򷵻�null��
		 * 		����ģʽ�£�����null��
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
		 * 	��ȡ�����ļ����ݡ�
		 * 
		 * Returns: 
		 * 		READ��DEFAULTģʽ�£������ļ������ݡ�
		 * 		����ģʽ�£�����null��
		 */
		
		if (checkMode_(READ))
			return content_.toString();
		return null;
	}
	
	//APPEND MODE
	
	//WRITE MODE
	
	//MODE SWITCH
}
