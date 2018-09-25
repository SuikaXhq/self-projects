package xhq.mahjong;

import java.util.*;

public class Hand 
{
	private HashMap<Integer,Integer> handMap;
	private int numOfTile = 0;
	private ArrayList<Values> values;

	public Hand() {
		handMap = new HashMap<Integer,Integer>();
		for (int i = 1;i <= 9;i++)
		{
			handMap.put(i,0);
		}
	}

	public Hand(int[] hand) {
		this();
		if (hand.length <= 14)
		{
			for (int i:hand)
			{
				handMap.put(i,handMap.get(i) + 1);
				numOfTile++;
			}
		}
	}

	public Hand(HashMap<Integer,Integer> hand) {
		handMap = new HashMap<Integer,Integer>();
		for (int i = 1;i <= 9;i++)
		{
			handMap.put(i,hand.get(i));
			numOfTile += hand.get(i);
		}
	}

	public HashMap<Integer,Integer> getHand() {
		return handMap;
	}

	public int getTileNum() {
		return numOfTile;
	}

	public boolean add(int p) {
		if (handMap.get(p) < 4)
		{
			handMap.put(p,handMap.get(p) + 1);
			numOfTile++;
			return true;
		} else {
			return false;
		}
	}

	public boolean add(int p,int n) {
		if (handMap.get(p) <= 4 - n)
		{
			handMap.put(p,handMap.get(p) + n);
			numOfTile += n;
			return true;
		} else {
			return false;
		}
	}

	public boolean del(int p) {
		if (handMap.get(p) > 0)
		{
			handMap.put(p,handMap.get(p) - 1);
			numOfTile--;
			return true;
		} else {
			return false;
		}
	}

	public boolean del(int p,int n) {
		if (handMap.get(p) >= n)
		{
			handMap.put(p,handMap.get(p) - n);
			numOfTile -= n;
			return true;
		} else {
			return false;
		}
	}

	public String toString() {
		StringBuffer sb = new StringBuffer();
		int i = 1;
		while (i <= handMap.size())
		{
			int j = 1;
			while (i <= handMap.size() && handMap.get(i) == 0)
				i++;//scan
			while (i <= handMap.size() && j <= handMap.get(i))
			{
				sb = sb.append(i + ",");
				j++;
				assert(sb.length() < 27):"Current String = " + sb.toString();
			}
			i++;
		}
		if (sb.length() != 0)
		{
			sb = sb.deleteCharAt(sb.length() - 1);
		} else {
			sb = sb.append("Empty Hand");
		}
		return sb.toString();
	}
}
