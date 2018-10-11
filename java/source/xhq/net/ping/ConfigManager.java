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

	//txt文件位于jar包同目录下res/中
	public ConfigManager() throws Exception {
		String path = getClass().getProtectionDomain().getCodeSource().getLocation().getPath();
		path = path.substring(1,path.lastIndexOf("/")) + "/res/DefaultIp.txt";
		file_ = new File(path);
		reader_ = new BufferedReader(new FileReader(file_));
		writer_ = new BufferedWriter(new FileWriter(file_));
	}

	//替换一行
	public void change(int index,String content) {
		if (index < outputStrings_.size())
			outputStrings_.set(index,content);
	}

	//添加一行
	public void append(int index,String content) {
		if (index == outputStrings_.size())
			outputStrings_.add(content);
	}

	//写入
	public void write() throws IOException {
		writer_.write(bufferedOutput_);
		writer_.flush();
		bufferedOutput_ = "";
	}

	//读取
	public ArrayList<String> readAll() throws IOException {
		String newString = null;
		while ((newString = reader_.readLine()) != null) {
			outputStrings_.add(newString);
		}
		return outputStrings_;
	}

/*
	//关闭输出流
	public void closeWriter() throws IOException {
		writer_.close();
	}

	//关闭输入流
	public void closeReader() throws IOException {
		reader_.close();
	}
*/
}