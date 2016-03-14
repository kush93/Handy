package presentationlayer;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.EditText;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.view.View.OnClickListener;
import android.graphics.Bitmap;
import android.widget.Toast;
import android.text.InputType;
import java.io.*;

import com.example.kushal.rihabhbhandari.R;
import businesslayer.HandwritingBL;
import businesslayer.HandwritingWrapper;

/**
 * Main activity for the handwriting functionality.
 * Files modified / made for this functionality:
 * - HandwritingUI.java
 * - HandwritingView.java
 * - HandwritingBL.java
 * - MainActivity.manifest
 *
 * res files:
 * - Various drawables (some work for later: Find more efficient way of displaying colors)
 * - content_writing_main.xml
 * - activity_writing_main.xml (however, these two layout .xml can probably be condensed)
 * - dimens.xml
 * - strings.xml
 * - AndroidManifest.xml
 *
 * colorClicked(View view) - Detects if a different color has been selected.
 *k
 * FOR ITERATION 2: Implement actual save functionality
 * FOR ITERATION 2: Implement multiple pencil and eraser sizes (strings already found in dimens.xml)
 */

public class HandwritingUI extends Activity implements OnClickListener {

    private HandwritingView handwritingView;
    private HandwritingBL handwritingBL;
    ImageButton currColor;
    ImageButton writingBtn;
    ImageButton eraserBtn;
    ImageButton newBtn;
    ImageButton saveBtn;

    static String emptyString = "";
    final String noteType = "handwritingNote";

    private String handwrittenTitle = null;
    private String fileName = null;
    private String tempFileName = null;

