package com.example.for_j;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;

import java.util.ArrayList;

public class ListItemAdapter extends BaseAdapter {

    public ArrayList<ListItem> items = new ArrayList<>();
    Context context;

    private ToDoFragment parentFragment;

    public interface ToDoListAdapterListener {
        void onCheckButtonClicked(int position, ToDoListDialog toDoListDialog);
    }

    private ListItemAdapter.ToDoListAdapterListener mListener;

    public void setListener(ListItemAdapter.ToDoListAdapterListener listener) {
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
        ListItem listItem = items.get(position);

        // listview_item.xml inflate 해서 convertView 참조
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.listview_item, parent, false);

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

        /*
        0: 빈칸 1: 다음날 2: 안함 3: 체크
         */
        /*// 서버에서 state 가지고 와서 버튼 이미지 바꾸기
        String loginId = "123";
        String name = items.get(position).getListName();
        String today = items.get(position).getListToday();
        String id = items.get(position).getListId();*/
        int state = items.get(position).getListState();

        /*String getStateURL = "http://203.250.133.162:8080/todoAPI/get_todo_list_state/" + loginId + "/" + id + "/" + name + "/" + today;
        ApiService getStateAPI = new ApiService();
        getStateAPI.getUrl(getStateURL);
        state = Integer.parseInt(getStateAPI.getValue("todo_state"));
        items.get(position).setListState(state);*/


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
//        System.out.println("어뎁터에서 이미지 바뀌는거 실행됨");

        // ImageView에 OnClickListener 추가
        imageView.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View v) {
                ToDoListDialog dialog = new ToDoListDialog(context, ListItemAdapter.this, position, "To-Do", (ListView)parent);
//                System.out.println("mListener 투두 쪽 실행됨1");
                if (mListener != null) {
//                    System.out.println("mListener 투두 쪽 실행됨2");
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
