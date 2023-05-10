package com.example.for_j;

import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Chronometer;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.RequiresApi;

import java.util.ArrayList;

public class TimeListItemAdapter extends BaseAdapter {

    public ArrayList<TimeListItem> items = new ArrayList<>();
    Context context;

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
                intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                context.startActivity(intent);
            }
        });

        Chronometer chrono = convertView.findViewById(R.id.timer);
        ImageView play = convertView.findViewById(R.id.play);
        ImageView pause = convertView.findViewById(R.id.pause);

        // Play에 OnClickListener 추가
        play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // chrono.setBase(SystemClock.elapsedRealtime());
                chrono.start();
                play.setVisibility(View.INVISIBLE);
                pause.setVisibility(View.VISIBLE);
            }
        });

        // Pause에 OnClickListener 추가
        pause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chrono.stop();
                play.setVisibility(View.VISIBLE);
                pause.setVisibility(View.INVISIBLE);
            }
        });


        return convertView;
    }

    public void addItem(TimeListItem item) {
        items.add(item);
    }

    public String getListId(int position){
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

    public String getListColor(int position){
        return items.get(position).getListColor();
    }

    public void setListId(int position, String id){
        items.get(position).setListId(id);
    }

    public void setListName(int position, String listName) {
        items.get(position).setListName(listName);
    }

    public void setListToday(int position, String listToday){
        items.get(position).setListToday(listToday);
    }

    public void setListCName(int position, String listCName) {
        items.get(position).setListCName(listCName);
    }

    public void setListColor(int position, String listColor){
        items.get(position).setListColor(listColor);
    }

    // items 배열 반환하는 메소드
    public ArrayList<TimeListItem> getTimeListItem() {
        return items;
    }
}