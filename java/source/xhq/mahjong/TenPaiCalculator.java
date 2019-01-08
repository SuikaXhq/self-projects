package xhq.mahjong;

import java.util.*;

public class TenPaiCalculator
{
	private Hand handCalculating;
	private ValueAnalyzer patternCalculating;
	
	public TenPaiCalculator() {
	}

	public ArrayList<Integer> getTenPai(Hand hand) {
		boolean[] result = calc(hand);
		int i = 1;
		ArrayList<Integer> tenPai = new ArrayList<Integer>();
		for (boolean b:result)
		{
			if (b)
				tenPai.add(i);
			i++;
		}
		return tenPai;
	}
	
	public boolean[] calc(Hand hand)
	{
		handCalculating = hand;
		boolean[] result = new boolean[9];
		for (int k = 0; k < result.length; k++) {
			result[k] = false;
		}
		Hand tempHand = new Hand(hand.getHand());
		Hand temp;
		switch (hand.getTileNum() % 3)
		{
		case 1:
			for (int p = 1;p <= 9;p++)
			{
				if (tempHand.add(p))
				{
					int numPair = 0;
					ArrayList<Integer> pairs = new ArrayList<Integer>();
					for (int j = 1;j <= 9;j++)
					{
						if (tempHand.getHand().get(j) >= 2)
						{
							numPair++;
							pairs.add(j);
						}
					}
					if (numPair == 7)//七对判断
					{
						temp = new Hand(tempHand.getHand());
						if (judge(temp))
						{
							//两杯口
						}
						result[p-1] = true;
						tempHand.del(p);
						continue;
					}
					for (int i:pairs)
					{
						tempHand.del(i,2);
						temp = new Hand(tempHand.getHand());
						if (judge(temp))
							result[p-1] = true;
						tempHand.add(i,2);
					}
					tempHand.del(p);
				}
			}
			break;
		case 2:
			for (int p = 1;p <= 9;p++)
			{
				if (tempHand.add(p))
				{
					temp = new Hand(tempHand.getHand());
					if (judge(temp))
						result[p-1] = true;
					tempHand.del(p);
				}
			}
			break;
		}
		return result;
	}

	private boolean judge(Hand hand) {
		for (int i = 1;i <= 9;i++)
		{
			switch (hand.getHand().get(i))
			{
			case 0:
				break;
			case 1:
				if (i <= 7 && hand.getHand().get(i+1) > 0 && hand.getHand().get(i+2) > 0)
				{
					hand.del(i);
					hand.del(i+1);
					hand.del(i+2);
					return judge(hand);
				} else {
					return false;
				}
			case 2:
				if (i <= 7 && hand.getHand().get(i+1) > 1 && hand.getHand().get(i+2) > 1)
				{
					hand.del(i,2);
					hand.del(i+1,2);
					hand.del(i+2,2);
					return judge(hand);
				} else {
					return false;
				}
			case 3:
				hand.del(i,3);
				return judge(hand);
			case 4:
				hand.del(i,3);
				return judge(hand);
			}
		}
		return true;
	}
}