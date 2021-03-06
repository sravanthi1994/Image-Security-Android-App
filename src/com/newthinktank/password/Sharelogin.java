package com.newthinktank.password;

import android.R.string;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.os.AsyncTask;
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

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Sharelogin extends Activity{

	public EditText pass=null;
	public EditText confirm=null;
	private Button but;
	String Content;
	/*JSONParser jsonParser = new JSONParser();
	private static final String LOGIN_URL = "http://rass-d5.net46.net/sharelogin.php";
	private static final String TAG_SUCCESS = "success";
	private static final String TAG_MESSAGE = "message";*/

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_sharelogin);
		pass= (EditText)findViewById(R.id.pass);
		confirm= (EditText)findViewById(R.id.confirm);
		but= (Button)findViewById(R.id.signup);
		setupmessagebutton();
	
		/*SharedPreferences pref = getSharedPreferences("ActivityPREF", Context.MODE_PRIVATE);
	    if(pref.getBoolean("activity_executed", false)){
	    	startActivity(new Intent(Sharelogin.this, Encrypt.class));
	        finish();
	    } else {
	        Editor ed = pref.edit();
	        ed.putBoolean("activity_executed", true);
	        ed.commit();
	    }*/
		final String PREFS_NAME = "MyPrefsFile";

		SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);

		if (settings.getBoolean("my_first_tim", true)) {
		    //the app is being launched for first time, do something
			setFirsLaunchFlag();
		    Log.d("Comments", "First time");

		             // first time task

		    // record the fact that the app has been started at least once
		    settings.edit().putBoolean("my_first_tim", false).commit(); 
		}
	
	}
	
	private void setFirsLaunchFlag(){
        SharedPreferences prefs = getSharedPreferences(
           "Preferences",
           Context.MODE_PRIVATE);
        SharedPreferences.Editor edit = prefs.edit();
        edit.putBoolean("firstLaunc",false);
        edit.commit();
  }
	
	 private void setupmessagebutton()
		{
		but.setOnClickListener(new View.OnClickListener(){
			public void onClick(View arg0){
				if(pass.getText().toString().equals(confirm.getText().toString()))
				{
	            new LongOperation().execute("http://rass-d5.net46.net/password.php");
				}
				else
				{
					Toast.makeText(Sharelogin.this,"passwords do not match!!",Toast.LENGTH_SHORT).show();
					((EditText) findViewById(R.id.pass)).setText("");
					((EditText) findViewById(R.id.confirm)).setText("");
				}
			}

		});
		}
	    public class LongOperation extends AsyncTask<String,Void,Void>{
	        private final HttpClient Client = new DefaultHttpClient();
	        private ProgressDialog Dialog = new ProgressDialog(Sharelogin.this);
	        protected Void doInBackground(String... params) {

	            try {

	                // Call long running operations here (perform background computation)
	                // NOTE: Don't call UI Element here.

	                // Server url call by Post method
	                HttpPost httpPost = new HttpPost(params[0]);
	                List<NameValuePair> postData = new ArrayList<NameValuePair>(1);
	                postData.add(new BasicNameValuePair("password",pass.getText().toString()));
	                httpPost.setEntity(new UrlEncodedFormEntity(postData));
	                HttpResponse response = Client.execute(httpPost);
	                Content = EntityUtils.toString(response.getEntity());
	                Log.d("Content", Content);
	                // Decoding the json data


	            } catch (ClientProtocolException e) {
	                cancel(true);
	            } catch (IOException e) {
	                cancel(true);
	            }

	            return null;
	        }

	        protected void onPreExecute() {
	            Dialog.setMessage("Signing up...");
	            Dialog.show();
	        }

	        protected void onPostExecute(Void aVoid) {
	            Dialog.dismiss();
	            String s= jsonDecode();
	            if(s.equals("success"))
	            {
	            	
	            	Toast.makeText(Sharelogin.this,s,Toast.LENGTH_SHORT).show();
	            	startActivity(new Intent(Sharelogin.this, Encrypt.class));
	            
	            }
	            else
	            {
	            	Toast.makeText(Sharelogin.this,s,Toast.LENGTH_SHORT).show();
	            	((EditText) findViewById(R.id.pass)).setText("");
					((EditText) findViewById(R.id.confirm)).setText("");
	            }
	            
	        }

	    }

	    private String jsonDecode() {

	        try {
	            JSONObject object = new JSONObject(Content);
	            return object.getString("result");
	            
	        } catch (JSONException e) { 
	            e.printStackTrace();
	        }
			return Content;

	    }
}
		
	
