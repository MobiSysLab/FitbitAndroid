package com.example.hari.gpstest.Location;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.Location;
import android.location.LocationManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.CookieManager;
import android.widget.Button;
import android.widget.TextView;

import com.example.hari.gpstest.R;

public class GetLocation extends AppCompatActivity {
    SharedPreferences fitbitPreference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.get_location);


        Button.OnClickListener btnClickListener = new View.OnClickListener() {
            public void onClick(View v) {
                logout();
            }
        };
        findViewById(R.id.logoutButton).setOnClickListener(btnClickListener);

        this.startService(new Intent(this, LocationService.class));
    }

    private void logout() {
        fitbitPreference = getSharedPreferences("fitbitPreference", MODE_PRIVATE);
        SharedPreferences.Editor editor = fitbitPreference.edit();

        editor.remove("userKey");
        editor.commit();

        Log.d("LOGOUT", "logout finished");

        clearCookies();
//        finishActivity();
    }

    private void clearCookies() {
        CookieManager.getInstance().removeAllCookies(null);
        CookieManager.getInstance().flush();
    }
}