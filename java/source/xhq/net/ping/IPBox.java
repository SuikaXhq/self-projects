package xhq.net.ping;

import javax.swing.*;
import java.awt.event.*;
import java.awt.*;
import java.util.*;

public class IPBox
{
	public final static DecimalFormat FORMAT=new DecimalFormat("0.00%");
	private Box backBox_;//IPBoxĸ��box
	private String defaultIP_;
	private Pinger pinger_;//Ping��
	private JLabel lossRateLabel_;//�����ʰٷֱȱ�ǩ
	private JTextArea resultArea_;//Ӧ��������
	private JTextField ipField_;//IP������
	private boolean isIPFieldChanged_ = false;
	private JButton startButton_;
	private JButton stopButton_;
	private Double receive_ = 0.0;//��Ӧ��
	private Double send_ = 0.0;//������
	private Timer timer_=new Timer();//ping��ʱ��
	private String tempResult_;
	private TimerTask timerTask_ = new TimerTask() {
		public void run() {
			tempResult_ = pinger_.ping();
			if (tempResult_.equals("��ʱ") && tempResult_.equals("����001"))
				receive_++;
			resultArea_.append(tempResult_ + "\r\n");
			resultArea_.setCaretPosition(resultArea_.getText().length());
			send_++;
			if (send != 0)
				lossRateLabel_.setText(FORMAT.format((send_ - receive_) / send_));
		}
	};//��ʱping����
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

		startButton_=new JButton("��ʼ");
		stopButton_=new JButton("ֹͣ");
		stopButton_.setEnabled(false);
		//��ʼ������Ԫ��

		JScrollPane jsp=new JScrollPane(resultArea_);
		jsp.setWheelScrollingEnabled(false);
		jsp.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		jsp.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_NEVER);
		jsp.setPreferredSize(new Dimension(350,200));
		//�Ż�����
		
		Box upBox=new Box(BoxLayout.X_AXIS);//IP������
		Box midBox=new Box(BoxLayout.X_AXIS);//��������
		Box downBox=new Box(BoxLayout.X_AXIS);//��ť��
		upBox.setPreferredSize(new Dimension(350,20));
		midBox.setPreferredSize(new Dimension(350,20));
		downBox.setPreferredSize(new Dimension(350,30));
		upBox.add(new JLabel("IP:"));
		upBox.add(ipField_);
		midBox.add(new JLabel("�����ʣ�"));
		midBox.add(lossRateLabel);
		downBox.add(startButton_);
		downBox.add(stopButton_);
		backBox_=new Box(BoxLayout.Y_AXIS);
		backBox_.add(upBox);
		backBox_.add(midBox);
		backBox_.add(downBox);
		backBox_.add(jsp);
		//����UI
	}

	//ping����
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

	//�����������
	public void addStartListener(ActionListener actionListener) {
		startButton_.addActionListener(actionListener);
	}
	public void addStopListener(ActionListener actionListener) {
		stopButton_.addActionListener(actionListener);
	}

	//�ж�IP�Ƿ���
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
