package com.example.kushal.Handy;

import android.test.ActivityInstrumentationTestCase2;
import android.widget.EditText;
import com.example.kushal.rihabhbhandari.R;
import com.robotium.solo.Solo;
import presentationlayer.MainActivity;
import presentationlayer.PhotoNoteUI;

/**
 * Created by Matthias on 16-04-11.
 */
public class PhotoNoteAcceptanceTest extends ActivityInstrumentationTestCase2<MainActivity>
{
	private Solo solo;

	private static final String PHOTONOTE_NAME1 = "SAMPLE PHOTONOTE 1";
	private static final String PHOTONOTE_NAME2 = "SAMPLE PHOTONOTE 2";
	private static final String CONTENTS = "SAMPLE CONTENTS";
	private static final String LABEL = "SAMPLE LABELS";

	public PhotoNoteAcceptanceTest()
	{
		super(MainActivity.class);
	}
	@Override
	public void setUp() throws Exception {

		solo = new Solo(getInstrumentation());
		//Getting activity reference to perform operation in the test cases below
		getActivity();
	}


	@Override
	public void tearDown() throws Exception {
		solo.finishOpenedActivities();
	}


	public void testPhotoNoteTypeContents() throws Exception
	{
		solo.unlockScreen();
		solo.sleep(500);

		solo.clickOnView(solo.getView(R.id.button_main_open_photo_note));
		solo.sleep(500);
		solo.assertCurrentActivity("Expected PhotoNoteUI Activity", PhotoNoteUI.class);

		EditText title = (EditText) solo.getView(R.id.editText_addPhoto_title);
		EditText contents = (EditText) solo.getView(R.id.editText_addPhoto_first_contents);

		solo.sleep(500);
		solo.enterText(title, PHOTONOTE_NAME1);

		solo.clickOnView(solo.getView(R.id.editText_addPhoto_first_contents));
		solo.sleep(500);
		solo.assertCurrentActivity("Expected PhotoNoteUI Activity", PhotoNoteUI.class);

		solo.sleep(500);
		solo.enterText(contents, CONTENTS);

		solo.clickOnView(solo.getView(R.id.imageButton_addPhoto_accept));
		solo.assertCurrentActivity("Expected MainActivity", MainActivity.class);
	}

	public void testBackButton() throws Exception
	{
		solo.unlockScreen();
		solo.sleep(500);

		solo.clickOnView(solo.getView(R.id.button_main_open_photo_note));
		solo.sleep(500);
		solo.assertCurrentActivity("Expected PhotoNoteUI Activity", PhotoNoteUI.class);

		EditText title = (EditText) solo.getView(R.id.editText_addPhoto_title);
		EditText label = (EditText) solo.getView(R.id.editText_addPhoto_label);

		solo.sleep(500);
		solo.enterText(title, PHOTONOTE_NAME2);

		solo.clickOnView(solo.getView(R.id.editText_addPhoto_label));
		solo.sleep(500);
		solo.assertCurrentActivity("Expected PhotoNoteUI Activity", PhotoNoteUI.class);

		solo.sleep(500);
		solo.enterText(label, LABEL);

		solo.clickOnView(solo.getView(R.id.imageButton_addPhoto_accept));
		solo.assertCurrentActivity("Expected MainActivity", MainActivity.class);
	}

}
