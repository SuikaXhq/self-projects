package xhq.common;

/*

Version : #2

*/

//import java.util.ArrayList;

public class Sorter
{
	int[] arr = {0,0};
	double[] arrd = {0.0,0.0};
	public static final boolean SORT_INT = true;
	public static final boolean SORT_DOUBLE = false;

	private Sorter(){}
	public Sorter(int[] ints)
	{
		arr = ints;
	}

	public Sorter(double[] doubles)
	{
		arrd = doubles;
	}

	public Sorter(int[] ints,double[] doubles)
	{
		arr = ints;
		arrd = doubles;
	}

	//int Bubble
	public static int[] sortBubbleU(int[] ints)
	{
		int n = ints.length;
		for (int i = 1;i <= n-1;i++)
		{
			for (int j = 1;j <= n-i;j++)
			{
				if (ints[j-1] > ints[j])
				{
					int temp = ints[j-1];
					ints[j-1] = ints[j];
					ints[j] = temp;
				}
			}
		}
		return ints;
	}

	public static int[] sortBubbleD(int[] ints)
	{
		int n = ints.length;
		for (int i = 1;i <= n-1;i++)
		{
			for (int j = 1;j <= n-i;j++)
			{
				if (ints[j-1] < ints[j])
				{
					int temp = ints[j-1];
					ints[j-1] = ints[j];
					ints[j] = temp;
				}
			}
		}
		return ints;
	}
	//int Bubble

	
	//double Bubble
	public static double[] sortBubbleU(double[] doubles)
	{
		int n = doubles.length;
		for (int i = 1;i <= n-1;i++)
		{
			for (int j = 1;j <= n-i;j++)
			{
				if (doubles[j-1] > doubles[j])
				{
					double temp = doubles[j-1];
					doubles[j-1] = doubles[j];
					doubles[j] = temp;
				}
			}
		}
		return doubles;
	}

	public static double[] sortBubbleD(double[] doubles)
	{
		int n = doubles.length;
		for (int i = 1;i <= n-1;i++)
		{
			for (int j = 1;j <= n-i;j++)
			{
				if (doubles[j-1] < doubles[j])
				{
					double temp = doubles[j-1];
					doubles[j-1] = doubles[j];
					doubles[j] = temp;
				}
			}
		}
		return doubles;
	}
	//double Bubble

	//Quick
	private int partQuickU(boolean isInt,int l,int r)
	{
		int mid = getMidI(arr,l,r);
		int left = l;
		int right = r;
		if (isInt)
		{
			int key = arr[mid];
			arr[mid] = arr[r];
			while (left < right)
			{
				while (left < right && arr[left] <= key)left++;
				arr[right] = arr[left];
				while (left < right && arr[right] >= key)right--;
				arr[left] = arr[right];
			}
			arr[right] = key;
			return right;
		} else {
			double key = arrd[mid];
			arrd[mid] = arrd[r];
			while (left < right)
			{
				while (left < right && arrd[left] <= key)left++;
				arrd[right] = arrd[left];
				while (left < right && arrd[right] >= key)right--;
				arrd[left] = arrd[right];
			}
			arrd[right] = key;
			return right;
		}
		
	}

	private void sortQuickU(boolean isInt,int left,int right)
	{
		if (left >= right)
			return;
		int i = partQuickU(isInt,left,right);
		sortQuickU(isInt,left,i-1);
		sortQuickU(isInt,i+1,right);
	}

	private int partQuickD(boolean isInt,int l,int r)
	{
		int mid = getMidI(arr,l,r);
		int left = l;
		int right = r;
		if (isInt)
		{
			int key = arr[mid];
			arr[mid] = arr[r];
			while (left < right)
			{
				while (left < right && arr[left] >= key)left++;
				arr[right] = arr[left];
				while (left < right && arr[right] <= key)right--;
				arr[left] = arr[right];
			}
			arr[right] = key;
			return right;
		} else {
			double key = arrd[mid];
			arrd[mid] = arrd[r];
			while (left < right)
			{
				while (left < right && arrd[left] >= key)left++;
				arrd[right] = arrd[left];
				while (left < right && arrd[right] <= key)right--;
				arrd[left] = arrd[right];
			}
			arrd[right] = key;
			return right;
		}
		
	}

