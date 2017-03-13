package com.zds.Strength;

import java.util.ArrayList;

import com.zds.items.ActivityItem;
import com.zds.items.HandleItem;
import com.zds.items.WidgetItem;

public class HandleStrength {
	public static void main(String[] args) {
		ArrayList<ActivityItem> activitylist = new ArrayList<>();
		compute_handle_strength(activitylist);
	}

	public static void compute_handle_strength(
			ArrayList<ActivityItem> activitylist) {

		for (ActivityItem activity : activitylist) {
			for (WidgetItem widget : activity.getWidgets()) {
				if (!widget.getDependWidgets().isEmpty()) {
					int count = 0;
					// 统计所有相关空间总的基础强度
					for (WidgetItem w1 : widget.getDependWidgets()) {
						count += typeStrength(w1);
					}
					// 按照强度比例将基础强度加给相关控件
					for (WidgetItem w2 : widget.getDependWidgets()) {
						w2.addStrength(widget.getBaseStrength() / count
								* typeStrength(w2));
					}
				}
				if (!widget.getHandles().isEmpty()) {
					for (HandleItem handle : widget.getHandles()) {
						double s = 0;
						for (String api : handle.getApis()) {
							// 特殊API强度
							s += apiStrength(api);
						}
						// API个数强度增益
						int ss = (int) (s + Math.log(handle.getApis().size()));
						// handle强度=api强度+控件基础强度+控件附加强度
						handle.setStrength(ss + widget.getStrength()
								+ widget.getBaseStrength());
					}
				}

			}

		}
	}

	public static int typeStrength(WidgetItem w) {
		switch (w.getType()) {
		case "Button":
			return 8;
		case "CheckBox":
			return 6;
		case "RadioButton":
			return 6;
		case "ToggleButton":
			return 6;

		case "TimePicker":
			return 1;
		case "DatePicker":
			return 1;

		case "ImageView":
			return 3;
		case "ImageButton":
			return 3;

		case "TextView":
			return 2;
		case "EditText":
			return 4;
		case "AutoCompleteTextView":
			return 2;
		default:
			return 0;
		}

	}

