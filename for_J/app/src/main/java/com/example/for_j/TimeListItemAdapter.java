package com.example.for_j;

import static com.example.for_j.CalendarUtill.selectedDate;

import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.os.Build;
import android.os.Handler;
import android.os.SystemClock;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Chronometer;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

@RequiresApi(api = Build.VERSION_CODES.O)
public class TimeListItemAdapter extends BaseAdapter {

    public ArrayList<TimeListItem> items = new ArrayList<>();
    Context context;

    private TimeFragment parentFragment;

    private LocalDate today = LocalDate.now();

    private String loginId;
    private String name;
    private String id;
    private int orderTotal;

    boolean isRun = false;
    String[] timeParts; // :을 기준으로 문자열 자르기
    int hours;
    int minutes;
    int seconds;
    private SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");
    String timeTmp;

//    ApiService
    private String getOrderInfoURL;
    private ApiService getOrderInfoAPI = new ApiService();

    private String playClickURL;
    private ApiService playClickAPI = new ApiService();

    private String pauseClickURL;
    private ApiService pauseClickAPI = new ApiService();

    public interface TimeListAdapterListener {
        void onCheckButtonClicked(int position, TimeTrackerListDialog timeListDialog);
    }

    private TimeListItemAdapter.TimeListAdapterListener mListener;

    public void setListener(TimeListItemAdapter.TimeListAdapterListener listener) {
        mListener = listener;
    }

    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public Object getItem(int position) {
        return items.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    // 리스트를 어떻게 보여줄지 정하는 메서드
    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        context = parent.getContext();
        TimeListItem TimelistItem = items.get(position);

        IdSave idSave = (IdSave) context.getApplicationContext();
        loginId = idSave.getUserId();

        // listview_item.xml inflate 해서 convertView 참조
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.time_listview_item, parent, false);

