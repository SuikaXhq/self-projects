package xhq.net.ping;

import java.io.*;
import java.util.regex.*;

public class Pinger 
{
	public final static Pattern RESPONSE_PATTERN = Pattern.compile("(\\d+ms)(\\s+)(TTL=\\d+)",Pattern.CASE_INSENSITIVE);
	private String ip_;
	private Runtime runtime_ = Runtime.getRuntime();
	private BufferedReader reader_;
	private String command_;
	private Process tempProcess_;
	private String answer_;
	private String tempString_ = null;
	private Matcher matcher_;

	public Pinger(String ip) {
		matcher_ = RESPONSE_PATTERN.matcher("");
		ip_ = ip;
		command_ = "ping " + ip_ + " -n 1";
	}

	public String getIP() {
		return ip_;
	}

	public String ping() {
		try {
			tempProcess_ = runtime_.exec(command_);
			if (tempProcess_ != null) {
				reader_ = new BufferedReader(new InputStreamReader(tempProcess_.getInputStream()));
				while ((tempString_ = reader_.readLine()) != null) {
					matcher_ = matcher_.reset(tempString_);
					if (matcher_.find())
						return tempString_;
				}
				reader_.close();
				reader_ = null;
				return "³¬Ê±";
			} else {
				reader_.close();
				reader_ = null;
				return "Error 001";
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			reader_ = null;
		}
		return "Error 002";
	}

	public void loadIP(String ip) {
		ip_ = ip;
		command_ = "ping " + ip_ + " -n 1";
	}
}
