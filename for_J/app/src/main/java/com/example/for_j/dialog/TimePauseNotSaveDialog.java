package com.example.for_j.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.for_j.R;

public class TimePauseNotSaveDialog extends Dialog {

    public TimePauseNotSaveDialog(Context context) {
        super(context);
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.time_pause_not_save_dialog);

        Button positiveBtn = findViewById(R.id.positiveBtn);

        positiveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });
    }
}
