package businesslayer;

import android.content.Context;

import java.util.ArrayList;

import persistancelayer.DataBaseHelper;
import persistancelayer.DataInterface;
import persistancelayer.TextNotePL;

/**
 * Created by Abdul Hadi on 10/04/2016.
 */
public class ChecklistBL {


    DataBaseHelper dataBaseHelper = null;
    DataInterface dataInterface = null;


    public ChecklistBL(Context context)
    {
        dataBaseHelper = new DataBaseHelper(context);
        //dataInterface = new DataBaseHelper(context);
    }

public boolean create(String time, String checklistName, ArrayList<String> arrayTask, String type )
{
    String strTask = "";
    int size = arrayTask.size();
    int i;

    for (i=0; i<size; i++)
    {
        strTask=strTask.concat(arrayTask.get(i)+"^");
    }
    boolean isInserted = dataBaseHelper.insertData(time, checklistName, "", strTask, null, type);
    return isInserted;
}

}
