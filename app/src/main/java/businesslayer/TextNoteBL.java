package businesslayer;

import android.content.Context;

import persistancelayer.DataBaseHelper;
import persistancelayer.TextNotePL;
/**
 * Created by rishabhbhandari on 2016-02-18.
 */
public class TextNoteBL
{

    DataBaseHelper dataBaseHelper = null;
    TextNotePL textNoteObj=new TextNotePL();
    public TextNoteBL(Context context){
        dataBaseHelper = new DataBaseHelper(context);
    }
    public ArrayList<TextNotePL> noteList=new ArrayList<TextNotePL>();

    public void create(String  noteName, String noteLabel, String note) // accesses the database as well as arraylist
    {
        //textNoteObj= new TextNotePL(noteName,noteLabel,note);
        String time = "12:00"; //for the time being, time is hard coded & text note doesn't have any path as it is not saved on the memory
        dataBaseHelper.insertData(time,noteName , noteLabel, note,null);
        textNoteObj.addData(noteName,noteLabel,note);
        //System.out.printf("11111 %s %s %s ",notename,notelabel,noteit);
    }

    public TextNotePL getTextNoteObj()
    {
        return textNoteObj;
    }

    public void getSavedData()
    {
        textNoteObj.addData("Rahul","Bhandari","first note");
        textNoteObj.addData("Itr1","comp3350","wiki second note");
    }
}
