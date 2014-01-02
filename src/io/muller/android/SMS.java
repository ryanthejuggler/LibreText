package io.muller.android;

import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;

public class SMS extends Message {
	public String getSimplePreview(){
		return this.getBody();
	}
	public static SMS getByID(String id, ContentResolver cr){
	  SMS sms = new SMS();
	  Uri inboxQuery = Uri.parse("content://mms-sms/conversations");
	  Cursor cur = cr.query(inboxQuery, new String[] { "_id",
        "thread_id", "address", "person", "date", "body", "type", "ct_t" },
        "thread_id=?", new String[]{id}, "date desc");
	  if(cur.moveToFirst()){
      String _id = cur.getString(cur.getColumnIndex("_id"));
      String thread_id = cur.getString(cur
          .getColumnIndex("thread_id"));
      String address = cur.getString(cur
          .getColumnIndex("address"));
      String person = cur.getString(cur
          .getColumnIndex("person"));
      String date = cur.getString(cur.getColumnIndex("date"));
      String body = cur.getString(cur.getColumnIndex("body"));
      String type = cur.getString(cur.getColumnIndex("type"));
      String ct_t = cur.getString(cur.getColumnIndex("ct_t"));
      sms.setID(_id);
      sms.setThreadId(thread_id);
      sms.setAddress(address);
      sms.setPerson(person);
      sms.setDate(date);
      sms.setBody(body);
      sms.setType(type);
      sms.setCt_t(ct_t);
      return sms;
	  }
	  return null;
	}
}
