package io.muller.cordova.plugin;

import java.util.HashMap;
import java.util.Map;

import org.apache.cordova.CallbackContext;
import org.apache.cordova.CordovaPlugin;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Intent;
import android.util.Log;

public class Pebble extends CordovaPlugin {
	private final String TAG="PebblePlugin";
	private final String SEND_MESSAGE_METHOD = "sendMessage";
	private final String SET_APP_NAME_METHOD = "setAppName";
	private static String appName = "PebblePlugin";
	
	public boolean execute(String action, JSONArray args, final CallbackContext callbackContext) throws JSONException {
		if(action.equals(SEND_MESSAGE_METHOD)){
			// Pebble.sendMessage(title, body)
			final Intent i = new Intent("com.getpebble.action.SEND_NOTIFICATION");

		    final Map<String,String> data = new HashMap<String,String>();
		    String title = args.getString(0);
		    String body = args.getString(1);
		    data.put("title", title);
		    data.put("body", body);
		    final JSONObject jsonData = new JSONObject(data);
		    final String notificationData = new JSONArray().put(jsonData).toString();

		    i.putExtra("messageType", "PEBBLE_ALERT");
		    i.putExtra("sender", appName);
		    i.putExtra("notificationData", notificationData);

		    Log.d(TAG, "About to send a modal alert to Pebble: " + notificationData);
		    cordova.getActivity().sendBroadcast(i);
		    callbackContext.success();
		    return true;
		}else if(action.equals(SET_APP_NAME_METHOD)){
			// Pebble.setAppName(name)
			appName = args.getString(0);
			callbackContext.success();
			return true;
		}
		return false;
	}
	
}