    private final static String OPEN_SAVED  = "OPEN_SAVED";
    private final static String DATA        = "DATA";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_writing_main);

        handwritingView = (HandwritingView)findViewById(R.id.handwriting);
        writingBtn = (ImageButton)findViewById(R.id.pencilBtn);
        eraserBtn = (ImageButton)findViewById(R.id.eraseBtn);
        newBtn = (ImageButton)findViewById(R.id.newBtn);
        saveBtn = (ImageButton)findViewById(R.id.saveBtn);

        Intent intent = this.getIntent();
        if (intent != null && intent.getBooleanExtra(OPEN_SAVED, false))
        {
            HandwritingWrapper wrapper = (HandwritingWrapper) intent.getSerializableExtra(DATA);

            assert (wrapper != null);

            if (wrapper.hasNoteTitle())
                handwrittenTitle = wrapper.getNoteTitle();

            if (wrapper.hasImages())
                handwritingView.loadImage(wrapper.getImages().get(0));

            fileName = wrapper.getFileName();

            System.out.printf("SYSOUT: HandwritingUI.onCreate(): if boolean == true\n");
            System.out.printf("SYSOUT: wrapper.getTitle() == \n");
            System.out.printf("SYSOUT: wrapper.getTitle() == %s\n", wrapper.getNoteTitle());
        }
        else
            System.out.printf("SYSOUT: if boolean == false\n");

        if (intent != null && intent.getByteArrayExtra("image") != null) {
            byte[] byteArray = intent.getByteArrayExtra("image");
            handwritingView.loadImage(byteArray);
        }

        handwritingBL = handwritingView.getHandwritingBL();

        // By default, black is selected color.
        // Makes black button slightly transparent to indicate that it is selected.
        LinearLayout colorBox = (LinearLayout)findViewById(R.id.colourOptions);
        currColor = (ImageButton)colorBox.getChildAt(0);
        currColor.setAlpha((float) 0.50);

        writingBtn.setOnClickListener(this);
        eraserBtn.setOnClickListener(this);
        newBtn.setOnClickListener(this);
        saveBtn.setOnClickListener(this);
    }

    // When user presses one of the top row buttons
    @Override
    public void onClick(View view){
        if(view.getId()==R.id.pencilBtn){
            // Pencil button has been clicked
            handwritingView.setErase(false);
        }
        else if(view.getId()==R.id.eraseBtn){
            // Erase button has been clicked
            handwritingView.setErase(true);
        }
        else if(view.getId()==R.id.newBtn){
            // New note button has been clicked
            AlertDialog.Builder newDialog = new AlertDialog.Builder(this);
            newDialog.setTitle("New Note");
            newDialog.setMessage("Would you like to start a new note (you will lose the current note)?");
            newDialog.setPositiveButton("Okay", new DialogInterface.OnClickListener(){
                public void onClick(DialogInterface dialog, int which){
                    handwritingView.newNote();
                    fileName = null;
                    tempFileName = null;
                    handwrittenTitle = null;
                    dialog.dismiss();
                }
            });
            newDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener(){
                public void onClick(DialogInterface dialog, int which){
                    dialog.cancel();
                }
            });
            newDialog.show();
        }
        else if(view.getId()==R.id.saveBtn){
            // Save button has been clicked

            if(fileName != null) {
                AlertDialog.Builder overwriteDialog = new AlertDialog.Builder(this);

                overwriteDialog.setTitle("Save Note");
                overwriteDialog.setMessage("This Handwritten Note already exists, would you like to overwrite it?");
                overwriteDialog.setPositiveButton("Overwrite File", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // Can now continue with save.
                        savePrompt();
                        dialog.dismiss();
                        //tempFilePath = null;
                    }
                });
                overwriteDialog.setNegativeButton("Save to New File", new DialogInterface.OnClickListener() {
                   public void onClick(DialogInterface dialog, int which) {
                       savePrompt();
                       tempFileName = fileName;
                       fileName = null;
                       dialog.dismiss();
                   }
                });
                overwriteDialog.setNeutralButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // Does not continue with save.
                        dialog.cancel();
                    }
                });
                overwriteDialog.show();
            }
            else {
                savePrompt();
            }
            if(tempFileName != null) {
                if(fileName == null) {
                    // Pressed Save to New File, but didn't Save
                    fileName = tempFileName;
                    tempFileName = null;
                }
                else {
                    // Pressed Save to New File, and did Save
                    tempFileName = null;
                }
            }
        }
    }

    private void savePrompt() {
        AlertDialog.Builder saveDialog = new AlertDialog.Builder(this);

        saveDialog.setTitle("Save Note");
        saveDialog.setMessage("Please enter a title and press Okay to Save.");
        final EditText input = new EditText(this);
        if(handwrittenTitle != null)
            input.setText(handwrittenTitle);
        input.setInputType(InputType.TYPE_CLASS_TEXT);
        saveDialog.setView(input);

        saveDialog.setPositiveButton("Okay", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                // Saves
                handwrittenTitle = input.getText().toString();
                handwritingView.setDrawingCacheEnabled(true);
                Bitmap currentImage = Bitmap.createBitmap(handwritingView.getDrawingCache());
                String savedFile = handwritingView.saveImage(currentImage, fileName);

                if (savedFile != null) {
                    String popupMsg = ("Saved as " + savedFile);
                    Toast popupWindow = Toast.makeText(getApplicationContext(),
                            popupMsg, Toast.LENGTH_SHORT);
                    popupWindow.show();
                    boolean isInserted = handwritingBL.create(handwrittenTitle, emptyString, emptyString, savedFile, noteType);

                    fileName = savedFile;
                } else {
                    String popupMsg = "Note could not be saved.";
                    Toast popupWindow = Toast.makeText(getApplicationContext(),
                            popupMsg, Toast.LENGTH_SHORT);
                    popupWindow.show();
                }
                tempFileName = null;
                handwritingView.destroyDrawingCache();
            }
        });
        saveDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                if(tempFileName != null) {
                    fileName = tempFileName;
                    tempFileName = null;
                }
                dialog.cancel();
            }
        });
        saveDialog.show();
    }

    // When user presses one of the color buttons
    public void colorClicked(View view) {
        //Selecting a colour
        if(view != currColor) {
            handwritingView.setErase(false); // Selecting a color switches to write mode
            ImageButton btnView = (ImageButton)view;
            String selColor = view.getTag().toString();
            handwritingView.changeColor(selColor);

            //Visually change selected color
            btnView.setAlpha((float) 0.50);
            currColor.setAlpha((float) 1.00);
            currColor = (ImageButton)view;
        }
    }

    public static void openNote(Context context, HandwritingWrapper wrapper){
        Intent intent = new Intent(context, HandwritingUI.class);
        intent.putExtra(OPEN_SAVED, true);
        intent.putExtra(DATA, wrapper);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        ((Activity) context).startActivityForResult(intent, MainActivity.REQUEST_NEW_NOTE);
    }
}
