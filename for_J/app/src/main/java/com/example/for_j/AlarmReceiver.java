package com.example.for_j;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class AlarmReceiver extends BroadcastReceiver {
    private static final String CHANNEL_ID = "AlarmChannel";

    @Override
    public void onReceive(Context context, Intent intent) {
        int code = intent.getIntExtra("code", 0);
        int hour = intent.getIntExtra("hour", 0);
        int minute = intent.getIntExtra("minute", 0);

        // 알림을 시작하는 서비스 호출
        Intent serviceIntent = new Intent(context, NotificationService.class);
        serviceIntent.putExtra("code", code);
        serviceIntent.putExtra("hour", hour);
        serviceIntent.putExtra("minute", minute);
        context.startService(serviceIntent);
    }
}