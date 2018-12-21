/*
 * Class TextManager
 * Version 0.0.3
 * Features: ������ռ���ļ���֧��ʹ������ļ���
 * 
 * Ver0.0.3 12/21 Updated:
 * 		- �ṩ�л��ļ�֧��
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
	private StringBuffer content_;//����
	private ArrayList<String> contentLines_;
	private int cursor;//���, ָʾ��һ�ζ�ȡ��������
	private int currentMode;//ָʾ��ǰģʽ
	
	public TextManager(File file, int mark) {
		/**
		 * 12/19 Ver0.0.1
		 * 
		 * 	����file����������File�ഴ��һ��TextManagerʵ�����в�����
		 * 		READ - ֻ���ҵ��ж�ȡ
		 * 		APPEND - ���ļ�ĩβ���
		 * 		WRITE - ����д�����ļ�
		 * 		DEFAULT - �������ֲ�������ʹ��
		 * 
		 * Parameter:
		 * 		file - Ҫ������File���͡�
		 * 		mark - ģʽ�������������ֶ�֮һ��READ, APPEND, WRITE, DEFAULT��
		 */
		
		currentMode = mark;
		changeFile(file);
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
		
		currentMode = mark;
		changeFile(name);
	}
	
	//��ʼ������
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
	
	//�����������ļ�
	public boolean changeFile(File file) {
		/**
		 * 12/21 Ver0.0.1
		 * 
		 * 	����file������File���������TextManager�����е��ļ���
		 * 	���file�뵱ǰ�����е��ļ���ͬ�򲻽��и���������true��
		 * 	��file��������ļ���ͬ���ҽ��������ɹ�����true������ʧ�ܷ���false��
		 * 
		 * Parameter:
		 * 		file - ��Ҫ������Ŀ���ļ�
		 */

		try {
			if (file == file_)return true;
			file_ = file;
			
			//ȷ���ļ������и�Ŀ¼����
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
		 * 	����fileName����������ڳ����Ŀ¼��·�����������е��ļ���
		 * 			���changeFile(File)
		 * 
		 * Parameter:
		 * 		fileName - Ŀ���ļ�����ڳ����Ŀ¼��·��
		 */
		
		//��ȡ��ǰjar��Ŀ¼
		String classPath = getClass().getProtectionDomain().getCodeSource().getLocation().getPath();
		classPath = classPath.substring(1,classPath.lastIndexOf('/'));
		String filePath = classPath + '/' + fileName;
		return changeFile(new File(filePath));
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
	public void reset() {
		/**
		 * 12/21 Ver0.0.1
		 * 
		 * 	���ô�TextManager�����ʼ����ɺ�״̬��ͬ��
		 * 		��Ч��reset(0)
		 */
		
		if (checkMode_(READ))cursor = -1;
	}
	public void reset(int index) {
		/**
		 * 12/21 Ver0.0.1
		 * 
		 * 	��TextManager�Ĺ������index������һ�ε���readLine()����ȡ�������ݡ�
		 * 
		 * Parameter:
		 * 		index - ��һ�ζ�ȡϣ���õ�������������ΧΪ0��������-1���������ˣ���
		 * 
		 * Throws:
		 * 		IllegalArgumentException: index������Ԥ�ڵķ�Χ��
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