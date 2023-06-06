package com.example.for_j;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.RequiresApi;

import com.example.for_j.dialog.CalDeleteCheckDialog;

public class CalDialog {
    private final Dialog dialog;
    private final Context context;

    // calendar 알림 변수
    private CalAlarm CalAlarm;

    // 서버 통신 변수
    private String deleteURL;
    private ApiService deleteAPI;

    private final String loginId;
    private final String name;
    private final String id;

    private HalfCalendarFragment parentFragment;

    public void setParentFragment(HalfCalendarFragment parentFragment){
        this.parentFragment = parentFragment;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public CalDialog(Context context, CalListAdapter listItemAdapter, int clickedPosition) {
        this.context = context;

        IdSave idSave = (IdSave) context.getApplicationContext();
        loginId = idSave.getUserId();

        name = String.valueOf(listItemAdapter.getListName(clickedPosition));
        id = String.valueOf(listItemAdapter.getListId(clickedPosition));

        // CalAlarm 객체 초기화
        CalAlarm = new CalAlarm(context);

        // 다이얼로그 객체 생성 -  check_box_dialog.xml설정
        dialog = new Dialog(context);
        dialog.setContentView(R.layout.cal_dialog);

        // 다이얼로그에 있는 'x' 버튼 클릭 시 다이얼로그 종료
        View exitBtn = dialog.findViewById(R.id.dialog_exitBtn);
        exitBtn.setOnClickListener(v -> dialog.dismiss());

        // 다이얼로그 텍스트뷰 설정
        TextView habitName = dialog.findViewById(R.id.calDialog_listTitle);
        habitName.setText(String.valueOf(listItemAdapter.getListName(clickedPosition)));

        Button habitListModifyBtn = dialog.findViewById(R.id.calDialog_modifyBtn);
        // todoListModifyBtn 클릭 이벤트 (다이얼로그에 있는 'TODO 수정'버튼 클릭 이벤트 -> 수진이 수정 화면으로)
        habitListModifyBtn.setOnClickListener(v -> {
            Intent intent = new Intent(context, CalSetDateModify.class);
            // 인텐트로 이름, 날짜 보내기
            intent.putExtra("title", name);
            intent.putExtra("id", id);
            context.startActivity(intent);
            dialog.dismiss();
        });

        // 다이얼로그 삭제/수정 버튼 클릭 이벤트
        // 삭제
        View deleteBtn = dialog.findViewById(R.id.calDialog_deleteBtn);
        deleteBtn.setOnClickListener(v -> deleteListItem());

        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
    }

    public void show() {
        dialog.show();
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void deleteListItem() {
        CalDeleteCheckDialog CDCD = new CalDeleteCheckDialog(context, isPositive -> {
            if (isPositive == 1){
                // 삭제 메소드 실행
                deleteURL = "http://203.250.133.162:8080/calendarAPI/cal_delete/" + loginId + "/" + id;
                System.out.println(deleteURL);
                deleteAPI = new ApiService();
                deleteAPI.deleteUrl(deleteURL);

                Log.d("id",id);
                CalAlarm.cancelAlarm(id);

            }
        });

        CDCD.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        // 다이얼로그 밖을 터치했을 때, 다이얼로그 꺼짐
        CDCD.setCanceledOnTouchOutside(true);
        CDCD.setCancelable(true);
        CDCD.setParentFragment(parentFragment);
        CDCD.show();

        if (parentFragment != null){
            parentFragment.onResume();
        }

        dialog.dismiss();
    }
}
