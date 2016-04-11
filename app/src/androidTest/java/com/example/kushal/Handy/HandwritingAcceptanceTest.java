package com.example.kushal.Handy;

import android.test.ActivityInstrumentationTestCase2;

import com.example.kushal.rihabhbhandari.R;
import com.robotium.solo.Solo;

import presentationlayer.MainActivity;
import presentationlayer.HandwritingUI;

/**
 * Created by Ian on 10/04/2016.
 */
public class HandwritingAcceptanceTest extends ActivityInstrumentationTestCase2<MainActivity> {
    private Solo solo;
    private static final String NAME1 = "Handwritten Note 1";

    public HandwritingAcceptanceTest() {
        super(MainActivity.class);
    }

    @Override
    public void setUp() throws Exception {
        // Solo object is created here
        solo = new Solo(getInstrumentation());
        // Get activity reference
        getActivity();
    }

    @Override
    public void tearDown() throws Exception {
        // Run after a test case has been completed
        solo.finishOpenedActivities();
    }

    // Test saving a handwritten note
    // Then attempts to save again, which will cause an overwrite to be detected
    public void testAddHandwritingNote() throws Exception {
        solo.unlockScreen();
        solo.sleep(500);
        solo.clickOnView(solo.getView(R.id.button_main_open_hand_writing));
        solo.assertCurrentActivity("Expected HandwritingUI Activity", HandwritingUI.class);
        solo.sleep(500);
        solo.clickOnView(solo.getView(R.id.saveBtn));
        solo.sleep(500);
        solo.enterText(0, NAME1);
        solo.sleep(500);
        solo.clickOnText("Okay");
        solo.assertCurrentActivity("Expected HandwritingUI Activity", HandwritingUI.class);
        solo.sleep(500);

        // To check if file has been saved, will attempt to override file
        // The previous entered title should still be there upon attempting to override
        solo.clickOnView(solo.getView(R.id.saveBtn));
        solo.sleep(500);
        solo.clickOnText("Overwrite File");
        solo.sleep(500);
        String enteredTitle = solo.getEditText(0).getText().toString();
        boolean isSame = enteredTitle.equals(NAME1);
        assertTrue("This file is being overwritten. Thus, saving was successful.", isSame);
        solo.clickOnText("Okay");
    }

    // Test creating a new note after another note has already been saved
    public void testNewHandwritingNote() throws Exception {
        solo.unlockScreen();
        solo.sleep(500);
        solo.clickOnView(solo.getView(R.id.button_main_open_hand_writing));
        solo.assertCurrentActivity("Expected HandwritingUI Activity", HandwritingUI.class);
        solo.sleep(500);
        solo.clickOnView(solo.getView(R.id.saveBtn));
        solo.sleep(500);
        solo.enterText(0, NAME1);
        solo.sleep(500);
        solo.clickOnText("Okay");
        solo.assertCurrentActivity("Expected HandwritingUI Activity", HandwritingUI.class);
        solo.sleep(500);
        solo.clickOnView(solo.getView(R.id.newBtn));
        solo.sleep(500);
        solo.clickOnText("Okay");

        // Now that a new note has been made, the previously entered title should not be
        // suggested
        solo.assertCurrentActivity("Expected HandwritingUI Activity", HandwritingUI.class);
        solo.sleep(500);
        solo.clickOnView(solo.getView(R.id.saveBtn));
        solo.sleep(500);
        String enteredTitle = solo.getEditText(0).getText().toString();
        boolean isEmpty;
        if(enteredTitle == null || enteredTitle.isEmpty() || enteredTitle.equals("")) {
            isEmpty = true;
        }
        else {
            isEmpty = false;
        }

        assertTrue("A new note was made. Thus previously saved file was not being overwritten.", isEmpty);
        solo.clickOnText("Cancel");
    }
}
