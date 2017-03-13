package com.zds.Strength;

import java.util.ArrayList;

import com.zds.items.ActivityItem;
import com.zds.items.WidgetItem;

public class ActivityStrength {
	private static final double back = 1;
	private static final double yuzhi = 0.00001;
	private static final double shuaijian = 0.5;
	private static final double mainStrength = 100;

	// 测试用例
	public static void main(String[] args) {
		init();
	}

	public static void add(ActivityItem a, int b, ActivityItem c) {
		if (b != 0) {
			WidgetItem w = new WidgetItem();
			w.setBaseStrength(b);
			a.addRelateActivity(c, w);
		} else
			a.addRelateActivity(c, null);

	}

	public static void init() {
		// get activity list
		ArrayList<ActivityItem> activitylist = new ArrayList<>();

		// 测试用例
		ActivityItem a1 = new ActivityItem("a1");
		ActivityItem a2 = new ActivityItem("a2");
		ActivityItem a3 = new ActivityItem("a3");
		ActivityItem a4 = new ActivityItem("a4");
		ActivityItem a5 = new ActivityItem("a5");
		add(a1, 5, a2);
		add(a2, 0, a1);
		add(a1, 2, a3);
		add(a3, 0, a1);
		add(a1, 5, a4);
		add(a4, 0, a1);
		add(a1, 10, a5);
		add(a5, 0, a1);
		add(a2, 2, a3);
		add(a3, 0, a2);
		add(a4, 2, a3);
		add(a3, 0, a4);
		add(a3, 4, a5);
		add(a5, 0, a3);
		a1.setIsMainActivity(true);
		activitylist.add(a1);
		activitylist.add(a2);
		activitylist.add(a3);
		activitylist.add(a4);
		activitylist.add(a5);

		// 初始化强度
		for (ActivityItem a : activitylist) {
			if (a.getIsMainActivity()) {
				a.setStrength(mainStrength);
			} else {
				a.setStrength(1);
			}
			double sout = 0;
			for (ActivityItem aa : a.getRelateActivitys()) {
				if (a.getWidgetToActivity().containsKey(aa)) {
					sout += a.getWidgetToActivity().get(aa).getBaseStrength();
				} else {
					sout += back;
				}
			}
			a.setSout(sout);

		}

		compute(activitylist);

		// for(ActivityItem a : activitylist){//证明是同一个实例
		// System.out.println(a.getActivityName()+
		// "   "+a.getStrength()+"   "+a.getSout());
		// System.out.println(a.toString());
		// ActivityItem a11=a.getRelateActivitys().get(0);
		// System.out.println(a11.toString());
		// }

	}

	// 测试例结束

	public static void compute(ArrayList<ActivityItem> activitylist) {
		double oldset = 0, newset = 0;
		for (ActivityItem a : activitylist) {// 遍历
			if (!a.getIsMainActivity()) {
				double count = 0;
				for (ActivityItem aa : a.getRelateActivitys()) {// 遍历相关activity
					if (aa.getWidgetToActivity().containsKey(a)) {// 相关有控件跳转到当前
						count += (aa.getWidgetToActivity().get(a)
								.getBaseStrength())
								/ aa.getSout()
								* aa.getStrength();
						// 添加
					} else {
						count += back / aa.getSout() * aa.getStrength();
					}

				}
				if (a == activitylist.get(activitylist.size() - 1)) {
					oldset = a.getStrength();
					newset = shuaijian * count;
				}
				a.setStrength(count * shuaijian);
			}
			System.out.println(a.getStrength());
		}
		if (Math.abs(oldset - newset) < yuzhi) {
			return;
		} else {
			compute(activitylist);
		}

	}
}
