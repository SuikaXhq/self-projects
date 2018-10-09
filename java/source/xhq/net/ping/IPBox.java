package xhq.net.ping;

import javax.swing.*;
import java.awt.event.*;
import java.awt.*;

public class IPBox
{
	private Box backBox_;//ĸ��box
	private String defaultIP_;//Ĭ��IP
	private JLabel lossRateLabel_;//�����ʰٷֱ�
	private JTextArea resultArea_;//�ش�������
	private JTextField ipField_;//IP������
	private JButton startButton_;//��ʼ��ť
	private JButton stopButton_;//ֹͣ��ť
	private Double receive_ = 0.0;//��Ӧ��
	private Double send_ = 0.0;//������
	private Timer timer_=new Timer();//ping��ʱ��
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
	};//��ʱping����
	}
	
	//���캯��
	public IPBox(String defaultIp) {
		defaultIp_ = defaultIp;

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

	//�����������
	public void addStartListener(ActionListener actionListener) {
		startButton_.addActionListener(actionListener);
	}
	public void addStopListener(ActionListener actionListener) {
		stopButton_.addActionListener(actionListener);
	}
}
