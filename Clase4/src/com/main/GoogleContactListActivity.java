package com.main;

import android.app.Activity;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.Toast;

import com.list.contacts.Contact;
import com.list.contacts.ContactAdapter;
import com.list.contacts.ContactManager;

public class GoogleContactListActivity extends Activity {

	private GridView list;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.main);

		list = (GridView) findViewById(R.id.contacts);

		ContactAdapter adapter = new ContactAdapter(this, ContactManager
				.getInstance().getAllContacts(this));

		list.setAdapter(adapter);

		list.setOnItemClickListener(new OnItemClickListener() {

			public void onItemClick(AdapterView list, View view, int position,
					long id) {
				String name = ((ContactAdapter) list.getAdapter()).getItem(
						position).getName();
				Toast.makeText(GoogleContactListActivity.this,
						"Calling Contact " + name, Toast.LENGTH_LONG).show();

			}
		});

		// para que la lista soporte el context menu
		registerForContextMenu(list);
	}

	@Override
	public void onCreateContextMenu(ContextMenu menu, View v,
			ContextMenuInfo menuInfo) {
		super.onCreateContextMenu(menu, v, menuInfo);

		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.call_menu, menu);
	}

	@Override
	public boolean onContextItemSelected(MenuItem item) {
		super.onContextItemSelected(item);
		AdapterContextMenuInfo menuInfo = (AdapterContextMenuInfo) item
				.getMenuInfo();

		switch (item.getItemId()) {
		case R.id.call:

			Contact contact = (Contact) list.getAdapter().getItem(
					menuInfo.position);
			String name = contact.getName();
			Toast.makeText(GoogleContactListActivity.this,
					"Calling Contact " + name, Toast.LENGTH_LONG).show();
			break;
		}
		return true;
	}

}
