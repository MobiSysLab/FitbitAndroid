package com.example.hari.gpstest;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.content.Context;
import android.content.SharedPreferences;

import com.example.hari.gpstest.Communication.Authorization;
import com.example.hari.gpstest.Location.GetLocation;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.URL;

public class MainActivity extends AppCompatActivity{
    static final String oauth2Code = "https://www.fitbit.com/oauth2/authorize?" +
            "response_type=code&" +
            "client_id=227ZFL&" +
            "redirect_uri=http%3A%2F%2F10.0.2.2%3A8080%2Fcallback" +
            "&scope=activity%20heartrate%20location%20nutrition%20profile%20settings%20sleep%20social%20weight&" +
            "expires_in=604800";
    private String code;
    public String userKey = "temp";
    private WebView webView;
    private boolean loadingFlag = false;
    Intent intent;

    private static String TAG = "*** MainActivity *** ";

    @JavascriptInterface
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        loginPage(this);
    }

    public void loginPage(Context content){
        Log.d(TAG, "Login Page");
        code = "";


        webView = (WebView) findViewById(R.id.web_view);

        webView.getSettings().setJavaScriptEnabled(true);

        webView.addJavascriptInterface(new ParseKey(content), "HtmlViewer");
        webView.setVisibility(View.VISIBLE);

        webView.setHorizontalScrollBarEnabled(false);
        webView.setOnTouchListener(new View.OnTouchListener() {
            public boolean onTouch(View v, MotionEvent event) {
                return (event.getAction() == MotionEvent.ACTION_MOVE);
            }
        });

        webView.setWebViewClient(new WebViewClient() {



            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                try {
                    if (url.contains("code=")) {
                        URL codeUrl = new URL(url.toString());
                        code = codeUrl.getQuery().split("=")[1];
                        Log.d(TAG, "after code URL");
                        Log.d(TAG, code);

                        Authorization auth = new Authorization(getApplicationContext());
                        auth.getToken(code);
                        webView.loadUrl("javascript:window.HtmlViewer.showHTML" +
                                "('<html>'+document.getElementsByTagName('html')[0].innerHTML+'</html>');");
                        webView.setVisibility(View.GONE);
                        loadingFlag = true;
                        Log.d(TAG, "before intent");

                        intent = new Intent(MainActivity.this, GetLocation.class);
                        startActivity(intent);
                        Log.d(TAG, "after intent");
                    }
                    Log.d(TAG, "after code condition");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        Log.d(TAG, "1");
        webView.loadUrl(oauth2Code);
        Log.d(TAG, "2");
        return;
    }


    class ParseKey {
        private Context ctx;

        ParseKey (Context ctx) {
            this.ctx = ctx;
        }

        @JavascriptInterface
        public void showHTML(String html) {
            userKey = Html.fromHtml(html).toString();
            try {
                JSONObject obj = new JSONObject(userKey);
                userKey = obj.getString("key");
                Log.d(TAG, String.valueOf(userKey));
            } catch (JSONException e) {
                System.out.println("Error");
            }
        }
    }
}