	private void sortQuickD(boolean isInt,int left,int right)
	{
		if (left >= right)
			return;
		int i = partQuickD(isInt,left,right);
		sortQuickD(isInt,left,i-1);
		sortQuickD(isInt,i+1,right);
	}

	public int[] sortQuickU(int[] ints)
	{
		arr = ints;
		sortQuickU(SORT_INT,0,arr.length - 1);
		return arr;
	}

	public int[] sortQuickU(int i)
	{
		sortQuickU(SORT_INT,0,arr.length - 1);
		return arr;
	}

	public int[] sortQuickD(int[] ints)
	{
		arr = ints;
		sortQuickD(SORT_INT,0,arr.length - 1);
		return arr;
	}

	public int[] sortQuickD(int i)
	{
		sortQuickD(SORT_INT,0,arr.length - 1);
		return arr;
	}

	public double[] sortQuickU(double[] doubles)
	{
		arrd = doubles;
		sortQuickU(SORT_DOUBLE,0,arrd.length - 1);
		return arrd;
	}

	public double[] sortQuickU(double d)
	{
		sortQuickU(SORT_DOUBLE,0,arrd.length - 1);
		return arrd;
	}

	public double[] sortQuickD(double[] doubles)
	{
		arrd = doubles;
		sortQuickD(SORT_DOUBLE,0,arrd.length - 1);
		return arrd;
	}

	public double[] sortQuickD(double d)
	{
		sortQuickD(SORT_DOUBLE,0,arrd.length - 1);
		return arrd;
	}
	//Quick

	//getMid
	public static int getMid(int a,int b,int c)
	{
		if (a > b)
		{
			if (a > c)
			{
				if (b > c)
				{
					return b;
				} else {
					return c;
				}
			} else {
				return a;
			}
		} else {
			if (a > c)
			{
				return a;
			} else {
				if (b > c)
				{
					return c;
				} else {
					return b;
				}
			}
		}
	}

	public static double getMid(double a,double b,double c)
	{
		if (a > b)
		{
			if (a > c)
			{
				if (b > c)
				{
					return b;
				} else {
					return c;
				}
			} else {
				return a;
			}
		} else {
			if (a > c)
			{
				return a;
			} else {
				if (b > c)
				{
					return c;
				} else {
					return b;
				}
			}
		}
	}

	private static int getMidI(int[] ints,int l,int r)
	{
		int n = r - l + 1;
		int mid = (int) ((r + l) / 2);
		if (ints[l] > ints[mid])
		{
			if (ints[mid] > ints[r])
			{
				return mid;
			} else {
				if (ints[l] > ints[r])
				{
					return r;
				} else {
					return l;
				}
			}
		} else {
			if (ints[l] > ints[r])
			{
				return l;
			} else {
				if (ints[mid] > ints[r])
				{
					return r;
				} else {
					return mid;
				}
			}
		}
	}

	public static int getMidI(double[] doubles,int l,int r)
	{
		int n = r - l + 1;
		int mid = (int) ((r + l) / 2);
		if (doubles[l] > doubles[mid])
		{
			if (doubles[mid] > doubles[r])
			{
				return mid;
			} else {
				if (doubles[l] > doubles[r])
				{
					return r;
				} else {
					return l;
				}
			}
		} else {
			if (doubles[l] > doubles[r])
			{
				return l;
			} else {
				if (doubles[mid] > doubles[r])
				{
					return r;
				} else {
					return mid;
				}
			}
		}
	}
	//getMid

	/*
	//arrylist Bubble
	public static ArrayList sortBubbleU(ArrayList a)
	{
		int n = a.size();
		for (int i = 1;i <= n-1;i++)
		{
			for (int j = 1;j <= n-i;j++)
			{
				if (a.get(j) > a.get(j+1))
				{
					a.add(a.get(j));
					a.set(j,a.get(j+1));
					a.set(j+1,a.get(n+1));
					a.remove(n+1);
				}
			}
		}
		return a;
	}

	public static ArrayList sortBubbleD(ArrayList a)
	{
		int n = a.size();
		for (int i = 1;i <= n-1;i++)
		{
			for (int j = 1;j <= n-i;j++)
			{
				if (a.get(j) < a.get(j+1))
				{
					a.add(a.get(j));
					a.set(j,a.get(j+1));
					a.set(j+1,a.get(n+1));
					a.remove(n+1);
				}
			}
		}
		return a;
	}
	//arrylist Bubble
	*/
}
