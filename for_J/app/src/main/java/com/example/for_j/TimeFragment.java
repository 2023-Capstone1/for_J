package com.example.for_j;

import static com.example.for_j.CalendarUtill.selectedDate;

import android.annotation.SuppressLint;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class TimeFragment extends Fragment {

    // 달력 관련 변수
    TextView TimeFragment_monthYearText; // 년월 텍스트뷰
    RecyclerView TimeFragment_recyclerView; // RecyclerView 객체 생성
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // 타임 프래그먼트 뷰 생성
        View timeView = inflater.inflate(R.layout.fragment_time, container, false);

        // 초기화
        TimeFragment_monthYearText = timeView.findViewById(R.id.monthYearText);
        TimeFragment_recyclerView = timeView.findViewById(R.id.weekRecyclerView); // 리사이클러뷰 초기화

        // 현재 날짜
        selectedDate = LocalDate.now();

        // 화면 설정
        ArrayList<LocalDate> weekDaysList = new ArrayList<>();
        for (int i = 0; i < 7; i++) {
            weekDaysList.add(selectedDate.plusDays(i));
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
    @SuppressLint("NewApi")
    private String monthYearFromDate(LocalDate date) {
        @SuppressLint({"NewApi", "LocalSuppress"}) DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy년 MM월");
        return date.format(formatter);
    }

    // 화면 설정
    @RequiresApi(api = Build.VERSION_CODES.O)
    private void setMonthView() {
        // 년월 텍스트뷰 세팅
        TimeFragment_monthYearText.setText(monthYearFromDate(CalendarUtill.selectedDate));
    }
}