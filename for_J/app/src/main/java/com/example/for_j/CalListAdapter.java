package com.example.for_j;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.core.content.ContextCompat;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class CalListAdapter extends BaseAdapter {

    public ArrayList<ListItem> items = new ArrayList<>();
    Context context;

    private HalfCalendarFragment parentFragment;

    public interface CalListAdapterListener{
        void onCheckButtonClicked(int position, CalDialog calDialog);
    }

    private CalListAdapter.CalListAdapterListener mListener;

    public void setListener(CalListAdapterListener listener) {
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
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        context = parent.getContext();
        ListItem listItem = items.get(position);

        if (convertView == null){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.listview_cal_item, parent, false);
        }

        ImageView Cal_color = convertView.findViewById(R.id.Cal_color);

        switch (items.get(position).getListColor()){
            case "pink":
                Cal_color.setBackgroundResource(R.drawable.color_round_box_pink);
                break;
            case "crimson":
                Cal_color.setBackgroundResource(R.drawable.color_round_box_crimson);
                break;
            case "orange":
                Cal_color.setBackgroundResource(R.drawable.color_round_box_orange);
                break;
            case "yellow":
                Cal_color.setBackgroundResource(R.drawable.color_round_box_yellow);
                break;
            case "light_green":
                Cal_color.setBackgroundResource(R.drawable.color_round_box_light_green);
                break;
            case "turquoise":
                Cal_color.setBackgroundResource(R.drawable.color_round_box_turquoise);
                break;
            case "pastel_blue":
                Cal_color.setBackgroundResource(R.drawable.color_round_box_pastel_blue);
                break;
            case "pastel_purple":
                Cal_color.setBackgroundResource(R.drawable.color_round_box_pastel_purple);
                break;
        }

        TextView cal_name = convertView.findViewById(R.id.cal_name);

        cal_name.setText(listItem.getListName());

        TextView cal_time = convertView.findViewById(R.id.cal_time);

        // 하루종일 == 0 이면 "오후 2:30 - 오후 4: 30" 이런식으로
        if (items.get(position).getListAllDay() == 0){
            String[] startTimeParts = listItem.getListStartTime().split(":");
            int startHour = Integer.parseInt(startTimeParts[0]);
            int startMinute = Integer.parseInt(startTimeParts[1]);
            String startTimeFormatted = String.format("%d:%02d %s", startHour % 12 == 0 ? 12 : startHour % 12, startMinute, startHour < 12 ? "AM" : "PM");

            String[] endTimeParts = listItem.getListEndTime().split(":");
            int endHour = Integer.parseInt(endTimeParts[0]);
            int endMinute = Integer.parseInt(endTimeParts[1]);
            String endTimeFormatted = String.format("%d:%02d %s", endHour % 12 == 0 ? 12 : endHour % 12, endMinute, endHour < 12 ? "AM" : "PM");

            String timeRange = startTimeFormatted + " - " + endTimeFormatted;
            cal_time.setText(timeRange);
        } else{
            cal_time.setText("하루종일");
//            cal_time.setVisibility(View.GONE);
        }










        return convertView;
    }

    public void addItem(ListItem item) {
        items.add(item);
    }

    public String getListId(int position){
        return items.get(position).getListId();
    }

    // getter들 만들기
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

    public String getListStartDate(int position) {
        return items.get(position).getListStartDate();
    }

    public String getListEndDate(int position) {
        return items.get(position).getListEndDate();
    }

    public String getListStartTime(int position) {return items.get(position).getListStartTime();}

    public String getListEndTime(int position) { return items.get(position).getListEndTime();}

    public int getListAllDay(int position) { return items.get(position).getListAllDay();}

    public String getListLocation(int position) { return items.get(position).getListLocation();}

    public int getListAlarm(int position) { return items.get(position).getListAlarm();}

    public String getListMemo(int position) { return items.get(position).getListMemo();}

    public void setListId(int position, String id){
        items.get(position).setListId(id);
    }

    // setter들 만들기
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

    public void setListStartTime(int position, String listStartTime) {
        items.get(position).setListStartTime(listStartTime);
    }

    public void setListEndTime(int position, String listEndTime) {
        items.get(position).setListEndTime(listEndTime);
    }

    public void setListAllDay(int position, int listAllDay){
        items.get(position).setListAllDay(listAllDay);
    }

    public void setListLocation(int position, String listLocation) {
        items.get(position).setListLocation(listLocation);
    }

    public void setListAlarm(int position, int listAlarm) {
        items.get(position).setListAlarm(listAlarm);
    }

    public void setListMemo(int position, String listMemo) {
        items.get(position).setListMemo(listMemo);
    }

    public void setListState(int position, int listState) {
        items.get(position).setListState(listState);
    }

    public void setListStartDate(int position, String listStartDate) {
        items.get(position).setListStartDate(listStartDate);
    }

    public void setListEndDate(int position, String listEndDate) {
        items.get(position).setListEndDate(listEndDate);
    }

    // items 배열 반환하는 메소드
    public ArrayList<ListItem> getListItem() { return items; }
}
