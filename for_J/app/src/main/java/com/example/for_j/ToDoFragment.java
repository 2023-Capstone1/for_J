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
import android.widget.Button;
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

public class ToDoFragment extends Fragment {

    // 달력 관련 변수
    TextView ToDoFragment_monthYearText; // 년월 텍스트뷰
    RecyclerView ToDoFragment_recyclerView; // RecyclerView 객체 생성
    ImageButton ToDoFragment_prevBtn; // 이전달 이동 버튼
    ImageButton ToDoFragment_nextBtn; // 다음달 이동 버튼

    // 리스트뷰 관련 변수
//    ListView ToDoFragment_listView; // 이거 아래 생성부분에서 반복문으로 생성 돌린만큼 변수 늘려야함
//    ListView ToDoFragment_listView2;
//    ListItemAdapter ToDoFragment_listAdapter;
    // 리스트 개수를 보여주기위한 텍스트뷰
    TextView ToDoFragment_listCountText;
    // 리스트뷰 내부 아이템 클릭 시 클릭 위치 전역 변수로 선언 -> 다이얼로그에 일정 이름을 보여주기 위함
    private int clickedPosition = -1;
    // 리스트뷰 오른쪽 상단에 있는 오늘 날짜 표시 텍스트뷰
    TextView ToDoFragment_list_today;

    // 다이얼로그 관련
    ToDoListDialog dialog;

    // +버튼
    ImageButton moveTodoSetDateNew;

    @SuppressLint({"MissingInflatedId", "ResourceType"})
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // 투두 프래그먼트 뷰 생성
        View todoView = inflater.inflate(R.layout.fragment_to_do, container, false);

        // 투두 리스트 추가 인텐트로 이동
        moveTodoSetDateNew = todoView.findViewById(R.id.todo_listAddBtn);
        moveTodoSetDateNew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), TodoSetDateNew.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                startActivity(intent);
            }
        });

        /*
         * 달력 관련 코드
         * */
        // 초기화
        ToDoFragment_monthYearText = todoView.findViewById(R.id.monthYearText);
        ToDoFragment_prevBtn = todoView.findViewById(R.id.preBtn);
        ToDoFragment_nextBtn = todoView.findViewById(R.id.nextBtn);
        ToDoFragment_recyclerView = todoView.findViewById(R.id.dateRecyclerView);

        // 현재 날짜
        selectedDate = LocalDate.now();

        // 달력 화면 설정
        setMonthView();

        // 이전달 버튼 이벤트
        ToDoFragment_prevBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                // 현재 월-1 변수에 담기
                CalendarUtill.selectedDate = CalendarUtill.selectedDate.minusMonths(1);
                setMonthView();
            }
        });
        // 다음달 버튼 이벤트
        ToDoFragment_nextBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                // 현재 월+1 변수에 담기
                CalendarUtill.selectedDate = CalendarUtill.selectedDate.plusMonths(1);
                setMonthView();
            }
        });

