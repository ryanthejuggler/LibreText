package io.muller.android;

import android.content.ContentResolver;
import android.database.Cursor;
import android.provider.ContactsContract;
import android.util.Log;

public class Contact {
	private static final String TAG = "io.muller.android.Contact";
	private String _id;
	private String displayName;

	public Contact() {

	}

	public static Contact getByID(String personID, ContentResolver cr) {
		Log.d(TAG, ContactsContract.Contacts._ID+"="+(personID == null ? "NULL" : personID));
		if (personID == null) {
			return null;
		}
		Cursor cur = cr.query(ContactsContract.RawContacts.CONTENT_URI, 
				new String[]{
					ContactsContract.RawContacts._ID,
					ContactsContract.RawContacts.DISPLAY_NAME_PRIMARY,
					ContactsContract.RawContacts.CONTACT_ID
				},
				ContactsContract.RawContacts._ID+"=?", new String[]{personID}, null);
		if (cur.getCount() > 0) {
			while (cur.moveToNext()) {

				String name = cur
						.getString(cur
								.getColumnIndex(ContactsContract.RawContacts.DISPLAY_NAME_PRIMARY));
				String cid = cur
						.getString(cur
								.getColumnIndex(ContactsContract.RawContacts.CONTACT_ID));
				Cursor c2 = cr.query(ContactsContract.Contacts.CONTENT_URI, new String[]{
						ContactsContract.Contacts.DISPLAY_NAME
				}, ContactsContract.Contacts._ID+"=?", new String[]{cid}, null);
				if(c2.getCount()>0){
					c2.moveToFirst();
					name = c2.getString(c2.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
					Log.d(TAG, "merged: "+name);
				}else{
					Log.d(TAG, "raw: "+name);
				}
				/*if (Integer
						.parseInt(cur.getString(cur
								.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER))) > 0) {
					// Query phone here. Covered next
				}*/
				Contact c = new Contact();
				c.setID(personID);
				c.setDisplayName(name);
				return c;
			}
		}
		return null;
	}
	

	public static void logAll(ContentResolver cr) {
		Log.d(TAG, "logAll");
		Cursor cur = cr.query(ContactsContract.Contacts.CONTENT_URI, null,
		// ContactsContract.Contacts._ID+"=?", new String[]{personID}, null);
				null, null, null);
		if (cur.getCount() > 0) {
			while (cur.moveToNext()) {

				String _id = cur
						.getString(cur
								.getColumnIndex(ContactsContract.Contacts._ID));
				String name = cur
						.getString(cur
								.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
				if (Integer
						.parseInt(cur.getString(cur
								.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER))) > 0) {
					// Query phone here. Covered next
				}
				Log.d(TAG, _id+"/"+name);
			}
		}
	}

	public String getID() {
		return _id;
	}

	public void setID(String _id) {
		this._id = _id;
	}

	public String getDisplayName() {
		return displayName;
	}

	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}

}
