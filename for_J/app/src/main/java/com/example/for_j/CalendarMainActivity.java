// 캘린더 부분 메인 화면 activity_calendar_main.xml, CalendarMainActivity.java
package com.example.for_j;

import static com.example.for_j.CalendarUtill.selectedDate;

import android.annotation.SuppressLint;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.google.android.material.navigation.NavigationBarView;

public class CalendarMainActivity extends AppCompatActivity {

    // 프래그먼트(화면) 선언
    ToDoFragment CalendarMain_toDoFragment;
    HabitFragment CalendarMain_habitFragment;
    CalendarFragment CalendarMain_calendarFragment;
    TimeFragment CalendarMain_timeFragment;

    @SuppressLint("NonConstantResourceId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar_main);

        CalendarMain_toDoFragment = new ToDoFragment();
        CalendarMain_habitFragment = new HabitFragment();
        CalendarMain_calendarFragment = new CalendarFragment();
        CalendarMain_timeFragment = new TimeFragment();
//        CalendarMain_halfCalendarFragment = new HalfCalendarFragment();

        // 프래그먼트 초기화면 구성 - 초기화면은 CalendarFragment로 설정
        getSupportFragmentManager().beginTransaction().replace(R.id.containers, CalendarMain_calendarFragment).commit();
//        getSupportFragmentManager().beginTransaction().replace(R.id.containers, CalendarMain_halfCalendarFragment).commit();

        // bottom_navigation_view 구현
        @SuppressLint({"MissingInflatedId", "LocalSuppress"}) NavigationBarView navigationBarView = findViewById(R.id.bottom_navigationView);
        navigationBarView.setOnItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.todo:
                    getSupportFragmentManager().beginTransaction().replace(R.id.containers, CalendarMain_toDoFragment).commit();
                    return true;
                case R.id.habit:
                    getSupportFragmentManager().beginTransaction().replace(R.id.containers, CalendarMain_habitFragment).commit();
                    return true;
                case R.id.calendar:
                    getSupportFragmentManager().beginTransaction().replace(R.id.containers, CalendarMain_calendarFragment).commit();
//                        getSupportFragmentManager().beginTransaction().replace(R.id.containers, CalendarMain_halfCalendarFragment).commit();
                    return true;
                case R.id.time:
                    getSupportFragmentManager().beginTransaction().replace(R.id.containers, CalendarMain_timeFragment).commit();
                    return true;
            }

            return false;
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void onBackPressed() {
        Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.containers);
        if (fragment instanceof CalendarFragment) {
            ((CalendarFragment) fragment).updateSelectedDate(selectedDate);
        }
        super.onBackPressed();
    }

}