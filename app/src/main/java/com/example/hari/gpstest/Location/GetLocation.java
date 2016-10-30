package com.example.hari.gpstest.Location;

import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.hari.gpstest.R;

public class GetLocation extends AppCompatActivity {
    private double latitude;
    private double longitude;
    LocationManager locationManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.get_location);

//        locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);

//        Button.OnClickListener btnClickListener = new View.OnClickListener() {
//            public void onClick(View v) {
//                TextView gpsTextField = (TextView) findViewById(R.id.gpsTextField);
//                getLocation();
//
//                gpsTextField.setText("Longitude : " + Double.toString(longitude) + ", Latitude : " + Double.toString(latitude));
//            }
//        };
//        findViewById(R.id.getGPSButton).setOnClickListener(btnClickListener);
        Button.OnClickListener btnClickListener = new View.OnClickListener() {
            public void onClick(View v) {
                logout();
            }
        };
        this.startService(new Intent(this, LocationService.class));
    }

    private boolean isGPSEnabbled() {
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
    }

    private boolean isNetworkEnabled() {
        return locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
    }

//    private void getLocation() {
//        if (isGPSEnabbled() && isNetworkEnabled()) {
//            String locationProvider = LocationManager.GPS_PROVIDER;
//            Location lastKnownLocation = locationManager.getLastKnownLocation(locationProvider);
//
//            if (lastKnownLocation != null)
//            {
//                longitude = lastKnownLocation.getLongitude();
//                latitude = lastKnownLocation.getLatitude();
//                Log.d("In Location", "Longitude : " + longitude + ", Latitude : " + latitude);
//            }
//        }
//    }
}
