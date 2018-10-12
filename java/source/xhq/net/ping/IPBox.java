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
	private Box backBox_;//IPBoxĸ��box
	private Pinger pinger_;//Ping��
	private JLabel lossRateLabel_;//�����ʰٷֱȱ�ǩ
	private JTextArea resultArea_;//Ӧ��������
	private JTextField ipField_;//IP������
	private boolean isIPFieldChanged_ = false;
	private JButton startButton_;
	private JButton stopButton_;
	private JButton deleteButton_;
	private Double receive_ = 0.0;//��Ӧ��
	private Double send_ = 0.0;//������
	private java.util.Timer timer_ = new java.util.Timer();//ping��ʱ��
	private String tempResult_;
	private Matcher resultMatcher_;
	private TimerTask timerTask_;
	
	public IPBox(String defaultIP) {
		pinger_ = new Pinger(defaultIP);

		//��ʼ��Ԫ��
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
		});//ip���֪ͨ
		startButton_ = new JButton("��ʼ");
		stopButton_ = new JButton("ֹͣ");
		stopButton_.setEnabled(false);
		deleteButton_ = new JButton("ɾ��");

		resultMatcher_ = Pinger.RESPONSE_PATTERN.matcher("");

		JScrollPane jsp = new JScrollPane(resultArea_);
		jsp.setWheelScrollingEnabled(false);
		jsp.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		jsp.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_NEVER);
		jsp.setPreferredSize(new Dimension(350,200));
		//�Ż�����
		
		Box upBox = new Box(BoxLayout.X_AXIS);//IP������
		Box midBox = new Box(BoxLayout.X_AXIS);//��������
		Box downBox = new Box(BoxLayout.X_AXIS);//��ť��
		Box underBox = new Box(BoxLayout.X_AXIS);//ɾ������
		upBox.setPreferredSize(new Dimension(350,20));
		midBox.setPreferredSize(new Dimension(350,20));
		downBox.setPreferredSize(new Dimension(350,30));
		underBox.setPreferredSize(new Dimension(350,30));
		upBox.add(new JLabel("IP:"));
		upBox.add(ipField_);
		midBox.add(new JLabel("�����ʣ�"));
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
		//����UI
	}

//��Ա������װ
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

//ping����
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

//�����������
	public void addStartListener(ActionListener actionListener) {
		startButton_.addActionListener(actionListener);
	}
	public void addStopListener(ActionListener actionListener) {
		stopButton_.addActionListener(actionListener);
	}
	public void addDeleteListener(ActionListener actionListener) {
		deleteButton_.addActionListener(actionListener);
	}

//�ж�IP�Ƿ���
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

//�ر�
	public void close() {
		timer_.cancel();
		timerTask_ = null;
	}

//��ʱping����
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
