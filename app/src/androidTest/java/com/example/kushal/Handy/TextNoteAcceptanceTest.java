package com.example.kushal.Handy;

import android.test.ActivityInstrumentationTestCase2;

import com.example.kushal.rihabhbhandari.R;
import com.robotium.solo.Solo;

import presentationlayer.MainActivity;
import presentationlayer.NoteTakerUI;


/**
 * Created by rishabhbhandari on 2016-04-06.
 */
public class TextNoteAcceptanceTest extends ActivityInstrumentationTestCase2<MainActivity> {
    private Solo solo;
    private static final String NAME1 = "Text Note 1";
    private static final String NAME2 = "Text Note 2";
    private static final String LABEL1 = "LABEL 1";
    private static final String LABEL2 = "LABEL 2";
    private static final String NOTETEXT1 = "NOTETEXT 1";
    private static final String NOTETEXT2 = "NOTETEXT 2";
    private static final String EDITTED_NOTENAME1 = "Text Note 1 edited";

    public TextNoteAcceptanceTest() {
        super(MainActivity.class);
    }
    @Override
    public void setUp() throws Exception {

        solo = new Solo(getInstrumentation());

        getActivity();
    }

    @Override
    public void tearDown() throws Exception {
        solo.finishOpenedActivities();
    }

    public void testAddTextNote() throws Exception{
        solo.unlockScreen();
        solo.sleep(500);

        solo.clickOnView(solo.getView(R.id.button_main_open_text_note));
        solo.assertCurrentActivity("Expected NoteTakerUI Activity", NoteTakerUI.class);
        //In text field 0, enter Name
        solo.sleep(500);
        solo.enterText(0, NAME1);
        solo.sleep(500);
        solo.enterText(1, LABEL1);
        solo.sleep(500);
        solo.enterText(2, NOTETEXT1);

        solo.sleep(500);
        solo.clickOnView(solo.getView(R.id.button_save_note));
        solo.sleep(500);
        solo.assertCurrentActivity("Expected MainActivity Activity", MainActivity.class);

        solo.clickOnView(solo.getView(R.id.button_main_open_text_note));
        solo.assertCurrentActivity("Expected NoteTakerUI Activity", NoteTakerUI.class);
        //In text field 0, enter Name
        solo.sleep(500);
        solo.enterText(0, NAME2);
        solo.sleep(500);
        solo.enterText(1, LABEL1);
        solo.clearEditText(1);
        solo.enterText(1, LABEL2);
        solo.sleep(500);
        solo.enterText(2, NOTETEXT2);

        solo.sleep(500);
        solo.clickOnView(solo.getView(R.id.button_save_note));
        solo.sleep(500);
        solo.assertCurrentActivity("Expected MainActivity Activity", MainActivity.class);

        solo.takeScreenshot();

        boolean notesFound = solo.searchText(NAME1) && solo.searchText(NAME2);
        assertTrue("Text Note 1 and Text Note 2 are found", notesFound);

    }
    public void testEditTextNote() throws Exception{

        solo.unlockScreen();
        //Click on first item of Student List
        solo.sleep(500);
        solo.clickInList(1);
        solo.assertCurrentActivity("Expected NoteTakerUI Activity", NoteTakerUI.class);
        solo.sleep(500);
        solo.clearEditText(0);
        solo.enterText(0, EDITTED_NOTENAME1);
        //Click on action menu item Save
        solo.sleep(500);
        solo.clickOnView(solo.getView(R.id.button_save_note));
        //Click on action menu item Add
        solo.sleep(500);

        //Assert that StudentDetail activity is opened
        solo.assertCurrentActivity("Expected MainActivity Activity", MainActivity.class);
        //Takes a screenshot and saves it in "/sdcard/Robotium-Screenshots/".
        solo.takeScreenshot();
        //Search for Student 1 and Student 2
        boolean noteFound = solo.searchText(EDITTED_NOTENAME1);

        assertTrue("Note 1 is found", noteFound);
    }


}
