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

public class CalHabitListAdapter extends BaseAdapter {

    public ArrayList<ListItem> items = new ArrayList<>();
    Context context;

    private HalfCalendarFragment parentFragment;

    public interface HabitListAdapterListener {
        void onCheckButtonClicked(int position, CalHabitDialog habitDialog);
    }

    private HabitListAdapterListener mListener;

    public void setListener(HabitListAdapterListener listener) {
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

        // listview_item.xml inflate 해서 convertView 참조
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.listview_cal_habit_item, parent, false);

            ImageView listCheckBtn = convertView.findViewById(R.id.listCheckBtn);
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

            Drawable btnDrawable;
            switch(items.get(position).getListState()){
                case 0:
                    switch (items.get(position).getListColor()) {
                        case "pink":
                            btnDrawable = ContextCompat.getDrawable(context, R.drawable.nfc_noncheck_pink);
                            listCheckBtn.setBackground(btnDrawable);
                            break;
                        case "crimson":
                            btnDrawable = ContextCompat.getDrawable(context, R.drawable.nfc_noncheck_crimson);
                            listCheckBtn.setBackground(btnDrawable);
                            break;
                        case "orange":
                            btnDrawable = ContextCompat.getDrawable(context, R.drawable.nfc_noncheck_orange);
                            listCheckBtn.setBackground(btnDrawable);
                            break;
                        case "yellow":
                            btnDrawable = ContextCompat.getDrawable(context, R.drawable.nfc_noncheck_yellow);
                            listCheckBtn.setBackground(btnDrawable);
                            break;
                        case "light_green":
                            btnDrawable = ContextCompat.getDrawable(context, R.drawable.nfc_noncheck_light_green);
                            listCheckBtn.setBackground(btnDrawable);
                            break;
                        case "turquoise":
                            btnDrawable = ContextCompat.getDrawable(context, R.drawable.nfc_noncheck_turquoise);
                            listCheckBtn.setBackground(btnDrawable);
                            break;
                        case "pastel_blue":
                            btnDrawable = ContextCompat.getDrawable(context, R.drawable.nfc_noncheck_pastel_blue);
                            listCheckBtn.setBackground(btnDrawable);
                            break;
                        case "pastel_purple":
                            btnDrawable = ContextCompat.getDrawable(context, R.drawable.nfc_noncheck_pastel_purple);
                            listCheckBtn.setBackground(btnDrawable);
                            break;
                    }
                    break;
                case 1:
                    switch (items.get(position).getListColor()) {
                        case "pink":
                            btnDrawable = ContextCompat.getDrawable(context, R.drawable.nfc_check_pink);
                            listCheckBtn.setBackground(btnDrawable);
                            break;
                        case "crimson":
                            btnDrawable = ContextCompat.getDrawable(context, R.drawable.nfc_check_crimson);
                            listCheckBtn.setBackground(btnDrawable);
                            break;
                        case "orange":
                            btnDrawable = ContextCompat.getDrawable(context, R.drawable.nfc_check_orange);
                            listCheckBtn.setBackground(btnDrawable);
                            break;
                        case "yellow":
                            btnDrawable = ContextCompat.getDrawable(context, R.drawable.nfc_check_yellow);
                            listCheckBtn.setBackground(btnDrawable);
                            break;
                        case "light_green":
                            btnDrawable = ContextCompat.getDrawable(context, R.drawable.nfc_check_light_green);
                            listCheckBtn.setBackground(btnDrawable);
                            break;
                        case "turquoise":
                            btnDrawable = ContextCompat.getDrawable(context, R.drawable.nfc_check_turquoise);
                            listCheckBtn.setBackground(btnDrawable);
                            break;
                        case "pastel_blue":
                            btnDrawable = ContextCompat.getDrawable(context, R.drawable.nfc_check_pastel_blue);
                            listCheckBtn.setBackground(btnDrawable);
                            break;
                        case "pastel_purple":
                            btnDrawable = ContextCompat.getDrawable(context, R.drawable.nfc_check_pastel_purple);
                            listCheckBtn.setBackground(btnDrawable);
                            break;
                    }
                    break;
            }

            TextView cal_name = convertView.findViewById(R.id.cal_name);
            cal_name.setText(listItem.getListName());



        }

        return convertView;
    }

    public void addItem(ListItem item) {
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

    public String getListStartDate(int position) {
        return items.get(position).getListStartDate();
    }

    public String getListEndDate(int position) {
        return items.get(position).getListEndDate();
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

    public void setListStartDate(int position, String listStartDate) {
        items.get(position).setListStartDate(listStartDate);
    }

    public void setListEndDate(int position, String listEndDate) {
        items.get(position).setListEndDate(listEndDate);
    }

    // items 배열 반환하는 메소드
    public ArrayList<ListItem> getListItem() { return items; }
}
