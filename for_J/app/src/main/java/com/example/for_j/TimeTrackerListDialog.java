package com.example.for_j;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class TimeTrackerListDialog {
    private final Dialog dialog;
    private final Context context;
    private final TimeListItemAdapter listItemAdapter;
    private final TextView listCountText;
    private final int clickedPosition;
    private final String dialogTitle;
    private final ListView listView;


    public TimeTrackerListDialog(Context context, TimeListItemAdapter listItemAdapter, TextView listCountText, int clickedPosition, String dialogTitle, ListView listView) {
        this.context = context;
        this.listItemAdapter = listItemAdapter;
        this.listCountText = listCountText;
        this.clickedPosition = clickedPosition;
        this.dialogTitle = dialogTitle;

        // 다이얼로그 객체 생성 -  checK_box_dialog.xml설정
        dialog = new Dialog(context);
        this.listView = listView;
        dialog.setContentView(R.layout.time_dialog);

        // 다이얼로그에 있는 'x' 버튼 클릭 시 다이얼로그 종료
        View exitBtn = dialog.findViewById(R.id.dialog_exitBtn);
        exitBtn.setOnClickListener(v -> dialog.dismiss());

        // 다이얼로그 텍스트뷰 설정
        TextView timeName = dialog.findViewById(R.id.timeDialog_listTitle);

        timeName.setText(String.valueOf(listItemAdapter.getListName(clickedPosition)));

        // 다이얼로그 삭제/수정 버튼 클릭 이벤트
        // 삭제
        View deleteBtn = dialog.findViewById(R.id.timeDialog_deleteBtn);
        deleteBtn.setOnClickListener(v -> deleteListItem());

        // 수정
        View updateBtn = dialog.findViewById(R.id.timeDialog_updateBtn);
        updateBtn.setOnClickListener(v -> modifyCHabitList());
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
    }

    public void show() {
        dialog.show();
    }

    private void deleteListItem() {
        ArrayList<ListItem> listItems = listItemAdapter.getListItem();

        listItems.remove(clickedPosition);
        listItemAdapter.notifyDataSetChanged();
        listCountText.setText(String.valueOf(listItemAdapter.getCount()));

        dialog.dismiss();
    }

    private void modifyCHabitList() {

        dialog.dismiss();
    }
}