package xhq.mahjong;

import xhq.common.Sorter;
import java.util.Random;

class MjAlpha 
{
	int[] numOfMj = {4,4,4,4,4,4,4,4,4};
	int[] handLock = {0,0,0,0,0,0,0,0,0,0,0,0,0};
	public static void main(String[] args) 
	{
		MjAlpha mj = new MjAlpha();
		mj.handCreate();
		for (int i:mj.handLock)
		{
			System.out.print(i);
		}
	}

	public int[] handCreate()
	{
		int a;
		Random rd = new Random();
		for (int i = 1;i <= 13;i++)
		{
			while (true)
			{
				a = rd.nextInt(9);
				if (numOfMj[a] > 0)
				{
					numOfMj[a]--;
					handLock[i-1] = a + 1;
					break;
				}
			}
		}
		handLock = Sorter.sortBubbleU(handLock);
		return handLock;
	}

	public void refresh()
	{
		numOfMj = new int[] {4,4,4,4,4,4,4,4,4};
		handLock = new int[] {0,0,0,0,0,0,0,0,0,0,0,0,0};
	}

}
