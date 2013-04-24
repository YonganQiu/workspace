package com.getpic;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.nio.ByteBuffer;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.PixelFormat;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class GetPictureFromFrameBufferActivity extends Activity {
    /** Called when the activity is first created. */
	static{
		System.loadLibrary("GetPicUsingJni");		
	}
	public TextView tx1;
	public ImageView mImage;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        tx1 = (TextView)findViewById(R.id.tx1); 
        mImage = (ImageView) findViewById(R.id.iv);
        
        Button bt1 = (Button)findViewById(R.id.bt1);
        bt1.setOnClickListener(new Button.OnClickListener()
        {
          @Override
          public void onClick(View v)
          {   
        	  Display display = getWindowManager().getDefaultDisplay(); 
              int width = display.getWidth();  
              int height = display.getHeight(); 
              int bit = 16;
              
        	  GetPicUsingJni getpic = new GetPicUsingJni();
              int i = getpic.getPicFromFrameBuffer(width, height, bit);
              if(i == -1){
              	tx1.setText("失败");
              }
              else{
              	tx1.setText("成功" + width + "*" + height);
              }
          }
        });
        
        Button bt2 = (Button)findViewById(R.id.bt2);
        bt2.setOnClickListener(new Button.OnClickListener()
        {
          @Override
          public void onClick(View v)
          {            
//              String codePath = getApplication().getPackageCodePath();
//              Log.i("elvis", codePath);
//        	 RootHelper.upgradeRootPermission(codePath);

              try {
                mImage.setImageBitmap(getFB());
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
          }
        });
                
    }

    private InputStream getInputStream() throws Exception {
        FileInputStream buf = new FileInputStream(new File("dev/graphics/fb0"));
        return buf;
    }
    private Bitmap getFB() throws Exception {
        File file = new File("/sdcard/mypic.png");
        file.createNewFile();
        DisplayMetrics metrics = new DisplayMetrics();
        Display display = getWindowManager().getDefaultDisplay();
        display.getMetrics(metrics);
        int height = metrics.heightPixels;
        int width = metrics.widthPixels;
        int pixelformat = display.getPixelFormat();
        PixelFormat localPixelFormat = new PixelFormat();
        PixelFormat.getPixelFormatInfo(pixelformat, localPixelFormat);
        int deepth = localPixelFormat.bytesPerPixel;

        Log.i("debug", "pixelformat " + pixelformat + " bytesPerPixel" + localPixelFormat.bytesPerPixel);

        byte[] piex = new byte[height * width * deepth];
        InputStream stream = getInputStream();
        DataInputStream dStream = new DataInputStream(stream);
        dStream.readFully(piex);
        
        int count = height * width;
        int[] data = new int[count];
        int j;
        for (int i = 0; i < count; i++) {
            j = i*deepth + 1;
            data[i] = piex[j] + piex[j+1] << 8;
        }

        Bitmap bitmap = Bitmap.createBitmap(data, width, height, Bitmap.Config.RGB_565);
        FileOutputStream out = new FileOutputStream(file);
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, out);

        return bitmap;
    }
}