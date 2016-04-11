package com.example.kushal.Handy;
import android.test.ActivityInstrumentationTestCase2;

import com.example.kushal.rihabhbhandari.R;
import com.robotium.solo.Solo;
import presentationlayer.MainActivity;
/**
 * Created by kushal on 2016-04-10.
 */
public class DndAcceptanceTest extends ActivityInstrumentationTestCase2<MainActivity>
{
    private Solo solo;
    public DndAcceptanceTest()
    {
        super(MainActivity.class);
    }

    public void setUp() throws Exception {
        solo = new Solo(getInstrumentation());
        getActivity();
    }

    @Override
    public void tearDown() throws Exception {
        solo.finishOpenedActivities();
    }

    public void testDnd() throws Exception
    {
        solo.unlockScreen();
        solo.sleep(500);
        solo.clickOnView(solo.getView(R.id.button_dnd));
        solo.assertCurrentActivity("Expected Main Activity",MainActivity.class);

    }
}
