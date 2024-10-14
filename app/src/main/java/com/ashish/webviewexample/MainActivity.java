package com.ashish.webviewexample;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import android.window.OnBackInvokedDispatcher;

public class MainActivity extends AppCompatActivity {

    WebView webView;
    ProgressBar pgBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        webView = findViewById(R.id.webView);
        pgBar = findViewById(R.id.pgBar);

        //Enable javaScript in the webView
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);


        webView.loadUrl("https://www.google.com");
        //The problem is that we are able to load the above url in our app but further searching anything through that browser it automatically opening our default browser and not opening in the same browser and to solve this we set webViewClient;
        webView.setWebViewClient(new WebViewClient()
        {
            //This is not a abstract method(Hence no mandatory to implement), therefore will not show error or any suggestion to implement but i have implemented to see which method is useful to help us load further url in same webView;
//            @Override
//            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
//                return super.shouldOverrideUrlLoading(view, request);
//            }

            //To show the progress bar while loading new page or url in the webView;
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                pgBar.setVisibility(View.GONE);
                super.onPageStarted(view, url, favicon);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                pgBar.setVisibility(View.INVISIBLE);
                super.onPageFinished(view, url);
            }
        }
                //After setting this, still there is problem that we can open the next(further) url in same web but can't go back one by one as a stack but the activity is closed and if that is itself the first activity as here the app is closed so for this we implement onBackPressed method to work on stack;
        );


        // Handle back button presses with OnBackPressedCallback
        OnBackPressedCallback callback = new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                if (webView.canGoBack()) {
                    webView.goBack();
                } else {
                    // If the WebView cannot go back, use the default back behavior
                    finish();
                }
            }
        };
        getOnBackPressedDispatcher().addCallback(this, callback);
    }

    //The Above One or this one to handle back pressed;
//    @Override
//    public void onBackPressed() {
//      if(webView.canGoBack()){
//          webView.goBack();
//      }else{
//          super.onBackPressed();
//      }
//    }
}