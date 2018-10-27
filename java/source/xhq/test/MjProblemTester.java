/*
 *  ���⣺��һɫ�����Ƿ��ܱ鼰�����������
 */

package xhq.test;

import java.util.*;
import java.io.*;
import xhq.mahjong.*;

public class MjProblemTester
{
	public static void main(String[] args) 
	{
		try {
			File resultFile = new File("Results.txt");
			BufferedWriter resultWriter = new BufferedWriter(new FileWriter(resultFile));
			Hand hand = null;
			TenPaiCalculator calculator = new TenPaiCalculator();
			TenPaiResult result = null;
			int temp = 0;
			int comb = 5*5*5*5*5*5*5*5*5;//������Ʊ�ʾ���
			ArrayList<Integer> combList = new ArrayList<Integer>();//�������
			ArrayList<TenPaiResult> tenPaiList = new ArrayList<TenPaiResult>();//�������
			HashMap<TenPaiResult, Hand> resultMap = new HashMap<TenPaiResult, Hand>();//���map
			
			for (int i = 0; i < comb; i++)//�����������
				if (check(i)) {//�޳���������ϣ����Ͼͼ���
					combList.add(i);
				}
			for (int i = 1; i < (1 << 10); i++)//�����������
				tenPaiList.add(new TenPaiResult(i));
			
			ArrayList<Integer> resultList = null;
			for (int i : combList) {
				hand = parseToHand(i);
				if (!(resultList = calculator.getTenPai(hand)).isEmpty()) {
					//�����������
					result = new TenPaiResult(resultList);
					if (!resultMap.containsKey(result))
						resultMap.put(result, hand);
				}
			}
			
			//���
			for (TenPaiResult r:tenPaiList) {
				resultWriter.write(r.toString() + ": ");
				if (resultMap.containsKey(r))
					resultWriter.write(resultMap.get(r).toString() + "\r\n");
				else
					resultWriter.write("None\r\n");
			}
			resultWriter.close();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	private static Hand parseToHand(int i) {
		//�������ΪHand
		HashMap<Integer,Integer> temp = new HashMap<Integer,Integer>();
		for (int index = 1;index <= 9;index++)
		{
			temp.put(index,0);
		}
		int curse = 1;
		while (i != 0) {
			temp.put(curse, i % 5);
			curse++;
			i /= 5;
		}
		return new Hand(temp);
	}
	
	private static boolean check(int i) {
		//����Ƿ�Ϊ13��
		int temp = 0;
		while (i != 0) {
			temp += i % 5;
			i /= 5;
		}
		return temp == 13;
	}

	public static class TenPaiResult
	{
		final private boolean[] result_;
		public TenPaiResult(boolean[] result) {
			result_ = result;
		}
		
		public TenPaiResult(int result) {
			result_ = new boolean[9];
			for (int i = 0; i < 9; i++) {
				if (((1 << i) & result) != 0)
					result_[i] = true;
			}
		}
		
		public TenPaiResult(ArrayList<Integer> list) {
			result_ = new boolean[9];
			for (int i:list)
				result_[i-1] = true;
		}
		
		public boolean[] getResult() {
			return result_;
		}

		public String toString() {
			String str = "";
			for (int i = 0; i < 9; i++)
				if (result_[i])
					str += i + 1;
			return str;
		}

		public int hashCode() {
			return toString().hashCode();
		}
		public boolean equals(Object resultB) {
			for (int i = 0; i < 9; i++)
				if (result_[i] != ((TenPaiResult)resultB).getResult()[i])
					return false;
			return true;
		}
	}
}
