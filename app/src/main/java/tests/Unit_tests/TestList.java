package tests.Unit_tests;

import junit.framework.TestCase;

import businesslayer.TextNoteBL;
import domain.Note;
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
                blObj=new TextNoteBL();
                note= new Note("testnote1","test1","this is test1");
                note2= new Note("testnote2","test2","this is test2");
                note3= new Note ("testnote3","test3","this is test3");
                note4= new Note ("testnote4","test4","this is test4");
                tObj.clearNoteList();
        }


        public void testcreate()
        {
            boolean result=blObj.create("Mar 26/03/2016 12:08PM", "notename1", "Itr2","notetextAssign1","root/user/gallery1","textNote1");
            assertTrue("create method:list is empty", !blObj.getTextNoteObj().getNoteList().isEmpty());
            blObj.create("April 27/03/2017 12:08PM", "notename2", "Itr_2","notetextAssign2","root/user/gallery2","textNote2");
            blObj.create("May 26/03/2016 12:09PM", "notename3", "Itr.2","notetextAssign3","root/user/gallery3","textNote3");
            assertTrue("the size is not right", blObj.getTextNoteObj().getNoteList().size() == 3);
        }

        public void testgetTextNoteObj()
        {
            blObj.create("Mar 26/03/2016 12:08PM", "notename1", "Itr2", "notetextAssign1", "root/user/gallery1", "textNote1");
            assertTrue("Text note object is null", blObj.getTextNoteObj()!=null);
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
