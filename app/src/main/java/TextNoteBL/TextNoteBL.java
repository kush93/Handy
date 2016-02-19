package TextNoteBL;

import com.example.kushal.Handy.TextNotePL;

/**
 * Created by rishabhbhandari on 2016-02-18.
 */
public class TextNoteBL
{

    TextNotePL textNoteObj;
    // public ArrayList<TextNotePL> noteList=new ArrayList<TextNotePL>();





    public void create(String notename, String notelabel, String noteit)
    {
        textNoteObj= new TextNotePL(notename,notelabel,noteit);
        System.out.printf("11111 %s %s %s ",notename,notelabel,noteit);
    }

}
