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
    private static final String NAME3 = "Text Note 3";
    private static final String LABEL1 = "LABEL 1";
    private static final String LABEL2 = "LABEL 2";
    private static final String LABEL3 = "LABEL 3";
    private static final String NOTETEXT1 = "NOTETEXT 1";
    private static final String NOTETEXT2 = "NOTETEXT 2";
    private static final String NOTETEXT3 = "NOTETEXT 3";

    public TextNoteAcceptanceTest() {
        super(MainActivity.class);
    }
    //override setUp and do all the stuff you are supposed to do in the beginning of an app, like creating DB if not present etc.
    @Override
    public void setUp() throws Exception {
        //setUp() is run before a test case is started.
        //This is where the solo object is created.
        solo = new Solo(getInstrumentation());
        //Getting activity reference to perform operation in the test cases below
        getActivity();
    }




    //override tearDown and do all the stuff you are supposed to do in the end, delete temp files etc
    @Override
    public void tearDown() throws Exception {
        //tearDown() is run after a test case has finished.
        //finishOpenedActivities() will finish all the activities that have been opened during the test execution.
        solo.finishOpenedActivities();
    }

    public void testAddTextNote() throws Exception{


        //Unlock the lock screen
        solo.unlockScreen();
        //solo.sleep(1000) inserts a delay of 1000ms. I have put it here so you can see what's happening.
        //You probably only going to need this if you wait for some execution in your app.
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


    }

}
