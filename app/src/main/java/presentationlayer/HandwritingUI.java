package presentationlayer;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.view.View.OnClickListener;

import com.example.kushal.rihabhbhandari.R;

/**
 * Main activity for the handwriting functionality.
 * Files modified / made for this functionality:
 * - HandwritingUI.java
 * - HandwritingView.java
 * - HandwritingBL.java
 * - MainActivity.manifest
 *
 * res files:
 * - Various drawables (TODO: Find more efficient way of displaying colors)
 * - content_writing_main.xml
 * - activity_writing_main.xml (however, these two layout .xml can probably be condensed)
 * - dimens.xml
 * - strings.xml
 * - AndroidManifest.xml
 *
 * colorClicked(View view) - Detects if a different color has been selected.
 *
 * FOR ITERATION 2: Implement actual save functionality
 * FOR ITERATION 2: Implement multiple pencil and eraser sizes (strings already found in dimens.xml)
 */

public class HandwritingUI extends Activity implements OnClickListener {

    private HandwritingView handwritingView;
    ImageButton currColor;
    ImageButton writingBtn;
    ImageButton eraserBtn;
    ImageButton newBtn;
    ImageButton saveBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_writing_main);
        handwritingView = (HandwritingView)findViewById(R.id.handwriting);
        writingBtn = (ImageButton)findViewById(R.id.pencilBtn);
        eraserBtn = (ImageButton)findViewById(R.id.eraseBtn);
        newBtn = (ImageButton)findViewById(R.id.newBtn);
        saveBtn = (ImageButton)findViewById(R.id.saveBtn);

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
            AlertDialog.Builder saveDialog = new AlertDialog.Builder(this);
            saveDialog.setTitle("Save Note");
            saveDialog.setMessage("Save note to device Gallery?");
            saveDialog.setPositiveButton("Okay", new DialogInterface.OnClickListener(){
                public void onClick(DialogInterface dialog, int which){
                    // Saves
                }
            });
            saveDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener(){
                public void onClick(DialogInterface dialog, int which){
                    dialog.cancel();
                }
            });
            saveDialog.show();
        }
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
}
