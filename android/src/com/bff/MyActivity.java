package com.bff;


import android.content.pm.ActivityInfo;
import android.os.Bundle;

import android.util.Log;
import android.webkit.WebView;
import org.apache.cordova.*;

public class MyActivity extends DroidGap {
    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.init();

        this.appView.clearCache(true);
        this.appView.clearHistory();
        this.appView.setWebViewClient(new CordovaWebViewClient(this, this.appView) {


            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {

                if(url.contains("http://")) {
                    //url = url.replace("app://", "");

                    // DO STUFF NOTHING! Stay in our app please.

                    return true;
                } else {
                    //view.loadUrl(url);
                    return super.shouldOverrideUrlLoading(view, url);
                }

            }

        });
        //setContentView(R.layout.main);
        super.loadUrl("file:///android_asset/www/index.html");
        setRequestedOrientation (ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
    }
}
