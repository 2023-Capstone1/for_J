package com.example.for_j;

import static com.example.for_j.CalendarUtill.selectedDate;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class TimeTrackerTimeTable extends AppCompatActivity {

    //상단바 관련 변수
    private ImageView BackButton;
    private TextView TimeTable_monthDateText;
//    private ImageView Time_Calendar;
//    private ImageView Pre_Date;
//    private ImageView Next_Date;

    // 타임 테이블 형태
    private GridLayout gridLayout;
    private final int ROWS = 30;
    private final int COLS = 7;

    // 서버 관련 함수
    private String roundTimeURL;
    private ApiService roundTimeAPI;

    private String loginId;
    private String listId;

    private List<String> startTime;
    private List<String> endTime;



    @RequiresApi(api = Build.VERSION_CODES.O)
    @SuppressLint({"MissingInflatedId", "SetTextI18n"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.timetable);

        IdSave idSave = (IdSave) getApplicationContext();
        loginId = idSave.getUserId();

        Intent intent = getIntent();
        listId = intent.getStringExtra("listId");
        String color = intent.getStringExtra("color");

        //getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new TimeFragment()).addToBackStack(null).commit();

        TimeTable_monthDateText = findViewById(R.id.monthDateText);
//        Pre_Date = findViewById(R.id.back_pointer);
//        Next_Date = findViewById(R.id.next_pointer);

        BackButton = findViewById(R.id.setting_back_button);
        BackButton.setOnClickListener(view -> finish());


        String dateString = intent.getStringExtra("date");

        selectedDate = LocalDate.parse(dateString, DateTimeFormatter.ISO_LOCAL_DATE);

        // 달력 화면 설정
        setMonthView();

        /*// 이전달 버튼 이벤트
        Pre_Date.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                // 현재 월-1 변수에 담기
                CalendarUtill.selectedDate = CalendarUtill.selectedDate.minusDays(1);
                setMonthView();
            }
        });
        // 다음달 버튼 이벤트
        Next_Date.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                // 현재 월+1 변수에 담기
                CalendarUtill.selectedDate = CalendarUtill.selectedDate.plusDays(1);
                setMonthView();
            }
        });
*/


        // 서버에서 라운드된 타임오더 가지고 오기
        roundTimeURL = "http://203.250.133.162:8080/timeAPI/get_round_time/" + loginId + "/" + listId;
        roundTimeAPI = new ApiService();
        roundTimeAPI.getUrl(roundTimeURL);

        startTime = new ArrayList<>();
        endTime = new ArrayList<>();

        for (int i = 0; i < Integer.parseInt(roundTimeAPI.getValue("timeOrder_total")); i++) {
            startTime.add(roundTimeAPI.getValue("timeOrder_startTime" + (i * 2 + 1)));
            endTime.add(roundTimeAPI.getValue("timeOrder_endTime" + (i * 2 + 1)));
        }


        int[][] table = new int[ROWS][COLS];
/*        // 모든 요소를 false로 초기화
        for (int i = 0; i < ROWS; i++) {
            for (int j = 0; j < COLS; j++) {
                table[i][j] = 0;
            }
        }*/

        int sMinute;
        int eMinute;
        for (int i = 0; i < Integer.parseInt(roundTimeAPI.getValue("timeOrder_total")); i++) {
            // 시작 시간과 종료 시간을 분 단위로 변환
            sMinute = getMinute(startTime.get(i));
            eMinute = getMinute(endTime.get(i));
            // 시작 시간부터 종료 시간까지 반복하면서 timeTable 값을 true로 설정
            for (int j = sMinute; j <= eMinute; j += 10) {
                int hourIndex = j / 60;  // 시간 인덱스 계산
                int minuteIndex = (j % 60) / 10;  // 분 인덱스 계산
                table[hourIndex][minuteIndex + 1] = i + 1;
                System.out.println("hourIndex: " + hourIndex + "\tminuteIndex + 1: " + (minuteIndex + 1));
                System.out.println("i+1: " + (i+1));
            }
        }


        gridLayout = findViewById(R.id.gridLayout);

        TextView[][] textViews = new TextView[ROWS][COLS];

        DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
        int height = displayMetrics.heightPixels / ROWS;
        int width = displayMetrics.widthPixels / COLS;

        // 그리드 레이아웃의 높이 설정
        ViewGroup.LayoutParams params = gridLayout.getLayoutParams();
        params.height = height * ROWS;
        gridLayout.setLayoutParams(params);


        int[][] count = new int[24][COLS];
        Boolean[][] start = new Boolean[24][COLS];
        for (int i = 0; i < 24; i++) {
            for (int j = 0; j < COLS; j++) {
                count[i][j] = 0;
                start[i][j] = false;
            }
        }

        for (int i = 1; i < 24; i++) {
            for (int j = 1; j < COLS; j++) {
                if (table[i][j] > 0) {
                    if (!start[i][j]){
                        start[i][j] = true;
                    }
                    count[i][table[i][j]]++;
                }
            }
        }

        Boolean runFlag;
        for (int i = 0; i < 24; i++){
            runFlag = false;
            for (int j = 0; j < COLS; j++){
                TextView textView = new TextView(this);
                textView.setGravity(Gravity.CENTER);
                GridLayout.LayoutParams params2 = new GridLayout.LayoutParams();
                System.out.println("i: " + i + "\tj: "+ j);
                if (!start[i][j]){
                    params2.rowSpec = GridLayout.spec(i);
                    params2.columnSpec = GridLayout.spec(j);
                    params2.setGravity(Gravity.FILL);
                    params2.width = width;
                    params2.height = height;
                    textView.setLayoutParams(params2);

                    textView.setBackgroundResource(R.drawable.grid_background);
                    gridLayout.addView(textView);
                    textViews[i][j] = textView;
                }else{
                    if (!runFlag) {
                        params2.rowSpec = GridLayout.spec(i);
                        params2.columnSpec = GridLayout.spec(j, count[i][table[i][j]]);
                        params2.setGravity(Gravity.FILL);
                        params2.width = width;
                        params2.height = height;
                        textView.setLayoutParams(params2);

                        // 색깔 넣기
                        if (table[i][j] % 2 == 0) {
                            switch (color) {
                                case "pink":
                                    textView.setBackgroundResource(R.drawable.time_box_pink);
                                    break;
                                case "crimson":
                                    textView.setBackgroundResource(R.drawable.time_box_crimson);
                                    break;
                                case "orange":
                                    textView.setBackgroundResource(R.drawable.time_box_orange);
                                    break;
                                case "yellow":
                                    textView.setBackgroundResource(R.drawable.time_box_yellow);
                                    break;
                                case "light_green":
                                    textView.setBackgroundResource(R.drawable.time_box_light_green);
                                    break;
                                case "turquoise":
                                    textView.setBackgroundResource(R.drawable.time_box_turquoise);
                                    break;
                                case "pastel_blue":
                                    textView.setBackgroundResource(R.drawable.time_box_pastel_blue);
                                    break;
                                case "pastel_purple":
                                    textView.setBackgroundResource(R.drawable.time_box_pastel_purple);
                                    break;
                            }
                        } else {
                            switch (color) {
                                case "pink":
                                    textView.setBackgroundResource(R.drawable.time_box_lighter_pink);
                                    break;
                                case "crimson":
                                    textView.setBackgroundResource(R.drawable.time_box_lighter_crimson);
                                    break;
                                case "orange":
                                    textView.setBackgroundResource(R.drawable.time_box_lighter_orange);
                                    break;
                                case "yellow":
                                    textView.setBackgroundResource(R.drawable.time_box_lighter_yellow);
                                    break;
                                case "light_green":
                                    textView.setBackgroundResource(R.drawable.time_box_lighter_light_green);
                                    break;
                                case "turquoise":
                                    textView.setBackgroundResource(R.drawable.time_box_lighter_turquoise);
                                    break;
                                case "pastel_blue":
                                    textView.setBackgroundResource(R.drawable.time_box_lighter_pastel_blue);
                                    break;
                                case "pastel_purple":
                                    textView.setBackgroundResource(R.drawable.time_box_lighter_pastel_purple);
                                    break;
                            }
                        }

                        gridLayout.addView(textView);
                        textViews[i][j] = textView;

                        runFlag = true;
                    }
                }
            }
            if (i <= 9) {
                textViews[i][0].setText("0" + i + ":00");
            }else {
                textViews[i][0].setText("" + i + ":00");
            }
        }

    }


    public static int getMinute(String time) {
        String[] parts = time.split(":");
        int hour = Integer.parseInt(parts[0]);
        int minute = Integer.parseInt(parts[1]);
        return hour * 60 + minute;
    }


    @SuppressLint("NewApi")
    private String monthYearFromDate(LocalDate date) {
        @SuppressLint({"NewApi", "LocalSuppress"})
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM월 dd일");
        return date.format(formatter);
    }

    // 화면 설정
    @RequiresApi(api = Build.VERSION_CODES.O)
    private void setMonthView() {
        // 년월 텍스트뷰 세팅
        TimeTable_monthDateText.setText(monthYearFromDate(CalendarUtill.selectedDate));

    }

}