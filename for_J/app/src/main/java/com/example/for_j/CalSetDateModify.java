package com.example.for_j;

import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.LinearLayout;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.SwitchCompat;
import androidx.fragment.app.DialogFragment;

import com.example.for_j.dialog.DatePickerFragment;
import com.example.for_j.dialog.TimePickerFragment;

import java.util.Calendar;
import java.util.Locale;

public class CalSetDateModify extends AppCompatActivity implements DatePickerFragment.OnDateSelectedListener, TimePickerFragment.OnTimeSelectedListener{

    // 날짜 저장 변수
    private Calendar selectedDate = Calendar.getInstance();

    // 날짜 스위치
    private SwitchCompat CSDN_AllDaySwitch;
    private int switchNum = 0;

    // *하루종일 on* //
    private LinearLayout CSDN_AllDayTrue;
    // 시작 날짜
    private AppCompatButton CSDN_AllDayTrueStartDate;
    // 종료 날짜
    private AppCompatButton CSDN_ALlDayTrueEndDate;

    // *하루종일 off* //
    private int time = 0;
    private LinearLayout CSDN_AllDayFalse;
    // 시작 날짜
    private AppCompatButton CSDN_AllDayFalseStartDate;
    // 시작 시간
    private AppCompatButton CSDN_AllDayFalseStartTime;
    // 종료 날짜
    private AppCompatButton CSDN_AllDayFalseEndDate;
    // 종료 시간
    private AppCompatButton CSDN_AllDayFalseEndTime;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cal_set_date_new);
        ActionBar actionbar = getSupportActionBar();
        actionbar.setTitle(Html.fromHtml("<font color='#D9EAF5'>CapstoneNewEditWidget</font>"));  // @colors/blue_white랑 같은색

        // 하루 종일 스위치 xml 연동
        CSDN_AllDaySwitch = findViewById(R.id.CSDN_AllDaySwitch);
        // *하루 종일 on* //
        // 하루 종일 true 레이아웃
        CSDN_AllDayTrue = findViewById(R.id.CSDN_AllDayTrue);
        // 시작 날짜 버튼
        CSDN_AllDayTrueStartDate = findViewById(R.id.CSDN_AllDayTrueStartDate);
        // 종료 날짜 버튼
        CSDN_ALlDayTrueEndDate = findViewById(R.id.CSDN_ALlDayTrueEndDate);


        /////////// 스위치 안에 있을 경우 처음에 클릭 안돼서 밖으로 뺌 ////////////
        // 시작 날짜 버튼 클릭
        CSDN_AllDayTrueStartDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //
                int year = selectedDate.get(Calendar.YEAR);
                int month = selectedDate.get(Calendar.MONTH);
                int day = selectedDate.get(Calendar.DAY_OF_MONTH);
                switchNum = 0;

                // Pass the selected date to the DatePickerFragment
                // 현재 날짜 데이트 피커로 보내기
                DialogFragment CSDN_DateDialog = new DatePickerFragment(selectedDate, CalSetDateModify.this);
                CSDN_DateDialog.show(getSupportFragmentManager(), "datePicker");
            }
        });

        // 종료 날짜 버튼 클릭
        CSDN_ALlDayTrueEndDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //
                int year = selectedDate.get(Calendar.YEAR);
                int month = selectedDate.get(Calendar.MONTH);
                int day = selectedDate.get(Calendar.DAY_OF_MONTH);
                switchNum = 1;

                // Pass the selected date to the DatePickerFragment
                // 현재 날짜 데이트 피커로 보내기
                DialogFragment CSDN_DateDialog = new DatePickerFragment(selectedDate, CalSetDateModify.this);
                CSDN_DateDialog.show(getSupportFragmentManager(), "datePicker");
            }
        });
        //////////////////////////////////////////////////////////

        // *하루 종일 off* //
        // 하루 종일 false 레이아웃
        CSDN_AllDayFalse = findViewById(R.id.CSDN_AllDayFalse);
        // 시작 날짜 xml 연동
        CSDN_AllDayFalseStartDate = findViewById(R.id.CSDN_AllDayFalseStartDate);
        // 시작 시간 xml 연동
        CSDN_AllDayFalseStartTime = findViewById(R.id.CSDN_AllDayFalseStartTime);
        // 종료 날짜 xml 연동
        CSDN_AllDayFalseEndDate = findViewById(R.id.CSDN_AllDayFalseEndDate);
        // 종료 시간 xml 연동
        CSDN_AllDayFalseEndTime = findViewById(R.id.CSDN_AllDayFalseEndTime);

        // 스위치 온오프
        CSDN_AllDaySwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    // 하루 종일 on 레이아웃 나타내기
                    CSDN_AllDayTrue.setVisibility(View.VISIBLE);
                    CSDN_AllDayFalse.setVisibility((View.GONE));
                    // 위에 작동부 있음
                }
                else{
                    // 하루 종일 off 레이아웃 나타내기
                    CSDN_AllDayTrue.setVisibility(View.GONE);
                    CSDN_AllDayFalse.setVisibility((View.VISIBLE));

                    // 시작 날짜 버튼 클릭
                    CSDN_AllDayFalseStartDate.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            //
                            int year = selectedDate.get(Calendar.YEAR);
                            int month = selectedDate.get(Calendar.MONTH);
                            int day = selectedDate.get(Calendar.DAY_OF_MONTH);
                            switchNum = 3;

                            // Pass the selected date to the DatePickerFragment
                            // 현재 날짜 데이트 피커로 보내기
                            DialogFragment CSDN_DateDialog = new DatePickerFragment(selectedDate, CalSetDateModify.this);
                            CSDN_DateDialog.show(getSupportFragmentManager(), "datePicker");

                        }
                    });

                    // 시작 시간 버튼 클릭
                    CSDN_AllDayFalseStartTime.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            time = 0;
                            // 현재 시간 가져오기
                            int hour = selectedDate.get(Calendar.HOUR_OF_DAY);
                            int minute = selectedDate.get(Calendar.MINUTE);

                            // 현재 시간 타임 피커로 보내기
                            DialogFragment HSDN_TimeDialog = new TimePickerFragment(selectedDate, CalSetDateModify.this);
                            HSDN_TimeDialog.show(getSupportFragmentManager(), "timePicker");

                        }
                    });

                    // 종료 날짜 버튼 클릭
                    CSDN_AllDayFalseEndDate.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            //
                            int year = selectedDate.get(Calendar.YEAR);
                            int month = selectedDate.get(Calendar.MONTH);
                            int day = selectedDate.get(Calendar.DAY_OF_MONTH);
                            switchNum = 4;

                            // Pass the selected date to the DatePickerFragment
                            // 현재 날짜 데이트 피커로 보내기
                            DialogFragment CSDN_DateDialog = new DatePickerFragment(selectedDate, CalSetDateModify.this);
                            CSDN_DateDialog.show(getSupportFragmentManager(), "datePicker");

                        }
                    });

                    // 종료 시간 버튼 클릭
                    CSDN_AllDayFalseEndTime.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            time = 1;
                            // 종료 시간 디폴트: 시작시간 + 1H
                            // 현재 시간 가져오기
                            int hour = selectedDate.get(Calendar.HOUR_OF_DAY);
                            hour += 1;
                            int minute = selectedDate.get(Calendar.MINUTE);

                            // 현재 시간 타임 피커로 보내기
                            DialogFragment HSDN_TimeDialog = new TimePickerFragment(selectedDate, CalSetDateModify.this);
                            HSDN_TimeDialog.show(getSupportFragmentManager(), "timePicker");

                        }
                    });


                }
            }
        });





























        // 장소 지도...ㅋ

        // 알림.. 이거 다이얼로그를 만들었었나...?

        // 반복.. 민선이꺼 가져오기

        // 메모..
    }


    @Override
    public void onDateSelected(int year, int month, int day) {
        // Update the button text
        // 선택된 날짜로 데이트 피커 업데이트
        String dateString = String.format(Locale.getDefault(), "%d-%02d-%02d", year, month + 1, day);

        switch (switchNum){
            case 0:
                // 시작 날짜 버튼 날짜 입력
                CSDN_AllDayTrueStartDate.setText(dateString);
                break;
            case 1:
                // 종료 날짜 버튼 날짜 입력
                CSDN_ALlDayTrueEndDate.setText(dateString);
                break;
            case 3:
                CSDN_AllDayFalseStartDate.setText(dateString);
                break;
            case 4:
                CSDN_AllDayFalseEndDate.setText(dateString);
                break;
        }
    }

    @Override
    public void onTimeSelected(int hour, int minute) {

        String t = "오전";
        if (hour > 12){
            hour -= 12;
            t = "오후";
        }

        String timeString = String.format(Locale.getDefault(), "%s %d:%02d", t, hour, minute);

        switch (time){
            case 0:
                CSDN_AllDayFalseStartTime.setText(timeString);
                break;
            case 1:
                CSDN_AllDayFalseEndTime.setText(timeString);
                break;
        }
    }
}