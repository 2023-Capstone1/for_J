package com.example.for_j;

import android.content.Context;
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

public class CalTodoListAdapter extends BaseAdapter {

    public ArrayList<ListItem> items = new ArrayList<>();
    Context context;

    private HalfCalendarFragment parentFragment;

    public interface TodoListAdapterListener {
        void onCheckButtonClicked(int position, CalTodoDialog todoDialog);
    }

    private TodoListAdapterListener mListener;

    public void setListener(TodoListAdapterListener listener) {
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
            convertView = inflater.inflate(R.layout.listview_cal_todo_item, parent, false);

            int state = items.get(position).getListState();

            Drawable selectedImg = null;

            switch (state) {
                case 0:
                    selectedImg = context.getResources().getDrawable(R.drawable.ic_check_box_empty);
                    break;
                case 1:
                    selectedImg = context.getResources().getDrawable(R.drawable.ic_check_box_delay);
                    break;
                case 2:
                    selectedImg = context.getResources().getDrawable(R.drawable.ic_check_box_incomplete);
                    break;
                case 3:
                    selectedImg = context.getResources().getDrawable(R.drawable.ic_check_box_complete);
                    break;
                default:
                    state = 0;
                    selectedImg = context.getResources().getDrawable(R.drawable.ic_check_box_empty);
                    break;
            }
            ImageView imageView = convertView.findViewById(R.id.listCheckBtn);
            imageView.setImageDrawable(selectedImg);

            TextView cal_name = convertView.findViewById(R.id.cal_name);
            cal_name.setText(items.get(position).getListName());

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

            // ImageView에 OnClickListener 추가
            imageView.setOnClickListener(new View.OnClickListener() {
                @RequiresApi(api = Build.VERSION_CODES.O)
                @Override
                public void onClick(View v) {
                    CalTodoDialog dialog = new CalTodoDialog(context, CalTodoListAdapter.this, position, "To-Do", (ListView)parent);
//                System.out.println("mListener 투두 쪽 실행됨1");
                    if (mListener != null) {
//                    System.out.println("mListener 투두 쪽 실행됨2");
                        mListener.onCheckButtonClicked(position, dialog);
                        dialog.show();

                    }
                }
            });

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
    public ArrayList<ListItem> getListItem() { return items; }
}
