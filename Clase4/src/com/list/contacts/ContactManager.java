package com.list.contacts;

import java.util.ArrayList;
import java.util.Collections;

import android.app.Activity;
import android.content.ContentResolver;
import android.database.Cursor;
import android.provider.Contacts;
import android.provider.Contacts.People;


public class ContactManager {

	private static ContactManager instance = new ContactManager();
	
	private ArrayList<Contact> contactsArray = new ArrayList<Contact>();

	private ContactManager() {

	}

	public static ContactManager getInstance() {
		return instance;
	}

	public ArrayList<Contact> getAllContacts(Activity activity) {
		contactsArray.clear();
		ContentResolver cr = activity.getContentResolver();
        Cursor cur = cr.query(People.CONTENT_URI, 
			null, null, null, null);
        if (cur.getCount() > 0) {
	     while (cur.moveToNext()) {
	         String id = cur.getString(cur.getColumnIndex(People._ID));
	         String name = cur.getString(cur.getColumnIndex(People.DISPLAY_NAME));
	         String number = getPhone(cur, cr, id);
	         for(int i=0;i<50;i++){
	        	 Contact contact = new Contact(id,name,number);
	  			 contactsArray.add(contact);
	         }      
	     }
        }
		return contactsArray;
	}
	
	
	public String getPhone(Cursor cur, ContentResolver cr, String id){
			Cursor pCur = cr.query(
					Contacts.Phones.CONTENT_URI, 
					null, 
					Contacts.Phones.PERSON_ID +" = ?", 
					new String[]{id}, null);
			int i=0;
			int pCount = pCur.getCount();
			String numbers = "";
			String[] phoneNum = new String[pCount];
			String[] phoneType = new String[pCount];
			while (pCur.moveToNext()) {
				
				phoneNum[i] = pCur.getString(
	                               pCur.getColumnIndex(Contacts.Phones.NUMBER));
				
				numbers =  phoneNum[i] + " ";
				
				phoneType[i] = pCur.getString(
	                               pCur.getColumnIndex(Contacts.Phones.TYPE));
				i++;
			} 
			
			return numbers;
	}
	
}
