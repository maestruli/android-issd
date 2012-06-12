package com.example.contacts;

import java.util.ArrayList;

import android.app.Activity;
import android.content.ContentResolver;
import android.database.Cursor;
import android.os.AsyncTask;
import android.provider.Contacts;
import android.provider.Contacts.People;

public class ContactManager {

	private static ContactManager instance = new ContactManager();

	private ArrayList<Contact> contactsArray = new ArrayList<Contact>();

	private AsyncTask<Void, Contact, Void> tasks = null;

	private ContactListener contactListener;

	private ContactManager() {

	}

	public static ContactManager getInstance() {
		return instance;
	}

	public ArrayList<Contact> getAllContacts(final Activity activity,
			final ContactListener listener) {
		contactsArray.clear();

		// Thread thread = new Thread() {
		//
		// public void run() {
		//
		// ContentResolver cr = activity.getContentResolver();
		// Cursor cur = cr.query(People.CONTENT_URI, null, null, null,
		// null);
		// if (cur.getCount() > 0) {
		// while (cur.moveToNext()) {
		// String id = cur.getString(cur
		// .getColumnIndex(People._ID));
		// String name = cur.getString(cur
		// .getColumnIndex(People.DISPLAY_NAME));
		// String number = getPhone(cur, cr, id);
		// for (int i = 0; i < 10; i++) {
		// Contact contact = new Contact(id, name, number);
		// contactsArray.add(contact);
		// // percentage = (count*100/cur.getCount());
		// listener.onContactAdded(contact, i);
		// }
		// }
		// }
		//
		// };
		//
		// };
		//
		// thread.start();

		this.contactListener = listener; // Creemos la variable contactListener
		if (tasks == null) {
			tasks = new AsyncTask<Void, Contact, Void>() {

				private int percentage;

				protected Void doInBackground(Void[] params) {
					contactsArray.clear();
					final ContentResolver cr = activity.getContentResolver();
					Cursor cur = cr.query(People.CONTENT_URI, null, null, null,
							null);
					if (cur.getCount() > 0) {
						int count = 0;
						while (cur.moveToNext()) {
							count++;
							String id = cur.getString(cur
									.getColumnIndex(People._ID));
							String name = cur.getString(cur
									.getColumnIndex(People.DISPLAY_NAME));
							String number = getPhone(cur, cr, id);
							for (int i = 0; i < 10; i++) {
								Contact contact = new Contact(id, name, number);
								percentage = (count * 100 / cur.getCount());
								contactsArray.add(contact);
								publishProgress(contact);
							}
						}
					}
					return null;
				}

				protected void onPostExecute(Void result) {
					tasks = null;
					contactListener.contactsAdded();
				};

				@Override
				protected void onProgressUpdate(Contact... values) {
					super.onProgressUpdate(values);
					if (values != null && values.length > 0) {
						contactListener.onContactAdded(values[0], percentage);
					}
				}
			};
			tasks.execute();
		}

		return contactsArray;
	}

	public String getPhone(Cursor cur, ContentResolver cr, String id) {
		Cursor pCur = cr.query(Contacts.Phones.CONTENT_URI, null,
				Contacts.Phones.PERSON_ID + " = ?", new String[] { id }, null);
		int i = 0;
		int pCount = pCur.getCount();
		String numbers = "";
		String[] phoneNum = new String[pCount];
		String[] phoneType = new String[pCount];
		while (pCur.moveToNext()) {

			phoneNum[i] = pCur.getString(pCur
					.getColumnIndex(Contacts.Phones.NUMBER));

			numbers = phoneNum[i] + " ";

			phoneType[i] = pCur.getString(pCur
					.getColumnIndex(Contacts.Phones.TYPE));
			i++;
		}

		return numbers;
	}

	public void removeListener() {
		contactListener = null;
	}

}
