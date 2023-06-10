package com.example.for_j;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.fonts.Font;
import android.graphics.fonts.FontFamily;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.SwitchCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.DialogFragment;

import com.example.for_j.dialog.ColorPaletteDialog;
import com.example.for_j.dialog.DatePickerFragment;
import com.example.for_j.dialog.RepeatCycle;
import com.example.for_j.dialog.TimePickerFragment;

import java.util.Calendar;
import java.util.Locale;
import java.util.Objects;


public class HabitSetDateNew extends AppCompatActivity implements DatePickerFragment.OnDateSelectedListener, TimePickerFragment.OnTimeSelectedListener {
    // Toast
    private Toast toast;

    // habit 알림 변수
    private HibitAlarm habitAlarm;

    // 날짜 저장 변수
    private Calendar startSelectedDate = Calendar.getInstance();
    private Calendar endSelectedDate = Calendar.getInstance();
    private final Calendar currentSelectedDate = Calendar.getInstance();

    // 색 버튼
    private AppCompatButton HSDN_Color;

    // 제목
    private EditText HSDN_Title;

    // 시작 날짜
    //private SwitchCompat HSDN_StartDateSwitch;
    private AppCompatButton HSDN_StartDateBtn;
    private int dateCheck;
    // 종료 날짜
    //private SwitchCompat HSDN_EndDateSwitch;
    private AppCompatButton HSDN_EndDateBtn;

    // 알림1234
    private SwitchCompat HSDN_AlarmSwitch;
    private AppCompatButton HSDN_AlarmBtn;

    // 반복 주기
    private AppCompatButton HSDN_RepeatBtn;

    // nfc
    private AppCompatButton HSDN_NFCBtn;

    // 취소
    private AppCompatButton HSDN_Cancle;
    // 저장
    private AppCompatButton HSDN_Save;

    // habit 스키마
    String SloginId = null;
    String Sname = null;
    String Stoday = null;
    String SstartDate = null;
    String SendDate = null;
    int SalarmSwitch = 0;
    String Salarm = null;
    String SrepeatDay = null;
    int SrepeatN = 0;
    String Shabit_color = null;
    String Shabit_nfc = "";
    int Shabit_state = 0;
    int hour, minute;

