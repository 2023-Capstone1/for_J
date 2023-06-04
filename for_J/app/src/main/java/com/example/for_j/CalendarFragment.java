/*
* 20230324 프래그먼트 화면에 달력 올리기
* 20230327 프래그먼트 달력 처음부터 다시
*/

package com.example.for_j;

import static com.example.for_j.CalendarUtill.selectedDate;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class CalendarFragment extends Fragment {
    private Context context;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.context = context;
    }

    // 달력 관련 변수
    // 풀캘린더
    TextView CalendarFragment_monthYearText; // 년월 텍스트뷰
    RecyclerView CalendarFragment_recyclerView; // RecyclerView 객체 생성
    ImageButton CalendarFragment_prevBtn;
    ImageButton CalendarFragment_nextBtn;

    //옵션 버튼
    ImageButton Btn_Option;

    // 리스트뷰관련 변수
    ListView CalendarFragment_scheduleListView;
    ListView CalendarFragment_todoListView;
    ListView CalendarFragment_habitListView;
    ListItemAdapter CalendarFragment_listAdapter;

    // 프레임레이아웃에 달력 프래그먼트 화면 출력
    @SuppressLint("MissingInflatedId")
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        /*
        * 달력 관련 코드
        * -달력 날짜 부분에 디비에서 일정이 있는 날짜 알아와서 해당 날짜에 바(-)나 점으로 표시해야함
        * */
        // 프래그먼트 뷰 생성
        View calendarView = inflater.inflate(R.layout.fragment_calendar, container, false);

        // 옵션 버튼 클릭 이벤트
        Btn_Option = calendarView.findViewById(R.id.btn_option);

        Btn_Option.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Menu.java로 이동하는 인텐트 생성
                Intent intent = new Intent(getActivity(), Menu.class);
                startActivity(intent);
            }
        });

        // 풀캘린더 초기화
        CalendarFragment_monthYearText = calendarView.findViewById(R.id.monthYearText);
        CalendarFragment_prevBtn = calendarView.findViewById(R.id.preBtn);
        CalendarFragment_nextBtn = calendarView.findViewById(R.id.nextBtn);
        CalendarFragment_recyclerView = calendarView.findViewById(R.id.dateRecyclerView);

        // 현재 날짜
        if (selectedDate == null) {
            selectedDate = LocalDate.now();
        }
        // 풀캘린더 화면 설정
        setFullMonthView();


        // 이전달 버튼 이벤트
        CalendarFragment_prevBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                // 현재 월-1 변수에 담기
                CalendarUtill.selectedDate = CalendarUtill.selectedDate.minusMonths(1);
                setFullMonthView();
            }
        });
        // 다음달 버튼 이벤트
        CalendarFragment_nextBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                // 현재 월+1 변수에 담기
                CalendarUtill.selectedDate = CalendarUtill.selectedDate.plusMonths(1);
                setFullMonthView();
            }
        });

        // 달력 스크롤 이벤트 -풀캘린더에서 아래로 스크롤하면 하프캘린더 -> 하프캘린더에서 스크롤하면 쿼터캘린더(주간캘린더)


//        /*
//         * 리스트뷰 관련 코드
//         * */
//        CalendarFragment_scheduleListView = calendarView.findViewById(R.id.calendar_schedule);
//        CalendarFragment_todoListView = calendarView.findViewById(R.id.calendar_todoList);
//        CalendarFragment_habitListView = calendarView.findViewById(R.id.calendar_habitList);
//        CalendarFragment_listAdapter = new ListItemAdapter();

        // Inflate the layout for this fragment
        return calendarView;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void updateSelectedDate(LocalDate selectedDate) {
        // selectedDate 업데이트 및 화면 갱신 작업 수행
        CalendarUtill.selectedDate = selectedDate;
        setFullMonthView();
    }



    // 날짜 타입 설정
    @SuppressLint("NewApi")
    private String monthYearFromDate(LocalDate date) {
        @SuppressLint({"NewApi", "LocalSuppress"}) DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy년 MM월");
        return date.format(formatter);
    }

    // 풀캘린더 화면 설정
    @RequiresApi(api = Build.VERSION_CODES.O)
    private void setFullMonthView() {
        // 년월 텍스트뷰 세팅
        CalendarFragment_monthYearText.setText(monthYearFromDate(CalendarUtill.selectedDate));

        // calendar_cell 텍스트뷰 세팅
        ArrayList<LocalDate> dayList = daysInMonthArray(CalendarUtill.selectedDate);

        CalendarAdapter adapter = new CalendarAdapter(context, dayList);
        adapter.setParentFragment(CalendarFragment.this);

        // 레이아웃 설정 (열 7개)
        RecyclerView.LayoutManager manager = new GridLayoutManager(getActivity().getApplicationContext(), 7);

        // 레이아웃 적용
        CalendarFragment_recyclerView.setLayoutManager(manager);

        // 어댑터 작용
        CalendarFragment_recyclerView.setAdapter(adapter);


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