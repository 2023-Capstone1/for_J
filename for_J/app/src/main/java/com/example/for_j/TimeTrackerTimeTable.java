package com.example.for_j;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.GridLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class TimeTrackerTimeTable extends AppCompatActivity {

    private GridLayout gridLayout;
    private final int ROWS = 30;
    private final int COLS = 7;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.timetable);

        gridLayout = findViewById(R.id.gridLayout);

        TextView[][] textViews = new TextView[ROWS][COLS];

        DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
        int height = displayMetrics.heightPixels / ROWS;
        int width = displayMetrics.widthPixels / COLS;

        // 그리드 레이아웃의 높이 설정
        ViewGroup.LayoutParams params = gridLayout.getLayoutParams();
        params.height = height * ROWS;
        gridLayout.setLayoutParams(params);

        for (int i = 0; i < 24; i++) {
            for (int j = 0; j < COLS; j++) {
                TextView textView = new TextView(this);
                textView.setGravity(Gravity.CENTER);

                GridLayout.LayoutParams params2 = new GridLayout.LayoutParams();
                params2.columnSpec = GridLayout.spec(j);
                params2.rowSpec = GridLayout.spec(i);
                params2.setGravity(Gravity.FILL);
                params2.width = width;
                params2.height = height;
                textView.setLayoutParams(params2);

                textView.setBackgroundResource(R.drawable.grid_background);
                gridLayout.addView(textView);

                textViews[i][j] = textView;
            }
        }

        for(int i = 0; i <24; i++) {
            if(i<=9) {
                textViews[i][0].setText("0" + i + ":00");}
            else  {
                textViews[i][0].setText(""+i + ":00");
            }
        }
    }
}