package BusinessLayer;

import android.widget.TextView;

import PersistanceLayer.TextNotePL;

/**
 * Created by rishabhbhandari on 2016-02-18.
 */
public class TextNoteBL
{

    TextNotePL textNoteObj=new TextNotePL();
    // public ArrayList<TextNotePL> noteList=new ArrayList<TextNotePL>();





    public void create(TextView noteName, TextView noteLabel, TextView note)
    {
        //textNoteObj= new TextNotePL(noteName,noteLabel,note);
        textNoteObj.addData(noteName,noteLabel,note);
        //System.out.printf("11111 %s %s %s ",notename,notelabel,noteit);
    }

}
