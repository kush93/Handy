package com.example.kushal.Handy;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.view.View;
import android.widget.GridLayout;
import android.widget.ImageView;

/**
 * Created by Matthias on 16-02-19.
 */
public class AddPhotoBL
{
    private boolean zoomOut = false;

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
        // GradientDrawable gd = new GradientDrawable();
        // gd.setColor(0xFF00FF00); // Changes this drawbale to use a single color instead of a gradient
        //gd.setCornerRadius(5);
        //gd.setStroke(1, 0xFF000000);

        //ivImage.setBackgroundDrawable(gd);
//        Display display = activity.getWindowManager().getDefaultDisplay();
//
//        int width = display.getWidth();
//        int height = display.getHeight();
//
//        ivImage.setMinimumWidth(width);
//        ivImage.setMinimumHeight(height);
//
//        ivImage.setMaxWidth(width);
//        ivImage.setMaxHeight(height);
        //ivImage.getLayoutParams().width = 20;
        //ivImage.getLayoutParams().height = 20;
        ivImage.setLayoutParams(new ActionBar.LayoutParams(
                GridLayout.LayoutParams.WRAP_CONTENT,
                GridLayout.LayoutParams.WRAP_CONTENT));
        ivImage.setImageBitmap(bitmap);

        return ivImage;
    }
}
