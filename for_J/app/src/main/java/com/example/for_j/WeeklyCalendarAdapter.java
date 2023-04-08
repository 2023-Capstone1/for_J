// TimeFragment에 들어가는 주간 캘린더 어댑터
package com.example.for_j;

import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import java.time.LocalDate;
import java.util.ArrayList;

public class WeeklyCalendarAdapter extends RecyclerView.Adapter<WeeklyCalendarAdapter.WeekCalendarViewHolder> {

    private ArrayList<LocalDate> weekDaysList;

    public WeeklyCalendarAdapter(ArrayList<LocalDate> weekDaysList) {
        this.weekDaysList = weekDaysList;
    }

    @NonNull
    @Override
    public WeekCalendarViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.calendar_cell_week, parent, false);
        return new WeekCalendarViewHolder(view);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onBindViewHolder(@NonNull WeekCalendarViewHolder holder, int position) {
        LocalDate day = weekDaysList.get(position);
        holder.weekDayText.setText(String.valueOf(day.getDayOfMonth()));
    }

    @Override
    public int getItemCount() {
        return weekDaysList.size();
    }

    public class WeekCalendarViewHolder extends RecyclerView.ViewHolder {
        TextView weekDayText;

        public WeekCalendarViewHolder(@NonNull View itemView) {
            super(itemView);
            weekDayText = itemView.findViewById(R.id.dayText);
        }
    }
}