    boolean isRepeatNull = true;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_habit_set_date_new);

        HSDN_Title = findViewById(R.id.HSDN_HabitTitle);

        IdSave idSave = (IdSave) getApplication();
        SloginId = idSave.getUserId();

        habitAlarm = new HibitAlarm(getApplicationContext());

        // 색상 선택
        HSDN_Color = findViewById(R.id.HSDN_Color);
        Drawable btnColor;
        // 최초 색상 초기화
        btnColor = ContextCompat.getDrawable(HabitSetDateNew.this, R.drawable.category_pink_false);
        HSDN_Color.setBackgroundDrawable(btnColor);
        Shabit_color="pink";

        // 여기에서 컬러팔레트에서 얻어온 색깔로 바꾸기
        HSDN_Color.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 컬러팔레트 띄우기
                ColorPaletteDialog CPD = new ColorPaletteDialog(HabitSetDateNew.this, new ColorPaletteDialog.ColorPaletteListener() {
                    @Override
                    public void getColorPaletteData(int color) {
                        Drawable btnColor;
//                        System.out.println(color);
                        switch (color) {
                            case 0:
                                Shabit_color = "pink";
                                btnColor = ContextCompat.getDrawable(HabitSetDateNew.this, R.drawable.category_pink_false);
                                HSDN_Color.setBackgroundDrawable(btnColor);
                                break;
                            case 1:
                                Shabit_color = "crimson";
                                btnColor = ContextCompat.getDrawable(HabitSetDateNew.this, R.drawable.category_crimson_false);
                                HSDN_Color.setBackgroundDrawable(btnColor);
                                break;
                            case 2:
                                Shabit_color = "orange";
                                btnColor = ContextCompat.getDrawable(HabitSetDateNew.this, R.drawable.category_orange_false);
                                HSDN_Color.setBackgroundDrawable(btnColor);
                                break;
                            case 3:
                                Shabit_color = "yellow";
                                btnColor = ContextCompat.getDrawable(HabitSetDateNew.this, R.drawable.category_yellow_false);
                                HSDN_Color.setBackgroundDrawable(btnColor);
                                break;
                            case 4:
                                Shabit_color = "light_green";
                                btnColor = ContextCompat.getDrawable(HabitSetDateNew.this, R.drawable.category_light_green_false);
                                HSDN_Color.setBackgroundDrawable(btnColor);
                                break;
                            case 5:
                                Shabit_color = "turquoise";
                                btnColor = ContextCompat.getDrawable(HabitSetDateNew.this, R.drawable.category_turquoise_false);
                                HSDN_Color.setBackgroundDrawable(btnColor);
                                break;
                            case 6:
                                Shabit_color = "pastel_blue";
                                btnColor = ContextCompat.getDrawable(HabitSetDateNew.this, R.drawable.category_pastel_blue_false);
                                HSDN_Color.setBackgroundDrawable(btnColor);
                                break;
                            case 7:
                                Shabit_color = "pastel_purple";
                                btnColor = ContextCompat.getDrawable(HabitSetDateNew.this, R.drawable.category_pastel_purple_false);
                                HSDN_Color.setBackgroundDrawable(btnColor);
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


        // 시작 날짜 버튼 xml 연동
        HSDN_StartDateBtn = findViewById(R.id.HSDN_StartDateBtn);


        HSDN_StartDateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dateCheck = 0;
                // 현재 날짜 데이트 피커로 보내기
                DialogFragment HSDN_DateDialog = new DatePickerFragment(startSelectedDate, HabitSetDateNew.this);
                HSDN_DateDialog.show(getSupportFragmentManager(), "datePicker");
            }
        });


        // 종료 날짜 버튼 xml 연동
        HSDN_EndDateBtn = findViewById(R.id.HSDN_EndDateBtn);

        // 날짜 버튼 클릭 (다이얼로그 띄우기)
        HSDN_EndDateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dateCheck = 1;
                // 현재 날짜 데이트 피커로 보내기
                DialogFragment HSDN_DateDialog = new DatePickerFragment(endSelectedDate, HabitSetDateNew.this);
                HSDN_DateDialog.show(getSupportFragmentManager(), "datePicker");

            }
        });


        // 알림 날짜 스위치, 버튼 xml 연동
        HSDN_AlarmSwitch = findViewById(R.id.HSDN_AlarmSwitch);
        HSDN_AlarmBtn = findViewById(R.id.HSDN_AlarmBtn);

        // 스위치 온오프
        HSDN_AlarmSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    // 시간 선택 할 수 있는 버튼 나타내기
                    HSDN_AlarmBtn.setVisibility(View.VISIBLE);
                    SalarmSwitch = 1;

                    // 시간 버튼 클릭 (다이얼로그 띄우기)
                    HSDN_AlarmBtn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            // 현재 시간 타임 피커로 보내기
                            DialogFragment HSDN_TimeDialog = new TimePickerFragment(currentSelectedDate, HabitSetDateNew.this);
                            HSDN_TimeDialog.show(getSupportFragmentManager(), "timePicker");

                        }
                    });
                } else {
                    // 시간 선택 할 수 있는 버튼 숨기기
                    HSDN_AlarmBtn.setVisibility(View.GONE);
                    SalarmSwitch = 0;
                }
            }
        });


        HSDN_RepeatBtn = findViewById(R.id.HSDN_RepeatBtn);

        // 반복 주기 버튼 클릭
        HSDN_RepeatBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // pickCategory 띄우기
                RepeatCycle RCD = new RepeatCycle(new RepeatCycle.RepeatDialogListener() {
                    @SuppressLint("SetTextI18n")
                    @Override
                    public void getRepeatData(String dayofWeek, int repeatN, boolean isWeekClick) {
                        if (isWeekClick) {
                            HSDN_RepeatBtn.setText(dayofWeek);
                            SrepeatDay = dayofWeek;
                            SrepeatN = 0;
                        } else {
                            String tmp = Integer.toString(repeatN) + "회";
                            HSDN_RepeatBtn.setText(tmp);
                            SrepeatN = repeatN;
                            SrepeatDay = null;
                        }
                    }
                });
                RCD.show(getSupportFragmentManager(), "RepeatCycle");
            }
        });


        // nfc 인텐트 연결
        HSDN_NFCBtn = findViewById(R.id.HSDN_NFCBtn);
        HSDN_NFCBtn.setOnClickListener(v -> {
            Intent registerNFCIntent = new Intent(this, HabitRegisterNFC.class);
            registerNFCResultLauncher.launch(registerNFCIntent);
        });

        // rNFC에서 nfc 카드값 가져오기!!!!


        // 저장 버튼
        HSDN_Save = findViewById(R.id.HSDN_Save);
        HSDN_Save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                try {
                    if (HSDN_Title.getText() != null) {
                        Sname = HSDN_Title.getText().toString();
                    }
                } catch (NullPointerException e) {
                    Sname = "";
//                    System.out.println("제목 null exception");
                    toast = Toast.makeText(HabitSetDateNew.this, "해빗 타이틀을 입력하세요.", Toast.LENGTH_SHORT);
                    toast.show();
                }

                try {
                    if (HSDN_StartDateBtn.getText() != null) {
                        SstartDate = HSDN_StartDateBtn.getText().toString();
                    }
                } catch (NullPointerException e) {
                    SstartDate = "";
//                    System.out.println("시작날짜 null exception");
//                    toast = Toast.makeText(HabitSetDateNew.this, "해빗 타이틀을 입력하세요.", Toast.LENGTH_SHORT);
//                    toast.show();
                }

                // 종료 날짜
                try {
                    if (HSDN_EndDateBtn.getText() != null) {
                        SendDate = HSDN_EndDateBtn.getText().toString();
                    }
                } catch (NullPointerException e) {
                    SendDate = "";
//                    System.out.println("종료날짜 null exception");
//                    toast = Toast.makeText(HabitSetDateNew.this, "종료 날짜를 선택해주세요.", Toast.LENGTH_SHORT);
//                    toast.show();
                }

                // 해빗 알람 스위치
                if (HSDN_AlarmSwitch.isChecked()) {
                    SalarmSwitch = 1;
                    // 해빗 알람 시간
                    try {
                        if (HSDN_AlarmBtn.getText() != null) {
                            Salarm = HSDN_AlarmBtn.getText().toString();
                            String[] splitTime = Salarm.split(" ");
                            String timeIndicator = splitTime[0]; // "오전" 또는 "오후"

                            // 시간과 분을 분리
                            String[] splitHourMinute = splitTime[1].split(":");
                            int originalHour = Integer.parseInt(splitHourMinute[0]); // 12시간 형식으로 된 시간
                            int originalMinute = Integer.parseInt(splitHourMinute[1]);

                            // 오전인 경우
                            if (timeIndicator.equals("오전")) {
                                // 시간과 분 그대로 저장
                                hour = originalHour;
                                minute = originalMinute;
                            } else { // 오후인 경우
                                // 시간에 12를 더하여 24시간 형식으로 변환
                                hour = originalHour + 12;
                                minute = originalMinute;
                            }
                        }
                    } catch (NullPointerException e) {
                        Salarm = "";
//                        System.out.println("알람 시간 null exception");
//                    toast = Toast.makeText(HabitSetDateNew.this, "종료 날짜를 선택해주세요.", Toast.LENGTH_SHORT);
//                    toast.show();
                    }
                } else {
                    SalarmSwitch = 0;
                    Salarm = "none";
                }

                // 해빗 반복 요일
                // 해빗 반복 n회
                try {
                    if (SrepeatDay == null && SrepeatN == 0) {
                        SrepeatDay = "";
                        isRepeatNull = true;
//                        System.out.println("둘다 널임 (" + isRepeatNull + ") 횟수: " + SrepeatN + " SrepeatDay: " + SrepeatDay);
                    } else if (SrepeatDay == null && SrepeatN > 0) {
                        SrepeatDay = "none";
                        isRepeatNull = false;
//                        System.out.println("요일만 널임(" + isRepeatNull + ") 횟수: " + SrepeatN + " SrepeatDay: " + SrepeatDay);
                    } else if (SrepeatDay.length() == 0 && SrepeatN > 0) {
                        SrepeatDay = "none";
                        isRepeatNull = false;
//                        System.out.println("요일이 값없음(" + isRepeatNull + ") 횟수: " + SrepeatN + " SrepeatDay: " + SrepeatDay);
                    } else if (SrepeatDay.length() != 0 && SrepeatN == 0) {
                        isRepeatNull = false;
//                        System.out.println("횟수가 값없음(" + isRepeatNull + ") 횟수: " + SrepeatN + " SrepeatDay: " + SrepeatDay);
                    }

                } catch (NullPointerException e) {
                    if (SrepeatN > 0) {
                        SrepeatDay = "none";
                        isRepeatNull = false;
//                        System.out.println("반복 요일이 널임 (" + isRepeatNull + ") 횟수: " + SrepeatN + " SrepeatDay: " + SrepeatDay);
                    } else {
                        SrepeatDay = "";
//                        System.out.println("반복 둘 다 선택 안됨(반복 요일 null exception)");
                        isRepeatNull = true;
//                        System.out.println("둘다 널임 (" + isRepeatNull + ") 횟수: " + SrepeatN + " SrepeatDay: " + SrepeatDay);
                    }
//                    toast = Toast.makeText(HabitSetDateNew.this, "종료 날짜를 선택해주세요.", Toast.LENGTH_SHORT);
//                    toast.show();
                }


                // 해빗 상태
                Shabit_state = 0;


                // 종료 날짜가 시작 날짜보다 빠르면 저장 불가능
                int compare = endSelectedDate.compareTo(startSelectedDate);
                int year, month, day;
