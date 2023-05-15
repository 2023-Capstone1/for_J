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
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.time.Instant;
import java.time.LocalDate;
import java.time.YearMonth;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Objects;

public class HabitFragment extends Fragment implements HabitListAdapter.HabitListAdapterListener{

    //옵션 버튼
    ImageButton Btn_Option;

    // 달력 관련 변수
    private TextView HabitFragment_monthYearText; // 년월 텍스트뷰
    private RecyclerView HabitFragment_recyclerView; // RecyclerView 객체 생성
    private ImageButton HabitFragment_prevBtn;
    private ImageButton HabitFragment_nextBtn;
    private DateTimeFormatter formatter; // 달력 날짜 포맷


    // 리스트 개수를 보여주기위한 텍스트뷰
    private TextView HabitFragment_ListCountText;
    private RelativeLayout nothingMessage;
    // 리스트뷰 내부 아이템 클릭 시 클릭 위치 전역 변수로 선언 -> 다이얼로그에 일정 이름을 보여주기 위함
    private int clickedPosition = -1;
    // 리스트뷰 오른쪽 상단에 있는 오늘 날짜 표시 텍스트뷰
    private TextView HabitFragment_list_today;

    // 다이얼로그 관련
    private HabitDialog dialog;

    // + 버튼
    private ImageButton moveHabitSetDateNew;


    // 서버 통신 관련 변수
    private ApiService checkTupleExistAPI;
    private String checkTupleExistURL;
    private ApiService getHabitListAPI;
    String habitURL;
    private Calendar calendar = Calendar.getInstance();
    private String loginID = "123";
    private String selectedDateStr;

    private LinearLayout listLayout;
    private View habitView;

    // 리스트뷰관련 변수
    private ListView HabitFragment_listView;
    private HabitListAdapter HabitFragment_listAdapter;


    // 리스트뷰관련 변수
//    ListView HabitFragment_listView;
//    HabitListAdapter HabitFragment_listAdapter;

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

        // 옵션 버튼 클릭 이벤트
        Btn_Option = habitView.findViewById(R.id.btn_option);

        Btn_Option.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Menu.java로 이동하는 인텐트 생성
                Intent intent = new Intent(getActivity(), Menu.class);
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
//        setMonthView();

