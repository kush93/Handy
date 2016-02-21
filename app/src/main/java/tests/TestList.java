package tests;

import com.example.kushal.Handy.Note;

import junit.framework.*;

import BusinessLayer.TextNoteBL;
import PersistanceLayer.TextNotePL;

/**
 * Created by kushal on 2016-02-21.
 */
public class TestList extends TestCase
{

        TextNotePL tObj;
        Note note;
        TextNoteBL blObj;

        protected  void setUp()
        {

                tObj = new TextNotePL();
                blObj=new TextNoteBL();
                note= new Note("test","1","this is test");
                tObj.clearNoteList();
        }

        public void testcreate()
        {

            blObj.create("testbl", "1","testingBLcreate");
            assertTrue("create method:list is empty",!blObj.getTextNoteObj().getNoteList().isEmpty());
            assertTrue("the size is not 1, which is not right", blObj.getTextNoteObj().getNoteList().size() == 1);
        }

        public void testaddData()
        {

            tObj.addData("test", "1", "this is test");
            assertTrue("Your list is still empty", !tObj.getNoteList().isEmpty());
            assertTrue("same note doesn't exists", tObj.containsNote(note));
            assertTrue("this is not the right size",tObj.getNoteList().size()==1);
        }

    protected void tearDown()
    {
        tObj=null;
        blObj=null;
        note=null;
    }


}
