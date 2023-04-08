// 리사이클러뷰에 들어가는 날짜 어댑터 -하프캘린더 어댑터
package com.example.for_j;

import android.graphics.Color;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import java.time.LocalDate;
import java.util.ArrayList;

public class HalfCalendarAdapter extends RecyclerView.Adapter<HalfCalendarAdapter.CalendarViewHolder> {
    // 하프캘린더 날짜 어뎁터 만들기
    ArrayList<LocalDate> dayList;

    // 클릭 이벤트
    public HalfCalendarAdapter(ArrayList<LocalDate> dayList) {this.dayList = dayList;}

    // 화면 연결
    @NonNull
    @Override
    public CalendarViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        View view = inflater.inflate(R.layout.half_calendar_cell, parent, false);

        return new CalendarViewHolder(view);
    }

    // 데이터 연결
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onBindViewHolder(@NonNull CalendarViewHolder holder, int position) {
        // 날짜 변수에 담기
        LocalDate day = dayList.get(position);
        // 날짜 지정
        if (day == null) {
            holder.dayText.setText("");
        } else {
            // 해당 일자 넣기
            holder.dayText.setText(String.valueOf(day.getDayOfMonth()));

            // 현재 날짜 색상 칠하기
            if (day.equals(CalendarUtill.selectedDate)) {
                holder.dayText.setTextColor(Color.WHITE); // 오늘 날짜 숫자 색상은 하얀색
                holder.parentView.setBackgroundColor(Color.BLACK); // 오늘 날짜 배경 색상은 검정색
            } else {
                holder.dayText.setTextColor(Color.BLACK); // 오늘 날짜 제외한 나머지 날짜 -> 기본 날짜 색 검정으로 지정
            }
        }

        // 토, 일 날짜 글씨 색 지정
        if ((position + 1) % 7 == 0) { // 토요일
            holder.dayText.setTextColor(Color.BLUE); // 파랑
        } else if (position == 0 || position % 7 == 0) { // 일요일
            holder.dayText.setTextColor(Color.RED); // 빨강
        }

        // 날짜 클릭 이벤트
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 인터페이스 사용 안하는 방법 - 직접 메세지 기능 구현
                int iYear = day.getYear(); // 년
                int iMonth = day.getMonthValue(); // 월
                int iDay = day.getDayOfMonth(); // 일

                String yearMonDay = iYear + "년" + iMonth + "월" + iDay + "일";

                Toast.makeText(holder.itemView.getContext(), yearMonDay, Toast.LENGTH_SHORT).show();

            }

        });
    }

    @Override
    public int getItemCount() {
        return dayList.size();
    }

    public class CalendarViewHolder extends RecyclerView.ViewHolder {
        TextView dayText;

        View parentView;

        public CalendarViewHolder(@NonNull View itemView) {
            super(itemView);

            dayText = itemView.findViewById(R.id.Calendar_DateDayText);

            // calendar_cell.xml에서 dayText의 부모 뷰
            parentView = itemView.findViewById(R.id.Calendar_DateParentView);
        }
    }


}
