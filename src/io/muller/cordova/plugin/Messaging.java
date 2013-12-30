package io.muller.cordova.plugin;

import io.muller.android.Contact;
import io.muller.android.Message;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import org.apache.cordova.CallbackContext;
import org.apache.cordova.CordovaPlugin;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;
import android.util.Log;

public class Messaging extends CordovaPlugin{
	private final String TAG = "MessagingPlugin";
	private final String GET_CONVERSATIONS_METHOD = "getConversations";
	private final String GET_CONVERSATION_METHOD = "getConversation";
	private final String GET_MESSAGES_METHOD = "getMessages";
	private final String SEND_MESSAGE_METHOD = "sendMessage";
	
	public boolean execute(String action, JSONArray args, final CallbackContext callbackContext) throws JSONException {
		if(action.equals(GET_CONVERSATIONS_METHOD)){
			// Messaging.getConversations(cb)
			getConversations(callbackContext);
			return true;
		}
		return false;
	}
	
	private void getConversations(final CallbackContext callbackContext) throws JSONException{
		ContentResolver cr = cordova.getActivity().getContentResolver();
		ArrayList<Message> msgs = Message.getConversations(cr);
		JSONArray arr = new JSONArray();
		//Contact.logAll(cr);
		for(Message m:msgs){
			String personID = m.getPerson();
			Log.d(TAG, "personID = "+(personID==null? "NULL":personID));
			Contact ct = Contact.getByID(m.getPerson(), cr);
			JSONObject o = new JSONObject();
			Log.d(TAG, m.getPerson()+": "+m.getBody()+" ("+m.getDate()+")");
			String name = m.getAddress();
			if(ct!=null){
				name = ct.getDisplayName();
			}
			Log.d(TAG, "name = "+name);
			o.put("_id",m.getID());
			o.put("thread_id", m.getThreadId());
			o.put("address", m.getAddress());
			o.put("person", name);
			o.put("person_id", personID);
			o.put("date", m.getDate());
			o.put("body", m.getBody());
			o.put("type", m.getType());
			arr.put(o);
		}
		callbackContext.success(arr);
	}
	
	private void getMessagesClunky(final CallbackContext callbackContext){
		Uri inboxQueryUri = Uri.parse("content://mms-sms/conversations");
		ContentResolver contentResolver = this.cordova.getActivity().getContentResolver();
		Cursor query = contentResolver
				.query(inboxQueryUri,new String[] { "_id", "thread_id", "address", "person", "date","body", "type", "ct_t" }, null, null, null);
		if (query.moveToFirst()) {
		    do {
		        String string = query.getString(query.getColumnIndex("ct_t"));
		        if ("application/vnd.wap.multipart.related".equals(string)) {
		            // it's MMS
		        	String mmsId = query.getString(query.getColumnIndex("_id"));
		        	Uri uri = Uri.parse("content://mms/");
		        	String selection = "_id = " + mmsId;
		        	Cursor cursor = contentResolver.query(uri, null, selection, null, null);
		        	String selectionPart = "mid=" + mmsId;
		        	uri = Uri.parse("content://mms/part");
		        	cursor = contentResolver.query(uri, null, selectionPart, null, null);
		        	if (cursor.moveToFirst()) {
		        	    do {
		        	        String partId = cursor.getString(cursor.getColumnIndex("_id"));
		        	        String type = cursor.getString(cursor.getColumnIndex("ct"));
		        	        if ("text/plain".equals(type)) {
		        	            String data = cursor.getString(cursor.getColumnIndex("_data"));
		        	            String body;
		        	            if (data != null) {
		        	                // implementation of this method below
		        	                body = getMmsText(partId, contentResolver);
		        	            } else {
		        	                body = cursor.getString(cursor.getColumnIndex("text"));
		        	            }
		        	        }
		        	    } while (cursor.moveToNext());
		        	}
		        } else {
		        	// it's SMS
		        	String smsId = query.getString(query.getColumnIndex("_id"));
		        	String selection = "_id = "+smsId;
		        	Uri uri = Uri.parse("content://sms");
		        	Cursor cursor = contentResolver.query(uri, null, selection, null, null);
		        	String phone = cursor.getString(cursor.getColumnIndex("address"));
		        	int type = cursor.getInt(cursor.getColumnIndex("type"));// 2 = sent, etc.
		        	String date = cursor.getString(cursor.getColumnIndex("date"));
		        	String body = cursor.getString(cursor.getColumnIndex("body"));
		        }
		    } while (query.moveToNext());
		}
	}
	
	private String getMmsText(String id, ContentResolver contentResolver) {
	    Uri partURI = Uri.parse("content://mms/part/" + id);
	    InputStream is = null;
	    StringBuilder sb = new StringBuilder();
	    try {
	        is = contentResolver.openInputStream(partURI);
	        if (is != null) {
	            InputStreamReader isr = new InputStreamReader(is, "UTF-8");
	            BufferedReader reader = new BufferedReader(isr);
	            String temp = reader.readLine();
	            while (temp != null) {
	                sb.append(temp);
	                temp = reader.readLine();
	            }
	        }
	    } catch (IOException e) {}
	    finally {
	        if (is != null) {
	            try {
	                is.close();
	            } catch (IOException e) {}
	        }
	    }
	    return sb.toString();
	}
}
