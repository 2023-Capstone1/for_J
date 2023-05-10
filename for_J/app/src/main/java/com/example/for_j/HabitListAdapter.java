package com.example.for_j;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.widget.AppCompatButton;
import androidx.core.content.ContextCompat;

import java.util.ArrayList;

public class HabitListAdapter extends BaseAdapter {

    public ArrayList<ListItem> items = new ArrayList<>();
    Context context;

    private HabitFragment parentFragment;

    public interface HabitListAdapterListener {
        void onCheckButtonClicked(int position, HabitDialog habitDialog);
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
            convertView = inflater.inflate(R.layout.listview_habit_item, parent, false);

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
        listNameText.setText(listItem.getListName());

        // 서버에서 state 가지고 와서 버튼 이미지 바꾸기
        String loginId = "123";
        String name = items.get(position).getListName();
        String today = items.get(position).getListToday();
        String id = items.get(position).getListId();
        int state = items.get(position).getListState();

        String getStateURL = "http://203.250.133.162:8080/habitAPI/get_habit_state/" + loginId + "/" + id + "/" + name + "/" + today;
        ApiService getStateAPI = new ApiService();
        getStateAPI.getUrl(getStateURL);
        state = Integer.parseInt(getStateAPI.getValue("habit_state"));
        items.get(position).setListState(state);

        ImageView listCheckBtn = convertView.findViewById(R.id.listCheckBtn);
        Drawable btnDrawable;

        switch(state){
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

        // ImageView에 OnClickListener 추가
        listCheckBtn.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View v) {
                HabitDialog dialog = new HabitDialog(context, HabitListAdapter.this, position, "HABIT", (ListView)parent);
                System.out.println("mListener 투두 쪽 실행됨1");
                if (mListener != null) {
                    System.out.println("mListener 투두 쪽 실행됨2");
                    mListener.onCheckButtonClicked(position, dialog);
                    dialog.show();
                }
            }
        });

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
