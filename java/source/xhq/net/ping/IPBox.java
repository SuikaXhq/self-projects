package xhq.net.ping;

import javax.swing.*;
import java.awt.event.*;
import java.awt.*;
import java.util.*;

public class IPBox
{
	public final static DecimalFormat FORMAT=new DecimalFormat("0.00%");
	private Box backBox_;//IPBox母体box
	private String defaultIP_;
	private Pinger pinger_;//Ping器
	private JLabel lossRateLabel_;//丢包率百分比标签
	private JTextArea resultArea_;//应答行区域
	private JTextField ipField_;//IP输入区
	private boolean isIPFieldChanged_ = false;
	private JButton startButton_;
	private JButton stopButton_;
	private Double receive_ = 0.0;//响应数
	private Double send_ = 0.0;//发送数
	private Timer timer_=new Timer();//ping计时器
	private String tempResult_;
	private TimerTask timerTask_ = new TimerTask() {
		public void run() {
			tempResult_ = pinger_.ping();
			if (tempResult_.equals("超时") && tempResult_.equals("错误001"))
				receive_++;
			resultArea_.append(tempResult_ + "\r\n");
			resultArea_.setCaretPosition(resultArea_.getText().length());
			send_++;
			if (send != 0)
				lossRateLabel_.setText(FORMAT.format((send_ - receive_) / send_));
		}
	};//定时ping操作
	}
	
	public IPBox(String defaultIp) {
		defaultIp_ = defaultIp;

		lossRateLabel_=new JLabel("0.00%");

		resultArea_=new JTextArea();
		resultArea_.setEditable(false);
		resultArea_.setSize(350,200);

		ipField_=new JTextField(defaultIP_);
		ipField_.setPreferredSize(new Dimension(350,20));
		ipField_.getDocument().addDocumentListener(new DocumentListener() {
			public void insertUpdate(DocumentEvent e) {turnOn();}
			public void changedUpdate(DocumentEvent e) {}
			public void removeUpdate(DocumentEvent e) {turnOn();}
			private void turnOn() {
				if (!isIPFieldChanged_)
					isIPFieldChanged_ = true;
		});

		startButton_=new JButton("开始");
		stopButton_=new JButton("停止");
		stopButton_.setEnabled(false);
		//初始化界面元素

		JScrollPane jsp=new JScrollPane(resultArea_);
		jsp.setWheelScrollingEnabled(false);
		jsp.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		jsp.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_NEVER);
		jsp.setPreferredSize(new Dimension(350,200));
		//优化界面
		
		Box upBox=new Box(BoxLayout.X_AXIS);//IP输入区
		Box midBox=new Box(BoxLayout.X_AXIS);//丢包率区
		Box downBox=new Box(BoxLayout.X_AXIS);//按钮区
		upBox.setPreferredSize(new Dimension(350,20));
		midBox.setPreferredSize(new Dimension(350,20));
		downBox.setPreferredSize(new Dimension(350,30));
		upBox.add(new JLabel("IP:"));
		upBox.add(ipField_);
		midBox.add(new JLabel("丢包率："));
		midBox.add(lossRateLabel);
		downBox.add(startButton_);
		downBox.add(stopButton_);
		backBox_=new Box(BoxLayout.Y_AXIS);
		backBox_.add(upBox);
		backBox_.add(midBox);
		backBox_.add(downBox);
		backBox_.add(jsp);
		//构造UI
	}

	//ping方法
	public void startPing() {
		resultArea_.setText("");
		timer_.scheduleAtFixedRate(timerTask_,0,1000);
		startButton_.setEnabled(false);
		stopButton_.setEnabled(true);
	}
	public void stopPing() {
		timerTask_.cancel();
		timer_.purge();
	}

	//添加侦听方法
	public void addStartListener(ActionListener actionListener) {
		startButton_.addActionListener(actionListener);
	}
	public void addStopListener(ActionListener actionListener) {
		stopButton_.addActionListener(actionListener);
	}

	//判断IP是否变更
	public boolean isIPFieldChanged() {
		return isIPFieldChanged_;
	}
	public String getIP() {
		return defaultIP_;
	}
	public void turnOff() {
		isIPFieldChanged_ = false;
	}
	public void loadIP() {
		defaultIP_ = ipField_.getText();
		pinger_.loadIP(defaultIP_);
	}
}
