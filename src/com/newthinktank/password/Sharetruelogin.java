package com.newthinktank.password;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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

import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Sharetruelogin extends Activity {

	public EditText type=null;
	private Button btn;
	String Content;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_sharetruelogin);
		type= (EditText)findViewById(R.id.typepass);
		btn= (Button)findViewById(R.id.loginbut);
		setupmessagebutton();
		if(firstLaunch()){
	           startActivity(new Intent(this, Sharelogin.class));
	           finish();
	       }else{
	    	  
	   		
	         //Do your normal stuff
	    	  
	       }
	}

	private boolean firstLaunch(){
        SharedPreferences prefs = getSharedPreferences(
           "Preferences",
           Context.MODE_PRIVATE);
        return prefs.getBoolean("firstLaunc",true);
   }
	
	private void setupmessagebutton()
	{
	btn.setOnClickListener(new View.OnClickListener(){
		public void onClick(View arg0){
			
            new LongOperation().execute("http://rass-d5.net46.net/sharetruelogin.php");
			
		}

	});
	}
    public class LongOperation extends AsyncTask<String,Void,Void>{
        private final HttpClient Client = new DefaultHttpClient();
        private ProgressDialog Dialog = new ProgressDialog(Sharetruelogin.this);
        protected Void doInBackground(String... params) {

            try {

                // Call long running operations here (perform background computation)
                // NOTE: Don't call UI Element here.

                // Server url call by Post method
                HttpPost httpPost = new HttpPost(params[0]);
                List<NameValuePair> postData = new ArrayList<NameValuePair>(1);
                postData.add(new BasicNameValuePair("password",type.getText().toString()));
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
            	
            	Toast.makeText(Sharetruelogin.this,s,Toast.LENGTH_SHORT).show();
            	startActivity(new Intent(Sharetruelogin.this, Encrypt.class));
            
            }
            else
            {
            	Toast.makeText(Sharetruelogin.this,s,Toast.LENGTH_SHORT).show();
            	((EditText) findViewById(R.id.typepass)).setText("");
				
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
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_sharetruelogin, menu);
		return true;
	}

}
