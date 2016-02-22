package BusinessLayer;


import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.KeyEvent;               // handling back-key is not sufficient for handling exiting dialog
import android.view.View;
import android.widget.GridLayout;
import android.widget.ImageView;

import java.io.*;

/**
 * Created by Matthias on 16-02-19.
 */
public class PhotoNoteBL extends Activity
{
	public static final String KEY_BITMAP = "bitmap";

	private int REQUEST_CAMERA = 0, SELECT_FILE = 1;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);

		callDialogBox();
	}

	void callDialogBox()
	{
		final CharSequence[] items = {"Take Photo", "Choose from Library", "Cancel"};

		AlertDialog.Builder builder = new AlertDialog.Builder(PhotoNoteBL.this);
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
				}
				else if (items[item].equals("Choose from Library"))
				{
					Intent intent = new Intent(
							Intent.ACTION_PICK,
							android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
					intent.setType("image/* video/*");
					startActivityForResult(
							Intent.createChooser(intent, "Select File"),
							SELECT_FILE);
				}
				else  // if (items[item].equals("Cancel"))
				{
					dialog.dismiss();
					finishOnCancel();
				}
			}
		});

		builder.setOnCancelListener(new DialogInterface.OnCancelListener()
		{
			@Override
			public void onCancel(DialogInterface dialog)
			{
				finishOnCancel();
			}
		});

		builder.show();
	}

	private void finishOnCancel()
	{
		setResult(Activity.RESULT_CANCELED, new Intent());
		finish();
	}

	// ================================================================
	// Methods for Dialog Results
	// ================================================================

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data)
	{
		super.onActivityResult(requestCode, resultCode, data);


		if (resultCode == Activity.RESULT_OK && (requestCode == SELECT_FILE || requestCode == REQUEST_CAMERA))
		{
			Bitmap bm;

			if (requestCode == SELECT_FILE)
				bm = onSelectFromGalleryResult(data);
			else // if (requestCode == REQUEST_CAMERA)
				bm = onCaptureImageResult(data);

			Intent resultIntent = new Intent();
			resultIntent.putExtra(KEY_BITMAP, bm);
			setResult(Activity.RESULT_OK, resultIntent);
			finish();
		}
		else
		{
			finishOnCancel();
		}
	}

	@SuppressWarnings ("deprecation")
	private Bitmap onSelectFromGalleryResult(Intent data)
	{
		Uri selectedImageUri = data.getData();
		String[] projection = {MediaStore.MediaColumns.DATA};
		Cursor cursor = managedQuery(selectedImageUri, projection, null, null, null);
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

		return bm;
	}

	private Bitmap onCaptureImageResult(Intent data)
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

		return bm;
	}


	/**
	 * Assign the given bitmap to a new ImageView, and sets onClick() method for zoom in/out for the bitmap,
	 *
	 * @param activity of the callee
	 * @param context of the callee
	 * @param bitmap
	 * @return
	 */
	public final ImageView makeImageView(Activity activity, final Context context, Bitmap bitmap)
	{
		final ImageView ivImage = new ImageView(context);

		// Photo = {Bitmap source, Bitmap bigVersion, boolean isSmall
		ivImage.setTag(new ClickableImageBL(ivImage, bitmap, activity));

		ivImage.setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				((ClickableImageBL) ivImage.getTag()).performClick();
			}
		});

		ivImage.setLayoutParams(new ActionBar.LayoutParams(
				GridLayout.LayoutParams.WRAP_CONTENT,
				GridLayout.LayoutParams.WRAP_CONTENT));
		ivImage.setImageBitmap(bitmap);

		return ivImage;
	}
}
