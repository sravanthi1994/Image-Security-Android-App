package com.newthinktank.password;

import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Signup extends Activity {

	public EditText spass=null;
	public EditText cpass=null;
	private Button signup;
	public static String check;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_signup);
		spass= (EditText)findViewById(R.id.spass);
		cpass= (EditText)findViewById(R.id.cpass);
		signup= (Button)findViewById(R.id.signup);
		final String PREFS_NAME = "MyPrefsFile";

		SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);

		if (settings.getBoolean("my_first_time", true)) {
		    //the app is being launched for first time, do something
			setFirsLaunchFlag();
		    Log.d("Comments", "First time");

		             // first time task

		    // record the fact that the app has been started at least once
		    settings.edit().putBoolean("my_first_time", false).commit(); 
		}
	
	}
	
	private void setFirsLaunchFlag(){
        SharedPreferences prefs = getSharedPreferences(
           "Preferences",
           Context.MODE_PRIVATE);
        SharedPreferences.Editor edit = prefs.edit();
        edit.putBoolean("firstLaunch",false);
        edit.commit();
  }

	public void signup(View v)
	{
		
		if(spass.getText().toString().equals(cpass.getText().toString()))
		{
			check=spass.getText().toString();
			Toast.makeText(Signup.this,"signup successful!!:)",Toast.LENGTH_SHORT).show();
			startActivity(new Intent(Signup.this, Initial.class));
		}
		else if(!spass.getText().toString().equals(cpass.getText().toString()))
		{
			Toast.makeText(Signup.this,"passwords do not match :(",Toast.LENGTH_SHORT).show();
        	((EditText) findViewById(R.id.spass)).setText("");
			((EditText) findViewById(R.id.cpass)).setText("");
		}
		else
		{
			Toast.makeText(Signup.this,"one or both fields empty :(",Toast.LENGTH_SHORT).show();
        	((EditText) findViewById(R.id.spass)).setText("");
			((EditText) findViewById(R.id.cpass)).setText("");
		}
		
		SharedPreferences settings = getSharedPreferences("mySettings", Activity.MODE_PRIVATE);
	    SharedPreferences.Editor editor = settings.edit();
	    editor.putString("plscome", spass.getText().toString());

	    // Commit the edits!
	    editor.commit();
		
	}
	
	

}
