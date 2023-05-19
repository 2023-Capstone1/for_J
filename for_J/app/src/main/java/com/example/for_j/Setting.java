package com.example.for_j;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.SwitchCompat;

public class Setting extends AppCompatActivity {
    Button setting_alarm_button, setting_todo_button, setting_habit_button, setting_calendar_button, setting_time_button;
    int hour, minute;

    String Alarm_Switch, Alarm_Time, Todo_Time, Todo_Switch, Habit_Time, Habit_Switch, Calendar_Time, Calendar_Switch, Time_Time, Time_Switch, Dark_Mode;
    TextView Setting_All_Del, Setting_Todo_Del, Setting_Habit_Del, Setting_Calendar_Del, Setting_Time_Del;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        // setting 알람 버튼을 누르면 타임 다이어리 팝업창 띄우기
        setting_alarm_button = findViewById(R.id.setting_alarm_button);
        setting_alarm_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SetTime customDialog = new SetTime(Setting.this,1);
                customDialog.show();
            }
        });

        // setting 투두 버튼을 누르면 타임 다이어리 팝업창 띄우기
        setting_todo_button = findViewById(R.id.setting_todo_button);
        setting_todo_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SetTime customDialog = new SetTime(Setting.this,2);
                customDialog.show();
            }
        });

        // setting 헤빗 버튼을 누르면 타임 다이어리 팝업창 띄우기
        setting_habit_button = findViewById(R.id.setting_habit_button);
        setting_habit_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SetTime customDialog = new SetTime(Setting.this,3);
                customDialog.show();
            }
        });

        // setting 켈린더 버튼을 누르면 타임 다이어리 팝업창 띄우기
        setting_calendar_button = findViewById(R.id.setting_calendar_button);
        setting_calendar_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SetTime customDialog = new SetTime(Setting.this,4);
                customDialog.show();
            }
        });

        // setting 타임 버튼을 누르면 타임 다이어리 팝업창 띄우기
        setting_time_button = findViewById(R.id.setting_time_button);
        setting_time_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SetTime customDialog = new SetTime(Setting.this,5);
                customDialog.show();
            }
        });

