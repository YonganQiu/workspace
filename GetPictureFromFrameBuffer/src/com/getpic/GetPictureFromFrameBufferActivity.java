package com.getpic;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class GetPictureFromFrameBufferActivity extends Activity {
    /** Called when the activity is first created. */
	static{
		System.loadLibrary("GetPicUsingJni");		
	}
	public TextView tx1;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        tx1 = (TextView)findViewById(R.id.tx1); 
        
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
              String codePath = getApplication().getPackageCodePath();
              Log.i("elvis", codePath);
        	 RootHelper.upgradeRootPermission(codePath);
          }
        });
                
    }
}