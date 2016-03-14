package businesslayer;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import persistancelayer.NoteInterface;
import presentationlayer.HandwritingUI;


/**
 * Created by Ian on 13/03/2016.
 */
public class HandwritingWrapper extends Activity implements NoteInterface, Serializable {
    private String id;
    private String title;
    private String contents;
    private String tags;
    private String time;
    private String filePath;
    private boolean isPinned;

    HandwritingBL handwritingBL;

    public HandwritingWrapper(String id, String title, String contents, String tags, String time, String filePath, boolean isPinned) {
        this.id = id;
        this.title = title;
        this.contents = contents;
        this.tags = tags;
        this.time = time;
        this.filePath=filePath;
        this.isPinned = isPinned;
    }
    public HandwritingWrapper (Context context)
    {
        handwritingBL = new HandwritingBL(context);

    }

    @Override
    public void openNote(Context context){
        HandwritingUI.openNote(context, this);
    }

    @Override
    public boolean hasImages() {
        return true;
    }

    @Override
    public List<Bitmap> getImages() {
        List<Bitmap> imageList = new ArrayList<>();

        String path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES) + "/Handy/";
        path = path + getFilePaths();
        BitmapFactory.Options bmOptions = new BitmapFactory.Options();
        Bitmap bmp = BitmapFactory.decodeFile(path, bmOptions);
        imageList.add(bmp);
        return imageList;
    }

    @Override
    public boolean isPinned() {
        return isPinned;
    }

    @Override
    public boolean hasNoteTitle() {
        return title != null && !title.isEmpty();
    }

    @Override
    public String getNoteTitle() {
        return title;
    }

    // Set to false so that getContents can be used to retrieve File Path
    @Override
    public boolean hasContents() {
        return false;
    }

    // Used for retrieving File Path, hence why hasContents is false;
    @Override
    public String getContents() {
        return null;
    }

    @Override
    public boolean hasTag() {
        return false;
    }

    @Override
    public String getTag() {
        return null;
    }

    @Override
    public boolean hasLastEditedTime() {
        return time != null && !time.isEmpty();
    }

    @Override
    public String getLastEditedTime() {
        return time;
    }

    @Override
    public boolean hasFilePaths()
    {
        return filePath != null && !filePath.isEmpty();
    }

    @Override
    public String getFilePaths()
    {
        return filePath;
    }

    @Override
    public String getNoteID()
    {
        return id;
    }
}
