package com.ind.sap.service.utils;

import org.apache.commons.lang.StringUtils;

import com.google.gson.JsonParseException;
import com.google.gson.JsonParser;

import net.sf.json.JSONObject;

public class Utils {
	public static void addJsonCode(JSONObject jo, String key, int code) {
		jo.put(key, code);
	}

	public static boolean isGoodJson(String json) {
		boolean flag;
		if (StringUtils.isBlank(json)) {
			flag = false;
		}
		try {
			new JsonParser().parse(json);
			flag = true;
		} catch (JsonParseException e) {
			flag = false;
		}

		return flag;
	}
}
