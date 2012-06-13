package com.example.contacts;

public interface ContactListener {

	void onContactAdded(Contact contact, int percentage);
	
	void contactsAdded();
	
	void onContactFullyLoader();
}
