// 하프캘린더 -> 풀캘린더에서 날짜 클릭 시 그날 하프캘린더 프래그먼트 보여주도록 수정부탁~!~!!~!
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

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;


public class HalfCalendarFragment extends Fragment implements CalListAdapter.CalListAdapterListener, CalHabitListAdapter.HabitListAdapterListener, CalTodoListAdapter.TodoListAdapterListener {

    private Calendar calendar = Calendar.getInstance();
    private String loginID = "123";

    private DateTimeFormatter formatter;
    private String selectedDateStr;

    // 다이얼로그 관련
    private CalDialog cDialog;
    private CalHabitDialog hDialog;
    private CalTodoDialog tDialog;


    private View halfCalView;

    private int clickedPosition = -1;

    // 하프 캘린더 화면 액티비티
    TextView HalfFragment_monthYearText; // 년월 텍스트뷰
    RecyclerView HalfFragment_recyclerView; // RecyclerView 객체 생성
    ImageButton HalfFragment_prevBtn; // 이전달 이동 버튼
    ImageButton HalfFragment_nextBtn; // 다음달 이동 버튼

    // +버튼
    private ImageButton moveTimeSetDateNew;

    // 리스트 관리 변수
    private LinearLayout HalfCal_AllList;
    private LinearLayout HalfCal_CalList;
    private LinearLayout HalfCal_habitList;
    private LinearLayout HalfCal_todoList;

    private ListView Cal_listView;
    private CalListAdapter Cal_listAdapter;
    private ListView Habit_listView;
    private CalHabitListAdapter Habit_listAdapter;
    private ListView Todo_listView;
    private CalTodoListAdapter Todo_listAdapter;

    // nothing 화면
    private TextView cal_nothing;
    private TextView habit_nothing;
    private TextView todo_nothing;







    // 서버 소통 변수
    private String CalIsTupleExistURL;
    private ApiService CalIsTupleExistAPI = new ApiService();

    private String getCalListURL;
    private ApiService getCalListAPI;

    private String HabitIsTupleExistURL;
    private ApiService HabitIsTupleExistAPI = new ApiService();

    private String getHabitListURL;
    private ApiService getHabitListAPI;

    private String TodoIsTupleExistURL;
    private ApiService TodoIsTupleExistAPI = new ApiService();

    private String getTodoListURL;
    private ApiService getTodoListAPI;



    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        halfCalView = inflater.inflate(R.layout.fragment_half_calendar, container, false);

