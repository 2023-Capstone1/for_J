package com.example.for_j;

import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.SwitchCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.DialogFragment;

import com.example.for_j.dialog.ColorPaletteDialog;
import com.example.for_j.dialog.DatePickerFragment;
import com.example.for_j.dialog.TimePickerFragment;

import java.util.Calendar;
import java.util.Locale;
import java.util.Objects;

public class CalSetDateNew extends AppCompatActivity implements DatePickerFragment.OnDateSelectedListener, TimePickerFragment.OnTimeSelectedListener{
    // Toast
    private Toast toast;

    // calendar 알림 변수
    private CalAlarm CalAlarm;

    // 날짜 저장 변수
    private final Calendar startSelectedDate = Calendar.getInstance();
    private final Calendar endSelectedDate = Calendar.getInstance();

    // 색 버튼
    private AppCompatButton CSDN_Color;

    // 타이틀
    private EditText CSDN_CalTitle;

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

    // 장소
    private EditText CSDN_LocationET;
    // 메모
    private EditText CSDN_MemoET;

    // 서버 통신 변수
    String login_id = null;
    String name = null;
    String color = null;
    int allDay = 1;
    String startDate = null;
    String startTime = "";
    String endDate = null;
    String endTime = "";
    String location = null;
    int alarm = 0;
    String memo = null;
    String TIME;
    int hour,minute;

