package com.example.for_j.dialog;

import static androidx.constraintlayout.widget.ConstraintLayoutStates.TAG;

import android.app.Dialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.util.Log;
import android.widget.TimePicker;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import java.util.Calendar;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class TimePickerFragment extends DialogFragment implements TimePickerDialog.OnTimeSetListener {

    public interface OnTimeSelectedListener {
        void onTimeSelected(int hour, int minute);
    }
    private final Calendar selectedDate;
    private final OnTimeSelectedListener tListener;

    public TimePickerFragment(Calendar selectedDate, OnTimeSelectedListener tListener) {
        this.selectedDate = selectedDate;
        this.tListener = tListener;
    }


    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        // Use the current time as the default values for the picker
        int hour = selectedDate.get(Calendar.HOUR_OF_DAY);
        int minute = selectedDate.get(Calendar.MINUTE);

        // Create a new instance of TimePickerDialog and return it
        return new TimePickerDialog(getActivity(), this, hour, minute,
                android.text.format.DateFormat.is24HourFormat(getActivity()));
    }

    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        // Create a new Calendar instance to hold the selected time
        Calendar selectedTime = Calendar.getInstance();
        selectedTime.set(Calendar.HOUR_OF_DAY, hourOfDay);
        selectedTime.set(Calendar.MINUTE, minute);

        // Get the current time
        Calendar currentTime = Calendar.getInstance();

        // If the selected time is in the past, add one day to the selected time
        if (selectedTime.before(currentTime)) {
            selectedTime.add(Calendar.DAY_OF_MONTH, 1);
        }

        // Calculate the delay between the current time and the selected time
        long delayInMillis = selectedTime.getTimeInMillis() - currentTime.getTimeInMillis();

        // Schedule a task to run at the selected time using a ScheduledExecutorService
        ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();
        executor.schedule(() -> {
            // Do something at the selected time
            // For example, show a notification or start an activity
            Log.d(TAG, "Scheduled task executed at " + selectedTime.getTime());
        }, delayInMillis, TimeUnit.MILLISECONDS);

        if (tListener != null) {
            tListener.onTimeSelected(hourOfDay, minute);
        }
    }
}