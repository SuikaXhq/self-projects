package xhq.net.ping;

import java.io.*;
import java.util.ArrayList;

public class ConfigManager
{
	private BufferedReader reader_;
	private BufferedWriter writer_;
	private String bufferedOutput_;
	private File file_;
	private ArrayList<String> outputStrings_ = new ArrayList<String>();

	//txt�ļ�λ��jar��ͬĿ¼��res/��
	public ConfigManager() throws Exception {
		String path = getClass().getProtectionDomain().getCodeSource().getLocation().getPath();
		path = path.substring(1,path.lastIndexOf("/")) + "/res/DefaultIp.txt";
		file_ = new File(path);
		reader_ = new BufferedReader(new FileReader(file_));
		writer_ = new BufferedWriter(new FileWriter(file_));
	}

	//�滻һ��
	public void change(int index,String content) {
		if (index < outputStrings_.size())
			outputStrings_.set(index,content);
	}

	//���һ��
	public void append(int index,String content) {
		if (index == outputStrings_.size())
			outputStrings_.add(content);
	}

	//д��
	public void write() throws IOException {
		writer_.write(bufferedOutput_);
		writer_.flush();
		bufferedOutput_ = "";
	}

	//��ȡ
	public ArrayList<String> readAll() throws IOException {
		String newString = null;
		while ((newString = reader_.readLine()) != null) {
			outputStrings_.add(newString);
		}
		return outputStrings_;
	}

/*
	//�ر������
	public void closeWriter() throws IOException {
		writer_.close();
	}

	//�ر�������
	public void closeReader() throws IOException {
		reader_.close();
	}
*/
}