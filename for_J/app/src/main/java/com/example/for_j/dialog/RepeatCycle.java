package com.example.for_j.dialog;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.NumberPicker;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatCheckBox;
import androidx.fragment.app.DialogFragment;

import com.example.for_j.R;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;

public class RepeatCycle extends DialogFragment {

    // 요일/n회 반복 버튼
    private AppCompatButton RC_DateOfWeek, RC_WeekNRepeat;
    private LinearLayout RC_DRC, RC_WNR;
    // 요일, n회 어떤 것이 선택되었는지 확인하는 변수
    private Boolean isWeekClick = true;    // ture: RC_DateofWeek false: RC_WeekNRepeat
    // n회 숫자 저장 변수
    private NumberPicker RC_WNRnum;

    // 요일 체크 박스
    private AppCompatCheckBox DRC_Sun,
            DRC_Mon,
            DRC_Tue,
            DRC_Wed,
            DRC_Thu,
            DRC_Fri,
            DRC_Sat;


    // 취소 버튼
    private ImageView RC_Close;
    // 저장 버튼
    private AppCompatButton RC_Save;


    private Context context;
    private RepeatDialogListener repeatDialogListener;
    public RepeatCycle(Context context, RepeatDialogListener repeatDialogListener) {
        super();
        this.context = context;
        this.repeatDialogListener = repeatDialogListener;
    }
    public interface RepeatDialogListener {
        void getRepeatData(String dayofWeek, int repeatN, boolean isWeekClick);
    }


    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        LayoutInflater inflater = requireActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.repeat_cycle, null);

        RC_DateOfWeek = view.findViewById(R.id.RC_DateofWeek);
        RC_WeekNRepeat = view.findViewById(R.id.RC_WeekNRepeat);
        RC_DRC = view.findViewById(R.id.RC_DRC);
        RC_WNR = view.findViewById(R.id.RC_WNR);

        DRC_Sun = view.findViewById(R.id.DRC_Sun);
        DRC_Mon = view.findViewById(R.id.DRC_Mon);
        DRC_Tue = view.findViewById(R.id.DRC_Tue);
        DRC_Wed = view.findViewById(R.id.DRC_Wed);
        DRC_Thu = view.findViewById(R.id.DRC_Thu);
        DRC_Fri = view.findViewById(R.id.DRC_Fri);
        DRC_Sat = view.findViewById(R.id.DRC_Sat);

        RC_DateOfWeek.setSelected(true);

        RC_WNRnum = view.findViewById(R.id.RC_WNRnum);
        RC_WNRnum.setMaxValue(7);   // 최댓값
        RC_WNRnum.setMinValue(0);   // 최솟값
        RC_WNRnum.setValue(0);  // 초기값
        // 최대값 or 최소값에서 멈출지 넘어갈지
        RC_WNRnum.setWrapSelectorWheel(true);
        //길게 눌렀을 때 몇 초부터 반응?
        RC_WNRnum.setOnLongPressUpdateInterval(100);


        // 요일별로 반복
        RC_DateOfWeek.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isWeekClick = true;
                RC_DRC.setVisibility(View.VISIBLE);
                RC_WNR.setVisibility(View.GONE);

                // Change button colors
                RC_DateOfWeek.setSelected(true);
                RC_WeekNRepeat.setSelected(false);
            }
        });

        // 주 n회 반복
        RC_WeekNRepeat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isWeekClick = false;
                RC_DRC.setVisibility(View.GONE);
                RC_WNR.setVisibility(View.VISIBLE);

                // Change button colors
                RC_DateOfWeek.setSelected(false);
                RC_WeekNRepeat.setSelected(true);

            }
        });


        // x 버튼
        RC_Close = view.findViewById(R.id.RC_Close);
        // 저장 버튼
        RC_Save = view.findViewById(R.id.RC_Save);

        // x 버튼 클릭 시 다이얼로그 종료
        RC_Close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 전 화면으로 이동
                dismiss();
            }
        });


        // 저장 버튼 누를 시
        RC_Save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String dayofWeek = "";
                if (DRC_Sun.isChecked()) {
                    dayofWeek += DRC_Sun.getText().toString();
                }
                if (DRC_Mon.isChecked()) {
                    dayofWeek += DRC_Mon.getText().toString();
                }
                if (DRC_Tue.isChecked()) {
                    dayofWeek += DRC_Tue.getText().toString();
                }
                if (DRC_Wed.isChecked()) {
                    dayofWeek += DRC_Wed.getText().toString();
                }
                if (DRC_Thu.isChecked()) {
                    dayofWeek += DRC_Thu.getText().toString();
                }
                if (DRC_Fri.isChecked()) {
                    dayofWeek += DRC_Fri.getText().toString();
                }
                if (DRC_Sat.isChecked()) {
                    dayofWeek += DRC_Sat.getText().toString();
                }

                int repeatN = RC_WNRnum.getValue();


                repeatDialogListener.getRepeatData(dayofWeek, repeatN, isWeekClick);
                dismiss();
            }
        });


        MaterialAlertDialogBuilder builder = new MaterialAlertDialogBuilder(requireActivity());
        builder.setView(view);

        Dialog dialog = builder.create();
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        return dialog;
    }
}
