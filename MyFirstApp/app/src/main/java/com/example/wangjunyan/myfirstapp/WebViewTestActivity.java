package com.example.wangjunyan.myfirstapp;

import android.app.Activity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;

public class WebViewTestActivity extends Activity {
    private final static String TAG = WebViewTestActivity.class.getSimpleName();

    private EditText mEdtAddress;
    private Button mBtnGo;
    private WebView mWebView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view_test);

        mEdtAddress = (EditText) findViewById(R.id.edt_address);
        mBtnGo = (Button) findViewById(R.id.btn_go);
        mWebView = (WebView) findViewById(R.id.webview);

        mBtnGo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = mEdtAddress.getText().toString();
                if(!TextUtils.isEmpty(url)){
                    mWebView.loadUrl("http://" + url);
                }
            }
        });

        mWebView.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url){
                Log.d(TAG, "shouldOverrideUrlLoading, url = " + url);
                view.loadUrl(url);
                return true;
            }
        });

        mWebView.setWebChromeClient(new WebChromeClient(){
           @Override
           public void onProgressChanged(WebView view, int newProgress){
               Log.d(TAG, "progress = " + newProgress);
           }

            @Override
            public void onReceivedTitle(WebView view, String title){
                Log.d(TAG, "title = " + title);
            }
        });

        WebSettings webSettings = mWebView.getSettings();
        webSettings.setJavaScriptEnabled(true);

        mWebView.loadUrl("http://www.baidu.com");
    }
}