//                System.out.println(isRepeatNull);
                if (Sname.length() == 0) {
                    toast = Toast.makeText(HabitSetDateNew.this, "해빗 타이틀을 입력하세요.", Toast.LENGTH_SHORT);
                    toast.show();
                } else if (SstartDate.length() == 0) {
                    toast = Toast.makeText(HabitSetDateNew.this, "시작 날짜를 선택해주세요.", Toast.LENGTH_SHORT);
                    toast.show();
                } else if (SendDate.length() == 0) {
                    toast = Toast.makeText(HabitSetDateNew.this, "종료 날짜를 선택해주세요.", Toast.LENGTH_SHORT);
                    toast.show();
                } else if (compare < 0) {   // 종료 날짜가 시작 날짜보다 빠르면 저장 불가능
                    toast = Toast.makeText(HabitSetDateNew.this, "종료 날짜를 시작날짜 이후로 선택해주세요.", Toast.LENGTH_SHORT);
                    toast.show();
                } else if (SalarmSwitch > 0 && Salarm.length() == 0) {
                    toast = Toast.makeText(HabitSetDateNew.this, "알람 시간을 입력해주세요.", Toast.LENGTH_SHORT);
                    toast.show();
                } else if (isRepeatNull) {
                    toast = Toast.makeText(HabitSetDateNew.this, "반복 주기를 선택해주세요.", Toast.LENGTH_SHORT);
                    toast.show();
                } else if (Shabit_nfc.length() == 0) {
                    toast = Toast.makeText(HabitSetDateNew.this, "nfc를 등록해주세요.", Toast.LENGTH_SHORT);
                    toast.show();
                } else {
                    // 오늘 날짜 반복해서 url 만들기
                    ApiService habitApiService = new ApiService();
                    ApiService AlarmApiService = new ApiService();
                    int success = 0;
//                    System.out.println("횟수: " + SrepeatN + " SrepeatDay: " + SrepeatDay);

                    if (SrepeatN > 0) { // 반복 횟수
                        String url = "http://203.250.133.162:8080/habitAPI/set_habit/" + SloginId + "/" + Sname + "/" + SstartDate + "/"
                                + SendDate + "/" + SalarmSwitch + "/" + Salarm + "/" + SrepeatDay + "/" + SrepeatN + "/" + Shabit_color + "/"
                                + Shabit_nfc + "/" + Shabit_state;
                        Log.d("TAG", url);
                        habitApiService.postUrl(url);
                        if (habitApiService.getStatus() == 200) {
                            success += 1;
                        }

                        if (!Objects.equals(Salarm, "none")) {
                            String Alarm_url = "http://203.250.133.162:8080/habitAPI/get_habit_Alarm/" + SloginId + "/" + Sname + "/" + SstartDate + "/"
                                    + SendDate + "/" + SalarmSwitch + "/" + Salarm + "/" + SrepeatDay + "/" + SrepeatN + "/" + Shabit_color + "/"
                                    + Shabit_nfc + "/" + Shabit_state;
                            AlarmApiService.getUrl(Alarm_url);


                            // 알림 설정
                            habitAlarm.setAlarm(hour, minute, AlarmApiService.getValue("habit_list_id"), Sname);
                        }
                    } else {    // 반복 날짜
                        boolean[] dayOfWeek = new boolean[7];
                        // Calendar.DAY_OF_WEEK 일요일 1, 월요일 2, 화요일 3, 수요일 4, 목요일 5, 금요일 6, 토요일 7
                        // dayOfWeek는 일요일이 0
                        for (int i = 0; i < SrepeatDay.length(); i++) {
                            char character = SrepeatDay.charAt(i);
                            switch (character) {
                                case '일':
                                    dayOfWeek[0] = true;
                                    break;
                                case '월':
                                    dayOfWeek[1] = true;
                                    break;
                                case '화':
                                    dayOfWeek[2] = true;
                                    break;
                                case '수':
                                    dayOfWeek[3] = true;
                                    break;
                                case '목':
                                    dayOfWeek[4] = true;
                                    break;
                                case '금':
                                    dayOfWeek[5] = true;
                                    break;
                                case '토':
                                    dayOfWeek[6] = true;
                                    break;

                            }
                        }

                        String url = "http://203.250.133.162:8080/habitAPI/set_habit/" + SloginId + "/" + Sname + "/" + SstartDate + "/"
                                + SendDate + "/" + SalarmSwitch + "/" + Salarm + "/" + SrepeatDay + "/" + SrepeatN + "/" + Shabit_color + "/"
                                + Shabit_nfc + "/" + Shabit_state;
                        Log.d("TAG", url);
                        habitApiService.postUrl(url);
                        if (habitApiService.getStatus() == 200) {
                            success += 1;
                        }
                        String Alarm_url = "http://203.250.133.162:8080/habitAPI/get_habit_Alarm/" + SloginId + "/" + Sname + "/" + SstartDate + "/"
                                + SendDate + "/" + SalarmSwitch + "/" + Salarm + "/" + SrepeatDay + "/" + SrepeatN + "/" + Shabit_color + "/"
                                + Shabit_nfc + "/" + Shabit_state;
                        AlarmApiService.getUrl(Alarm_url);

                        // 알림 설정
                        habitAlarm.setAlarm(hour, minute, AlarmApiService.getValue("habit_list_id"),Sname);
                    }
                    finish();
                }
            }
        });

        // 취소 버튼
        HSDN_Cancle = findViewById(R.id.HSDN_Cancle);
        HSDN_Cancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        initBtn();
    }

    @SuppressLint("SetTextI18n")
    public void initBtn(){
        Calendar calendar = Calendar.getInstance();

        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);

        for(int i = 0; i< 2; i++){
            dateCheck = i;
            onDateSelected(year, month, day);
        }
        onTimeSelected(hour, minute);

        HSDN_RepeatBtn.setText("not selected");
    }

    @Override
    public void onDateSelected(int year, int month, int day) {
        // Update the button text
        // 선택된 날짜로 데이트 피커 업데이트
        String dateString = String.format(Locale.getDefault(), "%d-%02d-%02d", year, month + 1, day);

        switch (dateCheck) {
            case 0:
                // 시작 날짜 버튼 날짜 입력
                HSDN_StartDateBtn.setText(dateString);
                SstartDate = dateString;
                break;
            case 1:
                // 종료 날짜 버튼 날짜 입력
                HSDN_EndDateBtn.setText(dateString);
                SendDate = dateString;
                break;
        }
    }


    @Override
    public void onTimeSelected(int hour, int minute) {

        Salarm = String.format(Locale.getDefault(), "%d:%02d", hour, minute);
        String t = "오전";
        if (hour > 12) {
            hour -= 12;
            t = "오후";
        }

        String timeString = String.format(Locale.getDefault(), "%s %d:%02d", t, hour, minute);

        HSDN_AlarmBtn.setText(timeString);
    }

    private final ActivityResultLauncher<Intent> registerNFCResultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getResultCode() == RESULT_OK) {
                    Intent data = result.getData();
                    if (data != null) {
                        Shabit_nfc = data.getStringExtra("habit_nfc");
                        // Do something with habit_nfc value
                        Toast.makeText(this, "nfc value: " + Shabit_nfc, Toast.LENGTH_SHORT).show();
                        HSDN_NFCBtn.setText("NFC: " + Shabit_nfc);
                    }
                }
            }
    );
}