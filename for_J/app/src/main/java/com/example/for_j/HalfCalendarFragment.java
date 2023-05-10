// 하프캘린더 -> 풀캘린더에서 날짜 클릭 시 그날 하프캘린더 프래그먼트 보여주도록 수정부탁~!~!!~!
package com.example.for_j;

import static com.example.for_j.CalendarUtill.selectedDate;

import android.annotation.SuppressLint;
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


public class HalfCalendarFragment extends Fragment {

    //서버
    private String getHabitUrl;
    private ApiService getHabitListAPI;
    private ApiService getHabitAPI;
    private String getTodoUrl;
    private ApiService getTodoListAPI;
    private ApiService checkTupleExistAPI;
    private String checkTupleExistURL;

    private Calendar calendar = Calendar.getInstance();
    private String loginID = "123";
    @SuppressLint("DefaultLocale")
    private String today = calendar.get(Calendar.YEAR) + "-" + String.format("%02d", calendar.get(Calendar.MONTH)+1) + "-" + String.format("%02d",calendar.get(Calendar.DAY_OF_MONTH));
    private int cateNum = 0;
    private int[] habitNum;
    private int[] todoNum;


    private LinearLayout listLayoutSet;
    private LinearLayout[] listlayoutarr;
    private List<String> distinctCNameList;
    private View habitView;
    private View todoView;

    private RelativeLayout nothing;

    private ListView[] habitFragment_listView;
    private ListItemAdapter[] habitFragment_listAdapter;


    private int clickedPosition = -1;

    //private dialog;

    // 하프 캘린더 화면 액티비티
    TextView HalfFragment_monthYearText; // 년월 텍스트뷰
    RecyclerView HalfFragment_recyclerView; // RecyclerView 객체 생성
    ImageButton HalfFragment_prevBtn; // 이전달 이동 버튼
    ImageButton HalfFragment_nextBtn; // 다음달 이동 버튼

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        habitView = inflater.inflate(R.layout.fragment_half_calendar, container, false);

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

        listLayoutSet = habitView.findViewById(R.id.habitList_add_position);
        listLayoutSet.setVisibility(View.VISIBLE);
        nothing = habitView.findViewById(R.id.nothing);

        checkTupleExistURL = "http://203.250.133.162:8080/checkAPI/get_is_tuple_exist/" + loginID + "/" + "todo" + "/" + today;
        checkTupleExistAPI = new ApiService();
        checkTupleExistAPI.getUrl(checkTupleExistURL);

        if (Objects.equals(checkTupleExistAPI.getValue("is_tuple_exist"), "0")){
            nothing.setVisibility(View.VISIBLE);
//            System.out.println("onCreate에서 nothingMessage VISIBLE 실행");
//            Toast toast = Toast.makeText(todoView.getContext(),"onCreate 서버에 값 없음", Toast.LENGTH_SHORT);
//            toast.show();
        } else{
            nothing.setVisibility(View.GONE);
//            System.out.println("onCreate에서 nothingMessage Gone 실행");
            getHabitFromServer();
        }

        // Inflatethe layout for this fragment
        return habitView;
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onResume() {
        super.onResume();
        // Rerun the code from the beginning

        if (nothing == null){
            nothing = habitView.findViewById(R.id.nothing);
            //System.out.println("onResume에서 nothingMessage 연결");
        }

        if (listLayoutSet != null){
            listLayoutSet.removeAllViewsInLayout();
            listLayoutSet.removeViewInLayout(listLayoutSet);
            checkTupleExistAPI.getUrl(checkTupleExistURL);

            if (Objects.equals(checkTupleExistAPI.getValue("is_tuple_exist"), "0")){
                nothing.setVisibility(View.VISIBLE);
//                System.out.println("onResume에서 nothingMessage visible 실행");
//                Toast toast = Toast.makeText(todoView.getContext(),"Resume 서버에 값 없음", Toast.LENGTH_SHORT);
//                toast.show();

            }else{
                nothing.setVisibility(View.GONE);
//                System.out.println("onResume에서 nothingMessage Gone 실행");
                getHabitFromServer();
            }
        }
    }

