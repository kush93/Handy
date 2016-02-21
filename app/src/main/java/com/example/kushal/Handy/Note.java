package com.example.kushal.Handy;

import android.widget.TextView;

/**
 * Created by rishabhbhandari on 2016-02-18.
 */
public class Note {

    public String noteName;
    public String labelName;
    public String note;
    public int x=0;
    public Note(String name, String label, String note)
    {
        this.noteName=name;
        this.labelName=label;
        this.note=note;
    }
    public boolean equalsNote(Note mynote){
        return (this.noteName.equals(mynote.noteName))&&(this.note.equals(mynote.note));
    }
}
