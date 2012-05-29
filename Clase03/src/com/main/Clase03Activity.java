package com.main;

import android.app.Activity;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

public class Clase03Activity extends Activity {
    /** Called when the activity is first created. */
	private ImageView imgLogo;
	private TextView textView;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        imgLogo = (ImageView)findViewById(R.id.imgLogo);
        
        Resources res = getResources();
        
        Drawable drawable = res.getDrawable(R.drawable.logo);
        
        imgLogo.setImageDrawable(drawable);
        
        
        
        String title = getString(R.string.title);
        
        textView = (TextView)findViewById(R.id.titleView);
        
        textView.setText(title);
        
        
    }
}