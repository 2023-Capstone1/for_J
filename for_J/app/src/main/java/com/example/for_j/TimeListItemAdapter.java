package com.example.for_j;

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

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

public class TimeListItemAdapter extends BaseAdapter {

    public ArrayList<TimeListItem> items = new ArrayList<>();
    Context context;

    private TimeFragment parentFragment;

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


        String loginId = "123";
        String name = items.get(position).getListName();
        String id = items.get(position).getListId();
        int[] order = {items.get(position).getListOrder()};
        String[] startTime = {items.get(position).getListStartTime()};
        String[] endTime = {items.get(position).getListEndTime()};
        String[] timeTaken = {items.get(position).getListTimeTaken()};

        Chronometer chrono = convertView.findViewById(R.id.timer);
        ImageView play = convertView.findViewById(R.id.play);
        ImageView pause = convertView.findViewById(R.id.pause);
        final long[] timeWhenStopped = {0}; // 멈춘 시점의 시간을 저장할 변수
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
                            items.get(position).setListOrder(order[0]);
                        }
                    }
//                    if (timeOrderString != null) {
//                        order[0] = Integer.parseInt(timeOrderString);
//                    }
                    items.get(position).setListOrder(order[0]);
                    play.setVisibility(View.VISIBLE);
                    pause.setVisibility(View.INVISIBLE);
                }
            }
        });

//        // 서버에서 order 가지고 와서 시간 출력하기
//        String loginId = "123";
//        String name = items.get(position).getListName();
//        String id = items.get(position).getListId();
//        int[] order = {items.get(position).getListOrder()};
//        String[] startTime = {items.get(position).getListStartTime()};
//        String[] endTime = {items.get(position).getListEndTime()};
//        String[] timeTaken = {items.get(position).getListTimeTaken()};
//
//        //String getTimeList = "http://203.250.133.162:8080/timeAPI/get_timer_info/" + loginId + "/" + id + "/" + name + "/" + order + "/" + startTime + "/" + endTime + "/" + timeTaken;
////        ApiService getStateAPI = new ApiService();
////        getStateAPI.getUrl(getTimeList);
////        order = Integer.parseInt(getStateAPI.getValue("time_order"));
////        items.get(position).setListOrder(order);
//
//
//        Chronometer chrono = convertView.findViewById(R.id.timer);
//        ImageView play = convertView.findViewById(R.id.play);
//        ImageView pause = convertView.findViewById(R.id.pause);
//        final long[] timeWhenStopped = {0}; // 멈춘 시점의 시간을 저장할 변수
//        final boolean[] isRunning = {false};
//        final long[] pauseOffset = {0};
//
//        // Play에 OnClickListener 추가
//        play.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (!isRunning[0]) {
//                    startTime[0] = String.valueOf(System.currentTimeMillis() - pauseOffset[0]);
//                    chrono.setBase(SystemClock.elapsedRealtime() - pauseOffset[0]);
//                    chrono.start();
//                    isRunning[0] = true;
//                    play.setVisibility(View.INVISIBLE);
//                    pause.setVisibility(View.VISIBLE);
//                    order[0]++;
//                }
//            }
//        });
//
//        // Pause에 OnClickListener 추가
//        pause.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (isRunning[0]) {
//                    endTime[0] = String.valueOf(System.currentTimeMillis());
//                    //timeWhenStopped[0] = chrono.getBase() - SystemClock.elapsedRealtime(); // 멈춘 시점의 시간을 계산
//                    chrono.stop();
//                    pauseOffset[0] = SystemClock.elapsedRealtime() - chrono.getBase();
//                    isRunning[0] = false;
//                    long millis = Long.parseLong(endTime[0]) - Long.parseLong(startTime[0]);
//                    SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss", Locale.getDefault());
//                    dateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
//                    timeTaken[0] = dateFormat.format(new Date(millis));
//                    String getTimeList = "http://203.250.133.162:8080/timeAPI/get_timer_info/" + loginId + "/" + id + "/" + name + "/" + order[0] + "/" + startTime[0] + "/" + endTime[0] + "/" + timeTaken[0];
//                    ApiService getStateAPI = new ApiService();
//                    getStateAPI.getUrl(getTimeList);
//                    String timeOrderString = getStateAPI.getValue("time_order");
//                    if (timeOrderString != null) {
//                        order[0] = Integer.parseInt(timeOrderString);
//                    }
//                    items.get(position).setListOrder(order[0]);
//                    play.setVisibility(View.VISIBLE);
//                    pause.setVisibility(View.INVISIBLE);
//                }
//            }
//        });


        return convertView;
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