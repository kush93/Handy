package com.example.kushal.Handy;

import java.util.ArrayList;

/**
 * Created by rishabhbhandari on 2016-02-18.
 */
public class TextNotePL {


    public ArrayList<Note> noteList= new ArrayList<Note>();
    public Note myNote;

    public TextNotePL(String noteName,String labelName, String note)
    {
        myNote= new Note(noteName,labelName,note);
        noteList.add(myNote) ;
    }
    public TextNotePL()
    {



    }
    public ArrayList<Note> getNoteList() {
        return noteList;
    }


}
