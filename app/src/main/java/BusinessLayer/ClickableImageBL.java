package businesslayer;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.graphics.RectF;
import android.util.DisplayMetrics;
import android.widget.ImageView;

/**
 * Created by Matthias on 16-02-20.
 *
 * Used for ImageView in AddPhoto. Tagged in ImageView by PhotoNoteBL.makeImageView().
 * Contains three variables: // Photo / Image / Handwriting = {Bitmap source, Bitmap bigVersion, boolean isSourceSize
 */
public class ClickableImageBL
{
    private ImageView imageView;        // tag. ImageView contains ClickableImageBL
    private Bitmap source;
    private Bitmap bigVersion;
    private boolean isSourceSize;

    public ClickableImageBL(ImageView imageView, Bitmap source, Activity activity)
    {
        this.imageView = imageView;
        this.source = source;
        this.isSourceSize = true;

        // build bigVersion
        DisplayMetrics displayMetrics = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);

        int reqWidth = displayMetrics.widthPixels;
        int reqHeight = displayMetrics.heightPixels;

        this.bigVersion = makeScaledBitmap(source, reqWidth, reqHeight);
    }

    // http://stackoverflow.com/questions/24961797/android-resize-bitmap-keeping-aspect-ratio
    // http://stackoverflow.com/questions/12778806/android-get-bitmap-width-and-height-after-scaled-by-matrix
    private Bitmap makeScaledBitmap(Bitmap b, int reqWidth, int reqHeight)
    {
        Matrix m = new Matrix();
        m.setRectToRect(new RectF(0, 0, b.getWidth(), b.getHeight()), new RectF(0, 0, reqWidth, reqHeight), Matrix.ScaleToFit.CENTER);
        return Bitmap.createBitmap(b, 0, 0, b.getWidth(), b.getHeight(), m, true);
    }

    // in the current version, the tag image view will switch between the small size and the big size.
    // maybe later, show a fullscreen to perform "see big size / rotate / write on it / etc"
    void performClick()
    {
        isSourceSize = !isSourceSize;

        Bitmap target;
        if (isSourceSize)
            target = bigVersion;
        else
            target = source;

        imageView.setImageBitmap(target);
    }
}
