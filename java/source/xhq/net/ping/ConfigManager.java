package xhq.net.ping;

import java.io.*;
import java.util.*;
import java.util.regex.*;

public class ConfigManager
{
	public final static Pattern CONFIG_PATTERN = Pattern.compile("\\d+:\\t",Pattern.CASE_INSENSITIVE);
	public final static Pattern IP_PATTERN = Pattern.compile("[a-zA-Z0-9]+(\\.[a-zA-Z0-9]+)*",Pattern.CASE_INSENSITIVE);
	private Matcher configMatcher_;
	private Matcher iPMatcher_;
	private BufferedReader reader_;
	private BufferedWriter writer_;
	private File file_;
	private File res_;
//	private ArrayList<String> outputStrings_ = new ArrayList<String>();

	//txt文件位于jar包同目录下res/DefaultIp.txt
	public ConfigManager() throws Exception {
		String path1 = getClass().getProtectionDomain().getCodeSource().getLocation().getPath();
		String path2 = path1.substring(1,path1.lastIndexOf("/")) + "/res";
		path1 = path1.substring(1,path1.lastIndexOf("/")) + "/res/DefaultIp.txt";
		file_ = new File(path1);
		res_ = new File(path2);
		if (!res_.exists()) {
			res_.mkdir();
		}
	}

/*
	//替换所有
	public void changeAll(ArrayList<String> arr) {
		outputStrings_ = arr;
	}

	//替换一行
	public void changeLine(int index, String content) {
		if (index < outputStrings_.size())
			outputStrings_.set(index,content);
	}

	//添加一行
	public void append(String content) {
		outputStrings_.add(content);
	}
*/

	//写入
	public void write(ArrayList<String> arr) throws IOException {
		writer_ = new BufferedWriter(new FileWriter(file_));
		for (int i = 0;i < arr.size();i++)
			writer_.write((i + 1) + ":\t" + arr.get(i) + "\r\n");//输出config格式
		writer_.flush();
		writer_.close();
	}

	//读取
	public ArrayList<String> readAll() throws IOException {
		ArrayList<String> inputStrings_ = new ArrayList<String>();
		if (!file_.createNewFile()) {
			reader_ = new BufferedReader(new FileReader(file_));
			String newString = null;
			int left = 0;
			int right = 0;
			configMatcher_ = CONFIG_PATTERN.matcher("");
			iPMatcher_ = IP_PATTERN.matcher("");
			while ((newString = reader_.readLine()) != null) {
				configMatcher_ = configMatcher_.reset(newString);//查找config格式，找不到则视为无关内容，与空文件一样对待
				if (configMatcher_.find()) {
					left = configMatcher_.end();
					iPMatcher_ = iPMatcher_.reset(newString);//查找IP，找不到合法域名则用空字符串代替
					if (iPMatcher_.find(left)) {
						right = iPMatcher_.end();
						inputStrings_.add(newString.substring(left, right));//仅存储IP
					} else {
						inputStrings_.add("");
					}
				} else {
					break;
				}
			}
			reader_.close();
		}
		if (inputStrings_.size() == 0)
			inputStrings_.add("");
		return inputStrings_;
	}

	//IP格式判断器
	public boolean isIP(String ip) {
		iPMatcher_.reset(ip);
		return iPMatcher_.find();
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