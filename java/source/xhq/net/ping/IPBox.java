package xhq.net.ping;

import javax.swing.*;
import java.awt.event.*;
import java.awt.*;

public class IPBox
{
	private Box backBox_;//母体box
	private String defaultIP_;//默认IP
	private JLabel lossRateLabel_;//丢包率百分比
	private JTextArea resultArea_;//回答行区域
	private JTextField ipField_;//IP输入区
	private JButton startButton_;//开始按钮
	private JButton stopButton_;//停止按钮
	private Double receive_ = 0.0;//响应数
	private Double send_ = 0.0;//发送数
	private Timer timer_=new Timer();//ping计时器
	private TimerTask timerTask_ = new TimerTask() {
		public void run() {
/*			result=ping();
			results.append(result + "\r\n");
			results.setCaretPosition(results.getText().length());
			send++;
			if (send!=0) {
				ptg=(send - receive) / send;
				ptgout.setText(df.format(ptg));
			}
*/		}
	};//定时ping操作
	}
	
	//构造函数
	public IPBox(String defaultIp) {
		defaultIp_ = defaultIp;

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

	//添加侦听方法
	public void addStartListener(ActionListener actionListener) {
		startButton_.addActionListener(actionListener);
	}
	public void addStopListener(ActionListener actionListener) {
		stopButton_.addActionListener(actionListener);
	}
}
