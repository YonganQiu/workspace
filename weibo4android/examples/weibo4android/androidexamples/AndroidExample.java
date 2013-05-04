package weibo4android.androidexamples;

import weibo4android.Weibo;
import weibo4android.WeiboException;
import weibo4android.http.AccessToken;
import weibo4android.http.RequestToken;
import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class AndroidExample extends Activity {
	/** Called when the activity is first created. */
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		System.setProperty("weibo4j.oauth.consumerKey", Weibo.CONSUMER_KEY);
    	System.setProperty("weibo4j.oauth.consumerSecret", Weibo.CONSUMER_SECRET);
    	Button beginOuathBtn=  (Button) findViewById(R.id.Button01);
    	

    	beginOuathBtn.setOnClickListener(new Button.OnClickListener()
        {

            public void onClick( View v )
            {   
            	/*Weibo weibo = new Weibo();
            	RequestToken requestToken;
				try {
					requestToken =weibo.getOAuthRequestToken("weibo4android://OAuthActivity");
	    			OAuthConstant.getInstance().setRequestToken(requestToken);
					Uri uri = Uri.parse(requestToken.getAuthenticationURL()+ "&display=mobile");
	    			startActivity(new Intent(Intent.ACTION_VIEW, uri));
				} catch (WeiboException e) {
					e.printStackTrace();
				}*/

                new Thread(new Runnable() {
                    
                    @Override
                    public void run() {
                        send();
                    }
                }).start();
            }
        } );
	}

	private void send() {
        System.setProperty("weibo4j.oauth.consumerKey", Weibo.CONSUMER_KEY);
        System.setProperty("weibo4j.oauth.consumerSecret", Weibo.CONSUMER_SECRET);
        Weibo weibo = new Weibo();
        try {
             String userId = "prince.elvis.hew@gmail.com";
             String passWord = "890501";
             AccessToken accessToken = weibo.getXAuthAccessToken(userId, passWord, "client_auth");
             Log.i("elvis", "Got access token.");
             Log.i("elvis", "Access token: "+ accessToken.getToken());
             Log.i("elvis", "Access token secret: "+ accessToken.getTokenSecret());
             weibo.updateStatus("TEST");
        } catch (WeiboException e) {
            e.printStackTrace();
        }

	}
}