//        // 카테고리랑 리스트뷰를 묶은 리니어레이아웃 추가 --> 디비에서 투두 카테고리 개수 가져와서 아래 생성 부분
//        /*
//        * 생성 부분
//        * */
//        // LinearLayout 생성
//        LinearLayout linearLayout = new LinearLayout(getContext());
//        linearLayout.setOrientation(LinearLayout.VERTICAL);
//        linearLayout.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
//        linearLayout.setTag("linear_layout_todo"); // setId 대신 태그 설정해서 태그로 참조하기
//
//        // Button을 생성하고 LinearLayout에 추가합니다.
//        Button button = new Button(getContext());
//        button.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
//        button.setPadding(5, 5, 5, 5);
//        button.setText("Personal"); // 카테고리 디비 테이블에서 투두 카테고리 이름 가져와서 setText 수정해야함
//        button.setTag("todo_category");
//        linearLayout.addView(button);
//
//        // ListView를 생성하고 LinearLayout에 추가합니다.
//        ListView listView = new ListView(getContext());
//        listView.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
//        listView.setDividerHeight(10);
//        listView.setDivider(ContextCompat.getDrawable(getContext(), R.drawable.list_item_divider));
//        listView.setTag("todo_category_list");
//        linearLayout.addView(listView);
//
//        // ScrollView에 LinearLayout을 추가합니다.
//        ScrollView scrollView = todoView.findViewById(R.id.todo_listScrollView);
//        scrollView.addView(linearLayout);
//
//        /*
//        * 리스트뷰 관련 코드
//        * */
//        // 리스트뷰 연결 -> 위 코드처럼 xml자바로 추가했을 때 사용
//        ToDoFragment_listView = scrollView.findViewWithTag("todo_category_list");

        // 리스트뷰 연결





       //////////////////////////////////////////////////////////////////////////////////////////
        /*
        ToDoFragment_listView = todoView.findViewById(R.id.todo_list);
        ToDoFragment_listView2 = todoView.findViewById(R.id.todo_list2);
        // 리스트뷰 어댑터 객체 생성
        ToDoFragment_listAdapter = new ListItemAdapter();

        // 리스트뷰 테스트용 -디비에서 가져오는 걸로 바꿔야함
        // to-do 일정 이름
        ToDoFragment_listAdapter.addItem(new ListItem("안드로이드 과제 제출"));
        ToDoFragment_listAdapter.addItem(new ListItem("캡스톤 회의"));
        ToDoFragment_listView.setAdapter(ToDoFragment_listAdapter);
        ToDoFragment_listView2.setAdapter(ToDoFragment_listAdapter);

        // toDoFragment_ListNumText 리스트 개수 출력 -db쿼리 카운트해서 가져오는게 좋을지 지금처럼 리스트뷰.getCount()하는게 좋을지 고민중
        ToDoFragment_listCountText = todoView.findViewById(R.id.todoListCount);
        // 모든 투두리스트 개수 합쳐서 출력하기 위해 ToDoFragment_listCount변수 추가
        ToDoFragment_listCountText.setText(String.valueOf( ToDoFragment_listView.getCount() + ToDoFragment_listView2.getCount() )); // 두 리스트뷰 아이템 개수 합쳐서 출력

        // 리스트뷰 오른쪽 위에 오늘 날짜 표시
        ToDoFragment_list_today = todoView.findViewById(R.id.todoToday);
        ToDoFragment_list_today.setText(dayFormat(CalendarUtill.selectedDate));
        */

        /*
         * 다이얼로그 관련
         * */
        /*
        // 리스트뷰 아이템 체크박스 클릭하면 체크박스 다이얼로그 띄우기
        ToDoFragment_listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                clickedPosition = position; // 클릭 위치 전역변수로 넘김

                dialog = new ToDoListDialog(getActivity(), ToDoFragment_listAdapter, ToDoFragment_listCountText, clickedPosition, "To-Do", ToDoFragment_listView);
                dialog.show();

                // 몇 번째 리스트 아이템 클릭했는지 확인용 토스트 메시지 -> 나중에 삭제하기
                Toast.makeText(getActivity(), position + "번째 선택", Toast.LENGTH_SHORT).show();
            }
        });

        ToDoFragment_listView2.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                clickedPosition = position; // 클릭 위치 전역변수로 넘김

                dialog = new ToDoListDialog(getActivity(), ToDoFragment_listAdapter, ToDoFragment_listCountText, clickedPosition, "To-Do", ToDoFragment_listView2);
                dialog.show();

                // 몇 번째 리스트 아이템 클릭했는지 확인용 토스트 메시지 -> 나중에 삭제하기
                Toast.makeText(getActivity(), position + "번째 선택", Toast.LENGTH_SHORT).show();
            }
        });
        */
        ///////////////////////////////////////////////////////////////////////////////////////////



        // Inflate the layout for this fragment
        return todoView;
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
        ToDoFragment_monthYearText.setText(monthYearFromDate(CalendarUtill.selectedDate));

        // calendar_cell 텍스트뷰 세팅
        ArrayList<LocalDate> dayList = daysInMonthArray(CalendarUtill.selectedDate);

        HalfCalendarAdapter halfAdapter = new HalfCalendarAdapter(dayList);

        // 레이아웃 설정 (열 7개)
        RecyclerView.LayoutManager manager = new GridLayoutManager(getActivity().getApplicationContext(), 7);

        // 레이아웃 적용
        ToDoFragment_recyclerView.setLayoutManager(manager);

        // 어댑터 작용
        ToDoFragment_recyclerView.setAdapter(halfAdapter);
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