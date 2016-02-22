package BusinessLayer1;

import PersistanceLayer1.TextNotePL;

/**
 * Created by rishabhbhandari on 2016-02-18.
 */
public class TextNoteBL
{

    TextNotePL textNoteObj=new TextNotePL();
    // public ArrayList<TextNotePL> noteList=new ArrayList<TextNotePL>();





    public void create(String  noteName, String noteLabel, String note)
    {
        //textNoteObj= new TextNotePL(noteName,noteLabel,note);
        textNoteObj.addData(noteName,noteLabel,note);
        //System.out.printf("11111 %s %s %s ",notename,notelabel,noteit);
    }

    public TextNotePL getTextNoteObj()
    {
        return textNoteObj;
    }
}
