package com.test;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
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

import com.example.contacts.Contact;
import com.example.contacts.ContactListener;
import com.example.contacts.ContactManager;

public class GoogleContactActivity extends Activity implements ContactListener {

	private GridView list;
	private Handler mHandler;
	private ProgressDialog progressDialog;
	private static final int ADD_CONTACT = 0;
	private static final String CONTACT = "Contacts";
	public static final int LOADING_CONTACTS_DIALOG = 0;

	@Override
	protected Dialog onCreateDialog(int id) {
		Dialog dialog = null;
		super.onCreateDialog(id);
		switch (id) {
		case LOADING_CONTACTS_DIALOG:
			progressDialog = new ProgressDialog(this);
			progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
			progressDialog.setMessage("Loading Contacts...");
			progressDialog.setCancelable(false);
			dialog = progressDialog;
			break;
		}
		return dialog;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.main);

		list = (GridView) findViewById(R.id.contacts);
		ContactAdapter adapter = new ContactAdapter(this, ContactManager
				.getInstance().getAllContacts(this, this));
		list.setAdapter(adapter);

		list.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> list, View view,
					int position, long id) {
				String name = ((ContactAdapter) list.getAdapter()).getItem(
						position).getName();
				Toast.makeText(GoogleContactActivity.this,
						"Calling Contact " + name, Toast.LENGTH_LONG).show();

			}
		});

		registerForContextMenu(list);

		mHandler = new Handler() {

			@Override
			public void handleMessage(Message msg) {
				super.handleMessage(msg);
				switch (msg.what) {
				case ADD_CONTACT:
					Bundle data = msg.getData();
					Contact contact = (Contact) data.getSerializable(CONTACT);
					ContactAdapter adapter = (ContactAdapter) list.getAdapter();
					adapter.contactAdded();
					break;
				}

			}
		};
	}

	@Override
	public void onCreateContextMenu(ContextMenu menu, View v,
			ContextMenuInfo menuInfo) {
		super.onCreateContextMenu(menu, v, menuInfo);

		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.contact_list_menu, menu);
	}

	@Override
	public boolean onContextItemSelected(MenuItem item) {
		super.onContextItemSelected(item);

		AdapterContextMenuInfo menuInfo = (AdapterContextMenuInfo) item
				.getMenuInfo();
		switch (item.getItemId()) {
		case R.id.call:
			Contact contact = ((Contact) list.getAdapter().getItem(
					menuInfo.position));
			String name = contact.getName();
			Toast.makeText(GoogleContactActivity.this,
					"Calling Contact " + name, Toast.LENGTH_LONG).show();
			break;
		}
		return true;
	}

	@Override
	public void onContactAdded(Contact contact, int percentage) {

		// Message message = mHandler.obtainMessage(ADD_CONTACT);
		// Bundle bundle = new Bundle();
		// bundle.putSerializable(CONTACT, contact);
		// message.setData(bundle);
		// mHandler.sendMessage(message);

		ContactAdapter adapter = (ContactAdapter) list.getAdapter();
		adapter.contactAdded();

		if (progressDialog != null) {
			progressDialog.setProgress(percentage);
		}

	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		ContactManager.getInstance().removeListener();
	}

	@Override
	public void contactsAdded() {
		removeDialog(LOADING_CONTACTS_DIALOG);
	}

}
