package presentationlayer;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;


// android.graphics.* classes are used for drawing/writing functionality
// android.view.MotionEvent is used to detect the movement of the user's touch input

import businesslayer.HandwritingBL;

/**
 * View class for handwriting functionality.
 * notepadCreate() -- Creation of writing area.
 * changeColor(String newColor) -- For when a different color is selected.
 * setErase(boolean isErase) -- For when Erase button has been pressed.
 * newNote() -- For when New Note button has been pressed.
 * 
 * FOR ITERATION 2: Implement functionality of changing brush and pen sizes.
 */

public class HandwritingView extends View {

    private HandwritingBL handwritingBL;
    private int selectedColor = Color.parseColor("#ff000000"); // Default initial color (black)
    private boolean eraseState=false;
    private Paint penPaint;
    private Paint notepadPaint;
    private Path penPath;
    private Canvas notepadCanvas;
    private Bitmap notepadBitmap;
    private Bitmap bgBitmap;

    public HandwritingView(Context context, AttributeSet attrs) {
        super(context, attrs);
        handwritingBL = new HandwritingBL(context);
        notepadCreate();
    }

    private void notepadCreate() {
        // For creating the writing space
        penPaint = new Paint(); // Writing is done with penPaint and
        penPath = new Path();   // penPath
        notepadPaint = new Paint();

        // Sets attributes of pen
        penPaint.setColor(selectedColor);
        penPaint.setStrokeWidth(15);
        penPaint.setStyle(Paint.Style.STROKE);
        penPaint.setStrokeJoin(Paint.Join.ROUND);
        penPaint.setStrokeCap(Paint.Cap.ROUND);
    }

    // Found in HandwritingBL
    public void changeColor(String newColor) {
        invalidate();
        selectedColor = handwritingBL.changeColor(newColor);
        penPaint.setColor(selectedColor);
    }

    // Found in HandwritingBL
    public void setErase(boolean isErase) {
        // Set eraseState
        eraseState=isErase;
        penPaint.setColor(handwritingBL.setErase(selectedColor, eraseState));
    }

    public void newNote() {
        notepadCanvas.drawColor(0, PorterDuff.Mode.CLEAR);
        invalidate();
    }

    public String saveImage(Bitmap savedImg) {
        String fileLoc = handwritingBL.saveImage(savedImg);
        return fileLoc;
    }

    // If it has been specified that a background is to be used for note
    public void loadImage(byte[] byteArray) {
        bgBitmap = handwritingBL.processImage(byteArray);
    }

    public HandwritingBL getHandwritingBL() {
        return handwritingBL;
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        notepadBitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
        notepadCanvas = new Canvas (notepadBitmap);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if(bgBitmap != null) {
            canvas.drawBitmap(bgBitmap, 0, 0, null);
        }
        canvas.drawBitmap(notepadBitmap, 0, 0, notepadPaint);
        canvas.drawPath(penPath, penPaint);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        // For user touch
        float touchX = event.getX();
        float touchY = event.getY();

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                penPath.moveTo(touchX, touchY);
                break;
            case MotionEvent.ACTION_MOVE:
                penPath.lineTo(touchX, touchY);
                break;
            case MotionEvent.ACTION_UP:
                notepadCanvas.drawPath(penPath, penPaint);
                penPath.reset();
                break;
            default:
                return false;
        }

        invalidate();
        return true;
    }
}
