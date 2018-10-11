package xhq.net.ping;

import java.io.*;
import java.util.regex.*;

public class Pinger 
{
	public final static Pattern PATTERN = Pattern.compile("(\\d+ms)(\\s+)(TTL=\\d+)",Pattern.CASE_INSENSITIVE);
	private String ip_;
	private Runtime runtime_ = Runtime.getRuntime();
	private BufferedReader reader_;
	private String command_;
	private Process tempProcess_;
	private String answer_;
	private String tempString_ = null;
	private Matcher matcher_;

	public Pinger(String ip) {
		ip_ = ip;
		command_ = "ping " + ip_ + " -n 1";
	}

	public String ping() {
		reader_ = null;
		try {
			tempProcess_ = runtime_.exec(command_);
			if (tempProcess_ != null) {
				reader_ = new BufferedReader(new InputStreamReader(tempProcess_.getInputStream()));
				tempString_ = reader_.readLine();
				matcher_ = PATTERN.matcher(tempString_);
				matcher_.find() ? return tempString_ : return "³¬Ê±";
			} else {
				return "´íÎó001";
			}
			reader_.close();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void loadIP(String ip) {
		ip_ = ip;
		command_ = "ping " + ip_ + " -n 1";
	}
}