        // 타임 리스트 추가 인텐트로 이동
        moveTimeSetDateNew = halfCalView.findViewById(R.id.time_listAddBtn);
        moveTimeSetDateNew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), CalSetDateNew.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                startActivity(intent);
            }
        });

        // 초기화
        HalfFragment_monthYearText = halfCalView.findViewById(R.id.Calendar_MonthYearText);
        HalfFragment_prevBtn = halfCalView.findViewById(R.id.Calendar_PreBtn);
        HalfFragment_nextBtn = halfCalView.findViewById(R.id.Calendar_NextBtn);
        HalfFragment_recyclerView = halfCalView.findViewById(R.id.Calendar_RecyclerView);

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

        formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        selectedDateStr = selectedDate.format(formatter);

        HalfCal_AllList = halfCalView.findViewById(R.id.HalfCal_AllList);
        HalfCal_CalList = halfCalView.findViewById(R.id.HalfCal_CalList);
        HalfCal_habitList = halfCalView.findViewById(R.id.HalfCal_habitList);
        HalfCal_todoList = halfCalView.findViewById(R.id.HalfCal_todoList);

        cal_nothing = halfCalView.findViewById(R.id.cal_nothing);
        cal_nothing.setVisibility(View.GONE);
        habit_nothing = halfCalView.findViewById(R.id.habit_nothing);
        habit_nothing.setVisibility(View.GONE);
        todo_nothing = halfCalView.findViewById(R.id.todo_nothing);
        todo_nothing.setVisibility(View.GONE);

        // Inflatethe layout for this fragment
        return halfCalView;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onResume() {
        super.onResume();
        // Rerun the code from the beginning

        setMonthView();

        // selectedDate 포매팅하기
        selectedDateStr = selectedDate.format(formatter);

        // 캘린더 레이아웃 설정
        if (HalfCal_CalList != null){
            HalfCal_CalList.removeAllViewsInLayout();
            HalfCal_CalList.removeViewInLayout(HalfCal_CalList);

            HalfCal_CalList = halfCalView.findViewById(R.id.HalfCal_CalList);

            // 캘린더 튜플이 있는지 확인
            CalIsTupleExistURL = "http://203.250.133.162:8080/checkAPI/get_is_tuple_exist/" + loginID + "/" + "calendar" + "/" + selectedDateStr;
            CalIsTupleExistAPI.getUrl(CalIsTupleExistURL);
            System.out.println(CalIsTupleExistAPI.getValue("is_tuple_exist"));

            // 캘린더가 없으면 nothing 띄우기
            if (Objects.equals(CalIsTupleExistAPI.getValue("is_tuple_exist"), "0")){
                cal_nothing.setVisibility(View.VISIBLE);
            } else {
                cal_nothing.setVisibility(View.GONE);
                getCalListFromServer();
            }
        }

        // 해빗 레이아웃 설정
        if (HalfCal_habitList != null){
            HalfCal_habitList.removeAllViewsInLayout();
            HalfCal_habitList.removeViewInLayout(HalfCal_habitList);

            HalfCal_habitList = halfCalView.findViewById(R.id.HalfCal_habitList);

            // 해빗 튜플이 있는지 확인
            HabitIsTupleExistURL = "http://203.250.133.162:8080/checkAPI/get_is_tuple_exist/" + loginID + "/" + "habit" + "/" + selectedDateStr;
            HabitIsTupleExistAPI.getUrl(HabitIsTupleExistURL);
            System.out.println(HabitIsTupleExistAPI.getValue("is_tuple_exist"));

            // 해빗이 없으면 nothing 띄우기
            if (Objects.equals(HabitIsTupleExistAPI.getValue("is_tuple_exist"), "0")){
                habit_nothing.setVisibility(View.VISIBLE);
            } else {
                habit_nothing.setVisibility(View.GONE);
                getHabitListFromServer();
            }
        }

        // 투두 레이아웃 설정
        if (HalfCal_todoList != null){
            HalfCal_todoList.removeAllViewsInLayout();
            HalfCal_todoList.removeViewInLayout(HalfCal_todoList);

            HalfCal_todoList = halfCalView.findViewById(R.id.HalfCal_todoList);

            // 투두가 있는지 확인
            TodoIsTupleExistURL = "http://203.250.133.162:8080/checkAPI/get_is_tuple_exist/" + loginID + "/" + "todo" + "/" + selectedDateStr;
            TodoIsTupleExistAPI.getUrl(TodoIsTupleExistURL);
            System.out.println(TodoIsTupleExistAPI.getValue("is_tuple_exist"));

            // 캘린더가 없으면 nothing 띄우기
            if (Objects.equals(TodoIsTupleExistAPI.getValue("is_tuple_exist"), "0")){
                todo_nothing.setVisibility(View.VISIBLE);
            } else {
                todo_nothing.setVisibility(View.GONE);
                getTodoListFromServer();
            }
        }


    }

    // 캘린더 리스트 불러오기
    @RequiresApi(api = Build.VERSION_CODES.O)
    private void getCalListFromServer(){
        selectedDateStr = selectedDate.format(formatter);

        getCalListURL = "http://203.250.133.162:8080/calendarAPI/get_cal_list/" + loginID + "/" + selectedDateStr;
//        System.out.println(getCalListURL);
        getCalListAPI = new ApiService();
        getCalListAPI.getUrl(getCalListURL);

        // 가져온 캘린더 리스트 어뎁터 아이템에 추가 하기
        Cal_listAdapter = new CalListAdapter();
        Cal_listAdapter.setListener(HalfCalendarFragment.this);
        Cal_listView = new ListView(getContext());

        Cal_listView.setNestedScrollingEnabled(false);
//        Cal_listView.setDivider(null);  // 디바이더 제거
        Cal_listView.setDividerHeight(2);
        /*{
            "cal_list_id0": "3",
                "cal_list_name0": "web test",
                "cal_list_color0": "pink",
                "cal_list_allDay0": "0",
                "cal_list_startDate0": "2023-05-18",
                "cal_list_startTime0": "14:00",
                "cal_list_endDate0": "2023-05-31",
                "cal_list_endTime0": "16:30",
                "cal_list_location0": "c401",
                "cal_list_alarm0": "0",
                "cal_list_memo0": "memo test",
                "total": "1",
                "SUCCESS": "200"
        }*/
        for (int i = 0; i < Integer.parseInt(getCalListAPI.getValue("total")); i++){
            Cal_listAdapter.addItem(
                    new ListItem(getCalListAPI.getValue("cal_list_id"+i), getCalListAPI.getValue("cal_list_name"+i),
                            getCalListAPI.getValue("cal_list_color"+i), Integer.parseInt(getCalListAPI.getValue("cal_list_allDay"+i)),
                            getCalListAPI.getValue("cal_list_startDate"+i), getCalListAPI.getValue("cal_list_startTime"+i),
                            getCalListAPI.getValue("cal_list_endDate"+i), getCalListAPI.getValue("cal_list_endTime"+i),
                            getCalListAPI.getValue("cal_list_location"+i), Integer.parseInt(getCalListAPI.getValue("cal_list_alarm"+i)),
                            getCalListAPI.getValue("cal_list_memo"+i))
            );
        }
        Cal_listView.setAdapter(Cal_listAdapter);
        HalfCal_CalList.addView(Cal_listView);

//        System.out.println(getCalListAPI.getValue("total"));



        // 스크롤 뷰와 리스트뷰 충돌방지 용 리스트뷰 높이 지정
        int totalHeight = 0;
        ViewGroup.LayoutParams params;
        for (int i = 0; i < Integer.parseInt(getCalListAPI.getValue("total")); i++){
            // 모든 항목을 표시하기 위해 리스트뷰의 높이를 계산
            View listItem = Cal_listAdapter.getView(i, null, Cal_listView);
            listItem.measure(0, 0);
            totalHeight += listItem.getMeasuredHeight();
        }

        /*if (Integer.parseInt(getCalListAPI.getValue("total")) == 1){
            totalHeight += 50;
        }*/


        // 리스트뷰의 높이를 고정
        params = Cal_listView.getLayoutParams();
        params.height = totalHeight + (20 * (Cal_listAdapter.getCount()));
        Cal_listView.setLayoutParams(params);

        Cal_listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                System.out.println("이거 실행됨");
                clickedPosition = position;

                cDialog = new CalDialog(getActivity(), Cal_listAdapter, clickedPosition, "Calendar", Cal_listView);
                cDialog.setParentFragment(HalfCalendarFragment.this);
                cDialog.show();
                onResume();
            }
        });
    }
    // 해빗 리스트 불러오기
    @RequiresApi(api = Build.VERSION_CODES.O)
    private void getHabitListFromServer(){
        selectedDateStr = selectedDate.format(formatter);

        getHabitListURL = "http://203.250.133.162:8080/habitAPI/get_habit_today/" + loginID + "/" + selectedDateStr;
        getHabitListAPI = new ApiService();
        getHabitListAPI.getUrl(getHabitListURL);

        Habit_listAdapter = new CalHabitListAdapter();
        Habit_listAdapter.setListener(HalfCalendarFragment.this);
        Habit_listView = new ListView(getContext());
        // 가져온 해빗 리스트 어뎁터 아이템에 추가하기
        /*{
            "habit_today_total": "1",
                "habit_list_id0": "19",
                "habit_name0": "test",
                "habit_today0": "2023-05-19",
                "habit_startDate0": "2023-05-01",
                "habit_endDate0": "2023-05-31",
                "habit_alarmSwitch0": "1",
                "habit_alarm0": "오후 2:22",
                "habit_repeatDay0": "null",
                "habit_repeatN0": "5",
                "habit_color0": "pink",
                "habit_nfc0": "ca05cd35",
                "habit_state0": "0",
                "habit_dayOfWeek0": "4",
                "SUCCESS": "200"
        }*/

        for (int i = 0; i < Integer.parseInt(getHabitListAPI.getValue("habit_today_total")); i++){
            Habit_listAdapter.addItem(
                    new ListItem(getHabitListAPI.getValue("habit_list_id"+i), getHabitListAPI.getValue("habit_name"+i), selectedDateStr,
                            getHabitListAPI.getValue("habit_color"+i), getHabitListAPI.getValue("habit_startDate"+i),
                            getHabitListAPI.getValue("habit_endDate"+i), Integer.parseInt(getHabitListAPI.getValue("habit_state"+i)))
            );
        }
        Habit_listView.setAdapter(Habit_listAdapter);
        HalfCal_habitList.addView(Habit_listView);


        // 스크롤 뷰와 리스트뷰 충돌방지 용 리스트뷰 높이 지정
        int totalHeight = 0;
        ViewGroup.LayoutParams params;
        for (int i = 0; i < Integer.parseInt(getHabitListAPI.getValue("habit_today_total")); i++){
            // 모든 항목을 표시하기 위해 리스트뷰의 높이를 계산
            View listItem = Habit_listAdapter.getView(i, null, Habit_listView);
            listItem.measure(0, 0);
            totalHeight += listItem.getMeasuredHeight();
        }

        /*if (Integer.parseInt(getHabitListAPI.getValue("habit_today_total")) == 1){
            totalHeight += 50;
        }*/

        // 리스트뷰의 높이를 고정
        params = Habit_listView.getLayoutParams();
        params.height = totalHeight + (20 * (Habit_listAdapter.getCount()));
        Habit_listView.setLayoutParams(params);

        Habit_listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                System.out.println("이거 실행됨");
                clickedPosition = position;

                hDialog = new CalHabitDialog(getActivity(), Habit_listAdapter, clickedPosition, "HABIT", Habit_listView);
                hDialog.setParentFragment(HalfCalendarFragment.this);
                hDialog.show();
                onResume();
            }
        });
    }
    // 투두 리스트 불러오기
    @RequiresApi(api = Build.VERSION_CODES.O)
    private void getTodoListFromServer(){
        selectedDateStr = selectedDate.format(formatter);

        getTodoListURL = "http://203.250.133.162:8080/todoAPI/get_todo_list_plus_color/" + loginID + "/" + selectedDateStr;
        getTodoListAPI = new ApiService();
        getTodoListAPI.getUrl(getTodoListURL);

        Todo_listAdapter = new CalTodoListAdapter();
        Todo_listAdapter.setListener(HalfCalendarFragment.this);
        Todo_listView = new ListView(getContext());
        /*{
            "todo_total": "1",
                "todo_list_id0": "3",
                "todo_name0": "testtest",
                "todo_cName0": "찾았다요놈",
                "todo_cColor0: "pink",
                "todo_state0": "0",
                "SUCCESS": "200"
        }*/
        for (int i = 0; i < Integer.parseInt(getTodoListAPI.getValue("todo_total")); i++){
            Todo_listAdapter.addItem(
                    new ListItem(getTodoListAPI.getValue("todo_list_id"+i), getTodoListAPI.getValue("todo_name"+i), selectedDateStr,
                            getTodoListAPI.getValue("todo_cName"+i), getTodoListAPI.getValue("todo_cColor"+i), Integer.parseInt(getTodoListAPI.getValue("todo_state"+i))));

        }


        Todo_listView.setAdapter(Todo_listAdapter);
        HalfCal_todoList.addView(Todo_listView);


        // 스크롤 뷰와 리스트뷰 충돌방지 용 리스트뷰 높이 지정
        int totalHeight = 0;
        ViewGroup.LayoutParams params;
        for (int i = 0; i < Integer.parseInt(getTodoListAPI.getValue("todo_total")); i++){
            // 모든 항목을 표시하기 위해 리스트뷰의 높이를 계산
            View listItem = Todo_listAdapter.getView(i, null, Todo_listView);
            listItem.measure(0, 0);
            totalHeight += listItem.getMeasuredHeight();

        }

        /*if (Integer.parseInt(getTodoListAPI.getValue("todo_total")) == 1){
            totalHeight += 40;
        }*/

        // 리스트뷰의 높이를 고정
        params = Todo_listView.getLayoutParams();
        params.height = totalHeight + (20 * (Todo_listAdapter.getCount()));
        Todo_listView.setLayoutParams(params);


        Todo_listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                System.out.println("이거 실행됨");
                clickedPosition = position;

                tDialog = new CalTodoDialog(getActivity(), Todo_listAdapter, clickedPosition, "TO-DO", Todo_listView);
                tDialog.setParentFragment(HalfCalendarFragment.this);
                tDialog.show();
                onResume();
            }
        });

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
        halfAdapter.setParentFragment(HalfCalendarFragment.this);

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

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onCheckButtonClicked(int position, CalDialog habitDialog) {
        // Handle check button click event
        habitDialog.setParentFragment(HalfCalendarFragment.this);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onCheckButtonClicked(int position, CalHabitDialog habitDialog) {
        habitDialog.setParentFragment(HalfCalendarFragment.this);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onCheckButtonClicked(int position, CalTodoDialog todoDialog) {
        todoDialog.setParentFragment(HalfCalendarFragment.this);
    }
}

// 여기 보관함임 건들 ㄴㄴ
//// 하프캘린더 -> 풀캘린더에서 날짜 클릭 시 그날 하프캘린더 프래그먼트 보여주도록 수정부탁~!~!!~!
//package com.example.for_j;
//
//import static com.example.for_j.CalendarUtill.selectedDate;
//
//import android.annotation.SuppressLint;
//import android.os.Build;
//import android.os.Bundle;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.ImageButton;
//import android.widget.TextView;
//
//import androidx.annotation.RequiresApi;
//import androidx.fragment.app.Fragment;
//import androidx.recyclerview.widget.GridLayoutManager;
//import androidx.recyclerview.widget.RecyclerView;
//
//import java.time.LocalDate;
//import java.time.YearMonth;
//import java.time.format.DateTimeFormatter;
//import java.util.ArrayList;
//
//public class HalfCalendarFragment extends Fragment {
//
//    // 하프 캘린더 화면 액티비티
//    TextView HalfFragment_monthYearText; // 년월 텍스트뷰
//    RecyclerView HalfFragment_recyclerView; // RecyclerView 객체 생성
//    ImageButton HalfFragment_prevBtn; // 이전달 이동 버튼
//    ImageButton HalfFragment_nextBtn; // 다음달 이동 버튼
//
//    @RequiresApi(api = Build.VERSION_CODES.O)
//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container,
//                             Bundle savedInstanceState) {
//        View habitView = inflater.inflate(R.layout.fragment_half_calendar, container, false);
//
//        // 초기화
//        HalfFragment_monthYearText = habitView.findViewById(R.id.Calendar_MonthYearText);
//        HalfFragment_prevBtn = habitView.findViewById(R.id.Calendar_PreBtn);
//        HalfFragment_nextBtn = habitView.findViewById(R.id.Calendar_NextBtn);
//        HalfFragment_recyclerView = habitView.findViewById(R.id.Calendar_RecyclerView);
//
//        // 현재 날짜
//        selectedDate = LocalDate.now();
//
//        // 달력 화면 설정
//        setMonthView();
//
//        // 이전달 버튼 이벤트
//        HalfFragment_prevBtn.setOnClickListener(new View.OnClickListener() {
//
//            @Override
//            public void onClick(View view) {
//                // 현재 월-1 변수에 담기
//                CalendarUtill.selectedDate = CalendarUtill.selectedDate.minusMonths(1);
//                setMonthView();
//            }
//        });
//        // 다음달 버튼 이벤트
//        HalfFragment_nextBtn.setOnClickListener(new View.OnClickListener() {
//
//            @Override
//            public void onClick(View view) {
//                // 현재 월+1 변수에 담기
//                CalendarUtill.selectedDate = CalendarUtill.selectedDate.plusMonths(1);
//                setMonthView();
//            }
//        });
//
//        // Inflatethe layout for this fragment
//        return habitView;
//    }
//
//    // 날짜 타입 설정
//    // 년월 텍스트뷰
//    @SuppressLint("NewApi")
//    private String monthYearFromDate(LocalDate date) {
//        @SuppressLint({"NewApi", "LocalSuppress"}) DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy년 MM월");
//        return date.format(formatter);
//    }
//    // 리스트뷰 오른쪽 위에 있는 날짜 텍스트뷰
//    @RequiresApi(api = Build.VERSION_CODES.O)
//    private String dayFormat(LocalDate date) {
//        DateTimeFormatter dayFormatter = DateTimeFormatter.ofPattern("dd");
//        return date.format(dayFormatter);
//    }
//
//    // 화면 설정
//    @RequiresApi(api = Build.VERSION_CODES.O)
//    private void setMonthView() {
//        // 년월 텍스트뷰 세팅
//        HalfFragment_monthYearText.setText(monthYearFromDate(CalendarUtill.selectedDate));
//
//        // calendar_cell 텍스트뷰 세팅
//        ArrayList<LocalDate> dayList = daysInMonthArray(CalendarUtill.selectedDate);
//
//        HalfCalendarAdapter halfAdapter = new HalfCalendarAdapter(dayList);
//
//        // 레이아웃 설정 (열 7개)
//        RecyclerView.LayoutManager manager = new GridLayoutManager(getActivity().getApplicationContext(), 7);
//
//        // 레이아웃 적용
//        HalfFragment_recyclerView.setLayoutManager(manager);
//
//        // 어댑터 작용
//        HalfFragment_recyclerView.setAdapter(halfAdapter);
//    }
//
//    @RequiresApi(api = Build.VERSION_CODES.O)
//    private ArrayList<LocalDate> daysInMonthArray(LocalDate date) {
//
//        ArrayList<LocalDate> dayList = new ArrayList<>();
//
//        @SuppressLint({"NewApi", "LocalSuppress"}) YearMonth yearMonth = YearMonth.from(date);
//
//        // 해당 월 마지막 날짜 가져오기
//        @SuppressLint({"NewApi", "LocalSuppress"}) int lastDay = yearMonth.lengthOfMonth();
//
//        // 해당 월의 첫 날짜 가져오기
//        @SuppressLint({"NewApi", "LocalSuppress"}) LocalDate firstDay = CalendarUtill.selectedDate.withDayOfMonth(1);
//
//        // 첫 날 요일 가져오기
//        @SuppressLint({"NewApi", "LocalSuppress"}) int dayOfWeek = firstDay.getDayOfWeek().getValue();
//
//        for (int i = 1;i < 42;i++) {
//            if (i <= dayOfWeek || i > lastDay + dayOfWeek) {
//                dayList.add(null);
//            } else {
//                dayList.add(LocalDate.of(CalendarUtill.selectedDate.getYear(), CalendarUtill.selectedDate.getMonth(), i - dayOfWeek));
//            }
//        }
//        return dayList;
//    }
//}