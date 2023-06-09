// 리사이클러뷰에 들어가는 날짜 어댑터 -풀캘린더 어댑터
package com.example.for_j;

import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class CalendarAdapter extends RecyclerView.Adapter<CalendarAdapter.CalendarViewHolder> {

    private Context context;
    ArrayList<LocalDate> dayList;

    public CalendarAdapter(Context context, ArrayList<LocalDate> dayList) {
        this.context = context;
        this.dayList = dayList;
    }

    private Calendar calendar = Calendar.getInstance();

    private CalendarFragment parentFragment;

    public void setParentFragment(CalendarFragment parentFragment){
        this.parentFragment = parentFragment;
    }

    // 날짜 xml 화면 연결
    @NonNull
    @Override
    public CalendarViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        View view = inflater.inflate(R.layout.calendar_cell, parent, false);

        return new CalendarViewHolder(view);
    }

    // 데이터 연결
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onBindViewHolder(@NonNull CalendarViewHolder holder, int position) {
        // 날짜 변수에 담기
        LocalDate day = dayList.get(position);
        // Calendar를 LocalDate와 비교하기 형태 맞추기
        Date date = calendar.getTime();
        Instant instant = date.toInstant();
        ZonedDateTime zonedDateTime = instant.atZone(ZoneId.systemDefault());
        LocalDate calendarDate = zonedDateTime.toLocalDate();

        // 날짜 지정
        if (day == null) {
            holder.dayText.setText("");
        } else {
            // 해당 일자 넣기
            holder.dayText.setText(String.valueOf(day.getDayOfMonth()));

            if (day.equals(calendarDate)){
                // 오늘날짜
                holder.dayText.setTextColor(ContextCompat.getColor(parentFragment.getContext(), R.color.indigo_100)); // 오늘 날짜 숫자 색상은 하얀색
                holder.parentView.setBackgroundResource(R.drawable.unselected_today_background_shape); //오늘 날짜 배경 모양
            }
            else{
                holder.dayText.setTextColor(Color.BLACK); // 선택 날짜 제외한 나머지 날짜 -> 기본 날짜 색 검정으로 지정
            }

            // 선택 날짜 색상 칠하기
            if (day.equals(CalendarUtill.selectedDate)) {
//                System.out.println(day + " " + calendar.getTime());
                if (day.equals(calendarDate)){
                    // 오늘날짜
                    holder.dayText.setTextColor(ContextCompat.getColor(parentFragment.getContext(), R.color.indigo_100)); // 오늘 날짜 숫자 색상은 하얀색
                    holder.parentView.setBackgroundResource(R.drawable.selected_today_background_shape); //오늘 날짜 배경 모양
                } else{
                    // 다른 날짜
                    holder.dayText.setTextColor(ContextCompat.getColor(parentFragment.getContext(), R.color.indigo_400)); // 오늘 날짜 숫자 색상은 하얀색
                    holder.parentView.setBackgroundResource(R.drawable.selected_date_background_shape); //오늘 날짜 배경 모양
                }
            }
            // 토, 일 날짜 글씨 색 지정
            else if (((position + 1) % 7 == 0) && day.equals(CalendarUtill.selectedDate) ) { // 토요일
                holder.dayText.setTextColor(Color.BLUE); // 파랑
//                System.out.println("파란색 토요일");
            } else if (position == 0 || position % 7 == 0  && day.equals(CalendarUtill.selectedDate)) { // 일요일
                holder.dayText.setTextColor(Color.RED); // 빨강
//                System.out.println("빨간색 일요일");
            }
            /*else {
                if (day.equals(calendarDate)){
                    // 오늘날짜
                    holder.dayText.setTextColor(ContextCompat.getColor(parentFragment.getContext(), R.color.indigo_100)); // 오늘 날짜 숫자 색상은 하얀색
                    holder.parentView.setBackgroundResource(R.drawable.unselected_today_background_shape); //오늘 날짜 배경 모양
                }
                else{
                    holder.dayText.setTextColor(Color.BLACK); // 선택 날짜 제외한 나머지 날짜 -> 기본 날짜 색 검정으로 지정
                }
            }*/
        }

        // 날짜 클릭 이벤트
        // 다른 날짜 클릭 시 해당 날짜의 to-do/habit 보여주기
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View view) {
                LocalDate clickedDate = dayList.get(holder.getAdapterPosition());

                if (clickedDate != null){
                    CalendarUtill.selectedDate = clickedDate;
                    notifyDataSetChanged();

                    if (parentFragment != null){
                        HalfCalendarFragment halfCalendarFragment = new HalfCalendarFragment(CalendarUtill.selectedDate);
                        FragmentManager fragmentManager = ((AppCompatActivity) context).getSupportFragmentManager();
                        fragmentManager.beginTransaction()
                                .replace(R.id.containers, halfCalendarFragment)
                                .addToBackStack(null)  // 이전 Fragment로 돌아갈 수 있도록 백 스택에 추가
                                .commit();
                    }
                }
            }
        });




    }

    @Override
    public int getItemCount() {
        return dayList.size();
    }

    // 뷰 홀더
    public static class CalendarViewHolder extends RecyclerView.ViewHolder {
        // 초기화
        TextView dayText;
        View parentView;

        public CalendarViewHolder(@NonNull View itemView) {
            super(itemView);

            dayText = itemView.findViewById(R.id.dayText); // calendar_cell.xml

            // calendar_cell.xml에서 dayText의 부모 뷰
            parentView = itemView.findViewById(R.id.dateTextParentView); // calendar_cell.xml -LieanrLayout id
        }

    }


}