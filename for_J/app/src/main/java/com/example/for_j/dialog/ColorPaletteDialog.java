package com.example.for_j.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatRadioButton;

import com.example.for_j.R;

public class ColorPaletteDialog extends Dialog {

    private Context context;
    private ColorPaletteListener colorPaletteListener;

    public interface ColorPaletteListener {
        void getColorPaletteData(int color);
    }

    public ColorPaletteDialog(@NonNull Context context, ColorPaletteListener colorPaletteListener) {
        super(context);
        this.context = context;
        this.colorPaletteListener = colorPaletteListener;
    }



//    private RadioGroup CP_RG;
    private AppCompatRadioButton categoryPink;
    private AppCompatRadioButton categoryCrimson;
    private AppCompatRadioButton categoryOrange;
    private AppCompatRadioButton categoryYellow;
    private AppCompatRadioButton categoryLightGreen;
    private AppCompatRadioButton categoryTurquoise;
    private AppCompatRadioButton categoryPastelBlue;
    private AppCompatRadioButton categoryPastelPurple;

    int color = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.category_color_palette);

//        CP_RG = findViewById(R.id.CP_RG);

        /*CP_RG.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @SuppressLint("NonConstantResourceId")
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int checkId) {
                switch (checkId) {
                    case R.id.categoryPink:
                        color = 0;
                        break;
                    case R.id.categoryCrimson:
                        color = 1;
                        break;
                    case R.id.categoryOrange:
                        color = 2;
                        break;
                    case R.id.categoryYellow:
                        color = 3;
                        break;
                    case R.id.categoryLightGreen:
                        color = 4;
                        break;
                    case R.id.categoryTurquoise:
                        color = 5;
                        break;
                    case R.id.categoryPastelBlue:
                        color = 6;
                        break;
                    case R.id.categoryPastelPurple:
                        color = 7;
                        break;
                }

            }
        });*/

        categoryPink = findViewById(R.id.categoryPink);
        categoryPink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                color = 0;
                colorPaletteListener.getColorPaletteData(color);
                dismiss();
            }
        });

        categoryCrimson = findViewById(R.id.categoryCrimson);
        categoryCrimson.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                color = 1;
                colorPaletteListener.getColorPaletteData(color);
                dismiss();
            }
        });

        categoryOrange = findViewById(R.id.categoryOrange);
        categoryOrange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                color = 2;
                colorPaletteListener.getColorPaletteData(color);
                dismiss();
            }
        });

        categoryYellow = findViewById(R.id.categoryYellow);
        categoryYellow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                color = 3;
                colorPaletteListener.getColorPaletteData(color);
                dismiss();
            }
        });

        categoryLightGreen = findViewById(R.id.categoryLightGreen);
        categoryLightGreen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                color = 4;
                colorPaletteListener.getColorPaletteData(color);
                dismiss();
            }
        });

        categoryTurquoise = findViewById(R.id.categoryTurquoise);
        categoryTurquoise.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                color = 5;
                colorPaletteListener.getColorPaletteData(color);
                dismiss();
            }
        });

        categoryPastelBlue = findViewById(R.id.categoryPastelBlue);
        categoryPastelBlue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                color = 6;
                colorPaletteListener.getColorPaletteData(color);
                dismiss();
            }
        });

        categoryPastelPurple = findViewById(R.id.categoryPastelPurple);
        categoryPastelPurple.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                color = 7;
                colorPaletteListener.getColorPaletteData(color);
                dismiss();
            }
        });
    }
}
