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
