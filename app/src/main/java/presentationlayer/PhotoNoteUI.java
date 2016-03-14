package presentationlayer;


import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import businesslayer.TextNoteWrapper;
import com.example.kushal.rihabhbhandari.R;

import java.util.ArrayList;

import businesslayer.PhotoNoteBL;
import persistancelayer.NoteInterface;

/**
 * UI Layer: PhotoNoteUI
 * BL: ClickableImageBL, PhotoNoteBL
 * PL: Android Library Functions
 */

public class PhotoNoteUI extends Activity {
    private LinearLayout root;

    // Database variables
    private ArrayList<ImageView> imageViews;
    private ArrayList<EditText> editTexts;

    // Default Fields
    private EditText editText_title, editText_label;
    PhotoNoteBL photoNoteBL = new PhotoNoteBL(this);

    final String noteType = "photoNote";
    String allFilePath = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_photo_ui);

        // root
        root = (LinearLayout) findViewById(R.id.LinearLayout_Items);

        // Database variables
        imageViews = new ArrayList<>();
        editTexts = new ArrayList<>();
        editTexts.add((EditText) findViewById(R.id.editText_addPhoto_first_contents));

        // Default Fields
        editText_title = (EditText) findViewById(R.id.editText_addPhoto_title);
        editText_label = (EditText) findViewById(R.id.editText_addPhoto_label);

        // for open from save
        Intent intent = this.getIntent();
        if (intent != null && intent.getBooleanExtra(NoteInterface.OPEN_SAVED, false))
        {
            TextNoteWrapper wrapper = (TextNoteWrapper) intent.getSerializableExtra(NoteInterface.DATA);

            assert (wrapper != null);

            if (wrapper.hasNoteTitle())
                editText_title.setText(wrapper.getNoteTitle());

            if (wrapper.hasTag())
                editText_label.setText(wrapper.getTag());


            // for contents ...

            //	        if (wrapper.hasContents())
            //		        editNote.setText(wrapper.getContents());
            createViewsFromSavedData(wrapper.getContents(), wrapper.getFilePaths());
        }
    }

    public static void openNote(Context context, TextNoteWrapper wrapper)
    {
        Intent intent = new Intent(context, NoteTakerUI.class);
        intent.putExtra(NoteInterface.OPEN_SAVED, true);
        intent.putExtra(NoteInterface.DATA, wrapper);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        ((Activity) context).startActivityForResult(intent, MainActivity.REQUEST_NEW_NOTE);
    }

    private void createViewsFromSavedData(String contents, String allFilePath)
    {
        String substring = contents;

        System.out.printf("SYSOUT: PhotoNoteUI: createViewsFromSavedData(): contents: %s\n", contents);

    }

    // ================================================================
    // Methods for Click Listeners
    // ================================================================

    public void onClickAcceptImageButton(View view) {
        if (editText_title.getText().toString().isEmpty()) {
            Toast.makeText(PhotoNoteUI.this, "Title is not specified", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(PhotoNoteUI.this, "Photo Note is saved", Toast.LENGTH_SHORT).show();

            //add to blPhoto and then it passes to the persistence layer
            int size = editTexts.size();

            //            ArrayList<String> photoNote=new ArrayList<String>();
            String photoNoteText = new String();
            for (int i = 0; i < editTexts.size(); i++) {

                photoNoteText = photoNoteText.concat((editTexts.get(i).getText().toString() + '/'));
            }


            //
            //            for (int i = 0; i < size; i++) {
            //                //editText_textNote = (EditText) findViewById(R.id.editText_addPhoto_first_contents);
            //
            //                //editTexts.add();
            //                photoNoteText=(editTexts.get(i).getText().toString() + "\\" + 1);
            //
            //
            //            }

            editText_title = (EditText) findViewById(R.id.editText_addPhoto_title);

            editText_label = (EditText) findViewById(R.id.editText_addPhoto_label);
            // passing data to PhotoNoteBL
            boolean isInserted = photoNoteBL.create(editText_title.getText().toString(), editText_label.getText().toString(), photoNoteText,allFilePath, noteType);


            if (isInserted == true)
                Toast.makeText(PhotoNoteUI.this, "Note was saved", Toast.LENGTH_LONG).show();
            else
                Toast.makeText(PhotoNoteUI.this, "Note was not saved", Toast.LENGTH_LONG).show();

            // MainActivity.getInstance().dataAdded(editText_title);// not being used
            super.onBackPressed();
        }
    }


    public void onClickCancelImageButton(View view) {
        Toast.makeText(PhotoNoteUI.this, "Photo Note is deleted", Toast.LENGTH_SHORT).show();
        super.onBackPressed();
    }

    final int GET_PHOTO = 0;

    public void onClickAddPhotoImageButton(View view) {
        Intent intent = new Intent(this, PhotoNoteBL.class);
        //	    intent.putExtra()
        startActivityForResult(intent, GET_PHOTO);
    }

    // ================================================================
    // Methods for Dialog Results
    // ================================================================

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == GET_PHOTO) {
                Bundle bundle = data.getExtras();
                Bitmap bm = bundle.getParcelable(PhotoNoteBL.KEY_BITMAP);
                onSuccessfulAddPhoto(bm);
            }
        }
    }

    /*String allPath = "";*/

    private void onSuccessfulAddPhoto(Bitmap bitmap) {
        allFilePath += PhotoNoteBL.filePath + "^";
        final ImageView ivImage = new PhotoNoteBL(this).makeImageView(this, getApplicationContext(), bitmap);
        ivImage.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                showDialogOnLongClickImageButton(ivImage);
                return true;
            }
        });
        addNewImageView(ivImage);
    }

    private void addNewImageView(ImageView imageView) {
        // add new ImageView
        imageViews.add(imageView);
        root.addView(imageView);

        // remove last EditText if it's not used
        if (!editTexts.isEmpty()) {
            EditText lastOne = editTexts.get(editTexts.size() - 1);

            if (lastOne.getText().length() == 0) {
                root.removeView(lastOne);
            }
        }

        // add EditText
        EditText newEditText = new EditText(this);
        newEditText.setHint("Continue Your Notes Here");
        editTexts.add(newEditText);
        root.addView(newEditText);
    }

    private void showDialogOnLongClickImageButton(final ImageView imageView) {
        final CharSequence[] items = {"Delete from my note", "Cancel"};
        //        final CharSequence[] items = {"Delete from my note", "Rotate", "Cancel"};

        AlertDialog.Builder builder = new AlertDialog.Builder(PhotoNoteUI.this);
        builder.setTitle("Options for this image");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                if (items[item].equals("Delete from my note")) {
                    Toast.makeText(PhotoNoteUI.this, "Deleted", Toast.LENGTH_SHORT).show();
                    removeImageView(imageView);
                }
                //                else if (items[item].equals("Rotate"))
                //                {
                //                    Toast.makeText(PhotoNoteUI.this, "Not yet implemented", Toast.LENGTH_SHORT).show();
                //                    dialog.dismiss();
                //                }
                else if (items[item].equals("Cancel")) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();
    }

    private void removeImageView(ImageView imageView) {
        root.removeView(imageView);
    }
}


