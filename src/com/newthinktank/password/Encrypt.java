package com.newthinktank.password;




import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Bitmap.CompressFormat;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

public class Encrypt extends Activity {
	
	int num;
	public int rando() {
		List<Integer> NumberList = new ArrayList<Integer>();{
			for (int i = 1; i <= 5000; i++) 
			     {
			      NumberList.add(i); 
			    } 
			  Collections.shuffle(NumberList);
			  num=NumberList.get(0);
			  NumberList.remove(0);
			}
		return num;
	}
	private static final int SELECTED_PICTURE=1;
    String filePath;
    String new1;
    String destpath;
    String destpath1;
    String compresspath;
    String nameoffolder= "/"+rando()+".jpg";
    String nameoffolder1= "/"+rando()+".jpg";
    String nameoffolder2= "/"+rando()+".jpg";
	ImageView iv;
	Button zero;
	Button one;
	Button two;
	File x;
	File y;
	Bitmap image;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		x=new File(android.os.Environment.getExternalStorageDirectory(),File.separator+"Password");
		x.mkdirs();
		//final File y=new File(android.os.Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES)+"/Password/camera/");
		//y.mkdirs();

		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_encrypt);
		iv= (ImageView)findViewById(R.id.imageView1);
		// ZERO BUTTON, WHEN YOU CLICK IT IT DIRECTS YOU TO GALLERY TO SELECT THE IMAGE
		zero = (Button) findViewById(R.id.button1);
		zero.setOnClickListener(new OnClickListener() {
		public void onClick(View v)
		{
			Intent i= new Intent(Intent.ACTION_PICK,android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
			startActivityForResult(i,SELECTED_PICTURE);
		}
		});
		one = (Button) findViewById(R.id.button2);
		one.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				try{
					blowfish encryptFile = new blowfish("thisismypassword");
					//destpath=Environment.getExternalStorageDirectory().getAbsolutePath()+nameoffolder;
					destpath= x.getPath()+nameoffolder;
					//destpath="/storage/sdcard/test";
					encryptFile.encrypt(new1,destpath);
					/*File file = new File(filePath);
					boolean deleted = file.delete();*/
					Toast.makeText(Encrypt.this,
							"Done encrypting..yey..",
							Toast.LENGTH_SHORT).show();
					
					}catch (Exception e) {
						Toast.makeText(Encrypt.this, e.getMessage(),
								Toast.LENGTH_SHORT).show();
				}
			}
		});
		two = (Button) findViewById(R.id.button3);
		two.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				try{
					blowfish decryptFile = new blowfish("thisismypassword");
					destpath1= x.getPath()+nameoffolder1;
					decryptFile.decrypt(destpath,destpath1 );
					Toast.makeText(Encrypt.this,
							"Done decrypting",
							Toast.LENGTH_SHORT).show();
					}catch (Exception e) {
						Toast.makeText(Encrypt.this, e.getMessage(),
								Toast.LENGTH_SHORT).show();
				}
			}
		});
		
		
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
	
	 public void createfile(Bitmap image) throws IOException 
	    {   
	    compresspath= x.getPath()+nameoffolder;
		//File f = new File(compresspath);

		//Convert bitmap to byte array
		//ByteArrayOutputStream bos = new ByteArrayOutputStream();
		//image.compress(CompressFormat.PNG,0 , bos);
		//byte[] bitmapdata = bos.toByteArray();

		//write the bytes in file
		FileOutputStream fos =null;
			fos=new FileOutputStream(compresspath);
			//fos.write(bitmapdata);
			image.compress(CompressFormat.JPEG,100 , fos);
			fos.flush();
			fos.close();
	}
	 
	// CODE TO GET THE FILEPATH AND DISPLAY THE IMAGE IN THE IMAGEVIEW
	protected void onActivityResult(int requestCode,int resultCode,Intent data)
	{
		super.onActivityResult(requestCode, resultCode, data);
		switch(requestCode)
		{
		case SELECTED_PICTURE:
			if(resultCode==RESULT_OK)
			{
				Uri uri= data.getData();
				String[]projection= {MediaStore.Images.Media.DATA};
				Cursor cursor= getContentResolver().query(uri, projection, null, null, null);
				cursor.moveToFirst();
				int columnIndex= cursor.getColumnIndex(projection[0]);
				filePath= cursor.getString(columnIndex);
				cursor.close();
				//Bitmap yourSelectedImage= BitmapFactory.decodeFile(filePath);
				//Drawable d= new BitmapDrawable(yourSelectedImage);
				//iv.setBackground(d);
				//iv.setImageBitmap(BitmapFactory.decodeFile(filePath));
				image= decodeSampledBitmapFromFile(filePath, 200, 200);
				iv.setImageBitmap(image);  //ENDS HERE
				/*Toast.makeText(Encrypt.this,
						"before try",
						Toast.LENGTH_SHORT).show();
				
					Toast.makeText(Encrypt.this,
							"inside try",
							Toast.LENGTH_SHORT).show();*/
					compresspath= x.getPath()+nameoffolder2;
					File f = new File(compresspath);
					FileOutputStream out = null;
					try {
						out = new FileOutputStream(f);
						image.compress(Bitmap.CompressFormat.JPEG, 100, out); // bmp is your Bitmap instance
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
					/*Toast.makeText(Encrypt.this,
							new1,
							Toast.LENGTH_SHORT).show();*/
				
			}
			break;
		 default: break;
		}
	}

	
}
