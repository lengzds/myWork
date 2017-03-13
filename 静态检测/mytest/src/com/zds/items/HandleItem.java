package com.zds.items;

import java.util.ArrayList;

public class HandleItem {
	
	private String HandleName;
	private ArrayList<String> Apis;
	private int Strength;
	
	public void setHandleName(String HandleName) {
		this.HandleName = HandleName;
	}

	public String getHandleName() {
		return HandleName;
	}
	
	public void addApi(String Api) {
		this.Apis.add(Api);
	}

	public ArrayList<String> getApis() {
		return Apis;
	}
	
	public void setStrength(int Strength) {
		this.Strength = Strength;
	}

	public int getStrength() {
		return Strength;
	}
}
