package com.example.for_j;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.IBinder;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class NotificationService extends Service {
    private static final String CHANNEL_ID = "AlarmChannel";
    private int code;
    private int hour;
    private int minute;
    private String selectedDateStr;
    IdSave idSave = (IdSave) getApplication();
    String user_id = idSave.getUserId();

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        code = intent.getIntExtra("code", 0);
        hour = intent.getIntExtra("hour", 0);
        minute = intent.getIntExtra("minute", 0);

        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        selectedDateStr = dateFormat.format(calendar.getTime());

        String contentText = getContentTextForCode(code, hour, minute);

        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        createNotificationChannel(notificationManager);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setSmallIcon(R.drawable.android_main_image)
                .setContentTitle("For_J")
                .setContentText(contentText)
                .setAutoCancel(true);

        if (notificationManager != null) {
            notificationManager.notify(code, builder.build());
        }

        // 서비스가 끝나면 자동으로 소멸되도록 설정
        stopSelf();

        return super.onStartCommand(intent, flags, startId);
    }

    // 코드에 따른 알림 내용 가져오기
    private String getContentTextForCode(int code, int hour, int minute) {
        switch (code) {
            case 1:
            {
//                // url 작성
//                ApiService AlarmApiService = new ApiService();
//                String url = "http://203.250.133.162:8080/settingAPI/get_todo_list/" + user_id + selectedDateStr;
//                AlarmApiService.getUrl(url);
//
//                return "To_do : 오늘의 To_do가 " + AlarmApiService.getValue("todo_total") + "개 남았습니다.";
            }
            case 2:
            {
                return "code 2 알람이 울립니다!! 시간: " + hour + ":" + minute+ ", 선택된 날짜: " + selectedDateStr;

            }
            case 3:
            {
                return "code 3 알람이 울립니다!! 시간: " + hour + ":" + minute+ ", 선택된 날짜: " + selectedDateStr;

            }
            default:
            {
                return "code ? 알람이 울립니다!! 시간: " + hour + ":" + minute+ ", 선택된 날짜: " + selectedDateStr;

            }
        }
    }

    // 알림 채널 생성 (Android 8.0 이상에서 필요)
    private void createNotificationChannel(NotificationManager notificationManager) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, "Alarm Channel", NotificationManager.IMPORTANCE_DEFAULT);
            notificationManager.createNotificationChannel(channel);
        }
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}