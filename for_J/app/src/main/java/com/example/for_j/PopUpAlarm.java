package com.example.for_j;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Intent;
import android.os.Build;
import android.os.IBinder;
import androidx.core.app.NotificationCompat;

public class PopUpAlarm extends Service {

    private static final int NOTIFICATION_ID = 1;
    private static final String CHANNEL_ID = "PopUp";
    private static final String CHANNEL_NAME = "PopUp Channel";

    private NotificationManager notificationManager;

    @Override
    public void onCreate() {
        super.onCreate();
//        notificationManager = getSystemService(notificationManager.class);
        createNotificationChannel();
    }
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        // Notification을 생성하고 표시하는 코드
        Notification notification = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setSmallIcon(R.drawable.android_main_image)
                .setContentTitle("for_j")
                .setContentText("일어나")
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setAutoCancel(true)
                .build();

//        notificationManager.notify(NOTIFICATION_ID, notification);

        // 서비스를 종료하지 않고 계속 실행하기 위해서 START_STICKY를 반환합니다.
        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        // 서비스가 종료될 때 Notification도 삭제합니다.
        notificationManager.cancel(NOTIFICATION_ID);
    }

    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            int importance = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, CHANNEL_NAME, importance);
            notificationManager.createNotificationChannel(channel);
        }
    }
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
