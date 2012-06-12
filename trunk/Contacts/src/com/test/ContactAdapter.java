package com.test;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.contacts.Contact;

public class ContactAdapter extends BaseAdapter {

	private ArrayList<Contact> list;
	private LayoutInflater inflater;

	public ContactAdapter(Context context, ArrayList<Contact> allContacts) {
		list = allContacts;
		inflater = LayoutInflater.from(context);

	}

	public void contactAdded() {
		notifyDataSetChanged();
	}

	@Override
	public int getCount() {
		return list.size();
	}

	@Override
	public Contact getItem(int position) {

		return list.get(position);
	}

	@Override
	public long getItemId(int position) {
		return 0;
	}

	class Holder {
		TextView nombre;
		TextView numero;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View view = convertView;

		if (view == null) {
			view = inflater.inflate(R.layout.contact_item, null);
		}

		Holder holder = (Holder) view.getTag();
		if (holder == null) {
			holder = new Holder();
			holder.nombre = (TextView) view.findViewById(R.id.txvName);
			holder.numero = (TextView) view.findViewById(R.id.txvNumber);
		}

		Contact contact = getItem(position);

		holder.nombre.setText(contact.getName());
		holder.numero.setText(contact.getNumber());

		view.setTag(holder);

		return view;
	}

}
