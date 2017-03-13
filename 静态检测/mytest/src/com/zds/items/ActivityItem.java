package com.zds.items;

import java.util.ArrayList;
import java.util.HashMap;

public class ActivityItem {
	private String activityName;
	private ArrayList<WidgetItem> widgets;
	private ArrayList<String> apis;
	private ArrayList<ActivityItem> relateActivitys;
	private HashMap<ActivityItem, WidgetItem> widgetToActivity;
	private double strength;
	private boolean isMainActivity;
	private double sout;

	public ActivityItem(String a) {
		activityName = a;
		widgets = new ArrayList<>();
		apis = new ArrayList<>();
		relateActivitys = new ArrayList<>();
		widgetToActivity = new HashMap<>();
		strength = 0;
		isMainActivity = false;
		sout = 0;
	}

	public void setActivityName(String ActivityName) {
		this.activityName = ActivityName;
	}

	public String getActivityName() {
		return activityName;
	}

	public void addWidget(WidgetItem WidgetName) {
		this.widgets.add(WidgetName);
	}

	public ArrayList<WidgetItem> getWidgets() {
		return widgets;
	}

	public void addApi(String Api) {
		this.apis.add(Api);
	}

	public ArrayList<String> getApis() {
		return apis;
	}

	public void addRelateActivity(ActivityItem RelateActivity, WidgetItem widget) {
		this.relateActivitys.add(RelateActivity);
		if (widget != null)
			this.widgetToActivity.put(RelateActivity, widget);
	}

	public HashMap<ActivityItem, WidgetItem> getWidgetToActivity() {
		return widgetToActivity;
	}

	public ArrayList<ActivityItem> getRelateActivitys() {
		return relateActivitys;
	}

	public void setStrength(double Strength) {
		this.strength = Strength;
	}

	public double getStrength() {
		return strength;
	}

	public void setIsMainActivity(boolean is) {
		this.isMainActivity = is;
	}

	public boolean getIsMainActivity() {
		return isMainActivity;
	}

	public void setSout(double sout) {
		this.sout = sout;
	}

	public double getSout() {
		return sout;
	}

}
