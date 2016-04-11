package businesslayer;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import persistancelayer.NoteInterface;
import presentationlayer.NoteTakerUI;

/**
 * Created by Matthias on 16-03-12.
 *
 * Wraps TextNote and PhotoNote.
 */
public class TextNoteWrapper extends Activity implements NoteInterface, Serializable {
    private String id;
    private String title;
    private String contents;
    private String tags;
    private String time;
    private String filePaths;
    private boolean isPinned;
    TextNoteBL textNoteBL;

    public TextNoteWrapper(String id, String title, String contents, String tags, String time, String filePaths, boolean isPinned) {
        this.id = id;
        this.title = title;
        this.contents = contents;
        this.tags = tags;
        this.time = time;
        this.isPinned = isPinned;
        this.filePaths = filePaths;

    }

    public TextNoteWrapper(Context context) {
        textNoteBL = new TextNoteBL(context);
    }



    @Override
    public void openNote(Context context) {
        NoteTakerUI.openNote(context, this);
    }

    @Override
    public boolean hasImages() {
        return false;
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

    @Override
    public boolean hasContents() {
        return contents != null && !contents.isEmpty();
    }

    @Override
    public String getContents() {
        return contents;
    }

    @Override
    public boolean hasTag() {
        return tags != null && !tags.isEmpty();
    }

    @Override
    public String getTag() {
        return tags;
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
    { return filePaths != null && !filePaths.isEmpty(); }

    @Override
    public String getFilePaths()
    { return filePaths; }

    @Override
    public String getNoteID()
    {
        return id;
    }
}