// TimeFragment에 들어가는 주간 캘린더 어댑터
package com.example.for_j;

import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Calendar;

public class WeeklyCalendarAdapter extends RecyclerView.Adapter<WeeklyCalendarAdapter.WeekCalendarViewHolder> {

    private ArrayList<Calendar> weekDaysList;
    private Calendar currentDate;

    public WeeklyCalendarAdapter(ArrayList<Calendar> weekDaysList) {
        this.weekDaysList = weekDaysList;
        // 현재 날짜 가져오기
        this.currentDate = Calendar.getInstance();
    }

    @NonNull
    @Override
    public WeekCalendarViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.calendar_cell_week, parent, false);
        return new WeekCalendarViewHolder(view, currentDate); // currentDate 전달
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onBindViewHolder(@NonNull WeekCalendarViewHolder holder, int position) {
        Calendar day = weekDaysList.get(position);
        holder.weekDayText.setText(String.valueOf(day.get(Calendar.DAY_OF_MONTH)));

        // 현재 날짜와 같은 경우 가운데 정렬
        if (day.equals(currentDate)) {
            holder.weekDayText.setTextColor(ContextCompat.getColor(holder.itemView.getContext(), R.color.colorAccent));
        } else {
            holder.weekDayText.setTextColor(ContextCompat.getColor(holder.itemView.getContext(), R.color.black));
        }
    }

    @Override
    public int getItemCount() {
        return weekDaysList.size();
    }

    public class WeekCalendarViewHolder extends RecyclerView.ViewHolder {
        TextView weekDayText;

        public WeekCalendarViewHolder(@NonNull View itemView, Calendar currentDate) {
            super(itemView);
            weekDayText = itemView.findViewById(R.id.dayText);
            // currentDate 설정
            WeeklyCalendarAdapter.this.currentDate = currentDate;
        }
    }
}