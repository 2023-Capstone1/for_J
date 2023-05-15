package com.example.for_j;

import static android.os.Build.VERSION_CODES.O;
import static com.example.for_j.CalendarUtill.selectedDate;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Typeface;
import android.graphics.drawable.GradientDrawable;
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

import androidx.annotation.RequiresApi;
import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.lang.annotation.Annotation;
import java.time.Instant;
import java.time.LocalDate;
import java.time.YearMonth;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

public class TimeFragment extends Fragment implements TimeListItemAdapter.TimeListAdapterListener {

    //옵션 버튼
    ImageButton Btn_Option;

    // 달력 관련 변수
    private TextView TimeFragment_monthYearText; // 년월 텍스트뷰
    private RecyclerView TimeFragment_recyclerView; // RecyclerView 객체 생성
    private ImageButton TimeFragment_prevBtn; // 이전달 이동 버튼
    private ImageButton TimeFragment_nextBtn; // 다음달 이동 버튼
    private DateTimeFormatter formatter;    // 달력 날짜 포맷

    // 리스트 개수를 보여주기위한 텍스트뷰
    private TextView TimeFragment_listCountText;
    private RelativeLayout nothingMessage;

    // 리스트뷰 내부 아이템 클릭 시 클릭 위치 전역 변수로 선언 -> 다이얼로그에 일정 이름을 보여주기 위함
    private int clickedPosition = -1;
    // 리스트뷰 오른쪽 상단에 있는 오늘 날짜 표시 텍스트뷰
    private TextView TimeFragment_list_today;

    // 다이얼로그 관련
    private TimeTrackerListDialog dialog;

    // +버튼
    private ImageButton moveTimeSetDateNew;



    // 서버 통신 관련 변수
    private ApiService checkTupleExistAPI;
    private String checkTupleExistURL;
    private ApiService getTimeDateCateAPI;
    private ApiService getTimeCateColorAPI;
    private String getCategoryUrl;
    private ApiService getTimeListAPI;
    private String timeUrl;
    private Calendar calendar = Calendar.getInstance();
    private String loginID = "123";
/*    private String startTime;
    private String endTime;
    private String timeTaken;
    private int order;*/
    //    @SuppressLint("DefaultLocale")
//    private String today = calendar.get(Calendar.YEAR) + "-" + String.format("%02d", calendar.get(Calendar.MONTH)+1) + "-" + String.format("%02d",calendar.get(Calendar.DAY_OF_MONTH));
    private String selectedDateStr;
    private int cateNum = 0;
    private int[] timeNum;
    private int isTodo = 0;




    private LinearLayout listLayoutSet;
    private LinearLayout[] listlayoutarr;
    private List<String> distinctCNameList;
    private List<String> distinctCColorlist;
    private View timeView;

    private ListView[] timeFragment_listView;
    private TimeListItemAdapter[] timeFragment_listAdapter;

    @SuppressLint({"MissingInflatedId", "ResourceType"})
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // 타임 프래그먼트 뷰 생성
        timeView = inflater.inflate(R.layout.fragment_time, container, false);

        // 타임 리스트 추가 인텐트로 이동
        moveTimeSetDateNew = timeView.findViewById(R.id.time_listAddBtn);
        moveTimeSetDateNew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), TimeTrackerSetDateNew.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                startActivity(intent);
            }
        });

        // 옵션 버튼 클릭 이벤트
        Btn_Option = timeView.findViewById(R.id.btn_option);

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
        TimeFragment_monthYearText = timeView.findViewById(R.id.monthYearText);
        TimeFragment_prevBtn = timeView.findViewById(R.id.preBtn);
        TimeFragment_nextBtn = timeView.findViewById(R.id.nextBtn);
        TimeFragment_recyclerView = timeView.findViewById(R.id.dateRecyclerView);

        // 현재 날짜
        selectedDate = LocalDate.now();

        // 달력 화면 설정
