package com.zds.analysis;

/**
 * 总结所有的api数量
 */
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import com.zds.items.ActivityItem;
import com.zds.items.HandleItem;
import com.zds.items.WidgetItem;

public class apiNumber {

	private static HashMap<String, Integer> apis = new HashMap<>();

	public static void main(String[] args) {
		apis.put("aaa", 1);
		apis.put("bbb", 1);
		apis.put("ccc", 1);
		apis.put("aaa", 4);

		Iterator iter = apis.entrySet().iterator();
		while (iter.hasNext()) {
			Map.Entry entry = (Map.Entry) iter.next();
			Object key = entry.getKey();
			Object val = entry.getValue();
			System.out.println(key + "  " + val);
		}
		// init();
	}

	public static void init() {
		ArrayList<ActivityItem> activitylist = new ArrayList<>();
		for (ActivityItem a : activitylist) {
			int stre_a = (int) a.getStrength();
			record(a.getApis(), stre_a);
			for (WidgetItem w1 : a.getWidgets()) {
				for (HandleItem h : w1.getHandles()) {
					record(h.getApis(), h.getStrength()); 
				}
			}
		}
	}

	public static void record(ArrayList<String> a, int strength) {
		for (String s : a) {
			if (apis.containsKey(s)) {
				apis.put(s, (apis.get(s) + strength));
			} else {
				apis.put(s, strength);
			}
		}
	}
}
