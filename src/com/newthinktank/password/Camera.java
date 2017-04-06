package com.newthinktank.password;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;




import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.R.string;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

public class Camera extends Activity {

	Encrypt enc=new Encrypt();
	Button one;
	Button two;
	Button three;
	private ImageView iv;
	File y;
	String nameoffolder="/"+enc.rando()+".jpg";
	Uri uriSavedImage;
	String destpath;
	String filename;
	String filename1;
	String new1;
	String compresspath;
	File x;
	Uri impath;
	Bitmap bp;
	String nameoffolder2="/"+enc.rando()+".jpg";
	private static final int CAMERA_REQUEST=1888;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		//final File x=new File(android.os.Environment.getExternalStorageDirectory(),File.separator+"Password");
		//x.mkdirs();
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_camera);
		this.iv= (ImageView) findViewById(R.id.imageView1);
		one = (Button) findViewById(R.id.button1);
		three = (Button) findViewById(R.id.share);
		one.setOnClickListener(new OnClickListener() {
		public void onClick(View v)
		{
			open();
		}
		});
		
		
		two = (Button) findViewById(R.id.button2);
		if(two.equals(null))
		{
			Toast.makeText(Camera.this,
					"two is null",
					Toast.LENGTH_SHORT).show();
		}
		two.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				/*Toast.makeText(Camera.this,
						"inside click",
						Toast.LENGTH_SHORT).show();*/
				try{
					/*Toast.makeText(Camera.this,
							"inside try",
							Toast.LENGTH_SHORT).show();*/
					blowfish encryptFil = new blowfish("thisismypassword");
					/*Toast.makeText(Camera.this,
							"xxx"+y.getPath()+"after constructor",
							Toast.LENGTH_SHORT).show();*/
					//destpath=Environment.getExternalStorageDirectory().getAbsolutePath()+nameoffolder;
					destpath= y.getPath()+nameoffolder;
					/*Toast.makeText(Camera.this,
							"after"+y.getPath()+"and"+destpath,
							Toast.LENGTH_SHORT).show();*/
					//destpath="/storage/sdcard/test";
					encryptFil.encrypt(new1,destpath);
					Toast.makeText(Camera.this,
							"done encrypting, yeyy",
							Toast.LENGTH_SHORT).show();
					//File file = new File(filename1);
					//boolean deleted = file.delete();
				//	Toast.makeText(Camera.this,
					//		"Done encrypting..yey..",
						//	Toast.LENGTH_SHORT).show();
					
					
					}catch (Exception e) {
						/*Toast.makeText(Camera.this, e.getMessage(),
								Toast.LENGTH_SHORT).show();*/
				}
			}
		});
		three.setOnClickListener(new OnClickListener() {
			public void onClick(View v)
			{
				signup();
			}
			});
	}
	public void signup()
	{
		Intent intent = new Intent(this, Sharetruelogin.class);
		startActivity(intent);
	}
	// CODE TO CAPTURE AN IMAGE AND SAVE IT A FOLDER
	public void open()
	{
		Intent i=new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
		y=new File(android.os.Environment.getExternalStorageDirectory()+"/Password/camera");
		y.mkdirs();
		filename="/"+enc.rando()+".jpg";
		File output=new File(y,filename);
		uriSavedImage=Uri.fromFile(output);
		//filename1=uriSavedImage.getPath();
		Toast.makeText(Camera.this,
				uriSavedImage.getPath(),
				Toast.LENGTH_SHORT).show();
		
		i.putExtra(MediaStore.EXTRA_OUTPUT, uriSavedImage);
		/*Toast.makeText(Camera.this,
				"after saving",
				Toast.LENGTH_SHORT).show();*/
		startActivityForResult(i,CAMERA_REQUEST);
		
		
	}
	
	public static Bitmap decodeSampledBitmapFromFile(String path, int reqWidth, int reqHeight)
    { // BEST QUALITY MATCH
       
       //First decode with inJustDecodeBounds=true to check dimensions
       final BitmapFactory.Options options = new BitmapFactory.Options();
       options.inJustDecodeBounds = true;
       BitmapFactory.decodeFile(path, options);

       // Calculate inSampleSize, Raw height and width of image
       final int height = options.outHeight;
       final int width = options.outWidth;
       options.inPreferredConfig = Bitmap.Config.RGB_565;
       int inSampleSize = 1;

       if (height > reqHeight)
       {
           inSampleSize = Math.round((float)height / (float)reqHeight);
       }
       int expectedWidth = width / inSampleSize;

       if (expectedWidth > reqWidth)
       {
           //if(Math.round((float)width / (float)reqWidth) > inSampleSize) // If bigger SampSize..
           inSampleSize = Math.round((float)width / (float)reqWidth);
       }

       options.inSampleSize = inSampleSize;

       // Decode bitmap with inSampleSize set
       options.inJustDecodeBounds = false;


       return BitmapFactory.decodeFile(path, options);
    }
	// CODE TO DISPLAY THE CAPTURED IMAGE
	protected void onActivityResult(int requestCode,int resultCode,Intent data ) {
		/*Toast.makeText(Camera.this,
				"on act result",
				Toast.LENGTH_SHORT).show();
		super.onActivityResult(requestCode, resultCode, data);*/
		if(requestCode==CAMERA_REQUEST && resultCode==RESULT_OK)
		{
			/*Toast.makeText(Camera.this,
					"camera request",
					Toast.LENGTH_SHORT).show();*/
			BitmapFactory.Options options=new BitmapFactory.Options();
			//final Bitmap bp=BitmapFactory.decodeFile(uriSavedImage.getPath(),options);
			filename1= uriSavedImage.getPath();
			bp= decodeSampledBitmapFromFile(filename1, 200, 200); //YOU WILL NOT NEED THIS. ITS FOR COMPRESSION
			iv.setImageBitmap(bp);
			iv.setTag(bp);  //ENDS HERE
			
			
			/*Toast.makeText(Camera.this,
					"before try",
					Toast.LENGTH_SHORT).show();*/
			
				/*Toast.makeText(Camera.this,
						"inside try",
						Toast.LENGTH_SHORT).show();*/
				//createfile(image);
				compresspath= y.getPath()+nameoffolder2;
				File f = new File(compresspath);
				FileOutputStream out = null;
				try {
					out = new FileOutputStream(f);
					bp.compress(Bitmap.CompressFormat.JPEG, 100, out); // bmp is your Bitmap instance
					// PNG is a lossless format, the compression factor (100) is ignored
				} catch (Exception e) {
					e.printStackTrace();
				} finally {
					try {
						if (out != null) {
							out.close();
							}
					} catch (IOException e) {
						e.printStackTrace();
					}
				}	
				new1=f.getAbsolutePath();
				/*Toast.makeText(Camera.this,
						new1,
						Toast.LENGTH_SHORT).show();*/
		}
		
	}

}
