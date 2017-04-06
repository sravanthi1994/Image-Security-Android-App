package com.newthinktank.password;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

public class Initial extends Activity {

	Button one;
	Button two;
	public static String s="win";
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_initial);
		Toast.makeText(Initial.this,Signup.check,Toast.LENGTH_SHORT).show();
		one = (Button) findViewById(R.id.gal);
		two = (Button) findViewById(R.id.cam);
	}

	public void calgal(View v)
	{
		Intent intent = new Intent(this, Encrypt.class);
		startActivity(intent);
	}
	
	public void calcam(View v)
	{
		Intent intent = new Intent(this, Camera.class);
		startActivity(intent);
	}
	
	
}
