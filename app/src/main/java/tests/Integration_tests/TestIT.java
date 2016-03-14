package tests.Integration_tests;

/**
 * Created by kushal on 2016-03-14.
 */

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import java.util.List;

import businesslayer.PhotoNoteBL;
import businesslayer.TextNoteBL;
import businesslayer.TextNoteWrapper;

import static junit.framework.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Created by kushal on 2016-03-13.
 */
public class TestIT
{
    @Mock
    TextNoteBL obj;
    PhotoNoteBL pobj;
    TextNoteWrapper Wobj;


    @Before
    public void create()
    {
        obj= mock(TextNoteBL.class);
        pobj=mock(PhotoNoteBL.class);
        Wobj= mock(TextNoteWrapper.class);
        // expectations
        when(obj.create("time","noteName","noteLabel","noteText","filepath","type")).thenReturn(true);
        when(pobj.create("note","label","allpath","text","type")).thenReturn(true);
    }

    @After
    public void tear()
    {
        obj=null;
        pobj=null;
        Wobj=null;
    }

    @Test
    public void testRetreval() throws Exception
    {
        List<TextNoteWrapper> noteW= Wobj.getSampleNotes("noteTpe");
        assertTrue("nothing is being retrieved!",!noteW.isEmpty());//verification
    }

    @Test
    public void testDBcreation() throws Exception
    {
        boolean result=obj.create("time","Nname","Nlabel","Ntext","fpath","typ");
        assertTrue("object not inserted to DB properly",!result);//verification
        boolean result2 = pobj.create("Time","Name","LAbel","filepath","Text") ;
        assertTrue("object for photonote not inserted",!result2);//verification
    }

}
