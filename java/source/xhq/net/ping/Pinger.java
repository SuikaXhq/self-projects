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
	public final static Pattern PATTERN = Pattern.compile("(\\d+ms)(\\s+)(TTL=\\d+)",Pattern.CASE_INSENSITIVE);
	private String ip_;
	private Runtime runtime_ = Runtime.getRuntime();
	private BufferedReader reader_;
	private String command_;
	private String answer_;
	private String temp_ = null;
	private Matcher matcher_;

	public Pinger(String ip) {
		ip_ = ip;
	}
}
