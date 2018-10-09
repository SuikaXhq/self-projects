package xhq.net.ping;

import java.io.*;
import java.util.ArrayList;

public class ConfigManager
{
	private BufferedReader reader_;
	private BufferedWriter writer_;
	private String bufferedOutput_;
	private File file_;

	//txt�ļ�λ��jar��ͬĿ¼��res/��
	public ConfigManager() throws Exception {
		String path = getClass().getProtectionDomain().getCodeSource().getLocation().getPath();
		path = path.substring(1,path.lastIndexOf("/")) + "/res/DefaultIp.txt";
		file_ = new File(path);
		reader_ = new BufferedReader(new FileReader(file_));
		writer_ = new BufferedWriter(new FileWriter(file_));
	}

	//���һ��
	public void appendln(String str) {
		bufferedOutput_ += str + "\r\n";
	}

	//д��
	public void write() throws IOException {
		writer_.write(bufferedOutput_);
		writer_.flush();
		bufferedOutput_ = "";
	}

	//��ȡ
	public ArrayList<String> read() throws IOException {
		String newString = null;
		ArrayList<String> iPs = new ArrayList<String>();
		while ((newString = reader_.readLine()) != null) {
			iPs.add(newString);
		}
		return iPs;
	}

	//�ر������
	public void closeWriter() throws IOException {
		writer_.close();
	}

	//�ر�������
	public void closeReader() throws IOException {
		reader_.close();
	}

	
}