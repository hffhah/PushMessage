package com.zihao.websocketlogin.utils;

import android.app.ActivityManager;
import android.app.ActivityManager.RunningServiceInfo;
import android.content.Context;
import android.telephony.TelephonyManager;

public class Utils {
	public static String getDeviceId(Context context) {
		TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
		return tm.getDeviceId();
	}
	
	public static boolean isServiceRunning(Context context,String servicePackage) {  
	    ActivityManager manager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);  
	    for (RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {  
	        if (servicePackage.equals(service.service.getClassName())) {  
	            return true;  
	        }  
	    }  
	    return false;  
	} 
}
