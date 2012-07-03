package com.main;

import android.app.Activity;
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
	}

	public void onClick(View v) {
		String name = nameText.getText().toString();
		String age = ageText.getText().toString();
		String lastname = lastNameText.getText().toString();
		String profesion = profesionText.getText().toString();
		DataBaseHelper.getInstance(this).insertAnEmployee(name, lastname, age,
				profesion);

	}

}