// 투두 리스트뷰 클릭 시 보여지는 다이얼로그 메소드(showCheckDialog, delete_listItem, modify_checkBox) 클래스로 분리함
package com.example.for_j;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import java.util.ArrayList;


public class ToDoListDialog {
    private final Dialog dialog;
    private final Context context;
    private final ListItemAdapter listItemAdapter;
    private final int clickedPosition;
    private final String dialogTitle;
    private final ListView listView;

    // 서버 통신 변수
    private String getStateURL;
    private String setStateURL;
    private String deleteURL;
    private ApiService getStateAPI;
    private ApiService setStateAPI;
    private ApiService deleteAPI;

    private String loginId = "123";
    private String name;
    private String today;
    private String id;
    private int state;

    public ToDoListDialog(Context context, ListItemAdapter listItemAdapter, int clickedPosition, String dialogTitle, ListView listView) {
        this.context = context;
        this.listItemAdapter = listItemAdapter;
        this.clickedPosition = clickedPosition;
        this.dialogTitle = dialogTitle;

        // 다이얼로그 객체 생성 -  checK_box_dialog.xml설정
        dialog = new Dialog(context);
        this.listView = listView;
        dialog.setContentView(R.layout.check_box_dialog);

        // 다이얼로그에 있는 'x' 버튼 클릭 시 다이얼로그 종료
        View exitBtn = dialog.findViewById(R.id.dialog_exitBtn);
        exitBtn.setOnClickListener(v -> dialog.dismiss());

        // 다이얼로그 텍스트뷰 설정
        TextView title = dialog.findViewById(R.id.dialog_categoryTitle);
        TextView todoName = dialog.findViewById(R.id.listTitle);

        title.setText(dialogTitle);
        todoName.setText(String.valueOf(listItemAdapter.getListName(clickedPosition)));


        // 다이얼로그 내 체크박스 이미지 선택 시 리스트의 체크박스 이미지 선택한 이미지로 변경
        RadioGroup radioGroup = dialog.findViewById(R.id.dialog_checkRadioGroup);
        RadioButton complete = dialog.findViewById(R.id.dialog_checkComplete);
        RadioButton delay = dialog.findViewById(R.id.dialog_checkDelay);
        RadioButton incomplete = dialog.findViewById(R.id.dialog_checkIncomplete);
        RadioButton empty = dialog.findViewById(R.id.dialog_checkEmpty);

        // 서버에서 state 가지고 와서 버튼 이미지 바꾸기
        name = String.valueOf(listItemAdapter.getListName(clickedPosition));
        today = String.valueOf(listItemAdapter.getListToday(clickedPosition));
        id = String.valueOf(listItemAdapter.getListId(clickedPosition));

        getStateURL = "http://203.250.133.162:8080/todoAPI/get_todo_list_state/" + loginId + "/" + id + "/" + name + "/" + today;
        getStateAPI = new ApiService();
        getStateAPI.getUrl(getStateURL);

        state = Integer.parseInt(getStateAPI.getValue("todo_state"));
        System.out.println("상태: " + state);

        switch (state){
            case 0:
                empty.setChecked(true);
                break;
            case 1:
                delay.setChecked(true);
                break;
            case 2:
                incomplete.setChecked(true);
                break;
            case 3:
                complete.setChecked(true);
                break;
        }

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int witch) {
                modifyCheckBoxImg(radioGroup);
            }
        });



        Button todoListModifyBtn = dialog.findViewById(R.id.dialog_todo_list_modify);
        // todoListModifyBtn 클릭 이벤트 (다이얼로그에 있는 'TODO 수정'버튼 클릭 이벤트 -> 수진이 수정 화면으로)
        todoListModifyBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, TodoSetDateModify.class);
                // 인텐트로 이름, 날짜 보내기
                String name = String.valueOf(listItemAdapter.getListName(clickedPosition));
                String today = String.valueOf(listItemAdapter.getListToday(clickedPosition));
                String id = String.valueOf(listItemAdapter.getListId(clickedPosition));
                intent.putExtra("title", name);
                intent.putExtra("today", today);
                intent.putExtra("id", id);
                context.startActivity(intent);
                dialog.dismiss();
            }
        });




        // 다이얼로그 삭제/수정 버튼 클릭 이벤트
        // 삭제
        View deleteBtn = dialog.findViewById(R.id.dialog_deleteBtn);
        deleteBtn.setOnClickListener(v -> deleteListItem());

//        // 수정
//        RadioGroup radioGroup = dialog.findViewById(R.id.dialog_checkRadioGroup);
//        View updateBtn = dialog.findViewById(R.id.dialog_updateBtn);
//        updateBtn.setOnClickListener(v -> modifyCheckBoxImg(radioGroup));
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

    }

    public void show() {
        dialog.show();
    }

    private void deleteListItem() {
        deleteURL = "http://203.250.133.162:8080/todoAPI/todo_delete/" + loginId + "/" + id;
        deleteAPI = new ApiService();
        deleteAPI.deleteUrl(deleteURL);

//        ArrayList<ListItem> listItems = listItemAdapter.getListItem();
//
//        listItems.remove(clickedPosition);
//        listItemAdapter.notifyDataSetChanged();
        //listCountText.setText(String.valueOf(listItemAdapter.getCount()));

        dialog.dismiss();
    }

    private void modifyCheckBoxImg(RadioGroup radioGroup) {
        int selectedRadio = radioGroup.getCheckedRadioButtonId();
        Drawable selectedImg = null;

        switch (selectedRadio) {
            case R.id.dialog_checkComplete:
                selectedImg = context.getResources().getDrawable(R.drawable.ic_check_box_complete);
                state = 3;
                break;
            case R.id.dialog_checkDelay:
                selectedImg = context.getResources().getDrawable(R.drawable.ic_check_box_delay);
                state = 1;
                break;
            case R.id.dialog_checkIncomplete:
                selectedImg = context.getResources().getDrawable(R.drawable.ic_check_box_incomplete);
                state = 2;
                break;
            case R.id.dialog_checkEmpty:
                selectedImg = context.getResources().getDrawable(R.drawable.ic_check_box_empty);
                state = 0;
                break;
            default:
                selectedImg = context.getResources().getDrawable(R.drawable.ic_check_box_empty);
                state = 0;
                break;
        }


        // 디비 상태값 업데이트
        setStateURL = "http://203.250.133.162:8080/todoAPI/update_todo_list_state/" + loginId + "/" + id + "/" + name + "/" + today + "/" + state;
        setStateAPI = new ApiService();
        setStateAPI.putUrl(setStateURL);

        View listItemView = listView.getChildAt(clickedPosition); // 매개변수로 받은 listView에서 지금 클릭한 리스트 아이템 찾기
        ImageView itemImgView = listItemView.findViewById(R.id.listCheckBtn); // 지금 클릭한 리스트 아이템의 체크박스 라디오버튼 연결

        itemImgView.setImageDrawable(selectedImg);
//        itemImgView.setBackgroundColor(Color.parseColor("#D9D9D9"));

        dialog.dismiss();
    }
}