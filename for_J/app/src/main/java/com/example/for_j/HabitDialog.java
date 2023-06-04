package com.example.for_j;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.RequiresApi;

import com.example.for_j.dialog.HabitDeleteCheckDialog;
import com.example.for_j.dialog.HabitDeleteDialog;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class HabitDialog {
    private final Dialog dialog;
    private final Context context;
    private final HabitListAdapter listItemAdapter;
    private final int clickedPosition;
    private final String dialogTitle;
    private final ListView listView;

    // 서버 통신 변수
    private String deleteURL;
    private ApiService deleteAPI;
    private String getSameNameURL;
    private ApiService getSameNameAPI;
    private String updateHabitURL;
    private ApiService updateHabitAPI;

    private String loginId;
    private String name;
    private String today;
    private String id;
    private int state;

    private HabitFragment parentFragment;

    public void setParentFragment(HabitFragment parentFragment){
        this.parentFragment = parentFragment;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public HabitDialog(Context context, HabitListAdapter listItemAdapter, int clickedPosition, String dialogTitle, ListView listView) {
        this.context = context;
        this.listItemAdapter = listItemAdapter;
        this.clickedPosition = clickedPosition;
        this.dialogTitle = dialogTitle;

        IdSave idSave = (IdSave) context.getApplicationContext();
        loginId = idSave.getUserId();

        // 다이얼로그 객체 생성 -  checK_box_dialog.xml설정
        dialog = new Dialog(context);
        this.listView = listView;
        dialog.setContentView(R.layout.habit_dialog);

        // 다이얼로그에 있는 'x' 버튼 클릭 시 다이얼로그 종료
        View exitBtn = dialog.findViewById(R.id.dialog_exitBtn);
        exitBtn.setOnClickListener(v -> dialog.dismiss());

        // 다이얼로그 텍스트뷰 설정
        TextView habitName = dialog.findViewById(R.id.habitDialog_listTitle);
        habitName.setText(String.valueOf(listItemAdapter.getListName(clickedPosition)));

        Button habitListModifyBtn = dialog.findViewById(R.id.habitDialog_modifyBtn);
        // todoListModifyBtn 클릭 이벤트 (다이얼로그에 있는 'TODO 수정'버튼 클릭 이벤트 -> 수진이 수정 화면으로)
        habitListModifyBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, HabitSetDateModify.class);
                // 인텐트로 이름, 날짜 보내기
                name = String.valueOf(listItemAdapter.getListName(clickedPosition));
                today = String.valueOf(listItemAdapter.getListToday(clickedPosition));
                id = String.valueOf(listItemAdapter.getListId(clickedPosition));
                intent.putExtra("title", name);
                intent.putExtra("today", today);
                intent.putExtra("id", id);
                context.startActivity(intent);
                dialog.dismiss();
            }
        });

        // 다이얼로그 삭제/수정 버튼 클릭 이벤트
        // 삭제
        View deleteBtn = dialog.findViewById(R.id.habitDialog_deleteBtn);
        deleteBtn.setOnClickListener(v -> deleteListItem());