	public static double apiStrength(String a) {
		// switch (a) {
		// case "query":
		// return 50;
		// default:
		// return 0;
		// }

		// 用.*android.content.Intent.*<init>.*匹配

		if (a.contains("android.app.Activity.getSystemService"))
			return 10.95110474796434;
		if (a.contains("android.view.ContextThemeWrapper.getSystemService"))
			return 10.915633770053805;
		if (a.contains("android.content.ContextWrapper.startService"))
			return 8.102283624480073;
		if (a.contains("android.content.ContextWrapper.sendBroadcast"))
			return 7.848933726364071;
		if (a.contains("android.content.ContextWrapper.bindService"))
			return 7.242082359256962;
		if (a.contains("android.content.ContextWrapper.sendOrderedBroadcast"))
			return 5.262690188904886;
		if (a.contains("android.app.ActivityManager.getRunningServices"))
			return 7.027314514039777;
		if (a.contains("android.app.Activity.startActivity"))
			return 7.027314514039777;

		// content数据库操作
		if (a.contains("android.content.ContentResolver.query"))
			return 12;
		if (a.contains("android.content.ContentResolver.delate"))
			return 7;
		if (a.contains("android.content.ContentResolver.insert"))
			return 7;
		if (a.contains("android.content.ContentResolver.update"))
			return 7;
		if (a.contains("android.database.sqlite.SQLiteDatabase.insert"))
			return 10.887754639419377;
		if (a.contains("android.database.sqlite.SQLiteCursor.getDatabase"))
			return 10.579361404425617;
		if (a.contains("android.database.sqlite.SQLiteCursor.onMove"))
			return 10.308685958415747;
		if (a.contains("android.database.sqlite.SQLiteDatabase.delete"))
			return 9.974970654157993;
		if (a.contains("android.database.sqlite.SQLiteDatabase.query"))
			return 9.699288417222856;
		if (a.contains("android.database.sqlite.SQLiteDatabase.update"))
			return 8.895355616996392;

		if (a.contains("android.content.Intent.setAction"))
			return 9.925444719888171;
		if (a.contains("android.content.Intent.getExtras"))
			return 8.138272638530186;
		if (a.contains("android.content.Intent.getData"))
			return 8.008698182988528;
		if (a.contains("android.content.Intent.putExtra"))
			return 5.187385805840755;

		// 手机文件
		if (a.contains("java.io.FileInputStream.read"))
			return 10;
		if (a.contains("java.io.File.renameTo"))
			return 9.518707035620796;
		if (a.contains("java.io.File.delete"))
			return 9.496270911389157;
		if (a.contains("java.io.File.listFiles"))
			return 8.583729715216482;
		if (a.contains("java.io.File.mkdirs"))
			return 6.71295620067707;

		// 网络
		if (a.contains("java.net.Socket.getInputStream"))
			return 5;
		if (a.contains("java.net.Socket.getOutputStream"))
			return 5;
		if (a.contains("java.net.URL.openConnection"))
			return 8;
		if (a.contains("org.apache.http.client.HttpClient.execute"))
			return 8;
		if (a.contains("java.net.HttpURLConnection.<init>"))
			return 9.055205875026193;
		if (a.contains("java.net.URL.openConnection"))
			return 8.641709066113805;
		if (a.contains("java.net.Socket.<init>"))
			return 8.843759381917984;
		if (a.contains("java.net.Socket.close"))
			return 8.634086942887738;
		if (a.contains("java.net.Socket.getInputStream"))
			return 8.196436811235028;
		if (a.contains("java.net.Socket.getOutputStream"))
			return 8.19229373114764;
		if (a.contains("java.net.Socket.connect"))
			return 6.295266001439646;
		if (a.contains("java.net.ServerSocket.bind"))
			return 2.833213344056216;
		if (a.contains("javax.net.ssl.HttpsURLConnection.getDefaultSSLSocketFactory"))
			return 8.534640105019959;
		if (a.contains("org.apache.http.impl.client.AbstractHttpClient.getCookieStore"))
			return 7.838737559599282;
		if (a.contains("org.apache.http.impl.client.AbstractHttpClient.execute"))
			return 7.744569809354496;
		if (a.contains("java.net.URL.getProtocol"))
			return 10.640029415415263;
		if (a.contains("java.net.URLConnection.getURL"))
			return 10.145923797230747;
		if (a.contains("java.net.URL.getFile"))
			return 9.96777604241849;

		if (a.contains("java.lang.Thread.<init>"))
			return 8.669399124305569;
		if (a.contains("java.lang.Thread.run"))
			return 7.856319571406588;

		if (a.contains("android.content.ContentResolver.query"))
			return 8.305731144875866;

		// 本手机信息
		if (a.contains("android.app.ActivityManager.getRunningTasks"))
			return 7.6953031349635665;
		if (a.contains("android.content.ContextWrapper.getApplicationContext"))
			return 7.66105638236183;
		if (a.contains("android.telephony.TelephonyManager.getDeviceId"))
			return 7.48605261786314;
		if (a.contains("android.accounts.AccountManager.getPassword"))
			return 6.371611847231857;
		if (a.contains("android.telephony.TelephonyManager.getLine1Number"))
			return 5.811140992976701;

		// 相机、传感器
		if (a.contains("android.hardware.SensorManager.getDefaultSensor"))
			return 7;
		if (a.contains("android.media.AudioRecord.read"))
			return 7.6783263565068856;
		if (a.contains("android.media.MediaRecorder.setOutputFile"))
			return 1.6094379124341003;
		if (a.contains("android.media.MediaRecorder.start"))
			return 1.6094379124341003;
		if (a.contains("android.media.MediaRecorder.prepare"))
			return 1.6094379124341003;
		if (a.contains("android.hardware.Camera.release"))
			return 5.1647859739235145;
		if (a.contains("android.hardware.Camera.open"))
			return 4.59511985013459;

		// 位置信息
		if (a.contains("android.location.Location.getLongitude"))
			return 7.615298339825815;
		if (a.contains("android.location.Location.getAltitude"))
			return 7.165493475060845;
		if (a.contains("android.location.Location.getLatitude"))
			return 7.122866658599083;
		if (a.contains("android.location.LocationManager.getLastKnownLocation"))
			return 6.949856455000773;
		if (a.contains("android.location.Location.setLongitude"))
			return 6.635946555686647;

		// 短信电话
		if (a.contains("com.android.internal.telephony.Itelephony$Stub$Proxy.call"))
			return 2;
		if (a.contains("java.security.MessageDigest.getInstance"))
			return 7.537962659768208;
		if (a.contains("android.telephony.SmsManager.sendTextMessage"))
			return 3.1354942159291497;
		if (a.contains("android.telephony.SmsMessage.getMessageBody"))
			return 1.0986122886681098;
		if (a.contains("android.telephony.TelephonyManager.getNetworkCountryIso"))
			return 7.534228326274089;

		if (a.contains("android.bluetooth.BluetoothAdapter.enable"))
			return 0.6931471805599453;

		/*
		 * if (a.contains(".*android.app.Activity.*getSystemService")) return
		 * 10.95110474796434; if
		 * (a.contains(".*android.view.ContextThemeWrapper.*getSystemService"))
		 * return 10.915633770053805; if
		 * (a.contains(".*android.content.ContextWrapper.*startService")) return
		 * 8.102283624480073; if
		 * (a.contains(".*android.content.ContextWrapper.*sendBroadcast"))
		 * return 7.848933726364071; if
		 * (a.contains(".*android.content.ContextWrapper.*bindService")) return
		 * 7.242082359256962; if
		 * (a.contains(".*android.content.ContextWrapper.*sendOrderedBroadcast"
		 * )) return 5.262690188904886; if
		 * (a.contains(".*android.app.ActivityManager.*getRunningServices"))
		 * return 7.027314514039777; if
		 * (a.contains(".*android.app.Activity.*startActivity")) return
		 * 7.027314514039777;
		 * 
		 * // content数据库操作 if
		 * (a.contains(".*android.content.ContentResolver.*query")) return 12;
		 * if (a.contains(".*android.content.ContentResolver.*delate")) return
		 * 7; if (a.contains(".*android.content.ContentResolver.*insert"))
		 * return 7; if
		 * (a.contains(".*android.content.ContentResolver.*update")) return 7;
		 * if (a.contains(".*android.database.sqlite.SQLiteDatabase.*insert"))
		 * return 10.887754639419377; if
		 * (a.contains(".*android.database.sqlite.SQLiteCursor.*getDatabase"))
		 * return 10.579361404425617; if
		 * (a.contains(".*android.database.sqlite.SQLiteCursor.*onMove")) return
		 * 10.308685958415747; if
		 * (a.contains(".*android.database.sqlite.SQLiteDatabase.*delete"))
		 * return 9.974970654157993; if
		 * (a.contains(".*android.database.sqlite.SQLiteDatabase.*query"))
		 * return 9.699288417222856; if
		 * (a.contains(".*android.database.sqlite.SQLiteDatabase.*update"))
		 * return 8.895355616996392;
		 * 
		 * if (a.contains(".*android.content.Intent.*setAction")) return
		 * 9.925444719888171; if
		 * (a.contains(".*android.content.Intent.*getExtras")) return
		 * 8.138272638530186; if
		 * (a.contains(".*android.content.Intent.*getData")) return
		 * 8.008698182988528; if
		 * (a.contains(".*android.content.Intent.*putExtra")) return
		 * 5.187385805840755;
		 * 
		 * // 手机文件 if (a.contains(".*java.io.FileInputStream.*read")) return 10;
		 * if (a.contains(".*java.io.File.*renameTo")) return 9.518707035620796;
		 * if (a.contains(".*java.io.File.*delete")) return 9.496270911389157;
		 * if (a.contains(".*java.io.File.*listFiles")) return
		 * 8.583729715216482; if (a.contains(".*java.io.File.*mkdirs")) return
		 * 6.71295620067707;
		 * 
		 * // 网络 if (a.contains(".*java.net.Socket.*getInputStream")) return 5;
		 * if (a.contains(".*java.net.Socket.*getOutputStream")) return 5; if
		 * (a.contains(".*java.net.URL.*openConnection")) return 8; if
		 * (a.contains(".*org.apache.http.client.HttpClient.*execute")) return
		 * 8; if (a.contains("java.net.HttpURLConnection.<init>")) return
		 * 9.055205875026193; if (a.contains(".*java.net.URL.*openConnection"))
		 * return 8.641709066113805; if (a.contains("java.net.Socket.<init>"))
		 * return 8.843759381917984; if (a.contains(".*java.net.Socket.*close"))
		 * return 8.634086942887738; if
		 * (a.contains(".*java.net.Socket.*getInputStream")) return
		 * 8.196436811235028; if
		 * (a.contains(".*java.net.Socket.*getOutputStream")) return
		 * 8.19229373114764; if (a.contains(".*java.net.Socket.*connect"))
		 * return 6.295266001439646; if
		 * (a.contains(".*java.net.ServerSocket.*bind")) return
		 * 2.833213344056216; if (a.contains(
		 * ".*javax.net.ssl.HttpsURLConnection.*getDefaultSSLSocketFactory"))
		 * return 8.534640105019959; if (a.contains(
		 * ".*org.apache.http.impl.client.AbstractHttpClient.*getCookieStore"))
		 * return 7.838737559599282; if
		 * (a.contains(".*org.apache.http.impl.client.AbstractHttpClient.*execute"
		 * )) return 7.744569809354496; if
		 * (a.contains(".*java.net.URL.*getProtocol")) return
		 * 10.640029415415263; if
		 * (a.contains(".*java.net.URLConnection.*getURL")) return
		 * 10.145923797230747; if (a.contains(".*java.net.URL.*getFile")) return
		 * 9.96777604241849;
		 * 
		 * if (a.contains("java.lang.Thread.<init>")) return 8.669399124305569;
		 * if (a.contains(".*java.lang.Thread.*run")) return 7.856319571406588;
		 * 
		 * if (a.contains(".*android.content.ContentResolver.*query")) return
		 * 8.305731144875866;
		 * 
		 * // 本手机信息 if
		 * (a.contains(".*android.app.ActivityManager.*getRunningTasks")) return
		 * 7.6953031349635665; if
		 * (a.contains(".*android.content.ContextWrapper.*getApplicationContext"
		 * )) return 7.66105638236183; if
		 * (a.contains(".*android.telephony.TelephonyManager.*getDeviceId"))
		 * return 7.48605261786314; if
		 * (a.contains(".*android.accounts.AccountManager.*getPassword")) return
		 * 6.371611847231857; if
		 * (a.contains("android.telephony.TelephonyManager.getLine1Number"))
		 * return 5.811140992976701;
		 * 
		 * // 相机、传感器 if
		 * (a.contains(".*android.hardware.SensorManager.*getDefaultSensor"))
		 * return 7; if (a.contains(".*android.media.AudioRecord.*read")) return
		 * 7.6783263565068856; if
		 * (a.contains(".*android.media.MediaRecorder.*setOutputFile")) return
		 * 1.6094379124341003; if
		 * (a.contains(".*android.media.MediaRecorder.*start")) return
		 * 1.6094379124341003; if
		 * (a.contains(".*android.media.MediaRecorder.*prepare")) return
		 * 1.6094379124341003; if
		 * (a.contains(".*android.hardware.Camera.*release")) return
		 * 5.1647859739235145; if
		 * (a.contains(".*android.hardware.Camera.*open")) return
		 * 4.59511985013459;
		 * 
		 * // 位置信息 if (a.contains(".*android.location.Location.*getLongitude"))
		 * return 7.615298339825815; if
		 * (a.contains(".*android.location.Location.*getAltitude")) return
		 * 7.165493475060845; if
		 * (a.contains(".*android.location.Location.*getLatitude")) return
		 * 7.122866658599083; if
		 * (a.contains(".*android.location.LocationManager.*getLastKnownLocation"
		 * )) return 6.949856455000773; if
		 * (a.contains(".*android.location.Location.*setLongitude")) return
		 * 6.635946555686647;
		 * 
		 * // 短信电话 if (a.contains(
		 * ".*com.android.internal.telephony.Itelephony$Stub$Proxy.*call"))
		 * return 2; if
		 * (a.contains(".*java.security.MessageDigest.*getInstance")) return
		 * 7.537962659768208; if
		 * (a.contains(".*android.telephony.SmsManager.*sendTextMessage"))
		 * return 3.1354942159291497; if
		 * (a.contains(".*android.telephony.SmsMessage.*getMessageBody")) return
		 * 1.0986122886681098; if
		 * (a.contains(".*android.telephony.TelephonyManager.*getNetworkCountryIso"
		 * )) return 7.534228326274089;
		 * 
		 * if (a.contains(".*android.bluetooth.BluetoothAdapter.*enable"))
		 * return 0.6931471805599453;
		 */

		return 0;

	}
}
