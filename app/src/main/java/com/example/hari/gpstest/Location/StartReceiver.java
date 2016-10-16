package com.example.hari.gpstest.Location;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.example.hari.gpstest.MainActivity;

/**
 * Created by Heojae on 2016-10-16.
 */

public class StartReceiver extends BroadcastReceiver{
    @Override
    public void onReceive(Context context, Intent intent)
    {
        String action = intent.getAction();

        if (action.equals("android.intent.action.BOOT_COMPLETED"))
        {
            Intent i = new Intent(context, LocationService.class);
            i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(i);
        }
    }
}
