
package tests.Unit_tests;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import businesslayer.TextNoteBL;
import domain.Note;
import persistancelayer.TextNotePL;

import static junit.framework.Assert.assertTrue;

/**
 * Created by kushal on 2E016-02-21.
 */
public class TestList {

    TextNotePL tObj;
    Note note, note2, note3, note4;

    TextNoteBL blObj;

    @Before
    protected void setUp() {
        tObj = new TextNotePL();
        blObj = new TextNoteBL();
        note = new Note(1, "Mar 14/2016, 12:08PM ", "Comp 3350", "ITR2", "first note", "", "textNote");
        note2 = new Note(2, "Mar 14/2016, 12:22PM ", "Comp 3350", "ITR4", "second note", "", "textNote");
        note3 = new Note(3, "Mar 14/2016, 12:28PM ", "Comp 3350", "ITR3", "third note", "", "textNote");
        note4 = new Note(4, "Mar 14/2016, 12:38PM ", "Comp 3350", "ITR0", "fourth note", "", "textNote");
        tObj.clearNoteList();
    }

    @Test
    public void testcreate() throws Exception {
        boolean result = blObj.create("Mar 26/03/2016 12:08PM", "notename1", "Itr2", "notetextAssign1", "root/user/gallery1", "textNote1");
        assertTrue("create method:list is empty", !blObj.getTextNoteObj().getNoteList().isEmpty());
        blObj.create("April 27/03/2017 12:08PM", "notename2", "Itr_2", "notetextAssign2", "root/user/gallery2", "textNote2");
        blObj.create("May 26/03/2016 12:09PM", "notename3", "Itr.2", "notetextAssign3", "root/user/gallery3", "textNote3");
        assertTrue("the size is not right", blObj.getTextNoteObj().getNoteList().size() == 3);
    }

    @Test
    public void testgetTextNoteObj() throws Exception {
        blObj.create("Mar 26/03/2016 12:08PM", "notename1", "Itr2", "notetextAssign1", "root/user/gallery1", "textNote1");
        assertTrue("Text note object is null", blObj.getTextNoteObj() != null);
    }


    @Test
    public void testaddData() {
        tObj.insertData("Mar 14/2016, 12:08PM ", "Comp 3350", "ITR2", "first note", "", "textNote");
        tObj.insertData("Mar 14/2016, 12:38PM ", "Comp 3250", "ITR3", "first sec note", "", "textNote");
        tObj.insertData("Mar 14/2016, 12:48PM ", "Comp 3150", "ITR4", "first third note", "", "textNote");
        assertTrue("Your list is still empty", !tObj.getNoteList().isEmpty());
        assertTrue("same note doesn't exists", tObj.containsNote(note2));
        assertTrue("same note doesn't exists", !tObj.containsNote(note4));
        assertTrue("this is not the right size", tObj.getNoteList().size() == 3);
    }

    @After
    protected void tearDown() {
        tObj = null;
        blObj = null;
        note = null;
    }


}