package com.example.kushal.Handy;

import android.widget.TextView;

/**
 * Created by rishabhbhandari on 2016-02-18.
 */
public class Note {

    public TextView noteName;
    public TextView labelName;
    public TextView note;
    public int x=0;
    public Note(TextView name, TextView label, TextView note)
    {
        this.noteName=name;
        this.labelName=label;
        this.note=note;
    }
}
