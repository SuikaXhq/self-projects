package xhq.net.ping;

import javax.swing.*;
import java.awt.event.*;
import java.awt.*;
import java.io.*;
import java.util.regex.*;
import java.util.*;
import java.text.*;

public class Pinger 
{
	public void static Pattern PATTERN = Pattern.compile("(\\d+ms)(\\s+)(TTL=\\d+)",Pattern.CASE_INSENSITIVE);
	private {
		String ip_;
		Runtime runtime_ = Runtime.getRuntime();
		BufferedReader reader_;
		String command_;
		String answer_;
		String temp_ = null;
		Matcher matcher_;
	}

	public Pinger(String ip) {
		ip_ = ip;
	}
}
