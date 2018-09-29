package xhq.net.ping;

import javax.swing.*;
import java.awt.event.*;
import java.awt.*;

public class IPBox
{
	Box backBox_;//母体box
	JLabel lossRateLabel_;//丢包率百分比
	JTextArea resultArea_;//回答行区域
	JTextField ipField_;//IP输入区
	JButton startButton_;//开始按钮
	JButton stopButton_;//停止按钮
	Double receive_ = 0.0;//响应数
	Double send_ = 0.0;//发送数
	String defaultIP_;//默认IP
	Timer timer_=new Timer();//ping计时器
	TimerTask timerTask_ = new TimerTask() {
		public void run() {
			result=ping();
			results.append(result + "\r\n");
			results.setCaretPosition(results.getText().length());
			send++;
			if (send!=0) {
				ptg=(send - receive) / send;
				ptgout.setText(df.format(ptg));
			}
		}
	};//定时ping操作
	
	//构造函数
	public Ipbox(Pinger pinger,String defaultIp,PingerStarter pst){
		backBox_=new Box(BoxLayout.Y_AXIS);
		lossRateLabel_=new JLabel("0.00%");
		resultArea_=new JTextArea();
		resultArea_.setEditable(false);
		resultArea_.setSize(350,200);
		ipField_=new JTextField(defaultIP_);
		ipField_.setPreferredSize(new Dimension(350,20));
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

//		startButton_.addActionListener(pinger.new StartListener());
//		stopButton_.addActionListener(pinger.new StopListener());
		//添加侦听
		
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
		backBox_.add(upBox);
		backBox_.add(midBox);
		backBox_.add(downBox);
		backBox_.add(jsp);
		//构造UI
	}
	
}
