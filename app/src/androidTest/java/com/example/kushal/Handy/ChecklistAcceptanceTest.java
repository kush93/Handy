package com.example.kushal.Handy;


import android.test.ActivityInstrumentationTestCase2;

import com.example.kushal.rihabhbhandari.R;
import com.robotium.solo.Solo;

import presentationlayer.MainActivity;
import presentationlayer.ChecklistUI;



/**
 * Created by Abdul Hadi on 10/04/2016.
 */
public class ChecklistAcceptanceTest extends ActivityInstrumentationTestCase2<MainActivity>
{
    private Solo solo;

    private static final String CHECKIST_NAME = "SAMPLE CHECKLIST";
    private static final String TASK1 = "Task 1";
    private static final String TASK2 = "Task 2";



    private static final String NAME1 = "Text Note 1";
    private static final String NAME2 = "Text Note 2";
    private static final String LABEL1 = "LABEL 1";
    private static final String LABEL2 = "LABEL 2";
    private static final String NOTETEXT1 = "NOTETEXT 1";
    private static final String NOTETEXT2 = "NOTETEXT 2";
    private static final String EDITTED_NOTENAME1 = "Text Note 1 edited";


    public ChecklistAcceptanceTest()
    {
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



    public void testAddChecklist() throws Exception
    {
        solo.unlockScreen();
        //solo.sleep(1000) inserts a delay of 1000ms. I have put it here so you can see what's happening.
        //You probably only going to need this if you wait for some execution in your app.
        solo.sleep(500);

        solo.clickOnView(solo.getView(R.id.button_checklist));
        solo.assertCurrentActivity("Expected ChecklistUI Activity", ChecklistUI.class);

        solo.sleep(500);
        solo.enterText(0, CHECKIST_NAME);

        solo.clickOnView(solo.getView(R.id.button_add_task));
        solo.assertCurrentActivity("Expected ChecklistUI Activity", ChecklistUI.class);

        solo.sleep(500);
        solo.enterText(0, TASK1);


        solo.clickOnView(solo.getView(R.id.button_save));
        solo.assertCurrentActivity("Expected ChecklistUI Activity", ChecklistUI.class);

    }
}
