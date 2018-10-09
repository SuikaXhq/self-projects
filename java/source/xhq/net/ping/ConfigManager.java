package xhq.net.ping;

import java.io.*;
import java.util.ArrayList;

public class ConfigManager
{
	private BufferedReader reader_;
	private BufferedWriter writer_;
	private String bufferedOutput_;
	private File file_;

	//txt文件位于jar包同目录下res/中
	public ConfigManager() throws Exception {
		String path = getClass().getProtectionDomain().getCodeSource().getLocation().getPath();
		path = path.substring(1,path.lastIndexOf("/")) + "/res/DefaultIp.txt";
		file_ = new File(path);
		reader_ = new BufferedReader(new FileReader(file_));
		writer_ = new BufferedWriter(new FileWriter(file_));
	}

	//添加一行
	public void appendln(String str) {
		bufferedOutput_ += str + "\r\n";
	}

	//写入
	public void write() throws IOException {
		writer_.write(bufferedOutput_);
		writer_.flush();
		bufferedOutput_ = "";
	}

	//读取
	public ArrayList<String> read() throws IOException {
		String newString = null;
		ArrayList<String> iPs = new ArrayList<String>();
		while ((newString = reader_.readLine()) != null) {
			iPs.add(newString);
		}
		return iPs;
	}

	//关闭输出流
	public void closeWriter() throws IOException {
		writer_.close();
	}

	//关闭输入流
	public void closeReader() throws IOException {
		reader_.close();
	}

	
}