//        int uiOptions = getWindow().getDecorView().getSystemUiVisibility();
//        int newUiOptions = uiOptions;
//        boolean isImmersiveModeEnabled = ((uiOptions | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY) == uiOptions);
//        if (isImmersiveModeEnabled) {
//            Log.i("Is on?", "Turning immersive mode mode off. ");
//        } else {
//            Log.i("Is on?", "Turning immersive mode mode on.");
//        }
//        newUiOptions ^= View.SYSTEM_UI_FLAG_HIDE_NAVIGATION;
//        newUiOptions ^= View.SYSTEM_UI_FLAG_FULLSCREEN;
//        newUiOptions ^= View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;
//        getWindow().getDecorView().setSystemUiVisibility(newUiOptions);

        // 객체 생성
        SwitchCompat setting_alarm_switch = findViewById(R.id.setting_alarm_switch);
        SwitchCompat setting_todo_switch = findViewById(R.id.setting_todo_switch);
        SwitchCompat setting_habit_switch = findViewById(R.id.setting_habit_switch);
        SwitchCompat setting_calendar_switch = findViewById(R.id.setting_calendar_switch);
        SwitchCompat setting_time_switch = findViewById(R.id.setting_time_switch);
        SwitchCompat setting_color_switch = findViewById(R.id.setting_color_switch);

        TextView setting_todo = findViewById(R.id.setting_todo);
        TextView setting_habit = findViewById(R.id.setting_habit);
        TextView setting_calendar = findViewById(R.id.setting_calendar);
        TextView setting_time = findViewById(R.id.setting_time);
        TextView setting_color = findViewById(R.id.setting_color);

        // url 작성
        String url = "http://203.250.133.162:8080/settingAPI/get_setting_info/" + "123";
        ApiService settingApiService = new ApiService();
        settingApiService.getUrl(url);

        Alarm_Time = settingApiService.getValue("alarm_time");
        Alarm_Switch = settingApiService.getValue("alarm_switch");
        Todo_Time = settingApiService.getValue("dodo_time");
        Todo_Switch = settingApiService.getValue("todo_switch");
        Habit_Time = settingApiService.getValue("habit_time");
        Habit_Switch = settingApiService.getValue("habit_switch");
        Calendar_Time = settingApiService.getValue("calender_time");
        Calendar_Switch = settingApiService.getValue("calender_switch");
        Time_Time = settingApiService.getValue("time_time");
        Time_Switch = settingApiService.getValue("time_switch");
        Dark_Mode = settingApiService.getValue("dark_mode");

        if (Alarm_Switch.equals("1")) {
            setting_alarm_switch.setChecked(true); // 체크된 상태로 표시
            setting_alarm_button.setVisibility(View.VISIBLE);
            if(Todo_Switch.equals("1")) {
                setting_todo_switch.setVisibility(View.VISIBLE);
                setting_todo.setTextColor(Color.BLACK);
                setting_todo_button.setVisibility(View.VISIBLE);
            }else{
                setting_todo_switch.setVisibility(View.INVISIBLE);
                setting_todo.setTextColor(Color.GRAY);
                setting_todo_button.setVisibility(View.INVISIBLE);
            }
            if(Habit_Switch.equals("1")) {
                setting_habit_switch.setVisibility(View.VISIBLE);
                setting_habit.setTextColor(Color.BLACK);
                setting_habit_button.setVisibility(View.VISIBLE);
            }else{
                setting_habit_switch.setVisibility(View.INVISIBLE);
                setting_habit.setTextColor(Color.GRAY);
                setting_habit_button.setVisibility(View.INVISIBLE);
            }
            if(Calendar_Switch.equals("1")) {
                setting_calendar_switch.setVisibility(View.VISIBLE);
                setting_calendar.setTextColor(Color.BLACK);
                setting_calendar_button.setVisibility(View.VISIBLE);
            }else{
                setting_calendar_switch.setVisibility(View.INVISIBLE);
                setting_calendar.setTextColor(Color.GRAY);
                setting_calendar_button.setVisibility(View.INVISIBLE);
            }
            if(Time_Switch.equals("1")) {
                setting_time_switch.setVisibility(View.VISIBLE);
                setting_time.setTextColor(Color.BLACK);
                setting_time_button.setVisibility(View.VISIBLE);
            }else{
                setting_time_switch.setVisibility(View.INVISIBLE);
                setting_time.setTextColor(Color.GRAY);
                setting_time_button.setVisibility(View.INVISIBLE);
            }
        } else {
            setting_alarm_switch.setChecked(false); // 체크되지 않은 상태로 표시

            setting_todo_switch.setVisibility(View.INVISIBLE);
            setting_habit_switch.setVisibility(View.INVISIBLE);
            setting_calendar_switch.setVisibility(View.INVISIBLE);
            setting_time_switch.setVisibility(View.INVISIBLE);

            setting_todo.setTextColor(Color.GRAY);
            setting_habit.setTextColor(Color.GRAY);
            setting_calendar.setTextColor(Color.GRAY);
            setting_time.setTextColor(Color.GRAY);

            setting_todo_button.setVisibility(View.INVISIBLE);
            setting_habit_button.setVisibility(View.INVISIBLE);
            setting_calendar_button.setVisibility(View.INVISIBLE);
            setting_time_button.setVisibility(View.INVISIBLE);
            setting_alarm_button.setVisibility(View.INVISIBLE);
        }

        // setting 알림 스위치 누르면 다른 알람 비활성화 및 활성화
        setting_alarm_switch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                // 스위치의 상태가 변경되면 호출됩니다.
                if (isChecked) {
                    // 스위치가 On 상태인 경우 처리할 로직을 작성합니다.
                    setting_alarm_button.setVisibility(View.VISIBLE);
                    if(Todo_Switch.equals("1")) {
                        setting_todo_switch.setVisibility(View.VISIBLE);
                        setting_todo.setTextColor(Color.BLACK);
                        setting_todo_button.setVisibility(View.VISIBLE);
                    }else{
                        setting_todo_switch.setVisibility(View.INVISIBLE);
                        setting_todo.setTextColor(Color.GRAY);
                        setting_todo_button.setVisibility(View.INVISIBLE);
                    }
                    if(Habit_Switch.equals("1")) {
                        setting_habit_switch.setVisibility(View.VISIBLE);
                        setting_habit.setTextColor(Color.BLACK);
                        setting_habit_button.setVisibility(View.VISIBLE);
                    }else{
                        setting_habit_switch.setVisibility(View.INVISIBLE);
                        setting_habit.setTextColor(Color.GRAY);
                        setting_habit_button.setVisibility(View.INVISIBLE);
                    }
                    if(Calendar_Switch.equals("1")) {
                        setting_calendar_switch.setVisibility(View.VISIBLE);
                        setting_calendar.setTextColor(Color.BLACK);
                        setting_calendar_button.setVisibility(View.VISIBLE);
                    }else{
                        setting_calendar_switch.setVisibility(View.INVISIBLE);
                        setting_calendar.setTextColor(Color.GRAY);
                        setting_calendar_button.setVisibility(View.INVISIBLE);
                    }
                    if(Time_Switch.equals("1")) {
                        setting_time_switch.setVisibility(View.VISIBLE);
                        setting_time.setTextColor(Color.BLACK);
                        setting_time_button.setVisibility(View.VISIBLE);
                    }else{
                        setting_time_switch.setVisibility(View.INVISIBLE);
                        setting_time.setTextColor(Color.GRAY);
                        setting_time_button.setVisibility(View.INVISIBLE);
                    }
                    // Alarm_Switch 상태 변경
                    Alarm_Switch = "1";
                } else {
                    // 스위치가 Off 상태인 경우 처리할 로직을 작성합니다.
                    setting_todo_switch.setVisibility(View.INVISIBLE);
                    setting_habit_switch.setVisibility(View.INVISIBLE);
                    setting_calendar_switch.setVisibility(View.INVISIBLE);
                    setting_time_switch.setVisibility(View.INVISIBLE);

                    setting_todo.setTextColor(Color.GRAY);
                    setting_habit.setTextColor(Color.GRAY);
                    setting_calendar.setTextColor(Color.GRAY);
                    setting_time.setTextColor(Color.GRAY);

                    setting_todo_button.setVisibility(View.INVISIBLE);
                    setting_habit_button.setVisibility(View.INVISIBLE);
                    setting_calendar_button.setVisibility(View.INVISIBLE);
                    setting_time_button.setVisibility(View.INVISIBLE);
                    setting_alarm_button.setVisibility(View.INVISIBLE);
                    // Alarm_Switch 상태 변경
                    Alarm_Switch = "0";
                }
            }
        });

        // setting 투두 스위치 누르면 투두 알림 활성화 및 비활성화
        setting_todo_switch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                // 스위치의 상태가 변경되면 호출됩니다.
                if (isChecked) {
                    // 스위치가 On 상태인 경우 처리할 로직을 작성합니다.
                    setting_todo.setTextColor(Color.BLACK);
                    setting_todo_button.setVisibility(View.VISIBLE);
                    // Todo_Switch 상태 변경
                    Todo_Switch = "1";
                } else {
                    // 스위치가 Off 상태인 경우 처리할 로직을 작성합니다.
                    setting_todo_switch.setEnabled(true);
                    setting_todo.setTextColor(Color.GRAY);
                    setting_todo_button.setVisibility(View.INVISIBLE);
                    // Todo_Switch 상태 변경
                    Todo_Switch = "0";
                }
            }
        });
        // setting 헤빗 스위치 누르면 헤빗 알림 활성화 및 비활성화
        setting_habit_switch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                // 스위치의 상태가 변경되면 호출됩니다.
                if (isChecked) {
                    // 스위치가 On 상태인 경우 처리할 로직을 작성합니다.
                    setting_habit.setTextColor(Color.BLACK);
                    setting_habit_button.setVisibility(View.VISIBLE);
                    // Habit_Switch 상태 변경
                    Habit_Switch = "1";
                } else {
                    // 스위치가 Off 상태인 경우 처리할 로직을 작성합니다.
                    setting_habit_switch.setEnabled(true);
                    setting_habit.setTextColor(Color.GRAY);
                    setting_habit_button.setVisibility(View.INVISIBLE);
                    // Habit_Switch 상태 변경
                    Habit_Switch = "0";
                }
            }
        });
        // setting 켈린더 스위치 누르면 켈린더 알림 활성화 및 비활성화
        setting_calendar_switch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                // 스위치의 상태가 변경되면 호출됩니다.
                if (isChecked) {
                    // 스위치가 On 상태인 경우 처리할 로직을 작성합니다.
                    setting_calendar.setTextColor(Color.BLACK);
                    setting_calendar_button.setVisibility(View.VISIBLE);
                    // Calendar_Switch 상태 변경
                    Calendar_Switch = "1";
                } else {
                    // 스위치가 Off 상태인 경우 처리할 로직을 작성합니다.
                    setting_calendar_switch.setEnabled(true);
                    setting_calendar.setTextColor(Color.GRAY);
                    setting_calendar_button.setVisibility(View.INVISIBLE);
                    // Calendar_Switch 상태 변경
                    Calendar_Switch = "0";
                }
            }
        });
        // setting 타임 스위치 누르면 타임 알림 활성화 및 비활성화
        setting_time_switch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                // 스위치의 상태가 변경되면 호출됩니다.
                if (isChecked) {
                    // 스위치가 On 상태인 경우 처리할 로직을 작성합니다.
                    setting_time.setTextColor(Color.BLACK);
                    setting_time_button.setVisibility(View.VISIBLE);
                    // Time_Switch 상태 변경
                    Time_Switch = "1";
                } else {
                    // 스위치가 Off 상태인 경우 처리할 로직을 작성합니다.
                    setting_time_switch.setEnabled(true);
                    setting_time.setTextColor(Color.GRAY);
                    setting_time_button.setVisibility(View.INVISIBLE);
                    // Time_Switch 상태 변경
                    Time_Switch = "1";
                }
            }
        });
        // 캘린더 스타일 라이트 모드, 다크 모드
        setting_color_switch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                // 스위치의 상태가 변경되면 호출됩니다.
                if (isChecked) {
                    // 스위치가 On 상태인 경우 처리할 로직을 작성합니다.
                    setting_color.setText("라이트 모드");
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                    // 라이트 모드 or 다크 모드 변경
                    Dark_Mode = "1";
                } else {
                    // 스위치가 Off 상태인 경우 처리할 로직을 작성합니다.
                    setting_color.setText("다크 모드");
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                    // 라이트 모드 or 다크 모드 변경
                    Dark_Mode = "0";
                }
            }
        });
        // 전체 일정 삭제 버튼
        Setting_All_Del = findViewById(R.id.setting_all_del);
        Setting_All_Del.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDeleteDialog("전체 일정");
            }
        });

        // Todo 일정 삭제 버튼
        Setting_Todo_Del = findViewById(R.id.setting_todo_del);
        Setting_Todo_Del.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDeleteDialog("Todo 일정");
            }
        });
        // Habit 일정 삭제 버튼
        Setting_Habit_Del = findViewById(R.id.setting_habit_del);
        Setting_Habit_Del.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDeleteDialog("Habit 일정");
            }
        });
        // Calendar 일정 삭제 버튼
        Setting_Calendar_Del = findViewById(R.id.setting_calendar_del);
        Setting_Calendar_Del.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDeleteDialog("Calendar 일정");
            }
        });
        // Time tracker 일정 삭제 버튼
        Setting_Time_Del = findViewById(R.id.setting_time_del);
        Setting_Time_Del.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDeleteDialog("Time tracker 일정");
            }
        });
        // setting 부분 뒤로 가기 버튼 구현
        ImageButton Setting_back_button = findViewById(R.id.setting_back_button);
        Setting_back_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // url 작성
                String url = "http://203.250.133.162:8080/settingAPI/get_setting_info/" + "123" + "/" +Alarm_Time + "/" + Alarm_Switch + "/"
                        + Todo_Time + "/" + Todo_Switch + "/" + Habit_Time + "/" + Habit_Switch + "/" + Calendar_Time + "/" + Calendar_Switch + "/"
                        + Time_Time + "/" + Time_Switch;
                ApiService setting_update_ApiService = new ApiService();
                setting_update_ApiService.putUrl(url);
                Intent intent = new Intent(getApplicationContext(), Menu.class);
                startActivity(intent);
            }
        });
    }
    // 각 버튼 눌렀을 때 설정한 시간으로 시간 출력
    public void setValue(int code, int hour, int minute){
        this.hour = hour;
        this.minute = minute;

        String savedHour, savedMinute, Time_Value;

        ApiService TimeApiService = new ApiService();
        String url = "http://203.250.133.162:8080/settingAPI/get_setting_info/" + "123";
        TimeApiService.getUrl(url);

        switch(code){
            case 1:
                Time_Value = TimeApiService.getValue("alarm_time");

                // 시간과 분을 분리하여 저장합니다.
                savedHour = Time_Value.substring(0, 2);
                savedMinute = Time_Value.substring(2, 4);

                hour = Integer.parseInt(savedHour);
                minute = Integer.parseInt(savedMinute);

                setting_alarm_button.setText(hour+":"+minute);
                Alarm_Time = savedHour + savedMinute;
                break;
            case 2:
                Time_Value = TimeApiService.getValue("todo_time");

                // 시간과 분을 분리하여 저장합니다.
                savedHour = Time_Value.substring(0, 2);
                savedMinute = Time_Value.substring(2, 4);

                hour = Integer.parseInt(savedHour);
                minute = Integer.parseInt(savedMinute);

                setting_todo_button.setText(hour+":"+minute);
                Todo_Time = savedHour + savedMinute;
                break;
            case 3:
                Time_Value = TimeApiService.getValue("habit_time");

                // 시간과 분을 분리하여 저장합니다.
                savedHour = Time_Value.substring(0, 2);
                savedMinute = Time_Value.substring(2, 4);

                hour = Integer.parseInt(savedHour);
                minute = Integer.parseInt(savedMinute);

                setting_habit_button.setText(hour+":"+minute);
                Habit_Time = savedHour + savedMinute;
                break;
            case 4:
                Time_Value = TimeApiService.getValue("calendar_time");

                // 시간과 분을 분리하여 저장합니다.
                savedHour = Time_Value.substring(0, 2);
                savedMinute = Time_Value.substring(2, 4);

                hour = Integer.parseInt(savedHour);
                minute = Integer.parseInt(savedMinute);

                setting_calendar_button.setText(hour+":"+minute);
                Calendar_Time = savedHour + savedMinute;
                break;
            case 5:
                Time_Value = TimeApiService.getValue("time_time");

                // 시간과 분을 분리하여 저장합니다.
                savedHour = Time_Value.substring(0, 2);
                savedMinute = Time_Value.substring(2, 4);

                hour = Integer.parseInt(savedHour);
                minute = Integer.parseInt(savedMinute);

                setting_time_button.setText(hour+":"+minute);
                Time_Time = savedHour + savedMinute;
                break;
        }
    }

    private void showDeleteDialog(String scheduleType) {
        Dialog dialog = new Dialog(Setting.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.delete_dialog);

        Button cancelButton = dialog.findViewById(R.id.no_Btn);
        Button deleteButton = dialog.findViewById(R.id.delete_Btn);

        // url 작성
        ApiService deleteApiService = new ApiService();

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (scheduleType.equals("전체 일정")) {
                    // 전체 일정 삭제 메소드 호출
                    String url = "http://203.250.133.162:8080/settingAPI/delete_all_schedule/" + "123";
                    deleteApiService.deleteUrl(url);
                } else if (scheduleType.equals("Todo 일정")) {
                    // Todo 일정 삭제 메소드 호출
                    String url = "http://203.250.133.162:8080/settingAPI/delete_all_todo/" + "123";
                    deleteApiService.deleteUrl(url);
                }else if (scheduleType.equals("Habit 일정")) {
                    // Habit 일정 삭제 메소드 호출
                    String url = "http://203.250.133.162:8080/settingAPI/delete_all_habit/" + "123";
                    deleteApiService.deleteUrl(url);
                }else if (scheduleType.equals("Calendar 일정")) {
                    // Calendar 일정 삭제 메소드 호출
                    String url = "http://203.250.133.162:8080/settingAPI/delete_all_Calendar/" + "123";
                    deleteApiService.deleteUrl(url);
                }else if (scheduleType.equals("Time tracker 일정")) {
                    // Calendar 일정 삭제 메소드 호출
                    String url = "http://203.250.133.162:8080/settingAPI/delete_all_timeTracker/" + "123";
                    deleteApiService.deleteUrl(url);
                }
                dialog.dismiss();
            }
        });
        dialog.show();
    }
}