        // 이전달 버튼 이벤트
        HabitFragment_prevBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                // 현재 월-1 변수에 담기
                CalendarUtill.selectedDate = CalendarUtill.selectedDate.minusMonths(1);
                setMonthView();
                onResume();
            }
        });
        // 다음달 버튼 이벤트
        HabitFragment_nextBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                // 현재 월+1 변수에 담기
                CalendarUtill.selectedDate = CalendarUtill.selectedDate.plusMonths(1);
                setMonthView();
                onResume();
            }
        });

        // 리스트뷰 오른쪽 위에 오늘 날짜 표시
        HabitFragment_list_today = habitView.findViewById(R.id.habitToday);
        HabitFragment_list_today.setText(String.valueOf(calendar.get(Calendar.DAY_OF_MONTH)));

        HabitFragment_ListCountText = habitView.findViewById(R.id.habitListcount);

        HabitFragment_list_today.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Date date = calendar.getTime();
                Instant instant = date.toInstant();
                ZonedDateTime zonedDateTime = instant.atZone(ZoneId.systemDefault());
                LocalDate calendarDate = zonedDateTime.toLocalDate();

                selectedDate = calendarDate;

                onResume();
            }
        });

        formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        selectedDateStr = selectedDate.format(formatter);

        listLayout = habitView.findViewById(R.id.todoList_add_position);
        listLayout.setVisibility(View.VISIBLE);
        nothingMessage = habitView.findViewById(R.id.nothingMessage);

        return habitView;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onResume() {
        super.onResume();

        setMonthView();

        if (nothingMessage == null){
            nothingMessage = habitView.findViewById(R.id.nothingMessage);
//            System.out.println("onResume에서 nothingMessage 연결");
        }

        if (HabitFragment_ListCountText == null){
            HabitFragment_ListCountText = habitView.findViewById(R.id.habitListcount);
        }

        if (listLayout != null){
            listLayout.removeAllViewsInLayout();
            listLayout.removeViewInLayout(listLayout);

            selectedDateStr = selectedDate.format(formatter);

            checkTupleExistURL = "http://203.250.133.162:8080/checkAPI/get_is_tuple_exist/" + loginID + "/" + "habit" + "/" + selectedDateStr;
            checkTupleExistAPI = new ApiService();
            checkTupleExistAPI.getUrl(checkTupleExistURL);

            if (Objects.equals(checkTupleExistAPI.getValue("is_tuple_exist"), "0")){
                nothingMessage.setVisibility(View.VISIBLE);
                // 모든 투두리스트 개수 합쳐서 출력하기 위해 HabitFragment_ListCountText변수 추가
                HabitFragment_ListCountText.setText(checkTupleExistAPI.getValue("is_tuple_exist"));
            }else{
                nothingMessage.setVisibility(View.GONE);
//                System.out.println("onResume에서 nothingMessage Gone 실행");
                getHabitFromServer();
            }
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void getHabitFromServer() {
        habitURL = "http://203.250.133.162:8080/habitAPI/get_habit_today/" + loginID + "/" + selectedDateStr;
        getHabitListAPI = new ApiService();
        getHabitListAPI.getUrl(habitURL);

        HabitFragment_ListCountText.setText(getHabitListAPI.getValue("habit_today_total"));
        System.out.println("해빗 토탈: " + getHabitListAPI.getValue("habit_today_total"));

        HabitFragment_listAdapter = new HabitListAdapter();
        HabitFragment_listAdapter.setListener(HabitFragment.this);
        HabitFragment_listView = new ListView(getContext());

        HabitFragment_listView.setNestedScrollingEnabled(false);
        HabitFragment_listView.setDivider(null);    // 디바이더 제거
        HabitFragment_listView.setDividerHeight(20);

        for (int i = 0; i < Integer.parseInt(getHabitListAPI.getValue("habit_today_total")); i++){
            HabitFragment_listAdapter.addItem(
                    new ListItem(getHabitListAPI.getValue("habit_list_id"+i), getHabitListAPI.getValue("habit_name"+i), selectedDateStr,
                            getHabitListAPI.getValue("habit_color"+i), getHabitListAPI.getValue("habit_startDate"+i),
                            getHabitListAPI.getValue("habit_endDate"+i), Integer.parseInt(getHabitListAPI.getValue("habit_state"+i)))
            );
        }
        HabitFragment_listView.setAdapter(HabitFragment_listAdapter);
        listLayout.addView(HabitFragment_listView);

        // 스크롤 뷰와 리스트뷰 충돌방지 용 리스트뷰 높이 지정
        int totalHeight = 0;
        ViewGroup.LayoutParams params;
        for (int i = 0; i < Integer.parseInt(getHabitListAPI.getValue("habit_today_total")); i++){
            // 모든 항목을 표시하기 위해 리스트뷰의 높이를 계산
            View listItem = HabitFragment_listAdapter.getView(i, null, HabitFragment_listView);
            listItem.measure(0, 0);
            totalHeight += listItem.getMeasuredHeight();
        }

        // 리스트뷰의 높이를 고정
        params = HabitFragment_listView.getLayoutParams();
        params.height = totalHeight + (HabitFragment_listView.getDividerHeight() * (HabitFragment_listAdapter.getCount() - 1));
        HabitFragment_listView.setLayoutParams(params);

        HabitFragment_listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                System.out.println("이거 실행됨");
                clickedPosition = position;

                dialog = new HabitDialog(getActivity(), HabitFragment_listAdapter, clickedPosition, "HABIT", HabitFragment_listView);
                dialog.setParentFragment(HabitFragment.this);
                dialog.show();
                onResume();
            }
        });

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
        halfAdapter.setParentFragment(HabitFragment.this);

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

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onCheckButtonClicked(int position, HabitDialog habitDialog) {
        // Handle check button click event
        habitDialog.setParentFragment(HabitFragment.this);
    }
}