//        // 수정
//        View updateBtn = dialog.findViewById(R.id.habitDialog_modifyBtn);
//        updateBtn.setOnClickListener(v -> modifyCHabitList());
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
    }

    public void show() {
        dialog.show();
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void deleteListItem() {

        // 오늘 날짜만 지우기
        // 다른날짜도 지우기 선택 다이얼로그 만들까..?


        // 여기서 기간이 설정된 모든 리스트 지우기
        // 같은 이름의 다른 날짜 해빗들도 같이 삭제됩니다. 그래도 삭제하시겠습니까 다이얼로그 띄우기
        // get_habit_same_name으로 다 가지고 와서 한꺼번에 삭제...

//        deleteURL = "http://203.250.133.162:8080/habitAPI/todo_delete/" + loginId + "/" + id;
        deleteAPI = new ApiService();
//        deleteAPI.deleteUrl(deleteURL);
        getSameNameAPI = new ApiService();

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Calendar calendar = Calendar.getInstance();
        Calendar Ctoday = Calendar.getInstance();
        try {
            Ctoday.setTime(sdf.parse(listItemAdapter.getListToday(clickedPosition)));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        HabitDeleteDialog HDD = new HabitDeleteDialog(context, new HabitDeleteDialog.HabitDeleteDialogListener() {
            @Override
            public void getDeletePosition(int deletePosition) {
                switch (deletePosition){
                    case 0: // 현재 날짜
                        HabitDeleteCheckDialog HDCD1 = new HabitDeleteCheckDialog(context, new HabitDeleteCheckDialog.HabitDeleteCheckDialogListener() {
                            @Override
                            public void IsPositive(int isPositive) {
                                if (isPositive == 1){
                                    // 삭제 메소드 실행
                                    deleteURL = "http://203.250.133.162:8080/habitAPI/habit_delete_with_id/" + loginId + "/" + listItemAdapter.getListId(clickedPosition);
                                    deleteAPI.deleteUrl(deleteURL);
                                }
                            }
                        }, deletePosition);
                        HDCD1.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
                        // 다이얼로그 밖을 터치했을 때, 다이얼로그 꺼짐
                        HDCD1.setCanceledOnTouchOutside(true);
                        HDCD1.setCancelable(true);
                        HDCD1.setParentFragment(parentFragment);
                        HDCD1.show();
                        break;
                    case 1: // 이전 날짜
                        HabitDeleteCheckDialog HDCD2 = new HabitDeleteCheckDialog(context, new HabitDeleteCheckDialog.HabitDeleteCheckDialogListener() {
                            @Override
                            public void IsPositive(int isPositive) throws ParseException {
                                if (isPositive == 1){
//                                    private String getSameNameURL;
//                                    private ApiService getSameNameAPI;

                                    /*
                                    5.1 - 5.31

                                    현재 날짜 5.14

                                    이전 삭제 5.15 - 5.31
                                    이후 삭제 5.1-5.13
                                     */
                                    getSameNameURL = "http://203.250.133.162:8080/habitAPI/get_habit_same_name/" + loginId + "/" + listItemAdapter.getListName(clickedPosition) + "/" + listItemAdapter.getListStartDate(clickedPosition) + "/" + listItemAdapter.getListEndDate(clickedPosition);
                                    System.out.println("get_habit_same_name: " + getSameNameURL);
                                    getSameNameAPI.getUrl(getSameNameURL);

                                    for (int i = 0; i < Integer.parseInt(getSameNameAPI.getValue("habit_today_total")); i++){
                                        System.out.println("현재 날짜: " + Ctoday.getTime());
                                        calendar.setTime(sdf.parse(getSameNameAPI.getValue("habit_today"+i)));
                                        System.out.println("저정된 today: " + calendar.getTime());

                                        if (calendar.compareTo(Ctoday) <= 0) {  // 캘린더가 투데이보다 이전이거나 같은 경우
                                            deleteURL = "http://203.250.133.162:8080/habitAPI/habit_delete_with_id/" + loginId + "/" + getSameNameAPI.getValue("habit_list_id"+i);
                                            System.out.println("habit_delete_with_id: " + deleteURL);
                                            deleteAPI.deleteUrl(deleteURL);
                                        }else{
                                            // 시작 날짜 업데이트
                                            updateHabitURL = "http://203.250.133.162:8080/habitAPI/update_startDate/" + loginId + "/" + getSameNameAPI.getValue("habit_list_id") + "/" + today;
                                            updateHabitAPI = new ApiService();
                                            updateHabitAPI.putUrl(updateHabitURL);
                                        }
                                    }




                                }
                            }
                        }, deletePosition);
                        HDCD2.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
                        // 다이얼로그 밖을 터치했을 때, 다이얼로그 꺼짐
                        HDCD2.setCanceledOnTouchOutside(true);
                        HDCD2.setCancelable(true);
                        HDCD2.setParentFragment(parentFragment);
                        HDCD2.show();
                        break;
                    case 2: // 이후 날짜
                        HabitDeleteCheckDialog HDCD3 = new HabitDeleteCheckDialog(context, new HabitDeleteCheckDialog.HabitDeleteCheckDialogListener() {
                            @Override
                            public void IsPositive(int isPositive) throws ParseException {
                                if (isPositive == 1){
                                    getSameNameURL = "http://203.250.133.162:8080/habitAPI/get_habit_same_name/" + loginId + "/" + listItemAdapter.getListName(clickedPosition) + "/" + listItemAdapter.getListStartDate(clickedPosition) + "/" + listItemAdapter.getListEndDate(clickedPosition);
                                    System.out.println("get_habit_same_name: " + getSameNameURL);
                                    getSameNameAPI.getUrl(getSameNameURL);

                                    for (int i = 0; i < Integer.parseInt(getSameNameAPI.getValue("habit_today_total"));i++){
                                        calendar.setTime(sdf.parse(getSameNameAPI.getValue("habit_today"+i)));
                                        System.out.println("현재 날짜: " + Ctoday.getTime());
                                        System.out.println("저정된 today: " + calendar.getTime());
                                        if (calendar.compareTo(Ctoday) >= 0) {  // 캘린더가 투데이보다 이후이거나 같은 경우
                                            deleteURL = "http://203.250.133.162:8080/habitAPI/habit_delete_with_id/" + loginId + "/" + getSameNameAPI.getValue("habit_list_id"+i);
                                            System.out.println("habit_delete_with_id: " + deleteURL);
                                            deleteAPI.deleteUrl(deleteURL);
                                        }else{
                                            // 끝 날짜 업데이트
                                            updateHabitURL = "http://203.250.133.162:8080/habitAPI/update_endDate/" + loginId + "/" + getSameNameAPI.getValue("habit_list_id") + "/" + today;
                                            updateHabitAPI = new ApiService();
                                            updateHabitAPI.putUrl(updateHabitURL);
                                        }
                                    }
                                }
                            }
                        }, deletePosition);
                        HDCD3.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
                        // 다이얼로그 밖을 터치했을 때, 다이얼로그 꺼짐
                        HDCD3.setCanceledOnTouchOutside(true);
                        HDCD3.setCancelable(true);
                        HDCD3.setParentFragment(parentFragment);
                        HDCD3.show();
                        break;
                    case 3: // 전체 삭제
                        HabitDeleteCheckDialog HDCD4 = new HabitDeleteCheckDialog(context, new HabitDeleteCheckDialog.HabitDeleteCheckDialogListener() {
                            @Override
                            public void IsPositive(int isPositive) {
                                if (isPositive == 1){

                                    getSameNameURL = "http://203.250.133.162:8080/habitAPI/get_habit_same_name/" + loginId + "/" + listItemAdapter.getListName(clickedPosition) + "/" + listItemAdapter.getListStartDate(clickedPosition) + "/" + listItemAdapter.getListEndDate(clickedPosition);
                                    System.out.println("get_habit_same_name: " + getSameNameURL);
                                    getSameNameAPI.getUrl(getSameNameURL);

                                    // 전체 날짜 삭제
                                    for (int i = 0; i < Integer.parseInt(getSameNameAPI.getValue("habit_today_total"));i++){
                                        deleteURL = "http://203.250.133.162:8080/habitAPI/habit_delete_with_id/" + loginId + "/" + getSameNameAPI.getValue("habit_list_id"+i);
                                        System.out.println("habit_delete_with_id: " + deleteURL);
                                        deleteAPI.deleteUrl(deleteURL);
                                    }
                                }
                            }
                        }, deletePosition);
                        HDCD4.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
                        // 다이얼로그 밖을 터치했을 때, 다이얼로그 꺼짐
                        HDCD4.setCanceledOnTouchOutside(true);
                        HDCD4.setCancelable(true);
                        HDCD4.setParentFragment(parentFragment);
                        HDCD4.show();
                        break;
                }
            }
        });
        HDD.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        // 다이얼로그 밖을 터치했을 때, 다이얼로그 꺼짐
        HDD.setCanceledOnTouchOutside(true);
        HDD.setCancelable(true);
        HDD.show();


//        if (parentFragment != null){
//            parentFragment.onResume();
//        }

        dialog.dismiss();
    }

    private void modifyCHabitList() {

        dialog.dismiss();
    }
}
