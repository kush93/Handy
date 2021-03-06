package businesslayer;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;

import persistancelayer.DataBaseHelper;
import persistancelayer.DataInterface;
import persistancelayer.TextNotePL;

/**
 * Created by rishabhbhandari on 2016-02-18.
 */
public class TextNoteBL {

    DataBaseHelper dataBaseHelper = null;
    DataInterface dataInterface = null;
    TextNotePL textNoteObj = new TextNotePL();

    public TextNoteBL(Context context)
    {
        dataBaseHelper = new DataBaseHelper(context);
    }

    public TextNoteBL() {
    }


	public boolean delete(String id)
	{
		return dataBaseHelper.delete(id);
	}

    public boolean create(String time, String noteName, String noteLabel, String noteText, String filePath, String type) {
        boolean isInserted = dataBaseHelper.insertData(time, noteName, noteLabel, noteText, null, type); // no file path needed for text note
        textNoteObj.insertData(time, noteName, noteLabel, noteText, null, type);
        return isInserted;
    }

    public boolean updateData(String id, String time, String noteName, String noteLabel, String textNote, String filePath, String noteType) {
        boolean result = dataBaseHelper.updateData(id, time, noteName, noteLabel, textNote, filePath, noteType);
        return result;
    }


    public List<String> getSavedData(String type) {
        List<String> textNoteList = new ArrayList<String>();
        textNoteList = dataBaseHelper.getData(type);
        textNoteObj.insertData("Mar 14/2016, 12:08PM ", "Comp 3350", "ITR2", "first note", "", "textNote");
        textNoteObj.insertData("Mar 14/2016, 12:22PM ", "Comp 3010", "Assignment 3", "second note", "", "textNote");
        return textNoteList;
    }

    public TextNotePL getTextNoteObj() {
        return textNoteObj;
    }


}
