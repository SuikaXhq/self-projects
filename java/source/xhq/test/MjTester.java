/*
 * 算听算法测试器
 * ***已废弃***
 */


package xhq.test;

import xhq.mahjong.*;
import java.io.*;

class MjTester
{
	public static void main(String[] args) 
	{
		while (true)
		{
			try
			{
				BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
				String s = br.readLine();
				if (!s.equals("exit"))
				{
					if (s.length() == 13)
					{
						int[] hand = new int[13];
						for (int i = 0;i < 13;i++)
						{
							hand[i] = Integer.parseInt(s.substring(i,i+1));
						}
						if (!TenPaiCalculator.getTenPai(new Hand(hand)).isEmpty())
						{
							System.out.println("テンパイ：" + TenPaiCalculator.getTenPai(new Hand(hand)));
						} else {
							System.out.println("ノーテン");
						}
						continue;
					}
					MjMountain mjm = new MjMountain();
					System.out.println(mjm.getHand());
					if (!TenPaiCalculator.getTenPai(mjm.getHand()).isEmpty())
					{
						System.out.println("テンパイ：" + TenPaiCalculator.getTenPai(mjm.getHand()));
					} else {
						System.out.println("ノーテン");
					}
				} else {
					System.exit(0);
				}
			}
			catch (IOException ioe)
			{
				System.out.println(ioe);
			}
			
		}
		
	}
}
