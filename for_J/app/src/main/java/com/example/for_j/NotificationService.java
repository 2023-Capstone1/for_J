package com.example.for_j;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class NotificationService extends Service {
    private static final String CHANNEL_ID = "AlarmChannel";
    private int code = 0;
    private int hour;
    private int minute;
    private int cal_Alarm;
    private String Sname;
    private String id;
    private String selectedDateStr;

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        code = intent.getIntExtra("code", 0);
        hour = intent.getIntExtra("hour", 0);
        minute = intent.getIntExtra("minute", 0);
        cal_Alarm = intent.getIntExtra("cal_Alarm",0);
        id = intent.getStringExtra("id"); // id를 Intent에 추가
        Sname = intent.getStringExtra("name");

        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        selectedDateStr = dateFormat.format(calendar.getTime());

        Log.d("selectedDateStr",selectedDateStr);

        String contentText;
        if(code == 0 && cal_Alarm == 0 || cal_Alarm == 1 || cal_Alarm == 2 || cal_Alarm == 3){
            if (cal_Alarm == 1) {
                // case 1: 시간과 분에서 10분을 빼는 조작
                Log.d("hour",String.valueOf(hour));
                Log.d("mi",String.valueOf(minute));
                int modifiedHour = hour;
                int modifiedMinute = minute - 10;
                // 시간과 분이 음수인 경우에 대한 예외 처리
                if (modifiedMinute < 0) {
                    modifiedHour -= 1;
                    modifiedMinute += 60;
                }
                contentText = getCalendarContentTextForCode(modifiedHour, modifiedMinute, Sname);
            }else if (cal_Alarm == 2) {
                Log.d("hour",String.valueOf(hour));
                Log.d("mi",String.valueOf(minute));
                // case 2: 1시간 전으로 조작
                int modifiedHour = hour - 1;
                int modifiedMinute = minute;
                // 시간이 음수인 경우에 대한 예외 처리
                if (modifiedHour < 0) {
                    // 음수일 경우, 23으로 설정하여 이전 날로 이동
                    modifiedHour = 23;
                }
                contentText = getCalendarContentTextForCode(modifiedHour, modifiedMinute, Sname);
            }else {
                Log.d("hour",String.valueOf(hour));
                Log.d("mi",String.valueOf(minute));
                // 기본적인 case: getHabitContentTextForCode 메서드 호출
                contentText = getHabitContentTextForCode(hour, minute, Sname);
            }
        }else if (code == 0) {
            Log.d("hour",String.valueOf(hour));
            Log.d("mi",String.valueOf(minute));
            contentText = getHabitContentTextForCode(hour, minute, Sname);
        } else {
            Log.d("hour",String.valueOf(hour));
            Log.d("mi",String.valueOf(minute));
            contentText = getContentTextForCode(code, hour, minute);
        }


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
        IdSave idSave = (IdSave) getApplication();
        String user_id = idSave.getUserId();
        switch (code) {
            case 1:
            {
                // url 작성
                ApiService AlarmApiService = new ApiService();
                String url = "http://203.250.133.162:8080/settingAPI/get_todo_list/" + user_id + selectedDateStr;
                AlarmApiService.getUrl(url);

                String num = AlarmApiService.getValue("todo_total");

                if(num == null){
                    return "오늘의 To_do가 " + "0개 남았습니다.";
                }else{
                    return "오늘의 To_do가 " + AlarmApiService.getValue("todo_total") + "개 남았습니다.";
                }
            }
            case 2:
            {
                // url 작성
                ApiService AlarmApiService = new ApiService();
                String url = "http://203.250.133.162:8080/settingAPI/get_habit_today/" + user_id + selectedDateStr;
                AlarmApiService.getUrl(url);

                String num = AlarmApiService.getValue("habit_today_total");

                if(num == null){
                    return "오늘의 Habit이 " + "0개 남았습니다.";
                }else{
                    return "오늘의 Habit이 " + AlarmApiService.getValue("habit_today_total") + "개 남았습니다.";
                }
            }
            case 3:
            {
                // url 작성
                ApiService AlarmApiService = new ApiService();
                String url = "http://203.250.133.162:8080/settingAPI/get_cal_list/" + user_id + selectedDateStr;
                AlarmApiService.getUrl(url);

                String num = AlarmApiService.getValue("total");

                if(num == null){
                    return "오늘의 Calendar 일정이 " + "0개 남았습니다.";
                }else{
                    return "오늘의 Calendar 일정이 " + AlarmApiService.getValue("habit_today_total") + "개 남았습니다.";
                }
            }
            default:
            {
                return "자신의 일정을 추가해 보아요";

            }
        }
    }

    // HabitContentTextForCode 메서드 추가
    private String getHabitContentTextForCode(int hour, int minute, String Sname) {
        String contentText = Sname + "이 " + hour+ "시 " + minute + "분으로 설정되었습니다.";
        return contentText;
    }

    // CalendarContentTextForCode 메서드 추가
    private String getCalendarContentTextForCode(int hour, int minute, String Sname) {
        String contentText = Sname + "이 " + hour+ "시 " + minute + "분으로 설정되었습니다.";
        return contentText;
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