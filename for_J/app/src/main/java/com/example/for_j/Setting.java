package com.example.for_j;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.SwitchCompat;

public class Setting extends AppCompatActivity {
    Button setting_alarm_button, setting_todo_button, setting_habit_button, setting_calendar_button, setting_time_button;
    int hour, minute;

    // setting 부분 뒤로 가기 버튼 구현
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        ImageButton Setting_back_button = findViewById(R.id.setting_back_button);
        Setting_back_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

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

        int uiOptions = getWindow().getDecorView().getSystemUiVisibility();
        int newUiOptions = uiOptions;
        boolean isImmersiveModeEnabled = ((uiOptions | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY) == uiOptions);
        if (isImmersiveModeEnabled) {
            Log.i("Is on?", "Turning immersive mode mode off. ");
        } else {
            Log.i("Is on?", "Turning immersive mode mode on.");
        }
        newUiOptions ^= View.SYSTEM_UI_FLAG_HIDE_NAVIGATION;
        newUiOptions ^= View.SYSTEM_UI_FLAG_FULLSCREEN;
        newUiOptions ^= View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;
        getWindow().getDecorView().setSystemUiVisibility(newUiOptions);

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

        // setting 알림 스위치 누르면 다른 알람 비활성화 및 활성화
        setting_alarm_switch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                // 스위치의 상태가 변경되면 호출됩니다.
                if (isChecked) {
                    // 스위치가 On 상태인 경우 처리할 로직을 작성합니다.
                    setting_todo_switch.setVisibility(View.VISIBLE);
                    setting_habit_switch.setVisibility(View.VISIBLE);
                    setting_calendar_switch.setVisibility(View.VISIBLE);
                    setting_time_switch.setVisibility(View.VISIBLE);

                    setting_todo.setTextColor(Color.BLACK);
                    setting_habit.setTextColor(Color.BLACK);
                    setting_calendar.setTextColor(Color.BLACK);
                    setting_time.setTextColor(Color.BLACK);

                    setting_alarm_button.setVisibility(View.VISIBLE);
                    setting_todo_button.setVisibility(View.VISIBLE);
                    setting_habit_button.setVisibility(View.VISIBLE);
                    setting_calendar_button.setVisibility(View.VISIBLE);
                    setting_time_button.setVisibility(View.VISIBLE);
                    setting_alarm_button.setVisibility(View.VISIBLE);
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
                } else {
                    // 스위치가 Off 상태인 경우 처리할 로직을 작성합니다.
                    setting_todo_switch.setEnabled(true);
                    setting_todo.setTextColor(Color.GRAY);
                    setting_todo_button.setVisibility(View.INVISIBLE);
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
                } else {
                    // 스위치가 Off 상태인 경우 처리할 로직을 작성합니다.
                    setting_habit_switch.setEnabled(true);
                    setting_habit.setTextColor(Color.GRAY);
                    setting_habit_button.setVisibility(View.INVISIBLE);
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
                } else {
                    // 스위치가 Off 상태인 경우 처리할 로직을 작성합니다.
                    setting_calendar_switch.setEnabled(true);
                    setting_calendar.setTextColor(Color.GRAY);
                    setting_calendar_button.setVisibility(View.INVISIBLE);
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
                } else {
                    // 스위치가 Off 상태인 경우 처리할 로직을 작성합니다.
                    setting_time_switch.setEnabled(true);
                    setting_time.setTextColor(Color.GRAY);
                    setting_time_button.setVisibility(View.INVISIBLE);
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
                } else {
                    // 스위치가 Off 상태인 경우 처리할 로직을 작성합니다.
                    setting_color.setText("다크 모드");
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                }
            }
        });
    }
    // 각 버튼 눌렀을 때 설정한 시간으로 시간 출력
    public void setValue(int code, int hour, int minute){
        this.hour = hour;
        this.minute = minute;

        switch(code){
            case 1:
                setting_alarm_button.setText(hour+":"+minute);
                break;
            case 2:
                setting_todo_button.setText(hour+":"+minute);
                break;
            case 3:
                setting_habit_button.setText(hour+":"+minute);
                break;
            case 4:
                setting_calendar_button.setText(hour+":"+minute);
                break;
            case 5:
                setting_time_button.setText(hour+":"+minute);
                break;
        }
    }
}