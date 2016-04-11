package tests.Integration_tests;

/**
 * Created by kushal on 2016-03-14.
 */

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import java.util.List;

import businesslayer.ChecklistBL;
import businesslayer.PhotoNoteBL;
import businesslayer.TextNoteBL;
import persistancelayer.NoteInterface;
import presentationlayer.MainActivity;

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
    MainActivity mobj;
    ChecklistBL cObj;

    
    @Before
    public void create()
    {
        obj= mock(TextNoteBL.class);
        pobj=mock(PhotoNoteBL.class);
        mobj = mock(MainActivity.class);
        cObj=mock(ChecklistBL.class);
        when(obj.create("time","noteName","noteLabel","noteText","filepath","type")).thenReturn(true);
        when(pobj.create("note","label","text","fpath","type")).thenReturn(true);
        when(cObj.create("time","cName",null,"type")).thenReturn(true);
    }

    @After
    public void tear()
    {
        obj=null;
        pobj=null;
        mobj =null;
        cObj=null;
    }

    @Test
    public void testRetrieval()
    {
        List<NoteInterface> noteW= mobj.getNotesFromDB("noteTpe");
        assertTrue("nothing is being retrieved!",!noteW.isEmpty());//verification
    }

    @Test
    public void testDBCreation()
    {
        boolean result=obj.create("time","Nname","Nlabel","Ntext","fpath","typ");
        assertTrue("object not inserted to DB properly",!result);//verification
        boolean result2 = pobj.create("Nname", "Nlabel", "Ntext", "fpath", "typ") ;
        assertTrue("object for photonote not inserted",!result2);//verification
    }

    @Test
    public void testUpdate()
    {
        boolean result=obj.create("time1","Nname1","Nlabel1","Ntext1","fpath1","typ1");
        boolean resultUpdated=obj.updateData("1", "Mar 26/03/2016 12:12PM", "updated notename1", "Itr2", "updated notetextAssign1", "root/user/gallery1", "textNote1");
        assertTrue("data was not updated!",!resultUpdated);
    }

    @Test
    public void testClcreation()
    {
        boolean result= cObj.create("time","cName",null,"type");
        assertTrue("checklist not created!",!result);
        boolean result2= cObj.create("","",null,"");
        assertTrue("empty should have been created",!result2);
    }

}
