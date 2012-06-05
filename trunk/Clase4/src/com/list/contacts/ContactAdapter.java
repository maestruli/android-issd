package com.list.contacts;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.main.R;

public class ContactAdapter extends BaseAdapter {

	private LayoutInflater inflater;
	private ArrayList<Contact> list;

	public ContactAdapter(Context context, ArrayList<Contact> contacts) {
		list = contacts;
		inflater = LayoutInflater.from(context);
	}

	public int getCount() {
		return list.size();
	}

	public Contact getItem(int arg0) {
		return list.get(arg0);
	}

	public long getItemId(int arg0) {
		// en este caso no nos hace falta
		return 0;
	}

	public View getView(int position, View convertView, ViewGroup arg2) {

		// se reutiliza el convertView, android te pasa la view anterior para
		// reutilizarla
		// porque el inflate es muy costoso en consumo de recursos
		View view = convertView;

		if (view == null) {
			view = inflater.inflate(R.layout.contact_item, null);
		}

		HolderContact holder = (HolderContact) view.getTag();
		// tambien para mejorar el rendimiento
		if(holder == null){
			holder = new HolderContact();
			
			holder.name = (TextView) view.findViewById(R.id.txvName);
			holder.number = (TextView) view.findViewById(R.id.txvNumber);
			
		}

		Contact contact = getItem(position);
		holder.name.setText(contact.getName());
		holder.number.setText(contact.getNumber());

		view.setTag(holder);

		return view;
	}

	class HolderContact {
		TextView name;
		TextView number;
	}

}