    String setCalURL;
    String CalAlarmURL;
    ApiService setCalAPI;
    ApiService calApiService;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cal_set_date_new);

        IdSave idSave = (IdSave) getApplication();
        login_id = idSave.getUserId();

        CSDN_CalTitle = findViewById(R.id.CSDN_CalTitle);

        CalAlarm = new CalAlarm(getApplicationContext());

        // 색상 선택
        CSDN_Color = findViewById(R.id.CSDN_Color);
        Drawable btnColor;
        // 최초 색상 초기화
        btnColor = ContextCompat.getDrawable(CalSetDateNew.this, R.drawable.category_pink_false);
        CSDN_Color.setBackgroundDrawable(btnColor);
        color="pink";

        // 여기에서 컬러팔레트에서 얻어온 색깔로 바꾸기
        CSDN_Color.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 컬러팔레트 띄우기
                ColorPaletteDialog CPD = new ColorPaletteDialog(CalSetDateNew.this, new ColorPaletteDialog.ColorPaletteListener() {
                    @Override
                    public void getColorPaletteData(int Pcolor) {
                        Drawable btnColor;
//                        System.out.println(color);
                        switch (Pcolor) {
                            case 0:
                                color = "pink";
                                btnColor = ContextCompat.getDrawable(CalSetDateNew.this, R.drawable.category_pink_false);
                                CSDN_Color.setBackgroundDrawable(btnColor);
                                break;
                            case 1:
                                color = "crimson";
                                btnColor = ContextCompat.getDrawable(CalSetDateNew.this, R.drawable.category_crimson_false);
                                CSDN_Color.setBackgroundDrawable(btnColor);
                                break;
                            case 2:
                                color = "orange";
                                btnColor = ContextCompat.getDrawable(CalSetDateNew.this, R.drawable.category_orange_false);
                                CSDN_Color.setBackgroundDrawable(btnColor);
                                break;
                            case 3:
                                color = "yellow";
                                btnColor = ContextCompat.getDrawable(CalSetDateNew.this, R.drawable.category_yellow_false);
                                CSDN_Color.setBackgroundDrawable(btnColor);
                                break;
                            case 4:
                                color = "light_green";
                                btnColor = ContextCompat.getDrawable(CalSetDateNew.this, R.drawable.category_light_green_false);
                                CSDN_Color.setBackgroundDrawable(btnColor);
                                break;
                            case 5:
                                color = "turquoise";
                                btnColor = ContextCompat.getDrawable(CalSetDateNew.this, R.drawable.category_turquoise_false);
                                CSDN_Color.setBackgroundDrawable(btnColor);
                                break;
                            case 6:
                                color = "pastel_blue";
                                btnColor = ContextCompat.getDrawable(CalSetDateNew.this, R.drawable.category_pastel_blue_false);
                                CSDN_Color.setBackgroundDrawable(btnColor);
                                break;
                            case 7:
                                color = "pastel_purple";
                                btnColor = ContextCompat.getDrawable(CalSetDateNew.this, R.drawable.category_pastel_purple_false);
                                CSDN_Color.setBackgroundDrawable(btnColor);
                                break;
                        }
                    }
                });
                CPD.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
                CPD.setCanceledOnTouchOutside(true);
                CPD.setCancelable(true);
                CPD.show();
            }
        });

        // 하루 종일 스위치 xml 연동
        // 날짜 스위치
        SwitchCompat CSDN_AllDaySwitch = findViewById(R.id.CSDN_AllDaySwitch);
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
                switchNum = 0;

                // Pass the selected date to the DatePickerFragment
                // 현재 날짜 데이트 피커로 보내기
                DialogFragment CSDN_DateDialog = new DatePickerFragment(startSelectedDate, CalSetDateNew.this);
                CSDN_DateDialog.show(getSupportFragmentManager(), "datePicker");
            }
        });

        // 종료 날짜 버튼 클릭
        CSDN_ALlDayTrueEndDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switchNum = 1;

                // Pass the selected date to the DatePickerFragment
                // 현재 날짜 데이트 피커로 보내기
                DialogFragment CSDN_DateDialog = new DatePickerFragment(endSelectedDate, CalSetDateNew.this);
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
                    allDay = 1;
                }
                else{
                    allDay = 0;
                    // 하루 종일 off 레이아웃 나타내기
                    CSDN_AllDayTrue.setVisibility(View.GONE);
                    CSDN_AllDayFalse.setVisibility((View.VISIBLE));

                    // 시작 날짜 버튼 클릭
                    CSDN_AllDayFalseStartDate.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            switchNum = 0;

                            // Pass the selected date to the DatePickerFragment
                            // 현재 날짜 데이트 피커로 보내기
                            DialogFragment CSDN_DateDialog = new DatePickerFragment(startSelectedDate, CalSetDateNew.this);
                            CSDN_DateDialog.show(getSupportFragmentManager(), "datePicker");

                        }
                    });

                    // 시작 시간 버튼 클릭
                    CSDN_AllDayFalseStartTime.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            time = 0;

                            // 현재 시간 타임 피커로 보내기
                            DialogFragment HSDN_TimeDialog = new TimePickerFragment(startSelectedDate, CalSetDateNew.this);
                            HSDN_TimeDialog.show(getSupportFragmentManager(), "timePicker");

                        }
                    });

                    // 종료 날짜 버튼 클릭
                    CSDN_AllDayFalseEndDate.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            switchNum = 1;

                            // Pass the selected date to the DatePickerFragment
                            // 현재 날짜 데이트 피커로 보내기
                            DialogFragment CSDN_DateDialog = new DatePickerFragment(endSelectedDate, CalSetDateNew.this);
                            CSDN_DateDialog.show(getSupportFragmentManager(), "datePicker");

                        }
                    });

                    // 종료 시간 버튼 클릭
                    CSDN_AllDayFalseEndTime.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            time = 1;

                            // 현재 시간 타임 피커로 보내기
                            DialogFragment HSDN_TimeDialog = new TimePickerFragment(endSelectedDate, CalSetDateNew.this);
                            HSDN_TimeDialog.show(getSupportFragmentManager(), "timePicker");
                        }
                    });
                }
            }
        });

        CSDN_LocationET = findViewById(R.id.CSDN_LocationET);

        final String alarmOption[] = {"일정 시작시간", "10분 전", "1시간 전", "1일 전"};

        Spinner CSDN_SetAlarmSpinner = findViewById(R.id.CSDN_SetAlarmSpinner);

        // Create a custom ArrayAdapter
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, alarmOption) {
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                // Inflate the default layout for the Spinner item
                View view = super.getView(position, convertView, parent);

                // Get the TextView used to display the item
                TextView textView = view.findViewById(android.R.id.text1);

                // Set the desired text size for the TextView
                float desiredTextSize = 25;
                textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, desiredTextSize);


                return view;
            }
        };

        CSDN_SetAlarmSpinner.setAdapter(adapter);

        CSDN_SetAlarmSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                alarm = position;
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        CSDN_MemoET = findViewById(R.id.CSDN_MemoET);


        // 저장
        AppCompatButton CSDN_Save = findViewById(R.id.CSDN_Save);
        CSDN_Save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    if (CSDN_CalTitle.getText() != null) {
                        name = CSDN_CalTitle.getText().toString();
                    }
                } catch (NullPointerException e) {
                    name = "";
                }

                // 하루종일이 아니면
                if (allDay == 0){
                    //CSDN_AllDayFalseStartDate
                    try{
                        if (CSDN_AllDayFalseStartDate.getText() != null){
                            startDate = CSDN_AllDayFalseStartDate.getText().toString();
                        }
                    }catch (NullPointerException e){
                        startDate = "";
                    }


                    //CSDN_AllDayFalseStartTime
                    /*try{
                        if (CSDN_AllDayFalseStartTime.getText() != null){
                            int hour = startSelectedDate.get(Calendar.HOUR);
                            int minute = startSelectedDate.get(Calendar.MINUTE);
                            String timeServerFormat = String.format(Locale.getDefault(), "%02d:%02d", hour, minute);
                            startTime = timeServerFormat;
                        }
                    }catch (NullPointerException e){
                        startTime = "";
                    }*/


                    //CSDN_AllDayFalseEndDate
                    try{
                        if (CSDN_AllDayFalseEndDate.getText() != null){
                            endDate = CSDN_AllDayFalseEndDate.getText().toString();
                        }
                    }catch (NullPointerException e){
                        endDate = "";
                    }


                    //CSDN_AllDayFalseEndTime
                    /*try{
                        if (CSDN_AllDayFalseEndTime.getText() != null){
                            int hour = endSelectedDate.get(Calendar.HOUR);
                            int minute = endSelectedDate.get(Calendar.MINUTE);
                            String timeServerFormat = String.format(Locale.getDefault(), "%02d:%02d", hour, minute);
                            endTime = timeServerFormat;
                        }
                    }catch (NullPointerException e){
                        endTime = "";
                    }*/

                } else {    // 하루 종일 이면
                    //CSDN_AllDayTrueStartDate
                    try{
                        if (CSDN_AllDayTrueStartDate.getText() != null){
                            startDate = CSDN_AllDayTrueStartDate.getText().toString();
                        }
                    }catch (NullPointerException e){
                        startDate = "";
                    }


                    //CSDN_ALlDayTrueEndDate
                    try{
                        if (CSDN_ALlDayTrueEndDate.getText() != null){
                            endDate = CSDN_ALlDayTrueEndDate.getText().toString();
                        }
                    }catch (NullPointerException e){
                        endDate = "";
                    }

                    startTime = "";
                    endTime = "";
                }

                try{
                    if (CSDN_LocationET.getText() != null) {
                        location = CSDN_LocationET.getText().toString();
                    }else{
                        location = "";
                    }
                }catch (NullPointerException e){
                    location = "";
                }

                try{
                    if (CSDN_MemoET.getText() != null) {
                        memo = CSDN_MemoET.getText().toString();
                    }else{
                        memo = "";
                    }
                }catch (NullPointerException e){
                    memo = "";
                }

                // 종료 날짜가 시작 날짜보다 빠르면 저장 불가능
                int compare = endSelectedDate.compareTo(startSelectedDate);

                if (name.length() == 0) {
                    toast = Toast.makeText(CalSetDateNew.this, "해빗 타이틀을 입력하세요.", Toast.LENGTH_SHORT);
                    toast.show();
                } else if (startDate.length() == 0){
                    toast = Toast.makeText(CalSetDateNew.this, "시작 날짜를 선택해주세요.", Toast.LENGTH_SHORT);
                    toast.show();
                } else if (endDate.length() == 0){
                    toast = Toast.makeText(CalSetDateNew.this, "종료 날짜를 선택해주세요.", Toast.LENGTH_SHORT);
                    toast.show();
                } else if (allDay == 0 && startTime.length() == 0){
                    toast = Toast.makeText(CalSetDateNew.this, "시작 시각을 선택해주세요.", Toast.LENGTH_SHORT);
                    toast.show();
                } else if (allDay == 0 && endTime.length() == 0){
                    toast = Toast.makeText(CalSetDateNew.this, "종료 시각을 선택해주세요.", Toast.LENGTH_SHORT);
                    toast.show();
                } else if (compare < 0) {   // 종료 날짜가 시작 날짜보다 빠르면 저장 불가능
                    toast = Toast.makeText(CalSetDateNew.this, "종료 날짜/시간를 시작날짜 이후로 선택해주세요.", Toast.LENGTH_SHORT);
                    toast.show();
                } else{

                    if (startTime.length() == 0){
                        startTime = "null";
                    }
                    if (endTime.length() == 0){
                        endTime = "null";
                    }
                    if (location.length() == 0){
                        location = "null";
                    }
                    if (memo.length() == 0){
                        memo = "null";
                    }


                    setCalURL = "http://203.250.133.162:8080/calendarAPI/set_calendar/" + login_id + "/" + name + "/" + color + "/" +
                            allDay + "/" + startDate + "/" + startTime + "/" + endDate + "/" + endTime + "/" + location + "/" + alarm +
                            "/" +  memo;
                    setCalAPI = new ApiService();
                    setCalAPI.postUrl(setCalURL);


                    CalAlarmURL = "http://203.250.133.162:8080/calendarAPI/get_cal_Alarm/" + login_id + "/" + name + "/" + color + "/" +
                            allDay + "/" + startDate + "/" + startTime + "/" + endDate + "/" + endTime + "/" + location + "/" + alarm +
                            "/" +  memo;
                    calApiService = new ApiService();
                    calApiService.getUrl(CalAlarmURL);

                    TIME = calApiService.getValue("cal_startTime");

                    if (!Objects.equals(TIME, "null")) {
                        String[] splitHourMinute = TIME.split(":");
                        hour = Integer.parseInt(splitHourMinute[0]); // 12시간 형식으로 된 시간
                        minute = Integer.parseInt(splitHourMinute[1]);

                        String cal_alarm = calApiService.getValue("cal_alarm");
                        String cal_name = calApiService.getValue("cal_name");
                        String id = calApiService.getValue("cal_list_id");

                        Log.d("hour",String.valueOf(hour));
                        Log.d("minute",String.valueOf(minute));
                        Log.d("cal_alarm",cal_alarm);

                        Log.d("cal_name",cal_name);
                        CalAlarm.setAlarm(hour, minute, Integer.parseInt(cal_alarm), id, cal_name);
                        Log.d("success","success");
                    }
                    finish();




                    /*toast = Toast.makeText(CalSetDateNew.this, "서버", Toast.LENGTH_SHORT);
                    toast.show();
                    System.out.println("loginid:" + login_id);
                    System.out.println("name:" + name);
                    System.out.println("color:" + color);
                    System.out.println("allDay:" + allDay);
                    System.out.println("startDate:" + startDate);
                    System.out.println("startTime:" + startTime);
                    System.out.println("endDate:" + endDate);
                    System.out.println("endTime:" + endTime);
                    System.out.println("location:" + location);
                    System.out.println("alarm:" + alarm);
                    System.out.println("memo:" + memo);
*/

                }
            }
        });

        // 취소
        AppCompatButton CSDN_Cancle = findViewById(R.id.CSDN_Cancle);
        CSDN_Cancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });



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
                CSDN_AllDayFalseStartDate.setText(dateString);
                startDate = dateString;
                break;
            case 1:
                // 종료 날짜 버튼 날짜 입력
                CSDN_ALlDayTrueEndDate.setText(dateString);
                CSDN_AllDayFalseEndDate.setText(dateString);
                endDate = dateString;
                break;
        }
    }

    @Override
    public void onTimeSelected(int hour, int minute) {
        String timeServerFormat = String.format(Locale.getDefault(), "%02d:%02d", hour, minute);



        String t = "오전";
//        int hhour = hour;
        if (hour > 12){
//            hhour -= 12;
            hour -= 12;
            t = "오후";
        }

        String timeString = String.format(Locale.getDefault(), "%s %d:%02d", t, hour, minute);

        switch (time){
            case 0:
                CSDN_AllDayFalseStartTime.setText(timeString);
                startTime = timeServerFormat;

                startSelectedDate.set(Calendar.HOUR_OF_DAY, hour);
                startSelectedDate.set(Calendar.MINUTE, minute);

//                System.out.println(startSelectedDate.get(Calendar.HOUR_OF_DAY) + ":" + startSelectedDate.get(Calendar.MINUTE));
                break;
            case 1:
                CSDN_AllDayFalseEndTime.setText(timeString);
                endTime = timeServerFormat;

                endSelectedDate.set(Calendar.HOUR_OF_DAY, hour);
                endSelectedDate.set(Calendar.MINUTE, minute);
//                System.out.println(endSelectedDate.get(Calendar.HOUR_OF_DAY) + ":" + endSelectedDate.get(Calendar.MINUTE));
                break;
        }
    }
}