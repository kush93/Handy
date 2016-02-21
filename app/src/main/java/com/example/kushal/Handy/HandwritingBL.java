package com.example.kushal.Handy;

import android.graphics.Color;

/**
 * Created by Ian on 21/02/2016.
 */
public class HandwritingBL {

    public int changeColor(String newColor) {
        return Color.parseColor(newColor);
    }

    public int setErase(int selectedColor, boolean eraseState) {
        int returnColor = selectedColor;

        if(eraseState){
            returnColor = Color.parseColor("#ffffffff");
        }

        return returnColor;
    }
}
