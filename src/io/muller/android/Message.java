package io.muller.android;

import java.util.ArrayList;

import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;

public abstract class Message {
	private String _id;
	private String thread_id;
	private String address;
	private String person;
	private String date;
	private String body;
	private String type;
	private String ct_t;
	
	public String getID() {
		return _id;
	}

	public void setID(String _id) {
		this._id = _id;
	}

	public String getThreadId() {
		return thread_id;
	}

	public void setThreadId(String thread_id) {
		this.thread_id = thread_id;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getPerson() {
		return person;
	}

	public void setPerson(String person) {
		this.person = person;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getCt_t() {
		return ct_t;
	}

	public void setCt_t(String ct_t) {
		this.ct_t = ct_t;
	}
	
	public static ArrayList<Message> getConversations(ContentResolver cr){
		ArrayList<Message> msgs = new ArrayList<Message>();
		Uri inboxQuery = Uri.parse("content://mms-sms/conversations");
		Cursor convoCursor = cr.query(
				inboxQuery,
				new String[] { 
						"_id", 
						"thread_id",
						"address", 
						"person", 
						"date",
						"body", 
						"type", 
						"ct_t" }, 
				null, null, "date desc");
		if(convoCursor.moveToFirst()){
			do {
				String _id = convoCursor.getString(convoCursor.getColumnIndex("_id"));
				String thread_id = convoCursor.getString(convoCursor.getColumnIndex("thread_id"));
				String address = convoCursor.getString(convoCursor.getColumnIndex("address"));
				String person = convoCursor.getString(convoCursor.getColumnIndex("person"));
				String date = convoCursor.getString(convoCursor.getColumnIndex("date"));
				String body = convoCursor.getString(convoCursor.getColumnIndex("body"));
				String type = convoCursor.getString(convoCursor.getColumnIndex("type"));
				String ct_t = convoCursor.getString(convoCursor.getColumnIndex("ct_t"));
				msgs.add(new Generic(
						_id,
						thread_id,
						address,
						person,
						date,
						body,
						type,
						ct_t
						));
			} while (convoCursor.moveToNext());
		}
		return msgs;
	}

}

class Generic extends Message{
	Generic(
			String _id,
			String thread_id,
			String address,
			String person,
			String date,
			String body,
			String type,
			String ct_t
			){
		this.setID(_id);
		this.setThreadId(thread_id);
		this.setAddress(address);
		this.setPerson(person);
		this.setDate(date);
		this.setBody(body);
		this.setType(type);
		this.setCt_t(ct_t);
	}
}
