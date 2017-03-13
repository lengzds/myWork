package com.zds.items;

import java.util.ArrayList;

public class WidgetItem {
	private String WidgetName;
	private ArrayList<HandleItem> Handles;
	private ArrayList<WidgetItem> DependWidgets;
	private int BaseStrength;//基础强度，与控件大小位置相关
	private int Strength;//附加强度
	private String type;

	public WidgetItem(){
		WidgetName=null;
		Handles=new ArrayList<>();
		DependWidgets=new ArrayList<>();
		BaseStrength=0;
		Strength=0;
	}
	public void setWidgetName(String WidgetName) {
		this.WidgetName = WidgetName;
	}

	public String getWidgetName() {
		return WidgetName;
	}

	public void addHandleName(HandleItem Handle) {
		this.Handles.add(Handle);
	}

	public ArrayList<HandleItem> getHandles() {
		return Handles;
	}

	public void addDependWidget(WidgetItem DependWidget) {
		this.DependWidgets.add(DependWidget);
	}

	public ArrayList<WidgetItem> getDependWidgets() {
		return DependWidgets;
	}

	public void setBaseStrength(int BaseStrength) {
		this.BaseStrength = BaseStrength;
	}

	public int getBaseStrength() {
		return BaseStrength;
	}

	public void setStrength(int Strength) {
		this.Strength = Strength;
	}
	public void addStrength(int Strength) {
		this.Strength += Strength;
	}

	public int getStrength() {
		return Strength;
	}
	
	public void setType(String type) {
		this.type = type;
	}

	public String getType() {
		return type;
	}

}
