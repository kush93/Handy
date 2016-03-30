package businesslayer;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;

import java.io.Serializable;
import java.util.List;

import persistancelayer.NoteInterface;

/**
 * Created by rishabhbhandari on 2016-03-29.
 */
public class PhotoNoteWrapper extends Activity implements NoteInterface, Serializable {



    private String id;
    private String title;
    private String contents;
    private String tags;
    private String time;
    private String filePath;
    private boolean isPinned;

    PhotoNoteBL photoNoteBL;

    public PhotoNoteWrapper(String id, String title, String contents, String tags, String time, String filePath, boolean isPinned) {
        this.id = id;
        this.title = title;
        this.contents = contents;
        this.tags = tags;
        this.time = time;
        this.filePath=filePath;
        this.isPinned = isPinned;
    }
    @Override
    public boolean hasImages() {
        return true;
    }

    @Override
    public List<Bitmap> getImages() {
        return null;
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
    public void openNote(Context context) {

    }

    @Override
    public String getNoteID()
    {
        return id;
    }
}
