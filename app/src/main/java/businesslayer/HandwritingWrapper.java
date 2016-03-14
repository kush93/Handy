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

//import android.widget.Toast;

/**
 * Created by Ian on 13/03/2016.
 */
public class HandwritingWrapper extends Activity implements NoteInterface, Serializable {

    private String title;
    private String contents;
    private String tags;
    private String time;
    private boolean isPinned;

    HandwritingBL handwritingBL;
    private static String noteType = "handwritingNote";

    public HandwritingWrapper(String title, String contents, String tags, String time, boolean isPinned) {
        this.title = title;
        this.contents = contents;
        this.tags = tags;
        this.time = time;
        this.isPinned = isPinned;

    }
    public HandwritingWrapper (Context context)
    {
        handwritingBL = new HandwritingBL(context);

    }


    public List<HandwritingWrapper> getSampleNotes(String noteType) {

        List<HandwritingWrapper> handwritingWrappers = new ArrayList<HandwritingWrapper>();
        List<String> returnedNotes = handwritingBL.getSavedData(noteType);

        int listSize=returnedNotes.size();
        if(listSize>0)
        {
            for(int i=0;i<listSize;i++)
            {
                String singleNote=returnedNotes.get(i);
                String token[]=singleNote.trim().split("/");
                // contents retrieves file path, but HandwritingWrapper returns false for hasContents()
                // This is intended
                handwritingWrappers.add(new HandwritingWrapper(token[2],token[5],token[3],token[1],false));
            }
        }

        return handwritingWrappers;
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
        path = path + getContents();
        BitmapFactory.Options bmOptions = new BitmapFactory.Options();
        Bitmap bmp = BitmapFactory.decodeFile(path, bmOptions);
        /**
        if(bmp == null) {
            String popupMsg = ("NULL");
            Toast popupWindow = Toast.makeText(getApplicationContext(),
                    popupMsg, Toast.LENGTH_SHORT);
            popupWindow.show();
        }
         **/
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
        //return contents != null && !contents.isEmpty();
    }

    // Used for retrieving File Path, hence why hasContents is false;
    @Override
    public String getContents() {
        return contents;
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
}
