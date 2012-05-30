package com.main;

import android.app.Activity;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class Clase03Activity extends Activity {
	/** Called when the activity is first created. */
	private ImageView imgLogo;
	private TextView textView;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		imgLogo = (ImageView) findViewById(R.id.imgLogo);

		Resources res = getResources();

		Drawable drawable = res.getDrawable(R.drawable.logo);

		imgLogo.setImageDrawable(drawable);

		String title = getString(R.string.title);

		textView = (TextView) findViewById(R.id.titleView);

		textView.setText(title);

		// textView.setTextColor(res.getColor(R.color.title));

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.user_input_menu, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		boolean result = super.onOptionsItemSelected(item);
		switch (item.getItemId()) {
		case R.id.menuDone:
			Toast.makeText(this, "The Contact has been saved",
					Toast.LENGTH_LONG).show();
			break;
		case R.id.menuCancel:
			Toast.makeText(this, "Ni Bostaaaa!",
					Toast.LENGTH_LONG).show();
			break;
		}
		
		return result;
	}
}