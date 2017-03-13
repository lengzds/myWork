package test;

import java.text.DecimalFormat;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MyTest {
	public static double a1 = 100, a2 = 1, a3 = 1, a4 = 1, a5 = 1;

	public static double n1 = 2, n2 = 5, n3 = 10, n4 = 4;

	public static void main(String[] args) {
		compute();

	}

	public static void compute() {
		double e = 0.5;

		double a01 = a1;
		double a02 = (n2 / (n1 + n2 + n2 + n3) * a1 + 1 / (n4 + 3) * a3) * e;
		double a03 = (n1 / (n1 + n2 + n2 + n3) * a1 + n1 / (n1 + 1) * a2 + n1
				/ (n1 + 1) * a4 + 0.5 * a5)
				* e;
		double a04 = (n2 / (n1 + n2 + n2 + n3) * a1 + 1 / (n4 + 3) * a3) * e;
		double a05 = (n3 / (n1 + n2 + n2 + n3) * a1 + n4 / (n4 + 3) * a3) * e;

		DecimalFormat df1 = new DecimalFormat("##00.00000 ");
		System.out.println("a1= " + df1.format(a01) + " a2= " + df1.format(a02)
				+ " a3= " + df1.format(a03) + " a4= " + df1.format(a04)
				+ " a5= " + df1.format(a05));

		if (a05 - a5 < 0.00001) {
			return;
		} else {
			a1 = a01;
			a2 = a02;
			a3 = a03;
			a4 = a04;
			a5 = a05;
			compute();
		}
	}
}
