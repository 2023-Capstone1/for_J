// 캘린더 부분 메인 화면 activity_calendar_main.xml, CalendarMainActivity.java
package com.example.for_j;

import static com.example.for_j.CalendarUtill.selectedDate;

import android.annotation.SuppressLint;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;

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
    HalfCalendarFragment CalendarMain_halfCalendarFragment;

    static int clickFlag = 0;
    MenuItem menuItem;

    @SuppressLint("NonConstantResourceId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar_main);

        CalendarMain_toDoFragment = new ToDoFragment();
        CalendarMain_habitFragment = new HabitFragment();
        CalendarMain_calendarFragment = new CalendarFragment();
        CalendarMain_timeFragment = new TimeFragment();
        CalendarMain_halfCalendarFragment = new HalfCalendarFragment();


        // bottom_navigation_view 구현
        @SuppressLint({"MissingInflatedId", "LocalSuppress"}) NavigationBarView navigationBarView = findViewById(R.id.bottom_navigationView);

        // 프래그먼트 초기화면 구성 - 초기화면은 CalendarFragment로 설정
        switch (clickFlag){
            case 0: // 풀캘린더
                getSupportFragmentManager().beginTransaction().replace(R.id.containers, CalendarMain_calendarFragment).commit();
                menuItem = navigationBarView.getMenu().findItem(R.id.calendar);
                menuItem.setChecked(true);
//                System.out.println("\n\n\n\n\n\n!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!! clickFlage" + clickFlag);
                break;
            case 1: // 하프캘린더
                getSupportFragmentManager().beginTransaction().replace(R.id.containers, CalendarMain_halfCalendarFragment).commit();
//                System.out.println("\n\n\n\n\n\n!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!! clickFlage" + clickFlag);
                menuItem = navigationBarView.getMenu().findItem(R.id.calendar);
                menuItem.setChecked(true);
                break;
            case 2: // 투두
                getSupportFragmentManager().beginTransaction().replace(R.id.containers, CalendarMain_toDoFragment).commit();
//                System.out.println("\n\n\n\n\n\n!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!! clickFlage" + clickFlag);
                menuItem = navigationBarView.getMenu().findItem(R.id.todo);
                menuItem.setChecked(true);
                break;
            case 3: // 해빗
                getSupportFragmentManager().beginTransaction().replace(R.id.containers, CalendarMain_habitFragment).commit();
//                System.out.println("\n\n\n\n\n\n!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!! clickFlage" + clickFlag);
                menuItem = navigationBarView.getMenu().findItem(R.id.habit);
                menuItem.setChecked(true);
                break;
            case 4: // 타임
                getSupportFragmentManager().beginTransaction().replace(R.id.containers, CalendarMain_timeFragment).commit();
//                System.out.println("\n\n\n\n\n\n!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!! clickFlage" + clickFlag);
                menuItem = navigationBarView.getMenu().findItem(R.id.time);
                menuItem.setChecked(true);
                break;
        }


//        getSupportFragmentManager().beginTransaction().replace(R.id.containers, CalendarMain_calendarFragment).commit();
//        getSupportFragmentManager().beginTransaction().replace(R.id.containers, CalendarMain_halfCalendarFragment).commit();

        navigationBarView.setOnItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.todo:
                    clickFlag = 2;
//                    System.out.println("\n\n\n\n\n\n!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!! clickFlage" + clickFlag);
                    getSupportFragmentManager().beginTransaction().replace(R.id.containers, CalendarMain_toDoFragment).commit();
                    return true;
                case R.id.habit:
                    clickFlag = 3;
//                    System.out.println("\n\n\n\n\n\n!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!! clickFlage" + clickFlag);
                    getSupportFragmentManager().beginTransaction().replace(R.id.containers, CalendarMain_habitFragment).commit();
                    return true;
                case R.id.calendar:
                    clickFlag = 1;
//                    System.out.println("\n\n\n\n\n\n!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!! clickFlage" + clickFlag);
                    getSupportFragmentManager().beginTransaction().replace(R.id.containers, CalendarMain_calendarFragment).commit();
//                        getSupportFragmentManager().beginTransaction().replace(R.id.containers, CalendarMain_halfCalendarFragment).commit();
                    return true;
                case R.id.time:
                    clickFlag = 4;
//                    System.out.println("\n\n\n\n\n\n!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!! clickFlage" + clickFlag);
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