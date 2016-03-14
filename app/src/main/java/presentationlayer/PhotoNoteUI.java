package presentationlayer;


import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.kushal.rihabhbhandari.R;

import java.util.ArrayList;

import businesslayer.PhotoNoteBL;

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
    private EditText editText_textNote;
    PhotoNoteBL photoNoteBL = new PhotoNoteBL(this);

    final String noteType = "photoNote";
    final String filePath = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_photo_ui);

        // root
        root = (LinearLayout) findViewById(R.id.LinearLayout_Items);

        // Database variables
        imageViews = new ArrayList<>();
        editTexts = new ArrayList<>();


        // Default Fields
        editText_title = (EditText) findViewById(R.id.editText_addPhoto_title);

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
            String photoNoteText = null;
          photoNoteText=(editTexts.get(0).getText().toString() + "\\" + 1);
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
            boolean isInserted = photoNoteBL.create(editText_title.getText().toString(), editText_label.getText().toString(), photoNoteText, noteType);


            if (isInserted == true)
                Toast.makeText(PhotoNoteUI.this, "Note was saved", Toast.LENGTH_LONG).show();
            else
                Toast.makeText(PhotoNoteUI.this, "Note was not saved", Toast.LENGTH_LONG).show();

           // MainActivity.getInstance().dataAdded(editText_title);// not being used
            super.onBackPressed();
        }
    }


    public void onClickCancelImageButton(View view)
    {
        Toast.makeText(PhotoNoteUI.this, "Photo Note is deleted", Toast.LENGTH_SHORT).show();
        super.onBackPressed();
    }

	final int GET_PHOTO = 0;

    public void onClickAddPhotoImageButton(View view)
    {
	    Intent intent = new Intent(this, PhotoNoteBL.class);
//	    intent.putExtra()
	    startActivityForResult(intent, GET_PHOTO);
    }

    // ================================================================
    // Methods for Dialog Results
    // ================================================================

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == Activity.RESULT_OK)
        {
            if (requestCode == GET_PHOTO)
            {
	            Bundle bundle = data.getExtras();
	            Bitmap bm = bundle.getParcelable(PhotoNoteBL.KEY_BITMAP);
	            onSuccessfulAddPhoto(bm);
            }
        }
    }

    private void onSuccessfulAddPhoto(Bitmap bitmap)
    {
        final ImageView ivImage = new PhotoNoteBL(this).makeImageView(this, getApplicationContext(), bitmap);
        ivImage.setOnLongClickListener(new View.OnLongClickListener()
        {
	        @Override
	        public boolean onLongClick(View v)
	        {
		        showDialogOnLongClickImageButton(ivImage);
		        return true;
	        }
        });
        addNewImageView(ivImage);
    }

    private void addNewImageView(ImageView imageView)
    {
        // add new ImageView
        imageViews.add(imageView);
        root.addView(imageView);

        // remove last EditText if it's not used
        if (!editTexts.isEmpty())
        {
            EditText lastOne = editTexts.get(editTexts.size() - 1);

            if (lastOne.getText().length() == 0)
            {
                root.removeView(lastOne);
            }
        }

        // add EditText
        EditText newEditText = new EditText(this);
        newEditText.setHint("Continue Your Notes Here");
        editTexts.add(newEditText);
        root.addView(newEditText);
    }

    private void showDialogOnLongClickImageButton(final ImageView imageView)
    {
        final CharSequence[] items = {"Delete from my note", "Cancel"};
//        final CharSequence[] items = {"Delete from my note", "Rotate", "Cancel"};

        AlertDialog.Builder builder = new AlertDialog.Builder(PhotoNoteUI.this);
        builder.setTitle("Options for this image");
        builder.setItems(items, new DialogInterface.OnClickListener()
        {
            @Override
            public void onClick(DialogInterface dialog, int item)
            {
                if (items[item].equals("Delete from my note"))
                {
                    Toast.makeText(PhotoNoteUI.this, "Deleted", Toast.LENGTH_SHORT).show();
                    removeImageView(imageView);
                }
//                else if (items[item].equals("Rotate"))
//                {
//                    Toast.makeText(PhotoNoteUI.this, "Not yet implemented", Toast.LENGTH_SHORT).show();
//                    dialog.dismiss();
//                }
                else if (items[item].equals("Cancel"))
                {
                    dialog.dismiss();
                }
            }
        });
        builder.show();
    }

    private void removeImageView(ImageView imageView)
    {
        root.removeView(imageView);
    }
}


