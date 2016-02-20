package com.example.kushal.Handy;


import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.GradientDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;

import android.support.v7.app.AppCompatActivity;
import android.view.Display;
import android.view.View;

import android.widget.*;
import com.example.kushal.rihabhbhandari.R;

// todo 1. add texts between photos
// todo 2. able to delete the last photo (or a list of all photos)
// todo 3. perhaps rotation for each photo
// todo 4. make them arraylist ... when open this page, show "last working section?" and show a sample list
// todo 5: UI: make the screen scrollable, move button's position

public class AddPhotoUI extends Activity
{
//    private boolean zoomOut = false;
    int REQUEST_CAMERA = 0, SELECT_FILE = 1;
    Button btnSelect;
    Button btnRemove;
    // ImageView ivImage;

    LinearLayout root;


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

        // btnSelect
        btnSelect = (Button) findViewById(R.id.btnSelectPhoto);

        // ivImage=new ImageView()
        btnSelect.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                selectImage();
            }
        });

        // btnRemove
        btnRemove = (Button) findViewById(R.id.btnRemoveLastPhoto);
        btnRemove.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                removeLastImageView();
            }
        });

        // Database variables
        imageViews = new ArrayList<>();
        editTexts = new ArrayList<>();

    }

    private void selectImage()
    {
        final CharSequence[] items = {"Take Photo", "Choose from Library", "Cancel"};

        AlertDialog.Builder builder = new AlertDialog.Builder(AddPhotoUI.this);
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

        final ImageView ivImage = new AddPhotoBL().makeImageView(this, getApplicationContext(), bm);
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

        final ImageView ivImage = new AddPhotoBL().makeImageView(this, getApplicationContext(), bm);
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
        editTexts.add(newEditText);
        root.addView(newEditText);
    }

    private void removeLastImageView()
    {
        if (!imageViews.isEmpty())
        {
            ImageView removed = imageViews.remove(imageViews.size() - 1);
            root.removeView(removed);
        }
    }
}


