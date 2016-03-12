package persistancelayer;

import android.graphics.Bitmap;
import android.widget.TextView;

import java.util.List;

/**
 * Created by rishabhbhandari on 2016-02-20.
 *
 * Updated by Matthias on 2016-03-12 for more methods required by main listview.
 */
public interface DataInterface {


    void addData(String noteName, String labelName,String note);

    // required for main listview
    boolean hasThumbnail();         // ic_pencil, ic_drawing if null, first image in PhotoNote, or all / some drawing in Handwriting
    boolean isPinned();             // pin / favourite
    boolean hasTag();
    Bitmap getThumbnail();
    String getTitle();
    String getContents();
    List<String> getTags();
    String getLastEditedTime();


}
