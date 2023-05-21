package com.example.for_j;

import android.app.Dialog;
import android.os.Build;
import android.view.View;
import android.widget.Button;
import android.widget.TimePicker;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;

public class SetTime extends Dialog {

    public SetTime(@NonNull Setting context, int code) {
        super(context);
        setContentView(R.layout.activity_set_time);
        TimePicker todoTP = findViewById(R.id.todoTP);
        Button set_time_submit = findViewById(R.id.set_time_submit);
        Button set_time_cancle = findViewById(R.id.set_time_cancle);

        // 팝업창의 확인 버튼을 눌렀을 때
        set_time_submit.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(View v) {
                int selectedHour = todoTP.getHour(); // 선택된 시간
                int selectedMinute = todoTP.getMinute();

                context.setValue(code, selectedHour, selectedMinute); // 다이얼 로그를 호출한 엑티비티의 해당 버튼의 값 변경
                dismiss();
            }
        });

        // 팝업창 끄기
        set_time_cancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
    }
}