package com.zds.Strength;

/**
 * 统计widget信息，生成实例
 */
import java.util.ArrayList;

import com.zds.items.WidgetItem;

class normal {
	public String name;
	public String type;
	public int area;
}

public class WidgetStrength_normal {
	public static void main(String[] args) {
		widget_base_strength("z");

	}

	public static void widget_base_strength(String s) {
		// 生成widget实例
		ArrayList<normal> allwidget = new ArrayList<normal>();
		allwidget.add(setnormal("t1", "TextView", 5));
		allwidget.add(setnormal("b1", "Button", 30));
		allwidget.add(setnormal("t2", "TextView", 8));
		allwidget.add(setnormal("b2", "Button", 40));
		// 本位置的平均强度
		int average = 8;
		// 统计个数及所占总大小
		int n = 0, total = 0;

		switch (s) {
		case "左上":
			average = 2;
		case "上":
			average = 1;
		case "右上":
			average = 6;
		case "左":
			average = 9;
		case "中":
			average = 15;
		case "右":
			average = 8;
		case "下":
			average = 10;
		}

		for (normal l : allwidget) {
			n++;
			total += l.area;
		}
		for (normal l : allwidget) {
			// 将有平均强度计算得到的总强度按照比例分配
			int m = average * n * l.area / total;
			WidgetItem w = new WidgetItem();
			w.setWidgetName(l.name);
			w.setBaseStrength(m);
			// addtolist;
		}

	}

	public static normal setnormal(String a, String b, int c) {
		normal n = new normal();
		n.name = a;
		n.type = b;
		n.area = c;
		return n;
	}
}