    //367

    @RequiresApi(api = Build.VERSION_CODES.M)
    private void getHabitFromServer() {

        // 서버에서 습관 목록 가져오기
        getHabitUrl = "http://203.250.133.162:8080/habitAPI/get_habit_today/" + loginID + "/" + today;
        getHabitListAPI = new ApiService();
        getHabitListAPI.getUrl(getHabitUrl);

//
//        int totalHabits = Integer.parseInt(getHabitListAPI.getValue("habit_total"));
//        String[] categoryNames = new String[totalHabits];
//
//        //String[]  categoryNames = new String[Integer.parseInt(getHabitListAPI.getValue("habit_total"))];
//        for (int i = 0; i < Integer.parseInt(getHabitListAPI.getValue("habit_total")); i++) {
//            categoryNames[i] = getHabitListAPI.getValue("habit_cName" + i);
//        }
//
//        Set<String> distinctCategoryNames = new HashSet<>(Arrays.asList(categoryNames));
//        cateNum = distinctCategoryNames.size();

        // 리스트뷰가 들어가야하는 리니어 레이아웃 연결
        if (listLayoutSet == null){
            listLayoutSet = habitView.findViewById(R.id.habitList_add_position);
        }
        listlayoutarr = new LinearLayout[cateNum];

        for (int i = 0; i < listlayoutarr.length; i++) {
            listlayoutarr[i] = new LinearLayout(habitView.getContext());

            // set the layout parameters and properties of the LinearLayout
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
            );
            listlayoutarr[i].setLayoutParams(layoutParams);
            listlayoutarr[i].setOrientation(LinearLayout.VERTICAL);
            listLayoutSet.addView(listlayoutarr[i]);
        }

        habitFragment_listView = new ListView[cateNum];
        habitFragment_listAdapter = new ListItemAdapter[cateNum];
        habitNum = new int[cateNum];

        for (int i = 0; i < cateNum; i++) {
            habitFragment_listAdapter[i] = new ListItemAdapter();
            habitFragment_listView[i] = new ListView(getContext());
            habitFragment_listView[i].setNestedScrollingEnabled(false);
            habitFragment_listView[i].setDivider(null);
            habitFragment_listView[i].setDividerHeight(20);
            for (int j = 0; j < Integer.parseInt(getHabitListAPI.getValue("habit_total")); j++) {
                if (Objects.equals(distinctCNameList.get(i), getHabitListAPI.getValue("habit_cName" + j))) {
                    habitFragment_listAdapter[i].addItem(
                            new ListItem(getHabitListAPI.getValue("habit_list_id" + j), getHabitListAPI.getValue("habit_name" + j), today,
                                    distinctCNameList.get(i), distinctCNameList.get(i), Integer.parseInt(getHabitListAPI.getValue("habit_state" + j))));
                }
            }

            habitFragment_listView[i].setAdapter(habitFragment_listAdapter[i]);

            listlayoutarr[i].addView(habitFragment_listView[i]);

        }


//
//        for (int i = 0; i < cateNum; i++) {
//            int totalHeight = 0;
//            for (int j = 0; j < habitFragment_listAdapter[i].getCount(); j++) {
//                View listItem = habitFragment_listAdapter[i].getView(j, null, habitFragment_listView[i]);
//                listItem.measure(0, 0);
//                totalHeight += listItem.getMeasuredHeight();
//            }
//
//            ViewGroup.LayoutParams params = habitFragment_listView[i].getLayoutParams();
//            params.height = totalHeight + (habitFragment_listView[i].getDividerHeight() * (habitFragment_listAdapter[i].getCount() - 1));
//            habitFragment_listView[i].setLayoutParams(params);
//
//        }
//
//        for (int i = 0; i < cateNum; i++) {
//            final int listViewPage = i;
//            habitFragment_listView[listViewPage].setOnItemClickListener(new AdapterView.OnItemClickListener() {
//                @Override
//                public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
//                    clickedPosition = position;
//
//                    System.out.println("listViewPage: " + listViewPage);
//                    //dialog = new HabitListDialog(getActivity(), habitFragment_listAdapter[listViewPage], clickedPosition, "Habit", habitFragment_listView[listViewPage]);
//
//                }
//            });
//        }

    }





