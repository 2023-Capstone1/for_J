// 하프캘린더 -> 풀캘린더에서 날짜 클릭 시 그날 하프캘린더 프래그먼트 보여주도록 수정부탁~!~!!~!
package com.example.for_j;

import static com.example.for_j.CalendarUtill.selectedDate;

import android.annotation.SuppressLint;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class HalfCalendarFragment extends Fragment {

    // 하프 캘린더 화면 액티비티
    TextView HalfFragment_monthYearText; // 년월 텍스트뷰
    RecyclerView HalfFragment_recyclerView; // RecyclerView 객체 생성
    ImageButton HalfFragment_prevBtn; // 이전달 이동 버튼
    ImageButton HalfFragment_nextBtn; // 다음달 이동 버튼

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View habitView = inflater.inflate(R.layout.fragment_half_calendar, container, false);

        // 초기화
        HalfFragment_monthYearText = habitView.findViewById(R.id.Calendar_MonthYearText);
        HalfFragment_prevBtn = habitView.findViewById(R.id.Calendar_PreBtn);
        HalfFragment_nextBtn = habitView.findViewById(R.id.Calendar_NextBtn);
        HalfFragment_recyclerView = habitView.findViewById(R.id.Calendar_RecyclerView);

        // 현재 날짜
        selectedDate = LocalDate.now();

        // 달력 화면 설정
        setMonthView();

        // 이전달 버튼 이벤트
        HalfFragment_prevBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                // 현재 월-1 변수에 담기
                CalendarUtill.selectedDate = CalendarUtill.selectedDate.minusMonths(1);
                setMonthView();
            }
        });
        // 다음달 버튼 이벤트
        HalfFragment_nextBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                // 현재 월+1 변수에 담기
                CalendarUtill.selectedDate = CalendarUtill.selectedDate.plusMonths(1);
                setMonthView();
            }
        });

        // Inflatethe layout for this fragment
        return habitView;
    }

    // 날짜 타입 설정
    // 년월 텍스트뷰
    @SuppressLint("NewApi")
    private String monthYearFromDate(LocalDate date) {
        @SuppressLint({"NewApi", "LocalSuppress"}) DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy년 MM월");
        return date.format(formatter);
    }
    // 리스트뷰 오른쪽 위에 있는 날짜 텍스트뷰
    @RequiresApi(api = Build.VERSION_CODES.O)
    private String dayFormat(LocalDate date) {
        DateTimeFormatter dayFormatter = DateTimeFormatter.ofPattern("dd");
        return date.format(dayFormatter);
    }

    // 화면 설정
    @RequiresApi(api = Build.VERSION_CODES.O)
    private void setMonthView() {
        // 년월 텍스트뷰 세팅
        HalfFragment_monthYearText.setText(monthYearFromDate(CalendarUtill.selectedDate));

        // calendar_cell 텍스트뷰 세팅
        ArrayList<LocalDate> dayList = daysInMonthArray(CalendarUtill.selectedDate);

        HalfCalendarAdapter halfAdapter = new HalfCalendarAdapter(dayList);

        // 레이아웃 설정 (열 7개)
        RecyclerView.LayoutManager manager = new GridLayoutManager(getActivity().getApplicationContext(), 7);

        // 레이아웃 적용
        HalfFragment_recyclerView.setLayoutManager(manager);

        // 어댑터 작용
        HalfFragment_recyclerView.setAdapter(halfAdapter);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private ArrayList<LocalDate> daysInMonthArray(LocalDate date) {

        ArrayList<LocalDate> dayList = new ArrayList<>();

        @SuppressLint({"NewApi", "LocalSuppress"}) YearMonth yearMonth = YearMonth.from(date);

        // 해당 월 마지막 날짜 가져오기
        @SuppressLint({"NewApi", "LocalSuppress"}) int lastDay = yearMonth.lengthOfMonth();

        // 해당 월의 첫 날짜 가져오기
        @SuppressLint({"NewApi", "LocalSuppress"}) LocalDate firstDay = CalendarUtill.selectedDate.withDayOfMonth(1);

        // 첫 날 요일 가져오기
        @SuppressLint({"NewApi", "LocalSuppress"}) int dayOfWeek = firstDay.getDayOfWeek().getValue();

        for (int i = 1;i < 42;i++) {
            if (i <= dayOfWeek || i > lastDay + dayOfWeek) {
                dayList.add(null);
            } else {
                dayList.add(LocalDate.of(CalendarUtill.selectedDate.getYear(), CalendarUtill.selectedDate.getMonth(), i - dayOfWeek));
            }
        }
        return dayList;
    }
}