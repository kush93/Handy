package persistancelayer;

import android.content.Context;
import android.graphics.Bitmap;

import java.util.List;

/**
 * Created by Matthias on 16-03-12.
 */
public interface NoteInterface
{
	String OPEN_SAVED  = "OPEN_SAVED";
	String DATA        = "DATA";

	String textNoteType        = "textNote";
	String handWritingNoteType = "handwritingNote";


	// required for main listview
	boolean         hasImages();         // ic_pencil, ic_drawing if null, first image in PhotoNote, or all / some drawing in Handwriting
	List<Bitmap>    getImages();

	boolean         isPinned();             // pin / favourite

	String          getNoteID();        // auto-incremented in DB

	boolean         hasNoteTitle();
	String          getNoteTitle();


	boolean         hasContents();
	String          getContents();

	boolean         hasTag();
	String 			getTag();

	boolean         hasLastEditedTime();
	String          getLastEditedTime();

	boolean         hasFilePaths();
	String          getFilePaths();

	void            openNote(Context context);
	// end of requirements
}