//    @RequiresApi(api = Build.VERSION_CODES.M)
//    private void getTodoFromServer(){
//        //오늘 날짜인지 확인
//        String currentDate = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());
//        if (today.equals(currentDate)) {
//            // The date is the current date, get the todo list from the server
//            getTodoUrl = "http://203.250.133.162:8080/todoAPI/get_todo_list/" + loginID + "/" + today;
//            getTodoListAPI = new ApiService();
//            getTodoListAPI.getUrl(getTodoUrl);
//
//            // Check if there are any todo items
//            if (Objects.equals(getTodoListAPI.getValue("todo_total"), "0")) {
//                nothing.setVisibility(View.VISIBLE);
//            } else {
//                nothing.setVisibility(View.GONE);
//                //addTodoItemsToListView();
//            }
//        } else {
//            // The date is not the current date, show a message
//            Toast.makeText(getContext(), "This is not today's date.", Toast.LENGTH_SHORT).show();
//        }




//        getTodoUrl = "http://203.250.133.162:8080/todoAPI/get_todo_list/" + loginID + "/" + today;
//        getTodoListAPI = new ApiService();
//        getTodoListAPI.getUrl(getTodoUrl);

//        todoFragment_listView = new ListView[cateNum];
//        todoFragment_listAdapter = new ListItemAdapter[cateNum];
//        todoNum = new int[cateNum];
//
//        // 각각 카테고리에 포함되어 있는 리스트 가지고 생성
//        for (int i = 0; i < cateNum; i++){
//            todoFragment_listAdapter[i] = new ListItemAdapter();
//            todoFragment_listView[i] = new ListView(getContext());
//            todoFragment_listView[i].setNestedScrollingEnabled(false);
//            todoFragment_listView[i].setDivider(null); // 디바이더 제거
//            todoFragment_listView[i].setDividerHeight(20);
//            for (int j = 0; j < Integer.parseInt(getTodoListAPI.getValue("todo_total")); j++) {
//                if (Objects.equals(distanceCNameList.get(i), getTodoListAPI.getValue("todo_cName" + j))) {
//                    habitFragment_listAdapter[i].addItem(
//                            new ListItem(getTodoListAPI.getValue("todo_list_id" + j), getHabitListAPI.getValue("todo_name" + j), today,
//                                    distanceCNameList.get(i), distanceCNameList.get(i), Integer.parseInt(getTodoListAPI.getValue("todo_state" + j))));
//                }
//            }
//
//            // 리스트 어뎁터 연결
//            todoFragment_listView[i].setAdapter(todoFragment_listAdapter[i]);
//
//            listlayoutarr[i].addView(todoFragment_listView[i]);
//        }
//
//        for (int i = 0; i < cateNum; i++) {
//            int totalHeight = 0;
//            for (int j = 0; j < habitFragment_listAdapter[i].getCount(); j++) {
//                View listItem = habitFragment_listAdapter[i].getView(j, null, habitFragment_listView[i]);
//                listItem.measure(0, 0);
//                totalHeight += listItem.getMeasuredHeight();
//            }
//
//            ViewGroup.LayoutParams params = habitFragment_listView[i].getLayoutParams();
//            params.height = totalHeight + (habitFragment_listView[i].getDividerHeight() * (habitFragment_listAdapter[i].getCount() - 1));
//            habitFragment_listView[i].setLayoutParams(params);
//
//        }
//
//        for (int i = 0; i < cateNum; i++) {
//            final int listViewPage = i;
//            habitFragment_listView[listViewPage].setOnItemClickListener(new AdapterView.OnItemClickListener() {
//                @Override
//                public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
//                    clickedPosition = position;
//
//                    System.out.println("listViewPage: " + listViewPage);
//                    //dialog = new HabitListDialog(getActivity(), habitFragment_listAdapter[listViewPage], clickedPosition, "Habit", habitFragment_listView[listViewPage]);
//
//                }
//            });
//        }
//    }

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