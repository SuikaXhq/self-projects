package xhq.net.ping;

import javax.swing.*;
import javax.swing.event.*;
import java.awt.event.*;
import java.awt.*;
import java.util.*;
import java.util.regex.*;
import java.text.*;

public class IPBox
{
	public final static DecimalFormat FORMAT = new DecimalFormat("0.00%");
	private Box backBox_;//IPBox母体box
	private Pinger pinger_;//Ping器
	private JLabel lossRateLabel_;//丢包率百分比标签
	private JTextArea resultArea_;//应答行区域
	private JTextField ipField_;//IP输入区
	private boolean isIPFieldChanged_ = false;
	private JButton startButton_;
	private JButton stopButton_;
	private JButton deleteButton_;
	private Double receive_ = 0.0;//响应数
	private Double send_ = 0.0;//发送数
	private java.util.Timer timer_ = new java.util.Timer();//ping计时器
	private String tempResult_;
	private Matcher resultMatcher_;
	private TimerTask timerTask_;
	
	public IPBox(String defaultIP) {
		pinger_ = new Pinger(defaultIP);

		//初始化元素
		lossRateLabel_ = new JLabel("0.00%");
		resultArea_ = new JTextArea();
		resultArea_.setEditable(false);
		resultArea_.setSize(350,200);
		ipField_ = new JTextField(defaultIP);
		ipField_.setCaretPosition(ipField_.getText().length());
		ipField_.setPreferredSize(new Dimension(350,20));
		ipField_.getDocument().addDocumentListener(new DocumentListener() {
			public void insertUpdate(DocumentEvent e) {turnOn_();}
			public void changedUpdate(DocumentEvent e) {}
			public void removeUpdate(DocumentEvent e) {turnOn_();}
			private void turnOn_() {
				if (!isIPFieldChanged_)
					isIPFieldChanged_ = true;
			}
		});//ip变更通知
		startButton_ = new JButton("开始");
		stopButton_ = new JButton("停止");
		stopButton_.setEnabled(false);
		deleteButton_ = new JButton("删除");

		resultMatcher_ = Pinger.RESPONSE_PATTERN.matcher("");

		JScrollPane jsp = new JScrollPane(resultArea_);
		jsp.setWheelScrollingEnabled(false);
		jsp.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		jsp.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_NEVER);
		jsp.setPreferredSize(new Dimension(350,200));
		//优化界面
		
		Box upBox = new Box(BoxLayout.X_AXIS);//IP输入区
		Box midBox = new Box(BoxLayout.X_AXIS);//丢包率区
		Box downBox = new Box(BoxLayout.X_AXIS);//按钮区
		Box underBox = new Box(BoxLayout.X_AXIS);//删除键区
		upBox.setPreferredSize(new Dimension(350,20));
		midBox.setPreferredSize(new Dimension(350,20));
		downBox.setPreferredSize(new Dimension(350,30));
		underBox.setPreferredSize(new Dimension(350,30));
		upBox.add(new JLabel("IP:"));
		upBox.add(ipField_);
		midBox.add(new JLabel("丢包率："));
		midBox.add(lossRateLabel_);
		downBox.add(startButton_);
		downBox.add(stopButton_);
		underBox.add(deleteButton_);
		backBox_ = new Box(BoxLayout.Y_AXIS);
		backBox_.setPreferredSize(new Dimension(350,300));
		backBox_.add(upBox);
		backBox_.add(midBox);
		backBox_.add(downBox);
		backBox_.add(jsp);
		backBox_.add(underBox);
		//构造UI
	}

//成员变量封装
	public boolean isIPFieldChanged() {
		return isIPFieldChanged_;
	}
	public String getIP() {
		return ipField_.getText();
	}
	public Pinger getPinger() {
		return pinger_;
	}
	public Box getBox() {
		return backBox_;
	}
	public void setDeleteButtonDisabled() {
		deleteButton_.setEnabled(false);
	}
	public ActionListener getStartListener() {
		return startButton_.getActionListeners()[0];
	}
	public ActionListener getStopListener() {
		return stopButton_.getActionListeners()[0];
	}
	public ActionListener getDeleteListener() {
		return deleteButton_.getActionListeners()[0];
	}

//ping方法
	public void startPing() {
		clear();
		resultArea_.setText("");
		timerTask_ = new PingTask();
		timer_.scheduleAtFixedRate(timerTask_,0,1000);
		startButton_.setEnabled(false);
		stopButton_.setEnabled(true);
	}
	public void stopPing() {
		timerTask_.cancel();
		timer_.purge();
		startButton_.setEnabled(true);
		stopButton_.setEnabled(false);
	}

//添加侦听方法
	public void addStartListener(ActionListener actionListener) {
		startButton_.addActionListener(actionListener);
	}
	public void addStopListener(ActionListener actionListener) {
		stopButton_.addActionListener(actionListener);
	}
	public void addDeleteListener(ActionListener actionListener) {
		deleteButton_.addActionListener(actionListener);
	}

//判断IP是否变更
	private void turnOff_() {
		isIPFieldChanged_ = false;
	}
	public void loadIP() {
		turnOff_();
		pinger_.loadIP(ipField_.getText());
	}
	public void clear() {
		receive_ = 0.0;
		send_ = 0.0;
	}

//关闭
	public void close() {
		timer_.cancel();
		timerTask_ = null;
	}

//定时ping操作
	private class PingTask extends TimerTask
	{
		public void run() {
			send_++;
			tempResult_ = pinger_.ping();
			resultMatcher_ = resultMatcher_.reset(tempResult_);
			if (resultMatcher_.find())
				receive_++;
			resultArea_.append(tempResult_ + "\r\n");
			resultArea_.setCaretPosition(resultArea_.getText().length());
			lossRateLabel_.setText(FORMAT.format((send_ - receive_) / send_));
		}
	}
}
