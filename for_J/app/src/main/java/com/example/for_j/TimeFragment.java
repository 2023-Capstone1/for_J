package com.example.for_j;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

public class TimeFragment extends Fragment {

    // 달력 관련 변수
    TextView TimeFragment_monthYearText; // 년월 텍스트뷰
    RecyclerView TimeFragment_recyclerView; // RecyclerView 객체 생성
    Calendar selectedDate; // 변경된 부분

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // 타임 프래그먼트 뷰 생성
        View timeView = inflater.inflate(R.layout.fragment_time, container, false);

        // 초기화
        TimeFragment_monthYearText = timeView.findViewById(R.id.monthYearText);
        TimeFragment_recyclerView = timeView.findViewById(R.id.weekRecyclerView); // 리사이클러뷰 초기화

        // 현재 날짜
        selectedDate = Calendar.getInstance(); // 변경된 부분

        // 화면 설정
        ArrayList<Calendar> weekDaysList = new ArrayList<>();
        for (int i = 0; i < 7; i++) {
            Calendar calendar = Calendar.getInstance();
            calendar.add(Calendar.DAY_OF_MONTH, i - 3); // 수요일로 맞추기 위해 3일 빼고 더함
            weekDaysList.add(calendar);
        }

        // 주간 캘린더 어댑터 설정
        WeeklyCalendarAdapter weekAdapter = new WeeklyCalendarAdapter(weekDaysList);
        TimeFragment_recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        TimeFragment_recyclerView.setAdapter(weekAdapter);

        // 달력 화면 설정
        setMonthView();

        // Inflate the layout for this fragment
        return timeView;
    }

    // 날짜 타입 설정
    private String monthYearFromDate(Calendar date) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy년 MM월", Locale.KOREA);
        return sdf.format(date.getTime());
    }

    // 화면 설정
    private void setMonthView() {
        // 년월 텍스트뷰 세팅
        TimeFragment_monthYearText.setText(monthYearFromDate(selectedDate));
    }
}