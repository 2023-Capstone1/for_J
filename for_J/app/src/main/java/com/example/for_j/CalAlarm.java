package com.example.for_j;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;

import java.util.Calendar;

public class CalAlarm {
    private Context context;
    private AlarmManager alarmManager;

    public CalAlarm(Context context) {
        this.context = context;
        this.alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
    }

    public void setAlarm(int hour, int minute, int cal_Alarm, String id, String name) {
        // 알람 설정
        Intent intent = new Intent(context, AlarmReceiver.class);
        intent.putExtra("hour", hour); // 시간 전달
        intent.putExtra("minute", minute); // 분 전달
        intent.putExtra("cal_Alarm", cal_Alarm); // cal_Alarm을 Intent에 추가
        intent.putExtra("id", id); // id를 Intent에 추가
        intent.putExtra("name", name); // name을 Intent에 추가

        PendingIntent pendingIntent;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            pendingIntent = PendingIntent.getBroadcast(context, Integer.parseInt(id), intent, PendingIntent.FLAG_IMMUTABLE);
        } else {
            pendingIntent = PendingIntent.getBroadcast(context, Integer.parseInt(id), intent, PendingIntent.FLAG_UPDATE_CURRENT);
        }

        // 현재 시간 설정
        Calendar currentTime = Calendar.getInstance();
        currentTime.setTimeInMillis(System.currentTimeMillis());

        // 선택한 시간 설정
        Calendar selectedTime = Calendar.getInstance();
        selectedTime.setTimeInMillis(System.currentTimeMillis());
        selectedTime.set(Calendar.HOUR_OF_DAY, hour);
        selectedTime.set(Calendar.MINUTE, minute);
        selectedTime.set(Calendar.SECOND, 0);

        // 선택한 시간이 현재 시간보다 이전인 경우, 다음 날로 설정
        if (selectedTime.before(currentTime)) {
            selectedTime.add(Calendar.DAY_OF_MONTH, 1);
        }

        if (alarmManager != null) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                alarmManager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, selectedTime.getTimeInMillis(), pendingIntent);
            } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                alarmManager.setExact(AlarmManager.RTC_WAKEUP, selectedTime.getTimeInMillis(), pendingIntent);
            } else {
                alarmManager.set(AlarmManager.RTC_WAKEUP, selectedTime.getTimeInMillis(), pendingIntent);
            }
        }
    }

    public void cancelAlarm(String id) {
        Intent intent = new Intent(context, AlarmReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, Integer.parseInt(id), intent, PendingIntent.FLAG_IMMUTABLE);
        if (alarmManager != null) {
            alarmManager.cancel(pendingIntent);
        }
    }
}
