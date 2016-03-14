package businesslayer;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;

import persistancelayer.DataBaseHelper;
import persistancelayer.TextNotePL;

/**
 * Created by rishabhbhandari on 2016-02-18.
 */
public class TextNoteBL {

    DataBaseHelper dataBaseHelper = null;
    TextNotePL textNoteObj = new TextNotePL();

    public TextNoteBL(Context context) {
        dataBaseHelper = new DataBaseHelper(context);

    }

    public  TextNoteBL(){}// empty constructor


    // public ArrayList<TextNotePL> noteList=new ArrayList<TextNotePL>();


    public boolean create(String time, String noteName, String noteLabel, String noteText, String filePath, String type) {
        //textNoteObj= new TextNotePL(noteName,noteLabel,note);
        // String time = "12:00";
        boolean isInserted = dataBaseHelper.insertData(time, noteName, noteLabel, noteText, null, type); // no file path needed for text note

        textNoteObj.addData(noteName, noteLabel, noteText);
        //System.out.printf("11111 %s %s %s ",notename,notelabel,noteit);
        return isInserted;
    }

    public TextNotePL getTextNoteObj() {
        return textNoteObj;
    }

    public List<String> getSavedData(String type) {
        List<String> textNoteList=new ArrayList<String>();
        textNoteList=dataBaseHelper.getData(type);



        textNoteObj.addData("Rahul", "Bhandari", "first note");

        textNoteObj.addData("Itr1", "comp3350", "wiki second note");
        return textNoteList;
    }


}
