/*
 * 排序算法xhq.common.Sorter测试器
 * ***已废弃***
 */

package xhq.test;

import xhq.common.Sorter;

public class SorterTester
{
	public static void main(String[] args) 
	{
		int[] arr = {5,4,6,2,8,1,3,9,7,0};
		Sorter s = new Sorter(arr);
		arr = s.sortQuickD(0);
		for (int i:arr)
		{
			System.out.print(i + ",");
		}
	}
}
