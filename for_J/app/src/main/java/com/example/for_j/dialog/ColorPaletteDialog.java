package com.example.for_j.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatRadioButton;

import com.example.for_j.R;

public class ColorPaletteDialog extends Dialog {

    private final ColorPaletteListener colorPaletteListener;

    public interface ColorPaletteListener {
        void getColorPaletteData(int color);
    }

    public ColorPaletteDialog(@NonNull Context context, ColorPaletteListener colorPaletteListener) {
        super(context);
        this.colorPaletteListener = colorPaletteListener;
    }


    int color = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.category_color_palette);

        //    private RadioGroup CP_RG;
        AppCompatRadioButton categoryPink = findViewById(R.id.categoryPink);
        categoryPink.setOnClickListener(view -> {
            color = 0;
            colorPaletteListener.getColorPaletteData(color);
            dismiss();
        });

        AppCompatRadioButton categoryCrimson = findViewById(R.id.categoryCrimson);
        categoryCrimson.setOnClickListener(view -> {
            color = 1;
            colorPaletteListener.getColorPaletteData(color);
            dismiss();
        });

        AppCompatRadioButton categoryOrange = findViewById(R.id.categoryOrange);
        categoryOrange.setOnClickListener(view -> {
            color = 2;
            colorPaletteListener.getColorPaletteData(color);
            dismiss();
        });

        AppCompatRadioButton categoryYellow = findViewById(R.id.categoryYellow);
        categoryYellow.setOnClickListener(view -> {
            color = 3;
            colorPaletteListener.getColorPaletteData(color);
            dismiss();
        });

        AppCompatRadioButton categoryLightGreen = findViewById(R.id.categoryLightGreen);
        categoryLightGreen.setOnClickListener(view -> {
            color = 4;
            colorPaletteListener.getColorPaletteData(color);
            dismiss();
        });

        AppCompatRadioButton categoryTurquoise = findViewById(R.id.categoryTurquoise);
        categoryTurquoise.setOnClickListener(view -> {
            color = 5;
            colorPaletteListener.getColorPaletteData(color);
            dismiss();
        });

        AppCompatRadioButton categoryPastelBlue = findViewById(R.id.categoryPastelBlue);
        categoryPastelBlue.setOnClickListener(view -> {
            color = 6;
            colorPaletteListener.getColorPaletteData(color);
            dismiss();
        });

        AppCompatRadioButton categoryPastelPurple = findViewById(R.id.categoryPastelPurple);
        categoryPastelPurple.setOnClickListener(view -> {
            color = 7;
            colorPaletteListener.getColorPaletteData(color);
            dismiss();
        });
    }
}
