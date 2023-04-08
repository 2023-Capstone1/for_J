package com.example.for_j.dialog;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.widget.DatePicker;

import androidx.fragment.app.DialogFragment;

import java.util.Calendar;

public class DatePickerFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener {

    public interface OnDateSelectedListener {
        void onDateSelected(int year, int month, int day);
    }

    private Calendar selectedDate;
    private OnDateSelectedListener mListener;

    public DatePickerFragment(Calendar selectedDate, OnDateSelectedListener mListener) {
        this.selectedDate = selectedDate;
        this.mListener = mListener;
    }


    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Get the current date
        int year = selectedDate.get(Calendar.YEAR);
        int month = selectedDate.get(Calendar.MONTH);
        int day = selectedDate.get(Calendar.DAY_OF_MONTH);

        // Create a new instance of DatePickerDialog and return it
        return new DatePickerDialog(getActivity(), this, year, month, day);
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int day) {
        // Update the selected date
        selectedDate.set(year, month, day);

        if (mListener != null) {
            mListener.onDateSelected(year, month, day);
        }
    }
}