            int colorValue;
            switch (items.get(position).getListColor()) {
                case "pink":
                    colorValue = context.getColor(R.color.lighter_pink);
                    convertView.setBackgroundTintList(ColorStateList.valueOf(colorValue));
                    break;
                case "crimson":
                    colorValue = context.getColor(R.color.lighter_crimson);
                    convertView.setBackgroundTintList(ColorStateList.valueOf(colorValue));
                    break;
                case "orange":
                    colorValue = context.getColor(R.color.lighter_orange);
                    convertView.setBackgroundTintList(ColorStateList.valueOf(colorValue));
                    break;
                case "yellow":
                    colorValue = context.getColor(R.color.lighter_yellow);
                    convertView.setBackgroundTintList(ColorStateList.valueOf(colorValue));
                    break;
                case "light_green":
                    colorValue = context.getColor(R.color.lighter_light_green);
                    convertView.setBackgroundTintList(ColorStateList.valueOf(colorValue));
                    break;
                case "turquoise":
                    colorValue = context.getColor(R.color.lighter_turquoise);
                    convertView.setBackgroundTintList(ColorStateList.valueOf(colorValue));
                    break;
                case "pastel_blue":
                    colorValue = context.getColor(R.color.lighter_pastel_blue);
                    convertView.setBackgroundTintList(ColorStateList.valueOf(colorValue));
                    break;
                case "pastel_purple":
                    colorValue = context.getColor(R.color.lighter_pastel_purple);
                    convertView.setBackgroundTintList(ColorStateList.valueOf(colorValue));
                    break;
            }

        }

        // 화면에 보여질 데이터 참조
        TextView listNameText = convertView.findViewById(R.id.listName);

        // 데이터 set
        listNameText.setText(TimelistItem.getListName());

        // 다이얼로그로 넘어가는 이미지
        ImageView option = convertView.findViewById(R.id.time_option);

        // ImageView에 OnClickListener 추가
        option.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TimeTrackerListDialog dialog = new TimeTrackerListDialog(context, TimeListItemAdapter.this, position, "Time", (ListView) parent);
                dialog.show();
            }
        });

        // timetable과 연결
        TextView listName = convertView.findViewById(R.id.listName);
        listName.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, TimeTrackerTimeTable.class);
                String dateString = selectedDate.format(DateTimeFormatter.ISO_LOCAL_DATE);
                intent.putExtra("date", dateString);
                intent.putExtra("listId", items.get(position).getListId());
                intent.putExtra("color", items.get(position).getListColor());
                context.startActivity(intent);
            }
        });

        name = items.get(position).getListName();
        id = items.get(position).getListId();

        LinearLayout listview_layout = convertView.findViewById(R.id.listview_layout);
        // 아이템의 크로노 미터로 변경
        items.get(position).chrono = convertView.findViewById(R.id.timer);
        ImageView play = convertView.findViewById(R.id.play);
        ImageView pause = convertView.findViewById(R.id.pause);
        // 기존에 저장되어 있는 오더 리스트 가지고 오기
        resetOrderList(position, convertView);

        // TimeAlarm의 인스턴스 생성
        TimeAlarm timeAlarm = new TimeAlarm(context);

        // play버튼 눌렀을 때
        play.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View view) {

                // TimeAlarm 시작
                timeAlarm.start();

                // 크로노미터 동작 중일 때 버튼 비활성화
                listName.setEnabled(false);
                listview_layout.setEnabled(false);

                if (!today.isEqual(items.get(position).today)){
                    Toast.makeText(context, "오늘이 아니면 플레이버튼이 비활성화 됩니다.", Toast.LENGTH_SHORT).show();
                    return;
                }

                int lastOrder = items.get(position).getLastOrder();

                // 크로노미터 초기화
                if (lastOrder > 0){
                    // 크로노미터 가장 마지막 timeTaken으로 초기화
                    // timeTake 포맷 hh:mm:ss
                    // :을 기준으로 가장 마지막 timeTaken 분리
                    timeParts = items.get(position).getListTimeTaken().get(lastOrder).split(":");
//                    timeParts = timeTakenTmp.get(lastOrder).split(":");
                    hours = Integer.parseInt(timeParts[0]);
                    minutes = Integer.parseInt(timeParts[1]);
                    seconds = Integer.parseInt(timeParts[2]);
                    long totalTimeInMillis = TimeUnit.HOURS.toMillis(hours) + TimeUnit.MINUTES.toMillis(minutes) + TimeUnit.SECONDS.toMillis(seconds);
                    items.get(position).chrono.setBase(SystemClock.elapsedRealtime() - totalTimeInMillis);

                    items.get(position).chrono.setBase(SystemClock.elapsedRealtime());  // 크로노미터 0으로 초기화
                }

                timeTmp = timeFormat.format(new Date(System.currentTimeMillis()));
                System.out.println("startTime 테스트" + timeTmp);

                playClickURL = "http://203.250.133.162:8080/timeAPI/play_click/" + loginId + "/" + items.get(position).getListId() + "/" + timeTmp;
                playClickAPI.getUrl(playClickURL);

                items.get(position).chrono.start();
                isRun = true;
                play.setVisibility(View.INVISIBLE);
                pause.setVisibility(View.VISIBLE);

                items.get(position).addListOrder(Integer.parseInt(playClickAPI.getValue("create_list_order")));
                items.get(position).addListStartTime(playClickAPI.getValue("create_list_startTime"));
                items.get(position).addListEndTime(playClickAPI.getValue("create_list_endTime"));
                items.get(position).addListTimeTaken(playClickAPI.getValue("create_list_timeTaken"));

                // hours가 1 증가할 때마다 알림 보여주기
                items.get(position).chrono.setOnChronometerTickListener(new Chronometer.OnChronometerTickListener() {
                    @Override
                    public void onChronometerTick(Chronometer chronometer) {
                        long elapsedMillis = SystemClock.elapsedRealtime() - chronometer.getBase();
                        hours = (int) (elapsedMillis / 3600000);
                        if (hours > 0) {
                            timeAlarm.showElapsedNotification(name, hours);
                        }
                    }
                });

            }
        });

        // pause 버튼 눌렀을 때
        pause.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View view) {
                if (!today.isEqual(items.get(position).today)){
                    Toast.makeText(context, "오늘이 아니면 플레이버튼이 비활성화 됩니다.", Toast.LENGTH_SHORT).show();
                    return;
                }

                // 크로노 미터가 멈추면 버튼 활성화
                listName.setEnabled(true);
                listview_layout.setEnabled(true);

                // 멈춘 시간 가져오기
                timeTmp = timeFormat.format(new Date(System.currentTimeMillis()));

                // TimeAlarm 정지
                timeAlarm.stop();

                pauseClickURL = "http://203.250.133.162:8080/timeAPI/pause_click/" + loginId + "/" + items.get(position).getListId() + "/" + timeTmp;
                pauseClickAPI.getUrl(pauseClickURL);

                items.get(position).chrono.stop();
                isRun = false;
                play.setVisibility(View.VISIBLE);
                pause.setVisibility(View.INVISIBLE);

                // 1분 이상일 때,
                if (!Objects.equals(pauseClickAPI.getValue("tuple"), "None")){
                    items.get(position).addListOrder(Integer.parseInt(pauseClickAPI.getValue("create_list_order")));
                    items.get(position).addListStartTime(pauseClickAPI.getValue("create_list_startTime"));
                    items.get(position).addListEndTime(pauseClickAPI.getValue("create_list_endTime"));
                    items.get(position).addListTimeTaken(pauseClickAPI.getValue("create_list_timeTaken"));
                }else{  // 1분 미만일 때
                    Toast.makeText(context, "1분 미만은 저장되지 않습니다.", Toast.LENGTH_SHORT).show();
                    items.get(position).removeLast();

                    int lastOrder = items.get(position).getLastOrder();

                    // 크로노미터 초기화
                    if (lastOrder > 0){
                        // 크로노미터 가장 마지막 timeTaken으로 초기화
                        // timeTake 포맷 hh:mm:ss
                        // :을 기준으로 가장 마지막 timeTaken 분리
//                        timeParts = timeTakenTmp.get(lastOrder).split(":");
                        timeParts = items.get(position).getListTimeTaken().get(lastOrder).split(":");
                        hours = Integer.parseInt(timeParts[0]);
                        minutes = Integer.parseInt(timeParts[1]);
                        seconds = Integer.parseInt(timeParts[2]);
                        long totalTimeInMillis = TimeUnit.HOURS.toMillis(hours) + TimeUnit.MINUTES.toMillis(minutes) + TimeUnit.SECONDS.toMillis(seconds);
                        items.get(position).chrono.setBase(SystemClock.elapsedRealtime() - totalTimeInMillis);
                    }else{
                        items.get(position).chrono.setBase(SystemClock.elapsedRealtime());  // 크로노미터 0으로 초기화
                    }
                }
            }
        });
        return convertView;
    }

    private void resetOrderList(int position, View view){
        List<Integer> orderListTmp;
        List<String> startTimeTmp;
        List<String> endTimeTmp;
        List<String> timeTakenTmp;

        orderListTmp = items.get(position).getListOrder();
        startTimeTmp = items.get(position).getListStartTime();
        endTimeTmp = items.get(position).getListEndTime();
        timeTakenTmp = items.get(position).getListTimeTaken();

        getOrderInfoURL = "http://203.250.133.162:8080/timeAPI/get_timer_info/" + loginId + "/" + items.get(position).getListId();
        getOrderInfoAPI.getUrl(getOrderInfoURL);

        orderTotal = Integer.parseInt(getOrderInfoAPI.getValue("timeOrder_total"));

        // 기존 오더 불러오기
        if (Integer.parseInt(getOrderInfoAPI.getValue("timeOrder_total")) > 0){
            for (int i = 0; i < orderTotal; i++){
                orderListTmp.add(Integer.parseInt(getOrderInfoAPI.getValue("timeOrder_order"+i)));
                startTimeTmp.add(getOrderInfoAPI.getValue("timeOrder_startTime"+i));
                endTimeTmp.add(getOrderInfoAPI.getValue("timeOrder_endTime"+i));
                timeTakenTmp.add(getOrderInfoAPI.getValue("timeOrder_timeTaken"+i));
            }

            // 리스트 아이템으로 연결
            items.get(position).setListOrder(orderListTmp);
            items.get(position).setListStartTime(startTimeTmp);
            items.get(position).setListEndTime(endTimeTmp);
            items.get(position).setListTimeTaken(timeTakenTmp);

            items.get(position).setNumOfOrder(orderTotal);

            if (items.get(position).chrono == null){
                items.get(position).chrono = view.findViewById(R.id.timer);
            }

            // 크로노미터 가장 마지막 timeTaken으로 초기화
            // timeTake 포맷 hh:mm:ss
            int lastOrder = items.get(position).getLastOrder();
            timeParts = timeTakenTmp.get(lastOrder).split(":");
            hours = Integer.parseInt(timeParts[0]);
            minutes = Integer.parseInt(timeParts[1]);
            seconds = Integer.parseInt(timeParts[2]);
            long totalTimeInMillis = TimeUnit.HOURS.toMillis(hours) + TimeUnit.MINUTES.toMillis(minutes) + TimeUnit.SECONDS.toMillis(seconds);
            items.get(position).chrono.setBase(SystemClock.elapsedRealtime() - totalTimeInMillis);

        }else{
            orderTotal = 0;
            items.get(position).setNumOfOrder(orderTotal);

            if (items.get(position).chrono == null){
                items.get(position).chrono = view.findViewById(R.id.timer);
            }
            items.get(position).chrono.setBase(SystemClock.elapsedRealtime());  // 크로노미터 0으로 초기화
        }
    }
    public void addItem(TimeListItem item) {
        items.add(item);
    }

    public String getListId(int position) {
        return items.get(position).getListId();
    }

    // 리스트 이름 반환
    public String getListName(int position) {
        return items.get(position).getListName();
    }

    public String getListToday(int position) {
        return items.get(position).getListToday();
    }

    public String getListCName(int position) {
        return items.get(position).getListCName();
    }

    public String getListColor(int position) {
        return items.get(position).getListColor();
    }

    public void setListId(int position, String id) {
        items.get(position).setListId(id);
    }

    public void setListName(int position, String listName) {
        items.get(position).setListName(listName);
    }

    public void setListToday(int position, String listToday) {
        items.get(position).setListToday(listToday);
    }

    public void setListCName(int position, String listCName) {
        items.get(position).setListCName(listCName);
    }

    public void setListColor(int position, String listColor) {
        items.get(position).setListColor(listColor);
    }

    // items 배열 반환하는 메소드
    public ArrayList<TimeListItem> getTimeListItem() {
        return items;
    }
}