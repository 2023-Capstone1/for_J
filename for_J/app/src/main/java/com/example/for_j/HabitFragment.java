package com.example.for_j;

import static com.example.for_j.CalendarUtill.selectedDate;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class HabitFragment extends Fragment {

    // 달력 관련 변수
    TextView HabitFragment_monthYearText; // 년월 텍스트뷰
    RecyclerView HabitFragment_recyclerView; // RecyclerView 객체 생성
    ImageButton HabitFragment_prevBtn;
    ImageButton HabitFragment_nextBtn;

    // 리스트뷰관련 변수
    ListView HabitFragment_listView;
    HabitListAdapter HabitFragment_listAdapter;
    // 리스트 개수를 보여주기위한 텍스트뷰
    TextView HabitFragment_ListCountText;
    // 리스트뷰 내부 아이템 클릭 시 클릭 위치 전역 변수로 선언 -> 다이얼로그에 일정 이름을 보여주기 위함
    private int clickedPosition = -1;
    // 리스트뷰 오른쪽 상단에 있는 오늘 날짜 표시 텍스트뷰
    TextView HabitFragment_list_today;

    // 다이얼로그 관련
    HabitDialog dialog;

    // + 버튼
    ImageButton moveHabitSetDateNew;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // 헤빗 프래그먼트 뷰 생성
        View habitView = inflater.inflate(R.layout.fragment_habit, container, false);

        // 해빗 리스트 추가 인텐트로 이동
        moveHabitSetDateNew = habitView.findViewById(R.id.habit_listAddBtn);
        moveHabitSetDateNew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), HabitSetDateNew.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                startActivity(intent);
            }
        });


        /*
         * 달력 관련 코드
         * */
        // 초기화
        HabitFragment_monthYearText = habitView.findViewById(R.id.monthYearText);
        HabitFragment_prevBtn = habitView.findViewById(R.id.preBtn);
        HabitFragment_nextBtn = habitView.findViewById(R.id.nextBtn);
        HabitFragment_recyclerView = habitView.findViewById(R.id.dateRecyclerView);

        // 현재 날짜
        selectedDate = LocalDate.now();

        // 화면 설정
        setMonthView();

        // 이전달 버튼 이벤트
        HabitFragment_prevBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                // 현재 월-1 변수에 담기
                CalendarUtill.selectedDate = CalendarUtill.selectedDate.minusMonths(1);
                setMonthView();
            }
        });
        // 다음달 버튼 이벤트
        HabitFragment_nextBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                // 현재 월+1 변수에 담기
                CalendarUtill.selectedDate = CalendarUtill.selectedDate.plusMonths(1);
                setMonthView();
            }
        });

        /*
         * 리스트뷰 관련 코드
         * */
        // 리스트뷰 연결
        HabitFragment_listView = habitView.findViewById(R.id.habit_habitList);
        // 리스트뷰 어뎁터
        HabitFragment_listAdapter = new HabitListAdapter();

        // 리스트뷰 테스트 -디비에서 가져오는 걸로 바꿔야함
        HabitFragment_listAdapter.addItem(new ListItem("물 1L 이상 마시기"));
        HabitFragment_listAdapter.addItem(new ListItem("운동 30분"));
        HabitFragment_listAdapter.addItem(new ListItem("영양제 챙겨 먹기"));
        HabitFragment_listView.setAdapter(HabitFragment_listAdapter);

        // 개수 텍스트뷰에 리스트 아이템 개수 출력 -db쿼리 카운트해서 가져오는게 좋을지 지금처럼 리스트뷰.getCount()하는게 좋을지 고민중
        HabitFragment_ListCountText = habitView.findViewById(R.id.habitListcount);
        HabitFragment_ListCountText.setText(String.valueOf(HabitFragment_listView.getCount()));

        // 리스트뷰 오른쪽 위에 오늘 날짜 표시
        HabitFragment_list_today = habitView.findViewById(R.id.todoToday);
        HabitFragment_list_today.setText(dayFormat(CalendarUtill.selectedDate));

        // habit은 달성여부를 사용자가 체크하는게 아니고 nfc 태그 이용해서 체크해야됨 -> 다이얼로그 관련 코드 일단 주석처리 -> nfc 체크o 아이콘은 drawable/nfc_check.png 사용
        /*
         * 다이얼로그 관련
         * */
        // 리스트뷰 아이템 체크박스 클릭하면 체크박스 다이얼로그 띄우기
        HabitFragment_listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                clickedPosition = position; // 클릭 위치 전역변수로 넘김

                dialog = new HabitDialog(getActivity(), HabitFragment_listAdapter, HabitFragment_ListCountText, clickedPosition, "To-Do", HabitFragment_listView);
                dialog.show();

                // 몇 번째 리스트 아이템 클릭했는지 확인용 토스트 메시지 -> 나중에 삭제하기
                Toast.makeText(getActivity(), position + "번째 선택", Toast.LENGTH_SHORT).show();
            }
        });

        // Inflate the layout for this fragment
        return habitView;
    }

    // 날짜 타입 설정
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
        HabitFragment_monthYearText.setText(monthYearFromDate(CalendarUtill.selectedDate));

        // calendar_cell 텍스트뷰 세팅
        ArrayList<LocalDate> dayList = daysInMonthArray(CalendarUtill.selectedDate);

        HalfCalendarAdapter halfAdapter = new HalfCalendarAdapter(dayList);

        // 레이아웃 설정 (열 7개)
        RecyclerView.LayoutManager manager = new GridLayoutManager(getActivity().getApplicationContext(), 7);

        // 레이아웃 적용
        HabitFragment_recyclerView.setLayoutManager(manager);

        // 어댑터 작용
        HabitFragment_recyclerView.setAdapter(halfAdapter);
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