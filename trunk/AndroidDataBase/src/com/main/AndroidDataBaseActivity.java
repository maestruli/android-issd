package com.main;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class AndroidDataBaseActivity extends Activity implements
		OnClickListener {
	private EditText nameText;
	private EditText ageText;
	private EditText lastNameText;
	private EditText profesionText;
	private Button save;
	private String id;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		nameText = (EditText) findViewById(R.id.nameText);
		ageText = (EditText) findViewById(R.id.ageText);
		lastNameText = (EditText) findViewById(R.id.lastNameText);
		profesionText = (EditText) findViewById(R.id.profesionText);

		save = (Button) findViewById(R.id.save);
		save.setOnClickListener(this);

		Intent intent = getIntent();
		if (intent != null) {
			String action = intent.getAction();
			if (action != null && action.equals(DataBaseHelper.MODIFY_CONTACT)) {
				save.setText("Actualizar");
				id = intent.getStringExtra("_id");
				Cursor cursor = DataBaseHelper.getInstance(this)
						.getEmployee(id);
				int nameId = cursor.getColumnIndex("name");
				int lastnameId = cursor.getColumnIndex("lastname");
				int ageId = cursor.getColumnIndex("age");
				int professionId = cursor.getColumnIndex("profession");
				if (cursor.moveToFirst()) {
					String name = cursor.getString(nameId);
					String lastname = cursor.getString(lastnameId);
					String age = cursor.getString(ageId);
					String profession = cursor.getString(professionId);
					nameText.setText(name);
					lastNameText.setText(lastname);
					ageText.setText(age);
					profesionText.setText(profession);
				}
			}
		}
	}

	public void onClick(View v) {
		String name = nameText.getText().toString();
		String age = ageText.getText().toString();
		String lastname = lastNameText.getText().toString();
		String profesion = profesionText.getText().toString();
		if (id == null) {
			DataBaseHelper.getInstance(this).insertAnEmployee(name, lastname,
					age, profesion);
		} else {
			DataBaseHelper.getInstance(this).updateEmployee(id, name, lastname,
					age, profesion);
		}
		callList();

	}

	private void callList() {
		Intent intent = new Intent(this, EmployeeListActivity.class);
		startActivity(intent);

	}

}