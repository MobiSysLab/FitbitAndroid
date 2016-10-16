package com.example.hari.gpstest.Location;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.os.SystemClock;
import android.util.Log;
import android.widget.Toast;

/**
 * Created by Heojae on 2016-10-16.
 */

public class LocationService extends Service{
    // An alarm for rising in special times to fire the
    // pendingIntentPositioning
    private AlarmManager alarmManagerPositioning;
    // A PendingIntent for calling a receiver in special times
    public PendingIntent pendingIntentPositioning;

    @Override
    public void onCreate() {
        super.onCreate();
        alarmManagerPositioning = (AlarmManager)
                getSystemService(Context.ALARM_SERVICE);
        Intent intentToFire = new Intent(
                ReceiverPositioningAlarm.ACTION_REFRESH_SCHEDULE_ALARM);
        intentToFire.putExtra(ReceiverPositioningAlarm.COMMAND,
                ReceiverPositioningAlarm.SENDER_SRV_POSITIONING);
        pendingIntentPositioning = PendingIntent.getBroadcast(this, 0,
                intentToFire, 0);
    };

    @Override
    public void onStart(Intent intent, int startId) {
        try {
            long interval = 60 * 1000;
            int alarmType = AlarmManager.ELAPSED_REALTIME_WAKEUP;
            long timetoRefresh = SystemClock.elapsedRealtime();
            alarmManagerPositioning.setInexactRepeating(alarmType,
                    timetoRefresh, interval, pendingIntentPositioning);
        } catch (NumberFormatException e) {
            Toast.makeText(this,
                    "error running service: " + e.getMessage(),
                    Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            Toast.makeText(this,
                    "error running service: " + e.getMessage(),
                    Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public IBinder onBind(Intent arg0) {
        return null;
    }

    @Override
    public void onDestroy() {
        this.alarmManagerPositioning.cancel(pendingIntentPositioning);
        ReceiverPositioningAlarm.stopLocationListener();
    }
}
