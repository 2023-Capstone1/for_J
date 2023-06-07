package com.example.for_j;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.SystemClock;
import android.preference.PreferenceManager;
import android.util.Log;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

public class TimeAlarm {
    private Context context;
    private long startTime;
    private boolean isRunning;
    private SharedPreferences TimeSwitch;

    public TimeAlarm(Context context) {
        this.context = context;
        this.startTime = 0;
        this.isRunning = false;
        this.TimeSwitch = PreferenceManager.getDefaultSharedPreferences(context);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            // Create the notification channel
            NotificationChannel channel = new NotificationChannel("channel_id", "Channel Name", NotificationManager.IMPORTANCE_DEFAULT);
            channel.setDescription("Channel Description");

            // Register the channel with the system
            NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
            notificationManager.createNotificationChannel(channel);
        }
    }

    public void setTimeSwitch(String timeSwitch) {
        SharedPreferences.Editor editor = TimeSwitch.edit();
        editor.putString("timeSwitch", timeSwitch);
        editor.apply();
    }

    public String getTimeSwitch() {
        return TimeSwitch.getString("timeSwitch", null);
    }

    public boolean isTimeSwitchEnabled() {
        return getTimeSwitch() != null && getTimeSwitch().equals("1");
    }

    public void start() {
        if (!isRunning) {
            startTime = SystemClock.elapsedRealtime();
            isRunning = true;
        }
    }

    public void stop() {
        if (isRunning) {
            isRunning = false;
        }
    }
    public long getStartTime() {
        return startTime;
    }

    public void showElapsedNotification(String taskName, int hour) {
        Log.d("success1", "success1");
        if (isRunning == true) {
            if (isTimeSwitchEnabled() && taskName != null) {
                NotificationCompat.Builder builder = new NotificationCompat.Builder(context, "channel_id")
                        .setSmallIcon(R.drawable.android_main_image)
                        .setContentTitle("작업 타이머")
                        .setContentText(taskName + " 작업이 " + hour + "시 경과했습니다.")
                        .setPriority(NotificationCompat.PRIORITY_DEFAULT);

                NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);
                notificationManager.notify(0, builder.build());
            }
        }
    }
}