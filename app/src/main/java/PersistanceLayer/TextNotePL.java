package PersistanceLayer;

import android.util.Log;
import android.widget.TextView;

import com.example.kushal.Handy.DataInterface;
import com.example.kushal.Handy.Note;

import java.util.ArrayList;
import java.util.Objects;

/**
 * Created by rishabhbhandari on 2016-02-18.
 */
public class TextNotePL implements DataInterface
{

    static ArrayList<Note> noteList= new ArrayList<Note>();
    public Note myNote;
    static int SIZE=0;

    public TextNotePL()
    {

    }


    public boolean containsNote(Note myNote){
        boolean result=false;
        for(int i=0;i<getNoteList().size()&&!result;i++)
        {
            if(((Note)getNoteList().get(i)).equalsNote(myNote)){
                result=true;
            }
        }
        return result;
    }

    public ArrayList<Note> getNoteList() {
        return noteList;
    }

    public void setNoteList(Note noteObj)
    {
        noteList.add(noteObj);

    }
    public void clearNoteList(){
        noteList.clear();
    }


    public static void setSIZE(int SIZE) {
        TextNotePL.SIZE = SIZE;
    }

    public void addData(String noteName, String labelName, String note)
    {
        myNote= new Note(noteName,labelName,note);
        noteList.add(myNote);
    }

}
