package com.main;

import android.app.ListActivity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CursorAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

public class EmployeeListActivity extends ListActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		Cursor cursor = DataBaseHelper.getInstance(this).getEmployees();
		CursorAdapter adapter = new SimpleCursorAdapter(this,
				R.layout.employee_item, cursor, new String[] { "name",
						"lastname" }, new int[] { R.id.txvName,
						R.id.txvLastName });
		setListAdapter(adapter);
		registerForContextMenu(getListView());
		
	}

	@Override
	public void onCreateContextMenu(ContextMenu menu, View v,
			ContextMenuInfo menuInfo) {
		super.onCreateContextMenu(menu, v, menuInfo);
		menu.add(0, 0, 0, "Borrar");
	}

	@Override
	public boolean onContextItemSelected(MenuItem item) {
		super.onContextItemSelected(item);

		if (item.getItemId() == 0) {
			AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item
					.getMenuInfo();
			Cursor employee = (Cursor) getListAdapter().getItem(info.position);
			if (employee != null && employee.moveToPosition(info.position)) {
				int idIndex = employee.getColumnIndex("_id");
				String idvalue = employee.getString(idIndex);
				DataBaseHelper.getInstance(this).deleteEmployee(idvalue);
				Cursor cursor = DataBaseHelper.getInstance(this).getEmployees();
				CursorAdapter adapter = new SimpleCursorAdapter(this,
						R.layout.employee_item, cursor, new String[] { "_id",
								"name", "lastname" }, new int[] { R.id.txvId,
								R.id.txvName, R.id.txvLastName });
				setListAdapter(adapter);

			}
		}
		return true;
	}

	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		super.onListItemClick(l, v, position, id);

		Cursor employee = (Cursor) getListAdapter().getItem(position);
		if (employee != null && employee.moveToPosition(position)) {
			int idIndex = employee.getColumnIndex("_id");
			String idvalue = employee.getString(idIndex);
			callUpdateContact(idvalue);
		}
	}

	private void callUpdateContact(String id) {
		Intent intent = new Intent(this, AndroidDataBaseActivity.class);
		intent.putExtra("_id", id);
		intent.setAction(DataBaseHelper.MODIFY_CONTACT);
		startActivity(intent);
	}

}
