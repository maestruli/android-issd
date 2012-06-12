package com.test;


import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class ContactsActivity extends ListActivity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        
        String[] cpOptions = getResources().getStringArray(R.array.choice);
        
        ArrayAdapter adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, cpOptions);
                
        setListAdapter(adapter);
    }
    
    
    /**
     * Event called when an item is clicked
     */
    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        // From the list we get the adapter
        String choice = (String) l.getAdapter().getItem(position);
        if(choice.equals("Contacts")) {
            callGoogleContactList();
        }
    }


	private void callGoogleContactList() {
		Intent intent = new Intent(this,GoogleContactActivity.class);
		startActivity(intent);
		
	}
}