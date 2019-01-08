/*
 * Expression Calculator
 * Ver0.0.1
 * 
 * 输入：表达式字符串
 * 输出：结果
 * 支持：( ) + - * /
 */

package xhq.math;

import java.util.*;
import java.util.regex.*;

public class ExpressionCalculator {
	public static final Pattern EXPRESSION_PATTERN = Pattern.compile("[(\\d+)(\\d*\\.\\d*)\\-\\+]");
	private String inputString_;
	
	public double calculate(String inputString) {
		inputString_ = inputString;
		
	}
	private 
	private double level0() {}
	
	private class Sign {
		private 
	}
}
