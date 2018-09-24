package xhq.mahjong;

import java.util.*;

public class MjMountain
{
	public HashMap<Integer,Integer> mountain;
	private int[] tempHand = {0,0,0,0,0,0,0,0,0,0,0,0,0};
	Hand handOrigin;

	public MjMountain() {
		mountain = new HashMap<Integer,Integer>();
		for (int i = 1;i <= 9;i++)
		{
			mountain.put(i,4);
		}
		create();
	}

	public Hand getHand() {
		return new Hand(handOrigin.getHand());
	}

	private void create() {
		int a;
		Random rd = new Random();
		for (int i = 1;i <= 13;i++)
		{
			a = rd.nextInt(9) + 1;
			while (mountain.get(a) == 0)
				a = rd.nextInt(9) + 1;
			mountain.put(a,mountain.get(a) - 1);
			tempHand[i-1] = a;
		}
		handOrigin = new Hand(tempHand);
	}

	public Hand draw(Hand hand,int p) {
		Hand temp = new Hand(hand.getHand());
		if (temp.del(p))
		{
			Random rd = new Random();
			int a = rd.nextInt(9) + 1;
			while (mountain.get(a) == 0)
				a = rd.nextInt(9) + 1;
			mountain.put(a,mountain.get(a) - 1);
			temp.add(a);
		}
		return temp;
	}
}
