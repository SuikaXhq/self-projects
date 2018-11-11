/*
 * 含进制转换的长整型类
 * 未实现：minus(), multiply(), divide(), mod()
 * 未解决：高于十进制下的toString()
 */

package xhq.math;

public class BasedInteger {
	private int base_ = 10;
	private long valueInBase10_ = 0;

	public BasedInteger() {
	}
	public BasedInteger(int base) {
		if (base <= 0)
			throw new RuntimeException("Negative base statement");
		base_ = base;
	}
	public BasedInteger(long value) {
		valueInBase10_ = value;
	}
	public BasedInteger(long valueInBase10, int base) {
		if (base <= 0)
			throw new RuntimeException("Negative base statement");
		base_ = base;
		valueInBase10_ = valueInBase10;
	}
	public BasedInteger(String value) {
		long temp = Long.parseLong(value);
		if (temp < 0)
			throw new RuntimeException("Negative value statement");
		valueInBase10_ = temp;
	}
	public BasedInteger(String value, int base, boolean isValueBased10) {
		if (base <= 0)
			throw new RuntimeException("Negative base statement");
		long valueLiteral = Long.parseLong(value);
		
		base_ = base;
		if (!isValueBased10) {
			long temp = 0;
			long tempBase = 1;
			while (valueLiteral != 0) {
				temp += valueLiteral % 10 * tempBase;
				valueLiteral /= 10;
				tempBase *= base_;
			}
			valueInBase10_ = temp;
		} else {
			valueInBase10_ = valueLiteral;
		}
	}

	public void setValueInBase10(long value) {
		valueInBase10_ = value;
	}
	public void setValue(String value) {
		long valueLiteral = Long.parseLong(value);
		if (base_ == 10) {
			setValueInBase10(valueLiteral);
		} else {
			long temp = 0;
			long tempBase = 1;
			while (valueLiteral != 0) {
				temp += valueLiteral % 10 * tempBase;
				valueLiteral /= 10;
				tempBase *= base_;
			}
			valueInBase10_ = temp;
		}
	}
	public void setBase(int base) {
		if (base <= 0)
			throw new RuntimeException("Negative base statement");
		base_ = base;
	}
	public long getValueInBase10() {
		return valueInBase10_;
	}
	public int getBase() {
		return base_;
	}
	public String toString() {
		if (valueInBase10_ == 0)
			return "0";
		StringBuffer temp = new StringBuffer();
		long tempValue = valueInBase10_;
		if (tempValue > 0) {
			while (tempValue != 0) {
				temp.insert(0, tempValue % base_);
				tempValue /= base_;
			}
		} else {
			while (tempValue != 0) {
				temp.insert(0, -tempValue % base_);
				tempValue /= base_;
			}
			temp.insert(0, '-');
		}
		return temp.toString();
	}
	public static BasedInteger add(BasedInteger bInt1, BasedInteger bInt2) {
		int base1 = bInt1.getBase();
		int base2 = bInt2.getBase();
		if (base1 <= base2) {
			return new BasedInteger(bInt1.getValueInBase10() + bInt2.getValueInBase10(), base1);
		} else {
			return new BasedInteger(bInt1.getValueInBase10() + bInt2.getValueInBase10(), base2);
		}
	}
}