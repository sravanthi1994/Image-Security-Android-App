package com.newthinktank.password;


import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Password extends Activity {

	private EditText password=null;
	private Button Login;
	String s;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		 super.onCreate(savedInstanceState);
	   		setContentView(R.layout.activity_password);
	   	 password= (EditText)findViewById(R.id.editText1);
	   		Login= (Button)findViewById(R.id.button1);
		if(firstLaunch()){
	           startActivity(new Intent(this, Signup.class));
	           s=Signup.check;
	           finish();
	       }else{
	    	  
	   		
	         //Do your normal stuff
	    	  
	       }
		
		
	}
	
	
	 private boolean firstLaunch(){
         SharedPreferences prefs = getSharedPreferences(
            "Preferences",
            Context.MODE_PRIVATE);
         return prefs.getBoolean("firstLaunch",true);
    }

	
	public void login(View v)
	{
	
		Intent intent = new Intent(this, Initial.class);
		
		SharedPreferences settings = getSharedPreferences("mySettings", Activity.MODE_PRIVATE);
	    String check = settings.getString("plscome", "false");
		/*Toast.makeText(getApplicationContext(),password.getText().toString() +"and"+check, Toast.LENGTH_SHORT).show();*/
		if(password.getText().toString().equals(check))
		{
			Toast.makeText(getApplicationContext(), "Redirecting....", Toast.LENGTH_SHORT).show();
			startActivity(intent);
		}
		else{
			Toast.makeText(getApplicationContext(), "Wrong Password....", Toast.LENGTH_SHORT).show();
		}
	}
	
	


}
