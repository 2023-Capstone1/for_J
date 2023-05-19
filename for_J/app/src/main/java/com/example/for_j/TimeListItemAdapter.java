package com.example.for_j;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.os.Build;
import android.os.SystemClock;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Chronometer;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.RequiresApi;

import com.example.for_j.dialog.TimePauseNotSaveDialog;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Objects;

public class TimeListItemAdapter extends BaseAdapter {

    public ArrayList<TimeListItem> items = new ArrayList<>();
    Context context;

    private TimeFragment parentFragment;

    private String loginId = "123";
    private String name;
    private String id;
    private int orderTotal;
    private List<Integer> orderListTmp;
    private List<String> startTimeTmp;
    private List<String> endTimeTmp;
    private List<String> timeTakenTmp;
    private Chronometer chrono;


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
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, TimeTrackerTimeTable.class);
                intent.putExtra("date", new Date().getTime());
                context.startActivity(intent);
            }
        });


        name = items.get(position).getListName();
        id = items.get(position).getListId();
        orderListTmp = items.get(position).getListOrder();
        startTimeTmp = items.get(position).getListStartTime();
        endTimeTmp = items.get(position).getListEndTime();
        timeTakenTmp = items.get(position).getListTimeTaken();

        // 기존에 저장되어 있는 오더 리스트 가지고 오기
        resetOrderList(position, convertView);
        chrono = convertView.findViewById(R.id.timer);
        ImageView play = convertView.findViewById(R.id.play);
        ImageView pause = convertView.findViewById(R.id.pause);


        // play버튼 눌렀을 때
        play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int lastOrder = items.get(position).getLastOrder();

                if (lastOrder > 0){
                    // 크로노미터 가장 마지막 timeTaken으로 초기화
                    // timeTake 포맷 hh:mm:ss
                    // :을 기준으로 가장 마지막 timeTaken 분리
                    timeParts = timeTakenTmp.get(lastOrder).split(":");
                    hours = Integer.parseInt(timeParts[0]);
                    minutes = Integer.parseInt(timeParts[1]);
                    seconds = Integer.parseInt(timeParts[2]);

                    long totalTimeInMillis = hours * 60 * 60 * 1000 + minutes * 60 * 1000 + seconds * 1000;

                    chrono.setBase(SystemClock.elapsedRealtime() - totalTimeInMillis);
                }else{
                    chrono.setBase(SystemClock.elapsedRealtime());
                }

                timeTmp = timeFormat.format(new Date(System.currentTimeMillis()));
                System.out.println("startTime 테스트" + timeTmp);

                playClickURL = "http://203.250.133.162:8080/timeAPI/play_click/" + loginId + "/" + items.get(position).getListId() + "/" + timeTmp;
                playClickAPI.getUrl(playClickURL);


                chrono.start();
                isRun = true;
                play.setVisibility(View.INVISIBLE);
                pause.setVisibility(View.VISIBLE);


                items.get(position).addListOrder(Integer.parseInt(playClickAPI.getValue("create_list_order")));
                orderListTmp.add(Integer.parseInt(playClickAPI.getValue("create_list_order")));
                items.get(position).addListStartTime(playClickAPI.getValue("create_list_startTime"));
                startTimeTmp.add(playClickAPI.getValue("create_list_startTime"));
                items.get(position).addListEndTime(playClickAPI.getValue("create_list_endTime"));
                endTimeTmp.add(playClickAPI.getValue("create_list_endTime"));
                items.get(position).addListTimeTaken(playClickAPI.getValue("create_list_timeTaken"));
                timeTakenTmp.add(playClickAPI.getValue("create_list_timeTaken"));
            }
        });

        // pause 버튼 눌렀을 때
        pause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                timeTmp = timeFormat.format(new Date(System.currentTimeMillis()));

                pauseClickURL = "http://203.250.133.162:8080/timeAPI/pause_click/" + loginId + "/" + items.get(position).getListId() + "/" + timeTmp;
                pauseClickAPI.getUrl(pauseClickURL);

                if (!Objects.equals(pauseClickAPI.getValue("tuple"), "None")){
                    chrono.stop();
                    isRun = false;
                    play.setVisibility(View.VISIBLE);
                    pause.setVisibility(View.INVISIBLE);
                    items.get(position).addListOrder(Integer.parseInt(pauseClickAPI.getValue("create_list_order")));
                    orderListTmp.add(Integer.parseInt(pauseClickAPI.getValue("create_list_order")));
                    items.get(position).addListStartTime(pauseClickAPI.getValue("create_list_startTime"));
                    startTimeTmp.add(pauseClickAPI.getValue("create_list_startTime"));
                    items.get(position).addListEndTime(pauseClickAPI.getValue("create_list_endTime"));
                    endTimeTmp.add(pauseClickAPI.getValue("create_list_endTime"));
                    items.get(position).addListTimeTaken(pauseClickAPI.getValue("create_list_timeTaken"));
                    timeTakenTmp.add(pauseClickAPI.getValue("create_list_timeTaken"));
                }else{
                    TimePauseNotSaveDialog timePauseNotSaveDialog = new TimePauseNotSaveDialog(context);
                    timePauseNotSaveDialog.show();
                    // 여기서 다시 resume();
                    chrono.stop();
                    isRun = false;
                    play.setVisibility(View.VISIBLE);
                    pause.setVisibility(View.INVISIBLE);

//                    items.get(position).remove(items.get(position).getLastOrder());
                    /*
                    List<Integer> numbers = new ArrayList<>();
                    numbers.add(1);
                    numbers.add(2);
                    numbers.add(3);

                    numbers.remove(1); // Removes the element at index 1 (value 2)
                    numbers.remove(Integer.valueOf(3)); // Removes the first occurrence of value 3
                    */

                }
            }
        });




        // get_timer_info
            /*
            timeOrder_total(모든 튜플의 개수)
            timeOrder_order
            timeOrder_startTime
            timeOrder_endTime
            timeOrder_timeTaken
            SUCCESS


            private int order;
            private List<Integer> orderListTmp;
            private List<String> startTimeTmp;
            private List<String> endTimeTmp;
            private List<String> timeTakenTmp;
            private Chronometer chrono;

            private String getOrderInfoURL;
            private ApiService getOrderInfoAPI;

            private String playClickURL;
            private ApiService playClickAPI;

            private String pauseClickURL;
            private ApiService pauseClickAPI;
             */


        /*final long[] timeWhenStopped = {0}; // 멈춘 시점의 시간을 저장할 변수
        final boolean[] isRunning = {false};
        final long[] pauseOffset = {0};

        play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isRunning[0]) {
                    if (startTime[0] == null || startTime[0].equals("0")) {
                        startTime[0] = String.valueOf(System.currentTimeMillis());
                    } else {
                        startTime[0] = endTime[0];
                    }
                    chrono.setBase(SystemClock.elapsedRealtime() - pauseOffset[0]);
                    chrono.start();
                    isRunning[0] = true;
                    play.setVisibility(View.INVISIBLE);
                    pause.setVisibility(View.VISIBLE);
                    order[0]++;
                }
            }
        });

        pause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isRunning[0]) {
                    endTime[0] = String.valueOf(System.currentTimeMillis());
                    chrono.stop();
                    pauseOffset[0] = SystemClock.elapsedRealtime() - chrono.getBase();
                    isRunning[0] = false;
                    long millis = Long.parseLong(endTime[0]) - Long.parseLong(startTime[0]);
                    SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss", Locale.getDefault());
                    dateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
                    timeTaken[0] = dateFormat.format(new Date(millis));
                    String getTimeList = "http://203.250.133.162:8080/timeAPI/get_timer_info/" + loginId + "/" + id + "/" + name + "/" + order[0] + "/" + startTime[0] + "/" + endTime[0] + "/" + timeTaken[0];
                    ApiService getTimeAPI = new ApiService();
                    getTimeAPI.getUrl(getTimeList);
                    String timeOrderString = getTimeAPI.getValue("time_order");
                    if (timeOrderString != null) {
                        int timeOrder = Integer.parseInt(timeOrderString);
                        getTimeList = "http://203.250.133.162:8080/timeAPI/get_timer_info/" + loginId + "/" + id + "/" + name + "/" + timeOrder + "/0/0/0";
                        getTimeAPI = new ApiService();
                        String startTimeString = getTimeAPI.getValue("start_time");
                        String endTimeString = getTimeAPI.getValue("end_time");
                        String timeTakenString = getTimeAPI.getValue("time_taken");

                        // 시간 정보를 저장하거나 업데이트합니다.
                        if (startTimeString != null && endTimeString != null && timeTakenString != null) {
                            startTime[0] = startTimeString;
                            endTime[0] = endTimeString;
                            timeTaken[0] = timeTakenString;
                            order[0] = timeOrder;
                            items.get(position).setListOrder(order);
                        }
                    }
//                    if (timeOrderString != null) {
//                        order[0] = Integer.parseInt(timeOrderString);
//                    }
                    items.get(position).setListOrder(order);
                    play.setVisibility(View.VISIBLE);
                    pause.setVisibility(View.INVISIBLE);
                }
            }
        });*/


        return convertView;
    }

    private void resetOrderList(int position, View view){
        // 기존 오더 불러오기
        if (orderListTmp == null){
            getOrderInfoURL = "http://203.250.133.162:8080/timeAPI/get_timer_info/" + loginId + "/" + items.get(position).getListId();
            getOrderInfoAPI.getUrl(getOrderInfoURL);

            orderTotal = Integer.parseInt(getOrderInfoAPI.getValue("timeOrder_total"));

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

            // 크로노미터 가장 마지막 timeTaken으로 초기화
            // timeTake 포맷 hh:mm:ss
            int lastOrder = items.get(position).getLastOrder();
            // :을 기준으로 가장 마지막 timeTaken 분리
            timeParts = timeTakenTmp.get(lastOrder).split(":");
            hours = Integer.parseInt(timeParts[0]);
            minutes = Integer.parseInt(timeParts[1]);
            seconds = Integer.parseInt(timeParts[2]);

            long totalTimeInMillis = hours * 60 * 60 * 1000 + minutes * 60 * 1000 + seconds * 1000;

            chrono.setBase(SystemClock.elapsedRealtime() - totalTimeInMillis);

        }else{
            orderTotal = 0;
            items.get(position).setNumOfOrder(orderTotal);

            if (chrono == null){
                chrono = view.findViewById(R.id.timer);
            }
            chrono.setBase(SystemClock.elapsedRealtime());  // 크로노미터 0으로 초기화
        }
    }

/*    private String formatElapsedTime(long elapsedMillis) {
        int hours = (int) (elapsedMillis / (1000 * 60 * 60) % 60 % 24);
        int minutes = (int) ((elapsedMillis / (1000 * 60)) % 60);
        int seconds = (int) ((elapsedMillis / 1000) % 60);

        return String.format("%02d:%02d:%02d", hours, minutes, seconds);
    }*/











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