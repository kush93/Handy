package tests;

import domain.Note;

import junit.framework.*;

import businesslayer.TextNoteBL;
import persistancelayer.TextNotePL;

/**
 * Created by kushal on 2016-02-21.
 */
public class TestList extends TestCase
{

        TextNotePL tObj;
        Note note, note2,note3,note4;

        TextNoteBL blObj;

        protected  void setUp()
        {
                tObj = new TextNotePL();
                blObj=new TextNoteBL(null);
                note= new Note("testnote1","test1","this is test1");
                note2= new Note("testnote2","test2","this is test2");
                note3= new Note ("testnote3","test3","this is test3");
                note4= new Note ("testnote4","test4","this is test4");
                tObj.clearNoteList();
        }

        public void testcreate()
        {
            blObj.create("testbl", "1", "testingBLcreate1");
            assertTrue("create method:list is empty", !blObj.getTextNoteObj().getNoteList().isEmpty());
            blObj.create("testb2", "2", "testingBLcreate2");
            blObj.create("testb3", "3","testingBLcreate3");
            blObj.create("testb4", "4","testingBLcreate4");
            assertTrue("the size is not 1, which is not right", blObj.getTextNoteObj().getNoteList().size() == 4);
        }

        public void testaddData()
        {
            tObj.addData("testnote1", "test1", "this is test1");
            tObj.addData("testnote2", "test2", "this is test2");
            tObj.addData("testnote3", "test3", "this is test3");
            assertTrue("Your list is still empty", !tObj.getNoteList().isEmpty());
            assertTrue("same note doesn't exists", tObj.containsNote(note2));
            assertTrue("same note doesn't exists", !tObj.containsNote(note4));
            assertTrue("this is not the right size",tObj.getNoteList().size()==3);

        }

    protected void tearDown()
    {
        tObj=null;
        blObj=null;
        note=null;
    }


}
