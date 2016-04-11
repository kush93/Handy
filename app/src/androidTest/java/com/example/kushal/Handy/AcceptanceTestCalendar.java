package com.example.kushal.Handy;
import android.test.ActivityInstrumentationTestCase2;

import com.example.kushal.rihabhbhandari.R;
import com.robotium.solo.Solo;

import presentationlayer.MainActivity;
/**
 * Created by kushal on 2016-04-10.
 */
public class AcceptanceTestCalendar extends ActivityInstrumentationTestCase2<MainActivity>
{
    private Solo solo;
    public AcceptanceTestCalendar()
    {
        super(MainActivity.class);
    }
    public void setUp() throws Exception {
        solo = new Solo(getInstrumentation());
        //Getting activity reference to perform operation in the test cases below
        getActivity();
    }

    //override tearDown and do all the stuff you are supposed to do in the end, delete temp files etc
    @Override
    public void tearDown() throws Exception {
        solo.finishOpenedActivities();
    }

    public void testCalendar() throws Exception
    {
        solo.unlockScreen();

        solo.sleep(500);
        solo.clickOnView(solo.getView(R.id.button_calendar));
        solo.assertCurrentActivity("Expected Main Activity", MainActivity.class);

    }

}
