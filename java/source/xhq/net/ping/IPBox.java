package xhq.net.ping;

import javax.swing.*;
import java.awt.event.*;
import java.awt.*;

public class IPBox
{
	Box backBox_;//ĸ��box
	JLabel lossRateLabel_;//�����ʰٷֱ�
	JTextArea resultArea_;//�ش�������
	JTextField ipField_;//IP������
	JButton startButton_;//��ʼ��ť
	JButton stopButton_;//ֹͣ��ť
	Double receive_ = 0.0;//��Ӧ��
	Double send_ = 0.0;//������
	String defaultIP_;//Ĭ��IP
	Timer timer_=new Timer();//ping��ʱ��
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
	};//��ʱping����
	
	//���캯��
	public Ipbox(Pinger pinger,String defaultIp,PingerStarter pst){
		backBox_=new Box(BoxLayout.Y_AXIS);
		lossRateLabel_=new JLabel("0.00%");
		resultArea_=new JTextArea();
		resultArea_.setEditable(false);
		resultArea_.setSize(350,200);
		ipField_=new JTextField(defaultIP_);
		ipField_.setPreferredSize(new Dimension(350,20));
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

//		startButton_.addActionListener(pinger.new StartListener());
//		stopButton_.addActionListener(pinger.new StopListener());
		//�������
		
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
		backBox_.add(upBox);
		backBox_.add(midBox);
		backBox_.add(downBox);
		backBox_.add(jsp);
		//����UI
	}
	
}
