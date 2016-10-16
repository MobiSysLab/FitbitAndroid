package com.example.hari.gpstest.Location;

/**
 * Created by Heojae on 2016-10-17.
 */

import android.location.Location;

public interface OnNewLocationListener {
    public abstract void onNewLocationReceived(Location location);
}
