package com.example.for_j;

import static com.example.for_j.CalendarUtill.selectedDate;

import android.annotation.SuppressLint;
import android.content.Context;
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
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.ArrayList;
import java.util.Date;

public class TimeTrackerTimeTable extends AppCompatActivity {

    //상단바 관련 변수
    private ImageView BackButton;
    private TextView TimeTable_monthDateText;
    private ImageView Time_Calendar;
    private ImageView Pre_Date;
    private ImageView Next_Date;

    // 타임 테이블 형태
    private GridLayout gridLayout;
    private final int ROWS = 30;
    private final int COLS = 7;
    private String date;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.timetable);

        //getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new TimeFragment()).addToBackStack(null).commit();

        TimeTable_monthDateText = findViewById(R.id.monthDateText);
        Pre_Date = findViewById(R.id.back_pointer);
        Next_Date = findViewById(R.id.next_pointer);

        BackButton = findViewById(R.id.setting_back_button);
        BackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

//        TimeTable_monthDateText
        // 현재 날짜
        selectedDate = LocalDate.now();

        // 달력 화면 설정
        setMonthView();

        // 이전달 버튼 이벤트
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

        Intent intent = getIntent();
        if(intent != null) {
            date = intent.getStringExtra("date");
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

        for (int i = 0; i < 24; i++) {
            for (int j = 0; j < COLS; j++) {
                TextView textView = new TextView(this);
                textView.setGravity(Gravity.CENTER);

                GridLayout.LayoutParams params2 = new GridLayout.LayoutParams();
                params2.columnSpec = GridLayout.spec(j);
                params2.rowSpec = GridLayout.spec(i);
                params2.setGravity(Gravity.FILL);
                params2.width = width;
                params2.height = height;
                textView.setLayoutParams(params2);

                textView.setBackgroundResource(R.drawable.grid_background);
                gridLayout.addView(textView);

                textViews[i][j] = textView;
            }
        }

        for(int i = 0; i <24; i++) {
            if(i<=9) {
                textViews[i][0].setText("0" + i + ":00");}
            else  {
                textViews[i][0].setText(""+i + ":00");
            }
        }
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