package com.picserver.utils;

import net.sf.json.JSONObject;

/**
 * json工具类
 * @author hadoop
 *
 */
public class JsonUtil {
	
	  public static String createJsonString(String key, Object value)
	    {
	        String jsonString = null;

	        JSONObject jsonObject = new JSONObject();
	        jsonObject.put(key, value);
	        jsonString = jsonObject.toString();

	        return jsonString;

	    }
}