//        setMonthView();

        // 이전달 버튼 이벤트
        TimeFragment_prevBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                // 현재 월-1 변수에 담기
                CalendarUtill.selectedDate = CalendarUtill.selectedDate.minusMonths(1);
                setMonthView();
                onResume();
            }
        });
        // 다음달 버튼 이벤트
        TimeFragment_nextBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                // 현재 월+1 변수에 담기
                CalendarUtill.selectedDate = CalendarUtill.selectedDate.plusMonths(1);
                setMonthView();
                onResume();
            }
        });

        // 리스트뷰 오른쪽 위에 오늘 날짜 표시
        TimeFragment_list_today = timeView.findViewById(R.id.timeToday);
        TimeFragment_list_today.setText(String.valueOf(calendar.get(Calendar.DAY_OF_MONTH)));

        TimeFragment_list_today.setOnClickListener(new View.OnClickListener() {
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


        listLayoutSet = timeView.findViewById(R.id.timeList_add_position);
        listLayoutSet.setVisibility(View.VISIBLE);
        nothingMessage = timeView.findViewById(R.id.nothingMessage);


        // Inflate the layout for this fragment
        return timeView;
    }



    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onResume() {
        super.onResume();

        // Rerun the code from the beginning
        // 달력 화면 설정
        setMonthView();

        if (nothingMessage == null){
            nothingMessage = timeView.findViewById(R.id.nothingMessage);
//            System.out.println("onResume에서 nothingMessage 연결");
        }

        if (listLayoutSet != null){
            listLayoutSet.removeAllViewsInLayout();
            listLayoutSet.removeViewInLayout(listLayoutSet);
//            checkTupleExistAPI.getUrl(checkTupleExistURL);

            selectedDateStr = selectedDate.format(formatter);

            checkTupleExistURL = "http://203.250.133.162:8080/checkAPI/get_is_tuple_exist/" + loginID + "/" + "time" + "/" + selectedDateStr;
            checkTupleExistAPI = new ApiService();
            checkTupleExistAPI.getUrl(checkTupleExistURL);
            System.out.println("메소드 : get_is_tuple_exist");

            if (Objects.equals(checkTupleExistAPI.getValue("is_tuple_exist"), "0")){
//                nothingMessage.setVisibility(View.VISIBLE);
//                System.out.println("onResume에서 nothingMessage visible 실행");
//                Toast toast = Toast.makeText(timeView.getContext(),"Resume 서버에 값 없음", Toast.LENGTH_SHORT);
//                toast.show();
                TimeFragment_listCountText = timeView.findViewById(R.id.timeListCount);
                // 모든 투두리스트 개수 합쳐서 출력하기 위해 TimeFragment_listCount변수 추가
                TimeFragment_listCountText.setText(checkTupleExistAPI.getValue("is_tuple_exist"));

            }else{
//                nothingMessage.setVisibility(View.GONE);
//                System.out.println("onResume에서 nothingMessage Gone 실행");
                getCategoryFromServer();
                getTimeFromServer();
            }
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void getCategoryFromServer(){
        // 서버에서 카테고리 가지고 오기
        selectedDateStr = selectedDate.format(formatter);
        System.out.println(selectedDateStr);

        getCategoryUrl = "http://203.250.133.162:8080/timeAPI/get_time_date_category/" + loginID + "/" + selectedDateStr + "/" + isTodo;
        getTimeDateCateAPI = new ApiService();
        getTimeDateCateAPI.getUrl(getCategoryUrl);
        System.out.println("메소드 : get_time_date_category");


        // 중복 카테고리 중복 처리
        String[] cName = new String[Integer.parseInt(getTimeDateCateAPI.getValue("time_category_total"))];
//        int categoryCount = 0;
//        if (getTimeDateCateAPI.getValue("time_category_total") != null && !getTimeDateCateAPI.getValue("time_category_total").isEmpty()) {
//            categoryCount = Integer.parseInt(getTimeDateCateAPI.getValue("time_category_total"));
//        }
        for (int i = 0; i < Integer.parseInt(getTimeDateCateAPI.getValue("time_category_total")); i++){
            cName[i] = getTimeDateCateAPI.getValue("time_cName"+i);
        }
        Set<String> setDistinctCName = new HashSet<>(Arrays.asList(cName));
        cateNum = setDistinctCName.size();

        // 중복 처리된 카테고리 리스트
        distinctCNameList = new ArrayList<>(setDistinctCName);
        distinctCColorlist = new ArrayList<>();

        // 리스트뷰가 들어가야하는 리니어 레이아웃 연결
        if (listLayoutSet == null){
            listLayoutSet = timeView.findViewById(R.id.timeList_add_position);
        }
        listlayoutarr = new LinearLayout[cateNum];

        for (int i = 0; i < listlayoutarr.length; i++) {
            listlayoutarr[i] = new LinearLayout(timeView.getContext());

            // set the layout parameters and properties of the LinearLayout
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
            );
            listlayoutarr[i].setLayoutParams(layoutParams);
            listlayoutarr[i].setOrientation(LinearLayout.VERTICAL);

            listLayoutSet.addView(listlayoutarr[i]);
        }

        // 카테고리 버튼 만들기 (clickable = false)
        for (int i = 0; i < cateNum; i++){
            AppCompatButton button = new AppCompatButton(timeView.getContext());
            button.setText(distinctCNameList.get(i)); // 버튼 텍스트 설정
            button.setLayoutParams(new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT));
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    120
            );
            params.setMargins(20, 20, 20, 20); // set margin values in pixels

            button.setLayoutParams(params);
            button.setTypeface(null, Typeface.BOLD);

            GradientDrawable shape = new GradientDrawable();
            shape.setShape(GradientDrawable.RECTANGLE);
            shape.setCornerRadii(new float[] { 16, 16, 16, 16, 16, 16, 16, 16 }); // set corner radii

            getCategoryUrl = "http://203.250.133.162:8080/categoryAPI/get_time_category/" + loginID + "/" + distinctCNameList.get(i) + "/" + isTodo;
            getTimeCateColorAPI = new ApiService();
            getTimeCateColorAPI.getUrl(getCategoryUrl);
            System.out.println("메소드 : get_time_category");


            button.setClickable(false);
            int colorValue;
            switch (getTimeCateColorAPI.getValue("time_category_color")){
                case "pink":
                    colorValue = timeView.getContext().getColor(R.color.pink);
                    shape.setColor(colorValue);
                    colorValue = timeView.getContext().getColor(R.color.lighter_pink);
                    button.setTextColor(colorValue);
                    distinctCColorlist.add("pink");
                    break;
                case "crimson":
                    colorValue = timeView.getContext().getColor(R.color.crimson);
                    shape.setColor(colorValue);
                    colorValue = timeView.getContext().getColor(R.color.lighter_crimson);
                    button.setTextColor(colorValue);
                    distinctCColorlist.add("crimson");
                    break;
                case "orange":
                    colorValue = timeView.getContext().getColor(R.color.orange);
                    shape.setColor(colorValue);
                    colorValue = timeView.getContext().getColor(R.color.lighter_orange);
                    button.setTextColor(colorValue);
                    distinctCColorlist.add("orange");
                    break;
                case "yellow":
                    colorValue = timeView.getContext().getColor(R.color.yellow);
                    shape.setColor(colorValue);
                    colorValue = timeView.getContext().getColor(R.color.lighter_yellow);
                    button.setTextColor(colorValue);
                    distinctCColorlist.add("yellow");
                    break;
                case "light_green":
                    colorValue = timeView.getContext().getColor(R.color.light_green);
                    shape.setColor(colorValue);
                    colorValue = timeView.getContext().getColor(R.color.lighter_light_green);
                    button.setTextColor(colorValue);
                    distinctCColorlist.add("light_green");
                    break;
                case "turquoise":
                    colorValue = timeView.getContext().getColor(R.color.turquoise);
                    shape.setColor(colorValue);
                    colorValue = timeView.getContext().getColor(R.color.lighter_turquoise);
                    button.setTextColor(colorValue);
                    distinctCColorlist.add("turquoise");
                    break;
                case "pastel_blue":
                    colorValue = timeView.getContext().getColor(R.color.pastel_blue);
                    shape.setColor(colorValue);
                    colorValue = timeView.getContext().getColor(R.color.lighter_pastel_blue);
                    button.setTextColor(colorValue);
                    distinctCColorlist.add("pastel_blue");
                    break;
                case "pastel_purple":
                    colorValue = timeView.getContext().getColor(R.color.pastel_purple);
                    shape.setColor(colorValue);
                    colorValue = timeView.getContext().getColor(R.color.lighter_pastel_purple);
                    button.setTextColor(colorValue);
                    distinctCColorlist.add("pastel_purple");
                    break;
            }
            button.setBackground(shape);
            listlayoutarr[i].addView(button); // 버튼을 LinearLayout에 추가
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void getTimeFromServer() {

        // 서버에서 타임 리스트 가지고 와서 카테고리 별로 분리해서 리스트에 추가하기
        timeUrl = "http://203.250.133.162:8080/timeAPI/get_time_list/" + loginID + "/" + selectedDateStr;
        getTimeListAPI = new ApiService();
        getTimeListAPI.getUrl(timeUrl);
        System.out.println("메소드 : get_time_list");


//        ListView[] timeFragment_listView;   // 카테고리 하나에 포함되어 있는 리스트뷰 배열
//        TimeListItemAdapter[] timeFragment_listAdapter; // 카테고리 하나에 포함되어 있는 리스트어뎁터 배열
//        LinearLayout[] listlayoutarr;   // 카테고리별 레이아웃
//        List<String> distinctCNameList; // 카테고리 리스트
//        private int[] timeNum;  // 카테고리별 투두 수

        // timeFragment_ListNumText 리스트 개수 출력
        TimeFragment_listCountText = timeView.findViewById(R.id.timeListCount);
        // 모든 타임리스트 개수 합쳐서 출력하기 위해 TimeFragment_listCount변수 추가
        TimeFragment_listCountText.setText(getTimeListAPI.getValue("time_total"));
        System.out.println("타임 토탈: " + getTimeListAPI.getValue("time_total"));

        // 카테고리 개수만큼의 리스트뷰 배열 만들기
        timeFragment_listView = new ListView[cateNum];
        timeFragment_listAdapter = new TimeListItemAdapter[cateNum];
//        timeNum = new int[cateNum];

        // 각각 카테고리에 포함되어 있는 리스트 가지고 생성
        for (int i = 0; i < cateNum; i++){
            timeFragment_listAdapter[i] = new TimeListItemAdapter();
            timeFragment_listAdapter[i].setListener(TimeFragment.this);
            timeFragment_listView[i] = new ListView(getContext());
            timeFragment_listView[i].setNestedScrollingEnabled(false);
            timeFragment_listView[i].setDivider(null); // 디바이더 제거
            timeFragment_listView[i].setDividerHeight(20);
            for (int j = 0; j < Integer.parseInt(getTimeListAPI.getValue("time_total")); j++){

                if (Objects.equals(distinctCNameList.get(i), getTimeListAPI.getValue("time_cName"+j))){
                    timeFragment_listAdapter[i].addItem(
                            new TimeListItem(getTimeListAPI.getValue("time_list_id"+j),
                                    getTimeListAPI.getValue("time_name"+j),
                                    selectedDateStr,
                                    getTimeListAPI.getValue("time_cName"+j),
                                    distinctCColorlist.get(i))
                    );
                }

            }
            // 리스트 어뎁터 연결
            timeFragment_listView[i].setAdapter(timeFragment_listAdapter[i]);

            listlayoutarr[i].addView(timeFragment_listView[i]);
        }


        // 스크롤 뷰와 리스트뷰 충돌방지 용 리스트뷰 높이 지정
        for (int i = 0; i < cateNum; i++){
            int totalHeight = 0;
            for (int j = 0; j < timeFragment_listAdapter[i].getCount(); j++){
                // 모든 항목을 표시하기 위해 리스트뷰의 높이를 계산
                View listItem = timeFragment_listAdapter[i].getView(j, null, timeFragment_listView[i]);
                listItem.measure(0,0);
                totalHeight += listItem.getMeasuredHeight();
            }
            // 리스트뷰의 높이를 고정
            ViewGroup.LayoutParams params = timeFragment_listView[i].getLayoutParams();
            params.height = totalHeight + (timeFragment_listView[i].getDividerHeight() * (timeFragment_listAdapter[i].getCount() - 1));
            timeFragment_listView[i].setLayoutParams(params);
        }

        for (int i = 0; i < cateNum; i++){
            final int listViewPage = i;
            timeFragment_listView[listViewPage].setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    clickedPosition = position; // 클릭 위치 전역변수로 넘김

                    Intent intent = new Intent(getContext(), TimeTrackerTimeTable.class);
                    intent.putExtra("date", new Date().getTime());
                    getContext().startActivity(intent);
                    /*System.out.println("listViewPage: " + listViewPage);
                    dialog = new TimeTrackerListDialog(getActivity(), timeFragment_listAdapter[listViewPage], clickedPosition, "Time", timeFragment_listView[listViewPage]);
                    dialog.setParentFragment(TimeFragment.this);
                    dialog.show();*/
                    // 몇 번째 리스트 아이템 클릭했는지 확인용 토스트 메시지 -> 나중에 삭제하기
//                    Toast.makeText(getActivity(), position + "번째 선택", Toast.LENGTH_SHORT).show();
                }
            });
        }
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
        TimeFragment_monthYearText.setText(monthYearFromDate(CalendarUtill.selectedDate));

        // calendar_cell 텍스트뷰 세팅
        ArrayList<LocalDate> dayList = daysInMonthArray(CalendarUtill.selectedDate);

        HalfCalendarAdapter halfAdapter = new HalfCalendarAdapter(dayList);
        halfAdapter.setParentFragment(TimeFragment.this);

        // 레이아웃 설정 (열 7개)
        RecyclerView.LayoutManager manager = new GridLayoutManager(getActivity().getApplicationContext(), 7);

        // 레이아웃 적용
        TimeFragment_recyclerView.setLayoutManager(manager);

        // 어댑터 작용
        TimeFragment_recyclerView.setAdapter(halfAdapter);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private ArrayList<LocalDate> daysInMonthArray(LocalDate date) {

        ArrayList<LocalDate> dayList = new ArrayList<>();

        @SuppressLint({"NewApi", "LocalSuppress"})
        YearMonth yearMonth = YearMonth.from(date);

        // 해당 월 마지막 날짜 가져오기
        @SuppressLint({"NewApi", "LocalSuppress"})
        int lastDay = yearMonth.lengthOfMonth();

        // 해당 월의 첫 날짜 가져오기
        @SuppressLint({"NewApi", "LocalSuppress"})
        LocalDate firstDay = CalendarUtill.selectedDate.withDayOfMonth(1);

        // 첫 날 요일 가져오기
        @SuppressLint({"NewApi", "LocalSuppress"})
        int dayOfWeek = firstDay.getDayOfWeek().getValue();

        for (int i = 1; i < 42; i++) {
            if (i <= dayOfWeek || i > lastDay + dayOfWeek) {
                dayList.add(null);
            } else {
                dayList.add(LocalDate.of(CalendarUtill.selectedDate.getYear(), CalendarUtill.selectedDate.getMonth(), i - dayOfWeek));
            }
        }
        return dayList;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    //@Override
    public void onCheckButtonClicked(int position, TimeTrackerListDialog timeTrackerListDialog) {
        // Handle check button click event
        timeTrackerListDialog.setParentFragment(TimeFragment.this);
    }
}