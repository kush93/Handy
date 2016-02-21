package com.example.kushal.Handy;


import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;

import android.view.View;

import android.widget.*;
import com.example.kushal.rihabhbhandari.R;

// todo: perhaps rotation for each photo

/**
 * UI Layer: PhotoNoteUI_Rename
 * BL: ClickableImageBL, PhotoNoteBL
 * PL: Android Library Functions
 */

public class PhotoNoteUI_Rename extends Activity
{
//    private boolean zoomOut = false;
    private int REQUEST_CAMERA = 0, SELECT_FILE = 1;

    private LinearLayout root;


    // Database variables
    private ArrayList<ImageView> imageViews;
    private ArrayList<EditText> editTexts;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_photo_ui);

        // root
        root = (LinearLayout) findViewById(R.id.LinearLayout_Items);

        // Database variables
        imageViews = new ArrayList<>();
        editTexts = new ArrayList<>();

    }

    public void onClickAcceptImageButton(View view)
    {
        Toast.makeText(PhotoNoteUI_Rename.this, "Photo Note is saved (not yet implemented).", Toast.LENGTH_SHORT).show();
        super.onBackPressed();
    }

    public void onClickCancelImageButton(View view)
    {
        Toast.makeText(PhotoNoteUI_Rename.this, "Photo Note is deleted.", Toast.LENGTH_SHORT).show();
        super.onBackPressed();
    }

    public void onClickAddPhotoImageButton(View view)
    {
        final CharSequence[] items = {"Take Photo", "Choose from Library", "Cancel"};

        AlertDialog.Builder builder = new AlertDialog.Builder(PhotoNoteUI_Rename.this);
        builder.setTitle("Add Photo!");
        builder.setItems(items, new DialogInterface.OnClickListener()
        {
            @Override
            public void onClick(DialogInterface dialog, int item)
            {
                if (items[item].equals("Take Photo"))
                {
                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(intent, REQUEST_CAMERA);
                } else if (items[item].equals("Choose from Library"))
                {
                    Intent intent = new Intent(
                            Intent.ACTION_PICK,
                            android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    intent.setType("image/* video/*");
                    startActivityForResult(
                            Intent.createChooser(intent, "Select File"),
                            SELECT_FILE);
                } else if (items[item].equals("Cancel"))
                {
                    dialog.dismiss();
                }
            }
        });
        builder.show();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == Activity.RESULT_OK)
        {
            if (requestCode == SELECT_FILE)
                onSelectFromGalleryResult(data);
            else if (requestCode == REQUEST_CAMERA)
                onCaptureImageResult(data);
        }
    }

    private void onCaptureImageResult(Intent data)
    {
        Bitmap bm = (Bitmap) data.getExtras().get("data");
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        bm.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        // Bitmap resized = Bitmap.createScaledBitmap(bm, 800, 150, true);
        File destination = new File(Environment.getExternalStorageDirectory(),
                                    System.currentTimeMillis() + ".jpg");

        FileOutputStream fo;
        try
        {
            destination.createNewFile();
            fo = new FileOutputStream(destination);
            fo.write(bytes.toByteArray());
            fo.close();
        } catch (FileNotFoundException e)
        {
            e.printStackTrace();
        } catch (IOException e)
        {
            e.printStackTrace();
        }

        final ImageView ivImage = new PhotoNoteBL().makeImageView(this, getApplicationContext(), bm);
        ivImage.setOnLongClickListener(new View.OnLongClickListener()
        {
            @Override
            public boolean onLongClick(View v)
            {

                return true;
            }
        });
        addNewImageView(ivImage);
    }

    @SuppressWarnings ("deprecation")
    private void onSelectFromGalleryResult(Intent data)
    {
        Uri selectedImageUri = data.getData();
        String[] projection = {MediaStore.MediaColumns.DATA};
        Cursor cursor = managedQuery(selectedImageUri, projection, null, null,
                                     null);
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.DATA);
        cursor.moveToFirst();

        String selectedImagePath = cursor.getString(column_index);

        Bitmap bm;
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(selectedImagePath, options);
        final int REQUIRED_SIZE = 200;
        int scale = 1;
        while (options.outWidth / scale / 2 >= REQUIRED_SIZE
               && options.outHeight / scale / 2 >= REQUIRED_SIZE)
            scale *= 2;
        options.inSampleSize = scale;
        options.inJustDecodeBounds = false;
        bm = BitmapFactory.decodeFile(selectedImagePath, options);

        final ImageView ivImage = new PhotoNoteBL().makeImageView(this, getApplicationContext(), bm);
        ivImage.setOnLongClickListener(new View.OnLongClickListener()
        {
            @Override
            public boolean onLongClick(View v)
            {
                showDialogOnLongClickImageButton((ImageView) v);
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
        final CharSequence[] items = {"Delete from my note", "Rotate", "Cancel"};

        AlertDialog.Builder builder = new AlertDialog.Builder(PhotoNoteUI_Rename.this);
        builder.setTitle("Options for this image");
        builder.setItems(items, new DialogInterface.OnClickListener()
        {
            @Override
            public void onClick(DialogInterface dialog, int item)
            {
                if (items[item].equals("Delete from my note"))
                {
                    Toast.makeText(PhotoNoteUI_Rename.this, "Deleted", Toast.LENGTH_SHORT).show();
                    removeImageView(imageView);
                }
                else if (items[item].equals("Rotate"))
                {
                    Toast.makeText(PhotoNoteUI_Rename.this, "Not yet implemented", Toast.LENGTH_SHORT).show();
                    dialog.dismiss();
                }
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


