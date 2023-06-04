package com.example.for_j;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.RequiresApi;

import java.util.ArrayList;

public class TimeTrackerListDialog {
    private final Dialog dialog;
    private final Context context;
    private final TimeListItemAdapter listItemAdapter;
    private final int clickedPosition;
    private final String dialogTitle;
    private final ListView listView;
    //private final TextView listCountText;

    // 서버 통신 변수
    private String getStateURL;
    private String setStateURL;
    private String deleteURL;
    private ApiService getStateAPI;
    private ApiService setStateAPI;
    private ApiService deleteAPI;

    private String loginId;
    private String name;
    private String today;
    private String id;
    private int state;

    private TimeFragment parentFragment;

    public void setParentFragment(TimeFragment parentFragment){
        this.parentFragment = parentFragment;
    }

    //public TimeTrackerListDialog(Context context, TimeListItemAdapter listItemAdapter, TextView listCountText, int clickedPosition, String dialogTitle, ListView listView) {
    @RequiresApi(api = Build.VERSION_CODES.O)
    public TimeTrackerListDialog(Context context, TimeListItemAdapter listItemAdapter, int clickedPosition, String dialogTitle, ListView listView) {
        this.context = context;
        this.listItemAdapter = listItemAdapter;
        //this.listCountText = listCountText;
        this.clickedPosition = clickedPosition;
        this.dialogTitle = dialogTitle;

        IdSave idSave = (IdSave) context.getApplicationContext();
        loginId = idSave.getUserId();

        // 다이얼로그 객체 생성 -  time_dialog.xml설정
        dialog = new Dialog(context);
        this.listView = listView;
        dialog.setContentView(R.layout.time_dialog);

        // 다이얼로그에 있는 'x' 버튼 클릭 시 다이얼로그 종료
        ImageButton exitBtn = dialog.findViewById(R.id.dialog_exitBtn);
        exitBtn.setOnClickListener(v -> dialog.dismiss());

        // 다이얼로그 텍스트뷰 설정
        TextView title = dialog.findViewById(R.id.dialog_categoryTitle);
        TextView timeName = dialog.findViewById(R.id.timeDialog_listTitle);

        title.setText(dialogTitle);
        timeName.setText(String.valueOf(listItemAdapter.getListName(clickedPosition)));


        id = String.valueOf(listItemAdapter.getListId(clickedPosition));

        // 다이얼로그 삭제/수정 버튼 클릭 이벤트
        // 삭제
        Button deleteBtn = dialog.findViewById(R.id.timeDialog_deleteBtn);
        deleteBtn.setOnClickListener(v -> deleteListItem());

        // 수정
        Button updateBtn = dialog.findViewById(R.id.timeDialog_updateBtn);
        updateBtn.setOnClickListener(v -> modifyCHabitList());
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
    }

    public void show() {
        dialog.show();
    }

    private void deleteListItem() {
        deleteURL = "http://203.250.133.162:8080/timeAPI/time_delete/" + loginId + "/" + id;
        deleteAPI = new ApiService();
        deleteAPI.deleteUrl(deleteURL);

//        ArrayList<ListItem> listItems = listItemAdapter.getListItem();
//
//        listItems.remove(clickedPosition);
//        listItemAdapter.notifyDataSetChanged();
        //listCountText.setText(String.valueOf(listItemAdapter.getCount()));

        dialog.dismiss();
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void modifyCHabitList() {
        Intent intent = new Intent(context, TimeSetDateModify.class);
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
}