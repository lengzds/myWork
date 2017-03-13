package com.zds.Strength;

/**
 * ͳ��widget��Ϣ������ʵ��
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
		// ����widgetʵ��
		ArrayList<normal> allwidget = new ArrayList<normal>();
		allwidget.add(setnormal("t1", "TextView", 5));
		allwidget.add(setnormal("b1", "Button", 30));
		allwidget.add(setnormal("t2", "TextView", 8));
		allwidget.add(setnormal("b2", "Button", 40));
		// ��λ�õ�ƽ��ǿ��
		int average = 8;
		// ͳ�Ƹ�������ռ�ܴ�С
		int n = 0, total = 0;

		switch (s) {
		case "����":
			average = 2;
		case "��":
			average = 1;
		case "����":
			average = 6;
		case "��":
			average = 9;
		case "��":
			average = 15;
		case "��":
			average = 8;
		case "��":
			average = 10;
		}

		for (normal l : allwidget) {
			n++;
			total += l.area;
		}
		for (normal l : allwidget) {
			// ����ƽ��ǿ�ȼ���õ�����ǿ�Ȱ��ձ�������
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
